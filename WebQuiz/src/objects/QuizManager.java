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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_TABLE + " WHERE "
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

}
