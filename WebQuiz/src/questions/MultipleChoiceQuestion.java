package questions;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import objects.Account;
import objects.MyDBInfo;

import com.mysql.jdbc.Statement;




public class MultipleChoiceQuestion extends Question{
	private static int DEFAULT_SCORE = 5;
	private String question;
	private ArrayList<String> answers;
	private ArrayList<String> choices;
	private String description;
	private String creator;
	private String type;
	private int score;
	private Timestamp timestamp;
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	private java.sql.Statement stmt;
	private ResultSet rs;
	private Connection con;
	
	public MultipleChoiceQuestion(int question_id, String question, String answer,
			String description, String creator, int score, Timestamp timestamp) {
		super(question_id);
		this.question = question;
		this.answers = new ArrayList<String>();
		this.choices = new ArrayList<String>();
		this.answers.add(answer);
		this.description = description;
		String[] parts = description.split(";");
		int len = parts.length;
		for(int i = 0; i < len ; i++){
			choices.add(parts[i]);
		}
		this.creator = creator;
		this.type = "MultipleChoiceQuestion";
		this.score = score;
		this.timestamp = timestamp;
	}
	
	public MultipleChoiceQuestion(String question, String answer,
			String description, String creator) {
		super();
		this.question = question;
		this.answers = new ArrayList<String>();
		this.choices = new ArrayList<String>();
		this.answers.add(answer);
		this.description = description;
		String[] parts = description.split(";");
		int len = parts.length;
		for(int i = 0; i < len ; i++){
			choices.add(parts[i]);
		}
		this.creator = creator;
		this.type = "MultipleChoiceQuestion";
		this.score = DEFAULT_SCORE;
		this.timestamp = new Timestamp(new Date().getTime());
	}
	
	public MultipleChoiceQuestion(int question_id) {
		
		super(question_id);
		choices = new ArrayList<String>();
		String query = "SELECT * FROM Questions INNER JOIN Answers " +
				"ON Questions.question_id = Answers.question_id " +
				"WHERE question_id =\"" + question_id + "\" AND type = QuestionResponseQuestion";
		connect();
		try{
			rs = stmt.executeQuery(query); // show all data
			rs.next();
			question = rs.getString("question");
			answers.add(rs.getString("answer"));
			description = rs.getString("description");
			String[] parts = description.split(";");
			int len = parts.length;
			for(int i = 0; i < len ; i++){
				choices.add(parts[i]);
			}
			description = rs.getString("description");
			creator = rs.getString("creator");
			type = rs.getString("type");
			score = rs.getInt("score");
			timestamp = rs.getTimestamp("time_stamp");
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void connect(){
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
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}
/*
	public String getQuestion() {
		return this.question;
	}
	
	public ArrayList<String> getAnswers() {
		return this.answers;
	}
*/
	@Override
	public String getCreator() {
		return creator;
	}
	
/*
	public String getTypeString() {
		return "FILL_IN_BLANK";
	}
*/
	
	private boolean isCorrectAnswer(ArrayList<String> userAnswers) {
		return this.answers.contains(userAnswers.get(0).trim().toLowerCase());
	}
	@Override
	public int getScore(ArrayList<String> userAnswers) {
		if(isCorrectAnswer(userAnswers)){
			return score;
		}
		return 0;
	}
	
	@Override
	public int getQuestionScore() {
			return score;
	}
	
	@Override
	public String getTimestamp() {
		return timestamp.toString();
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
		//return null;
	}
	@Override
	public String getView(int QuesinQuiz) {
		// TODO Auto-generated method stub
		String htmlContent = "<p>Q" + Integer.toString(QuesinQuiz) + ": " + question + "</p>";
		for(int i = 0; i < choices.size(); i++){
			htmlContent += "<input type=\"radio\" name=\"ans"+ Integer.toString(this.getId()) +"\" value=\"" + choices.get(i) + "\"> " + choices.get(i) + "<br>";
		}
		return htmlContent;
	}
	@Override
	public String getAnsView(int QuesinQuiz) { // guest mode
		// TODO Auto-generated method stub
		String htmlContent = "<p>Q" + Integer.toString(QuesinQuiz) + ": " + question + "</p>";
		htmlContent += "<p>";
		for(int i = 0; i < choices.size(); i++){
			htmlContent += (char)(i + 65) + ". " + choices.get(i) + "<br>";
		}
		htmlContent +="<p>&nbsp;&nbsp;&nbsp;<b>Answer:</b>&nbsp;";
		for(int i = 0; i < answers.size(); i++){
			htmlContent += Integer.toString(i + 1) + ".";
			htmlContent += answers.get(i);
			htmlContent += "<br>";
		}
		htmlContent +="</p>";
		return htmlContent;
		//return null;
	}
	@Override
	public String getResultView(int QuesinQuiz, ArrayList<String> userAnswers) {
		// TODO Auto-generated method stub
		String htmlContent = "<p>Q" + Integer.toString(QuesinQuiz) + ": " + question + "</p>";
		htmlContent += "<p>";
		for(int i = 0; i < choices.size(); i++){
			htmlContent += (char)(i + 65) + ". " + choices.get(i) + "<br>";
		}
		htmlContent +="<p>&nbsp;&nbsp;&nbsp;<b>Right Answer:</b>&nbsp;";
		for(int i = 0; i < answers.size(); i++){
			htmlContent += Integer.toString(i + 1) + ".";
			htmlContent += answers.get(i);
			htmlContent += "&nbsp;";
		}
		htmlContent +="</p>";
		htmlContent +="<p><b>Your answer:&nbsp;</b> " + userAnswers.get(0) + "&nbsp;";
		if(isCorrectAnswer(userAnswers)){
			htmlContent += "<b>is correct!</b></p>";
		}else{
			htmlContent += "<b>is wrong! Sorry!</b></p>";
		}
		return htmlContent;
	}
	@Override
	public ArrayList<String> getUserAnswer(HttpServletRequest request,
			int QuesinQuiz) {
		// TODO Auto-generated method stub
		ArrayList<String> userAnswers = new ArrayList<String>();
		String ans = request.getParameter("ans" + Integer.toString(QuesinQuiz));
		if(ans.equals(null)){
			ans = "";
		}
		userAnswers.add(ans);
		return userAnswers;
	}
	@Override
	public String getDescription() {
		return description;
		//return null;
	}
	@Override
	public String createQuestionQuery(int question_id) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO Questions VALUES(";
		query += "\"" + Integer.toString(question_id) + "\",";
		query += "\"" + question + "\",";
		query += "\"" + description + "\",";
		query += "\"" + type + "\",";
		query += "\"" + creator + "\",";
		query += Integer.toString(score) + ","; //confused with INT parameter in sql
		timestamp = new Timestamp(new Date().getTime());
		query += "CURRENT_TIMESTAMP);"; //so is CURRENT_TIMESTAMP
		return query;
	}
	@Override
	public String createQuestionAnswerQuery(int question_id, String ans){
		String query = "INSERT INTO Questions VALUES(";
		query += "\"" + Integer.toString(question_id) + ", \"";
		query += "\"" + ans + "\");";
		return query;
	}
	
	@Override
	public void addQuestionAndAnswer(int question_id) {
		// TODO Auto-generated method stub
		String query = createQuestionQuery(question_id);
		connect();
		try{
			stmt.executeUpdate(query); // show all data
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		try{
			for(int i = 0; i < answers.size(); i++){//we assume at lease one answer
				query = createQuestionAnswerQuery(question_id,answers.get(i));
				stmt.executeUpdate(query);
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	@Override
	public void addAnswer(int question_id, ArrayList<String> anses){
		String query;
		connect();
		try{
			for(int i = 0; i < anses.size(); i++){//we assume at lease one answer
				query = createQuestionAnswerQuery(question_id,anses.get(i));
				stmt.executeUpdate(query);
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = score;
	}

	@Override
	public String createUpdateScoreQuery(int question_id, int score){
		// TODO Auto-generated method stub
		String query = "UPDATE Questions SET score = ";
		query += Integer.toString(score) + " WHERE question_id = ";
		query +="\"" + Integer.toString(question_id) + "\";";
		return query;
	}

	@Override
	public void updateScore(int question_id) {
		// TODO Auto-generated method stub
		String query = createUpdateScoreQuery(question_id, this.score);
		connect();
		try{
			stmt.executeUpdate(query); // show all data
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		int id = 1;
		String questions = "Computer brands";
		String answers = "haha";
		ArrayList<String> sol = new ArrayList<String>();
		sol.add("hah11a");
		String description = "dell;hp;Apple";
		String creator = "Steven";
		int score = 5;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp temp = new Timestamp(new Date().getTime());
		ArrayList<String> t = new ArrayList<String>();
		t.add("hp");
		MultipleChoiceQuestion test = new MultipleChoiceQuestion(id, questions, answers,
				description, creator, score, temp);
		System.out.println(test.getView(2));
		System.out.println(test.getResultView(2,sol));
//		return 0;
	}
}
