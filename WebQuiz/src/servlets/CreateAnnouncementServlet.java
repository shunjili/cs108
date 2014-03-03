package servlets;
import objects.*;
import objects.AnnouncementManager.Announcement;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Account;

/**
 * Servlet implementation class CreateAnnouncementServlet
 */
@WebServlet("/CreateAnnouncementServlet")
public class CreateAnnouncementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAnnouncementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String announcementText = request.getParameter("announcementField");
		Account thisAccount = (Account) request.getSession().getAttribute("loggedAccount");
		announcementText = thisAccount.getDisplayName() + ": " + announcementText;
		Announcement announcement;
		try {
			announcement = new Announcement(thisAccount.getUsername(), announcementText);
			boolean announcementIsMade = AnnouncementManager.makeAnnouncement(announcement);
			if(announcementIsMade) {
				request.getRequestDispatcher("announcementMade.html").forward(request, response);
			} else {
				request.getRequestDispatcher("announcementFailed.html").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getRequestDispatcher("announcementFailed.html").forward(request, response);
		}

	}

}
