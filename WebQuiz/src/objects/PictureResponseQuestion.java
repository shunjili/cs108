package objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class PictureResponseQuestion implements Question{
	
	private String questionID;
	private String question;
	private String description;	//description is the img src
	private String creator_id;
	private Question.Type type;
	private int score;
	private Timestamp timestamp;
	
	public PictureResponseQuestion(String questionID, String question, String description,
			String creator_id, int score, Timestamp timestamp) {
		this.questionID = questionID;
		this.question = question;
		this.description = description;
		this.creator_id = creator_id;
		this.type = Question.Type.PIC_RESPONSE;
		this.score = score;
		this.timestamp = timestamp;
	}
	
	@Override
	public String getQuestionID() {
		return this.questionID;
	}
	
	@Override
	public String getQuestion() {
		return this.question;
	}

	@Override
	public String getDescription() {	// get img src
		return this.description;
	}

	@Override
	public String getCreatorID() {
		return this.creator_id;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public String getTypeString() {
		return "PIC_RESPONSE";
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String getTimestampString() {
		return this.timestamp.toString();
	}

	@Override
	public String getHTML(boolean showAnswer) {
		String html= " <div class=\"panel-body\">"+
 		question + 
 		"<div><img scr=\""+
 		description +
 		"\" alt=\""+ creator_id + "\"></div>" +
 		"<div class=\"input-group\">"+ 
			"<span class=\"input-group-addon\">Your Answer</span>"+
			 "<input name = \"" + questionID + "\"type=\"text\" class=\"form-control\" placeholder=\"You Anwer \">"+
			"</div>"+
			"</div>";
		if(showAnswer){
			ArrayList<String> answers = QuestionManager.getAnswers(questionID);
			if(answers != null){
				for(int i = 0; i < answers.size(); i++){
					html += String.format("<div class=\"panel-footer\">%s</div>", answers.get(i));
				}
			}
		}
		return html;
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		this.questionID = "" + id;
		
	}

	@Override
	public boolean isCorrect(ArrayList<String> userAnswers) {
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

	@Override
	public int getScore(ArrayList<String> userAnswers) {
		if(isCorrect(userAnswers)){
			return score;
		}
		return 0;
	}

	@Override
	public String getResultView(ArrayList<String> userAnswers) {
		ArrayList<String> answers = new ArrayList<String>();
		answers = QuestionManager.getAnswers(questionID);
		String htmlContent = "<div class=\"panel-body\">" + question;
		htmlContent += "<div><img scr=\""+ description + "\" alt=\""+ creator_id + "\"></div>";
		htmlContent += "<div class=\"input-group\">" ;
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
