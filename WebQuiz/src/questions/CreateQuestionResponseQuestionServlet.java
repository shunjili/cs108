package questions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Account;
import questions.Question;
import questions.QuestionResponseQuestion;

/**
 * Servlet implementation class CreateQuestionResponseQuestionServlet
 */
@WebServlet("/CreateQuestionResponseQuestionServlet")
public class CreateQuestionResponseQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionResponseQuestionServlet() {
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
//		ArrayList<Question> questions = (ArrayList<Question>)request.getSession().getAttribute("Questions"); 
//		if(questions == null) {
//			questions = new ArrayList<Question>();
//			request.getSession().setAttribute("Questions", questions);
//		}
		
		String question = request.getParameter("question");
		String answers = request.getParameter("answers");
		Account creator = (Account)request.getSession().getAttribute("loggedAccount");
		String creatorName = creator.getUsername();
		QuestionResponseQuestion newQ = new QuestionResponseQuestion(question, answers,creatorName);
		//haven't check sql quer
		//newQ.addQuestionAndAnswer(newQ.getId());
//		questions.add(newQ);
		 
		RequestDispatcher dispatch = request.getRequestDispatcher("createQuestionSuccess.jsp");
		dispatch.forward(request, response);
	}

}
