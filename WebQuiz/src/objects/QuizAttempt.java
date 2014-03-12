package objects;

import java.sql.Timestamp;

public class QuizAttempt {
	
	
	private int quiz_id;
	private String username;
	private int score;
	private Timestamp start_time;
	private long duration;
	
	public QuizAttempt(int quiz_id, String username, int score, Timestamp start_time, long duration) {
		this.quiz_id = quiz_id;
		this.username = username;
		this.score = score;
		this.start_time = start_time;
		this.duration = duration;
	}
	
	public int getQuizID() {
		return this.quiz_id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Timestamp getStartTime() {
		return this.start_time;
	}
	
	public String getStartTimeStr() {
		return this.start_time.toString();
	}
	
	public long getDuration() {
		return this.duration;
	}

}
