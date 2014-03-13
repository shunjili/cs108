<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@
page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friend Requests</title>
</head>

<%
	Account thisAccount = (Account) session
			.getAttribute("loggedAccount");

	if(thisAccount == null) {
%>
<body>
	<h2>
		Please <a href="loginPage.jsp">login</a> to view your messages.
	</h2>
</body>
<%
	} else {
		ArrayList<FriendRequest> friendRequests = AccountManager.getFriendReqeustsForUser(thisAccount.getUsername());
%>
<%@include file="navbar.html" %>

	<div class="page-header">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<h1>
					Friend Requests
				</h1>
			</div>
		</div>
	</div>
<%
		if (friendRequests.size() > 0) {
%>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-4">
			<div class="panel panel-primary">
				<div class="panel-heading">The following people have requested to be friends</div>
				<ul class="list-group">
					
					<%for (FriendRequest r : friendRequests) {
						Account requester = AccountManager.getAccountByUsername(r.getRequester());%>
						<li class="list-group-item">
						<form action="ConfirmFriendRequestServlet" method="post">
						<input type="hidden" name="requester" value="<%= requester.getUsername() %>">
						<input type="hidden" name="requested" value="<%= thisAccount.getUsername() %>">
						<h4 class="list-group-item-heading"><%= requester.getDisplayName() %></h4>
						<p class="list-group-item-text"><%= r.getMessage() %></p>
						<p></p>
						<button type="submit" class="btn btn-default">Confirm Friend Request</button>
						</form>
						</li>
					<%
					}
					%>
				</ul>
			</div>
		</div>
	</div>
	<%} else { %>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-5">
			<p>You do not have any friend requests.</p>
			<p><a href="accountIndex.jsp">Find Friends</a></p>
		</div>
	</div>
	<%} %>
<%
	}
%>
</html>