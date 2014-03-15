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
			 "<input name = \"" + question_id + "\"type=\"text\" class=\"form-control\" placeholder=\"Please seperate your answers with #\">"+
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
		if(userAnswers == null||userAnswers.size() <=0||userAnswers.contains(null)){
			return false;
		}else{
			//ArrayList<String> answers = new ArrayList<String>();
			//answers = QuestionManager.getAnswers(question_id);
			ArrayList<String> answers = new ArrayList<String>();
			ArrayList<String> answertemp = new ArrayList<String>();
			answertemp = QuestionManager.getAnswers(question_id);
			for(int i = 0; i < answertemp.size(); i++){
				answers.add(answertemp.get(i).toLowerCase());
			}
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
		
		String htmlContent = "<div class=\"panel-body\">" + question;
		htmlContent += "<table class=\"table\">";
		
		htmlContent += "<thead><tr><th>Correct Answer</th><th>Your Answer</th></tr></thead>";
		htmlContent += "<tbody><tr><td>";

		for(int i = 0; i < answers.size(); i++){
//			htmlContent += Integer.toString(i + 1) + ".";
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
