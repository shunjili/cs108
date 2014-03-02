package objects;

import java.util.ArrayList;

public class MultipleChoiceQuestion {
	private String question;
	private int answerIndex;
	private ArrayList<String> possibleAnswers;
	private String description;
	private Account creator;
	private Question.Type type;
	private int score;
	private long timestamp;
	
	
	public MultipleChoiceQuestion(String question, int answerIndex, ArrayList<String> possibleAnswers,
					String description, Account creator, int score, long timestamp) {
		this.question = question;
		this.answerIndex = answerIndex;
		this.possibleAnswers = possibleAnswers;
		this.description = description;
		this.creator = creator;
		this.type = Question.Type.MULTIPLE_CHOICE;
		this.score = score;
		this.timestamp = timestamp;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public int getAnswerIndex() {
		return this.answerIndex;
	}
	
	public ArrayList<String> getPossibleAnswers() {
		return this.possibleAnswers;
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
		return "MULTIPLE_CHOICE";
	}
	
	public int getScore() {
		return this.score;
	}
	
	public long getTimestamp() {
		return this.timestamp;
	}
	
	public boolean isCorrectAnswer(int answerIndex) {
		return (answerIndex == this.answerIndex);
	}
}
