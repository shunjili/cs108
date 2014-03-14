package servlets;
import java.sql.Timestamp;
import java.util.Date;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Account;
//import objects.HtmlEscape;
import objects.Question;
import objects.QuestionManager;

/**
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionServlet() {
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
		Account loggedAccount = ((Account) request.getSession().getAttribute("loggedAccount"));
		String quiz_id = request.getParameter("quiz_id").replace("\"", "\\\"");
//		String question = HtmlEscape.escape(request.getParameter("question"));
//		String answer = HtmlEscape.escape(request.getParameter("answer"));
//		String questionTypeString = request.getParameter("type").replace("\"", "\\\"");
//		String scoreString = HtmlEscape.escape(request.getParameter("score"));
//		String description = HtmlEscape.escape(request.getParameter("description"));
		
		String question = (request.getParameter("question"));
		String answer = (request.getParameter("answer"));
		String questionTypeString = request.getParameter("type").replace("\"", "\\\"");
		String scoreString = (request.getParameter("score"));
		String description = (request.getParameter("description"));
		int score = 0;
		
		if (question == null || question.equals("")||answer == null || answer.equals("")) {
			request.getRequestDispatcher("createQuestions.jsp?id="+quiz_id+"&message=invalid input!").forward(request, response);
			return;
		}
		
		try {
			score = Integer.parseInt(scoreString);
		} catch (NumberFormatException e) {
			System.out.print("The entered score is not a valid number string");
			score = 0;
		}
		if(questionTypeString.equals(Question.MULTIPLE_CHOICE_STR)){
			for(int i = 0 ; i < Question.MAX_NUM_CHOICES; i ++){
//				String choice = HtmlEscape.escape(request.getParameter("choice"+i));
				String choice = (request.getParameter("choice"+i));
				if(choice != null){
					question += "#"+choice;
				}
			}
			try {
				int choiceNumber = Integer.parseInt(answer);
			} catch (NumberFormatException e) {
				// Just in case the user entered a invalid answer choice for the MCQ, we just set the first choice as the correct answer
				answer = "1";
				e.printStackTrace();
			}
			
		}
		Question.Type type = QuestionManager.getTypeForString(questionTypeString);
		
		int index =  Integer.parseInt(request.getParameter("questionIndex"));
		
		Timestamp timeStamp = new Timestamp( new Date().getTime());
		Question toStore = QuestionManager.constructQuestion(type, "dummy_id", question, description, loggedAccount.getUsername(), score, timeStamp);
		if(QuestionManager.storeNewQuestion(toStore, quiz_id, index, answer) >=0){
			System.out.println("Success stored question");
		}else{
			System.out.println("Failed to store the questions");
		}
		String returnURL = String.format("createQuestions.jsp?id=%s", quiz_id);
		request.getRequestDispatcher(returnURL).forward(request, response);
		
	}

}
