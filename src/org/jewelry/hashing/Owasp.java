package org.jewelry.hashing;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Arrays;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.apache.commons.codec.binary.Base64;

import grandcentral.auth.tokenAuth;


//Owasp stands for one way authentication password salt

public class Owasp {
	
	private static final int ITERATION_NUMBER = 1000;
	
	
	
	
	public Owasp(){
		
	}
	
	 /**
	    * Inserts a new user in the database
	    * @param con Connection An open connection to a databse
	    * @param login String The login of the user
	    * @param password String The password of the user
	    * @return boolean Returns true if the login and password are ok (not null and length(login)<=100
	    * @throws SQLException If the database is unavailable
	    * @throws NoSuchAlgorithmException If the algorithm SHA-1 or the SecureRandom is not supported by the JVM
	    */
	
	 public boolean createUser(Connection con, String login, String password, String email, String firstname, String lastname, Date dob) throws SQLException, NoSuchAlgorithmException{
		 PreparedStatement ps =null;
		 PreparedStatement ps1 =null;
		 PreparedStatement ps2 =null;
		 ResultSet rs = null;
		 
		 
		 try{
			 if (login!=null&&password!=null&&login.length()<=100){
			 // Uses a secure Random not a simple Random
             SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // Salt generation 64 bits long
             byte[] bSalt = new byte[8];
             random.nextBytes(bSalt);
             // Digest computation
             byte[] bDigest = null;
			try {
				bDigest = getHash(ITERATION_NUMBER,password,bSalt);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             String sDigest = null;
			try {
				sDigest = byteToBase64(bDigest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             String sSalt = null;
			try {
				sSalt = byteToBase64(bSalt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
             ps=con.prepareStatement("INSERT INTO CUSTOMERS (userid,password,email,firstname,lastname,dob,salt) values (?,?,?,?,?,?,?)");
             ps.setString(1, login);
             ps.setString(2, sDigest);
             ps.setString(3, email);
             ps.setString(4, firstname);
             ps.setString(5, lastname);
             ps.setDate(6, dob);
             ps.setString(7, sSalt);
             ps.executeUpdate();
             
             ps1 = con.prepareStatement("SELECT ID FROM CUSTOMERS WHERE USERID=?");
             ps1.setString(1, login);
             rs=ps1.executeQuery();
             int id = 0;
             if(rs.next()){
            	 id = rs.getInt("ID");           	 
             }
             
             
             ps2=con.prepareStatement("INSERT INTO USER_ROLE (id,userid,role) values (?,?,?) ");
             ps2.setInt(1, id);
             ps2.setString(2, login);
             ps2.setString(3, "customer");
             ps2.executeUpdate();
             
             return true;
			 }else{
				 return false;
			 }
		 }finally{
			 close(ps);
			 con.close();
		 }
	 }
     

	
	/**
	    * Authenticates the user with a given login and password
	    * If password and/or login is null then always returns false.
	    * If the user does not exist in the database returns false.
	    * @param con Connection An open connection to a databse
	    * @param login String The login of the user
	    * @param password String The password of the user
	    * @return boolean Returns true if the user is authenticated, false otherwise
	    * @throws SQLException If the database is inconsistent or unavailable (
	    *           (Two users with the same login, salt or digested password altered etc.)
	    * @throws NoSuchAlgorithmException If the algorithm SHA-1 is not supported by the JVM
	    */
	
	
	public boolean authenticateUser(String login, String password, Connection con) throws SQLException, NoSuchAlgorithmException{
		
		boolean authenticated = false;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try{
			boolean userExist = true;
			//INPUT VALIDATION
			if(login==null||password==null){
				userExist = false;
				login ="";
				password="";
			}
			
			ps = con.prepareStatement("SELECT PASSWORD, SALT FROM CUSTOMERS WHERE USERID=?");
			ps.setString(1, login);
			rs=ps.executeQuery();
			String digest, salt;
			if(rs.next()){
				digest = rs.getString("PASSWORD");
				salt = rs.getString("SALT");
				
				//DATABASE VALIDATION
				if(digest == null || salt == null){
					throw new SQLException("Database inconsistant Salt or Digested Password altered");
				}
				
				
				//Should not append, because login is the primary key
				if(rs.next()){
					throw new SQLException("Database inconsistent two CREDENTIALS with the same LOGIN");
				}
			}else{// TIME RESISTANT ATTACK (Even if the user does not exist the
	               // Computation time is equal to the time needed for a legitimate user
	               digest = "000000000000000000000000000=";
	               salt = "00000000000=";
	               userExist = false;
				
			}
			
			byte[] bDigest = null;
			try {
				bDigest = base64ToByte(digest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] bSalt = null;
			try {
				bSalt = base64ToByte(salt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//COMPUTE THE NEW DIGEST
			byte[] proposedDigest = getHash(ITERATION_NUMBER, password, bSalt);
			
			return Arrays.equals(proposedDigest, bDigest) && userExist;
			
			
		}catch(IOException ex){
			throw new SQLException("Database inconsistent Salt or Digested Password altered");
		}
		finally{
			close(rs);
			close(ps);
			con.close();
		}
		
	}
	
	public void close(ResultSet rs){
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void close(Statement ps){
		if(ps!=null)
			try{
				ps.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}
	
	  /**
	    * From a base 64 representation, returns the corresponding byte[] 
	    * @param data String The base64 representation
	    * @return byte[]
	    * @throws IOException
	    * The commented code is the Base64 implementation of Sun's Base64
	    * We are using apache commons codec
	    */
	
	public static byte[] base64ToByte(String data) throws Exception{
		//BASE64Decoder decoder = new BASE64Decoder();
		//return decoder.decodeBuffer(data);
		Base64 decoder = new Base64();
		return decoder.decode(data.getBytes());
		
	}
	
	/**
	    * From a byte[] returns a base 64 representation
	    * @param data byte[]
	    * @return String
	    * @throws IOException
	    */
	
	public static String byteToBase64(byte[] data) throws Exception{
		Base64 encoder = new Base64();
		return new String(encoder.encode(data));
	}
	
	/**
	    * From a password, a number of iterations and a salt,
	    * returns the corresponding digest
	    * @param iterationNb int The number of iterations of the algorithm
	    * @param password String The password to encrypt
	    * @param salt byte[] The salt
	    * @return byte[] The digested password
	    * @throws NoSuchAlgorithmException If the algorithm doesn't exist
	 * @throws UnsupportedEncodingException 
	    */
	
	public byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
      
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < iterationNb; i++) {
	           digest.reset();
	           input = digest.digest(input);
	       }
		return input;
	}
	
	public static void main(String[] args){
		String driver = "org.postgresql.Driver";
		try{
			Class.forName(driver);			
		}catch(ClassNotFoundException e){
			System.out.println("JDBC Driver class not loaded");
			e.printStackTrace();
			return;
		}
		
		System.out.println("PostgreSQL JDBC Driver Registered!");
		
		
		String host = "localhost";
		String dbName = "new_demo";
		int port = 5432;
		String postgresURL = "jdbc:postgresql://"+host+":"+port+"/"+dbName;
		
		String usrName = "postgres";
		String pwd="postgres";
		
		Connection connection = null;
		
		try{
			
			connection = DriverManager.getConnection(postgresURL,usrName,pwd);
			
		}catch(SQLException e){
			System.out.println("Connection failed to the DB");
			e.printStackTrace();
			return;
		}
		
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		
		
		
		
	}
	
	

}
