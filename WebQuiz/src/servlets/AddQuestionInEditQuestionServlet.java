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
 * Servlet implementation class AddQuestionInEditQuestionServlet
 */
@WebServlet("/AddQuestionInEditQuestionServlet")
public class AddQuestionInEditQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQuestionInEditQuestionServlet() {
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
		Account loggedAccount = ((Account) request.getSession().getAttribute("loggedAccount"));
		String quiz_id = request.getParameter("quiz_id");
		String question = HtmlEscape.escape(request.getParameter("question"));
		String answer = HtmlEscape.escape(request.getParameter("answer"));
		String[] ans = answer.split("#");
//		String question = (request.getParameter("question"));
//		String answer = (request.getParameter("answer"));	
		
		//String[] ans = answer.split("#");
		String questionTypeString = request.getParameter("type");
		String description = HtmlEscape.escape(request.getParameter("description"));
//		String description = (request.getParameter("description"));
		
		String scoreString = request.getParameter("score").replace("\"", "\\\"");
		int score = 0;
		
		if (question == null || question.equals("")||answer == null || answer.equals("")) {
			request.getRequestDispatcher("EditQuestions.jsp?id="+quiz_id+"&message=invalid input!").forward(request, response);
			return;
		}
		
		try {
			score = Integer.parseInt(scoreString);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.print("The entered score is not a valid number string");
			score = 0;
		}
		
		if(questionTypeString.equals(Question.MULTIPLE_CHOICE_STR)){
			for(int i = 0 ; i < Question.MAX_NUM_CHOICES; i ++){
				String choice = HtmlEscape.escape(request.getParameter("choice"+i));
//				String choice = (request.getParameter("choice"+i));
				if(choice != null){
					question += "#"+choice;
				}
			}
		}
		Question.Type type = QuestionManager.getTypeForString(questionTypeString);
		//String creator = ((Account) (request.getSession().getAttribute("loggedAccount"))).getUsername();
		int index =  Integer.parseInt(request.getParameter("questionIndex"));
		Timestamp timeStamp = new Timestamp( new Date().getTime());
		Question toStore = QuestionManager.constructQuestion(type, "dummy_id", question, description, loggedAccount.getUsername(), score, timeStamp);
		if(QuestionManager.storeNewQuestionMultiple(toStore, quiz_id, index, ans) > 0){
			System.out.println("Success stored question");
		}else{
			System.out.println("Failed to store the questions");
		}
		String returnURL = String.format("EditQuestions.jsp?id=%s", quiz_id);
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

}
