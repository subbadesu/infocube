package com.infocube.risk.var;

import java.util.List;
import java.util.stream.Collectors;

import com.infocube.risk.db.ConnectionProperties;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Trade;

public class TradeVarCalculator implements VarCalculator {

    private Trade trade;
    private VarContainer tradeVarContainer;

    public TradeVarCalculator(Trade trade) {
        this.trade = trade;
    }

    @Override
    public void compute(boolean refresh) {
        if (tradeVarContainer == null || refresh) {
            int instrumentId = trade.getInstrumentId();
            ObjectStore<Instrument> instStore = null;
            try (DbConnection dbConnection = new DbConnection(new ConnectionProperties())) {
                instStore = dbConnection.getObjectStore(Instrument.class);
            }
            if (instStore != null) {
                Instrument instrument = instStore.get(instrumentId);
                VarCalculator varCalculator = new InstrumentVarCalculator(instrument);
                varCalculator.compute(refresh);
                VarContainer varContainer = varCalculator.getVarContainer();
                List<Double> tradePnLVector = varContainer.getPnLVector().stream().map(x -> x * trade.getQuantity())
                        .collect(Collectors.toList());

                tradeVarContainer = new BaseVarContainer(tradePnLVector);
            }
        }
    }

    @Override
    public VarContainer getVarContainer() {
        return tradeVarContainer;
    }

}
