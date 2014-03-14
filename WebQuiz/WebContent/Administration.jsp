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