package servlets;

import java.io.IOException;
import objects.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendFriendRequestServlet
 */
@WebServlet("/SendFriendRequestServlet")
public class SendFriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendFriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("sendFriendRequest.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requester = request.getParameter("requester");
		String requested = request.getParameter("requested");
		String message = request.getParameter("message");
		FriendRequest r = new FriendRequest(requester, requested, message);
		boolean success = AccountManager.sendFriendRequest(r);
		if (success) {
			request.getRequestDispatcher("showProfile.jsp?username=" + requested).forward(request, response);
		} else {
			request.getRequestDispatcher("DatabaseQueryFail.html").forward(request, response);
		}
	}

}
