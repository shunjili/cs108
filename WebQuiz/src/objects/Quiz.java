package objects;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

public class Quiz {
	// quiz_name, (fk) creator, description,time_stamp, category,
	// correct_immediately, one_page, random_order, number_of_times_taken,
	// number_of_reviews, average_rating
	
	//public enum QuizCategory {};
	
	private String name;
	private String description;
	private ArrayList<Question> questions;
	private String creator;
	private String category;
	private ArrayList<String> tags;
	private boolean correctImmediately;
	private boolean onePage;
	private boolean randomOrder;
	private int timesTaken;
	private int numReviews;
	private double rating;
	private Timestamp timestamp;
	
	public Quiz(String name, String description, ArrayList<Question> questions, 
			String creator, String category, ArrayList<String> tags,
			boolean correctImmediately, boolean onePage, boolean randomOrder,
			int timesTaken, int numReviews, double rating, Timestamp timestamp) {
		this.name = name;
		this.description = description;
		this.questions = questions;
		this.creator = creator;
		this.category = category;
		this.tags = tags;
		this.correctImmediately = correctImmediately;
		this.onePage = onePage;
		this.randomOrder = randomOrder;
		this.timesTaken = timesTaken;
		this.numReviews = numReviews;
		this.rating = rating;
		this.timestamp = timestamp;
	}
	
	public String getQuizName() {
		return this.name;
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
	
	private void randomizeQuestionOrder() {
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
	
	public ArrayList<String> getQuizTags() {
		return this.tags;
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
	
	public int getNumReviews() { 
		return this.numReviews;
	}
	
	public double getQuizRating() {
		return this.rating;
	}
	
	public Timestamp getQuizTimestamp() {
		return this.timestamp;
	}
	
	public String getQuizTimestampString() {
		return this.timestamp.toString();
	}
	
	
}
