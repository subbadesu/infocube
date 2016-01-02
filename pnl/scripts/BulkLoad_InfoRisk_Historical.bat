@echo off
if NOT DEFINED CASSANDRA_HOME set CASSANDRA_HOME="C:\Program Files\DataStax Community\apache-cassandra\bin"

rem First produce sstable using the BulkLoad program in the directory C:\Users\Partha\Projects\GitHub\infocube\pnl\data\infocube\inforisk_historical
rem Then run this program to connect to the cluster at localhost:9042 and load the 
%CASSANDRA_HOME%\sstableloader -v -d localhost C:\Users\Partha\Projects\GitHub\infocube\pnl\data\infocube\inforisk_historical