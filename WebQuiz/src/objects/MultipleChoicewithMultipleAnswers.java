package objects;

import java.sql.Timestamp;

public class MultipleChoicewithMultipleAnswers extends MultipleChoiceQuestion{
	
	public MultipleChoicewithMultipleAnswers(String questionID, String question, String description,
			String creator_id, int score, Timestamp timestamp) {
		super( questionID,  question,  description,
				 creator_id,  score,  timestamp);
	}
	
	public Question.Type getType() {
		return Question.Type.MULTIPLE_CHOICE_MULTIPLE_ANSWER;
	}
	
	public String getTypeString() {
		return "MULTIPLE_CHOICE_MULTIPLE_ANSWER";
	}
}
