package com.infocube.risk.var.montecarlo;

public class NormalParameters {

    private double mean;

    /**
     * @return the mean
     */
    public double getMean() {
        return mean;
    }

    /**
     * @param mean
     *            the mean to set
     */
    public void setMean(double mean) {
        this.mean = mean;
    }

    /**
     * @return the stddev
     */
    public double getStddev() {
        return stddev;
    }

    /**
     * @param stddev
     *            the stddev to set
     */
    public void setStddev(double stddev) {
        this.stddev = stddev;
    }

    private double stddev;

    public NormalParameters(double mean, double stddev) {
        this.mean = mean;
        this.stddev = stddev;
    }
}
