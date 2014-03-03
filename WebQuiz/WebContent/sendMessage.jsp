<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Send a Message</title>
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
	<p>Welcome, <%=thisAccount.getDisplayName()%>. Send a message:</p>
	<form method="POST" action="SendMessageServlet">
	<p>To:<input type="text" name="toField" /></p>
	<p>Message:</p>
	<textarea cols="60" rows="5" name="messageField">Type your message here.</textarea>
	<input type="submit" value="Send"/>
	</form>
</body>
<%
	}
%>
</html>