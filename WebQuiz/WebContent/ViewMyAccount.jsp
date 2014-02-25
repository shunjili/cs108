<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@
page import="objects.Account"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<% 
Account thisAccount= (Account) session.getAttribute("loggedAccount");
%>
<div class="page-header">
  <h1>View My Account Info  <small>Welcome</small></h1>
</div>
<div class="row">
<div class="col-md-1">

</div>
<div class="col-md-2">
<div>
 <%= thisAccount.getDisplayName() %>
</div>
<p>
Some Basic Information
</p>
</div>
<div class="col-md-7">This is this person's main activity panel</div>
<div class="col-md-2"><a href = "/WebQuiz/Login.html">Login Page</a></div>
</div>
</body>
</html>