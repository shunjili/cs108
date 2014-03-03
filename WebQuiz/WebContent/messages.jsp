<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@
page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Messages</title>
</head>

<%
	Account thisAccount = (Account) session
			.getAttribute("loggedAccount");

	if(thisAccount == null) {
%>
<body>
	<h2>
		Please <a href="loginPage.jsp">login</a> to view your messages.
	</h2>
</body>
<%
	} else {
		ArrayList<Message> messageList = MessageManager.getReceived(thisAccount.getUsername());		
%>
<body>
	<h2>
		<a href="ViewMyAccount.jsp"> Return to Account Page. </a>
	</h2>
	<h4>Your Messages:</h4>
	<ul>
		<%
			for(Message msg : messageList) {
				Account sender = AccountManager.getAccountByUsername(msg.getSender());
				%>
				<li>From <%= sender.getDisplayName() %> At <%=msg.getTimeString() %>: <%= msg.getMessage() %></li>
		<%
			}
		%>
	</ul>
</body>
<%
	}
%>
</html>