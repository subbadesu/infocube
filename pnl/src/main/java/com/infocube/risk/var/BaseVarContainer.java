package com.infocube.risk.var;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class BaseVarContainer implements VarContainer {

    private List<Double> pnlVector;
    private int returnDays;

    public BaseVarContainer(List<Double> pnlVector, int returnDays) {
        this.pnlVector = pnlVector;
        this.returnDays = returnDays;
    }

    public BaseVarContainer(List<Double> pnlVector) {
        this(pnlVector, 1); // assume daily VaR by default
    }

    @Override
    public double getVaR(double confidenceLevel, int numDays) {
        double valueAtRisk = 0.0;
        validateVaRParameters(confidenceLevel, numDays);

        if (CollectionUtils.isNotEmpty(pnlVector)) {
            int rank = (int) Math.round(pnlVector.size() * (1 - confidenceLevel));
            valueAtRisk = pnlVector.get(rank) * Math.sqrt(numDays / returnDays);
        }

        return valueAtRisk;
    }

    @Override
    public double getExpectedShortFall(double confidenceLevel, int day) {
        double expectedShortFall = 0.0;
        validateVaRParameters(confidenceLevel, day);
        if (CollectionUtils.isNotEmpty(pnlVector)) {
            int rank = (int) Math.round(pnlVector.size() * (1 - confidenceLevel));

            List<Double> pnlVectorAtAndBelowVaR = pnlVector.subList(0, rank);
            double sumShortFall = pnlVectorAtAndBelowVaR.stream().reduce(0.0, (d1, d2) -> d1 + d2);
            expectedShortFall = sumShortFall / (rank == 0 ? 1 : rank - 0);
        }

        return expectedShortFall;
    }

    @Override
    public int getDayFactor() {
        return returnDays;
    }

    @Override
    public List<Double> getPnLVector() {
        return pnlVector;
    }

    private void validateVaRParameters(double confidenceLevel, int day) {
        if (day % returnDays != 0) {
            throw new IllegalArgumentException(String.format(
                    "Cannot calculate %d-day VaR based on PnL vector calculated using %d-day return", day, returnDays));
        }

        if (confidenceLevel < 0.00 && confidenceLevel > 1.00) {
            throw new IllegalArgumentException("Confidence Level must be between 0 and 1 (e.g. 0.95 implies 95%");
        }
    }

}
