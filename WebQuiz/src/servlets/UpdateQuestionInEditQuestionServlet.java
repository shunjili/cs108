package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.*;

/**
 * Servlet implementation class UpdateQuestionInEditQuestionServlet
 */
@WebServlet("/UpdateQuestionInEditQuestionServlet")
public class UpdateQuestionInEditQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuestionInEditQuestionServlet() {
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
		String quiz_id = request.getParameter("quiz_id");
		String question = request.getParameter("question");
		String question_id = request.getParameter("question_id");
		String answer = request.getParameter("answer");
		String[] ans = answer.split("#");
		String questionTypeString = request.getParameter("type");
		if(questionTypeString.equals(Question.MULTIPLE_CHOICE_STR)){
			for(int i = 0 ; i < Question.MAX_NUM_CHOICES; i ++){
				String choice = request.getParameter("choice"+i);
				if(choice != null){
					question += "#"+choice;
				}
			}
		}
		//we need creator
		//String creator = ((Account) (request.getSession().getAttribute("loggedAccount"))).getUsername();
		Question.Type type = QuestionManager.getTypeForString(questionTypeString);
		Timestamp timeStamp = new Timestamp( new Date().getTime());
		
		Question toStore = QuestionManager.constructQuestion(type, question_id, question, null, "1", 10, timeStamp);
		if(QuestionManager.updateQuestion(toStore, quiz_id, ans) >= 0){
			System.out.println("Success stored question");
		}else{
			System.out.println("Failed to store the questions");
		}
		String returnURL = String.format("EditQuestions.jsp?id=%s", quiz_id);
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

}
