<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList, javax.servlet.http.HttpSession, java.sql.Timestamp, java.util.Date, java.util.HashMap, servlets.*"
%>
<%
Account loggedAccount = ((Account) request.getSession().getAttribute("loggedAccount"));
if (loggedAccount == null) {
	request.getRequestDispatcher("loginPage.jsp").forward(request, response);
	return;
}
session = request.getSession();
String quiz_id = (String)session.getAttribute("quiz_id");
if(quiz_id == null){
	quiz_id = request.getParameter("id");
	session.setAttribute("quiz_id",quiz_id);
}
Quiz currentQuiz = QuizManager.getQuizById(quiz_id);
ArrayList<Question> Questions = (ArrayList<Question>) session.getAttribute(EvaluateOneQuizQuestionServlet.CurQuestion_Str);
if(Questions == null || Questions.size() <= 0){

	//Questions = QuestionManager.getQuestionsForQuiz(quiz_id);
	Questions = QuizManager.getQuizById(quiz_id).getQuestions();
	session.setAttribute(EvaluateOneQuizQuestionServlet.CurQuestion_Str, Questions);
}

Timestamp startingTime = (Timestamp) session.getAttribute(EvaluateQuizServlet.StartingTime_Str);
if(startingTime == null){
	startingTime = new Timestamp( new Date().getTime());
	session.setAttribute(EvaluateQuizServlet.StartingTime_Str, startingTime);
}
Integer tempScore = (Integer)session.getAttribute(EvaluateOneQuizQuestionServlet.TempScore_Str);
if(tempScore == null){
	tempScore = 0;
	session.setAttribute(EvaluateOneQuizQuestionServlet.TempScore_Str, tempScore);
}

HashMap<Question, ArrayList<String>> questionAnswerHash = (HashMap<Question, ArrayList<String>>) session.getAttribute(EvaluateQuizServlet.Hash_Str);
ArrayList<Question> questionsList =(ArrayList<Question>) session.getAttribute(EvaluateQuizServlet.Questions_Str);
boolean valid = questionAnswerHash != null && questionsList != null && questionAnswerHash.size() == questionsList.size();

//store it into session
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/ico.jpg" /> 
<%@include file="navbar.html" %>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<link rel="stylesheet"
		href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet"
		href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<title>Single Page</title>
</head>
<body>
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
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-7">

				<div>
					Current Score: <%=tempScore %>
				</div><br>

				<%if(valid&&currentQuiz.isCorrectedImmediately()){ 
					//for(int i = 0; i < questions.size(); i++){
						Question question = questionsList.get(questionsList.size() - 1);
						ArrayList<String> answers = questionAnswerHash.get(question);
						System.out.println(answers);
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
				    		<h3 class="panel-title">Question (Score: <%=question.getScore() %>)</h3>
				    	</div>
				    	<%= question.getResultView(answers) %>
				    </div>
				<%}%>
					<form action="EvaluateOneQuizQuestionServlet" method ="post">
					<input type="hidden" name="quiz_id" value="<%=quiz_id %>">
			
				<%if ((Questions != null) &&(Questions.size() >= 0)){ %>
						<div class="panel panel-primary"> <div class="panel-heading">
				    	<h3 class="panel-title">Question (Score: <%=Questions.get(0).getScore() %>)</h3>
				  		</div>
				  			<%= Questions.get(0).getHTML(false) %>
				  		</div>	
				 	<button type="submit" class="btn btn-default">Submit</button>
					</form>
					<% //Questions.remove(0); 
					   //session.setAttribute("Questions", Questions);%>
				<%}else{ %>
				<p>You do not have a quiz for this id or you don't have any questions in this quiz!</p>
				<%} %>
			</div>
		</div>
</body>
</html>