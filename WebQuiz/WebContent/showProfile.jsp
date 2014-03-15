<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="images/ico.jpg" /> 
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<%
Account loggedAccount = (Account)session.getAttribute("loggedAccount");
if (loggedAccount == null) {
	request.getRequestDispatcher("loginPage.jsp").forward(request, response);
	return;
}
String shownUsername = request.getParameter("username");
Account shownAccount = AccountManager.getAccountByUsername(shownUsername);
if (shownAccount == null) {
%>
	<p>Error getting account for username <%=shownUsername %></p>
	<p>Return to <a href="loginPage.jsp">homepage</a></p>
<%	
} else if (!shownAccount.isActive()) {
%>
	<p><%=shownAccount.getDisplayName() %>'s account has been disabled.</p>
<%	
} else {
	String shownDisplayName = shownAccount.getDisplayName();
	String loggedUsername = loggedAccount.getUsername();
	String loggedDisplayName = loggedAccount.getDisplayName();
	boolean equal = shownAccount.equals(loggedAccount);
	boolean areFriends = AccountManager.areFriends(loggedUsername, shownUsername);
	boolean showPrivate = shownAccount.isPrivate() && !equal && !areFriends
						&& (loggedAccount.getType() != Account.Type.ADMIN);
	boolean requestShownToLogged = AccountManager.requestIsPending(shownUsername, loggedUsername);
	boolean requestLoggedToShown = AccountManager.requestIsPending(loggedUsername, shownUsername);
%>
<%@include file="navbar.html" %>
<div class="page-header">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<h1>
				<%=shownDisplayName %> <small>(<%=shownUsername %>)</small>
			</h1>
		</div>
	</div>
</div>
<%
	if (showPrivate) {
%>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<p><%=shownDisplayName %> has a private account. Become friends to see more details</p>
			</div>
		</div>
<%
		if (requestShownToLogged) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="ConfirmFriendRequestServlet" method="post">
					<p><%= shownDisplayName %> has requested to be your friend  
					<input type="hidden" name="requester" value="<%= shownUsername %>">
					<input type="hidden" name="requested" value="<%= loggedUsername %>">
					<button type="submit" class="btn btn-default">Confirm Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		} else if (requestLoggedToShown) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>Friend Request Pending</p>
				</div>
			</div>
<%
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="SendFriendRequestServlet" method="get">
					<p>Request to be <%=shownDisplayName %>'s friend  
					<input type="hidden" name="requester" value="<%=loggedUsername%>">
					<input type="hidden" name="requested" value="<%=shownUsername%>">
					<button type="submit" class="btn btn-default">Send Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		}
	} else {
		if (equal) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>This is your profile</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
<%
				if (shownAccount.isPrivate()) {
%>
					<form action="MakePublicServlet" method="post">
						<input type="hidden" name="username" value="<%=shownUsername%>">
						<button type="submit" class="btn btn-default">Make Account Public</button>
					</form>
<%
				} else {
%>
					<form action="MakePrivateServlet" method="post">
						<input type="hidden" name="username" value="<%=shownUsername%>">
						<button type="submit" class="btn btn-default">Make Account Public</button>
					</form>
<%					
				}
%>
				</div>
			</div>
<%
			
		} else if (areFriends) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>You and <%=shownDisplayName %> are friends</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="sendMessage.jsp" method="get">
					<input type="hidden" name="toUsername" value="<%=shownUsername %>">
					<p>Send <%=shownDisplayName %> a message  
					<button type="submit" class="btn btn-default">Send Message</button></p>
				</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="RemoveFriendServlet" method="post">
					<input type="hidden" name="username1" value="<%=shownUsername %>">
					<input type="hidden" name="username2" value="<%=loggedUsername %>">
					<button type="submit" class="btn btn-default">Remove Friend</button>
				</form>
				</div>
			</div><br>
<%
			
		} else if (requestShownToLogged) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="ConfirmFriendRequestServlet" method="post">
					<p><%= shownDisplayName %> has requested to be your friend  
					<input type="hidden" name="requester" value="<%= shownUsername %>">
					<input type="hidden" name="requested" value="<%= loggedUsername %>">
					<button type="submit" class="btn btn-default">Confirm Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		} else if (requestLoggedToShown) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<p>Friend Request Pending</p>
				</div>
			</div>
<%
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
				<form action="SendFriendRequestServlet" method="get">
					<p>Request to be <%=shownDisplayName %>'s friend  
					<input type="hidden" name="requester" value="<%=loggedUsername%>">
					<input type="hidden" name="requested" value="<%=shownUsername%>">
					<button type="submit" class="btn btn-default">Send Friend Request</button></p>
				</form>
				</div>
			</div>
<%
		}
		ArrayList<Quiz> createdQuizzes = QuizManager.getSelfCreatedQuiz(shownUsername);
		if (createdQuizzes == null) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quizzes Created by <%=shownDisplayName %></div>
						<div class="panel-body">Error getting quizzes created by <%=shownDisplayName %></div>
					</div>
				</div>
			</div>
<%
		} else if (createdQuizzes.size() > 0) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quizzes Created by <%=shownDisplayName %> <span class="badge"><%=createdQuizzes.size()%></span></div>
							<table class="table">
								<thead>
									<tr>
										<th>Quiz Name</th>
										<th>Rating</th>
										<th>Category</th>
										<th>Times Taken</th>
										<th>Creation Time</th>
										
									</tr>
								</thead>
								<tbody>
									<%
										for (Quiz quiz : createdQuizzes) {
									%>
									<tr>
										<td><a
											href="QuizInfo.jsp?id=<%=quiz.getQuizID()%>"><%=quiz.getQuizName()%></a></td>
										<td><%=quiz.getQuizRating()%></td>
										<td><%=quiz.getQuizCategory()%></td>
										<td><%=quiz.getTimesTaken() %></td>
										<td><%=quiz.getQuizTimestampString() %></td>
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
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quizzes Created by <%=shownDisplayName %> <span class="badge"><%=createdQuizzes.size()%></span></div>
						<div class="panel-body"><%=shownDisplayName %> has not created any quizzes</div>
					</div>
				</div>
			</div>
<%
		}
		
		ArrayList<Quiz> takenQuizzes = QuizManager.getQuizzesTaken(shownUsername);
		if (takenQuizzes == null) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quizzes Taken by <%=shownDisplayName %></div>
						<div class="panel-body">Error getting quizzes taken by <%=shownDisplayName %></div>
					</div>
				</div>
			</div>
<%
		} else if (takenQuizzes.size() > 0) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quizzes Taken by <%=shownDisplayName %> <span class="badge"><%=takenQuizzes.size()%></span></div>
							<table class="table">
								<thead>
									<tr>
										<th>Quiz Name</th>
										<th>Rating</th>
										<th>Category</th>
										<th>Times Taken</th>
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
										<td><%=quiz.getQuizTimestampString() %></td>
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
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quizzes Taken by <%=shownDisplayName %> <span class="badge"><%=takenQuizzes.size()%></span></div>
						<div class="panel-body"><%=shownDisplayName %> has not taken any quizzes</div>
					</div>
				</div>
			</div>
<%
		}
		
		ArrayList<QuizAttempt> attempts = QuizManager.getQuizAttemptsForUser(shownUsername);
		if (attempts == null) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quiz Attempts fo <%=shownDisplayName %></div>
						<div class="panel-body">Error getting quiz attempts for <%=shownDisplayName %></div>
					</div>
				</div>
			</div>
<%
		} else if (attempts.size() > 0) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quiz Attempts for <%=shownDisplayName %> <span class="badge"><%=attempts.size()%></span></div>
							<table class="table">
							<thead>
								<tr>
									<th>Quiz Name</th>
									<th>Score</th>
									<th>Duration</th>
									<th>Time</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (QuizAttempt attempt : attempts) {
										Quiz quiz = QuizManager.getQuizById("" + attempt.getQuizID());
								%>
								<tr>
									<td><a
										href="QuizInfo.jsp?username=<%=quiz.getQuizID()%>"><%=quiz.getQuizName()%></a></td>
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
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Quiz Attempts for <%=shownDisplayName %> <span class="badge"><%=attempts.size()%></span></div>
						<div class="panel-body"><%=shownDisplayName %> has not attempted any quizzes</div>
					</div>
				</div>
			</div>
<%
		}
		
		
		
		
		ArrayList<Account> friends = AccountManager.getFriendsForUser(shownUsername);
%>
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<div class="panel panel-primary">
<%
		if (friends == null) {
%>
					<div class="panel-heading">Friends</div>
						<div class="panel-body">Error getting friends for <%=shownDisplayName %></div>
<%
		} else if (friends.size() > 0) {
%>
					<div class="panel-heading">Friends <span class="badge"><%=friends.size()%></span></div>
					<table class="table">
						<thead>
							<tr>
								<th>Name</th>
								<th>Username</th>
								<th>Type</th>
							</tr>
						</thead>
						<tbody>
							<%
								for (Account friend : friends) {
							%>
							<tr>
								<td><a
									href="showProfile.jsp?username=<%=friend.getUsername()%>"><%=friend.getDisplayName()%></a></td>
								<td><%=friend.getUsername()%></td>
								<td><%=friend.getTypeString()%></td>
							</tr>
							<%
								}
							%>
						</tbody>
					</table>
<%
		} else {
%>
					<div class="panel-heading">Friends <span class="badge" align="right"><%=friends.size() %></span></div>
						<div class="panel-body"><%=shownDisplayName %> does not have any friends yet</div>
<%			
		}
			%>
			</div>
		</div>
	</div>	
<%
		ArrayList<Achievement> achievements = QuizManager.getAchievementsForUser(shownUsername);
		if (achievements == null) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Achievements</div>
							<div class="panel-body">Error getting achievements for <%=shownDisplayName %></div>
					</div>
				</div>
			</div>
<%
		} else if (achievements.size() > 0) {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Achievements <span class="badge" align="right"><%=achievements.size() %></span></div>
						<table class="table">
							<thead>
								<tr>
									<th><span class="glyphicon glyphicon-th-list"></span></th>
									<th>Name</th>
									<th>Description</th>
									<th>Time</th>
								</tr>
							</thead>
							<tbody>
								<%
									for (Achievement a : achievements) {
										Achievement.Type type = a.getType();
								%>
								<tr>
									<td><span class="<%=a.getAchievementIconString() %>"></span></td>
									<td><%=a.getNameForType(type) %></td>
									<td><%=a.getDescriptionForType(type) %></td>
									<td><%=a.getTimestamp().toString() %></td>
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
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Achievements <span class="badge" align="right"><%=achievements.size() %></span></div>
							<div class="panel-body"><%=shownDisplayName %> does not have any achievements yet</div>
					</div>
				</div>
			</div>
<%	
		}
	}
}
%>
</body>
</html>