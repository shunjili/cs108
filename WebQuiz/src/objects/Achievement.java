package objects;

import java.sql.Timestamp;

public class Achievement {

	public static enum Type {ONE_CREATED, FIVE_CREATED, TEN_CREATED, HIGH_SCORE, TEN_TAKEN, PRACTICE, BAD_TYPE};
	public static String ONE_CREATED_STR = "ONE_CREATED";
	public static String FIVE_CREATED_STR = "FIVE_CREATED";
	public static String TEN_CREATED_STR = "TEN_CREATED";
	public static String HIGH_SCORE_STR = "HIGH_SCORE";
	public static String TEN_TAKEN_STR = "TEN_TAKEN";
	public static String PRACTICE_STR = "PRACTICE";
	public static String BAD_TYPE_STR = "BAD_TYPE";
	
	private String username;
	private Type type;
	private String description;
	private Timestamp time_stamp;
	
	public Achievement(String username, Type type, String description, Timestamp time_stamp) {
		this.username = username;
		this.type = type;
		this.description = description;
		this.time_stamp = time_stamp;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public String getTypeStr() {
		return getStringForType(this.type);
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Timestamp getTimestamp() {
		return this.time_stamp;
	}
	
	public static Achievement.Type getTypeForString(String input) {
		if(input.equals(ONE_CREATED_STR))
			return Achievement.Type.ONE_CREATED;
		else if(input.equals(FIVE_CREATED_STR))
			return Achievement.Type.FIVE_CREATED;
		else if(input.equals(TEN_CREATED_STR))
			return Achievement.Type.TEN_CREATED;
		else if(input.equals(HIGH_SCORE_STR))
			return Achievement.Type.HIGH_SCORE;
		else if(input.equals(TEN_TAKEN_STR))
			return Achievement.Type.TEN_CREATED;
		else if(input.equals(PRACTICE_STR))
			return Achievement.Type.PRACTICE;
		else
			return Achievement.Type.BAD_TYPE;
	}
	
	public static String getStringForType(Type input) {
		switch(input) {
		case ONE_CREATED: return ONE_CREATED_STR;
		case FIVE_CREATED: return FIVE_CREATED_STR;
		case TEN_CREATED: return TEN_CREATED_STR;
		case HIGH_SCORE: return HIGH_SCORE_STR;
		case TEN_TAKEN: return TEN_TAKEN_STR;
		case PRACTICE: return PRACTICE_STR;
		default: return BAD_TYPE_STR;
		}
	}
	
}
