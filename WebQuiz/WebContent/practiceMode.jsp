<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList,java.sql.Timestamp, servlets.*, java.util.Date;"
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
<title>Practive Mode Quiz</title>
</head>
<%
String quiz_id = request.getParameter("id");
ArrayList<Question> Questions = QuestionManager.getQuestionsForQuiz(quiz_id);
Timestamp startingTime = (Timestamp) session.getAttribute(EvaluateQuizServlet.StartingTime_Str);
if(startingTime == null){
	startingTime = new Timestamp( new Date().getTime());
	session.setAttribute(EvaluateQuizServlet.StartingTime_Str, startingTime);
}
%>
<body>
<%@include file="navbar.html" %>
	<div class="page-header">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-7">
				<h1>
					View the Quiz <small> Good Luck!</small>
				</h1>
			</div>
		</div>
	</div>
	<div class="col-md-1"></div>
		<div class="col-md-2">
			<div>
				Some Information About this quiz
			</div>
			<p>Description of the Quiz</p>
		</div>
	<div class="col-md-7">
			<form action="PracticeModeServlet" method ="post">
			<input type="hidden" name="quiz_id" value="<%=quiz_id %>">
			
		<%if (Questions != null){ %>
			<%for (int i = 0; i < Questions.size(); i ++){ %>
					<div class="panel panel-primary"> <div class="panel-heading">
		    		<h3 class="panel-title">Question # <%= i+1 %></h3>
		  			</div>
		  			<%= Questions.get(i).getHTML(false) %>
		  			</div>	
			<%} %>
		 	<button type="submit" class="btn btn-default">Submit</button>
			</form>
		<%}else{ %>
		<p>You do not have a quiz for this id or you don't have any questions in this quiz!</p>
		<%} %>
	</div>
</body>
</html>