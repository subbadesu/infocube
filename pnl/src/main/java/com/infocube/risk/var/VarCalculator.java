package com.infocube.risk.var;

public interface VarCalculator {
    void compute(boolean refresh);

    VarContainer getVarContainer();
}
