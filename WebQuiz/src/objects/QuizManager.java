package objects;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;


public class QuizManager {

	public static final String QUIZ_ID_COL = "quiz_id";
	public static final String QUIZ_NAME_COL = "quiz_name";
	public static final String CREATOR_COL = "creator";
	public static final String DESCRIPTION_COL = "description";
	public static final String TIMESTAMP_COL = "time_stamp";
	public static final String CATEGORY_COL = "category";
	public static final String CORRECT_IMMEDIATELY_COL = "correct_immediately";
	public static final String ONE_PAGE_COL = "one_page";
	public static final String RANDOM_ORDER_COL = "random_order";
	public static final String NUMBER_OF_TIMES_TAKEN_COL = "number_of_times_taken";
	public static final String NUMBER_OF_REVIEWS_COL = "number_of_reviews";
	public static final String AVERAGE_RATING_COL = "average_rating";

	public static final String TAG_QUIZ_ID_COL = "quiz_id";
	public static final String TAG_TAG_COL = "tag";

	private static final String ATTEMPT_SCORE_COL = "score";
	private static final String ATTEMPT_QUIZ_ID_COL = "quiz_id";
	private static final String ATTEMPT_USERNAME_COL = "username";
	private static final String ATTEMPT_START_COL = "start_time";
	private static final String ATTEMPT_DURATION_COL = "duration";
	
	private static final String ACHIEVEMENTS_USERNAME_COL = "username";
	private static final String ACHIEVEMENTS_TYPE_COL = "type";
	private static final String ACHIEVEMENTS_DESCRIPTION_COL = "description";
	private static final String ACHIEVEMENTS_TIMESTAMP_COL = "time_stamp";


		public static ArrayList<Quiz> getAllQuizzes() {
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_TABLE + " ORDER BY "
					+ TIMESTAMP_COL + " DESC;";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			int quiz_id;
			String quizName;
			String creator;
			String description;
			Timestamp timestamp;
			String category;
			boolean correctImmediately;
			boolean onePage;
			boolean randomOrder;
			int numberOfTimesTaken;
			int numberOfReviews;
			double averageRating;
			
			ArrayList<Question> questionList;
			ArrayList<String> tags;

			ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

			while(rs.next()) {
				quiz_id = rs.getInt(QUIZ_ID_COL);
				quizName = rs.getString(QUIZ_NAME_COL);
				creator = rs.getString(CREATOR_COL);
				description = rs.getString(DESCRIPTION_COL);
				timestamp = rs.getTimestamp(TIMESTAMP_COL);
				category = rs.getString(CATEGORY_COL);
				correctImmediately = (rs.getInt(CORRECT_IMMEDIATELY_COL) != 0);
				numberOfTimesTaken = rs.getInt(NUMBER_OF_TIMES_TAKEN_COL);
				numberOfReviews = rs.getInt(NUMBER_OF_REVIEWS_COL);
				onePage = (rs.getInt(ONE_PAGE_COL) != 0);
				randomOrder = (rs.getInt(RANDOM_ORDER_COL) != 0);
				averageRating = rs.getDouble(AVERAGE_RATING_COL);
				questionList = QuestionManager.getQuestionsForQuiz("" + quiz_id);
				tags = getTagsForQuiz("" + quiz_id);
				Quiz q = new Quiz(quizName, description, questionList, creator, category, tags, correctImmediately,
								onePage, randomOrder, numberOfTimesTaken, numberOfReviews, averageRating, timestamp, quiz_id);
				quizzes.add(q);
			}
			con.close();
			return quizzes;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Quiz getQuizById(String quiz_id) {
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_TABLE + " WHERE "
					+ QUIZ_ID_COL + "=" + quiz_id + ";";
			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			int quizQueryId = 0;
			String quizName = "";
			String creator = "";
			String description = "";
			Timestamp timestamp = null;;
			String category = "";
			boolean correctImmediately = false;
			boolean onePage = false;
			boolean randomOrder = false;
			int numberOfTimesTaken = -1;
			int numberOfReviews = -1;
			double averageRating = -1.0d;

			boolean readEntry = false;

			while(rs.next()) {
				readEntry = true;
				quizQueryId = rs.getInt(QUIZ_ID_COL);
				quizName = rs.getString(QUIZ_NAME_COL);
				creator = rs.getString(CREATOR_COL);
				description = rs.getString(DESCRIPTION_COL);
				timestamp = rs.getTimestamp(TIMESTAMP_COL);
				category = rs.getString(CATEGORY_COL);
				correctImmediately = (rs.getInt(CORRECT_IMMEDIATELY_COL) != 0);
				numberOfTimesTaken = rs.getInt(NUMBER_OF_TIMES_TAKEN_COL);
				numberOfReviews = rs.getInt(NUMBER_OF_REVIEWS_COL);
				onePage = (rs.getInt(ONE_PAGE_COL) != 0);
				randomOrder = (rs.getInt(RANDOM_ORDER_COL) != 0);
				averageRating = rs.getDouble(AVERAGE_RATING_COL);
			}
			con.close();
			if(readEntry) {
				if(Integer.parseInt(quiz_id) != quizQueryId)
					return null;
				
				ArrayList<Question> questionList = QuestionManager.getQuestionsForQuiz(quiz_id);
				ArrayList<String> tags = getTagsForQuiz(quiz_id);
				return new Quiz(quizName, description, questionList, creator, category, tags,
						correctImmediately, onePage, randomOrder, numberOfTimesTaken, numberOfReviews, averageRating, timestamp, quizQueryId);
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean addReviewForQuiz(String quiz_id, int newRating) {
		Quiz quiz = getQuizById(quiz_id);
		double rating = quiz.getQuizRating();
		int numReviews = quiz.getNumReviews();
		double newAvgRating = ((rating * numReviews) + newRating) / (numReviews + 1);
		numReviews++;
		
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

			//prepare query to update average rating
			String update = "UPDATE " + MyDBInfo.QUIZ_TABLE + " SET " + AVERAGE_RATING_COL + "="
					+ newAvgRating + " WHERE " + QUIZ_ID_COL + "=" + quiz_id + ";";

			//execute the query
			stmt.executeUpdate(update);
			
			//prepare query to update number of reviews
			update = "UPDATE " + MyDBInfo.QUIZ_TABLE + " SET " + NUMBER_OF_REVIEWS_COL + "="
					+ numReviews + " WHERE " + QUIZ_ID_COL + "=" + quiz_id + ";";
 			//execute the query
			stmt.executeUpdate(update);

			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Adds the quiz, AND it's Questions and Tags to the database.
	 * @param toStore
	 * @return
	 */
	public static int storeQuizQuestionTags(Quiz toStore) {
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
			String query = "INSERT INTO " + MyDBInfo.QUIZ_TABLE + " ("
					+ QUIZ_NAME_COL + "," + CREATOR_COL
					+ "," + DESCRIPTION_COL + "," + TIMESTAMP_COL
					+ "," + CATEGORY_COL + "," + CORRECT_IMMEDIATELY_COL
					+ "," + ONE_PAGE_COL + "," + RANDOM_ORDER_COL
					+ "," + NUMBER_OF_TIMES_TAKEN_COL + "," + NUMBER_OF_REVIEWS_COL
					+ "," + AVERAGE_RATING_COL + ") VALUES ("
					+ "\"" + toStore.getQuizName() + "\""
					+ ",\"" + toStore.getQuizCreator() + "\""
					+ ",\"" + toStore.getQuizDescription() + "\""
					+ ",\'" + toStore.getQuizTimestamp().toString() + "\'"
					+ ",\"" + toStore.getQuizCategory() + "\""
					+ "," + (toStore.isCorrectedImmediately() ? 1 : 0)
					+ "," + (toStore.isOnePage() ? 1 : 0)
					+ "," + (toStore.isRandomOrder() ? 1 : 0)
					+ "," + toStore.getTimesTaken()
					+ "," + toStore.getNumReviews()
					+ "," + toStore.getQuizRating() + ");";
					
			//execute the query
			int result = stmt.executeUpdate(query);
			
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
			
			rs.next();
			
			int quiz_id_int = rs.getInt("LAST_INSERT_ID()");
			String quiz_id_str = "" + quiz_id_int;
			
			
			con.close();
			for(int i = 0; i < toStore.getQuestions().size(); i++) {
				if(QuestionManager.storeNewQuestion(toStore.getQuestions().get(i), quiz_id_str, i) < 0)
					return -1;
			}
			
			for(String tag : toStore.getQuizTags()) {
				if(!addTagToQuiz(quiz_id_str, tag))
					return -1;
			}
			return quiz_id_int;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}


	private static ArrayList<String> getTagsForQuiz(String quiz_id) {
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_TAG_TABLE + " WHERE "
					+ TAG_QUIZ_ID_COL + "=" + quiz_id + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<String> tags = new ArrayList<String>();

			while(rs.next()) {
				tags.add(rs.getString(TAG_TAG_COL));
			}
			con.close();
			return tags;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean addTagToQuiz(String quiz_id, String tag) {
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
			String query = "INSERT INTO " + MyDBInfo.QUIZ_TAG_TABLE + " VALUES ("
					+ quiz_id + ",\"" + tag + "\");";
			
			int result = stmt.executeUpdate(query);
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
					
	}
	/**
	 * get quiz id according to its name and etc.
	 * @param toGet
	 * @return
	 */
	public static Object getQuizId(Quiz toGet) {
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
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + MyDBInfo.QUIZ_TABLE + " WHERE " + QUIZ_NAME_COL + "=\"" + toGet.getQuizName() + "\";");
			rs.next();	
			int quiz_id_int = rs.getInt(QUIZ_ID_COL);
			String quiz_id_str = "" + quiz_id_int;
			con.close();
			return quiz_id_str;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @param max maximum number of recent created quiz to show
	 * @return an array of recently created quiz;
	 */
	public static ArrayList<Quiz> getRecentQuiz(int max){
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_TABLE + " ORDER BY "
					+ TIMESTAMP_COL + " DESC LIMIT " + max + " ;";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);
			
			//put all the results into an arrayList
			ArrayList<Quiz> returnList = new ArrayList<Quiz>();
			Quiz currentQuiz = parseQuiz(rs);
			while(currentQuiz != null) {
				returnList.add(currentQuiz);
				currentQuiz = parseQuiz(rs);
			}
			
			return returnList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param creatorID use username of the person with creates this list of quizzes
	 * @return
	 */
	public static ArrayList<Quiz> getSelfCreatedQuiz(String creatorID){
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_TABLE + " WHERE "
					 + CREATOR_COL + "=\"" + creatorID +"\" ORDER BY "
					+ TIMESTAMP_COL + " DESC;";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);
			
			//put all the results into an arrayList
			ArrayList<Quiz> returnList = new ArrayList<Quiz>();
			Quiz currentQuiz = parseQuiz(rs);
			while(currentQuiz != null) {
				returnList.add(currentQuiz);
				currentQuiz = parseQuiz(rs);
			}
			
			return returnList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param creatorID use username of the person with creates this list of quizzes.
	 * Returns the most recent quizzes, with at most parameter max being returned.
	 * @return
	 */
	public static ArrayList<Quiz> getSelfCreatedQuiz(String creatorID, int max){
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_TABLE + " WHERE "
					 + CREATOR_COL + "=\"" + creatorID +"\" ORDER BY "
					+ TIMESTAMP_COL + " DESC LIMIT " + max + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);
			
			//put all the results into an arrayList
			ArrayList<Quiz> returnList = new ArrayList<Quiz>();
			Quiz currentQuiz = parseQuiz(rs);
			while(currentQuiz != null) {
				returnList.add(currentQuiz);
				currentQuiz = parseQuiz(rs);
			}
			
			return returnList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static Quiz parseQuiz(ResultSet rs) throws SQLException {
		int quiz_id = 0;
		String quizName = "";
		String creator = "";
		String description = "";
		Timestamp timestamp = null;;
		String category = "";
		boolean correctImmediately = false;
		boolean onePage = false;
		boolean randomOrder = false;
		int numberOfTimesTaken = -1;
		int numberOfReviews = -1;
		double averageRating = -1.0d;

		boolean readEntry = false;

		while(rs.next()) {
			readEntry = true;
			quiz_id = rs.getInt(QUIZ_ID_COL);
			quizName = rs.getString(QUIZ_NAME_COL);
			creator = rs.getString(CREATOR_COL);
			description = rs.getString(DESCRIPTION_COL);
			timestamp = rs.getTimestamp(TIMESTAMP_COL);
			category = rs.getString(CATEGORY_COL);
			correctImmediately = (rs.getInt(CORRECT_IMMEDIATELY_COL) != 0);
			numberOfTimesTaken = rs.getInt(NUMBER_OF_TIMES_TAKEN_COL);
			numberOfReviews = rs.getInt(NUMBER_OF_REVIEWS_COL);
			onePage = (rs.getInt(ONE_PAGE_COL) != 0);
			randomOrder = (rs.getInt(RANDOM_ORDER_COL) != 0);
			averageRating = rs.getDouble(AVERAGE_RATING_COL);
			break;
		}
		if(readEntry) {
			ArrayList<Question> questionList = QuestionManager.getQuestionsForQuiz(quiz_id + "");
			ArrayList<String> tags = getTagsForQuiz(quiz_id + "");
			return new Quiz(quizName, description, questionList, creator, category, tags,
					correctImmediately, onePage, randomOrder, numberOfTimesTaken, numberOfReviews, averageRating, timestamp, quiz_id);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the top max attempts for the specified quiz. They are ordered first by score, then
	 * by duration.
	 * @param quiz_id
	 * @param max
	 * @return
	 */
	public static ArrayList<QuizAttempt> getTopAttempts(String quiz_id, int max) {
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
			String query = "SELECT A.* FROM " + MyDBInfo.ATTEMPTS_TABLE + " A LEFT OUTER JOIN " + MyDBInfo.ATTEMPTS_TABLE
					+ " B ON (A." + ATTEMPT_USERNAME_COL + "= B." + ATTEMPT_USERNAME_COL + " AND A." + ATTEMPT_SCORE_COL + " < B." + ATTEMPT_SCORE_COL
					+ ") WHERE B." + ATTEMPT_USERNAME_COL + " IS NULL ORDER BY " + ATTEMPT_SCORE_COL + " DESC, " + ATTEMPT_START_COL + " ASC LIMIT " + max + ";";

					
			ResultSet rs = stmt.executeQuery(query);
			
			ArrayList<QuizAttempt> resultList = new ArrayList<QuizAttempt>();
			QuizAttempt newAttempt = parseAttempt(rs);
			while(newAttempt != null) {
				resultList.add(newAttempt);
				newAttempt = parseAttempt(rs);
			}
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<QuizAttempt> getLastAttemptsForUser(String quiz_id, String username, int max) {
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
				String query = "SELECT * FROM " + MyDBInfo.ATTEMPTS_TABLE + " WHERE " + ATTEMPT_QUIZ_ID_COL + "="
						+ quiz_id + " AND " + ATTEMPT_USERNAME_COL + "=\"" + username + "\" ORDER BY " + ATTEMPT_START_COL
						+ " DESC LIMIT " + max + ";";

						
				ResultSet rs = stmt.executeQuery(query);
				
				ArrayList<QuizAttempt> resultList = new ArrayList<QuizAttempt>();
				QuizAttempt newAttempt = parseAttempt(rs);
				while(newAttempt != null) {
					resultList.add(newAttempt);
					newAttempt = parseAttempt(rs);
				}
				return resultList;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	public static boolean storeAttempt(QuizAttempt attempt) {
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
			String query = "INSERT INTO " + MyDBInfo.ATTEMPTS_TABLE + " VALUES ("
					+ attempt.getQuizID() + ",\"" + attempt.getUsername() + "\","
					+ attempt.getScore() + ",'" + attempt.getStartTime().toString() + "',"
					+ attempt.getDuration() + ");";
			
			int result = stmt.executeUpdate(query);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static QuizAttempt parseAttempt(ResultSet rs) throws SQLException {
		int quiz_id_int;
		String username;
		int score;
		Timestamp start_time;
		long duration;
		while(rs.next()) {
			quiz_id_int = rs.getInt(ATTEMPT_QUIZ_ID_COL);
			username = rs.getString(ATTEMPT_USERNAME_COL);
			score = rs.getInt(ATTEMPT_SCORE_COL);
			start_time = rs.getTimestamp(ATTEMPT_START_COL);
			duration = rs.getLong(ATTEMPT_DURATION_COL);
			return new QuizAttempt(quiz_id_int, username, score, start_time, duration);
		}
		return null;

	}
	
	
	public static boolean storeAchievement(Achievement achieve) {
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
			
			String query = "INSERT INTO " + MyDBInfo.ACHIEVEMENTS_TABLE + " VALUES ("
					+ "\"" + achieve.getUsername() + "\",\"" + achieve.getTypeStr() + "\",\""
					+ achieve.getDescription() + "\",\'" + achieve.getTimestamp().toString() + "\');";
			
			int result = stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Achievement> getAchievementsForUser(String username, int max) {
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
			
			String query = "SELECT * FROM " + MyDBInfo.ACHIEVEMENTS_TABLE + " WHERE "
					+ ACHIEVEMENTS_USERNAME_COL + "=\"" + username + "\" ORDER BY " + ACHIEVEMENTS_TIMESTAMP_COL
					+ " DESC LIMIT " + max + ";";
			
			ResultSet rs = stmt.executeQuery(query);
			
			ArrayList<Achievement> resultList = new ArrayList<Achievement>();
			
			for(int i = 0; i < max; i++) {
				Achievement newAchievement = parseAchievement(rs);
				if(newAchievement == null)
					break;
				else
					resultList.add(newAchievement);
			}
			return resultList;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
					
	}
	
	private static Achievement parseAchievement(ResultSet rs) throws SQLException {
		String username;
		Achievement.Type type;
		String description;
		Timestamp time_stamp;
		while(rs.next()) {
			username = rs.getString(ACHIEVEMENTS_USERNAME_COL);
			type = Achievement.getTypeForString(rs.getString(ACHIEVEMENTS_TYPE_COL));
			description = rs.getString(ACHIEVEMENTS_DESCRIPTION_COL);
			time_stamp = rs.getTimestamp(ACHIEVEMENTS_TIMESTAMP_COL);
			return new Achievement(username, type, description, time_stamp);
		}
		return null;
	}
	
	//main method for testing
	public static void main(String[] args) {
		
		
		Quiz quiz1 = QuizManager.getQuizById("1");
		Quiz quiz2 = QuizManager.getQuizById("3");
	
		ArrayList<Question> questionList1 = new ArrayList<Question>();
		
		Question test1 = new MultipleChoiceQuestion("1", "Who of the following was consul of Rome during the end of the Second Punic War?#" +
				"Cato the Elder#Cato the Younger#Julius Caesar#Scipio Africanus?", "This is a question about the Roman Republic.",
				"sally", 10, new Timestamp(System.currentTimeMillis()));
		
		Question test2 = new QuestionResponseQuestion("2", "Which Roman consul was defeated at the battle of Cannae?",
				"This is a question about the Roman Republic.","sally", 50, new Timestamp(System.currentTimeMillis()));
		
		questionList1.add(test1);
		questionList1.add(test2);
		
		ArrayList<String> tagList1 = new ArrayList<String>();
		tagList1.add("Rome");
		tagList1.add("Ancient");
		tagList1.add("Classics");
		
		
		Quiz quiz3 = new Quiz("quiz3","this quiz is added by the manager", questionList1,
				"john", "History", tagList1, false, false, false, 0, 0, 0.0d,
				new Timestamp(System.currentTimeMillis()));
		
		int result = QuizManager.storeQuizQuestionTags(quiz3);
		quiz3.setQuizID(result);
		
		QuizAttempt testAttempt = new QuizAttempt(1, "john", 60, new Timestamp(System.currentTimeMillis()), 70);
		QuizManager.storeAttempt(testAttempt);
		ArrayList<QuizAttempt> topAttempts = QuizManager.getLastAttemptsForUser("1", "john", 5);
		
		Achievement A1 = new Achievement("john", Achievement.Type.PRACTICE, "foo", new Timestamp(System.currentTimeMillis()));
		Achievement A2 = new Achievement("john", Achievement.Type.ONE_CREATED, "bar", new Timestamp(System.currentTimeMillis() + 2000));
		QuizManager.storeAchievement(A1);
		QuizManager.storeAchievement(A2);
		
		ArrayList<Achievement> achieveList = QuizManager.getAchievementsForUser("john", 5);
		
	}
	

}
