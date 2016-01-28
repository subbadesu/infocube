package com.infocube.risk.var.montecarlo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.apache.cassandra.exceptions.InvalidRequestException;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.google.common.base.Ticker;
import com.infocube.risk.db.Connection;
import com.infocube.risk.db.ConnectionProperties;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.HistoricalPrice;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.utils.Constants;
import com.infocube.risk.utils.LocalDateComparator;
import com.infocube.risk.utils.Pair;
import com.infocube.risk.utils.PriceUtils;
import com.infocube.risk.var.BaseVarContainer;
import com.infocube.risk.var.VarCalculator;
import com.infocube.risk.var.VarContainer;

public class InstrumentVarCalculator implements VarCalculator {

    private static final int DEFAULT_NUM_TRIALS = 1000; // # of days available
    private static final String REALTIME_PRICE_QUOTE_CSV_URL = "http://finance.yahoo.com/d/quotes.csv?s=%s&f=%s";
    private static final String REALTIME_FORMAT = "d1l1mov";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private Instrument instrument;
    private int numTrials;
    private VarContainer varContainer;
    private boolean useLatestClosingForToday;
    private boolean updateLatestPrice;
    private Connection connection;
    private final NormalDistribution normalDistribution = new NormalDistribution(0, 1);
    private final Random random = new Random();

    public InstrumentVarCalculator(Instrument instrument) { // TODO: inject Connection
        this(instrument, DEFAULT_NUM_TRIALS);
    }

    public InstrumentVarCalculator(Instrument instrument, int numTrials) {
        this(instrument, numTrials, true);
    }

    public InstrumentVarCalculator(Instrument instrument, int numTrials, boolean useLatestClosingForToday) {
        this(new DbConnection(new ConnectionProperties()), instrument, numTrials, useLatestClosingForToday);
    }

    public InstrumentVarCalculator(Connection connection, Instrument instrument, int numTrials) {
        this(connection, instrument, numTrials, true);
    }

    public InstrumentVarCalculator(Connection connection, Instrument instrument, int numTrials,
            boolean useLatestClosingForToday) {
        this.connection = connection;
        this.instrument = instrument;
        this.numTrials = numTrials;
        this.useLatestClosingForToday = useLatestClosingForToday;
    }

    public InstrumentVarCalculator(Connection connection, Instrument instrument) {
        this(connection, instrument, Constants.DEFAULT_MAX_DAYS_FOR_RETURNS);
    }

    @Override
    public void compute(boolean refresh) {
        if (varContainer == null || refresh) {
            double priceToday = getPriceToday();
            Map<Integer, Double> pnlVector = new TreeMap<>();
            Pair<LocalDate, NormalParameters> normalParameters = getNormalParameters(instrument.getSymbol());
            if (normalParameters != null) {
                double mean = normalParameters.second.getMean();
                double stddev = normalParameters.second.getStddev();
                double drift = mean - (stddev * stddev) * 0.5;
                for (int i = 0; i < numTrials; i++) {
                    double cumulativeProbability = random.nextDouble(); // between 0 and 1
                    double projectedPrice = priceToday * Math.exp(drift
                            + stddev * stddev * normalDistribution.inverseCumulativeProbability(cumulativeProbability));
                    pnlVector.put(i, projectedPrice - priceToday);
                }
            }

            varContainer = new BaseVarContainer(pnlVector, priceToday);
        }
    }

    @Override
    public VarContainer getVarContainer() {
        return varContainer;
    }

    private double getPriceToday() {
        String symbol = instrument.getSymbol();
        com.infocube.risk.utils.Pair<LocalDate, Double> latestHistoricDateAndPrice = PriceUtils
                .getLatestHistoricPrice((DbConnection) connection, symbol);
        if (useLatestClosingForToday) {
            if (latestHistoricDateAndPrice != null) {
                return latestHistoricDateAndPrice.second;
            }
        }

        Pair<Date, Double> currentPriceDateAndValue = getPriceOnline(symbol);
        if (currentPriceDateAndValue != null) {
            return currentPriceDateAndValue.second;
        }
        return 0.0; // unknown price - flat and 0 var.
    }

    private void updateHistoricPrice(String symbol, Pair<Date, Double> priceDateAndValue, List<String> line) {
        String daysRange = line.get(2);
        String[] lowHighArray = daysRange.split("-");
        Double low = new Double(lowHighArray[0]);
        Double high = new Double(lowHighArray[1]);
        Double open = new Double(line.get(3));
        Integer volume = Integer.parseInt(line.get(4));
        HistoricalPrice historicalPrice = new HistoricalPrice(symbol, priceDateAndValue.first,
                priceDateAndValue.second.doubleValue(), priceDateAndValue.second.doubleValue(), open.doubleValue(),
                high.doubleValue(), low.doubleValue(), volume.intValue());

        ObjectStore<HistoricalPrice> priceStore = connection.getObjectStore(HistoricalPrice.class);
        priceStore.save(historicalPrice);
    }

    private Pair<Date, Double> getPriceOnline(String symbol) {
        Pair<Date, Double> datePricePair = null;
        HttpURLConnection conn;
        try {
            URL url = new URL(String.format(REALTIME_PRICE_QUOTE_CSV_URL, symbol, REALTIME_FORMAT));
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                CsvListReader csvReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE)) {
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Current price data not found for " + symbol);
                return null;
            }

            // csvReader.getHeader(false);

            List<String> line;
            while ((line = csvReader.read()) != null) {
                Date dt = DATE_FORMAT.parse(line.get(0));
                Double price = new Double(line.get(1));
                datePricePair = new Pair<>(dt, price);

                // function with side-effect - bad, re-factor later on
                if (updateLatestPrice) {
                    updateHistoricPrice(symbol, datePricePair, line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datePricePair;
    }

    private Pair<LocalDate, NormalParameters> getNormalParameters(String symbol) {
        Session session = ((DbConnection) connection).getSession();
        Select selectStmt = QueryBuilder.select().column("date").column("mean").column("stddev")
                .from(Constants.DBSCHEMA_NAME, Constants.NORMAL_PARAMETER_TABLE_NAME)
                .where(QueryBuilder.eq("ticker", symbol))
                .limit(1);
        ResultSet resultSet = session.execute(selectStmt);
        Row row = resultSet.one();
        if (row != null) {
            return new Pair<>(row.getDate("date"),
                    new NormalParameters(row.getDouble("mean"), row.getDouble("stddev")));
        }
        return null;
    }

}
