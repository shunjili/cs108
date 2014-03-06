<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="objects.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accounts Index</title>
</head>
<%
	Account thisAccount = (Account) session
		.getAttribute("loggedAccount");
%>
<body>
	<% if(thisAccount == null) { %>
	<p><a href="loginPage.jsp">Log in to Quizville</a></p>
	<% } else { %>
	<p><a href="ViewMyAccount.jsp">Return to account view</a></p>
	<%} %>
	<h2>This is a list of all accounts. Usernames are in parentheses</h2>
	<ul>
		<%
			for(Account acct : AccountManager.getAllAccounts()){
		%>
		<li><a href="showProfile.jsp?username=<%=acct.getUsername()%>"><%=acct.getDisplayName() %></a> (<%=acct.getUsername()%>) - <%=acct.getTypeString()%></li>
		<%-- <li><%=acct.getDisplayName()%> (<%=acct.getUsername()%>) - <%=acct.getTypeString()%></li> --%>
		<%
			}
		%>
	</ul>
</body>
</html>