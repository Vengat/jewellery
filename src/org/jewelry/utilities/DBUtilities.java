package org.jewelry.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import org.postgresql.jdbc2.optional.ConnectionPool;


public class DBUtilities {
	
	private static final String driver = "org.postgresql.Driver";
	private static final String host = "localhost";
	private static final String dbName = "new_demo";
	private static final int port = 5432;
	private static final String postgresURL = "jdbc:postgresql://"+host+":"+port+"/"+dbName;
	
	private static final String usrName = "postgres";
	private static final String pwd="postgres";

	 public static Connection establishSimpleConnectionToDB(){
		
		try{
			Class.forName(driver);			
		}catch(ClassNotFoundException cne){
			System.out.println("JDBC Driver class not loaded");
			cne.printStackTrace();
		}
		
		Connection connection = null;
		try{
			connection = DriverManager.getConnection(postgresURL,usrName,pwd);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return connection;
				
	}
	 
	public static Connection establishPooledConnectionToDB(){
		
		String foo = "Not connected";
		Connection conn = null;
		try{
			Context ctx = new InitialContext();
			if(ctx==null) throw new Exception("Uh...Oh...No Context");

			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/postgres");
			
			if(ds!=null){
				 conn = ds.getConnection();
				
				if(conn!=null){
					
					foo = "Connected "+conn.toString();
					System.out.println("Connection success from establishPooledConnectionToDB foo--->"+foo);
					
				}

			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return conn;
		
	}

}
