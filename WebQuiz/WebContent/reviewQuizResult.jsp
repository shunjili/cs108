<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@
page import="objects.*, java.util.ArrayList, java.util.HashMap, servlets.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Quiz Result</title>
</head>
<%
int score = (Integer) session.getAttribute("score");
HashMap<Question, ArrayList<String>> questionAnswerHash = (HashMap<Question, ArrayList<String>>) session.getAttribute(EvaluateQuizServlet.Hash_Str);
ArrayList<Question> questions =(ArrayList<Question>) session.getAttribute(EvaluateQuizServlet.Questions_Str);
boolean valid = questionAnswerHash != null && questions != null && questionAnswerHash.size() == questions.size();
%>
<body>
<div class="page-header">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-7">
				<h1>
					Review Quiz Results <small> You got <%=score %> points!</small>
				</h1>
			</div>
		</div>
	</div>
<div class="row">
	<div class="col-md-3"></div>
	<div class="col-md-7">

		<%if(valid){ 
			for(int i = 0; i < questions.size(); i++){
				Question question = questions.get(i);
				ArrayList<String> answers = questionAnswerHash.get(question);
		%>
			<div class="panel panel-primary"> 
				<div class="panel-heading">
		    		<h3 class="panel-title">Question # <%= i+1 %></h3>
		    	</div>
		    	<%= question.getResultView(answers) %>
		    </div>
		<%} }%>
	</div>
	<div class="col-md-2"></div>
</div>
</body>
</html>