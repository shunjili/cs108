<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
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
<title>Quizzes</title>
</head>
<%
	Account thisAccount = (Account) session
		.getAttribute("loggedAccount");
%>
<body>
	<% if(thisAccount == null) { %>
	<p><a href="loginPage.jsp">Log in to Quizville</a></p>
	<% } else {
			String tag = request.getParameter("tag");
			ArrayList<Quiz> quizzes;
			if(tag != null && !tag.equals("")){
				quizzes = QuizManager.getQuizzesWithTag(tag);
			}else{
				quizzes = QuizManager.getAllQuizzes();	
			}
			if (quizzes == null) {
	%>
	<h1>Error getting quizzes.</h1>
	<%
	} else {
	%>
<%@include file="navbar.html" %>
	<div class="page-header">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<h1>
					Quiz Index
				</h1>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<div class="panel panel-primary">
				<div class="panel-heading">Quizzes:</div>
				<table class="table">
					<thead>
						<tr>
							<th>Quiz Name</th>
							<th>Rating</th>
							<th>Category</th>
							<th>Times Taken</th>
							<th>Creator</th>
							<th>Creation Time</th>
							
						</tr>
					</thead>
					<tbody>
						<%
							for (Quiz quiz : quizzes) {
						%>
						<tr>
							<td><a
								href="QuizInfo.jsp?id=<%=quiz.getQuizID()%>"><%=quiz.getQuizName()%></a></td>
							<td><%=quiz.getQuizRating()%></td>
							<td><%=quiz.getQuizCategory()%></td>
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
							<td><%=quiz.getQuizTimestampString() %></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
			<p><a href="ViewMyAccount.jsp">Homepage</a></p>
		</div>
	</div>
	<%
		}
	}
	%>
</body>

<%-- <%
	Account thisAccount = (Account) session
		.getAttribute("loggedAccount");
%>
<body>
	<% if(thisAccount == null) { %>
	<p><a href="loginPage.jsp">Log in to Quizville</a></p>
	<% } else { %>
	<p><a href="ViewMyAccount.jsp">Return to homepage</a></p>
	<h2>Quizzes:</h2>
		<%
			ArrayList<Quiz> quizzes = QuizManager.getAllQuizzes();
			if (quizzes != null) {
		%>
			<ul>
		<%
				for(Quiz quiz : quizzes){
 					Account creator = quiz.getQuizCreatorAccount();
		%>
		<li>
		<a href="QuizInfo.jsp?id=<%=quiz.getQuizID()%>"><%=quiz.getQuizName() %></a>
			 - <%=quiz.getQuizCategory() %> - Times Taken: <%=quiz.getTimesTaken() %> - Rating: <%=quiz.getQuizRating() %>
			 - Created <%=quiz.getQuizTimestampString() %>
			 - Created by 
			 <%if (creator == null) { %>
			 <%=quiz.getQuizCreator()%>
			 <%} else {%>
			 <a href="showProfile.jsp?username=<%=creator.getUsername()%>"><%=creator.getDisplayName() %></a>
			 <%} %>
		</li>
			<%
				}
			%>
		</ul>
		<%
			} else {
		%>
		<p>There are no quizzes</p>
		<%
			}
		}
		%>
</body> --%>
</html>