<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Showing the Quiz</title>
</head>
<%
String quiz_id = request.getParameter("id");
ArrayList<Question> Questions = QuestionManager.getQuestionsForQuiz(quiz_id);
%>
<body>
	<div class="page-header">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-7">
				<h1>
					View the Quiz <small> Good Luck!</small>
				</h1>
			</div>
		</div>
	</div>
	<div class="col-md-1"></div>
		<div class="col-md-2">
			<div>
				Some Information About this quiz
			</div>
			<p>Description of the Quiz</p>
		</div>
	<div class="col-md-7">
			<form action="post">
			
		<%for (int i = 0; i < 3; i ++){ %>

				<div class="panel panel-primary"> <div class="panel-heading">
	    		<h3 class="panel-title">Question # <%= i %></h3>
	  			</div>
	  			<%if(i ==0){ %>
		 			 <div class="panel-body">
		    		This is Question Response Question
		    		<div class="input-group">
		  			<span class="input-group-addon">Your Answer</span>
		 			 <input type="text" class="form-control" placeholder="Username">
					</div>
		  			</div>
	  			<%} %>
	  			<%if(i ==1){ %>
		 			 <div class="panel-body">
		    		This is Mutiple Choice Question
		    		<div class="input-group">
      				<span class="input-group-addon">
        			<input type="radio">
      				</span>
      				<label type="text" class="form-control">This is choice one!</label>
    				</div>
    				<br>
    				<div class="input-group">
      				<span class="input-group-addon">
        			<input type="radio">
      				</span>
      				<label type="text" class="form-control">This is choice two!</label>
    				</div>
    				<br>
    				
    				<div class="input-group">
      				<span class="input-group-addon">
        			<input type="radio">
      				</span>
      				<label type="text" class="form-control">This is choice three!</label>
    				</div>
		  			</div>
	  			<%} %>
	  			<%if(i ==2){ %>
		 			 <div class="panel-body">
		    		This is a filling the blank Question
		    		<div class="input-group">
		  			<span class="input-group-addon">Your Answer</span>
		 			 <input type="text" class="form-control" placeholder="Username">
					</div>
		  			</div>
	  			<%} %>
	  			</div>	
		<%} %>
		 <button type="submit" class="btn btn-default">Submit</button>
	
	</form>


	</div>
	
</body>
</html>