<%@page import="org.jewelry.cookieutilities.CookieUtilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"  %>
<jsp:useBean id="registrationInfo" class="org.jewelry.createuser.UserRegistrationDetails" scope="session"/>
<jsp:setProperty name="registrationInfo" property="*"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New user registration</title>

<script>
 function validateForm(){
	 if(document.frm.newUserName.value==""|| document.frm.newUserName.value=="New user name" || document.frm.newUserName.value=="Missing User Name")
	  {
	      alert("User Name left blank");
	      document.frm.newUserName.focus();
	      return false;
	    }
	    else if(document.frm.pwd.value==""||document.frm.pwd.value=="Missing password"){
	    	alert("Password left blank");
	    	document.frm.pwd.focus();
	    	return false;
	    	
	    }else if(document.frm.firstName.value==""||document.frm.firstName.value=="First name not entered"||document.frm.firstName.value=="Enter firstname"){
	    	alert("Fist name is blank");
	    	document.frm.firstName.focus();
	    	return false;
	    }else if(document.frm.lastName.value==""||document.frm.lastName.value=="Enter secondname"||document.frm.lastName.value=="Second name not entered"){
	    	alert("Second name is blank");
	    	document.frm.secondName.focus();
	    	return false;
	    }else if(document.frm.dob.value==""||document.frm.dob.value=="Date of birth not entered"||document.frm.dob.value=="Enter dd/mm/yyyy"){
	    	alert("Date is blank");
	    	document.frm.Date.focus();
	    	return false;	
	    }else if(document.frm.email.value==""||document.frm.email.value=="someone@domain.com"||document.frm.dob.value=="email empty"){
	    	alert("Email is blank");
	    	document.frm.Date.focus();
	    	return false;
	    }else if(document.frm.pincode.value==""||document.frm.pincode.value=="pincode missing"||document.frm.pincode.value==""){
	    	alert("Pincode is blank");
	    	document.frm.pincode.focus();
	    	return false;
	    }else{
	    	return true;
	    }

 }
</script>
</head>
<BODY BGCOLOR="#FDF5E6">
<body>



<%String errorWhileRegistering1=(String) session.getAttribute("errorWhileRegistering1");
  String errorWhileRegistering2=(String) session.getAttribute("errorWhileRegistering2");
  String errorWhileRegistering3=(String) session.getAttribute("errorWhileRegistering3");
  String userExists=(String) session.getAttribute("userExists");
  String missingValue = (String) session.getAttribute("missingValue");
%>

<%!
   public boolean errorWhileRegisteringNewUser(String errorWhileRegistering, String newUserName){
	boolean error = false;
	if(errorWhileRegistering!=null && errorWhileRegistering.equalsIgnoreCase(newUserName)) {
		error =true;
	}
	return error;
}

  public String displayUserCreationErrorMessage(){
	 String errorMessage =  "<br><font color=\"red\"></font>*Error occured while creating userid, please try again!!!";	  
	 return errorMessage;
  }
	%>


<br>
<!-- <CENTER><IMG SRC="<%=request.getContextPath()%>/imagelib/Harini_2012.JPG" alt="Harini" name="Harini" width="1000" height="500" id="img14"></CENTER> -->
<br>
<%
  String newUserName = "userid";
  String password="";
  String firstname =	"firstname";
  String lastname = "secondname";
  String dob =  "dd/mm/yyyy"; 
  String email = "someone@domain.com";
  String houseNo = "";
  String streetName = "";
  String areaName = "";
  String city = "";
  String state = "";
  String pincode = "";
  String country = "";
	  
  Cookie[] cookies = request.getCookies();
  if(cookies!=null){
	   newUserName = CookieUtilities.getCookieValue(request,"newUserName","");
	   password = CookieUtilities.getCookieValue(request,"pwd","");
	   firstname = CookieUtilities.getCookieValue(request,"firstName","");
	   lastname = CookieUtilities.getCookieValue(request,"lastName","");
	   dob = CookieUtilities.getCookieValue(request,"dateofBirth","");
	   email = CookieUtilities.getCookieValue(request,"email","");	   
	   houseNo = CookieUtilities.getCookieValue(request,"houseNo","");
	   streetName = CookieUtilities.getCookieValue(request,"streetName","");
	   areaName = CookieUtilities.getCookieValue(request,"areaName","");
	   city = CookieUtilities.getCookieValue(request,"city","");
	   state = CookieUtilities.getCookieValue(request,"state","");
	   pincode = CookieUtilities.getCookieValue(request,"pincode","");
	   country = CookieUtilities.getCookieValue(request,"country","");
	   
  }  
  boolean redirected = false;
  if(newUserName=="Missing User Name"||password=="Missing password"||firstname=="First name not entered"||lastname=="Second name not entered"||dob=="Date of birth not entered"||email=="email empty"||pincode=="pincode missing"){
	  redirected = true;
  }
%>
<CENTER>
<FORM NAME="frm" METHOD=POST ACTION="/jewellery/CreateUser"  onsubmit="return validateForm()">
<%if(redirected == false){ %>
	<P>Lets start the registration process :-)
<%}else{ %>
    <P>Please provide the required details to complete the registration process
<%} %>	
<br>

<%if(errorWhileRegisteringNewUser(errorWhileRegistering1, newUserName)){ 
	displayUserCreationErrorMessage();
 }else if(errorWhileRegisteringNewUser(errorWhileRegistering2, newUserName)){
	 displayUserCreationErrorMessage();
 }else if(errorWhileRegisteringNewUser(errorWhileRegistering3, newUserName)){
	 displayUserCreationErrorMessage();
 }else if(errorWhileRegisteringNewUser(userExists, newUserName)){
	 displayUserCreationErrorMessage();%>
<br> The userId <%=newUserName%> already exists. Please try another user id
 <%} %>
 
 	 
 

 
<br>
Username: <INPUT TYPE="text" name="newUserName" value=<%=newUserName%>>*
<br>
Password:  <INPUT type="password" name="pwd" align="right">*
<br>
Firstname:<INPUT TYPE="text" name="firstName" value=<%=firstname%>>
<br>
Lastname:<INPUT TYPE="text" name="lastName" value=<%=lastname%>>
<br>
Enter dob:<INPUT TYPE="text" name="dob" value=<%=dob%>>
<br>
Enter email:<INPUT TYPE="text" name="email" value=<%=email%>>
<br>

<FIELDSET>
<LEGEND>Enter Address:</LEGEND>
<%if(missingValue!=null && missingValue.equalsIgnoreCase(newUserName)){%>
	 <br><font color="red">*Please make sure you have filled all the address details</font>	 
 <%} %>
<br>
House:<INPUT TYPE="text" name="houseNo">
<br>
Street:<INPUT TYPE="text" name="streetName">
<br>
Area:<INPUT TYPE="text" name="areaName">
<br>
City:<INPUT TYPE="text" name="city">
<br>
State:<INPUT TYPE="text" name="state">
<br>
Pincode:<INPUT TYPE="text" name="pincode">
<br>
Country:<INPUT TYPE="text" name="country">
</FIELDSET>
<br>
<INPUT TYPE="submit" NAME="SUBMIT" VALUE="ADD MY ACCOUNT">
<br>
<a href="/jewellery/jsplib/index.jsp">No I have my credentials, Redirect me to the sign in page</a>
</FORM>
</CENTER>
</body>
</html>