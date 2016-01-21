package com.infocube.risk.utils;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.infocube.risk.db.DbConnection;
import com.infocube.risk.utils.Pair;

public class PriceUtils {

    public static Pair<LocalDate, Double> getLatestHistoricPrice(DbConnection connection, String symbol) {
        Session session = connection.getSession();
        Select selectStmt = QueryBuilder.select().column("date").column("adj_close")
                .from(Constants.DBSCHEMA_NAME, Constants.HISTORICAL_TABLE_NAME).where(QueryBuilder.eq("ticker", symbol))
                .limit(1);
        ResultSet resultSet = session.execute(selectStmt);
        Row row = resultSet.one();
        if (row != null) {
            return new Pair<>(row.getDate("date"), row.getDouble("adj_close"));
        }
        return null;
    }

}
