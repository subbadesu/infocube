package com.infocube.risk.var;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.cassandra.exceptions.InvalidRequestException;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.DbStore;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.HistoricalPrice;
import com.infocube.risk.entities.Instrument;

public class InstrumentVarCalculator implements VarCalculator {

    private static final String HISTORICAL_TABLE_NAME = "inforisk_historical";
    private static final String DBSCHEMA_NAME = "infocube";
    private static final String DBHOST_NAME = "localhost";
    private static final int DEFAULT_MAX_DAYS_FOR_RETURNS = 252; // # of days available
    private static final String REALTIME_PRICE_QUOTE_CSV_URL = "http://finance.yahoo.com/d/quotes.csv?s=%s&f=%s";
    private static final String REALTIME_FORMAT = "d1l1mov";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private Instrument instrument;
    private int maxDaysForReturns;
    private VarContainer varContainer;
    private boolean useLatestClosingForToday;
    private boolean updateLatestPrice;

    public InstrumentVarCalculator(Instrument instrument) { // TODO: inject Connection
        this(instrument, DEFAULT_MAX_DAYS_FOR_RETURNS);
    }

    public InstrumentVarCalculator(Instrument instrument, int maxDaysForReturns) {
        this(instrument, maxDaysForReturns, true);
    }

    public InstrumentVarCalculator(Instrument instrument, int maxDaysForReturns, boolean useLatestClosingForToday) {
        this.instrument = instrument;
        this.maxDaysForReturns = maxDaysForReturns;
        this.useLatestClosingForToday = useLatestClosingForToday;
    }

    @Override
    public void compute(boolean refresh) {
        if (varContainer == null || refresh) {
            double priceToday = getPriceToday();
            List<Double> dailyReturns = computeDailyReturns();
            List<Double> pnlVector = new ArrayList<Double>(dailyReturns.size());

            int day = 1;
            for (Double dailyReturn : dailyReturns) {
                double projectedPrice = priceToday * Math.exp(dailyReturn * day);
                pnlVector.add(projectedPrice);
            }
            Collections.sort(pnlVector);

            varContainer = new BaseVarContainer(pnlVector);
        }
    }

    @Override
    public VarContainer getVarContainer() {
        return varContainer;
    }

    private List<Double> computeDailyReturns() {
        String symbol = instrument.getSymbol();
        List<Double> closingPrices = new ArrayList<>();
        try (DbConnection dbConnection = new DbConnection(DBHOST_NAME, DBSCHEMA_NAME)) {
            Session session = dbConnection.getSession();
            Select selectStmt = QueryBuilder.select().column("date").column("adj_close")
                    .from(DBSCHEMA_NAME, HISTORICAL_TABLE_NAME).where(QueryBuilder.eq("ticker", symbol))
                    .limit(maxDaysForReturns);
            ResultSet resultSet = session.execute(selectStmt);

            for (Row row : resultSet) {
                closingPrices.add(row.getDouble("adj_close"));
            }
        }

        List<Double> dailyReturns = new ArrayList<>(closingPrices.size() - 1);
        for (int i = 0; i < closingPrices.size(); i++) {
            if (i + 1 < closingPrices.size()) {
                dailyReturns.add(Math.log(closingPrices.get(i) / closingPrices.get(i + 1)));
            }
        }

        return dailyReturns;
    }

    private double getPriceToday() {
        String symbol = instrument.getSymbol();
        Pair<LocalDate, Double> latestHistoricDateAndPrice = getLatestHistoricPrice(symbol);
        if (useLatestClosingForToday) {
            if (latestHistoricDateAndPrice != null) {
                return latestHistoricDateAndPrice.second;
            }
        }

        /*
        Calendar today = Calendar.getInstance();
        int yearOfToday = today.get(Calendar.YEAR);
        int monthOfToday = today.get(Calendar.MONTH);
        int dayOfToday = today.get(Calendar.DAY_OF_MONTH);
        if (latestHistoricDateAndPrice != null) {
            LocalDate latestHistoricDate = latestHistoricDateAndPrice.first;
            int yearOfLatestHistoricPrice = latestHistoricDate.getYear();
            int monthOfLatestHistoricPrice = latestHistoricDate.getMonth();
            int dayOfLatestHistoricPrice = latestHistoricDate.getDay();

            if ((dayOfToday == dayOfLatestHistoricPrice) && (monthOfToday == monthOfLatestHistoricPrice)
                    && (yearOfToday == yearOfLatestHistoricPrice)) {
                return latestHistoricDateAndPrice.second;
            }
        }

        else {
        */
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

        try (DbConnection dbConnection = new DbConnection(DBHOST_NAME, DBSCHEMA_NAME)) {
            ObjectStore<HistoricalPrice> priceStore = dbConnection.getObjectStore(HistoricalPrice.class);
            priceStore.save(historicalPrice);

        }
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
        } catch (InvalidRequestException | ParseException | IOException e) {
            e.printStackTrace();
        }

        return datePricePair;
    }

    private Pair<LocalDate, Double> getLatestHistoricPrice(String symbol) {
        try (DbConnection dbConnection = new DbConnection(DBHOST_NAME, DBSCHEMA_NAME)) {
            Session session = dbConnection.getSession();
            Select selectStmt = QueryBuilder.select().column("date").column("adj_close")
                    .from(DBSCHEMA_NAME, HISTORICAL_TABLE_NAME).where(QueryBuilder.eq("ticker", symbol)).limit(1);
            ResultSet resultSet = session.execute(selectStmt);
            Row row = resultSet.one();
            if (row != null) {
                return new Pair<>(row.getDate("date"), row.getDouble("adj_close"));
            }
        }
        return null;
    }

    public int getMaxDaysForReturns() {
        return maxDaysForReturns;
    }

    public void setMaxDaysForReturns(int maxDaysForReturns) {
        this.maxDaysForReturns = maxDaysForReturns;
    }

    static class Pair<T1, T2> {
        T1 first;
        T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    };

}
