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
<title>Showing the Quiz</title>
</head>
<%
Account loggedAccount = ((Account) request.getSession().getAttribute("loggedAccount"));
if (loggedAccount == null) {
	request.getRequestDispatcher("loginPage.jsp").forward(request, response);
}
String quiz_id = request.getParameter("id");
String msg = request.getParameter("message");
//ArrayList<Question> Questions = QuestionManager.getQuestionsForQuiz(quiz_id);
ArrayList<Question> Questions = QuizManager.getQuizById(quiz_id).getQuestions();
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
	<div class="col-md-3"></div>
	<div class="col-md-7">
			<form action="EvaluateQuizServlet" method ="post">
			<input type="hidden" name="quiz_id" value="<%=quiz_id %>">
			
		<%if (Questions != null){ %>
			<%for (int i = 0; i < Questions.size(); i ++){
					Question q = Questions.get(i);%>
					<div class="panel panel-primary"> <div class="panel-heading">
		    		<h3 class="panel-title">Question #<%= i+1 %> (Score: <%=q.getScore() %>)</h3>
		  			</div>
		  			<%= q.getHTML(false) %>
		  			</div>	
			<%} %>
		 	<button type="submit" class="btn btn-default">Submit</button>
			</form>
		<%}else{ %>
		<p>There is no quiz for this id or there are no questions in this quiz!</p>
		<%} %>
	</div>
	
</body>
</html>