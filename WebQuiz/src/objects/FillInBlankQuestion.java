package objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FillInBlankQuestion implements Question {
	private String questionID;
	private String question;
	private String description;
	private String creator_id;
	private Question.Type type;
	private int score;
	private Timestamp timestamp;
	
	
	public FillInBlankQuestion(String questionID, String question, String description,
			String creator_id, int score, Timestamp timestamp) {
		this.questionID = questionID;
		this.question = question;
		this.description = description;
		this.creator_id = creator_id;
		this.type = Question.Type.FILL_IN_BLANK;
		this.score = score;
		this.timestamp = timestamp;
	}
	
	public String getQuestionID() {
		return this.questionID;
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
		return "FILL_IN_BLANK";
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
	
	@Override
	public String getHTML(boolean showAnswer) {
		// TODO Auto-generated method stub
		return " <div class=\"panel-body\">"+
 		question + 
 		"<div class=\"input-group\">"+ 
			"<span class=\"input-group-addon\">Your Answer</span>"+
			 "<input type=\"text\" class=\"form-control\" placeholder=\"Username\">"+
			"</div>"+
			"</div>";
	}
}
