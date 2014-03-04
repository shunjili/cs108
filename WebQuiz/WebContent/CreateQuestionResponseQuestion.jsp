<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
<h1>Welcome to QuizVille?....</h1>

	<form action="CreateQuestionResponseQuestionServlet" method="post" id = "question">
		<p>Your question:
		<input type = "textarea" name="question" rows="4" cols="50" /></p>
		<p>Your answers : 
		<input type = "textarea"  name="answers" rows="4" cols="50" placeholder = "split by ;"/></p>
		<p><input type="submit" value = "submit"/></p>
	</form>
	
	
	<form action="CreateQuestionResponseQuestionServlet" method="post" id = "question">
		<p>Your question:</p>
		<p><textarea name="question" rows="4" cols="50" /></textarea></p>
		<p>Your answers :</p>
		<p><textarea  name="answers" rows="4" cols="50" placeholder = "split by ;"/></textarea></p>
		<p><input type="submit" value = "submit"/></p>
	</form>

</body>
</html>