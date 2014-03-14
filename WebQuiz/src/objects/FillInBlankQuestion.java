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
		question = question.replaceAll("#", "_");
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
			 "<input name = \"" + questionID + "\" type=\"text\" class=\"form-control\" placeholder=\"Please seperate your answers with #\">"+
			"</div>"+
			"</div>";
	}
	
	public boolean isCorrect(ArrayList<String> userAnswers){
		if(userAnswers == null||userAnswers.size() <=0||userAnswers.contains(null)){
			return false;
		}else{
			ArrayList<String> answers = new ArrayList<String>();
			answers = QuestionManager.getAnswers(questionID);
			//check answer might have multiple user answers 
			int len = userAnswers.size();
			boolean flag = true;
			for(int i = 0; i < len; i++){
				// if any of the solutions are not in the answer then it's false
				if(!answers.contains(userAnswers.get(i).trim().toLowerCase())){
					flag = false;
					break;
				}
			}		
			//return flag
			return flag;
		}	
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
		
		String htmlContent = "<div class=\"panel-body\">" + question ;
		htmlContent += "<table class=\"table\">";
		
		htmlContent += "<thead><tr><th>Correct Answer</th><th>Your Answer</th></tr></thead>";
		htmlContent += "<tbody><tr><td>";

		for(int i = 0; i < answers.size(); i++){
			htmlContent += Integer.toString(i + 1) + ".";
			htmlContent += answers.get(i);
			htmlContent += "&nbsp;";
		}
		
		htmlContent += "</td><td>";

		if(userAnswers == null||userAnswers.contains(null)){
			htmlContent += "";
			htmlContent += "&nbsp;"; 
		}else{
			for(int i = 0; i < userAnswers.size(); i++){;
				htmlContent += userAnswers.get(i);
				htmlContent += "&nbsp;";
			}	
		}
		htmlContent += "</td></tr></tbody></table>";
		htmlContent +="</div>";
		return htmlContent;
		
	}
}
