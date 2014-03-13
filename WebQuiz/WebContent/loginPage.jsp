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
<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
	  <form action="HandleLoginServlet" method="post" class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" name="username" class="form-control" placeholder="Username">
          <input type="password" name="password" class="form-control" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-default">Login</button>
      </form>
      <form action="CreateAccount.html" method="get" class="navbar-form navbar-left">
      	<button type="submit" class="btn btn-default">Create Account</button>
      </form>
    </div>
   </div>
</nav>

<div class="page-header">
	<div class="row">
		<h1 align = "center">
			Welcome to Quizville
		</h1>
	</div>
</div>

 <div class="panel-body" align="center">
 	<img src="images/quiz2.jpg" width="347" height="310"/>
 		<p></p>
 		<p>Quick Links: 
	  <a href="http://www.stanford.edu/">Stanford University</a> · 
	  <a href="http://www.stanford.edu/class/cs108/">CS 108</a></p>
	<p></p>
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