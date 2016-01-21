package com.infocube.risk.var;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;

import com.datastax.driver.core.LocalDate;

public class BaseVarContainer implements VarContainer {

    private Map<Integer, Double> pnlVector;
    private List<Double> sortedPnlValues;
    private int returnDays;

    public BaseVarContainer(Map<Integer, Double> pnlVector, int returnDays) {
        this.pnlVector = pnlVector;
        this.returnDays = returnDays;
        this.sortedPnlValues = new ArrayList<>(pnlVector.values());
        Collections.sort(sortedPnlValues);
    }

    public BaseVarContainer(Map<Integer, Double> pnlVector) {
        this(pnlVector, 1); // assume daily VaR by default
    }

    @Override
    public double getVaR(double confidenceLevel, int numDays) {
        double maxDailyLoss = 0.0;
        double worseValue = 0.0;
        validateVaRParameters(confidenceLevel, numDays);

        if (CollectionUtils.isNotEmpty(sortedPnlValues)) {
            int rank = (int) Math.round(sortedPnlValues.size() * (1 - confidenceLevel));
            worseValue = sortedPnlValues.get(rank);
        }

        Optional<Double> valueToday = pnlVector.values().stream().findFirst();
        maxDailyLoss = worseValue - (valueToday != null ? valueToday.get() : 0.0);
        double scalingFactor = Math.sqrt(numDays / returnDays);

        double var = maxDailyLoss * scalingFactor;
        return Math.abs(var);
    }

    @Override
    public double getExpectedShortFall(double confidenceLevel, int day) {
        double expectedShortFall = 0.0;
        validateVaRParameters(confidenceLevel, day);
        if (CollectionUtils.isNotEmpty(sortedPnlValues)) {
            Optional<Double> valueTodayOptional = pnlVector.values().stream().findFirst();
            double valueToday = valueTodayOptional != null ? valueTodayOptional.get() : 0.0;
            int rank = (int) Math.round(sortedPnlValues.size() * (1 - confidenceLevel));

            List<Double> pnlVectorAtAndBelowVaR = sortedPnlValues.subList(0, rank);
            double sumShortFall = pnlVectorAtAndBelowVaR.stream().reduce(0.0, (d1, d2) -> d1 + (d2 - valueToday));
            double dailyExpectedShortFall = (sumShortFall / (rank == 0 ? 1 : rank));
            double scalingFactor = day / returnDays;
            expectedShortFall = dailyExpectedShortFall * scalingFactor;
        }

        return Math.abs(expectedShortFall);
    }

    @Override
    public int getDayFactor() {
        return returnDays;
    }

    @Override
    public Map<Integer, Double> getPnLVector() {
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
