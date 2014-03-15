package objects;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MultipleAnswerQuestion implements Question{
	private String questionID;
	private String question;
	private int description;	// number of answers
	private String creator_id;
	private Question.Type type;
	private int score;
	private Timestamp timestamp;
	
	public MultipleAnswerQuestion(String questionID, String question, String description, String creator_id,
			int score, Timestamp timestamp) {
		this.questionID = questionID;
		this.question = question;
		this.description = Integer.parseInt(description);
		this.creator_id = creator_id;
		this.type = Question.Type.MULTIPLE_ANSWER;
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
	public String getDescription() {
		return Integer.toString(this.description); // plz store the answer numbers
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
		return "MULTIPLE_ANSWER";
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
		// TODO Auto-generated method stub
		String html= " <div class=\"panel-body\">"+
 		question + 
 		"<div class=\"input-group\">"+ 
			"<span class=\"input-group-addon\">Your Answer</span>"+
			 "<input name = \"" + questionID + "\"type=\"text\" class=\"form-control\" placeholder=\"Your Answer Please Split by #\">"+
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
		this.questionID = "" + id;
	}
	@Override
	public boolean isCorrect(ArrayList<String> userAnswers) {
		if(userAnswers == null||userAnswers.size() <=0||userAnswers.contains(null)||userAnswers.size() < description){
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
