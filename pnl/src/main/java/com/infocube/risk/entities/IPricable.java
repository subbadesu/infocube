package com.infocube.risk.entities;

import java.util.Collection;

public interface IPricable {

	double getPv();
	double getPv(double withShock); // r=> Shocked Pv = getPv() * (1+r) or getPv()*exp(r*t)
	
	Collection<Pnl> getFv(Collection<Double> shocks);
}
