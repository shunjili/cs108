package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Timestamp;

public class MultipleChoiceQuestion implements Question {
	private String questionID;
	private String question;
	private String questionPrefix;
	private String description;
	private String creator_id;
	private Question.Type type;
	private int score;
	private Timestamp timestamp;
	private String[] choices;
	
	
	public MultipleChoiceQuestion(String questionID, String question, String description,
			String creator_id, int score, Timestamp timestamp) {
		this.questionID = questionID;	
		parseQuestionAndChoices(question);
		this.description = description;
		this.creator_id = creator_id;
		this.type = Question.Type.MULTIPLE_CHOICE;
		this.score = score;
		this.timestamp = timestamp;
	}
	public boolean parseQuestionAndChoices(String questionString){
		String[] stringArray = questionString.split("#");
		question = questionString;
		if(stringArray.length < 2){
			questionPrefix = questionString;
			choices = null;
			return false;
		}else{
			questionPrefix = stringArray[0];
			choices = Arrays.copyOfRange(stringArray, 1, stringArray.length);
			return true;
		}
	}
	
	public int getNumberOfChoices(){
		return choices.length;
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

	public String getHTML(boolean answer) {
		String html =  " <div class=\"panel-body\">"+ questionPrefix;

		if(choices != null){
			for (int i = 0; i < choices.length; i++){
				html +=String.format("<div class=\"input-group\"><span class=\"input-group-addon\"><input type=\"radio\" name = %s ></span><label type=\"text\" class=\"form-control\">%s</label></div><br>", questionID, choices[i]) ;
			}
		}
		
		html += "</div>";
		if(answer){
			html += String.format("<div class=\"panel-footer\">%s</div>", "This is a dummy answer");
		}
		return html;
	}
}
