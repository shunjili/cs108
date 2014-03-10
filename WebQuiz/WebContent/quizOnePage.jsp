<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@
page import="objects.*, java.util.ArrayList"
%>
<%
String quiz_id = request.getParameter("id");
ArrayList<Question> Questions = QuestionManager.getQuestionsForQuiz(quiz_id);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<style type="text/css"></style>
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<link rel="stylesheet" type="text/css" href="./css/jcarousel.connected-carousels.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script type="text/javascript" src="./js/jquery.jcarousel.js"></script>
	<script type="text/javascript" src="./js/jcarousel.connected-carousels.js"></script>
	<link rel="stylesheet"
		href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet"
		href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<link type="text/css" rel="stylesheet" href="chrome-extension://cpngackimfmofbokmjmljamhdncknpmg/style.css"><script type="text/javascript" charset="utf-8" src="chrome-extension://cpngackimfmofbokmjmljamhdncknpmg/js/page_context.js"></script>
<title>Single Page</title>
</head>
<body screen_capture_injected="true" style="">

	<div class="wrapper">
        <h1>Welcome to Questions</h1>           
        <p>This example shows how to connect two carousels together so that one carousels acts as a navigation for the other.</p>   
        <div class="connected-carousels">
             <div class="stage">     	
             	<%if (Questions != null){ %>
               		<form action="EvaluateQuizServlet" method ="post">
						<input type="hidden" name="quiz_id" value="<%=quiz_id %>">
							<div class="carousel carousel-stage" data-jcarousel="true">
								<ul>
								<%for (int i = 0; i < Questions.size(); i ++){ %>
		  							<li>
		  							Question # <%= i+1 %>
		  							<%= Questions.get(i).getHTML(false) %>
		  							</li>	
								<%} %>
								</ul>
							</div>
							<a href= <%= "\"./quizOnePage.jsp?id=" + quiz_id + "\""%> class="prev prev-stage" data-jcarouselcontrol="true"><span> < </span></a>
                   			<a href= <%= "\"./quizOnePage.jsp?id=" + quiz_id + "\""%> class="next next-stage" data-jcarouselcontrol="true"><span> > </span></a>
		 					<button type="submit" class="btn btn-default">Submit</button>
						</form>							
				<%}else{ %>
					<p>You do not have a quiz for this id or you dont have any questions in this quiz!</p>
				<%} %>   
        	</div>
    	</div>
	</div>
</body>
</html>