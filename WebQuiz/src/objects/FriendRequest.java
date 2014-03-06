package objects;

import java.sql.Timestamp;

public class FriendRequest {
	private String requester;
	private String requested;
	private String message;
	private Timestamp timestamp;
	
	public FriendRequest(String requester, String requested, String message) {
		this.requester = requester;
		this.requested = requested;
		this.message = message;
		this.timestamp = null;
	}
	
	public FriendRequest(String requester, String requested, String message, Timestamp timestamp) {
		this.requester = requester;
		this.requested = requested;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	public String getRequester() {
		return this.requester;
	}
	
	public String getRequested() {
		return this.requested;
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
