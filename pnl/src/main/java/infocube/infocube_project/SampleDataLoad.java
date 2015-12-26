package infocube.infocube_project;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class SampleDataLoad {
	
	
	
	public static void main (String argv[])
	{
		
		Cluster cluster;
		Session session;
		cluster = Cluster.builder().addContactPoint("localhost").build();
		session = cluster.connect("infocube");
		ResultSet results =  session.execute("select * from infocube.inforisk_trade");
		for (Row row : results) {
			System.out.println( row.getInt("instrument"));
			}
		cluster.close();
	}

}
