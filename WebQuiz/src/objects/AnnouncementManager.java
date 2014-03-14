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
		Timestamp timestamp;
		public Announcement(String username, String announcementText){
			this.username = username;
			this.announcement = announcementText;
			this.timestamp = null;
		}
		
		public Announcement(String username, String announcementText, Timestamp timestamp) {
			this.username = username;
			this.announcement = announcementText;
			this.timestamp = timestamp;
		}
		
		public String getAnnouncementUsername() {
			return this.username;
		}
		
		public String getAnnouncementText() {
			return this.announcement;
		}
		
		public Timestamp getAnnouncementTimestamp() {
			return this.timestamp;
		}
		
		public String getAnnouncementTimestampString() {
			return this.timestamp.toString();
		}
	}
	
	public static ArrayList<Announcement> getAllAnnouncements() {
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
			ArrayList<Announcement> announcements = new ArrayList<Announcement>();

			String username;
			String announcementText;
			Timestamp timestamp;
			while(rs.next()) {
				username = rs.getString(USERNAME_COL);
				announcementText = rs.getString(ANNOUNCEMENT_COL);
				timestamp = rs.getTimestamp(TIMESTAMP_COL);
				Announcement a = new Announcement(username, announcementText, timestamp);
				announcements.add(a);
			}
			con.close();
			return announcements;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean deleteAnnouncement(String username, String announcementText) {
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
			String query = "DELETE FROM " + MyDBInfo.ANNOUNCEMENTS_TABLE + " WHERE " + USERNAME_COL 
					+ "=\"" + username + "\" AND " + ANNOUNCEMENT_COL + "=\"" + announcementText + "\";";

			//execute the query
			System.out.println(query);
			stmt.executeUpdate(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Announcement> getRecentAnnouncements(int maxLimit){
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
			String query = "SELECT * FROM " + MyDBInfo.ANNOUNCEMENTS_TABLE + " ORDER BY "
					+ TIMESTAMP_COL + " DESC LIMIT " + maxLimit + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);
			ArrayList<Announcement> resultList = new ArrayList<Announcement>();

			String username;
			String announcement;
			Timestamp timestamp;
			
			while(rs.next()) {
				username = rs.getString(USERNAME_COL);
				announcement = rs.getString(ANNOUNCEMENT_COL);
				timestamp = rs.getTimestamp(TIMESTAMP_COL);
				
				resultList.add(new Announcement(username, announcement, timestamp));
			}
			con.close();
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
