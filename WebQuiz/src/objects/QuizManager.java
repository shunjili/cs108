package objects;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;


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
	public static final String CAN_PRACTICE_COL = "can_practice";

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

			ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

			while(true) {
				Quiz resultQuiz = parseQuiz(rs);
				if(resultQuiz == null)
					break;
				else
					quizzes.add(resultQuiz);
			}
			con.close();
			return quizzes;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean deleteQuiz(String quiz_id) {
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
			
			String query = "DELETE FROM " + MyDBInfo.QUIZ_QUESTION_TABLE + " WHERE "
					+ QUIZ_ID_COL + "=\"" + quiz_id + "\";";
			stmt.executeUpdate(query);
			
			query = "DELETE FROM " + MyDBInfo.QUIZ_TAG_TABLE + " WHERE "
					+ QUIZ_ID_COL + "=\"" + quiz_id + "\";";
			stmt.executeUpdate(query);
			
			query = "DELETE FROM " + MyDBInfo.ATTEMPTS_TABLE + " WHERE "
					+ QUIZ_ID_COL + "=\"" + quiz_id + "\";";
			stmt.executeUpdate(query);
			
			query = "DELETE FROM " + MyDBInfo.QUIZ_TABLE + " WHERE "
					+ QUIZ_ID_COL + "=\"" + quiz_id + "\";";
			stmt.executeUpdate(query);
			
			con.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the quizzes sorted by the number of times taken. Highest number of times
	 * taken come first.
	 * @param max maximum number of quizzes to be returned
	 * @return
	 */
	public static ArrayList<Quiz> getMostPopularQuizzes(int max) {
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
					+ NUMBER_OF_TIMES_TAKEN_COL + " DESC LIMIT " + max + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

			while(true) {
				Quiz resultQuiz = parseQuiz(rs);
				if(resultQuiz == null)
					break;
				else
					quizzes.add(resultQuiz);
			}
			con.close();
			return quizzes;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the quizzes sorted by the number of times taken. Highest number of times
	 * taken come first.
	 * @param max maximum number of quizzes to be returned
	 * @return
	 */
	public static ArrayList<Quiz> getHighestRatedQuizzes(int max) {
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
					+ AVERAGE_RATING_COL + " DESC LIMIT " + max + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

			while(true) {
				Quiz resultQuiz = parseQuiz(rs);
				if(resultQuiz == null)
					break;
				else
					quizzes.add(resultQuiz);
			}
			con.close();
			return quizzes;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the quizzes sorted by the number of times taken. Highest number of times
	 * taken come first.
	 * @param max maximum number of quizzes to be returned
	 * @return
	 */
	public static ArrayList<Quiz> getMostRecentQuizzes(int max) {
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
					+ TIMESTAMP_COL + " DESC LIMIT " + max + ";";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Quiz> quizzes = new ArrayList<Quiz>();

			while(true) {
				Quiz resultQuiz = parseQuiz(rs);
				if(resultQuiz == null)
					break;
				else
					quizzes.add(resultQuiz);
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

			Quiz resultQuiz = parseQuiz(rs);
			con.close();
			return resultQuiz;

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

			int canPractice = 0;
			if(toStore.canPractice())
				canPractice = 1;

			//prepare query
			String query = "INSERT INTO " + MyDBInfo.QUIZ_TABLE + " ("
					+ QUIZ_NAME_COL + "," + CREATOR_COL
					+ "," + DESCRIPTION_COL + "," + TIMESTAMP_COL
					+ "," + CATEGORY_COL + "," + CORRECT_IMMEDIATELY_COL
					+ "," + ONE_PAGE_COL + "," + RANDOM_ORDER_COL
					+ "," + NUMBER_OF_TIMES_TAKEN_COL + "," + NUMBER_OF_REVIEWS_COL
					+ "," + AVERAGE_RATING_COL + "," + CAN_PRACTICE_COL + ") VALUES ("
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
					+ "," + toStore.getQuizRating() + "," + canPractice + ");";

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

			updateAchievementsNewQuiz(toStore.getQuizCreator());
			con.close();
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

			con.close();
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

			con.close();
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

			con.close();
			return returnList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	private static void incrementTimesTaken(int quiz_id) {
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
			String query = "UPDATE " + MyDBInfo.QUIZ_TABLE + " SET "
					+ NUMBER_OF_TIMES_TAKEN_COL + " = " + NUMBER_OF_TIMES_TAKEN_COL + " + 1 WHERE "
					+ QUIZ_ID_COL + "=" + quiz_id + ";";

			int result = stmt.executeUpdate(query);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
		boolean canPractice = false;

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
			if(rs.getInt(CAN_PRACTICE_COL) != 0)
				canPractice = true;
			else
				canPractice = false;
			break;
		}
		if(readEntry) {
			ArrayList<Question> questionList = QuestionManager.getQuestionsForQuiz(quiz_id + "");
			ArrayList<String> tags = getTagsForQuiz(quiz_id + "");
			return new Quiz(quizName, description, questionList, creator, category, tags,
					correctImmediately, onePage, randomOrder, numberOfTimesTaken, numberOfReviews, averageRating, timestamp, quiz_id, canPractice);
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


	public static ArrayList<QuizAttempt> getTopAttemptsLastDay(String quiz_id, int max) {
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
					+ ") WHERE B." + ATTEMPT_USERNAME_COL + " IS NULL AND A." + ATTEMPT_START_COL + ">=DATE_SUB(NOW(), INTERVAL 1 DAY) ORDER BY "
					+ ATTEMPT_SCORE_COL + " DESC, " + ATTEMPT_START_COL + " ASC LIMIT " + max + ";";


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

			incrementTimesTaken(attempt.getQuizID());
			updateAchievementsNewAttempt(attempt.getUsername(), attempt.getQuizID());
			con.close();
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

	/**
	 * updates the achievements for the user with the specified username. Update the
	 * achievements linked to attempting a quiz (TEN_TAKEN, HIGH_SCORE, etc).
	 * @param username username of the Account to update Achievements
	 * @param quiz_id ID of the quiz just taken
	 */
	private static void updateAchievementsNewAttempt(String username, int quiz_id) {
		HashSet<Achievement.Type> missingAchievements = getMissingAchievements(username);

		int numAttempts = getNumQuizAttemptsUser(username);


		if(missingAchievements.contains(Achievement.Type.HIGH_SCORE)) {
			ArrayList<QuizAttempt> topAttempts = getTopAttempts(quiz_id + "", 1);
			if(topAttempts.size() == 0) {
				Achievement highScore = new Achievement(username, Achievement.Type.HIGH_SCORE,
						new Timestamp(System.currentTimeMillis()));
				QuizManager.storeAchievement(highScore);
			} else if(topAttempts.get(0).getUsername().equals(username)) {
				Achievement highScore = new Achievement(username, Achievement.Type.HIGH_SCORE,
						new Timestamp(System.currentTimeMillis()));
				QuizManager.storeAchievement(highScore);
			}
		}

		if(missingAchievements.contains(Achievement.Type.TEN_TAKEN)) {
			if(numAttempts >= 10) {
				Achievement tenTaken = new Achievement(username, Achievement.Type.TEN_TAKEN,
						new Timestamp(System.currentTimeMillis()));
				QuizManager.storeAchievement(tenTaken);
			}
		}
	}

	private static void updateAchievementsNewQuiz(String username) {
		HashSet<Achievement.Type> missingAchievements = getMissingAchievements(username);

		int numCreated = getNumQuizzesCreatedUser(username);

		if(missingAchievements.contains(Achievement.Type.ONE_CREATED) && numCreated >= 1) {
			Achievement oneCreated = new Achievement(username, Achievement.Type.ONE_CREATED,
					new Timestamp(System.currentTimeMillis()));
			QuizManager.storeAchievement(oneCreated);
		} 

		if(missingAchievements.contains(Achievement.Type.FIVE_CREATED) && numCreated >= 5) {
			Achievement fiveCreated = new Achievement(username, Achievement.Type.FIVE_CREATED,
					new Timestamp(System.currentTimeMillis()));
			QuizManager.storeAchievement(fiveCreated);
		} 

		if(missingAchievements.contains(Achievement.Type.TEN_CREATED) && numCreated >= 10) {
			Achievement tenCreated = new Achievement(username, Achievement.Type.TEN_CREATED,
					new Timestamp(System.currentTimeMillis()));
			QuizManager.storeAchievement(tenCreated);
		}
	}


	public static void updatePracticeAchievement(String username) {
		if(getMissingAchievements(username).contains(Achievement.Type.PRACTICE)) {
			Achievement practice = new Achievement(username, Achievement.Type.PRACTICE,
					new Timestamp(System.currentTimeMillis()));
			QuizManager.storeAchievement(practice);
		}
	}

	/**
	 * Gets count of Quizzes created by the user, 
	 * @param username username of Account to count quizzed created by.
	 * @return count of quizzes created by the specified user, -1 if error
	 */
	public static int getNumQuizzesCreatedUser(String username) {
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
			String query = "SELECT COUNT(" + CREATOR_COL +") AS CreatedUser FROM "
					+ MyDBInfo.QUIZ_TABLE + " WHERE " + CREATOR_COL + "=\"" + username + "\";";

			ResultSet rs = stmt.executeQuery(query);

			rs.next();
			int numCreated = rs.getInt("CreatedUser");
			con.close();
			return numCreated;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Returns the count of Quizzes taken by the user passed in, or -1 if error.
	 * @param username username of user to count Quizzes taken by.
	 * @return count of quiz attempts, -1 on error.
	 */
	public static int getNumQuizAttemptsUser(String username) {
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
			String query = "SELECT COUNT(" + ATTEMPT_USERNAME_COL +") AS AttemptsUser FROM "
					+ MyDBInfo.ATTEMPTS_TABLE + " WHERE " + ATTEMPT_USERNAME_COL + "=\"" + username + "\";";

			ResultSet rs = stmt.executeQuery(query);
			rs.next();

			int numTaken = rs.getInt("AttemptsUser");
			con.close();
			return numTaken;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
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
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Achievement> getAchievementsForUser(String username) {
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
					+ " DESC;";

			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Achievement> resultList = new ArrayList<Achievement>();

			while(true) {
				Achievement newAchievement = parseAchievement(rs);
				if(newAchievement == null)
					break;
				else
					resultList.add(newAchievement);
			}
			con.close();
			return resultList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}


	}

	/**
	 * returns a set of the Achievements not earned by the user with the username passed in
	 * @param username username of the user to check
	 * @return HashSet<Achievement.Type> of the Achievements that the user has not yet earned.
	 */
	private static HashSet<Achievement.Type> getMissingAchievements(String username) {
		HashSet<Achievement.Type> missingAchievements = Achievement.getTypeSet();
		ArrayList<Achievement> currentAchievements = getAchievementsForUser(username);
		for(Achievement achievement : currentAchievements)
			missingAchievements.remove(achievement.getType());

		return missingAchievements;

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

	/**
	 * return the total number of times people have attempted quizzes
	 * @return number of attempts, -1 on error
	 */
	public static int getTotalAttempts() {
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

			String query = "SELECT SUM(" + NUMBER_OF_TIMES_TAKEN_COL + ") AS sum FROM " + MyDBInfo.QUIZ_TABLE 
					+ ";";
			
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int result = rs.getInt("sum");
			con.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int getNumQuizzes() {
		return getAllQuizzes().size();
	}

	//main method for testing
	public static void main(String[] args) {

		AccountManager.disableAccount("john");
		Account acct1 = AccountManager.getAccountByUsername("john");
		AccountManager.enableAccount("john");
		Account acct2 = AccountManager.getAccountByUsername("john");

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
				"sally", "History", tagList1, false, false, false, 0, 0, 0.0d,
				new Timestamp(System.currentTimeMillis()), 0, true);

		int result = QuizManager.storeQuizQuestionTags(quiz3);
		quiz3.setQuizID(result);

		QuizAttempt testAttempt = new QuizAttempt(1, "john", 60, new Timestamp(System.currentTimeMillis()), 70);
		QuizManager.storeAttempt(testAttempt);
		ArrayList<QuizAttempt> topAttempts = QuizManager.getTopAttemptsLastDay("1", 5);

		/*Achievement A1 = new Achievement("john", Achievement.Type.PRACTICE, "foo", new Timestamp(System.currentTimeMillis()));
		Achievement A2 = new Achievement("john", Achievement.Type.ONE_CREATED, "bar", new Timestamp(System.currentTimeMillis() + 2000));
		QuizManager.storeAchievement(A1);
		QuizManager.storeAchievement(A2);*/

		ArrayList<Achievement> achieveList = QuizManager.getAchievementsForUser("john");
		Quiz testQuery = QuizManager.getQuizById(result + "");
		
		ArrayList<Quiz> quizPopular = QuizManager.getMostPopularQuizzes(5);
		ArrayList<Quiz> quizRated = QuizManager.getHighestRatedQuizzes(5);
		int attemptCount = QuizManager.getTotalAttempts();

	}


}
