package org.infocube.risk.web.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.infocube.risk.web.entities.PnL;

public class RiskFrontEndUtil {
	
	
	public static ArrayList<PnL> convertoPnLObjects(Map<Integer,Double> pnlVector)
	{
		ArrayList<PnL> pnlVectortoFrontEnd = new ArrayList<PnL>();
		 Iterator<Entry<Integer, Double>> it  = pnlVector.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Integer,Double> pair = (Map.Entry<Integer ,Double>)it.next();
		        PnL pnL = new PnL(pair.getKey().intValue(),pair.getValue().doubleValue());
		        pnlVectortoFrontEnd.add(pnL);
		        System.out.println(pair.getKey() + " : " + pair.getValue());
		    }

		return pnlVectortoFrontEnd;
		
		
		 
		
	}
	

}
