create keyspace "infocube" 
		with repliacation = 
		{ 'class' : 'PortfolioStrategy',  'replication_factor' : 2}

CREATE TABLE INFORISK_Portfolio (  
		PortfolioId int  PRIMARY KEY,
		PortfolioName varchar )
		
CREATE TABLE INFORISK_Trade (  
		PortfolioId int   PRIMARY KEY,
		TradeId int,
		Quantity int,
		Notional float,
		NotionalUSD float,
		MarketValue float,
		MarketValueUSD float,
		Instrument int )
		
CREATE TABLE INFORISK_Sensitivity (  
		SensitivityID  int  PRIMARY KEY,
		TradeID int,
		SensitivityType  varchar,
		InstrumentID  int,
		SensitivityValue  float )
		
CREATE TABLE INFORISK_Instrument (  
		InstrumentId  int  PRIMARY KEY,
		CUSIP  varchar,
		SEDOL  varchar,
		RIC  varchar,
		ISIN  varchar,
		Issuer  int,
		Symbol  varchar,
		curve  varchar,
		tenor  varchar,
		currency  varchar )
		
CREATE TABLE INFORISK_Issuer (  
		IssuerID int  PRIMARY KEY,
		IssuerLongName varchar,
		IssuerShortName varchar,
		IssuerRating int,
		IssuerSector int )
		
CREATE TABLE INFORISK_Rating ( 
		RatingID int  PRIMARY KEY,
		MoodyRating varchar,
		SPrating varchar,
		FitchRating varchar,
		InternalRating varchar )
		
CREATE TABLE INFORISK_Sector ( 
		SectorID int  PRIMARY KEY,
		Level1 varchar,
		Level2 varchar,
		Level3 varchar,
		Level4 varchar )
		
CREATE TABLE INFORISK_TimeSeries ( 
		TimeSeriesID int  PRIMARY KEY,
		Date Date,
		DataValue float )
		
CREATE TABLE INFORISK_TimeSeriesMapping (  
		TimeSeriesID int  PRIMARY KEY,
		InstrumentID int,
		Symbol varchar,
		Type varchar,
		Curve varchar,
		Currency varchar,
		Tenor varchar )
		






		
