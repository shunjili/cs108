<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="objects.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String shownUsername = request.getParameter("username");
	Account shownAccount = AccountManager.getAccountByUsername(shownUsername);
	String shownDisplayName = shownAccount.getDisplayName();
	Account loggedAccount = (Account)session.getAttribute("loggedAccount");
	String loggedUsername = loggedAccount.getUsername();
	String loggedDisplayName = loggedAccount.getDisplayName();
%>
<title><%=shownDisplayName%></title>
</head>
<body>
<h1>
<%=shownDisplayName%>
</h1>
<p>(<%=shownUsername%>)<p>
<%
	if (!shownAccount.equals(loggedAccount)) {
		if (!AccountManager.areFriends(loggedUsername, shownUsername)) {
			if (AccountManager.requestIsPending(loggedUsername, shownUsername)) {
%>
				<h3>Friend Request Pending</h3>
<%
			} else if (AccountManager.requestIsPending(shownUsername, loggedUsername)) {
%>				<p><%= shownDisplayName %> has requested to be your friend</p>
				<form action="ConfirmFriendRequestServlet" method="post">
					<input type="hidden" name="requester" value="<%= shownUsername %>">
					<input type="hidden" name="requested" value="<%= loggedUsername %>">
					<button type="submit">Confirm Friend Request</button>
				</form>
<%			
			} else {
%>
				<form action="SendFriendRequestServlet" method="get">
					<input type="hidden" name="requester" value="<%= loggedUsername %>">
					<input type="hidden" name="requested" value="<%= shownUsername %>">
					<button type="submit">Send Friend Request</button>
				</form>
<%			
			}
		} else {
%>
			<h3>You and <%=shownDisplayName %> are friends</h3>
<%	
		}
	} else {
%>
		<h3>This is your profile</h3>
<%	
	}
%>

<h2>Friends</h2>
<ul>
	<%
		for(Account friend : AccountManager.getFriendsForUser(shownUsername)){
	%>
			<li><a href="showProfile.jsp?username=<%=friend.getUsername()%>"><%=friend.getDisplayName() %></a> (<%=friend.getUsername()%>) - <%=friend.getTypeString()%></li>
	<%
		}
	%>
</ul>
<h4><a href="ViewMyAccount.jsp">Homepage</a></h4>

</body>
</html>