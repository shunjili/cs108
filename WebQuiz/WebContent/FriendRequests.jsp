<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@
page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friend Requests</title>
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
		ArrayList<FriendRequest> friendRequests = AccountManager.getFriendReqeustsForUser(thisAccount.getUsername());		
%>
<body>
	<h2>
		<a href="ViewMyAccount.jsp"> Homepage. </a>
	</h2>
	<h4>The following people have requested to be your friend:</h4>
	<ul>
		<%
			for(FriendRequest r : friendRequests) {
				Account requester = AccountManager.getAccountByUsername(r.getRequester());
				%>
				<li>
				<form action="ConfirmFriendRequestServlet" method="post">
					<p><a href="showProfile.jsp?username=<%=requester.getUsername()%>"><%=requester.getDisplayName() %></a>
					: <%= r.getMessage() %>
					<input type="hidden" name="requester" value="<%= requester.getUsername() %>">
					<input type="hidden" name="requested" value="<%= thisAccount.getUsername() %>">
					<button type="submit">Confirm Friend Request</button></p>
				</form>
				</li>
		<%
			}
		%>
	</ul>
</body>
<%
	}
%>
</html>