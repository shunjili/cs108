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
				ArrayList<Question> questionList = QuestionManager.getQuestionsForQuiz(quiz_id);
				ArrayList<String> tags = getTagsForQuiz(quiz_id);
				return new Quiz(quizName, description, questionList, creator, category, tags,
						correctImmediately, onePage, randomOrder, numberOfTimesTaken, numberOfReviews, averageRating, timestamp);
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Adds the quiz, AND it's Questions and Tags to the database.
	 * @param toStore
	 * @return
	 */
	public static boolean storeQuizQuestionTags(Quiz toStore) {
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
				if(!QuestionManager.storeNewQuestion(toStore.getQuestions().get(i), quiz_id_str, i))
					return false;
			}
			
			for(String tag : toStore.getQuizTags()) {
				if(!addTagToQuiz(quiz_id_str, tag))
					return false;
			}
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
					+ "\"" + quiz_id + "\",\"" + tag + "\");";
			
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
		
		boolean result = QuizManager.storeQuizQuestionTags(quiz3);
	
	}
	

}
