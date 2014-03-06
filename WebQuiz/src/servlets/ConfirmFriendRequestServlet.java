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
 * Servlet implementation class ConfirmFriendRequestServlet
 */
@WebServlet("/ConfirmFriendRequestServlet")
public class ConfirmFriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmFriendRequestServlet() {
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
		String requester = request.getParameter("requester");
		String requested = request.getParameter("requested");
		boolean success = AccountManager.confirmFriendRequest(requester, requested);
		if (success) {
			request.getRequestDispatcher("showProfile.jsp?username=" + requested).forward(request, response);
		} else {
			request.getRequestDispatcher("DatabaseQueryFail.html").forward(request, response);
		}
	}

}
