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
	@Override
	public String getHTML(boolean showAnswer) {
		
		
		String html= " <div class=\"panel-body\">"+
 		question + 
 		"<div class=\"input-group\">"+ 
			"<span class=\"input-group-addon\">Your Answer</span>"+
			 "<input name = \"" + question_id + "\"type=\"text\" class=\"form-control\" placeholder=\"You Anwer \">"+
			"</div>"+
			"</div>";
		if(showAnswer){
			ArrayList<String> answers = QuestionManager.getAnswers(question_id);
			if(answers != null){
				for(int i = 0; i < answers.size(); i++){
					html += String.format("<div class=\"panel-footer\">%s</div>", answers.get(i));
				}
			}
		}
		return html;
	}

	@Override
	public String getQuestionID() {
		return this.question_id;
	}
	
	@Override
	public void setID(int id) {
		this.question_id = "" + id;
	}
	
	public boolean isCorrect(ArrayList<String> userAnswers){		
		ArrayList<String> answers = new ArrayList<String>();
		answers = QuestionManager.getAnswers(question_id);
		//check answer might have multiple user answers 
		int len = userAnswers.size();
		boolean flag = true;
		for(int i = 0; i < len; i++){
			// if any of the solutions are not in the answer then it's false
			if(!answers.contains(userAnswers.get(i).trim())){
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
		answers = QuestionManager.getAnswers(question_id);
		String htmlContent = "<div class=\"panel-body\">" + question + "<div class=\"input-group\">" ;
		htmlContent += "<span class=\"input-group-addon\">Correct Answer</span>";
		htmlContent += "<span class=\"input-group-addon\">";
		for(int i = 0; i < answers.size(); i++){
//			htmlContent += Integer.toString(i + 1) + ".";
			htmlContent += answers.get(i);
			htmlContent += "&nbsp;";
		}
		htmlContent += "</span>";
		htmlContent +="</div><br>";
		htmlContent +="<div class=\"input-group\">";
		htmlContent +="<span class=\"input-group-addon\">Your Answer</span>";
		if(userAnswers == null){
			htmlContent += "<span class=\"input-group-addon\">" ;
			htmlContent += "no answers which ";
			htmlContent += "&nbsp;" ; 
		}else{
			for(int i = 0; i < userAnswers.size(); i++){
				htmlContent += "<span class=\"input-group-addon\">" ;
				htmlContent += userAnswers.get(i);
				htmlContent += "&nbsp;" ;
			}	
		}
		if(isCorrect(userAnswers)){
			htmlContent += "<b>is correct!</b></span>";
		}else{
			htmlContent += "is wrong.</b></span>";
		}
		htmlContent +="</div></div>";
		return htmlContent;
		
	}

}
