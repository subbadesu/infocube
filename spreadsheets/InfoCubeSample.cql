INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (1,'AAPL');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (2,'GOOG');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (3,'CSCO');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (4,'MSFT');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (5,'TSLA');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (6,'GE');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (7,'AMZN');
INSERT INTO INFOCUBE.INFORISK_Instrument (InstrumentId , Symbol ) VALUES (8,'GS');

select * from infocube.inforisk_instrument;

INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,1,100,1);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,2,150,2);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,3,200,3);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,4,250,4);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,5,225,5);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,6,175,6);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,7,125,7);
INSERT INTO INFOCUBE.INFORISK_Trade (PortfolioId, TradeId,Quantity,InstrumentId) VALUES (1,8,75,8);

select * from infocube.inforisk_trade;


select * from infocube.inforisk_portfolio where portfolioid = 1;
select * from infocube.inforisk_trade where portfolioid = 1;

select count(*) from infocube.inforisk_historical;
select * from infocube.inforisk_historical;
select * from infocube.inforisk_normal_parameters;

insert into infocube.inforisk_normal_parameters (ticker,date,Mean,STDDEV) values('MSFT','2016-01-07',0.000146095247639801,0.00806051118653742);
insert into infocube.inforisk_normal_parameters (ticker,date,Mean,STDDEV) values('AAPL','2016-01-07',0.000369630402409772,0.00899188076969213);
insert into infocube.inforisk_normal_parameters (ticker,date,Mean,STDDEV) values('AMZN','2016-01-07',0.000446178629431361,0.0111252285737654);
insert into infocube.inforisk_normal_parameters (ticker,date,Mean,STDDEV) values('CSCO','2016-01-07',3.05467492930247E-05,0.00861936828452307);
insert into infocube.inforisk_normal_parameters (ticker,date,Mean,STDDEV) values('GE','2016-01-07',1.82242766715655E-05,0.00933772856576504);
insert into infocube.inforisk_normal_parameters (ticker,date,Mean,STDDEV) values('GOOG','2016-01-07',0.00023670337297093,0.00703237682399876);
insert into infocube.inforisk_normal_parameters (ticker,date,Mean,STDDEV) values('GS','2016-01-07',-2.18618420832057E-05,0.0115433967322318);
insert into infocube.inforisk_normal_parameters (ticker,date,mean,stddev) values('TSLA','2016-01-07',0.000678670233535017,0.0149120565843832);



--delete from infocube.inforisk_historical;