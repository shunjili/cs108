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
	<script type="text/javascript" src="cookies.js"></script>
<script type="text/javascript">
        windows.onload = checkCookie();
</script>

</head>
<body>
<p>&nbsp;</p>
<div align = "center">
	<form class="form-inline" role="form" method="POST" action = "HandleLoginServlet">
	  <div class="form-group">
	    <label class="sr-only" for="exampleInputEmail2">username</label>
	    <input type="text" class="form-control" name="username" placeholder="Enter Username">
	  </div>
	  <div class="form-group">
	    <label class="sr-only" for="exampleInputPassword2">Password</label>
	    <input type="text" class="form-control" name="password" placeholder="Enter Password">
	  </div>
	  <div class="checkbox">
	    <label>
	      <input name="remember" type="checkbox" id="rememberUser"> Remember me
	    </label>
	  </div>
	  <input type="submit" value="login" class="btn btn-primary"/>
	</form>
</div>

<div>
	<p>&nbsp;</p>
	<div style="float:left">
		<img src="images/quiz2.jpg"  />
	</div>
	
	<p>&nbsp;</p>
	<h2 align = "center">
		Take Quiz Anywhere
	</h2>
	<p>&nbsp;</p>
	<form  method="POST" action = "CreateAccountServlet">
		<p align = "center">Enter Username:<input type="text" name="username"  placeholder="Username"></p>
		<p>&nbsp;</p>
		<p align = "center">Enter Password:<input type="text" name="password1" placeholder="Password"></p>
		<p>&nbsp;</p>
		<p align = "center">Confirm Password:<input type="text" name="password2" placeholder="Confirm Password"></p>
		<h2 align = "center"><input type="submit" value="Sign Up" class="btn btn-primary"/></h2>
	</form>
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
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
  		 <p align = "center">Copyright © 2014 WebQuiz , all rights reserved</p>
	</div>
	
	
</body>
</html>