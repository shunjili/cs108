<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@
page import="objects.*,java.util.ArrayList"%>
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
<title>Friend Requests</title>
</head>

<%
	Account thisAccount = (Account) session
			.getAttribute("loggedAccount");

	if(thisAccount == null) {
		request.getRequestDispatcher("loginPage.jsp").forward(request, response);
		return;
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
						Account requester = AccountManager.getAccountByUsername(r.getRequester());
						String message = r.getMessage();%>
						<li class="list-group-item">
						<form action="ConfirmFriendRequestServlet" method="post">
						<input type="hidden" name="requester" value="<%= requester.getUsername() %>">
						<input type="hidden" name="requested" value="<%= thisAccount.getUsername() %>">
						<h4 class="list-group-item-heading"><%= requester.getDisplayName() %></h4>
						<%if (!message.equals("null")) {
						%>
							<p class="list-group-item-text"><%= message %></p>
						<%
						}%>
						
						<p></p>
						<button type="submit" class="btn btn-default">Confirm Friend Request</button>
						</form>
						<p></p>
						<form action="RemoveFriendRequestServlet" method="post">
						<input type="hidden" name="requester" value="<%=requester.getUsername() %>">
						<input type="hidden" name="requested" value="<%=thisAccount.getUsername() %>">
						<button type="submit" class="btn btn-default">Ignore Friend Request</button>
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
				<div class="panel panel-primary">
					<div class="panel-heading">The following people have requested to be friends</div>
					<div class="panel-body">
						<p>You do not have any friend requests.</p>
					</div>
				</div>
			</div>
		</div>
	<%} %>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-5">
			<a href="FindFriends.jsp">Find Friends</a>
		</div>
	</div>
<%
	}
%>
</html>