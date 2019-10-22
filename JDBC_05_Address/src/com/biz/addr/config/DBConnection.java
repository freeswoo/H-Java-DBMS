package com.biz.addr.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static Connection dbConn = null;
	
	static {
		
		String jdbcDriver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "user4";
		String password = "user4";
		
		try {
			Class.forName(jdbcDriver);
			dbConn = DriverManager.getConnection(url, user, password);
			System.out.println("DB 연결됨!!");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Connection getDBConnection() {
		return dbConn;
	}

}
