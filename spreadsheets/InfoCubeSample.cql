INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (1,'AAPL');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (2,'GOOG');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (3,'CSCO');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (4,'MSFT');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (5,'TSLA');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (6,'GE');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (7,'AMZN');

--select * from infocube.inforisk_instrument;

INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,1,100,1);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,2,100,2);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,3,100,3);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,4,100,4);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,5,100,5);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,6,100,6);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,7,100,7);

--select * from infocube.inforisk_trade;


--select * from infocube.inforisk_portfolio;