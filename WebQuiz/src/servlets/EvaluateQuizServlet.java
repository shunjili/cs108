package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.Date;

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
/**
 * Servlet implementation class EvaluateQuizServlet
 */
@WebServlet("/EvaluateQuizServlet")
public class EvaluateQuizServlet extends HttpServlet {
	public static final String Hash_Str = "questionAnswerHash";
	public static final String Questions_Str = "questionList";
	public static final String StartingTime_Str = "startingTime";
	public static final String Duration_str = "duration";

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluateQuizServlet() {
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
		// TODO increament quiz taken count
		Account loggedAccount = ((Account) request.getSession().getAttribute("loggedAccount"));
		if (loggedAccount == null) {
			request.getRequestDispatcher("loginPage.jsp").forward(request, response);
			return;
		}
				
		String quiz_id = request.getParameter("quiz_id");
		ArrayList<Question> questions = QuestionManager.getQuestionsForQuiz(quiz_id);
		int score = 0;
		HashMap<Question, ArrayList<String>> questionAnswerHash = new HashMap<Question, ArrayList<String>>();
		ArrayList<Question> questionList = new ArrayList<Question>();
		if(questions != null){
			for(int i =0 ; i < questions.size(); i++){
				Question question = questions.get(i);
				String answer = request.getParameter(question.getQuestionID());
				ArrayList<String> answers= new ArrayList<String>();
				if(answer == null || answer.isEmpty()){ 
					answer = null;
					answers.add(answer);
				}else{
					String[] ans = (HtmlEscape.escape(answer)).split("#");	
					for(int j = 0; j < ans.length;j++){
						answers.add(ans[j]);
					}
				}
				//answers.add(answer);
				questionAnswerHash.put(question, answers);
				questionList.add(question);
				if(question.isCorrect(answers)){
					score += question.getScore();
				}
				
			}
			//System.out.println("The total score is " + score);
		}
		HttpSession session = request.getSession();
		Timestamp startingTime = (Timestamp) session.getAttribute(EvaluateQuizServlet.StartingTime_Str);
		long duration = 0;
		if(startingTime != null){
			// the duration is in minutes
			Timestamp now = new Timestamp(new Date().getTime());
			duration = (now.getTime()- startingTime.getTime())/1000;
			//System.out.println("test duration is " + duration);
			session.setAttribute(Duration_str, duration);
			//store the quiz attempt before reseting the start time
			QuizAttempt attempt = new QuizAttempt(Integer.parseInt(quiz_id), loggedAccount.getUsername(),score, startingTime, duration);
			if(QuizManager.storeAttempt(attempt)){
				System.out.println("attempt stored correctly");
			}else{
				System.out.println("attempt failed to be stored");

			}
			session.setAttribute(StartingTime_Str, null);
		}
		session.setAttribute(EvaluateOneQuizQuestionServlet.Score_Str, score);
		session.setAttribute(Hash_Str, questionAnswerHash);
		session.setAttribute(Questions_Str, questionList);
		String returnURL = String.format("reviewQuizResult.jsp?quiz_id=%s", quiz_id);
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

}
