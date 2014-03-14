package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.Account;
import objects.HtmlEscape;
import objects.Question;
import objects.QuestionManager;
import objects.QuizAttempt;
import objects.QuizManager;

/**
 * Servlet implementation class PracticeModeServlet
 */
@WebServlet("/PracticeModeServlet")
public class PracticeModeServlet extends HttpServlet {
	public static final String Hash_Str = "questionAnswerHash";
	public static final String Questions_Str = "questionList";
	public static final String StartingTime_Str = "startingTime";
	public static final String Duration_str = "duration";

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PracticeModeServlet() {
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
		
		//the user dont have to login in in practice mode
		
		
		Account loggedAccount = ((Account) request.getSession().getAttribute("loggedAccount"));
		if (loggedAccount == null) {
			request.getRequestDispatcher("loginPage.jsp").forward(request, response);
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
					String[] ans = (HtmlEscape.escape(answer)).split("##");	
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
		QuizManager.updatePracticeAchievement(loggedAccount.getUsername());
		HttpSession session = request.getSession();
		Timestamp startingTime = (Timestamp) session.getAttribute(EvaluateQuizServlet.StartingTime_Str);
		long duration = 0;
		if(startingTime != null){
			// the duration is in minutes
			Timestamp now = new Timestamp(new Date().getTime());
			duration = (now.getTime()- startingTime.getTime())/60000;
			//System.out.println("test duration is " + duration);
			session.setAttribute(Duration_str, duration);
			//store the quiz attempt before reseting the start time
			session.setAttribute(StartingTime_Str, null);
		}
		
		QuizManager.updatePracticeAchievement(loggedAccount.getUsername());
		
		session.setAttribute(EvaluateOneQuizQuestionServlet.Score_Str, score);
		session.setAttribute(Hash_Str, questionAnswerHash);
		session.setAttribute(Questions_Str, questionList);
		String returnURL = String.format("reviewQuizResult.jsp?quiz_id=%s", quiz_id);
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

}
