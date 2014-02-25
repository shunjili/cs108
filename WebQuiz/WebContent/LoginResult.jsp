<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@
page import objects.Account;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
objects.Account loggedAccount = (objects.Account) request.getAttribute("loggedAccount");
if(loggedAccount == null) {
%>

<title>Login failed</title>
</head>
<body>
	<h1>Login failed</h1>
	<h3><a href = "Login.html">Return to Login Page</a></h3>
</body>

<%
} else {
%>

<title>Welcome <%= loggedAccount.getDisplayName() %></title>
</head>
<body>
	<h1>Welcome to Qizzville,  <%= loggedAccount.getDisplayName() %>!</h1>
	<h3><a href = "Login.html">Return to Login Page</a></h3>
</body>
<%
}
%>
</html>