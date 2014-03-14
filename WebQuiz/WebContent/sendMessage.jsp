<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
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
<title>Send Message</title>
</head>
<%
	Account thisAccount = (Account) session
			.getAttribute("loggedAccount");
	if(thisAccount == null) {
		request.getRequestDispatcher("loginPage.jsp").forward(request, response);
		return;
	} else {
		String toUsername = request.getParameter("toUsername");
		Account toAccount = AccountManager.getAccountByUsername(toUsername);
%>
<body>
<%@include file="navbar.html" %>
<div class="page-header">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<h1>
				Send Message
			</h1>
		</div>
	</div>
</div>
<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<div class="panel panel-primary">
				<div class="panel-heading">Send a message to <%=toAccount.getDisplayName() %></div>
				<div class="panel-body">
					<form action="SendMessageServlet" method="post">
						<input type="hidden" name="toUsername" value="<%=toUsername %>">
						<textarea name="messageField" cols="60" rows="5" class="form-control" placeholder="Type your message here"></textarea>
						<p></p>
						<button type="submit" class="btn btn-default">Send</button>
					</form>
				</div>
			</div>
			<p><a href="showProfile.jsp?username=<%=toAccount.getUsername()%>">Back to <%=toAccount.getDisplayName() %>'s profile</a></p>
		</div>
</div>
</body>
<%
	}
%>
</html>