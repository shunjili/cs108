<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList, servlets.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/ico.jpg" /> 
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
Account loggedAccount = ((Account) request.getSession().getAttribute("loggedAccount"));
if (loggedAccount == null) {
	request.getRequestDispatcher("loginPage.jsp").forward(request, response);
	return;
}
String quiz_id = request.getParameter("id");
Quiz currentQuiz = QuizManager.getQuizById(quiz_id);
ArrayList<Question> questions = QuestionManager.getQuestionsForQuiz(quiz_id);
int numQuestions = 0;
if(questions != null){
	numQuestions = questions.size();
}
int totalScore = 0;
for (int i = 0; i < numQuestions; i++) {
	totalScore += questions.get(i).getScore();
}
ArrayList<String> tags = QuizManager.getTagsForQuiz(quiz_id);
String tagsString = "";
int numTags = tags.size();
for (int i = 0; i < numTags; i++) {
	String tag = tags.get(i);
	if (!tag.equals("")) {
		if (i == numTags - 1) {
			tagsString += tags.get(i);
		} else {
			tagsString += tags.get(i) + ", ";
		}
	}
}
%>
<body>
	<%@include file="navbar.html" %>
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
					    <li class="list-group-item">Created By: 
					    <%Account creator = currentQuiz.getQuizCreatorAccount(); %>
					    	<a href="showProfile?username="<%=creator.getUsername() %>><%=creator.getDisplayName() %></a></li>
					    <li class="list-group-item">Description: <%= currentQuiz.getQuizDescription() %></li>
					    <li class="list-group-item">Average Rating: <%= currentQuiz.getQuizRating() %></li>
					    <li class="list-group-item">Number of Questions: <%=numQuestions %></li>
					    <li class="list-group-item">Total Score: <%=totalScore %></li>
					    <li class="list-group-item">Tags: <%=tagsString %></li>
					    
					    <%--High scores --%>
					    <li class="list-group-item">
<%
						ArrayList<QuizAttempt> attempts = QuizManager.getTopAttempts(quiz_id, 5);
%>
						High Scores: 
<%
						if (attempts.size() > 0) {
%>
					    	<br>
					    	<table class="table">
								<thead>
									<tr>
										<th>Name</th>
										<th>Username</th>
										<th>Score</th>
										<th>Duration</th>
										<th>Time</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (QuizAttempt attempt : attempts) {
											Account acct = AccountManager.getAccountByUsername(attempt.getUsername());
									%>
									<tr>
										<td><a
											href="showProfile.jsp?username=<%=acct.getUsername()%>"><%=acct.getDisplayName()%></a></td>
										<td><%=acct.getUsername()%></td>
										<td><%=attempt.getScore()%></td>
										<td><%=attempt.getDurationString() %></td>
										<td><%=attempt.getStartTimeStr() %></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
<%
						} else {
%>
						There are no stored quiz attempts for this quiz
<%
						}
%>
					    </li>
					    
					    <%--User's past performance --%>
					    <li class="list-group-item">
<%
						attempts = QuizManager.getLastAttemptsForUser(quiz_id, loggedAccount.getUsername(), 5);
%>
						Your past performance on this quiz: 
<%
						if (attempts.size() > 0) {
%>
					    	<br>
					    	<table class="table">
								<thead>
									<tr>
										<th>Score</th>
										<th>Duration</th>
										<th>Time</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (QuizAttempt attempt : attempts) {
											Account acct = AccountManager.getAccountByUsername(attempt.getUsername());
									%>
									<tr>
										<td><%=attempt.getScore()%></td>
										<td><%=attempt.getDurationString() %></td>
										<td><%=attempt.getStartTimeStr() %></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
<%
						} else {
%>
						You have not taken this quiz yet.
<%
						}
%>
					    </li>
					    
					    <%--Bottom buttons --%>
					  	<li class="list-group-item">
					  		<%if(!currentQuiz.isOnePage()){ %>
					  			<a href = "/WebQuiz/quiz.jsp?id=<%=quiz_id%>"><button type="submit" class="btn btn-default">Start the Quiz</button></a>
					  		<%}else{ %>
					  			<% 
						  			session.setAttribute(EvaluateQuizServlet.Questions_Str,null);
						  			session.setAttribute(EvaluateQuizServlet.Hash_Str,null);
						  			session.setAttribute(EvaluateOneQuizQuestionServlet.Score_Str,null);
						  			session.setAttribute("quiz_id",null);
					  			%>
					  			<a href = "/WebQuiz/quizOnePage.jsp?id=<%=quiz_id%>"><button type="submit" class="btn btn-default">Start the Quiz</button></a>
					  		<%} %>
					  			<a href = "/WebQuiz/quizHighScores.jsp?id=<%=quiz_id %>"><button type="submit" class="btn btn-default">Quiz High Scores</button></a>
								<a href = "/WebQuiz/InviteFriendForQuiz.jsp?quiz_id=<%=quiz_id%>"><button type="submit" class="btn btn-default">Invite a friend to take this quiz!</button></a>	
							<%if(currentQuiz.canPractice()){ %>	
								<a href = "/WebQuiz/practiceMode.jsp?id=<%=quiz_id%>"><button type="submit" class="btn btn-default">Practice Mode</button></a>	
							<%}%>	
							<%if(currentQuiz.getQuizCreator().equals(loggedAccount.getUsername())){ %>	
								<a href = "/WebQuiz/EditQuestions.jsp?id=<%=quiz_id%>"><button type="submit" class="btn btn-default">Edit Questions</button></a>	
							<%} %>						  		  	
					  	</li>
					  </ul>
				</div>
			</div>
		</div>
	<%} %>

	
</body>
</html>