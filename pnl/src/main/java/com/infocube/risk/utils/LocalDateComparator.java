package com.infocube.risk.utils;

import java.util.Comparator;

import com.datastax.driver.core.LocalDate;

public class LocalDateComparator implements Comparator<LocalDate> {

    @Override
    public int compare(LocalDate o1, LocalDate o2) {
        return o2.getDaysSinceEpoch() - o1.getDaysSinceEpoch();
    }

}
