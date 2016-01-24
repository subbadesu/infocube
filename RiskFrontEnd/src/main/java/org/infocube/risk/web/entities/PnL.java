package org.infocube.risk.web.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PnL {
	
	int iD;
	double value;
	public int getiD() {
		return iD;
	}
	public void setiD(int iD) {
		this.iD = iD;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PnL [iD=" + iD + ", value=" + value + "]";
	}
	public PnL(int iD, double value) {
		this.iD = iD;
		this.value = value;
	}
	public PnL() {
	}
	


}
