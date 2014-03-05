package objects;

import objects.Account.Type;

import java.sql.Timestamp;

public interface Question {
	public enum Type{QUESTION_RESPONSE, FILL_IN_BLANK, MULTIPLE_CHOICE, PIC_RESPONSE, BAD_TYPE};
	public static final String QUESTION_RESPONSE_STR = "QUESTION_REPSONSE";
	public static final String FILL_IN_BLANK_STR = "FILL_IN_BLANK";
	public static final String MULTIPLE_CHOICE_STR = "MULTIPLE_CHOICE";
	public static final String PIC_RESPONSE_STR = "PIC_RESPONSE";
	public static final String BAD_TYPE_STR = "BAD_TYPE";
	
			
			
	
	public String getQuestionID();
	public String getQuestion();
	public String getDescription();
	public String getCreatorID();
	public Question.Type getType();
	public String getTypeString();
	public int getScore();
	public Timestamp getTimestamp();
	public String getTimestampString();
	public String getHTML(boolean showAnswer);
}
