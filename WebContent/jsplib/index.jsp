<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    

<%String customer =  request.getRemoteUser(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<BODY BGCOLOR="#FBFBEF">
<body>
<center>
<h2>Padmavathy Jewellers Welcomes You</h2>
</center>
<br>
<%--  <%response.setIntHeader("Refresh", 5); %>--%>
<%=new java.util.Date() %>
<br>
<p> Hi <%=customer %>, please wait for a moment while we transfer you to a new page
<br>
<CENTER><IMG SRC="<%=request.getContextPath()%>/imagelib/Hanuman.jpg" alt="Hanuman" name="Hanuman" width="1000" height="500" id="img12"></CENTER>
<br>
<br>
<FORM ACTION="/jewellery/WelcomePatron" METHOD=POST>

SSL Session ID: <%= request.getAttribute("javax.servlet.request.ssl_session") %>

<!-- Username: <INPUT type="text" name="userName" align="right">
<br>
<br>
Password:  <INPUT type="password" name="pwd" align="right">
<br>
<br>
<INPUT type="submit" name="submit" value="Enter" align="right">
</FORM>
<br>
<FORM ACTION="/jewellery/jsplib/register.jsp">
<INPUT type="submit" name="submit" value="Register" align="right">
 -->
<%
  String url1 = response.encodeURL("/WelcomeCustomer");
  String url2 = response.encodeURL("/jewellery/secure/login.jsp");
%>

<% if(customer!=null){
	request.getRequestDispatcher(url1).forward(request,response);
}else{
	response.sendRedirect(url2); 
}%>
</FORM>
</body>
</html>