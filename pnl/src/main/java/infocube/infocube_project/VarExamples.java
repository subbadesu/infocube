package infocube.infocube_project;

import java.util.Map;
import org.apache.log4j.Logger;

import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.entities.Trade;
import com.infocube.risk.var.VarCalculator;
import com.infocube.risk.var.VarContainer;
import com.infocube.risk.var.historic.InstrumentVarCalculator;
import com.infocube.risk.var.historic.PortfolioVarCalculator;
import com.infocube.risk.var.historic.TradeVarCalculator;

public class VarExamples {

    private static final Logger LOG = Logger.getLogger(VarExamples.class);

    public static void main(String[] args) {

        DbConnection conn = new DbConnection("localhost", "infocube");

        doMonteCarloVarExample(conn);
        doHistoricVarExample(conn);

        conn.close();
    }

    private static void doMonteCarloVarExample(DbConnection conn) {
        showInstrumentMonteCarloVarExample(conn);
        showTradeMonteCarloVarExample(conn);
        showPortfolioMonteCarloVarExample(conn);
    }

    private static void doHistoricVarExample(DbConnection conn) {
        showInstrumentHistoricVarExample(conn);
        showTradeHistoricVarExample(conn);
        showPortfolioHistoricVarExample(conn);
    }

    private static void showInstrumentMonteCarloVarExample(DbConnection conn) {
        ObjectStore<Instrument> instrumentStore = conn.getObjectStore(Instrument.class);
        for (int i = 1; i < 8; i++) {
            Instrument instrument = instrumentStore.get(i);
            VarCalculator varCalculator = new com.infocube.risk.var.montecarlo.InstrumentVarCalculator(conn, instrument,
                    2000);
            LOG.info("MC VaR for Instrument => " + instrument);
            calculateVar(varCalculator);
        }
    }

    private static void showPortfolioMonteCarloVarExample(DbConnection conn) {
        ObjectStore<Portfolio> portfolioStore = conn.getObjectStore(Portfolio.class);
        Portfolio portfolio = portfolioStore.get(1);
        VarCalculator varCalculator = new com.infocube.risk.var.montecarlo.PortfolioVarCalculator(conn, portfolio);
        LOG.info("MC VaR for Portfolio => " + portfolio);
        calculateVar(varCalculator);
    }

    private static void showTradeMonteCarloVarExample(DbConnection conn) {
        ObjectStore<Trade> tradeStore = conn.getObjectStore(Trade.class);
        Trade trade = tradeStore.get(1, 8);
        VarCalculator varCalculator = new com.infocube.risk.var.montecarlo.TradeVarCalculator(conn, trade);
        LOG.info("MC VaR for Trade => " + trade);
        calculateVar(varCalculator);
    }

    private static void showPortfolioHistoricVarExample(DbConnection conn) {
        ObjectStore<Portfolio> portfolioStore = conn.getObjectStore(Portfolio.class);
        Portfolio portfolio = portfolioStore.get(1);
        VarCalculator varCalculator = new PortfolioVarCalculator(conn, portfolio);
        LOG.info("Historic VaR for Portfolio => " + portfolio);
        calculateVar(varCalculator);
    }

    private static void showTradeHistoricVarExample(DbConnection conn) {
        ObjectStore<Trade> tradeStore = conn.getObjectStore(Trade.class);
        Trade trade = tradeStore.get(1, 8);
        VarCalculator varCalculator = new TradeVarCalculator(conn, trade);
        LOG.info("Historic VaR for Trade => " + trade);
        calculateVar(varCalculator);
    }

    private static void showInstrumentHistoricVarExample(DbConnection conn) {
        for (int i = 1; i < 8; i++) {
            ObjectStore<Instrument> instrumentStore = conn.getObjectStore(Instrument.class);
            Instrument instrument = instrumentStore.get(i);
            VarCalculator varCalculator = new InstrumentVarCalculator(conn, instrument, 2000);
            LOG.info("Historic VaR for Instrument => " + instrument);
            calculateVar(varCalculator);
        }
    }

    private static void calculateVar(VarCalculator varCalculator) {
        varCalculator.compute(true);
        VarContainer varContainer = varCalculator.getVarContainer();
        Map<Integer, Double> pnLVector = varContainer.getPnLVector();
        // for (Entry<Integer, Double> pnl : pnLVector.entrySet()) {
        // LOG.info(pnl);
        // }

        int day = 1;
        double confidence = 0.99;

        LOG.info(String.format("\t%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));
        confidence = 0.95;
        LOG.info(String.format("\t%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));
        day = 5;
        confidence = 0.99;
        LOG.info(String.format("\t%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));
        confidence = 0.95;
        LOG.info(String.format("\t%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));

        day = 1;
        confidence = 0.99;
        LOG.info(String.format("\t%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
        day = 5;
        LOG.info(String.format("\t%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
        day = 1;
        confidence = 0.95;
        LOG.info(String.format("\t%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
        day = 5;
        LOG.info(String.format("\t%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
    }
}
