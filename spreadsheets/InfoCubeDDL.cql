--DROP KEYSPACE infocube;
CREATE KEYSPACE IF NOT EXISTS infocube
		with replication = 
		{ 'class' : 'SimpleStrategy',  'replication_factor' : 1};

CREATE TABLE IF NOT EXISTS infocube.inforisk_Portfolio (  
		PortfolioId int  PRIMARY KEY,
		PortfolioName varchar );
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_Trade (  
		PortfolioId int  ,
		TradeId int,
		Quantity int,
		Notional double,
		NotionalUSD double,
		MarketValue double,
		MarketValueUSD double,
		InstrumentId int ,
		PRIMARY KEY (PortfolioId, TradeId));
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_Sensitivity (  
		SensitivityId  int,
		TradeId int,
		SensitivityType  varchar,
		InstrumentId  int,
		SensitivityValue  double,
		PRIMARY KEY (SensitivityId, TradeId));
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_Instrument (  
		InstrumentId  int  PRIMARY KEY,
		CUSIP  varchar,
		SEDOL  varchar,
		RIC  varchar,
		ISIN  varchar,
		IssuerId  int,
		Symbol  varchar,
		curve  varchar,
		tenor  varchar,
		currency  varchar );
		
CREATE INDEX Symbol on infocube.inforisk_Instrument(Symbol);
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_Issuer (  
		IssuerId int  PRIMARY KEY,
		IssuerLongName varchar,
		IssuerShortName varchar,
		IssuerRating int,
		IssuerSector int );
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_Rating ( 
		RatingId int  PRIMARY KEY,
		MoodyRating varchar,
		SPrating varchar,
		FitchRating varchar,
		InternalRating varchar );
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_Sector ( 
		SectorId int  PRIMARY KEY,
		Level1 varchar,
		Level2 varchar,
		Level3 varchar,
		Level4 varchar );
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_TimeSeries ( 
		TimeSeriesId int  PRIMARY KEY,
		Date Date,
		DataValue double );
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_TimeSeriesMapping ( 
		TimeSeriesId int  PRIMARY KEY,
		InstrumentId int,
		Symbol varchar,
		Type varchar,
		Curve varchar,
		Currency varchar,
		Tenor varchar,
		Mean double,
		StdDev double,
		Drift double );
		
CREATE TABLE IF NOT EXISTS infocube.inforisk_historic (
		InstrumentId int,
		Date Date,
		Symbol varchar,
		AdjustedClosingPrice double,
		PRIMARY KEY(InstrumentId, Date)		
);		

CREATE TABLE IF NOT EXISTS infocube.historical_prices (
		ticker ascii, 
		date timestamp, 
		open decimal, 
		high decimal, 
		low decimal, 
		close decimal, 
		volume bigint, 
		adj_close decimal, 
		PRIMARY KEY (ticker, date)
) WITH CLUSTERING ORDER BY (date DESC);

		
