package objects;

import objects.Account.Type;

import java.sql.Timestamp;

public interface Question {
	public enum Type{QUESTION_RESPONSE, FILL_IN_BLANK, MULTIPLE_CHOICE, PIC_RESPONSE, BAD_TYPE};
	
	public String getQuestionID();
	public String getQuestion();
	public String getDescription();
	public String getCreatorID();
	public Question.Type getType();
	public String getTypeString();
	public int getScore();
	public Timestamp getTimestamp();
	public String getTimestampString();
	public String getHTML();
}
