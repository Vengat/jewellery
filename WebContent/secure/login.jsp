<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Padmavathy Jewellers Login Page</title>
</head>
<center>
<h2>Padmavathy Jewellers Welcomes you</h2>
</center>
<br>
<%-- <%response.setIntHeader("Refresh", 60); %> --%>
<%=new Date() %>
<body>
<br>
<FORM METHOD=POST ACTION="j_security_check">
<p>
<font size="2"> <strong> Enter user ID and password: </strong></font>
<BR>
<strong> User ID</strong> <input type="text" size="20" name="j_username">
<strong> Password </strong>  <input type="password" size="20" name="j_password">
<BR>
<BR>
<font size="2">  <strong> And then click this button: </strong></font>
<input type="submit" name="login" value="Login">
</p>
</FORM>
<FORM ACTION="/jewellery/secure/register.jsp">
	<INPUT type="submit" name="submit" value="Register" align="right">
</FORM>


</body>
</html>