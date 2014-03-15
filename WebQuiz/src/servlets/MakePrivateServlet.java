package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Account;
import objects.AccountManager;

/**
 * Servlet implementation class MakePrivateServlet
 */
@WebServlet("/MakePrivateServlet")
public class MakePrivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakePrivateServlet() {
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
		String username = request.getParameter("username");
		AccountManager.makePrivate(username);
		Account loggedAccount = (Account)(request.getSession().getAttribute("loggedAccount"));
		if (loggedAccount != null && loggedAccount.getUsername().equals(username)) {
			loggedAccount.setPrivacy(true);
			request.getSession().setAttribute("loggedAccount", loggedAccount);
		}
		request.getRequestDispatcher("showProfile.jsp?username=" + username).forward(request, response);
		return;
	}

}
