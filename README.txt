12/31/2015
How to run:
1. Start Cassandra DataStax server (it may have been installed as Windows service with default port as 9042)
2. Run BulkLoad.java with a ticker like GS as argument - this will produce SSTable data files under the <cwd>/data/inforisk/inforisk_historical
3. Run C:\Program Files\DataStax Community\apache-cassandra\bin\SSTableLoader -v -d localhost <cwd>/data/inforisk/inforisk_historical to load 
   GS historical prices
4. Run VarExamples to calculate VaR for a single Trade of GS and single instrument of GS