package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class AccountManager {

	private static final int MAX_USERNAME_LEN = 32;
	private static final int SALT_LEN = 40;
	
	private static final String USERNAME_COL = "username";
	private static final String DISPLAYNAME_COL = "displayname";
	private static final String PASSHASH_COL = "passhash";
	private static final String SALT_COL = "salt";
	private static final String TYPE_COL = "type";
	


	/**
	 * This methods takes in a username and password, and returns the Account with the specified
	 * username if 1)An account with that username exists, and 2)If the password for that username
	 * is correct.
	 * @param username
	 * @param password
	 * @return Account if username & password are valid, null otherwise.
	 */
	public Account getAccountLogin(String queryUsername, String password) {

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
					+ "=" + queryUsername;

			//execute the query
			ResultSet rs = stmt.executeQuery(query);



			String username;
			String displayname;
			String passhash;
			String salt;
			String typeString;


			//get results. If no Account is return in this loop, then no account
			//with matching credentials was found, and we return null.
			while(rs.next()) {
				//grab query info
				username = rs.getString(USERNAME_COL);
				displayname = rs.getString(DISPLAYNAME_COL);
				passhash = rs.getString(PASSHASH_COL);
				salt = rs.getString("SALT_COL");
				typeString = rs.getString("TYPE_COL");

				//check if password is correct
				if(isPasswordCorrect(password, passhash, salt)) {
					//if so, create and return the account
					Account resultAcct = new Account(username, displayname, Account.stringToType(typeString));
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


	/**
	 * Stores the account passed in into the database. Returns true if successful, false
	 * if the username already exists, is too long, etc.
	 * @param newAccount Account to be written to DB
	 * @param password password string to be hashed and paired with the account
	 * @return true on success, false on failure
	 */
	public boolean storeNewAccount(Account newAccount, String password) {
		
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
					+ "=" + newAccount.getUsername();
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				//if we found ANY records with the given username, return false
				return false;
			}
			
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
			String passhash = new String(hashval);
			

			//execute the update
			stmt.executeUpdate(query);
		}
		
		
		return false;
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
	private boolean isPasswordCorrect(String password, String passhash, String salt) {

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
		byte[] passhashBytes = passhash.getBytes();

		//check if the hashResult is the same as the stored hash value
		if(Arrays.equals(hashResult, passhashBytes)) {
			return true; //if so, return true
		} else {
			return false; // otherwise return false
		}
	}
	
	/**
	 * Generates a string of random 
	 * @return
	 */
	private String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] saltBytes = new byte[SALT_LEN];
		random.nextBytes(saltBytes);
		
		return new String(saltBytes);
	}

}
