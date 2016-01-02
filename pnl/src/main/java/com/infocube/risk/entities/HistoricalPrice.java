package com.infocube.risk.entities;

import java.util.Date;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "infocube", name = "inforisk_historical")
public class HistoricalPrice {

    @PartitionKey(value = 0)
    @Column(name = "ticker")
    String symbol;

    @PartitionKey(value = 1)
    @Column(name = "date")
    LocalDate localDate;

    @Column
    double open;

    @Column
    double high;

    @Column
    double low;

    @Column
    double close;

    @Column
    int volume;

    @Column(name = "adj_close")
    double adjustedClose;

    public HistoricalPrice() {

    }

    public HistoricalPrice(String symbol, Date date, double adjustedClose) {
        this.symbol = symbol;
        this.localDate = LocalDate.fromMillisSinceEpoch(date.getTime());
        this.adjustedClose = adjustedClose;
    }

    public HistoricalPrice(String symbol, Date date, double adjustedClose, double close, double open, double high,
            double low, int volume) {
        this.symbol = symbol;
        this.localDate = LocalDate.fromMillisSinceEpoch(date.getTime());
        this.adjustedClose = adjustedClose;
        this.close = close;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public HistoricalPrice(String symbol, LocalDate localDate, double adjustedClose) {
        this.symbol = symbol;
        this.localDate = localDate;
        this.adjustedClose = adjustedClose;
    }

    public HistoricalPrice(String symbol, LocalDate localDate, double adjustedClose, double close, double open,
            double high, double low, int volume) {
        this.symbol = symbol;
        this.localDate = localDate;
        this.adjustedClose = adjustedClose;
        this.close = close;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }
    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol
     *            the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the date
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    /**
     * @return the open
     */
    public double getOpen() {
        return open;
    }

    /**
     * @param open
     *            the open to set
     */
    public void setOpen(double open) {
        this.open = open;
    }

    /**
     * @return the high
     */
    public double getHigh() {
        return high;
    }

    /**
     * @param high
     *            the high to set
     */
    public void setHigh(double high) {
        this.high = high;
    }

    /**
     * @return the low
     */
    public double getLow() {
        return low;
    }

    /**
     * @param low
     *            the low to set
     */
    public void setLow(double low) {
        this.low = low;
    }

    /**
     * @return the close
     */
    public double getClose() {
        return close;
    }

    /**
     * @param close
     *            the close to set
     */
    public void setClose(double close) {
        this.close = close;
    }

    /**
     * @return the volume
     */
    public int getVolume() {
        return volume;
    }

    /**
     * @param volume
     *            the volume to set
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * @return the adjustedclose
     */
    public double getAdjustedClose() {
        return adjustedClose;
    }

    /**
     * @param adj_close
     *            the adjustedclose to set
     */
    public void setAdjustedClose(double adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

}
