package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MessageManager {


	private static final int MAX_USERNAME_LEN = 32;


	private static final String ID_COL = "message_id";
	private static final String SENDER_COL = "sender";
	private static final String RECEIVER_COL = "receiver";
	private static final String MESSAGE_COL = "message";
	private static final String TIMESTAMP_COL = "time_stamp";


	public static boolean sendMessage(Message newMsg) {

		//ensure users exists
		if(!AccountManager.usernameExists(newMsg.getSender()) || !AccountManager.usernameExists(newMsg.getReceiver()))
			return false;



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
			String query = "INSERT INTO " + MyDBInfo.MESSAGES_TABLE
					+ " (" + SENDER_COL + "," + RECEIVER_COL + "," + MESSAGE_COL + "," + TIMESTAMP_COL +")"
					+ " VALUES (\"" + newMsg.getSender()
					+ "\", \"" + newMsg.getReceiver() + "\", \"" + newMsg.getMessage() + "\", "
					+ "NOW()" + ");";

			//execute the query
			int returnVal = stmt.executeUpdate(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public static ArrayList<Message> getReceived(String username) {
		if(!AccountManager.usernameExists(username))
			return null;

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
			String query = "SELECT * FROM " + MyDBInfo.MESSAGES_TABLE + " WHERE " + RECEIVER_COL + "=\""
					+ username + "\" ORDER BY " + TIMESTAMP_COL + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Message> resultList = new ArrayList<Message>();

			int id;
			String sender;
			String receiver;
			String message;
			Timestamp time_stamp;
			while(rs.next()) {
				id = rs.getInt(ID_COL);
				sender = rs.getString(SENDER_COL);
				receiver = rs.getString(RECEIVER_COL);
				message = rs.getString(MESSAGE_COL);
				time_stamp = rs.getTimestamp(TIMESTAMP_COL);
				resultList.add(new Message(id, sender, receiver, message, time_stamp));
			}
			con.close();
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static ArrayList<Message> getSent(String username) {
		if(!AccountManager.usernameExists(username))
			return null;

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
			String query = "SELECT * FROM " + MyDBInfo.MESSAGES_TABLE + " WHERE " + SENDER_COL + "=\""
					+ username + "\" ORDER BY " + TIMESTAMP_COL + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Message> resultList = new ArrayList<Message>();

			int id;
			String sender;
			String receiver;
			String message;
			Timestamp time_stamp;
			while(rs.next()) {
				id = rs.getInt(ID_COL);
				sender = rs.getString(SENDER_COL);
				receiver = rs.getString(RECEIVER_COL);
				message = rs.getString(MESSAGE_COL);
				time_stamp = rs.getTimestamp(TIMESTAMP_COL);
				resultList.add(new Message(id, sender, receiver, message, time_stamp));
			}
			con.close();
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean deleteMessage(Message msg) {

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
			String query = "DELETE FROM " + MyDBInfo.MESSAGES_TABLE
					+ " WHERE " + ID_COL + "=" + msg.getId() + ";";

			//execute the query
			int returnVal = stmt.executeUpdate(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}


