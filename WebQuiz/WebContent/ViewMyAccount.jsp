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
  <h1>View <%= thisAccount.getDisplayName() %> Info  <small>Welcome</small></h1>
</div>
<div class="row">
<div class="col-md-2"></div>
<div class="col-md-8">This is a block</div>
<div class="col-md-2"></div>
</div>
</body>
</html>