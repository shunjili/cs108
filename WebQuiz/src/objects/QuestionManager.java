package objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;

import objects.Question.Type;

public class QuestionManager {

	public static final String QUIZ_ID_COL = "quiz_id";
	
	public static final String TYPE_COL = "type";
	public static final String QUESTION_ID_COL = "question_id";
	public static final String QUESTION_COL = "question";
	public static final String DESCRIPTION_COL = "description";
	public static final String CREATOR_ID_COL = "creator";
	public static final String SCORE_COL = "score";
	public static final String TIMESTAMP_COL = "timestamp";

	private static Question.Type getTypeForString(String type) {
		if (type.equals("FILL_IN_BLANK")) {
			return Question.Type.FILL_IN_BLANK;
		} else if (type.equals("MULTIPLE_CHOICE")) {
			return Question.Type.MULTIPLE_CHOICE;
		} else if (type.equals("QUESTION_RESPONSE")) {
			return Question.Type.QUESTION_RESPONSE;
		} else return Question.Type.BAD_TYPE;
	}

	public static ArrayList<Question> getQuestionsForQuiz(String quiz_id) {
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_QUESTION_TABLE +
					" INNER JOIN " + MyDBInfo.QUESTIONS_TABLE +
					" ON Questions.question_id = QuizQuestionTable.question_id " +
					"WHERE quiz_id = \"" + quiz_id + "\" ORDER BY question_index;";

			//execute the query
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<Question> questions = new ArrayList<Question>();
			Question.Type type;
			String question_id;
			String question;
			String description;
			String creator_id;
			int score;
			Timestamp timestamp;

			//get results and puts the questions into an arraylist.
			while(rs.next()) {
				type = getTypeForString(rs.getString(TYPE_COL));
				question_id = rs.getString(QUESTION_ID_COL);
				question = rs.getString(QUESTION_COL);
				description = rs.getString(DESCRIPTION_COL);
				creator_id = rs.getString(CREATOR_ID_COL);
				score = rs.getInt(SCORE_COL);
				timestamp = rs.getTimestamp(TIMESTAMP_COL);
				
				Question q = constructQuestion(type, question_id, question, description, creator_id,
						score, timestamp);
				questions.add(q);
			}

			// closes the connection
			con.close();
			
			// if no questions were found, returns null, otherwise returns the array list of questions
			if (questions.isEmpty()) {
				return null;
			} else return questions;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Question constructQuestion(Question.Type type, String question_id,
			String question, String description, String creator_id, int score,
			Timestamp timestamp) {
		switch (type) {
			case FILL_IN_BLANK: return new FillInBlankQuestion(question_id, question, description, creator_id, score, timestamp);
			case MULTIPLE_CHOICE: return new MultipleChoiceQuestion(question_id, question, description, creator_id, score, timestamp);
			case QUESTION_RESPONSE: return new QuestionResponseQuestion(question_id, question, description, creator_id, score, timestamp);
			default: return null;
		}
	}
	
	

}
