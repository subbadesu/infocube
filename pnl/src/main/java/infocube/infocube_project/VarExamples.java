package infocube.infocube_project;

import java.util.List;

import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Trade;
import com.infocube.risk.var.VarCalculator;
import com.infocube.risk.var.VarContainer;
import com.infocube.risk.var.historic.InstrumentVarCalculator;
import com.infocube.risk.var.historic.TradeVarCalculator;

public class VarExamples {

    public static void main(String[] args) {
        DbConnection conn = new DbConnection("localhost", "infocube");
        showInstrumentVarExample(conn);
        showTradeVarExample(conn);
    }

    private static void showTradeVarExample(DbConnection conn) {
        ObjectStore<Trade> tradeStore = conn.getObjectStore(Trade.class);
        Trade trade = tradeStore.get(1, 8);
        VarCalculator varCalculator = new TradeVarCalculator(trade);
        System.out.println("Trade => " + trade);
        calculateVar(varCalculator);
    }

    private static void showInstrumentVarExample(DbConnection conn) {
        ObjectStore<Instrument> instrumentStore = conn.getObjectStore(Instrument.class);
        Instrument instrument = instrumentStore.get(8);
        VarCalculator varCalculator = new InstrumentVarCalculator(instrument, 2000);
        System.out.println("Instrument => " + instrument);
        calculateVar(varCalculator);
    }

    private static void calculateVar(VarCalculator varCalculator) {
        varCalculator.compute(true);
        VarContainer varContainer = varCalculator.getVarContainer();
        List<Double> pnLVector = varContainer.getPnLVector();
        for (Double pnl : pnLVector) {
            System.out.println(pnl);
        }

        System.out.println("1-day VaR at 99% confidence: " + varContainer.getVaR(0.99, 1));
        System.out.println("5-day VaR at 95% confidence: " + varContainer.getVaR(0.99, 5));
        System.out.println("1-day VaR at 95% confidence: " + varContainer.getVaR(0.95, 1));
        System.out.println("5-day VaR at 95% confidence: " + varContainer.getVaR(0.95, 5));

        System.out.println("1-day Expected Shortfall at 99% confidence: " + varContainer.getExpectedShortFall(0.99, 1));
        System.out.println("5-day Expected Shortfall at 95% confidence: " + varContainer.getExpectedShortFall(0.99, 5));
        System.out.println("1-day Expected Shortfall at 95% confidence: " + varContainer.getExpectedShortFall(0.95, 1));
        System.out.println("5-day Expected Shortfall at 95% confidence: " + varContainer.getExpectedShortFall(0.95, 5));
    }
}
