package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import objects.*;

/**
 * Servlet implementation class SendMessageServlet
 */
@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMessageServlet() {
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
		String receiver = request.getParameter("toUsername");
		String messageText = request.getParameter("messageField");
		if (messageText == null) {
			messageText = "";
		}

		Account thisAccount = (Account) request.getSession().getAttribute("loggedAccount");
		if (thisAccount == null) {
			request.getRequestDispatcher("loginPage.jsp").forward(request, response);
			return;
		}
		
		Message newMsg = new Message(thisAccount.getUsername(), receiver, messageText);
		boolean result = MessageManager.sendMessage(newMsg);
		if(result == true) {
			request.getRequestDispatcher("showProfile.jsp?username="+ receiver).forward(request, response);
			return;
		} else {
			request.getRequestDispatcher("messageFailed.html").forward(request, response);
			return;
		}

	}

}
