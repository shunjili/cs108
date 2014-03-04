package questions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import objects.Account;
import objects.MyDBInfo;


public abstract class Question {
	public enum Type{QUESTION_RESPONSE, FILL_IN_BLANK, MULTIPLE_CHOICE, PIC_RESPONSE};
	
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	private java.sql.Statement stmt;
	private ResultSet rs;
	private Connection con;
	
	private int id;	//question id
	
	
	public Question(int question_id){
		this.id = question_id;
	}
	public Question(){
		int question_id = 0;
		String query = "SELECT * FROM Questions ORDER BY question_id DESC";
		connect();
		try{
			rs = stmt.executeQuery(query); // show all data
			rs.next();
			question_id = Integer.parseInt(rs.getString("question_id")) + 1;
			con.close();
		}catch(SQLException e){
			//e.printStackTrace();
		}
		this.id = question_id;
		
	}
	public int getId(){
		return id;
	}
	
	private void connect(){
		try{
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection
				( "jdbc:mysql://" + server, account ,password);
			
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			
			//rs = stmt.executeQuery("SELECT * FROM metropolises");
			//rs.absolute(3);
			//System.out.print(rs.getString("metropolis"));
			
		}catch(SQLException e){
			//e.printStackTrace();
		}catch(ClassNotFoundException e){
			//e.printStackTrace();
		}
		
	}
	
	public abstract void setScore(int score);
	public abstract String createUpdateScoreQuery(int question_id, int score);
	public abstract void updateScore(int question_id);
	
	public abstract String getType(); //find most the same could put here,but need put more parameter here.
	public abstract int getScore(ArrayList<String> userAnswers);
	public abstract String getTimestamp();//find most the same could put here,but need put more parameter here.
	public abstract String getView(int QuesinQuiz);
	public abstract String getAnsView(int QuesinQuiz);
	public abstract String getResultView(int QuesinQuiz, ArrayList<String> userAnswers);
	public abstract ArrayList<String> getUserAnswer(HttpServletRequest request, int QuesinQuiz);
	public abstract String getDescription();//find most the same could put here,but need put more parameter here.
	public abstract String getCreator();//find most the same could put here,but need put more parameter here.
	
	
	public abstract String createQuestionQuery(int question_id);//find most the same could put here,but need put more parameter here.
	public abstract String createQuestionAnswerQuery(int question_id,String ans);//find most the same could put here,but need put more parameter here.
	public abstract int getQuestionScore();//find most the same could put here,but need put more parameter here.
	public abstract void addQuestionAndAnswer(int question_id);//find most the same could put here,but need put more parameter here.
	public abstract void addAnswer(int question_id, ArrayList<String> anses);	//find most the same could put here
}
