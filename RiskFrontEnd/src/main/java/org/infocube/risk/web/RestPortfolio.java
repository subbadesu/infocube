package org.infocube.risk.web;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.services.PortfolioService;

@Path("Portfolio")
public class RestPortfolio {
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Portfolio> getPortfolios()
	{
		return new PortfolioService().getPortfolios();
		
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	
	@Path("/{PortfolioId}")
	public String getPortfoliosID(@PathParam("messageId") String Portfolio)
	{
		return "Got the portofolios()" + Portfolio ;
		
	}

}
