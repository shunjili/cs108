package servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.Account;
import objects.AccountManager; 

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String INVALID_USERNAME = "invalid_username";
	public static final String INVALID_PASSWORD = "invalid_password";
	public static final String PASSWORDS_DO_NOT_MATCH = "passwords_do_not_match";

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
		String displayName = request.getParameter("displayName");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		boolean success = false;
		
		//first, check if passwords are equal
		if(!password1.equals(password2)) {
			//if not, set success = false, and forward to the result page immediately
			request.setAttribute("success", success);
			request.getRequestDispatcher("CreateAccountUnsuccessful.html").forward(request, response);
			return;
		} else if (password1.equals("")) {
			request.setAttribute("success", success);
			request.getRequestDispatcher("CreateAccountUnsuccessful.html").forward(request, response);
			return;
		} else if (username.equals("")) {
			request.setAttribute("success", success);
			request.getRequestDispatcher("CreateAccountUnsuccessful.html").forward(request, response);
			return;
		}

		//now, try storing the account
		if (displayName == null || displayName.equals("")) {
			displayName = username;
		}
		Account newAccount = new Account(username, displayName, Account.Type.USER, false);
		success = AccountManager.storeNewAccount(newAccount, password1);
		if (success) {
			Account loggedAccount = AccountManager.getAccountLogin(username, password1);
			request.setAttribute("loggedAccount", loggedAccount);
			HttpSession session = request.getSession();
			session.setAttribute("loggedAccount", loggedAccount);
			request.getRequestDispatcher("ViewMyAccount.jsp").forward(request, response);
		} else {
			request.setAttribute("success", success);
			request.getRequestDispatcher("CreateAccountUnsuccessful.html").forward(request, response);
			return;
		}
	}
}