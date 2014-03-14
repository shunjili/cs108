package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.*;

/**
 * Servlet implementation class UpdateQuizServlet
 */
@WebServlet("/UpdateQuizServlet")
public class UpdateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuizServlet() {
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
		String quiz_id = request.getParameter("quiz_id").replace("\"", "\\\"");
		String tag = request.getParameter("tag");//then split by #
		String[] temp = (HtmlEscape.escape(tag)).split("#");
		int len = temp.length;
		System.out.print("This is length");
		System.out.println(len);
		for(int i = 0; i < len ; i++){
			if(temp[i].equals("")){
				break;
			}
			if(QuizManager.addTagToQuiz(quiz_id, temp[i].trim().toLowerCase())){
				
			}else{
				String returnURL = "ViewMyAccount.jsp";
				request.getRequestDispatcher(returnURL).forward(request, response);
			}
		}
		String returnURL = "ViewMyAccount.jsp";
		request.getRequestDispatcher(returnURL).forward(request, response);
		
	}

}
