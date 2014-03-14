<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administration</title>
</head>
<%
	Account thisAccount = (Account) session.getAttribute("loggedAccount");
%>
<body>
<%
	if (thisAccount == null) {
		request.getRequestDispatcher("loginPage.jsp").forward(request, response);
		return;
	} else {
%>
		<%@include file="navbar.html" %>
		<div class="page-header">
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<h1>
						Quiz Leaderboard
					</h1>
				</div>
			</div>
		</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quiz Rankings</div>
						<div class="panel-body">
							<ul class="nav nav-tabs">
				  			<li class="active"><a href="#rating" data-toggle="tab">Ratings Leaderboard</a></li>
				  			<li><a href="#timesTaken" data-toggle="tab">Times Taken Leaderboard</a></li>
							</ul>
							
							<div class="tab-content">
							<br>
								
								<div class="tab-pane active" id="rating">
									<table class="table">
										<thead>
											<tr>
												<th>#</th>
												<th>Quiz Name</th>
												<th>Average Rating</th>
												<th>Creator</th>
											</tr>
										</thead>
										<tbody>
<%
											ArrayList<Quiz> quizzes = QuizManager.getHighestRatedQuizzes();
											if (quizzes != null) {
												int count = 1;
												for (Quiz quiz : quizzes) {
													double rating = quiz.getQuizRating();
%>
											<tr>
												<td><%=count %></td>
												<%count++; %>
												<td><a
													href="QuizInfo.jsp?id=<%=quiz.getQuizID()%>"><%=quiz.getQuizName()%></a></td>
												<td><%=rating %></td>
												<td>
													<%
													Account creator = quiz.getQuizCreatorAccount();
													if (creator == null) {
													%>
													<%=quiz.getQuizCreator() %>
													<%
													} else {
													%>
													<a href="showProfile.jsp?username=<%=creator.getUsername()%>"><%=creator.getDisplayName() %></a>
													<%
													}
													%>
												</td>
											</tr>
<%
												}
											}
%>
										</tbody>
									</table>
								</div>
								
								<div class="tab-pane" id="timesTaken">
									<table class="table">
										<thead>
											<tr>
												<th>#</th>
												<th>Quiz Name</th>
												<th>Times Taken</th>
												<th>Creator</th>
											</tr>
										</thead>
										<tbody>
<%
											quizzes = QuizManager.getAllQuizzesMostTakenOrder();
											if (quizzes != null) {
												int count = 1;
												for (Quiz quiz : quizzes) {
%>
											<tr>
												<td><%=count %></td>
												<%count++; %>
												<td><a
													href="QuizInfo.jsp?id=<%=quiz.getQuizID()%>"><%=quiz.getQuizName()%></a></td>
												<td><%=quiz.getTimesTaken() %></td>
												<td>
													<%
													Account creator = quiz.getQuizCreatorAccount();
													if (creator == null) {
													%>
													<%=quiz.getQuizCreator() %>
													<%
													} else {
													%>
													<a href="showProfile.jsp?username=<%=creator.getUsername()%>"><%=creator.getDisplayName() %></a>
													<%
													}
													%>
												</td>
											</tr>
<%
												}
											}
%>
										</tbody>
									</table>
								</div>
								
							</div>
						
						
						
						
						</div>
					</div>
				</div>
			</div>
<%
		}

%>
</body>
</html>