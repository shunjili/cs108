<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<title>Create Quiz</title>
</head>
<body>
	<div class="page-header">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-7">
				<h1>
					Create Quiz
				</h1>
			</div>
		</div>
	</div>
	<div class="col-md-1">
	</div>
	<div class="col-md-7">
	<div class="panel panel-primary">
		<div class="panel-heading">Create a New Quiz</div>
			<div class="panel-body">
			    <form action="CreateQuizServlet" method="post">
		    		<div class="input-group">
		 				<span class="input-group-addon">Quiz name</span>
		  				<input type="text"  name = "name" class="form-control" placeholder="Quiz name">
					</div>
		 			 <br>
		 			 <div class="input-group">
		 				<span class="input-group-addon">description</span>
		  				<input type="text"  name = "description" class="form-control" placeholder="description">
					</div>
		 			 <br>
		 			 <div class="input-group">
		 				<span class="input-group-addon">category</span>
		 				<span  class="form-control">
		 					<select name="category">
  							<option value="Common Sense">Common Sense</option>
  							<option value="Life Style">Life Style</option>
  							<option value="Academic">Academic</option>
 							<option value="Sport">Sport</option>
						</select>
		 				</span>
		 				
					</div>
		 			 <br>
		 			 <div class="input-group">
		 				<span class="input-group-addon">Tag(Optional)</span>
		  				<input type="text" name = "tag" class="form-control" placeholder="Question Tag">
					</div>
					<br>
					<div class="input-group">
		 				<span class="input-group-addon">Correct Immediately
		  				<input type="checkbox" name="correctImmediately" value = True>
		  				</span>
		  				<span class="input-group-addon">One Page
		  				<input type="checkbox" name="onePage" value = True>
		  				</span>
		  				<span class="input-group-addon">Random Order
		  				<input type="checkbox" name="randomOrder" value = True>
		  				</span>
					</div>
					<br>
					<button type="submit" class="btn btn-default">Start Adding Questions</button>
				</form>
			</div>
	</div>
	
	</body>
</html>