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
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAccountServlet() {
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
		
		//grab the necessary parameters
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		boolean success = false;
		
		//first, check if passwords are equal
		if(!password1.equals(password2)) {
			//if not, set success = false, and forward to the result page immediately
			request.setAttribute("success", success);
			request.getRequestDispatcher("CreateAccountResult.jsp").forward(request, response);
			return;
		}

		//now, try storing the account
		Account newAccount = new Account(username);
		success = AccountManager.storeNewAccount(newAccount, password1);
		request.setAttribute("success", success);
		request.setAttribute("newAccount", newAccount);
		request.getRequestDispatcher("CreateAccountResult.jsp").forward(request, response);


	}
}