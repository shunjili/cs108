<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="objects.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String requester = request.getParameter("requester");
	String requested = request.getParameter("requested");
	Account friendAccount = AccountManager.getAccountByUsername(requested);
%>
<title>Requesting Friend</title>
</head>
<body>
<h3>Request to be friends with <%=friendAccount.getDisplayName()%></h3>
<form action = "SendFriendRequestServlet" method="post">
	<input type="hidden" name="requester" value="<%= requester %>">
	<input type="hidden" name="requested" value="<%= requested %>">
<!-- 	<p>Send a Message:<input type="text" name="message"/></p> -->
	<p>Send a Message:</p><textarea cols="60" rows="5" name="message">Type your message here.</textarea>
	<p><button type="submit">Send Friend Request</button></p>
</form>
<h2 align = "center"><a href="showProfile.jsp?username=<%=friendAccount.getUsername()%>">Back to <%=friendAccount.getDisplayName() %>'s profile</a></h2>

</body>
</html>