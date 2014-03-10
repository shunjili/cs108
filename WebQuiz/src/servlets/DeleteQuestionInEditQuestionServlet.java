package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.QuestionManager;

/**
 * Servlet implementation class DeleteQuestionInEditQuestionServlet
 */
@WebServlet("/DeleteQuestionInEditQuestionServlet")
public class DeleteQuestionInEditQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuestionInEditQuestionServlet() {
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
		String question_id = request.getParameter("question_id");
		if(QuestionManager.deleteQuestion(question_id, quiz_id)){
			System.out.println("Success delete question");
		}else{
			System.out.println("Failed to delte the questions");
		}
		String returnURL = String.format("EditQuestions.jsp?id=%s", quiz_id);
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

}
