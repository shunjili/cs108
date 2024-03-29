package objects;

import java.sql.Timestamp;
import java.util.HashSet;

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
	
	public Achievement(String username, Type type, Timestamp time_stamp) {
		this.username = username;
		this.type = type;
		this.description = "";
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
	
	public String getNameForType(Type input) {
		switch(input) {
		case ONE_CREATED: return "Novice Quiz Maker";
		case FIVE_CREATED: return "Prolific Quiz Maker";
		case TEN_CREATED: return "Quiz Making Connoisseur";
		case HIGH_SCORE: return "Genius";
		case TEN_TAKEN: return "Quiz Taking Machine";
		case PRACTICE: return "Practice Makes Perfect";
		default: return BAD_TYPE_STR;
		}
	}
	
	public String getDescriptionForType(Type input) {
		switch(input) {
		case ONE_CREATED: return "Created a quiz.";
		case FIVE_CREATED: return "Created 5 quizzes.";
		case TEN_CREATED: return "Created 10 quizzes.";
		case HIGH_SCORE: return "Got a high score on a quiz.";
		case TEN_TAKEN: return "Taken 10 quizzes.";
		case PRACTICE: return "Taken a quiz in practice mode";
		default: return BAD_TYPE_STR;
		}
	}
	
	public String getAchievementIconString() {
		switch(this.type) {
		case ONE_CREATED: return "glyphicon glyphicon-pencil";
		case FIVE_CREATED: return "glyphicon glyphicon-star-empty";
		case TEN_CREATED: return "glyphicon glyphicon-flash";
		case HIGH_SCORE: return "glyphicon glyphicon-search";
		case TEN_TAKEN: return "glyphicon glyphicon-fire";
		default: return "glyphicon glyphicon-cog";
		}
	}
	
	public static HashSet<Type> getTypeSet() {
		HashSet<Type> typeSet = new HashSet<Type>();
		for(Type type : Type.values())
			typeSet.add(type);
		return typeSet;
	}
	
}
