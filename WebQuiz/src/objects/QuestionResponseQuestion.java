package objects;

import java.util.ArrayList;
import java.sql.Timestamp;

public class QuestionResponseQuestion implements Question {
	private String question_id;
	private String question;
	private String description;
	private String creator_id;
	private Question.Type type;
	private int score;
	private Timestamp timestamp;
	
	
	public QuestionResponseQuestion(String question_id, String question, String description, String creator_id,
			int score, Timestamp timestamp) {
		this.question_id = question_id;
		this.question = question;
		this.description = description;
		this.creator_id = creator_id;
		this.type = Question.Type.QUESTION_RESPONSE;
		this.score = score;
		this.timestamp = timestamp;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getCreatorID() {
		return this.creator_id;
	}
	
	public Question.Type getType() {
		return this.type;
	}
	
	public String getTypeString() {
		return "QUESTION_RESPONSE";
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public String getTimestampString() {
		return this.timestamp.toString();
	}
	
	public String getHTML() {
		
		return " <div class=\"panel-body\">"+
 		question + 
 		"<div class=\"input-group\">"+ 
			"<span class=\"input-group-addon\">Your Answer</span>"+
			 "<input type=\"text\" class=\"form-control\" placeholder=\"Username\">"+
			"</div>"+
			"</div>";
	}

	@Override
	public String getQuestionID() {
		return this.question_id;
	}
}
