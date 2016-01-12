package infocube.infocube_project;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.PropertyConfigurator;

import com.datastax.driver.core.LocalDate;
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

    public static void main(String[] args) {

        DbConnection conn = new DbConnection("localhost", "infocube");
        showPortfolioVarExample(conn);
        showInstrumentVarExample(conn);
        showTradeVarExample(conn);
    }

    private static void showPortfolioVarExample(DbConnection conn) {
        ObjectStore<Portfolio> portfolioStore = conn.getObjectStore(Portfolio.class);
        Portfolio portfolio = portfolioStore.get(1);
        VarCalculator varCalculator = new PortfolioVarCalculator(conn, portfolio);
        System.out.println("Portfolio => " + portfolio);
        calculateVar(varCalculator);
    }

    private static void showTradeVarExample(DbConnection conn) {
        ObjectStore<Trade> tradeStore = conn.getObjectStore(Trade.class);
        Trade trade = tradeStore.get(1, 8);
        VarCalculator varCalculator = new TradeVarCalculator(conn, trade);
        System.out.println("Trade => " + trade);
        calculateVar(varCalculator);
    }

    private static void showInstrumentVarExample(DbConnection conn) {
        ObjectStore<Instrument> instrumentStore = conn.getObjectStore(Instrument.class);
        Instrument instrument = instrumentStore.get(8);
        VarCalculator varCalculator = new InstrumentVarCalculator(conn, instrument, 2000);
        System.out.println("Instrument => " + instrument);
        calculateVar(varCalculator);
    }

    private static void calculateVar(VarCalculator varCalculator) {
        varCalculator.compute(true);
        VarContainer varContainer = varCalculator.getVarContainer();
        Map<LocalDate, Double> pnLVector = varContainer.getPnLVector();
        for (Entry<LocalDate, Double> pnl : pnLVector.entrySet()) {
            System.out.println(pnl);
        }

        int day = 1;
        double confidence = 0.99;

        System.out.println(String.format("%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));
        confidence = 0.95;
        System.out.println(String.format("%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));
        day = 5;
        confidence = 0.99;
        System.out.println(String.format("%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));
        confidence = 0.95;
        System.out.println(String.format("%d-day VaR at %d%% confidence: %f", day, (int) (confidence * 100),
                varContainer.getVaR(confidence, day)));

        day = 1;
        confidence = 0.99;
        System.out.println(String.format("%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
        day = 5;
        System.out.println(String.format("%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
        day = 1;
        confidence = 0.95;
        System.out.println(String.format("%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
        day = 5;
        System.out.println(String.format("%d-day Expected Shortfall at %d%% confidence: %f", day,
                (int) (confidence * 100), varContainer.getExpectedShortFall(confidence, day)));
    }
}
