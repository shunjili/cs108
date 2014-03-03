package objects;

import java.sql.Timestamp;;

public class Message {
	
	private int id;
	private String sender;
	private String receiver;
	private String message;
	private Timestamp timestamp;
	
	public Message(String from, String to, String message) {
		this.id = -1;//-1 means message did not come from SQL table
		this.sender = from.toLowerCase();
		this.receiver = to.toLowerCase();
		this.message = message;
		this.timestamp = null;
	}
	
	public Message(int id, String from, String to, String message, Timestamp time_stamp) {
		this.id = id;
		this.sender = from.toLowerCase();
		this.receiver = to.toLowerCase();
		this.message = message;
		this.timestamp = time_stamp;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getSender() {
		return this.sender;
	}
	
	public String getReceiver() {
		return this.receiver;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	
	public String getTimeString() {
		return timestamp.toString();
	}

}
