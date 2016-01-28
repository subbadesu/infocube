package com.infocube.risk.var.historic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.datastax.driver.core.LocalDate;
import com.infocube.risk.db.Connection;
import com.infocube.risk.db.ConnectionProperties;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.entities.Trade;
import com.infocube.risk.utils.LocalDateComparator;
import com.infocube.risk.var.BaseVarContainer;
import com.infocube.risk.var.VarCalculator;
import com.infocube.risk.var.VarContainer;

/**
 * @author Partha
 *
 */
public class PortfolioVarCalculator implements VarCalculator {

    private Portfolio portfolio;
    private VarContainer portfolioVarContainer;
    private Connection connection;

    public PortfolioVarCalculator(Portfolio portfolio) {
        this(new DbConnection(new ConnectionProperties()), portfolio);
    }

    public PortfolioVarCalculator(Connection connection, Portfolio portfolio) {
        this.connection = connection;
        this.portfolio = portfolio;
    }

    @Override
    public void compute(boolean refresh) {
        if (portfolioVarContainer == null || refresh) {
            int portfolioId = portfolio.getPortfolioId();
            ObjectStore<Trade> tradeStore = null;
            tradeStore = connection.getObjectStore(Trade.class);
            if (tradeStore != null) {
                List<Trade> trades = tradeStore.getAll(portfolioId);
                Map<Trade, Map<Integer, Double>> tradePnlVector = new HashMap<>();
                Set<Integer> allTradeDates = new TreeSet<>();
                double portfolioPriceToday = 0.0;
                for (Trade trade : trades) {
                    // parallelize this
                    TradeVarCalculator tradeVarCalculator = new TradeVarCalculator(connection, trade);
                    tradeVarCalculator.compute(refresh);
                    VarContainer varContainer = tradeVarCalculator.getVarContainer();
                    if (varContainer != null) {
                        Map<Integer, Double> pnlVector = varContainer.getPnLVector();
                        tradePnlVector.put(trade, pnlVector);
                        allTradeDates.addAll(pnlVector.keySet());
                        portfolioPriceToday += varContainer.getPriceToday();
                    }
                }

                Map<Integer, Double> portfolioPnlVector = new TreeMap<>();
                for (Integer tradeDate : allTradeDates) {
                    double portfolioPnl = 0;
                    boolean atLeastOneTrade = false;
                    for (Trade trade : trades) {
                        Map<Integer, Double> pnlVector = tradePnlVector.get(trade);
                        Double tradePnl = pnlVector.get(tradeDate);
                        if (tradePnl != null) {
                            portfolioPnl += tradePnl;
                            atLeastOneTrade = true;
                        }
                    }
                    if (atLeastOneTrade) {
                        portfolioPnlVector.put(tradeDate, portfolioPnl);
                    }
                }
                portfolioVarContainer = new BaseVarContainer(portfolioPnlVector, portfolioPriceToday);
            }
        }
    }

    @Override
    public VarContainer getVarContainer() {
        return portfolioVarContainer;
    }

}
