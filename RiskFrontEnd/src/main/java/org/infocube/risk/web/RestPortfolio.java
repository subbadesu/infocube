package org.infocube.risk.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.infocube.risk.web.entities.PnL;
import org.infocube.risk.web.util.RiskFrontEndUtil;

import com.infocube.risk.entities.Portfolio;
import com.infocube.risk.services.PortfolioService;
//import com.infocube.risk.services.VaRService;
import com.infocube.risk.services.VaRService;

@Path("Portfolio")
public class RestPortfolio {
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Portfolio> getPortfolios()
	{
		return new PortfolioService().getPortfolios();
		
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/{PortfolioId}")
    public List<Portfolio> getPortfoliosID(@PathParam("PortfolioId") int PortfolioId)
	{
		return new PortfolioService().getPortfolio(PortfolioId);
		
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("VaR/{PortfolioId}")
    public ArrayList<PnL> getVaRPortfolio(@PathParam("PortfolioId") int portfolioId)
	{
        VaRService varService = new VaRService();
        Map<Integer, Double> PnLVector = varService.computePortfolioHistricalVaR(portfolioId);
		return (RiskFrontEndUtil.convertoPnLObjects(PnLVector));
	}

}
