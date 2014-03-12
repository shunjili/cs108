<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
		<a href="quiz.jsp?id=<%=quiz.getQuizID()%>"><%=quiz.getQuizName() %></a>
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
</body>
</html>