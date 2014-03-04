package objects;

import objects.Account.Type;

public interface Question {
	public enum Type{QUESTION_RESPONSE, FILL_IN_BLANK, MULTIPLE_CHOICE, PIC_RESPONSE};
	
	public String getHTML();
}
