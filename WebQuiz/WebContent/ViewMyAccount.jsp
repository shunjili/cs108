<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*"%>
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
<%
		Account thisAccount = (Account) session
				.getAttribute("loggedAccount");

	if(thisAccount == null) {
%>
<body>
	<h2>
		Please <a href="loginPage.jsp">login</a> to view your account.
	</h2>
</body>
<%
	} else {
%>
<body>
	<div class="page-header">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-7">
				<h1>
					View My Account Info <small>Welcome</small>
				</h1>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-2">
			<div>
				<%=thisAccount.getDisplayName()%>
			</div>
			<p>Some Basic Information</p>
		</div>
		<div class="col-md-7">
			<h3>Announcements</h3>

			<h3>List of Popular Quiz</h3>
			<h3>List of Recently Created Quiz</h3>
			<h3>List of Taken Quiz</h3>
			<h3>List of their recent quiz creating activities</h3>

		</div>
		<div class="col-md-2">
			<ul>
				<li><a href="/WebQuiz/loginPage.jsp">Login Page</a></li>
				<li><a href="/WebQuiz/messages.jsp"> Messages in inbox: 
				<%= MessageManager.getReceived(thisAccount.getUsername()).size()%></a></li>
				<li>
					<div>Your friends recent activities</div>
				</li>
			</ul>
		</div>
</body>
<%
	}
%>
</html>