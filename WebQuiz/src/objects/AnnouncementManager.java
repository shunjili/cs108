package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AnnouncementManager {

	/**
	 * @param args
	 */
	public static final int maxRecentAnnouncement = 5;
	private static final String USERNAME_COL = "username";
	private static final String ANNOUNCEMENT_COL = "announcement";
	private static final String TIMESTAMP_COL = "time_stamp";

	public static class Announcement {
		String username;
		String announcement;
		public Announcement(String username, String announcementText){
			this.username = username;
			this.announcement = announcementText;
		}
	}
	public static boolean makeAnnouncement(Announcement announcement){
		//check the announcer is valid 
		if(!AccountManager.usernameExists(announcement.username))
			return false;
		
		//try save it in the database
		//Check for user in database
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			//set up DB connection
			Connection con = DriverManager.getConnection
					( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);

			//prepare query
			String query = "INSERT INTO " + MyDBInfo.ANNOUNCEMENTS_TABLE
					+ " (" + USERNAME_COL + "," + ANNOUNCEMENT_COL  + "," + TIMESTAMP_COL + ")"
					+ " VALUES (\"" + announcement.username
					+ "\", \"" + announcement.announcement +"\", "
					+ "NOW()"+ ");";

			//execute the query
			System.out.println(query);
			int returnVal = stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<String> getRecentAnnouncements(int maxLimit){
		//Check for user in database
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			//set up DB connection
			Connection con = DriverManager.getConnection
					( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);

			//prepare query
			String query = "SELECT * FROM " + MyDBInfo.ANNOUNCEMENTS_TABLE;

			//execute the query
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<String> resultList = new ArrayList<String>();

			String announcement;
			while(rs.next()) {
				announcement = rs.getString(ANNOUNCEMENT_COL);
				resultList.add(announcement);
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
