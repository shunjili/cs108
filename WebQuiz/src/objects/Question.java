package objects;

import objects.Account.Type;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface Question {
	public enum Type{QUESTION_RESPONSE, FILL_IN_BLANK, MULTIPLE_CHOICE, PIC_RESPONSE, BAD_TYPE};
	public static final String QUESTION_RESPONSE_STR = "QUESTION_RESPONSE";
	public static final String FILL_IN_BLANK_STR = "FILL_IN_BLANK";
	public static final String MULTIPLE_CHOICE_STR = "MULTIPLE_CHOICE";
	public static final String PIC_RESPONSE_STR = "PIC_RESPONSE";
	public static final String BAD_TYPE_STR = "BAD_TYPE";
	public static final int MAX_NUM_CHOICES =4;
	
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
	public void setID(int id);
	
	public boolean isCorrect(ArrayList<String> userAnswers);
	// for extension question types, userAnswer should be array list
	// for required type, there is only one string in userAnswer.
	public int getScore(ArrayList<String> userAnswers);
	public String getResultView( ArrayList<String> userAnswers);
}
