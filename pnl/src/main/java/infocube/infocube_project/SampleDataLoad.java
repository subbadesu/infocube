package infocube.infocube_project;

import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.datastax.driver.core.Cluster;
/*
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
*/
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.entities.Trade;

public class SampleDataLoad {
	
	
	
	public static void main (String argv[])
	{
		DbConnection conn = new DbConnection("localhost", "infocube");
		ObjectStore<Portfolio> portfolioStore = conn.getObjectStore(Portfolio.class);
		portfolioStore.save(new Portfolio(1, "Equity Portfolio 1"));
		Portfolio portfolio = portfolioStore.get(1);
		System.out.println("Portfolio name: " + portfolio.getPortfolioName());
		//portfolioStore.delete(portfolio);

		ObjectStore<Instrument> instrumentStore = conn.getObjectStore(Instrument.class);
		Instrument instrument = new Instrument(1);
		instrument.setSymbol("MSFT");
		instrumentStore.save(instrument);
		instrument = instrumentStore.get(1);
		System.out.println("Instrument symbol: " + instrument.getSymbol());

		ObjectStore<Trade> tradeStore = conn.getObjectStore(Trade.class);
		tradeStore.save(new Trade(1, 1, 1, 100));
		Trade trade = tradeStore.get(1, 1);
		System.out.println("Trade Id: " + trade.getTradeId() + ", Portfolio Id: " + trade.getPortfolioId()
		                  +", Instrument Id: " + trade.getInstrumentId());
		
	}

}
