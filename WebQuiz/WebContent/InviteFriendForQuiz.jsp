<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/ico.jpg" /> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="navbar.html" %>
<%
Account thisAccount = (Account) session.getAttribute("loggedAccount");
if(thisAccount == null) { 
	request.getRequestDispatcher("loginPage.jsp").forward(request, response);
	return;
}
String quiz_id = request.getParameter("quiz_id");
if(quiz_id != null){
	String placeHolderMessage = "Hi, would you like you to take this <a href = '/WebQuiz/quiz.jsp?id="+quiz_id+"'> quiz </a> ?";
%>
	<div class="page-header">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-7">
					<h1>
						Invite a Friend
					</h1>
				</div>
			</div>		
	</div>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-7">
			<div class="panel panel-primary">
				<div class="panel-heading">Invite a Friend</div>
				<div class="panel-body">
					<form action="SendMessageServlet" method="post">
					<div class="input-group input-group-lg">
  						<span class="input-group-addon">Friend Username:</span>
  						<input type="text" name="toUsername" class="form-control" placeholder="Username">
  						<input type="hidden" name = "messageField" value="<%=placeHolderMessage%>">
					</div>
					<br>
					<p>Message: <%=placeHolderMessage %></p>
						
					<button type="submit" class="btn btn-default">Send</button>
					</form>
				</div>
			</div>
			<p><a href="ViewMyAccount.jsp">Homepage</a></p>
		</div>
	</div>		
<%} %>
</body>
</html>