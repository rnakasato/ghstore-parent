package com.nakasato.ghstore.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefaultConnection {
	
	public static Connection getConnection() 
			throws ClassNotFoundException, 
		SQLException{
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://localhost:5432/ghstore";
		String user = "postgres";
		String password = "rnakasato";
		Class.forName( driver );
		Connection conn = 
				DriverManager.getConnection( url, user, password);

		return conn;
	}
	
	public static void releaseResources(Connection conn, PreparedStatement pst, ResultSet rs) throws SQLException{
		if(rs != null){
			rs.close();
		}
		if(pst != null){
			pst.close();
		}
		if(conn != null){
			conn.close();
		}
	}

}
