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
	
	public void setID(int id) {
		this.questionID = "" + id;
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
				html +=String.format("<div class=\"input-group\"><span class=\"input-group-addon\"><input type=\"radio\" name = %s value = %d></span><label type=\"text\" class=\"form-control\">%s</label></div><br>", questionID, i+1, choices[i]) ;
			}
		}
		
		html += "</div>";
		if(answer){
			ArrayList<String> answers = QuestionManager.getAnswers(questionID);
			if(answers != null){
				for(int i = 0; i < answers.size(); i++){
					html += String.format("<div class=\"panel-footer\">%s</div>", answers.get(i));
				}
			}
		}
		return html;
	}
	
	public boolean isCorrect(ArrayList<String> userAnswers){		
		ArrayList<String> answers = new ArrayList<String>();
		answers = QuestionManager.getAnswers(questionID);
		//check answer might have multiple user answers 
		int len = userAnswers.size();
		boolean flag = true;
		for(int i = 0; i < len; i++){
			// if any of the solutions are not in the answer then it's false
			if(!answers.contains(userAnswers.get(i).trim())){
				System.out.println(userAnswers.get(i));
				flag = false;
				break;
			}
		}		
		//return flag
		return flag;	
	}

	public int getScore(ArrayList<String> userAnswers){
		if(isCorrect(userAnswers)){
			return score;
		}
		return 0;
		
	}
	public String getResultView(ArrayList<String> userAnswers){
		
		ArrayList<String> answers = new ArrayList<String>();
		answers = QuestionManager.getAnswers(questionID);
		
		String htmlContent = "<div class=\"panel-body\">" + questionPrefix;
		htmlContent += "<table class=\"table\">";
		
		htmlContent += "<thead><tr><th>Correct Answer</th><th>Your Answer</th></tr></thead>";
		htmlContent += "<tbody><tr><td>";

		for(int i = 0; i < answers.size(); i++){
//			htmlContent += Integer.toString(i + 1) + ".";
			htmlContent += choices[Integer.parseInt(answers.get(i))-1];
			htmlContent += "&nbsp;";
		}		
		htmlContent += "</td><td>";

		if(userAnswers == null){
			htmlContent += "";
			htmlContent += "&nbsp;"; 
		}else{
			for(int i = 0; i < userAnswers.size(); i++){;
				htmlContent += choices[Integer.parseInt(userAnswers.get(i)) - 1];
				htmlContent += "&nbsp;";
			}	

		}
		htmlContent += "</td></tr></tbody></table>";
		htmlContent +="</div>";
		return htmlContent;
		
	}
}
