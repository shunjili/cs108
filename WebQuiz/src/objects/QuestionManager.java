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
	
	public static final String INDEX_COL = "question_index";
	
	public static final String ANSWERS_QUESTION_ID_COL = "question_id";
	public static final String ANSWER_COL = "answer";

	public static Question.Type getTypeForString(String type) {
		if (type.equals(Question.FILL_IN_BLANK_STR)) {
			return Question.Type.FILL_IN_BLANK;
		} else if (type.equals(Question.MULTIPLE_CHOICE_STR)) {
			return Question.Type.MULTIPLE_CHOICE;
		} else if (type.equals(Question.QUESTION_RESPONSE_STR)) {
			return Question.Type.QUESTION_RESPONSE;
		} else if (type.equals(Question.PIC_RESPONSE_STR)) {
			return Question.Type.PIC_RESPONSE;
		}else if (type.equals(Question.MULTIPLE_ANSWER_STR)) {
			return Question.Type.MULTIPLE_ANSWER;
		}else return Question.Type.BAD_TYPE;
	}

	private static String getStringForType(Question.Type type) {
		switch(type) {
		case FILL_IN_BLANK: return Question.FILL_IN_BLANK_STR;
		case MULTIPLE_CHOICE: return Question.MULTIPLE_CHOICE_STR;
		case PIC_RESPONSE: return Question.PIC_RESPONSE_STR;
		case QUESTION_RESPONSE: return Question.QUESTION_RESPONSE_STR;
		case MULTIPLE_ANSWER: return Question.MULTIPLE_ANSWER_STR;
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
					"WHERE quiz_id =" + quiz_id + " ORDER BY question_index;";

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
				if(q != null)
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
	public static int storeNewQuestion(Question toStore, String quiz_id, int index) {
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
			return questionId;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Same as the above function except that it has an additional parameter to store the answer 
	 * together with the question
	 * @param toStore
	 * @param quiz_id
	 * @param index
	 * @param answer
	 * @return
	 */
	public static int storeNewQuestion(Question toStore, String quiz_id, int index, String answer){
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
			
			
			//insert into the answers table
			query = "INSERT INTO " + MyDBInfo.ANSWERS_TABLE + " VALUES ("
					+ "" + questionId + ",\"" + answer + "\");" ;
			
			result = stmt.executeUpdate(query);
			
			//now, add to the QuizQuestionTable
			query = "INSERT INTO " + MyDBInfo.QUIZ_QUESTION_TABLE + " VALUES (\""
					+ quiz_id + "\",\"" + questionId + "\"," + index + ");";
			
			//execute the query to add to QuizQuestionTable
			result = stmt.executeUpdate(query);
			
			con.close();
			return questionId;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	/**
	 * Same as the above function except that it has an additional parameter to store the answer 
	 * together with the question
	 * @param toStore
	 * @param quiz_id
	 * @param index
	 * @param answer
	 * @return
	 */
	public static int storeNewQuestionMultiple(Question toStore, String quiz_id, int index, String[] answers){
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
			
			
			//insert into the answers table
			for(String answer : answers) {
				//insert into the answers table
				query = "INSERT INTO " + MyDBInfo.ANSWERS_TABLE + " VALUES ("
						+ "" + questionId + ",\"" + answer + "\");" ;
				
				result = stmt.executeUpdate(query);
			}
			
			//now, add to the QuizQuestionTable
			query = "INSERT INTO " + MyDBInfo.QUIZ_QUESTION_TABLE + " VALUES ("
					+ quiz_id + "," + questionId + "," + index + ");";
			
			//execute the query to add to QuizQuestionTable
			result = stmt.executeUpdate(query);
			
			con.close();
			return questionId;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	

	public static Question constructQuestion(Question.Type type, String question_id,
			String question, String description, String creator_id, int score,
			Timestamp timestamp) {
		//System.out.println(getStringForType(type));
		switch (type) {
			case FILL_IN_BLANK: 
				return new FillInBlankQuestion(question_id, question, description, creator_id, score, timestamp);
			case MULTIPLE_CHOICE: return new MultipleChoiceQuestion(question_id, question, description, creator_id, score, timestamp);
			case QUESTION_RESPONSE: return new QuestionResponseQuestion(question_id, question, description, creator_id, score, timestamp);
			case PIC_RESPONSE: return new PictureResponseQuestion(question_id, question, description, creator_id, score, timestamp);
			case MULTIPLE_ANSWER: return new MultipleAnswerQuestion(question_id, question, description, creator_id, score, timestamp);
			default: return null;
		}
	}
	
	
// Main method to test QuestionManager
     public static void main(String[] args) {
//             Question test1 = new MultipleChoiceQuestion("1", "Who of the following was consul of Rome during the end of the Second Punic War?;
//                             "Cato the Elder;Cato the Younger;Julius Caesar;Scipio Africanus?", "This is a question about the Roman Republic.",
//                             "sally", 10, new Timestamp(System.currentTimeMillis()));
             
             Question test2 = new QuestionResponseQuestion("2", "Name one of Hamical Barca's sons (first name only).",
                             "This is a question about the Second Punic War.","sally", 50, new Timestamp(System.currentTimeMillis()));
             
             Question test3 = new QuestionResponseQuestion("2", "Carthage's principal dieties were Ba'al Hammon and what other god?",
            		 "This is a question on Carthaginian culture.","sally", 50, new Timestamp(System.currentTimeMillis()));
             
//             QuestionManager.storeNewQuestion(test1, "2", 1);
             
             String[] answers = {"Hannibal", "Hasdrubal","Mago"};
             
             int returnID = QuestionManager.storeNewQuestion(test3, "2", 2, "Tanit");
             test3.setID(returnID);
             
             returnID = QuestionManager.storeNewQuestionMultiple(test2, "2", 2, answers);
             test2.setID(returnID);
             
             returnID = QuestionManager.updateQuestion(test2, "2", answers);
             test2.setID(returnID);
             
             deleteQuestion(test3.getQuestionID(), "2");
             
             ArrayList<String> testAnswers = QuestionManager.getAnswers(test2.getQuestionID());
             System.out.println(testAnswers.toString());
             
             testAnswers = QuestionManager.getAnswers(test3.getQuestionID());
             System.out.println(testAnswers.toString());
             
             ArrayList<String> userAnswers = new ArrayList<String>();
             userAnswers.add("haha");
 			// System.out.println(test2.getResultView(userAnswers ));
             ArrayList<Question> questionList = QuestionManager.getQuestionsForQuiz("2");
            
             Question test4 = questionList.get(0);
             Question test5 = questionList.get(1);
            
             

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

	/**
	 * Deletes question with the specified question ID. Also removes question from the QuizQuestionsTable
	 * and from the Answers table;
	 * @param question_id
	 * @param quiz_id
	 * @return
	 */
	public static boolean deleteQuestion(String question_id, String quiz_id) {
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
			String query = "DELETE FROM " + MyDBInfo.QUIZ_QUESTION_TABLE + " WHERE "
					+ QUESTION_ID_COL + "=" + question_id + ";";
			//execute the query
			int result= stmt.executeUpdate(query);	
			
			query = "DELETE FROM " + MyDBInfo.ANSWERS_TABLE + " WHERE "
					+ ANSWERS_QUESTION_ID_COL + "=" + question_id + ";";
			result = stmt.executeUpdate(query);
			
			query = "DELETE FROM " + MyDBInfo.QUESTIONS_TABLE + " WHERE "
					+ QUESTION_ID_COL + "=" + question_id + ";";
			result = stmt.executeUpdate(query);
			
			con.close();
			//return answers
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeQuestionFromQuiz(String question_id, String quiz_id) {
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
			String query = "DELETE FROM " + MyDBInfo.QUIZ_QUESTION_TABLE + " WHERE "
					+ QUESTION_ID_COL + "=" + question_id + ";";
			//execute the query
			int result= stmt.executeUpdate(query);	
			
			query = "DELETE FROM " + MyDBInfo.ANSWERS_TABLE + " WHERE "
					+ ANSWERS_QUESTION_ID_COL + "=" + question_id + ";";
			result = stmt.executeUpdate(query);
			
			
			con.close();
			//return answers
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Updates the question with the specified parameters. 
	 * @param toUpdate
	 * @param quiz_id
	 * @param answers
	 * @return
	 */
	public static int updateQuestion(Question toUpdate, String quiz_id, String[] answers) {
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
			String query = "SELECT * FROM " + MyDBInfo.QUIZ_QUESTION_TABLE + " WHERE "
					+ QUESTION_ID_COL + "=" + toUpdate.getQuestionID() + ";";
			//execute the query
			ResultSet rs = stmt.executeQuery(query);
			
			int index = 1;
			while(rs.next()) {
				index = rs.getInt(INDEX_COL);
			}
		
			deleteQuestion(toUpdate.getQuestionID(), quiz_id);			
			int result = storeNewQuestionMultiple(toUpdate, quiz_id, index, answers);
			
			
			con.close();
			//return answers
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
