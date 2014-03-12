package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.Question;
import objects.QuestionManager;

/**
 * Servlet implementation class EvaluateQuizServlet
 */
@WebServlet("/EvaluateQuizServlet")
public class EvaluateQuizServlet extends HttpServlet {
	public static final String Hash_Str = "questionAnswerHash";
	public static final String Questions_Str = "questionList";

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
				answers.add(answer);
				if(answer != null){
					questionAnswerHash.put(question, answers);
					questionList.add(question);
					if(question.isCorrect(answers)){
						score += question.getScore();

					}
				}
			}
			//System.out.println("The total score is " + score);
		}
		HttpSession session = request.getSession();
		session.setAttribute("score", score);
		session.setAttribute(Hash_Str, questionAnswerHash);
		session.setAttribute(Questions_Str, questionList);
		String returnURL = String.format("reviewQuizResult.jsp", quiz_id);
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

}
