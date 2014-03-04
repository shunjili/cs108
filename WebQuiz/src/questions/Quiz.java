package questions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import objects.Account;
import objects.MyDBInfo;



public class Quiz {
	// quiz_name, (fk) creator, description,time_stamp, category,
	// correct_immediately, one_page, random_order, number_of_times_taken,
	// number_of_reviews, average_rating
	
	//public enum QuizCategory {};		// sorry i do not know how to use enum, just save time, may be tell me later
	//private QuizCategory category;
	
	

	//private ArrayList<String> categoryoptions = new ArrayList<String>();	// put in html combo components
	//categoryoptions.add("Sport");
	//categoryoptions.add("Entertainment");
	//categoryoptions.add("Education");


	
	private int quizId;
	private String quizName;
	private String creator;
	private String description;
	private Timestamp createtime;
	private String category;
	private boolean correctImmediately;
	private boolean onePage;
	private boolean randomOrder;
	private int timesTaken;
	private ArrayList<Question> questions;	
	
	private int numberOfReview;
	private double rating;
	
	private int totalScore;// total score of the exam
	
	private ArrayList<String> tags;
	private ArrayList<String> Comments;
	
	
	private ArrayList<String> typeList;
	private ArrayList<Integer> question_idList;
	
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	private java.sql.Statement stmt;
	private ResultSet rs;
	private Connection con;
	
	
	public Quiz(int quizId, String quizName, String creator, String description, 
			Timestamp createTime, String category, boolean correctImmediately, 
			boolean onePage, boolean randomOrder, ArrayList<Question> questions){
		this.quizId = quizId;
		this.quizName = quizName;
		this.creator = creator;
		this.description = description;
		this.createtime = createTime;
		this.category = category;
		this.correctImmediately = correctImmediately;
		this.onePage = onePage;
		this.randomOrder = randomOrder;
		this.timesTaken = 0;
		this.questions = questions;
		this.totalScore = 0;
		for(int i = 0; i < questions.size(); i++){
			totalScore += questions.get(i).getQuestionScore();
		}
		this.numberOfReview = 0;
		this.tags = new ArrayList<String>();
		this.Comments = new ArrayList<String>();
		this.rating = 0;
		// no number of review no tag no rating no comments;
	}
	
	public Quiz(int quizId){	// all sql queries not tested include previous questions
		connect();
		try{
			String query = "SELECT * FROM Quizzes WHERE quiz_id =\"" + quizId + "\"";
			rs = stmt.executeQuery(query); // show all data
			rs.next();
			this.quizId = quizId;
			this.quizName = rs.getString("quiz_name");
			this.creator = rs.getString("creator");
			this.description = rs.getString("description");
			this.createtime = rs.getTimestamp("time_stamp");
			this.category = rs.getString("category");
			this.correctImmediately = rs.getBoolean("correct_immediately");
			this.onePage = rs.getBoolean("one_page");
			this.randomOrder = rs.getBoolean("random_order");
			this.timesTaken = rs.getInt("number_of_times_taken");
			this.numberOfReview = rs.getInt("number_of_reviews");
			this.rating = rs.getDouble("average_rating");
			
			query = "SELECT * FROM QuizQuestionTable INNER JOIN Questions " +
					"ON Questions.question_id = QuizQuestionTable.question_id "+
					"WHERE quiz_id =\"" + quizId + "ORDER BY question_index\"";
			rs = stmt.executeQuery(query); // show all data
			this.totalScore = 0;
			this.questions = new ArrayList<Question>();
			while(rs.next()){
				String type = rs.getString("type");
				this.typeList.add(type);
				int question_id = rs.getInt("question_id");
				this.question_idList.add(question_id);
				this.totalScore += rs.getInt("score");
				//this.questions.add(getQuestion(question_id, type));	// i found that i added connect to db in sub question class, may open twice and cause problems gonna fix it
			}
			
			query = "SELECT * FROM QuizTagTable WHERE quiz_id =\"" + quizId + "\"";
			rs = stmt.executeQuery(query); // show all data
			this.tags = new ArrayList<String>();
			while(rs.next()){
				tags.add(rs.getString("tag"));
			}
			
			query = "SELECT * FROM QuizReview WHERE quiz_id =\"" + quizId + "\"";
			rs = stmt.executeQuery(query); // show all data
			this.Comments = new ArrayList<String>();
			while(rs.next()){
				Comments.add(rs.getString("comment"));
			}
			
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		//fixed here
		for(int i = 0; i < question_idList.size(); i++){
			String type = typeList.get(i);
			int question_id = question_idList.get(i);
			this.questions.add(getQuestion(question_id, type));
		}
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
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}
	
	private Question getQuestion(int question_id, String type){
		Question ques = null;
		if(type.equals("QuestionResponseQuestions")){
			ques = new QuestionResponseQuestion(question_id);
		}else if(type.equals("FillInBlankQuestions")){
			ques = new FillInBlankQuestion(question_id);
		}else if(type.equals("MultipleChoiceQuestions")){
			ques = new MultipleChoiceQuestion(question_id);
		}else if(type.equals("PictureResponseQuestions")){
			ques = new PictureResponseQuestion(question_id);
		}
		return ques;
	}
	
	
	// no number of review no tag no rating;
	//not sure about the sql still in testing, need help
	public String createQuizTagQuery(int quiz_id, String oneTag) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO QuizTagTable VALUES(";
		query += "\"" + Integer.toString(quiz_id) + "\",";
		query += "\"" + oneTag + "\");";
		return query;
	}

	public String createQuizReviewQuery(int quiz_id, String username,String comment, int oneRating){
		String query = "INSERT INTO QuizReview VALUES(";
		query += "\"" + Integer.toString(quiz_id) + "\",";
		query += "\"" + username + "\",";
		query += "\"" + comment + "\",";
		query += Integer.toString(oneRating) + ");";
		return query;
	}
	
	public String updateQuizQuery(int quiz_id, int oneRating){
		numberOfReview++;
		double newAverage = ((double)(numberOfReview - 1) * rating + (double)oneRating)/((double)numberOfReview);
		String query = "UPDATE Quizzes SET average_rating = ";
		query += Double.toString(newAverage) + ",";	//so is double in sql, my metro sql works with "200", but the sql by young is without quotation
		query += "WHERE quiz_id = ";
		query += "\"" + Integer.toString(quiz_id) + "\";";
		return query;
	}
	
	public String createQuizQuery(){
		String query = "INSERT INTO Quizzess VALUES(";
		query += "\"" + Integer.toString(quizId) + "\",";
		query += "\"" + quizName + "\",";
		query += "\"" + creator + "\",";
		query += "\"" + description + "\",";
		query += "CURRENT_TIMESTAMP,";
		query += "\"" + category + "\",";
		query += Boolean.toString(correctImmediately) + ",";
		query += Boolean.toString(onePage) + ",";
		query += Boolean.toString(randomOrder) + ",";
		query += Integer.toString(timesTaken) + ",";
		query += Integer.toString(numberOfReview) + ",";
		query += Double.toString(rating) + ");";
		return query;
	}
	//may add more functions but now is enough
	public String createQuizQuestionQuery(int quizId, int questionId, int i){
		String query = "INSERT INTO QuizQuestionTable VALUES(";
		query += Integer.toString(quizId) + ","; 	
		query += Integer.toString(questionId) + ","; 
		query += Integer.toString(i) + ");"; 
		return query;	
	}
	
	public void addQuiz(){
		String query = createQuizQuery();
		connect();
		try{
			stmt.executeUpdate(query); 
			for(int i = 0; i < questions.size(); i++){
				query = createQuizQuestionQuery(quizId, questions.get(i).getId(), i);
				stmt.executeUpdate(query); 
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addQuestion(int quizId, int questionId, int i){
		String query = createQuizQuestionQuery(quizId, questionId,i);
		connect();
		try{
			stmt.executeUpdate(query); 
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addReviewRating(int quiz_id, String username,String comment, int oneRating){
		String statement = username;
		for(int i = 0; i < oneRating; i++){
			statement += "☆";
		}
		statement += "<br>" + "Comments" + comment;
		this.Comments.add(statement);
		String query = createQuizReviewQuery(quiz_id, username, comment, oneRating);
		connect();
		try{
			stmt.executeUpdate(query); 
			query = updateQuizQuery(quiz_id, oneRating);
			stmt.executeUpdate(query);
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void addTag(int quiz_id, String newtags){	//because user could type "funny;good"etc
		String[] parts = newtags.split(";");
		int len = parts.length;
		for(int i = 0; i < len ; i++){
			this.tags.add(parts[i]);
		}	
		String query;
		connect();
		try{
			for(int i = 0; i < tags.size(); i++){
				query = createQuizTagQuery(quiz_id,tags.get(i));
				stmt.executeUpdate(query); 
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public String getQuizName() {
		return this.quizName;
	}
	
	public String getQuizDescription() {
		return this.description;
	}
	
	public ArrayList<Question> getQuestions() {
		if (randomOrder) {
			randomizeQuestionOrder();
		}
		return questions;
	}
	
	private void randomizeQuestionOrder() {	// good Job JB!
		int len = questions.size();
		Random rgen = new Random();
		for (int i = 0; i < len; i++) {
			int random = rgen.nextInt(len);
			Question temp = questions.get(i);
			questions.set(i, questions.get(random));
			questions.set(random, temp);
		}
	}
	
	public String getQuizCreator() {
		return this.creator;
	}
	
	public String getQuizCategory() {
		return this.category;
	}
	
	public String getQuizTags() {		
		String htmlContent = "";
		int len = this.tags.size();
		if( len > 0){
			htmlContent = "<p>";
			for(int i = 0; i < len; i++){
				htmlContent += "<b>" + this.tags.get(i) + ";&nbsp;</b>";
			}
			htmlContent += "</p>";
		}		
		return htmlContent;
	}
	
	public String getQuizComments() {	
		String htmlContent = "";
		int len = this.Comments.size();
		if( len > 0){
			htmlContent = "<p>";
			for(int i = 0; i < len; i++){
				htmlContent += "<b>" + this.Comments.get(i) + ";&nbsp;</b>";
			}
			htmlContent += "</p>";
		}		
		return htmlContent;
	}
	
	public boolean isCorrectedImmediately() {
		return this.correctImmediately;
	}
	
	public boolean isOnePage() {
		return this.onePage;
	}
	
	public boolean isRandomOrder() {
		return this.randomOrder;
	}
	
	public int getTimesTaken() {
		return this.timesTaken;
	}
	
	public double getQuizRating() {
		return this.rating;
	}
	
	public String getQuizTimestamp() {
		return this.createtime.toString();
	}
	
	public String getQuizMultiPageViewtest(){
		questions = getQuestions();
		String htmlContent;
		htmlContent = "<h2>This is<b>&nbsp;" + quizName + "</b> Quiz</h2>";
		for(int i = 0; i < questions.size(); i++){
			htmlContent += questions.get(i).getView(i + 1);
			htmlContent += "<br>";
		}
		htmlContent = htmlContent.substring(0, htmlContent.length() - 4);
		return htmlContent;	
	}
	/*
	public String[] getAllCategory(){
		return categoryoptions;
	}
	
	public void addCategory(){
		categoryoptions.
	}
	*/
	public static void main(String[] args){
		int id = 1;
		String questions1 = "Computer brand";
		String questions2 = "is established in 1964";
		//ArrayList<String> answers = new ArrayList<String>();
		//answers.add("dell");
		String answers = "dell";
		String description = "Questions about about Computers";
		String creator = "Steven";
		int score = 5;
		Timestamp temp = new Timestamp(new Date().getTime());
		ArrayList<String> t = new ArrayList<String>();
		t.add("delp");
		FillInBlankQuestion test1 = new FillInBlankQuestion(id, questions1,questions2,answers,description, creator, score, temp);
		
		
		int id2 = 2;
		String questions3 = "Computer brands";
		String answers2 = "dell";
		String description2 = "https://lh6.googleusercontent.com/-qA6BZB27IPw/AAAAAAAAAAI/AAAAAAAAKYE/FpK1qXct5t4/s120-c/photo.jpg";
		String creator2 = "Steven";
		int score2 = 5;
		Timestamp temp2 = new Timestamp(new Date().getTime());
		PictureResponseQuestion test2 = new PictureResponseQuestion(id2, questions3,answers2,description2, creator2, score2, temp2);
		
		int id3 = 3;
		String questions4 = "Computer brands";
		String answers3 = "dell;hp";
		String description3 = "Questions about about Computers";
		String creator3 = "Steven";
		int score3 = 5;
		DateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp temp3 = new Timestamp(new Date().getTime());
		QuestionResponseQuestion test3 = new QuestionResponseQuestion(id3, questions4,answers3,description3, creator3, score3, temp3);
		
		int id4 = 4;
		String questions5 = "Computer brands";
		String answers4 = "haha";
		String description4 = "dell;hp;Apple";
		String creator4 = "Steven";
		int score4 = 5;
		DateFormat dateFormat4 = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp temp4 = new Timestamp(new Date().getTime());
		ArrayList<String> t4 = new ArrayList<String>();
		t4.add("hp");
		MultipleChoiceQuestion test4 = new MultipleChoiceQuestion(id4, questions5, answers4,
				description4, creator4, score4, temp4);
		
		
		int quizId = 1;
		String quizName = "test";
		String qcreator = "Shuhui";
		String qdescription = "test";
		Timestamp createTime = new Timestamp(new Date().getTime());
		String category = "brands";
		boolean correctImmediately = false; 
		boolean onePage = true;
		boolean randomOrder = true;
		ArrayList<Question> questions =  new ArrayList<Question>();
		questions.add(test1);
		questions.add(test2);
		questions.add(test3);
		questions.add(test4);
		Quiz testQuiz= new Quiz(quizId, quizName, qcreator, qdescription, 
				createTime, category, correctImmediately, 
				onePage, randomOrder, questions);
		System.out.println(testQuiz.getQuizMultiPageViewtest());
		
		
	}
}
