package infocube.infocube_project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.datastax.driver.core.LocalDate;
/*
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
*/
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.db.ObjectStore;
import com.infocube.risk.entities.HistoricalPrice;
import com.infocube.risk.entities.Instrument;
import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.entities.Trade;

public class SampleDataLoad {
	
	
	
    public static void main(String argv[]) throws ParseException
	{
        DbConnection conn = new DbConnection("localhost", "infocube");
         
        ObjectStore<Trade> tradeStore = conn.getObjectStore(Trade.class);
        tradeStore.save(new Trade(1, 1, 1, 100));
        Trade trade = tradeStore.get(1, 1);
        System.out.println("Trade Id: " + trade.getTradeId() + ", Portfolio Id: " + trade.getPortfolioId()
                + ", Instrument Id: " + trade.getInstrumentId());
        List<Trade> allTrades = tradeStore.getAll(1);
        allTrades.forEach(x -> System.out.println(x));

        ObjectStore<Portfolio> portfolioStore = conn.getObjectStore(Portfolio.class);
        portfolioStore.save(new Portfolio(1, "Equity Portfolio 1"));
        Portfolio portfolio = portfolioStore.get(1);
        System.out.println("Portfolio name: " + portfolio.getPortfolioName());
        portfolioStore.delete(portfolio);

        ObjectStore<Instrument> instrumentStore = conn.getObjectStore(Instrument.class);
        Instrument instrument = new Instrument(1);
        instrument.setSymbol("MSFT");
        instrumentStore.save(instrument);
        instrument = instrumentStore.get(1);
        System.out.println("Instrument symbol: " + instrument.getSymbol());

        ObjectStore<HistoricalPrice> priceStore = conn.getObjectStore(HistoricalPrice.class);
        Date dt = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016");
        HistoricalPrice price = new HistoricalPrice("GS", dt, 180.23);
        priceStore.save(price);
        LocalDate localDate = LocalDate.fromMillisSinceEpoch(dt.getTime());
        price = priceStore.get("GS", localDate);
        System.out.println("Ticker: " + price.getSymbol() + ", date: " + price.getLocalDate() + ", price: "
                + price.getAdjustedClose());

        try {
            conn.close();
        } finally {

        }
	}

}
