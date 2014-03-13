<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="objects.*"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Create Announcement</title>
</head>
<%
	Account thisAccount = (Account) session
			.getAttribute("loggedAccount");
	if(thisAccount == null) {
%>

<body>
	<h2>
		Please <a href="loginPage.jsp">login</a> to send a message.
	</h2>
</body>
<%
	} else {
%>
<body>
<div class="page-header">
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-7">
			<h1>
				Make an Announcement <small></small>
			</h1>
		</div>
	</div>
</div>
<div class="col-md-1"></div>
		<div class="col-md-7">
			<div class="panel panel-primary">
				<div class="panel-heading">Make an Announcement</div>
				<div class="panel-body">
					<form action="CreateAnnouncementServlet" method="post">
						<textarea name="announcementField" cols="60" rows="5" class="form-control" placeholder="Type your announcement here"></textarea>
						<p></p>
						<button type="submit" class="btn btn-default">Send</button>
					</form>
				</div>
			</div>
			<p><a href="ViewMyAccount.jsp">Homepage</a></p>
		</div>
</body>
<%
}
%>
</html>