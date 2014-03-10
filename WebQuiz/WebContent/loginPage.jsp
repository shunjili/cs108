<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Quizzville</title>

<link rel="shortcut icon" href="images/ico.jpg" /> 
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="./js/cookies.js"></script>
<script type="text/javascript">
        windows.onload = checkCookie();
</script>

</head>
<body>
<h1>Login to Quizzville</h1>
<div>
	<img src="images/quiz2.jpg" width="347" height="310" style="float:left"/>
	<h2 align = "center">
		Take Quiz Anywhere
	</h2>
	<p>&nbsp;</p>
	<form method="POST" action = "HandleLoginServlet">
		<p align = "center">Enter Username:<input type="text" name="username" placeholder="Username"/></p>
		<p align = "center">Enter Password:<input type="text" name="password" placeholder="Password"/></p>
		<p align = "center"><input type="submit" value="login" class="btn btn-primary"/></p>
		<p align = "center"><input name="remember" type="checkbox" id="rememberUser"> Remember me</p>
	</form>
	<h2 align = "center"><a href="CreateAccount.html">Sign up</a></h2>
</div>

<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

	<div>
		<div align = "right">
			  <p>Quick Links</p>
			  <a href="http://www.stanford.edu/">Stanford University</a><br>
			  <a href="http://www.stanford.edu/class/cs108/">CS 108</a><br>
			  
  		</div>
  		 <p align = "center">About us
			    · <a href=#>Shuhui</a>
			    · <a href=#>Steven</a>
			    · <a href=#>Shunji</a>
			    · <a href=#>JB</a>
			    </p>
  		 <p align = "center">Copyright © 2014 WebQuiz , all rights reserved</p>
	</div>
	
	
</body>
</html>