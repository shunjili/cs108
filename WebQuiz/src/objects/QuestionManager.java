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
	public static final String CREATOR_COL = "creator";
	public static final String SCORE_COL = "score";
	public static final String TIMESTAMP_COL = "time_stamp";
	

	public static Question.Type getTypeForString(String type) {
		if (type.equals(Question.FILL_IN_BLANK_STR)) {
			return Question.Type.FILL_IN_BLANK;
		} else if (type.equals(Question.MULTIPLE_CHOICE_STR)) {
			return Question.Type.MULTIPLE_CHOICE;
		} else if (type.equals(Question.QUESTION_RESPONSE_STR)) {
			return Question.Type.QUESTION_RESPONSE;
		} else return Question.Type.BAD_TYPE;
	}

	private static String getStringForType(Question.Type type) {
		switch(type) {
		case FILL_IN_BLANK: return Question.FILL_IN_BLANK_STR;
		case MULTIPLE_CHOICE: return Question.MULTIPLE_CHOICE_STR;
		case PIC_RESPONSE: return Question.PIC_RESPONSE_STR;
		case QUESTION_RESPONSE: return Question.QUESTION_RESPONSE_STR;
		default: return Question.BAD_TYPE_STR;
		}
	}
	
	
	/**
	 * Finds all questions belonging to the quiz with the ID passed in by performing
	 * and inner join on QuizQuestionsTable. Returns an array list of questions
	 * ordered by their index in the quiz.
	 * @param quiz_id Quiz id (quiz_id) to which the returned questions belong.
	 * @return ArrayList<Question> containing all the questions belonging to the quiz
	 * with the specified quiz_id.
	 */
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
			int question_id;
			String question;
			String description;
			String creator_id;
			int score;
			Timestamp timestamp;

			//get results and puts the questions into an arraylist.
			while(rs.next()) {
				type = getTypeForString(rs.getString(TYPE_COL));
				question_id = rs.getInt(QUESTION_ID_COL);
				String question_id_str = "" + question_id;
				question = rs.getString(QUESTION_COL);
				description = rs.getString(DESCRIPTION_COL);
				creator_id = rs.getString(CREATOR_COL);
				score = rs.getInt(SCORE_COL);
				timestamp = rs.getTimestamp(TIMESTAMP_COL);
				
				Question q = constructQuestion(type, question_id_str, question, description, creator_id,
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
	
	/**
	 * Writes the question passed in to the Questions table in the database.
	 * @param toStore
	 * @return true on successful write, false if writing to database was unsuccessful.
	 */
	public static boolean storeNewQuestion(Question toStore, String quiz_id, int index) {
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
			String query = "INSERT INTO " + MyDBInfo.QUESTIONS_TABLE
					+ " (" + QUESTION_COL
					+ "," + DESCRIPTION_COL
					+ "," + TYPE_COL
					+ "," + CREATOR_COL
					+ "," + SCORE_COL
					+ "," + TIMESTAMP_COL
					+ ") VALUES("
					+ "\"" + toStore.getQuestion() + "\""
					+ ",\"" + toStore.getDescription() + "\""
					+ ",\"" + getStringForType(toStore.getType()) + "\""
					+ ",\"" + toStore.getCreatorID() + "\""
					+ "," + toStore.getScore()
					+ ",\'" + toStore.getTimestamp().toString() + "\');";

			//execute the query
			int result = stmt.executeUpdate(query);
			
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
			
			rs.next();
			
			int questionId = rs.getInt("LAST_INSERT_ID()");
			
			
			
			//now, add to the QuizQuestionTable
			query = "INSERT INTO " + MyDBInfo.QUIZ_QUESTION_TABLE + " VALUES (\""
					+ quiz_id + "\",\"" + questionId + "\"," + index + ");";
			
			//execute the query to add to QuizQuestionTable
			result = stmt.executeUpdate(query);
			
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Question constructQuestion(Question.Type type, String question_id,
			String question, String description, String creator_id, int score,
			Timestamp timestamp) {
		System.out.println(getStringForType(type));
		switch (type) {
			case FILL_IN_BLANK: return new FillInBlankQuestion(question_id, question, description, creator_id, score, timestamp);
			case MULTIPLE_CHOICE: return new MultipleChoiceQuestion(question_id, question, description, creator_id, score, timestamp);
			case QUESTION_RESPONSE: return new QuestionResponseQuestion(question_id, question, description, creator_id, score, timestamp);
			default: return null;
		}
	}
	
	
// Main method to test QuestionManager
     public static void main(String[] args) {
//             Question test1 = new MultipleChoiceQuestion("1", "Who of the following was consul of Rome during the end of the Second Punic War?;
//                             "Cato the Elder;Cato the Younger;Julius Caesar;Scipio Africanus?", "This is a question about the Roman Republic.",
//                             "sally", 10, new Timestamp(System.currentTimeMillis()));
             
             Question test2 = new QuestionResponseQuestion("2", "Which Roman consul was defeated at the battle of Cannae?",
                             "This is a question about the Roman Republic","sally", 50, new Timestamp(System.currentTimeMillis()));
             
//             QuestionManager.storeNewQuestion(test1, "2", 1);
             QuestionManager.storeNewQuestion(test2, "2", 2);
             QuestionManager.getAnswers("1");
             
             ArrayList<String> userAnswers = new ArrayList<String>();
             userAnswers.add("haha");
 			 System.out.println(test2.getResultView(userAnswers ));
             ArrayList<Question> questionList = QuestionManager.getQuestionsForQuiz("2");
             Question test3 = questionList.get(0);
             Question test4 = questionList.get(1);

     }

	
	
	
	// for extension question types, userAnswer should be array list
	// for required type, there is only one string in userAnswer.
	public static ArrayList<String> getAnswers(String question_id){
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
			String query = "SELECT * FROM " + MyDBInfo.ANSWERS_TABLE
					+ " WHERE " + QUESTION_ID_COL + " = " 
					+ question_id +" ;";
			//execute the query
			
			ResultSet rs = stmt.executeQuery(query);	
			
			//Get correct answers
			ArrayList<String> answers = new ArrayList<String>();
			while(rs.next()){
				String ans = rs.getString("answer");
				answers.add(ans);
			}
			
			con.close();
			//return answers
			return answers;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
