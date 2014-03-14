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
%>
		<p>Please <a href="loginPage.jsp">login</a> to Quizville</p>
<%
	} else {
%>
		<%@include file="navbar.html" %>
		<div class="page-header">
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<h1>
						Administration
					</h1>
				</div>
			</div>
		</div>
<%
		if (thisAccount.getType() != Account.Type.ADMIN) {
%>
			<p>You do not have access to this page, since you are not an Administrator.</p>
<%
		} else {
%>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-7">
					<div class="panel panel-primary">
						<div class="panel-heading">Perform administrative actions</div>
						<div class="panel-body">
							<ul class="nav nav-tabs">
				  			<li class="active"><a href="#users" data-toggle="tab">Users</a></li>
				  			<li><a href="#quizzes" data-toggle="tab">Quizzes</a></li>
				  			<li><a href="#announcements" data-toggle="tab">Announcements</a></li>
				  			<li><a href="#stats" data-toggle="tab">Statistics</a></li>
							</ul>
							
							<div class="tab-content">
							<br>
								<div class="tab-pane active" id="users">
				    				<table class="table">
				    					<thead>
											<tr>
												<th>Name</th>
												<th>Username</th>
												<th>Type</th>
												<th>Disable Account</th>
												<th>Make Admin</th>
											</tr>
										</thead>
										<tbody>
<%
											ArrayList<Account> accounts = AccountManager.getAllAccounts();
											if (accounts != null) {
												for (Account acct : accounts) {
													Account.Type type = acct.getType();
%>
											<tr>
												<td><a
													href="showProfile.jsp?username=<%=acct.getUsername()%>"><%=acct.getDisplayName()%></a></td>
												<td><%=acct.getUsername()%></td>
												<td><%=acct.getTypeString()%></td>
												<td>
<%
													if (type == Account.Type.ADMIN) {
%>
													N/A
<%
													} else {
%>
													<form action="DisableAccountServlet" method="post">
														<input type="hidden" name="username" value="<%=acct.getUsername() %>"/>
														<button type="submit" class="btn btn-default">Disable</button>
													</form>
<%
													}
%>
												</td>
												<td>
<%
													if (type == Account.Type.ADMIN) {
%>
													N/A
<%
													} else {
%>
													<form action="MakeUserAdminServlet" method="post">
														<input type="hidden" name="username" value="<%=acct.getUsername() %>"/>
														<button type="submit" class="btn btn-default">Make Admin</button>
													</form>
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
								
								<div class="tab-pane" id="quizzes">
									<table class="table">
										<thead>
											<tr>
												<th>Quiz Name</th>
												<th>Category</th>
												<th>Creator</th>
												<th>Delete</th>
											</tr>
										</thead>
										<tbody>
<%
											ArrayList<Quiz> quizzes = QuizManager.getAllQuizzes();
											if (quizzes != null) {
												for (Quiz quiz : quizzes) {
%>
											<tr>
												<td><a
													href="QuizInfo.jsp?id=<%=quiz.getQuizID()%>"><%=quiz.getQuizName()%></a></td>
												<td><%=quiz.getQuizCategory()%></td>
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
												<td>
													<form action="DeleteQuizServlet" method="post">
														<input type="hidden" name="quiz_id" value="<%=quiz.getQuizID()%>"/>
														<button type="submit" class="btn btn-default">Delete</button>
													</form>
												</td>
											</tr>
<%
												}
											}
%>
										</tbody>
									</table>
								</div>
								
								<div class="tab-pane" id="announcements">
									<form action="CreateAnnouncementServlet" method="post">
										<p>Send an announcement:</p>
										<textarea name="announcementField" cols="60" rows="5" class="form-control" placeholder="Type your announcement here"></textarea>
										<p></p>
										<button type="submit" class="btn btn-default">Send</button>
									</form>
									<br>
									<table class="table">
										<thead>
											<tr>
												<th>Name</th>
												<th>Announcement</th>
												<th>Time</th>
												<th>Delete</th>
											</tr>
										</thead>
										<tbody>
<%
											ArrayList<AnnouncementManager.Announcement> announcements = AnnouncementManager.getAllAnnouncements();
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
														<td>
															<form action="DeleteAnnouncementServlet" method="post">
																<input type="hidden" name="username" value="<%=username%>"/>
																<input type="hidden" name="text" value="<%=text %>"/>
																<button type="submit" class="btn btn-default">Delete</button>
															</form>
														</td>
													</tr>
<%
												}
											}
%>
										</tbody>
									</table>
								</div>
								
								
								<div class="tab-pane" id="stats">
									<table class="table">
										<thead>
											<tr>
												<th>Statistic</th>
												<th>#</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>Number of Users (Active & Disabled)</td>
												<td><%=AccountManager.getTotalAccountCount() %></td>
											</tr>
											<tr>
												<td>Number of Quizzes</td>
												<td><%=QuizManager.getNumQuizzes() %></td>
											</tr>
											<tr>
												<td>Number of Quiz Attempts</td>
												<td><%=QuizManager.getTotalAttempts() %></td>
											</tr>
											<tr>
												<td>Number of Friendships</td>
												<td><%=AccountManager.getNumFriendships() %></td>
											</tr>
											
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
	}

%>
</body>
</html>