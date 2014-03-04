package objects;

import java.util.ArrayList;

public class FillInBlankQuestion implements Question {
	private String question;
	private ArrayList<String> answers;
	private String description;
	private Account creator;
	private Question.Type type;
	private int score;
	private long timestamp;
	
	
	public FillInBlankQuestion(String question, ArrayList<String> answers,
					String description, Account creator, int score, long timestamp) {
		this.question = question;
		this.answers = answers;
		this.description = description;
		this.creator = creator;
		this.type = Question.Type.FILL_IN_BLANK;
		this.score = score;
		this.timestamp = timestamp;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public ArrayList<String> getAnswers() {
		return this.answers;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Account getCreator() {
		return this.creator;
	}
	
	public Question.Type getType() {
		return this.type;
	}
	
	public String getTypeString() {
		return "FILL_IN_BLANK";
	}
	
	public int getScore() {
		return this.score;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}
	
	public boolean isCorrectAnswer(String answer) {
		return this.answers.contains(answer);
	}
	
	public String getHTML() {
		return "";
	}
}
