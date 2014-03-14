<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accounts Index</title>
</head>
<%
	Account thisAccount = (Account) session
		.getAttribute("loggedAccount");
%>
<body>
	<% if(thisAccount == null) { %>
	<p><a href="loginPage.jsp">Log in to Quizville</a></p>
	<% } else {
			ArrayList<Account> accounts = AccountManager.getAllAccounts();
			if (accounts == null) {
	%>
	<h1>Error getting accounts.</h1>
	<%
			} else {
	%>
<%@include file="navbar.html" %>
	<div class="page-header">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<h1>
					Accounts Index
				</h1>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<div class="panel panel-primary">
				<div class="panel-heading">Accounts:</div>
				<table class="table">
					<thead>
						<tr>
							<th>Name</th>
							<th>Username</th>
							<th>Type</th>
						</tr>
					</thead>
					<tbody>
						<%
							for (Account acct : accounts) {
						%>
						<tr>
							<td><a
								href="showProfile.jsp?username=<%=acct.getUsername()%>"><%=acct.getDisplayName()%></a></td>
							<td><%=acct.getUsername()%></td>
							<td><%=acct.getTypeString()%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%
		}
	}
	%>
</body>
</html>