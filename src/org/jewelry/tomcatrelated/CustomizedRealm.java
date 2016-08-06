package org.jewelry.tomcatrelated;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.catalina.realm.DataSourceRealm;
import org.apache.catalina.realm.GenericPrincipal;
import org.jewelry.utilities.DBUtilities;
import org.jewelry.hashing.Owasp;


public class CustomizedRealm extends DataSourceRealm{
	
/*	private String pDbUsername=null;
	private static final Connection conn = null;
	private static final Owasp owasp = null;
	
	
	public String getDbUsername() {
	      return this.pDbUsername;
	}
	
	public void setDbUsername(String dbUsername) {
	      this.pDbUsername = dbUsername;
	}
	
	static Connection getDataSourceConnection(){
		return DBUtilities.establishPooledConnectionToDB();
	}*/
	
	static Owasp getOwaspInstance(){
		return new Owasp();
	}
	
	
	@Override
	protected Principal authenticate(Connection dbConnection,String username, String credentials){
		
		String dbCredentials = getPassword(dbConnection, username);
		
        // Validate the user's credentials
        boolean validated = false;

		
		//conn = CustomizedRealm.getDataSourceConnection();
		
		try{
			validated=CustomizedRealm.getOwaspInstance().authenticateUser(username,credentials,dbConnection);
		}catch(SQLException sqe){
			sqe.printStackTrace();
		}catch(NoSuchAlgorithmException nsae){
			nsae.printStackTrace();
		}
		
	       if (!validated) {
	    	   return (null);
	        } 

	        ArrayList<String> list = getRoles(dbConnection, username);

	        // Create and return a suitable Principal for this user
	        return (new GenericPrincipal(username, credentials, list));

	    }

	

}
