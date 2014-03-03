<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="objects.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<p>Welcome, <%=thisAccount.getDisplayName() %>. Make an Announcement:</p>
	<form method="POST" action="CreateAnnouncementServlet">
	<p>Announcement:</p>
	<textarea cols="60" rows="5" name="announcementField">Type your announcement here.</textarea>
	<input type="submit" value="Send"/>
	</form>
</body>
<%
}
%>
</html>