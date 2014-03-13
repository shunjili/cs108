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
int score = (Integer) session.getAttribute(EvaluateOneQuizQuestionServlet.Score_Str);
String quiz_id = request.getParameter("quiz_id");
Long duration = (Long) session.getAttribute(EvaluateQuizServlet.Duration_str);
HashMap<Question, ArrayList<String>> questionAnswerHash = (HashMap<Question, ArrayList<String>>) session.getAttribute(EvaluateQuizServlet.Hash_Str);
ArrayList<Question> questions =(ArrayList<Question>) session.getAttribute(EvaluateQuizServlet.Questions_Str);
boolean valid = questionAnswerHash != null && questions != null && questionAnswerHash.size() == questions.size();
System.out.println(questionAnswerHash.size());
System.out.println(questions.size());
System.out.println(valid);
%>
<body>
<%@include file="navbar.html" %>
<div class="page-header">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-7">
				<h1>
					Review Quiz Results <small> You got <%=score %> points! 
					<%if(duration != null ){ %>
						Duration: <%=duration %> minutes
					<%}%>
					 </small>
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
				boolean correct = question.isCorrect(answers);
				String panelClass;
				if(correct){
					 panelClass = "success";
				}else{
					panelClass = "danger";
				}
		%>
			<div class="panel panel-<%=panelClass%>"> 
				<div class="panel-heading">
		    		<h3 class="panel-title">Question # <%= i+1 %></h3>
		    	</div>
		    	<%= question.getResultView(answers) %>
		    </div>
		<%}%>
			<div class="panel panel-info"> 
				<div class="panel-heading">
		    		<h3 class="panel-title">Please Rate the Quiz</h3>
		    	</div>
		    	<div class="panel-body">
		    	On the scale of 1 to 5, what do you think represents the quality of this quiz?
			    	<div class="btn-group">
			    		<a href="/WebQuiz/ReviewSubmissionServlet?rating=1&quiz_id=<%=quiz_id%>"> <button type="button" class="btn btn-default">1</button></a>
			    		<a href="/WebQuiz/ReviewSubmissionServlet?rating=2&quiz_id=<%=quiz_id%>"> <button type="button" class="btn btn-default">2</button></a>
			    		<a href="/WebQuiz/ReviewSubmissionServlet?rating=3&quiz_id=<%=quiz_id%>"> <button type="button" class="btn btn-default">3</button></a>
			    		<a href="/WebQuiz/ReviewSubmissionServlet?rating=4&quiz_id=<%=quiz_id%>"> <button type="button" class="btn btn-default">4</button></a>
			    		<a href="/WebQuiz/ReviewSubmissionServlet?rating=5&quiz_id=<%=quiz_id%>"> <button type="button" class="btn btn-default">5</button></a>
			    		
					</div>	
		    	</div>
    	
	    	</div>
		<%}%>
	</div>
	<div class="col-md-2"></div>
</div>
<%
	session.setAttribute(EvaluateQuizServlet.Questions_Str,null);
	session.setAttribute(EvaluateQuizServlet.Hash_Str,null);
	session.setAttribute(EvaluateOneQuizQuestionServlet.Score_Str,null);
	session.setAttribute("quiz_id",null);

%>
</body>
</html>