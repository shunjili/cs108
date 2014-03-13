<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@
page import="objects.*,java.util.ArrayList, servlets.*"%>
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
<title>Messages</title>
</head>

<%
	Account thisAccount = (Account) session
			.getAttribute("loggedAccount");

	if(thisAccount == null) {
%>
<body>
	<p><a href="loginPage.jsp">Log in to Quizville</a></p>
</body>
<%
	} else {
		ArrayList<Message> messageList = MessageManager.getReceived(thisAccount.getUsername());		
%>
<body>
<%@include file="navbar.html" %>
	<div class="page-header">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<h1>
					Messages
				</h1>
			</div>
		</div>
	</div>

<%
		if (messageList.size() > 0) {
%>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-5">
			<div class="panel panel-primary">
				<div class="panel-heading">Your messages</div>
				<ul class="list-group">
					
					<%for (Message msg : messageList) {
						Account sender = AccountManager.getAccountByUsername(msg.getSender());%>
						<li class="list-group-item">
						<h4 class="list-group-item-heading"><%= sender.getDisplayName() %></h4>
						<p class="list-group-item-text"><%= msg.getTimeString() %></p>
						<p class="list-group-item-text"><%= msg.getMessage() %></p>
						<p></p>
						<form action="DeleteMessageServlet" method="post">
						<input type="hidden" name="msgID" value="<%= msg.getId() %>">
						<button type="submit" class="btn btn-sm">Delete Message</button>
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
					<div class="panel-heading">Your messages</div>
					<div class="panel-body">You do not have any messages.</div>
				</div>
			</div>
		</div>
	<%} %>



<%-- 	<h4>Your Messages:</h4>
	<ul>
		<%
			for(Message msg : messageList) {
				Account sender = AccountManager.getAccountByUsername(msg.getSender());
				%>
				<li>From <%= sender.getDisplayName() %> At <%=msg.getTimeString() %>: <%= msg.getMessage() %></li>
		<%
			}
		%>
	</ul> --%>
</body>
<%
	}
%>
</html>