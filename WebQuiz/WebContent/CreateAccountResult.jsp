<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@
page import="objects.Account"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/ico.jpg" /> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
boolean success = (Boolean) request.getAttribute("success");
if(!success) {
%>
<title>Creation Unsuccessful</title>
</head>
<body>
<h1>Account creation was unsuccessful.</h1>
<p><a href="CreateAccount.html">Return to Account Creation Page.</a></p>
<p><a href="Login.html">Login Page</a></p>
</body>
<%
} else {
	objects.Account newAccount = (objects.Account) request.getAttribute("newAccount");
%>

<title>Account Created!</title>
</head>
<body>
<h1>Welcome <%=newAccount.getDisplayName()%>! Your account was created successfully.</h1>
<p><a href="CreateAccount.html">Return to Account Creation Page.</a></p>
<p><a href="Login.html">Login Page</a></p>
</body>

<%
}
%>
</html>