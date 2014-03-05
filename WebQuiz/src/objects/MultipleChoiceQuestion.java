package objects;

import java.util.ArrayList;
import java.sql.Timestamp;

public class MultipleChoiceQuestion implements Question {
	private String questionID;
	private String question;
	private String description;
	private String creator_id;
	private Question.Type type;
	private int score;
	private Timestamp timestamp;
	
	
	public MultipleChoiceQuestion(String questionID, String question, String description,
			String creator_id, int score, Timestamp timestamp) {
		this.questionID = questionID;
		this.question = question;
		this.description = description;
		this.creator_id = creator_id;
		this.type = Question.Type.MULTIPLE_CHOICE;
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
		return "MULTIPLE_CHOICE";
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
		String html =  " <div class=\"panel-body\">"+ question;
		for (int i = 0; i < 4; i++){
			html += "<div class=\"input-group\"><span class=\"input-group-addon\"><input type=\"radio\"></span><label type=\"text\" class=\"form-control\">This is choice one!</label></div><br>";
		}
		html += "</div>";
		return html;
	}
}
