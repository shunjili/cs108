<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="objects.*"%>
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
<%
	String requester = request.getParameter("requester");
	String requested = request.getParameter("requested");
	Account friendAccount = AccountManager.getAccountByUsername(requested);
%>
<title>Friend Request</title>
</head>
<body>
<%@include file="navbar.html" %>
<div class="page-header">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<h1>
				Send Friend Request
			</h1>
		</div>
	</div>
</div>
<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<div class="panel panel-primary">
				<div class="panel-heading">Request to be friends with <%=friendAccount.getDisplayName() %></div>
				<div class="panel-body">
					<form action="SendFriendRequestServlet" method="post">
						<input type="hidden" name="requester" value="<%=requester %>">
						<input type="hidden" name="requested" value="<%=requested %>">
						<textarea cols="60" rows="5" class="form-control" placeholder="Send a Message"></textarea>
						<button type="submit" class="btn btn-default">Send</button>
					</form>
				</div>
			</div>
			<p><a href="showProfile.jsp?username=<%=friendAccount.getUsername()%>">Back to <%=friendAccount.getDisplayName() %>'s profile</a></p>
			<p><a href="ViewMyAccount.jsp">Homepage</a></p>
		</div>
</div>
</body>
</html>