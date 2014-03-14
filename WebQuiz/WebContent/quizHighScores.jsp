<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quiz High Scores</title>
</head>
<%
	Account thisAccount = (Account) session
		.getAttribute("loggedAccount");
%>
<body>
	<% if(thisAccount == null) {
		request.getRequestDispatcher("loginPage.jsp").forward(request, response);
		return;
	} else {
			String quiz_id = request.getParameter("id");
			Quiz quiz = QuizManager.getQuizById(quiz_id);
			ArrayList<QuizAttempt> attempts = QuizManager.getTopAttempts(quiz_id, 5);
			if (attempts == null) {
	%>
	<h1>Error getting quiz high scores.</h1>
	<%
			} else {
	%>
<%@include file="navbar.html" %>
	<div class="page-header">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<h1>
					Quiz High Scores
				</h1>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<div class="panel panel-primary">
				<div class="panel-heading"><a href="QuizInfo.jsp?id=<%=quiz_id %>"><%=quiz.getQuizName() %></a> High Scores</div>
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
							<td><%=attempt.getDuration() %> seconds</td>
							<td><%=attempt.getStartTimeStr() %></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%
		}
	}
	%>
</body>
</html>