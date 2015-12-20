create keyspace "infocube"
		with replication = 
		{ 'class' : 'SimpleStrategy',  'replication_factor' : 1};

CREATE TABLE infocube.inforisk_Portfolio (  
		PortfolioId int  PRIMARY KEY,
		PortfolioName varchar );
		
CREATE TABLE infocube.inforisk_Trade (  
		PortfolioId int  ,
		TradeId int,
		Quantity int,
		Notional float,
		NotionalUSD float,
		MarketValue float,
		MarketValueUSD float,
		Instrument int ,
		PRIMARY KEY (PortfolioId, TradeId));
		
CREATE TABLE infocube.inforisk_Sensitivity (  
		SensitivityId  int,
		TradeId int,
		SensitivityType  varchar,
		InstrumentId  int,
		SensitivityValue  float,
		PRIMARY KEY (SensitivityId, TradeId));
		
CREATE TABLE infocube.inforisk_Instrument (  
		InstrumentId  int  PRIMARY KEY,
		CUSIP  varchar,
		SEDOL  varchar,
		RIC  varchar,
		ISIN  varchar,
		Issuer  int,
		Symbol  varchar,
		curve  varchar,
		tenor  varchar,
		currency  varchar );
		
CREATE TABLE infocube.inforisk_Issuer (  
		IssuerId int  PRIMARY KEY,
		IssuerLongName varchar,
		IssuerShortName varchar,
		IssuerRating int,
		IssuerSector int );
		
CREATE TABLE infocube.inforisk_Rating ( 
		RatingId int  PRIMARY KEY,
		MoodyRating varchar,
		SPrating varchar,
		FitchRating varchar,
		InternalRating varchar );
		
CREATE TABLE infocube.inforisk_Sector ( 
		SectorId int  PRIMARY KEY,
		Level1 varchar,
		Level2 varchar,
		Level3 varchar,
		Level4 varchar );
		
CREATE TABLE infocube.inforisk_TimeSeries ( 
		TimeSeriesId int  PRIMARY KEY,
		Date Date,
		DataValue float );
		
CREATE TABLE infocube.inforisk_TimeSeriesMapping ( 
		TimeSeriesId int  PRIMARY KEY,
		InstrumentId int,
		Symbol varchar,
		Type varchar,
		Curve varchar,
		Currency varchar,
		Tenor varchar,
		Mean float,
		StdDev float,
		Drift float );
