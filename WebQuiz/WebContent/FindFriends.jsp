<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find Friends</title>
</head>
<body>
<%
	Account thisAccount = (Account) session.getAttribute("loggedAccount");
	if (thisAccount == null) {
		request.getRequestDispatcher("loginPage.jsp").forward(request, response);
		return;
	}
	String search = request.getParameter("search");
%>
<%@include file="navbar.html" %>
<div class="page-header">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<h1>
				Find Friends
			</h1>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-7">
		<div class="panel panel-primary">
			<div class="panel-heading">Find Friends:</div>
				<div class="panel-body">
					<form action="FindFriends.jsp" method="get" role="search">
						<div class="form-group">
							<input type="text" name="search" class="form-control" placeholder="Friend Username">
						</div>
						<button type="submit" class="btn btn-default">Find Friends</button>
					</form>
				</div>
<%
				if (search != null) {
					ArrayList<Account> accounts = new ArrayList<Account>();
					if (search.equals("")) {
						accounts = AccountManager.getAllAccounts();
					} else {
						accounts = AccountManager.getAccountsByUsername(search);
					}
%>
				<table class="table">
					<thead>
						<tr>
							<th>Name</th>
							<th>Username</th>
							<th>Type</th>
							<th>Privacy</th>
							<th>Request/Remove</th>
						</tr>
					</thead>
					<tbody>
						<%
							for (Account acct : accounts) {
								String username = acct.getUsername();
						%>
						<tr>
							<td><a
								href="showProfile.jsp?username=<%=username%>"><%=acct.getDisplayName()%></a></td>
							<td><%=username%></td>
							<td><%=acct.getTypeString()%></td>
							<td>
<%
							if (acct.isPrivate()) {
%>
								PRIVATE
<%								
							} else {
%>
								PUBLIC
<%									
							}
%>
							</td>
<%
							String loggedUsername = thisAccount.getUsername();
							if (AccountManager.areFriends(loggedUsername, username)) {
%>
							<td>
								<form action="RemoveFriendServlet" method="post">
									<input type="hidden" name="username1" value="<%=username %>">
									<input type="hidden" name="username2" value="<%=loggedUsername %>">
									<button type="submit" class="btn btn-default">Remove Friend</button>
								</form>
							</td>
<%
							} else if (AccountManager.requestIsPending(loggedUsername, username)) {
%>
							<td>Request Pending</td>
<%							
							} else if (AccountManager.requestIsPending(username, loggedUsername)) {
%>
							<td>
								<form action="ConfirmFriendRequestServlet" method="post">
									<input type="hidden" name="requester" value="<%= username %>">
									<input type="hidden" name="requested" value="<%= loggedUsername %>">
									<button type="submit" class="btn btn-default">Confirm Friend Request</button>
								</form>
							</td>
<%
							} else {
%>
							<td>
								<form action="SendFriendRequestServlet" method="get">
									<input type="hidden" name="requester" value="<%=loggedUsername%>">
									<input type="hidden" name="requested" value="<%=username%>">
									<button type="submit" class="btn btn-default">Send Friend Request</button>
								</form>
							</td>
<%
							}
%>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
<%				
				}
%>
		</div>
	</div>
</div>


</body>
</html>