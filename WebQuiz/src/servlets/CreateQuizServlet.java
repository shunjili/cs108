package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.*;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
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
		System.out.println("Success stored Quiz");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		ArrayList<Question> Questions = new ArrayList<Question>();
		//String creator = ((Account) (request.getSession().getAttribute("loggedAccount"))).getUsername();
		String creator = "test";
		String category = request.getParameter("category");
		
		ArrayList<String> tags = new ArrayList<String>();
		String tag = request.getParameter("tag");//then split by ;
		String[] temp = tag.split(";");
		int len = temp.length;
		for(int i = 0; i < len ; i++){
			tags.add(temp[i].trim().toLowerCase());
		}
		
		boolean correctImmediately = Boolean.parseBoolean(request.getParameter("correctImmediately"));
		boolean onePage = Boolean.parseBoolean(request.getParameter("onePage"));
		boolean randomOrder = Boolean.parseBoolean(request.getParameter("randomOrder"));
		
		int timesTaken = 0;
		int numReviews = 0;
		double rating = 0;
		Timestamp timeStamp = new Timestamp( new Date().getTime());
		
		Quiz toStore = new Quiz(name, description, Questions, creator, category, tags,correctImmediately, onePage, randomOrder, timesTaken, numReviews, rating, timeStamp);
		
		if(QuizManager.storeQuizQuestionTags(toStore)){
			System.out.println("Success stored Quiz");
		}else{
			System.out.println("Failed to store the Quiz");
		}
		String returnURL = String.format("createQuestions.jsp?id=%s", QuizManager.getQuizId(toStore));
		request.getRequestDispatcher(returnURL).forward(request, response);
	}

}