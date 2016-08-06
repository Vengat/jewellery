package org.jewelry.createuser;


import java.io.*;
import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Date;
import java.text.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.*;
import org.jewelry.hashing.Owasp;
import org.jewelry.utilities.DBUtilities;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet(description = "This servlet will insert data into the db", urlPatterns = { "/CreateUser" })
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public java.util.Date formatDate(String s) throws Exception{
    	java.util.Date date;
    	DateFormat formatter ;
    	formatter = new SimpleDateFormat("dd/mm/yyyy");
    	date = (java.util.Date) formatter.parse(s);
    	System.out.println("Date of birth is "+date);
    	return date;    	
    }
    
    public boolean appropriateDOB(String dob) throws Exception{
    	return dob.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
    }
    
    public boolean verifyUserExists_Old(String login){
    	String driver = "org.postgresql.Driver";
    	boolean userExists = false;
		try{
			Class.forName(driver);			
		}catch(ClassNotFoundException e){
			System.out.println("JDBC Driver class not loaded");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("PostgreSQL JDBC Driver Registered!");
		
		
		String host = "localhost";
		String dbName = "new_demo";
		int port = 5432;
		String postgresURL = "jdbc:postgresql://"+host+":"+port+"/"+dbName;
		
		String usrName = "postgres";
		String pwd="postgres";
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		
		String userid =null;
		
		
		
		if(StringUtils.isEmpty(login)) return false;

		
		try{
			System.out.println("About to query");
			connection = DriverManager.getConnection(postgresURL,usrName,pwd);
			ps=connection.prepareStatement("SELECT userid, firstname, lastname FROM customers where userid=?");
			ps.setString(1, login);
			rs=ps.executeQuery();
			if(rs.next()){
				userid = rs.getString("userid");
				System.out.println("Verifying if user exists in the db "+userid);
				if(userid!=null){
					userExists= true;
				}else{
					userExists = false;
				}
			}
		}catch(SQLException e){
			System.out.println("Connection failed to the DB");
			e.printStackTrace();
			return false;
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("User exists "+userExists+" and the userid is "+userid);
	    return userExists;
    	
    }
    
    public boolean verifyUserExists(String login){
    	
    	boolean userExists = false;
    	/*String driver = "org.postgresql.Driver";
    	boolean userExists = false;
		try{
			Class.forName(driver);			
		}catch(ClassNotFoundException e){
			System.out.println("JDBC Driver class not loaded");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("PostgreSQL JDBC Driver Registered!");*/
		
		
		/*String host = "localhost";
		String dbName = "new_demo";
		int port = 5432;
		String postgresURL = "jdbc:postgresql://"+host+":"+port+"/"+dbName;
		
		String usrName = "postgres";
		String pwd="postgres";*/
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		
		String userid =null;
		
		
		
		if(StringUtils.isEmpty(login)) return false;

		
		try{
			System.out.println("About to query");
			//connection = DriverManager.getConnection(postgresURL,usrName,pwd);
			connection=DBUtilities.establishPooledConnectionToDB();
			ps=connection.prepareStatement("SELECT userid, firstname, lastname FROM customers where userid=?");
			ps.setString(1, login);
			rs=ps.executeQuery();
			if(rs.next()){
				userid = rs.getString("userid");
				System.out.println("Verifying if user exists in the db "+userid);
				if(userid!=null){
					userExists= true;
				}else{
					userExists = false;
				}
			}
		}catch(SQLException e){
			System.out.println("Connection failed to the DB");
			e.printStackTrace();
			return false;
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println("User exists "+userExists+" and the userid is "+userid);
	    return userExists;
    	
    }
    
    
    public boolean createUser(String login, String password, String email, String firstname, String lastname, Date dob) throws SQLException, NoSuchAlgorithmException{
    	
    	/*String driver = "org.postgresql.Driver";
		try{
			Class.forName(driver);			
		}catch(ClassNotFoundException e){
			System.out.println("JDBC Driver class not loaded");
			e.printStackTrace();
			return false;
		}
		
		System.out.println("PostgreSQL JDBC Driver Registered!");
		
		
		String host = "localhost";
		String dbName = "new_demo";
		int port = 5432;
		String postgresURL = "jdbc:postgresql://"+host+":"+port+"/"+dbName;
		
		String usrName = "postgres";
		String pwd="postgres";*/
		
		Connection connection = null;
		connection=DBUtilities.establishPooledConnectionToDB();
		
		/*try{
			
			connection = DriverManager.getConnection(postgresURL,usrName,pwd);
			
			
		}catch(SQLException e){
			System.out.println("Connection failed to the DB");
			e.printStackTrace();
			return false;
		}*/
		
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
    	Owasp ow = new Owasp();
    	return ow.createUser(connection, login, password, email, firstname, lastname, dob);
    }

    
    
    
    public boolean createUser_Old(String login, String password, String email, String firstname, String lastname, Date dob) throws SQLException, NoSuchAlgorithmException{
    	
    	String driver = "org.postgresql.Driver";
		try{
			Class.forName(driver);			
		}catch(ClassNotFoundException e){
			System.out.println("JDBC Driver class not loaded");
			e.printStackTrace();
			return false;
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
			//connection=DBUtilities.establishPooledConnectionToDB();
			
		}catch(SQLException e){
			System.out.println("Connection failed to the DB");
			e.printStackTrace();
			return false;
		}
		
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
    	Owasp ow = new Owasp();
    	return ow.createUser(connection, login, password, email, firstname, lastname, dob);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> addressDets = new ArrayList<String>();
		
		
		HttpSession session = request.getSession();
		boolean missingValue=false;
		
		boolean missingnewUserName=false;
		boolean missingPwd=false;
		boolean missingfirstName=false;
		boolean missinglastName=false;
		boolean missingdateofBirth=false;
		boolean missingemail=false;
		boolean missinghouseNo=false;
		boolean missingstreetName=false;
		boolean missingareaName=false;
		boolean missingcity=false;
		boolean missingstate=false;
		boolean missingpincode=false;
		boolean missingcountry=false;
		
		
		
		
		
		String newUserName = (String) session.getAttribute("userId");
		if(newUserName==null||newUserName.equalsIgnoreCase(request.getParameter("newUserName"))==false){
			newUserName = request.getParameter("newUserName");
			if(StringUtils.isEmpty(newUserName)){
				newUserName = "Missing User Name";
				missingValue=true;
				missingnewUserName=true;
				System.out.println("Missing User Name");
			}
		}
		
		
		String pwd = request.getParameter("pwd");
		if(StringUtils.isEmpty(pwd)){
			pwd="Missing password";
			missingValue = true;
			missingPwd = true;
			System.out.println("Missing password");
		}
		
		String firstName = request.getParameter("firstName");
		if(StringUtils.isEmpty(firstName)){
			firstName="First name not entered";
			missingValue = true;
			missingfirstName=true;
			System.out.println("First name not entered");
		}
		
		String lastName = request.getParameter("lastName");
		if(StringUtils.isEmpty(lastName)){
			lastName = "Second/Last name not entered";
			missingValue=true;
			missinglastName=true;
			System.out.println("Second/Last name not entered");
		}
		
		String dateofBirth = request.getParameter("dob");
		if(StringUtils.isEmpty(dateofBirth)){
			dateofBirth = "Date of birth not entered";
			missingValue=true;
			missingdateofBirth=true;
			System.out.println("Date of birth not entered");
		}
		
		String email = request.getParameter("email");
		if(StringUtils.isEmpty(email)){
			email = "email empty";
			missingValue = true;
			missingemail=true;
			System.out.println("email empty");
		}
		
		String houseNo = request.getParameter("houseNo");
		if(StringUtils.isEmpty(houseNo)){
			houseNo = "house name and no missing";
			missingValue = true;
			missinghouseNo=true;
			System.out.println("house name and no missing");
		}
		addressDets.add(houseNo);

		String streetName = request.getParameter("streetName");
		if(StringUtils.isEmpty(streetName)){
			streetName = "street name and no missing";
			missingValue = true;
			missingstreetName=true;
			System.out.println("street name and no missing");
		}
		addressDets.add(streetName);

		String areaName = request.getParameter("areaName");
		if(StringUtils.isEmpty(areaName)){
			areaName = "Area name missing";
			missingValue = true;
			missingareaName = true;
			System.out.println("Area name missing");
		}
		addressDets.add(areaName);

		String city = request.getParameter("city");
		if(StringUtils.isEmpty(city)){
			city = "city missing";
			missingValue = true;
			missingcity = true;
			System.out.println("city missing");
		}
		addressDets.add(city);

		String state = request.getParameter("state");
		if(StringUtils.isEmpty(state)){
			state = "state missing";
			missingValue = true;
			missingstate=true;
			System.out.println("state missing");
		}
		addressDets.add(state);

		String pincode = request.getParameter("pincode");
		if(StringUtils.isEmpty(pincode)){
			pincode = "pincode missing";
			missingValue = true;
			missingpincode=true;
			System.out.println("pincode missing");
		}
		addressDets.add(pincode);

		String country = request.getParameter("country");
		if(StringUtils.isEmpty(country)){
			country = "country name missing";
			missingValue = true;
			missingcountry=true;
			System.out.println("country name missing");
		}
		addressDets.add(country);

		Cookie cnewUserNameCrUser = new Cookie("newUserName",newUserName);
		//cnewUserNameCrUser.setMaxAge(60*60*24*7);//One week 
		response.addCookie(cnewUserNameCrUser);
		
		Cookie cpwd = new Cookie("pwd","Re-enter the password");
		//cpwd.setMaxAge(60*60*24*7);
		response.addCookie(cpwd);
		
		Cookie cfirstName = new Cookie("firstName",firstName);
		//cfirstName.setMaxAge(60*60*24*7);
		response.addCookie(cfirstName);
		
		Cookie clastName = new Cookie("lastName",lastName);
		//clastName.setMaxAge(60*60*24*7);
		response.addCookie(clastName);
		
		Cookie cdateofBirth = new Cookie("dateofBirth",dateofBirth);
		//cdateofBirth.setMaxAge(60*60*24*7);
		response.addCookie(cdateofBirth);
		
		Cookie cEmail = new Cookie("email",email);
		//cEmail.setMaxAge(60*60*24*7);
		response.addCookie(cEmail);
	

		Cookie  chouseNo= new Cookie("houseNo",houseNo);
		response.addCookie(chouseNo);

		Cookie  cstreetName= new Cookie("streetName",streetName);
		response.addCookie(cstreetName);

		Cookie  careaName= new Cookie("areaName",areaName);
		response.addCookie(careaName);

		Cookie ccity = new Cookie("city",city);
		response.addCookie(ccity);

		Cookie  cstate= new Cookie("state",state);
		response.addCookie(cstate);

		Cookie cpincode = new Cookie("pincode",pincode);
		response.addCookie(cpincode);

		Cookie  ccountry= new Cookie("country",country);
		response.addCookie(ccountry);

		java.util.Date dob1 =null;
		java.sql.Date dob = null;
		System.out.println("Date of birth is "+dateofBirth);
		try {
			 dob1 =  formatDate(dateofBirth);
			 System.out.println("Date of birth is "+dob1);
			 dob = new java.sql.Date(dob1.getTime());
			 System.out.println("Date of birth is "+dob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception occured while formatting dob");
			e.printStackTrace();
		}
		
		
		
		
		/*if(missingValue) {
			//out.println("<p>Please provide all the info...You are being redirected to the register page"+
			//           "</CENTER></BODY></HTML>");
			session.setAttribute("missingValue",newUserName );
			response.sendRedirect("/jewellery/jsplib/register.jsp");
			return;
		}*/
		
		if(missingnewUserName || missingPwd || missingfirstName || missinglastName || missingdateofBirth || missingemail || missinghouseNo || missingstreetName || missingareaName || missingcity || missingstate || missingpincode || missingcountry){
			session.setAttribute("missingnewUserName",newUserName );
			session.setAttribute("missingPwd","");
			session.setAttribute("missingfirstName",firstName);
			session.setAttribute("missinglastName",lastName);
			session.setAttribute("missingdateofBirth",dateofBirth);
			session.setAttribute("missingemail",email);
			session.setAttribute("missinghouseNo",houseNo);
			session.setAttribute("missingstreetName",streetName);
			session.setAttribute("missingareaName",areaName);
			session.setAttribute("missingcity",city);
			session.setAttribute("missingstate",state);
			session.setAttribute("missingpincode",pincode);
			session.setAttribute("missingcountry",country);
			
			System.out.println("Some of the fields are missing");
			response.sendRedirect("/jewellery/jsplib/register.jsp");
			return;
			
		}
	   
		
		
		if(!verifyUserExists(newUserName)){
			
			try {
				if(createUser(newUserName, pwd, email,  firstName,  lastName,  dob)){
					System.out.println("User creation success");
					userCreationSuccess( request,  response,  firstName,  newUserName);
				}else{
					/*out.println("<p><br><br><br><H2>Hi "+firstName+"! Your account could not be created at this moment\n"+
							"<br>Please try again...");
					out.println("</CENTER></BODY></HTML>");*/
					session.setAttribute("errorWhileRegistering1", newUserName);
					response.sendRedirect("/jewellery/jsplib/register.jsp");
				}
			} catch (NoSuchAlgorithmException e) {
				/*out.println("<p><br><br><br><H2>Hi "+firstName+"! Your account could not be created at this moment\n"+
				"<br>Please try again...");
				out.println("</CENTER></BODY></HTML>");*/
				session.setAttribute("errorWhileRegistering2", newUserName);
				response.sendRedirect("/jewellery/jsplib/register.jsp");

				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				/*out.println("<p><br><br><br><H2>Hi "+firstName+"! Your account could not be created at this moment\n"+
				"<br>Please try again...");
				out.println("</CENTER></BODY></HTML>");*/
				session.setAttribute("errorWhileRegistering3", newUserName);
				response.sendRedirect("/jewellery/jsplib/register.jsp");

				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}else{
			//userExists( request,  response,  firstName,  newUserName);
			session.setAttribute("userExists", newUserName);
			response.sendRedirect("/jewellery/jsplib/register.jsp");
			return;
		}
		
	}
	
    String docType =
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
		"Transitional//EN\"\n";
	String title = "";

	
	private void userCreationSuccess(HttpServletRequest request, HttpServletResponse response, String firstName, String newUserName) throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		title = "Adding New User";

		
		out.println(docType +
				"<HTML>\n" +
				"<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
				"<BODY BGCOLOR=\"#FDF5E6\"><CENTER>\n" +
				"<H1>Adding New User</H1>\n");
		out.println("<p><br><br><br><H2>Hi "+firstName+"! Your account has been successfully created \n"+
				"<br> Please login with your UserId "+newUserName);		
		out.println("<p><a href=\"/jewellery/jsplib/index.jsp\">Take me to the login page</a>");
		out.println("</CENTER></BODY></HTML>");
		request.getSession(true).invalidate();
	}
	
	private void userExists(HttpServletRequest request, HttpServletResponse response, String firstName, String newUserName) throws ServletException, IOException{
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
	    title = "The UserId you are tryig to register is already been used by another user";
		out.println("<p><br><br><br><H2>Hi "+firstName+"! Your account could not be created as the user id already is used by someone else\n"+
		"<br>Please try with a different user id");	
		
		out.println("</CENTER></BODY></HTML>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		// TODO Auto-generated method stub
	}

}
