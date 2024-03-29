package servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.QuizManager;

/**
 * Servlet implementation class ReviewSubmissionServlet
 */
@WebServlet("/ReviewSubmissionServlet")
public class ReviewSubmissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewSubmissionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO implemented rating submission mechanism
		int rating = Integer.parseInt(request.getParameter("rating"));
		String quiz_id = request.getParameter("quiz_id");
		if(QuizManager.addReviewForQuiz(quiz_id, rating)){
			System.out.println("rating stored successfully");
		}else{
			System.out.println("rating failed to be stored");
		}
		request.getRequestDispatcher("ViewMyAccount.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
