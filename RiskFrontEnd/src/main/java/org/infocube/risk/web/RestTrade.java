package org.infocube.risk.web;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.entities.Trade;
import com.infocube.risk.services.TradeService;

@Path("Trade")
public class RestTrade {
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/{PortfolioId}")
	public ArrayList<Trade> getTrades(@PathParam("PortfolioId") int PortfolioId)
	{
		return new TradeService().getTrades(PortfolioId);
		
	}
	

}
