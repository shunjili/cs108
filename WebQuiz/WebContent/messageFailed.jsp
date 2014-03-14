<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/ico.jpg" /> 
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message Failed</title>
</head>
<body>
<%@include file="navbar.html" %>
<div class="page-header">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<h1>
				Message failed to send.
			</h1>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-7">
		<p>Check that the recipient was a valid account username (not Display Name).</p>
	</div>
</div>
<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-7">
		<a href="messages.jsp">View Your Messages</a>
	</div>
</div>

</body>
</html>