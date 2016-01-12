package com.infocube.risk.var;

import java.util.Map;

import com.datastax.driver.core.LocalDate;

public interface VarContainer {

    /**
     * @return day - typically 1
     */
    int getDayFactor();
    
    /**
     * @return PnL Vector
     */
    Map<LocalDate, Double> getPnLVector();

    /**
     * @param confidenceLevel
     *            VaR Confidence Level between 0.00 and 1.00 e.g 0.99 for 99% confidence
     * @param day
     *            VaR Period e.g. 5-day
     * @return VaR value
     */
    double getVaR(double confidenceLevel, int day);

    /**
     * @param confidenceLevel
     *            VaR Confidence Level between 0.00 and 1.00 e.g 0.99 for 99% confidence
     * @param day
     *            VaR Period e.g. 5-day
     * @return VaR value
     */
    double getExpectedShortFall(double confidenceLevel, int day);
}
