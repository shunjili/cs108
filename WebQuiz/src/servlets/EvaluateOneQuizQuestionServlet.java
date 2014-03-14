package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.Account;
//import objects.HtmlEscape;
import objects.Question;
import objects.QuestionManager;
import objects.QuizAttempt;
import objects.QuizManager;
import servlets.*;

/**
 * Servlet implementation class EvaluateOneQuizQuestionServlet
 */
@WebServlet("/EvaluateOneQuizQuestionServlet")
public class EvaluateOneQuizQuestionServlet extends HttpServlet {
	public static final String Score_Str = "Score";
	public static final String CurQuestion_Str = "Questions";
	public static final String TempScore_Str = "tempScore";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluateOneQuizQuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		String quiz_id = request.getParameter("quiz_id");
		//ArrayList<Question> rawQuestions = QuestionManager.getQuestionsForQuiz(quiz_id);//this may change if it is random order
		
		ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute(CurQuestion_Str);
		
		ArrayList<Question> questionList = (ArrayList<Question>) session.getAttribute(EvaluateQuizServlet.Questions_Str);		
		if(questionList == null){//record sequence
			questionList = new ArrayList<Question>();
		}
		
		//questionList.add(questions.get(0));
		
		Integer tempScore = (Integer)session.getAttribute(TempScore_Str);
		if(tempScore == null){
			tempScore = 0;
		}
		if(questions.size() > 1){// if there are still questions
			HashMap<Question, ArrayList<String>> questionAnswerHash = (HashMap<Question, ArrayList<String>>) session.getAttribute("questionAnswerHash");
			if(questionAnswerHash == null){//record sequence
				questionAnswerHash = new HashMap<Question, ArrayList<String>>();
			}
			//if(questions != null){
			//	for(int i = 0 ; i < 1; i++){
					Question question = questions.get(0);
					String answer = request.getParameter(question.getQuestionID());
					ArrayList<String> answers= new ArrayList<String>();
					if(answer == null || answer.isEmpty()){ 
						answer = null;
						answers.add(answer);
					}else{
						String[] ans = (HtmlEscape.escape(answer)).split("##");	
//						String[] ans = ((answer)).split("##");	
						for(int j = 0; j < ans.length;j++){
							answers.add(ans[j]);
						}
					}
					questionAnswerHash.put(question, answers);
					questionList.add(question);
					if(question.isCorrect(answers)){
						tempScore += question.getScore();
					}
					
			//	}
			//}
			questions.remove(0);
			session.setAttribute(TempScore_Str, tempScore);
			session.setAttribute(EvaluateQuizServlet.Hash_Str, questionAnswerHash);
			session.setAttribute(EvaluateQuizServlet.Questions_Str, questionList);
			session.setAttribute(CurQuestion_Str, questions);		
			String returnURL = String.format("quizOnePage.jsp", quiz_id);
			request.getRequestDispatcher(returnURL).forward(request, response);
		}else{
		
			HashMap<Question, ArrayList<String>> questionAnswerHash = (HashMap<Question, ArrayList<String>>) session.getAttribute("questionAnswerHash");
			if(questionAnswerHash == null){//record sequence
				questionAnswerHash = new HashMap<Question, ArrayList<String>>();
			}
			Question question = questions.get(0);
			String answer = request.getParameter(question.getQuestionID());
			ArrayList<String> answers= new ArrayList<String>();
			if(answer == null|| answer.isEmpty()) answer = "You did not answer this question";
			if(answer.split("#").length >1){
				answers =  (ArrayList<String>) Arrays.asList(answer.split("#"));
			}else{
				answers.add(answer);
			}
			questionAnswerHash.put(question, answers);
			questionList.add(question);
			if(question.isCorrect(answers)){
				tempScore += question.getScore();
			}
			questions.remove(0);//change to null
			
			
			Timestamp startingTime = (Timestamp) session.getAttribute(EvaluateQuizServlet.StartingTime_Str);
			long duration = 0;
			if(startingTime != null){
				// the duration is in minutes
				Timestamp now = new Timestamp(new Date().getTime());
				duration = (now.getTime()- startingTime.getTime())/1000;
				//System.out.println("test duration is " + duration);
				session.setAttribute(EvaluateQuizServlet.Duration_str, duration);
				session.setAttribute(EvaluateQuizServlet.StartingTime_Str, null);
			}
			
			//(int quiz_id, String username, int score, Timestamp start_time, long duration)
			Account user = ((Account) session.getAttribute("loggedAccount"));
			//String user = "foo";
			//System.out.println(user.getUsername());
			//System.out.println(quiz_id);
			QuizAttempt tempAttempt = new QuizAttempt(Integer.parseInt(quiz_id), user.getUsername(), tempScore, startingTime, duration );
			if(!QuizManager.storeAttempt(tempAttempt))
				System.out.print("store err");

			session.setAttribute(Score_Str, tempScore);
			session.setAttribute(EvaluateQuizServlet.Hash_Str, questionAnswerHash);
			session.setAttribute(EvaluateQuizServlet.Questions_Str, questionList);
			session.setAttribute(CurQuestion_Str, null);	
			session.setAttribute(TempScore_Str, null);	
			
			String returnURL = String.format("reviewQuizResult.jsp", quiz_id);
			request.getRequestDispatcher(returnURL).forward(request, response);
		}
		
	}

}
