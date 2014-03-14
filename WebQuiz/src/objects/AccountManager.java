package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class AccountManager {

	private static final int MAX_USERNAME_LEN = 32;
	private static final int SALT_LEN = 20;


	private static final String USERNAME_COL = "username";
	private static final String DISPLAYNAME_COL = "displayname";
	private static final String PASSHASH_COL = "passhash";
	private static final String SALT_COL = "salt";
	private static final String TYPE_COL = "type";
	private static final String ISPRIVATE_COL = "isPrivate";
	private static final String ISACTIVE_COL = "isActive";
	private static final String FRIENDS_COL1 = "user";
	private static final String FRIENDS_COL2 = "friends_name";
	private static final String REQUESTER_COL = "requester";
	private static final String REQUESTED_COL = "requested";
	private static final String MESSAGE_COL = "message";
	private static final String TIMESTAMP_COL = "time_stamp";



	/**
	 * This methods takes in a username and password, and returns the Account with the specified
	 * username if 1)An account with that username exists, and 2)If the password for that username
	 * is correct.
	 * @param username
	 * @param password
	 * @return Account if username & password are valid, null otherwise.
	 */
	public static Account getAccountLogin(String queryUsername, String password) {

		//sanitize input
		if(queryUsername.length() > MAX_USERNAME_LEN)
			return null;

		//change queryUsername to lower case; don't want to be case-sensitive
		queryUsername = queryUsername.toLowerCase();

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
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + " WHERE " + USERNAME_COL
					+ "=\"" + queryUsername + "\";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			String username;
			String displayname;
			String passhash;
			String salt;
			String typeString;
			boolean isPrivate;
			boolean isActive;

			//get results. If no Account is return in this loop, then no account
			//with matching credentials was found, and we return null.
			while(rs.next()) {
				//grab query info
				username = rs.getString(USERNAME_COL);
				displayname = rs.getString(DISPLAYNAME_COL);
				passhash = rs.getString(PASSHASH_COL);
				salt = rs.getString(SALT_COL);
				typeString = rs.getString(TYPE_COL);
				isPrivate = (rs.getInt(ISPRIVATE_COL) > 0);
				isActive = (rs.getInt(ISACTIVE_COL) > 0);

				//check if password is correct
				if(isPasswordCorrect(password, passhash, salt)) {
					//if so, create and return the account
					Account resultAcct = new Account(username, displayname, Account.stringToType(typeString), isPrivate, isActive);
					return resultAcct;
				}
			}

			//if we reach this point, there was no account found, or the password was incorrect
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<Account> getAllAccounts() {
		ArrayList<Account> accountList = new ArrayList<Account>();
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
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + ";";
			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			Account resultAccount = parseAccount(rs);
			while(resultAccount != null) {
				accountList.add(resultAccount);
				resultAccount = parseAccount(rs);
			}

			//if we reach this point, there was no account found, or the password was incorrect
			con.close();
			return accountList;

		} catch (SQLException e) {
			e.printStackTrace();
			return accountList;
		}
	}
	
	public static ArrayList<Account> getAllActiveAccounts() {
		ArrayList<Account> accountList = new ArrayList<Account>();
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
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + " WHERE " + ISACTIVE_COL + ">0 ;";
			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			Account resultAccount = parseAccount(rs);
			while(resultAccount != null) {
				if (resultAccount.isActive()) {
					accountList.add(resultAccount);
				}
				resultAccount = parseAccount(rs);
			}

			//if we reach this point, there was no account found, or the password was incorrect
			con.close();
			return accountList;

		} catch (SQLException e) {
			e.printStackTrace();
			return accountList;
		}
	}
	

	public static ArrayList<Account> getAccountsByDisplayName(String search) {
		ArrayList<Account> accountList = new ArrayList<Account>();
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
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + " WHERE " + ISACTIVE_COL + ">0 AND "
					+ DISPLAYNAME_COL + " LIKE \"%" + search + "%\";";
			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			Account resultAccount = parseAccount(rs);
			while(resultAccount != null) {
				if (resultAccount.isActive()) {
					accountList.add(resultAccount);
				}
				resultAccount = parseAccount(rs);
			}

			//if we reach this point, there was no account found, or the password was incorrect
			con.close();
			return accountList;

		} catch (SQLException e) {
			e.printStackTrace();
			return accountList;
		}
	}
	
	public static ArrayList<Account> getAccountsByUsername(String search) {
		ArrayList<Account> accountList = new ArrayList<Account>();
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
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + " WHERE " + ISACTIVE_COL + ">0 AND "
					+ USERNAME_COL + " LIKE \"%" + search + "%\";";
			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			Account resultAccount = parseAccount(rs);
			while(resultAccount != null) {
				if (resultAccount.isActive()) {
					accountList.add(resultAccount);
				}
				resultAccount = parseAccount(rs);
			}

			//if we reach this point, there was no account found, or the password was incorrect
			con.close();
			return accountList;

		} catch (SQLException e) {
			e.printStackTrace();
			return accountList;
		}
	}
	
	
	public static int getNumActiveAccounts() {
		return getAllActiveAccounts().size();
	}

	public static Account getAccountByUsername(String queryUsername) {

		//sanitize input
		if(queryUsername.length() > MAX_USERNAME_LEN)
			return null;

		//change queryUsername to lower case; don't want to be case-sensitive
		queryUsername = queryUsername.toLowerCase();

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
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + " WHERE " + USERNAME_COL
					+ "=\"" + queryUsername + "\";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			Account resultAccount = parseAccount(rs);
			con.close();
			return resultAccount;


		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Stores the account passed in into the database. Returns true if successful, false
	 * if the username already exists, is too long, etc.
	 * @param newAccount Account to be written to DB
	 * @param password password string to be hashed and paired with the account
	 * @return true on success, false on failure
	 */
	public static boolean storeNewAccount(Account newAccount, String password) {

		//sanitize input
		if(newAccount.getUsername().length() > MAX_USERNAME_LEN)
			return false;

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

			//first, check if there are any existing Accounts with the same username
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + " WHERE " + USERNAME_COL
					+ "=\"" + newAccount.getUsername() + "\";";

			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				//if we found ANY records with the given username, return false
				return false;
			}

			//create a MessageDigest to do hashing
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("SHA");
			} catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
				return false;
			}

			//if there was not existing account, create the account

			//create the passhash and salt
			String salt = generateSalt();
			String complete = password + salt;
			byte[] hashval = digest.digest(complete.getBytes());
			String passhash = hexToString(hashval);

			String username = newAccount.getUsername();
			String typeStr = newAccount.getTypeString();
			String displayname = newAccount.getDisplayName();
			int isPrivateInt = 0;
			int isActiveInt = 0;
			if(newAccount.isPrivate()) isPrivateInt = 1;
			if(newAccount.isActive()) isActiveInt = 1;

			//build the insert statement
			query = "INSERT INTO " + MyDBInfo.ACCOUNTS_TABLE + " VALUES "
					+ "(\"" + username + "\",\"" + displayname + "\",\"" + passhash + "\",\"" + salt
					+ "\",\"" + typeStr + "\"," + isPrivateInt + "," + isActiveInt + ");";

			//execute the update
			stmt.executeUpdate(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	private static Account parseAccount(ResultSet rs) throws SQLException {
		String username;
		String displayname;
		String typeString;
		boolean isPrivate;
		boolean isActive;

		//get results. If no Account is return in this loop, then no account
		//with matching credentials was found, and we return null.
		while(rs.next()) {
			//grab query info
			username = rs.getString(USERNAME_COL);
			displayname = rs.getString(DISPLAYNAME_COL);
			typeString = rs.getString(TYPE_COL);
			isPrivate = (rs.getInt(ISPRIVATE_COL) > 0);
			isActive = (rs.getInt(ISACTIVE_COL) > 0);

			//check if password is correct

			Account resultAcct = new Account(username, displayname, Account.stringToType(typeString), isPrivate, isActive);
			return resultAcct;
		}
		return null;
	}

	public static boolean usernameExists(String queryUsername) {

		//sanitize input
		if(queryUsername.length() > MAX_USERNAME_LEN)
			return false;

		//change queryUsername to lower case; don't want to be case-sensitive
		queryUsername = queryUsername.toLowerCase();

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
			String query = "SELECT * FROM " + MyDBInfo.ACCOUNTS_TABLE + " WHERE " + USERNAME_COL
					+ "=\"" + queryUsername + "\";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);




			//get results. If no Account is return in this loop, then no account
			//with matching credentials was found, and we return null.
			while(rs.next()) {
				con.close();
				return true;
			}
			con.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * disables
	 * @param username
	 * @return
	 */
	public static boolean disableAccount(String username) {
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

			//prepare query to make account disabled
			String update = "UPDATE " + MyDBInfo.ACCOUNTS_TABLE + " SET " + ISACTIVE_COL + "=0 WHERE "
					+ USERNAME_COL + "=\"" + username + "\";";
			
			stmt.executeUpdate(update);
			
			//prepare query to remove all friendships
			update = "DELETE FROM " + MyDBInfo.FRIENDS_TABLE + " WHERE " + FRIENDS_COL1
					+ "=\"" + username + "\" OR " + FRIENDS_COL2 + "=\"" + username + "\";";
			stmt.executeUpdate(update);
			
			//prepare query to remove all pending friend requests
			update = "DELETE FROM " + MyDBInfo.FRIEND_REQUESTS_TABLE + " WHERE " + REQUESTER_COL
					+ "=\"" + username + "\" OR " + REQUESTED_COL + "=\"" + username + "\";";
			stmt.executeUpdate(update);
			
			
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean enableAccount(String username) {
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
			String query = "UPDATE " + MyDBInfo.ACCOUNTS_TABLE + " SET " + ISACTIVE_COL + "=1 WHERE "
					+ USERNAME_COL + "=\"" + username + "\";";
			
			int result = stmt.executeUpdate(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean makeUserAdmin(String username) {
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
			String query = "UPDATE " + MyDBInfo.ACCOUNTS_TABLE + " SET " + TYPE_COL + "=\"" + Account.ADMIN_STR +  "\" WHERE "
					+ USERNAME_COL + "=\"" + username + "\";";
			
			int result = stmt.executeUpdate(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This functions determines whether or not the password and salt passed in create
	 * a hash equal to the hash value passed in.
	 * @param password password user entered in
	 * @param passhash hash value for account
	 * @param salt salt value for account
	 * @return true if the password and salt create the same hash as passhash, false if they
	 * create different hashes, or if there was an error with MessageDigest.
	 */
	private static boolean isPasswordCorrect(String password, String passhash, String salt) {

		//set up the message digest
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}

		//concatenate the password with the salt
		String complete = password + salt;

		//calculate the hash of the salted password
		byte[] hashResult = digest.digest(complete.getBytes());
		byte[] passhashBytes = hexToArray(passhash);

		//check if the hashResult is the same as the stored hash value
		if(Arrays.equals(hashResult, passhashBytes)) {
			return true; //if so, return true
		} else {
			return false; // otherwise return false
		}
	}

	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	 */
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}


	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	 */
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}

	/**
	 * Generates a string of random hex characters
	 * @return
	 */
	private static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] saltBytes = new byte[SALT_LEN];
		random.nextBytes(saltBytes);

		return hexToString(saltBytes);
	}


	/*
	 * This section of the AccountManager code handles friend functionality
	 */

	public static boolean areFriends(String username1, String username2) {
		// make sure usernames both exist
		if (!usernameExists(username1) || !usernameExists(username2)) return false;

		// make usernames lower case
		username1 = username1.toLowerCase();
		username2 = username2.toLowerCase();

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

			//prepare query (select all rows in friendsTable where user = username1 and friends_name = username2)
			String query = "SELECT * FROM " + MyDBInfo.FRIENDS_TABLE + " WHERE " + FRIENDS_COL1
					+ "=\"" + username1 + "\" AND " + FRIENDS_COL2 + "=\"" + username2 + "\";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			// if the two users are friends, return true
			while (rs.next()) {
				con.close();
				return true;
			}

			// if they are not friends, return false
			con.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addFriendship(String username1, String username2) {
		// make usernames lower case
		username1 = username1.toLowerCase();
		username2 = username2.toLowerCase();

		// if the two users are already friends, do nothing and return false
		if (areFriends(username1, username2)) return false;

		// try to add friendship to table (two-way relationship)
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

			// prepare query
			// Insert (username1, username2) and (username2, username1) into friends table
			String query = "INSERT INTO " + MyDBInfo.FRIENDS_TABLE + " VALUES "
					+ "(\"" + username1 + "\",\"" + username2 + "\");";
			stmt.executeUpdate(query);
			query = "INSERT INTO " + MyDBInfo.FRIENDS_TABLE + " VALUES "
					+ "(\"" + username2 + "\",\"" + username1 + "\");";
			stmt.executeUpdate(query);

			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeFriendship(String username1, String username2) {
		// make usernames lower case
		username1 = username1.toLowerCase();
		username2 = username2.toLowerCase();

		// if the two users aren't friends, return false
		if (!areFriends(username1, username2)) return false;

		// try to remove friendship from table (two-way relationship)
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

			// prepare query
			// Remove (username1, username2) and (username2, username1) from friends table
			String update = "DELETE FROM " + MyDBInfo.FRIENDS_TABLE + " WHERE " + FRIENDS_COL1
					+ "=\"" + username1 + "\" AND " + FRIENDS_COL2 + "=\"" + username2 + "\";";
			stmt.executeUpdate(update);
			update = "DELETE FROM " + MyDBInfo.FRIENDS_TABLE + " WHERE " + FRIENDS_COL1
					+ "=\"" + username2 + "\" AND " + FRIENDS_COL2 + "=\"" + username1 + "\";";
			stmt.executeUpdate(update);

			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static int getNumFriendships() {
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
			String query = "SELECT * FROM " + MyDBInfo.FRIENDS_TABLE;

			//execute the query
			ResultSet rs = stmt.executeQuery(query);
			
			int count = 0;
			
			while (rs.next()) {
				count++;
			}
			
			con.close();
			return (count / 2);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static ArrayList<Account> getFriendsForUser(String username) {
		// make lower case
		username = username.toLowerCase();

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
			String query = "SELECT * FROM " + MyDBInfo.FRIENDS_TABLE + " INNER JOIN " + MyDBInfo.ACCOUNTS_TABLE +
					" ON " + MyDBInfo.FRIENDS_TABLE + "." + FRIENDS_COL2 + " = " + MyDBInfo.ACCOUNTS_TABLE + "." +
					USERNAME_COL + " WHERE " + FRIENDS_COL1 + " = \"" + username + "\";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Account> friends = new ArrayList<Account>();

			String friendName;
			String displayname;
			String typeString;
			boolean isPrivate;

			while (rs.next()) {
				friendName = rs.getString(FRIENDS_COL2);
				displayname = rs.getString(DISPLAYNAME_COL);
				typeString = rs.getString(TYPE_COL);
				isPrivate = (rs.getInt(ISPRIVATE_COL) > 0);

				//check if password is correct

				Account friend = new Account(friendName, displayname, Account.stringToType(typeString), isPrivate);
				friends.add(friend);
			}

			con.close();
			return friends;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean requestIsPending(String requester, String requested) {
		// make usernames lower case
		requester = requester.toLowerCase();
		requested = requested.toLowerCase();

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
			String query = "SELECT * FROM " + MyDBInfo.FRIEND_REQUESTS_TABLE + " WHERE " + REQUESTER_COL
					+ "=\"" + requester + "\" AND " + REQUESTED_COL + "=\"" + requested + "\";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			//get results. If no FriendRequest is found in this loop, then we return false.
			while(rs.next()) {
				con.close();
				return true;
			}
			con.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	/*
	 * This section of the AccountManager code handles friend requests
	 */

	public static boolean sendFriendRequest(FriendRequest newRequest) {

		// if the two users are friends, return false
		if (areFriends(newRequest.getRequester(), newRequest.getRequested())) return false;

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

			// see if the request already exists
			if (requestIsPending(newRequest.getRequester(), newRequest.getRequested())) return false;

			// prepare query to add the request to the table
			String query = "INSERT INTO " + MyDBInfo.FRIEND_REQUESTS_TABLE
					+ " (" + REQUESTER_COL + "," + REQUESTED_COL + "," + MESSAGE_COL + "," + TIMESTAMP_COL +")"
					+ " VALUES (\"" + newRequest.getRequester() + "\", \"" + newRequest.getRequested()
					+ "\", \"" + newRequest.getMessage() + "\", " + "NOW()" + ");";

			stmt.executeUpdate(query);

			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeFriendRequest(String requester, String requested) {
		// try to remove friendship from table (two-way relationship)
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

			// prepare query to remove friend request from table
			String update = "DELETE FROM " + MyDBInfo.FRIEND_REQUESTS_TABLE + " WHERE " + REQUESTER_COL
					+ "=\"" + requester + "\" AND " + REQUESTED_COL + "=\"" + requested + "\";";
			stmt.executeUpdate(update);

			update = "DELETE FROM " + MyDBInfo.FRIEND_REQUESTS_TABLE + " WHERE " + REQUESTER_COL
					+ "=\"" + requested + "\" AND " + REQUESTED_COL + "=\"" + requester + "\";";
			stmt.executeUpdate(update);

			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean confirmFriendRequest(String requester, String requested) {
		System.out.println("Requester: " + requester + ", Requested: " + requested);
		if (!requestIsPending(requester, requested)) {
			System.out.println("Request not pending");
			return false;
		}
		boolean success = removeFriendRequest(requester, requested);
		if (success) {
			success = addFriendship(requester, requested);
			if (!success) {
				System.out.println("Problem adding friendship");
			}
		} else {
			System.out.println("Problem removing friend request.");
		}
		return success;
	}

	public static ArrayList<FriendRequest> getFriendReqeustsForUser(String username) {
		//make lower case
		username = username.toLowerCase();

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
			String query = "SELECT * FROM " + MyDBInfo.FRIEND_REQUESTS_TABLE + " WHERE " + REQUESTED_COL
					+ "=\"" + username + "\";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<FriendRequest> requests = new ArrayList<FriendRequest>();

			String requester;
			String requested;
			String message;
			Timestamp timestamp;

			//get results. If no FriendRequest is found in this loop, then we return an empty ArrayList.
			while(rs.next()) {
				requester = rs.getString(REQUESTER_COL);
				requested = rs.getString(REQUESTED_COL);
				message = rs.getString(MESSAGE_COL);
				timestamp = rs.getTimestamp(TIMESTAMP_COL);

				FriendRequest r = new FriendRequest(requester, requested, message, timestamp);
				requests.add(r);

			}
			con.close();
			return requests;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * returns the total number of accounts, regardless of active status
	 * @return count of accounts, -1 on error
	 */
	public static int getTotalAccountCount() {
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
			String query = "SELECT COUNT(*) AS count FROM " + MyDBInfo.ACCOUNTS_TABLE + ";";

			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int result = rs.getInt("count");
			con.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
