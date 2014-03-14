<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<title>Homepage</title>
</head>
<%
	Account thisAccount = (Account) session
				.getAttribute("loggedAccount");

	if(thisAccount == null) {
%>
<body>
	<h2>
		Please <a href="loginPage.jsp">login</a> to view your account.
	</h2>
</body>
<%
	} else {
		ArrayList<AnnouncementManager.Announcement> announcements = AnnouncementManager.getRecentAnnouncements(10);
		ArrayList<Quiz> recentQuizzes = QuizManager.getRecentQuiz(5);
%>
<%@include file="navbar.html" %>
<body>
	<div class="page-header">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-7">
				<h1>
					Welcome <small>Homepage</small>
				</h1>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-2">
			<div class="well">
				<div>
					<%=thisAccount.getDisplayName()%>
				</div>
				<div>Type: <%=thisAccount.getTypeString() %></div>
<%
				if (thisAccount.isPrivate()) {
%>
				<div>Private</div>
<%	
				}
%>
			</div>
			<div class="list-group">
				<a href="/WebQuiz/showProfile.jsp?username=<%=thisAccount.getUsername()%>" class="list-group-item">View My Profile</a>	
				<a href="/WebQuiz/CreateQuiz.jsp" class="list-group-item">Create a Quiz</a>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-primary">
				 <div class="panel-heading">Announcements</div>
					<table class="table">
						<thead>
							<tr>
								<th>Name</th>
								<th>Announcement</th>
								<th>Time</th>
							</tr>
						</thead>
						<tbody>
<%
							if (announcements != null) {
								for (AnnouncementManager.Announcement a : announcements) {
									String username = a.getAnnouncementUsername();
									String text = a.getAnnouncementText();
									String timestamp = a.getAnnouncementTimestampString();
%>
									<tr>
										<td>
										<%
										Account acct = AccountManager.getAccountByUsername(username);
										if (acct == null) {
										%>
										<%=username %>
										<%
										} else {
										%>
										<a href="showProfile.jsp?username=<%=acct.getUsername()%>"><%=acct.getDisplayName() %></a>
										<%
										}
										%>
										</td>
										<td><%=text %></td>
										<td><%=timestamp %></td>
									</tr>
<%
								}
							}
%>
						</tbody>
					</table>
			</div>
			<div class="panel panel-primary">
				 <div class="panel-heading">List of Popular Quizzes</div>
<%
					ArrayList<Quiz> popularQuizzes = QuizManager.getMostPopularQuizzes(5);
%>
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
							for (Quiz quiz : popularQuizzes) {
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
			<div class="panel panel-primary">
				 <div class="panel-heading">List of Recently Created Quizzes</div>
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
							for (Quiz quiz : recentQuizzes) {
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
			<div class="panel panel-primary">
				 <div class="panel-heading">List of Taken Quizzes</div>
<%
				ArrayList<Quiz> takenQuizzes = QuizManager.getQuizzesTaken(thisAccount.getUsername());
%>
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
							for (Quiz quiz : takenQuizzes) {
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

		</div>
	
		<div class="col-md-2">
			<h5>Additional Options:</h5>
				<div class="list-group">
					<a href="/WebQuiz/accountIndex.jsp" class="list-group-item">Account Index</a>
					<a href="/WebQuiz/quizIndex.jsp" class="list-group-item">Quizzes</a>
					<a href="/WebQuiz/messages.jsp" class="list-group-item"> <span class="badge"><%= MessageManager.getReceived(thisAccount.getUsername()).size()%></span>Messages</a>
					<a href = "/WebQuiz/FriendRequests.jsp" class="list-group-item"> 
						<span class="badge"><%= AccountManager.getFriendReqeustsForUser(thisAccount.getUsername()).size()%></span>Friend Requests</a>
<%
					if (thisAccount.getType() == Account.Type.ADMIN) {
%>
					<a href = "/WebQuiz/makeAnnouncement.jsp" class="list-group-item">Create Announcement</a>
					<a href="/WebQuiz/Administration.jsp" class="list-group-item">Administration</a>
<%
					}
%>
					<a href="/WebQuiz/loginPage.jsp" class="list-group-item">Logout</a>
				</div>
		</div>
		<div class="col-md-1"></div>
</body>
<%
	}
%>
</html>