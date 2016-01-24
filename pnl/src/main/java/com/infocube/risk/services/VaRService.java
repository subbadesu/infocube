package com.infocube.risk.services;

import java.util.Map;

import org.apache.log4j.Logger;

import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.var.VarCalculator;
import com.infocube.risk.var.VarContainer;
import com.infocube.risk.var.historic.PortfolioVarCalculator;

public class VaRService {

    private static final Logger LOG = Logger.getLogger(VaRService.class);

 
     public  Map<Integer,Double> computePortfolioHistricalVaR(int portfolioID) {
        DbConnection conn = new DbConnection("localhost", "infocube");
    	ObjectStore<Portfolio> portfolioStore = conn.getObjectStore(Portfolio.class);
        Portfolio portfolio = portfolioStore.get(portfolioID);
        VarCalculator varCalculator = new PortfolioVarCalculator(conn, portfolio);
        LOG.info("Historic VaR for Portfolio => " + portfolio);
        calculateVar(varCalculator);
        VarContainer varContainer = varCalculator.getVarContainer();
        return varContainer.getPnLVector();
    }



    private  void calculateVar(VarCalculator varCalculator) {
        varCalculator.compute(true);
        VarContainer varContainer = varCalculator.getVarContainer();
        Map<Integer, Double> pnLVector = varContainer.getPnLVector();
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
