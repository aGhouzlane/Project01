package com.revature.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	private static String url;
	
	private static String user;
	
	private static String password;
	
	private static ConnectionFactory cf;
	
	private static final String PROPERTIES_FILE = "src/main/resources/database.properties";
	
	public static Connection getConnection() {
		
		if (cf == null) {
			cf = new ConnectionFactory();
		}
		
		return cf.createConnection();
	}
	
	public static ResultSet getFirstRow(String sql) throws SQLException, IOException 
	{
		log.appendLog(sql);
		Connection connection = getConnection();
		ResultSet row = connection.createStatement().executeQuery(sql);
		
		if(row.next()) 
		{
			connection.close();
			return row;
		}
		connection.close();
		return null;	
	}
	
	public static void execute(String sql) throws SQLException, IOException 
	{
		log.appendLog(sql);
		Connection connection = getConnection();
		connection.createStatement().executeUpdate(sql);
		connection.close();
	}
	
	private ConnectionFactory() {
		
		Properties prop = new Properties();
		
		try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)){
			
			prop.load(fis);
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Connection createConnection() {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}

}
