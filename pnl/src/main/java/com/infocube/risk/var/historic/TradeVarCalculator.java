package com.infocube.risk.var.historic;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.datastax.driver.core.LocalDate;
import com.infocube.risk.db.Connection;
import com.infocube.risk.db.ConnectionProperties;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Trade;
import com.infocube.risk.utils.LocalDateComparator;
import com.infocube.risk.var.BaseVarContainer;
import com.infocube.risk.var.VarCalculator;
import com.infocube.risk.var.VarContainer;

public class TradeVarCalculator implements VarCalculator {

    private Trade trade;
    private VarContainer tradeVarContainer;
    private Connection connection;

    public TradeVarCalculator(Trade trade) {
        this(new DbConnection(new ConnectionProperties()), trade);
    }

    public TradeVarCalculator(Connection connection, Trade trade) {
        this.connection = connection;
        this.trade = trade;
    }

    @Override
    public void compute(boolean refresh) {
        if (tradeVarContainer == null || refresh) {
            int instrumentId = trade.getInstrumentId();
            ObjectStore<Instrument> instStore = null;
            instStore = connection.getObjectStore(Instrument.class);
            if (instStore != null) {
                Instrument instrument = instStore.get(instrumentId);
                VarCalculator varCalculator = new InstrumentVarCalculator(instrument);
                varCalculator.compute(refresh);
                VarContainer varContainer = varCalculator.getVarContainer();
                Map<LocalDate, Double> pnLVector = varContainer.getPnLVector();
                Map<LocalDate, Double> tradePnLVector = new TreeMap<>(new LocalDateComparator());
                for (Entry<LocalDate, Double> pnl : pnLVector.entrySet()) {
                    tradePnLVector.put(pnl.getKey(), pnl.getValue() * trade.getQuantity());
                }
                tradeVarContainer = new BaseVarContainer(tradePnLVector);
            }
        }
    }

    @Override
    public VarContainer getVarContainer() {
        return tradeVarContainer;
    }

}
