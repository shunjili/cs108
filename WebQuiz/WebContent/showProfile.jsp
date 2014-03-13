<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="objects.*,java.util.ArrayList"%>
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
</head>
<body>
<%
String shownUsername = request.getParameter("username");
Account shownAccount = AccountManager.getAccountByUsername(shownUsername);
Account loggedAccount = (Account)session.getAttribute("loggedAccount");
if (loggedAccount == null) {
%>
	<p><a href="loginPage.jsp">Log in to Quizville</a></p>
<%	
} else if (shownAccount == null) {
%>
	<p>Error getting account for username <%=shownUsername %></p>
	<p>Return to <a href="loginPage.jsp">homepage</a></p>
<%		
} else {
	String shownDisplayName = shownAccount.getDisplayName();
	String loggedUsername = loggedAccount.getUsername();
	String loggedDisplayName = loggedAccount.getDisplayName();
	boolean equal = shownAccount.equals(loggedAccount);
	boolean areFriends = AccountManager.areFriends(loggedUsername, shownUsername);
	boolean showPrivate = shownAccount.isPrivate() && !equal && !areFriends
						&& (loggedAccount.getType() != Account.Type.ADMIN);
	boolean requestShownToLogged = AccountManager.requestIsPending(shownUsername, loggedUsername);
	boolean requestLoggedToShown = AccountManager.requestIsPending(loggedUsername, shownUsername);
%>
<div class="page-header">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<h1>
				<%=shownDisplayName %> <small>(<%=shownUsername %>)</small>
			</h1>
		</div>
	</div>
</div>
<%
	if (showPrivate) {
%>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<p><%=shownDisplayName %> has a private account. Become friends to see more details</p>
			</div>
		</div>
<%
		if (requestShownToLogged) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="ConfirmFriendRequestServlet" method="post">
					<p><%= shownDisplayName %> has requested to be your friend  
					<input type="hidden" name="requester" value="<%= shownUsername %>">
					<input type="hidden" name="requested" value="<%= loggedUsername %>">
					<button type="submit" class="btn btn-default">Confirm Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		} else if (requestLoggedToShown) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>Friend Request Pending</p>
				</div>
			</div>
<%
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="SendFriendRequestServlet" method="get">
					<p>Request to be <%=shownDisplayName %>'s friend  
					<input type="hidden" name="requester" value="<%=loggedUsername%>">
					<input type="hidden" name="requested" value="<%=shownUsername%>">
					<button type="submit" class="btn btn-default">Send Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		}
	} else {
		if (equal) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>This is your profile</p>
				</div>
			</div>
<%
			
		} else if (areFriends) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>You and <%=shownDisplayName %> are friends</p>
				</div>
			</div>
<%
			
		} else if (requestShownToLogged) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="ConfirmFriendRequestServlet" method="post">
					<p><%= shownDisplayName %> has requested to be your friend  
					<input type="hidden" name="requester" value="<%= shownUsername %>">
					<input type="hidden" name="requested" value="<%= loggedUsername %>">
					<button type="submit" class="btn btn-default">Confirm Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		} else if (requestLoggedToShown) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>Friend Request Pending</p>
				</div>
			</div>
<%
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="SendFriendRequestServlet" method="get">
					<p>Request to be <%=shownDisplayName %>'s friend  
					<input type="hidden" name="requester" value="<%=loggedUsername%>">
					<input type="hidden" name="requested" value="<%=shownUsername%>">
					<button type="submit" class="btn btn-default">Send Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		}
		ArrayList<Account> friends = AccountManager.getFriendsForUser(shownUsername);
%>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<div class="panel panel-primary">
					<!-- <div class="panel-heading">Friends:</div> -->
<%
		if (friends == null) {
%>
					<div class="panel-heading">Friends</div>
						<div class="panel-body">Error getting friends for <%=shownDisplayName %></div>
<%
		} else if (friends.size() > 0) {
%>
					<div class="panel-heading">Friends <span class="badge"><%=friends.size()%></span></div>
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
								for (Account friend : friends) {
							%>
							<tr>
								<td><a
									href="showProfile.jsp?username=<%=friend.getUsername()%>"><%=friend.getDisplayName()%></a></td>
								<td><%=friend.getUsername()%></td>
								<td><%=friend.getTypeString()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
<%
		} else {
%>
					<div class="panel-heading">Friends <span class="badge" align="right"><%=friends.size() %></span></div>
						<div class="panel-body"><%=shownDisplayName %> does not have any friends yet</div>
<%			
		}
%>
				</div>
				<p><a href="ViewMyAccount.jsp">Homepage</a></p>
			</div>
		</div>	
<%
	}
}
%>

</body>
</html>