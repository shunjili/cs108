<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Edit Quiz</title>
</head>
<%
	String quiz_id = request.getParameter("id");
	ArrayList<Question> Questions = QuestionManager.getQuestionsForQuiz(quiz_id);
	int questionIndex = 0;
	if(Questions != null){
		questionIndex = Questions.size();
	}
%>
<script type="text/javascript">
	$(document).ready(function(){
		<%if (Questions != null){%>
			<%for (int i = 0; i < Questions.size(); i ++){ %>
				$(<%="\"#qu"+ Integer.toString(i) + "\""%>).hide();
				$(<%="\"#edit"+ Integer.toString(i) + "\""%>).click(function(){
					$(<%="\"#qu"+ Integer.toString(i) + "\""%>).toggle(1000);
				});	
			<%} %>
		<%} %>
	});
</script>

<body>
	<div class="col-md-7">		
		<%if (Questions != null){%>
			<%for (int j = 0; j < Questions.size(); j ++){ %>	
				<div class="panel panel-primary">
					<div class="panel-heading">
			    		<h3 class="panel-title">Question # <%= j+1 %></h3>
			  		</div>
			  		<%= Questions.get(j).getHTML(true) %>	  		
			  		<button id=<%="edit" + j%> type="button" class="btn btn-default">edit</button>
			  		
			  		<div id = <%="qu" + j%>>
			  			<div class="panel panel-primary">
							<div class="panel-heading">Create a New Question</div>
						 	<div class="panel-body">
						 		<ul class="nav nav-tabs">
			  						<li class="active"><a href="#question_response" data-toggle="tab">Question Response</a></li>
			  						<li><a href="#multiple_choice" data-toggle="tab">Multiple Choice</a></li>
			  						<li><a href="#filling_blank" data-toggle="tab">Filling the blank</a></li>
								</ul>
								<div class="tab-content"><br>
			  						<div class="tab-pane active" id="question_response">
							    		<form action="UpdateQuestionInEditQuestionServlet" method="post">
											<input type = "hidden" name = "quiz_id" value= "<%=quiz_id %>">
											<input type = "hidden" name = "question_id" value= "<%=Questions.get(j).getQuestionID() %>">
											<input type = "hidden" name = "type" value = "<%= Question.QUESTION_RESPONSE_STR %>">
			    							<div class="input-group">
			 									<span class="input-group-addon">Question</span>
			  									<input type="text"  name = "question" class="form-control" placeholder="Question">
											</div><br>
			 			 					<div class="input-group">
			 									<span class="input-group-addon">Score</span>
			  									<input type="text"  name = "score" class="form-control" placeholder="score">
											</div><br>
			 			 					<div class="input-group">
			 									<span class="input-group-addon">Description</span>
			  									<input type="text"  name = "description" class="form-control" placeholder="description">
											</div><br>
											<div class="input-group">
								 				<span class="input-group-addon">Correct Answer</span>
								  				<input type="text" name = "answer" class="form-control" placeholder="Correct Answer">
											</div><br>
								 			 <div class="input-group">
								 				<span class="input-group-addon">Tag(Optional)</span>
								  				<input type="text" name = "tag" class="form-control" placeholder="Question Tag">
											</div><br>
											<button type="submit" class="btn btn-default">Submit</button>
										</form>
			  						</div>
			  						<div class="tab-pane" id="multiple_choice">
			  							<form action="UpdateQuestionInEditQuestionServlet" method="post">
											<input type = "hidden" name = "quiz_id" value= "<%=quiz_id %>">
											<input type = "hidden" name = "question_id" value= "<%=Questions.get(j).getQuestionID() %>">
											<input type = "hidden" name = "type" value = "<%= Question.MULTIPLE_CHOICE_STR %>">
								    		<div class="input-group">
								 				<span class="input-group-addon">Question</span>
								  				<input type="text"  name = "question" class="form-control" placeholder="Question">
											</div><br>
											<% for (int i = 0 ; i < Question.MAX_NUM_CHOICES; i++) {%>
											<div class="input-group">
								 				<span class="input-group-addon">Choice <%=i+1 %></span>
								  				<input type="text"  name = "choice<%=i%>" class="form-control" placeholder="choice1">
											</div><br>
											<%} %>
								 			 <div class="input-group">
								 				<span class="input-group-addon">Score</span>
								  				<input type="text"  name = "score" class="form-control" placeholder="score">
											</div><br>
								 			<div class="input-group">
								 				<span class="input-group-addon">Description</span>
								  				<input type="text"  name = "description" class="form-control" placeholder="description">
											</div><br>
											<div class="input-group">
								 				<span class="input-group-addon">Correct Answer</span>
								  				<input type="text" name = "answer" class="form-control" placeholder="Correct Answer">
											</div><br>
								 			 <div class="input-group">
								 				<span class="input-group-addon">Tag(Optional)</span>
								  				<input type="text" name = "tag" class="form-control" placeholder="Question Tag">
											</div><br>
											<button type="submit" class="btn btn-default">Submit</button>	
						 				</form>
			  						</div>
			  						<div class="tab-pane" id="filling_blank">
			  							<form action="UpdateQuestionInEditQuestionServlet" method="post">
											<input type = "hidden" name = "quiz_id" value= "<%=quiz_id %>">
											<input type = "hidden" name = "question_id" value= "<%=Questions.get(j).getQuestionID() %>">
											<input type = "hidden" name = "type" value = "<%= Question.FILL_IN_BLANK_STR %>">
								    		<div class="input-group">
								 				<span class="input-group-addon">Question</span>
								  				<input type="text"  name = "question" class="form-control" placeholder="Use #### to rerepsent blank to be filled in ">
											</div><br>
								 			<div class="input-group">
								 				<span class="input-group-addon">Score</span>
								  				<input type="text"  name = "score" class="form-control" placeholder="score">
											</div><br>
								 			<div class="input-group">
								 				<span class="input-group-addon">Description</span>
								  				<input type="text"  name = "description" class="form-control" placeholder="description">
											</div><br>
											<div class="input-group">
								 				<span class="input-group-addon">Correct Answer</span>
								  				<input type="text" name = "answer" class="form-control" placeholder="Correct Answer">
											</div><br>
								 			<div class="input-group">
								 				<span class="input-group-addon">Tag(Optional)</span>
								  				<input type="text" name = "tag" class="form-control" placeholder="Question Tag">
											</div><br>
											<button type="submit" class="btn btn-default">Submit</button>
						 				</form>
									</div>
								</div>
							</div>
						</div>
			  		</div>
			  		
			  		<form action="DeleteQuestionInEditQuestionServlet" method="post">
			  			<input type = "hidden" name = "quiz_id" value= "<%=quiz_id %>">
			  			<input type = "hidden" name = "question_id" value= "<%=Questions.get(j).getQuestionID() %>">
			  			<button id=<%="delete" + j%> type="button" class="btn btn-default">delete</button>
			  		</form>
			  				
			  	</div>	
			<%} %><br>
		<%}else{ %>
			<p>You do not have a quiz for this id or you dont have any questions in this quiz!</p>
		<%} %>
		
		
		
		<div class="panel panel-primary">
			 <div class="panel-heading">Create a New Question</div>
			 	<div class="panel-body">
			 		<ul class="nav nav-tabs">
  						<li class="active"><a href="#question_response" data-toggle="tab">Question Response</a></li>
  						<li><a href="#multiple_choice" data-toggle="tab">Multiple Choice</a></li>
  						<li><a href="#filling_blank" data-toggle="tab">Filling the blank</a></li>
					</ul>
					<div class="tab-content"><br>
  						<div class="tab-pane active" id="question_response">
				    		<form action="AddQuestionInEditQuestionServlet" method="post">
								<input type = "hidden" name = "quiz_id" value= "<%=quiz_id %>">
								<input type = "hidden" name = "questionIndex" value = "<%=questionIndex %>">
								<input type = "hidden" name = "type" value = "<%= Question.QUESTION_RESPONSE_STR %>">
    							<div class="input-group">
 									<span class="input-group-addon">Question</span>
  									<input type="text"  name = "question" class="form-control" placeholder="Question">
								</div><br>
 			 					<div class="input-group">
 									<span class="input-group-addon">Score</span>
  									<input type="text"  name = "score" class="form-control" placeholder="score">
								</div><br>
 			 					<div class="input-group">
 									<span class="input-group-addon">Description</span>
  									<input type="text"  name = "description" class="form-control" placeholder="description">
								</div><br>
								<div class="input-group">
					 				<span class="input-group-addon">Correct Answer</span>
					  				<input type="text" name = "answer" class="form-control" placeholder="Correct Answer">
								</div><br>
					 			 <div class="input-group">
					 				<span class="input-group-addon">Tag(Optional)</span>
					  				<input type="text" name = "tag" class="form-control" placeholder="Question Tag">
								</div><br>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>
  						</div>
  						<div class="tab-pane" id="multiple_choice">
  							<form action="AddQuestionInEditQuestionServlet" method="post">
								<input type = "hidden" name = "quiz_id" value= "<%=quiz_id %>">
								<input type = "hidden" name = "questionIndex" value = "<%=questionIndex %>">
								<input type = "hidden" name = "type" value = "<%= Question.MULTIPLE_CHOICE_STR %>">
					    		<div class="input-group">
					 				<span class="input-group-addon">Question</span>
					  				<input type="text"  name = "question" class="form-control" placeholder="Question">
								</div><br>
								<% for (int i = 0 ; i < Question.MAX_NUM_CHOICES; i++) {%>
								<div class="input-group">
					 				<span class="input-group-addon">Choice <%=i+1 %></span>
					  				<input type="text"  name = "choice<%=i%>" class="form-control" placeholder="choice1">
								</div><br>
								<%} %>
					 			 <div class="input-group">
					 				<span class="input-group-addon">Score</span>
					  				<input type="text"  name = "score" class="form-control" placeholder="score">
								</div><br>
					 			<div class="input-group">
					 				<span class="input-group-addon">Description</span>
					  				<input type="text"  name = "description" class="form-control" placeholder="description">
								</div><br>
								<div class="input-group">
					 				<span class="input-group-addon">Correct Answer</span>
					  				<input type="text" name = "answer" class="form-control" placeholder="Correct Answer">
								</div><br>
					 			 <div class="input-group">
					 				<span class="input-group-addon">Tag(Optional)</span>
					  				<input type="text" name = "tag" class="form-control" placeholder="Question Tag">
								</div><br>
								<button type="submit" class="btn btn-default">Submit</button>	
			 				</form>
  						</div>
  						<div class="tab-pane" id="filling_blank">
  							<form action="AddQuestionInEditQuestionServlet" method="post">
								<input type = "hidden" name = "quiz_id" value= "<%=quiz_id %>">
								<input type = "hidden" name = "questionIndex" value = "<%=questionIndex %>">
								<input type = "hidden" name = "type" value = "<%= Question.FILL_IN_BLANK_STR %>">
					    		<div class="input-group">
					 				<span class="input-group-addon">Question</span>
					  				<input type="text"  name = "question" class="form-control" placeholder="Use #### to rerepsent blank to be filled in ">
								</div><br>
					 			<div class="input-group">
					 				<span class="input-group-addon">Score</span>
					  				<input type="text"  name = "score" class="form-control" placeholder="score">
								</div><br>
					 			<div class="input-group">
					 				<span class="input-group-addon">Description</span>
					  				<input type="text"  name = "description" class="form-control" placeholder="description">
								</div><br>
								<div class="input-group">
					 				<span class="input-group-addon">Correct Answer</span>
					  				<input type="text" name = "answer" class="form-control" placeholder="Correct Answer">
								</div><br>
					 			<div class="input-group">
					 				<span class="input-group-addon">Tag(Optional)</span>
					  				<input type="text" name = "tag" class="form-control" placeholder="Question Tag">
								</div><br>
								<button type="submit" class="btn btn-default">Submit</button>
			 				</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>