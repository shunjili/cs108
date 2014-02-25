package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.AccountManager;
import objects.Account;

/**
 * Servlet implementation class HandleLoginServlet
 */
@WebServlet("/HandleLoginServlet")
public class HandleLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleLoginServlet() {
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
		//grab our parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//get the loggedAccount. If it's null, we'll know something was wrong. This
		//is checked in the LoginResult.jsp file.
		Account loggedAccount = AccountManager.getAccountLogin(username, password);
		request.setAttribute("loggedAccount", loggedAccount);
		request.getRequestDispatcher("LoginResult.jsp").forward(request, response);
		
	}

}
