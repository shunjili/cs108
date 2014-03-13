<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View The Quiz</title>
</head>
<% 
String quiz_id = request.getParameter("id");
Quiz currentQuiz = QuizManager.getQuizById(quiz_id); %>
<body>
	<div class="page-header">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<h1>
					Quiz Info <small>Ready?</small>
				</h1>
			</div>
		</div>
	</div>
	<%if (currentQuiz != null){ %>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<div class="panel panel-primary">
					<div class="panel-heading"><%= currentQuiz.getQuizName() %></div>
					<ul class="list-group">
					    <li class="list-group-item">Category: <%=currentQuiz.getQuizCategory() %></li>
					    <li class="list-group-item">Created By: <%=AccountManager.getAccountByUsername("john").getDisplayName() %></li>
					    <li class="list-group-item">Description: <%= currentQuiz.getQuizDescription() %></li>
					    <li class="list-group-item">Rating: <%= currentQuiz.getQuizRating() %></li>
					  	<li class="list-group-item"><a href = "/WebQuiz/quiz.jsp?id=<%=quiz_id%>"><button type="submit" class="btn btn-default">Start the Quiz</button></a></li>
					  	
					  </ul>
				</div>
			</div>
		</div>
	<%} %>

	
</body>
</html>