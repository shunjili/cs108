package objects;

public class Account {
	
	public enum Type{USER, ADMIN};
	
	
	private String username;
	private String displayname;
	private Type userType;
	
	public Account(String username) {
		this.username = username;
		this.displayname = username;
		this.userType = Type.USER;
	}
	
	public Account(String username, String displayname, Type userType) {
		this.username = username;
		this.displayname = displayname;
		this.userType = userType;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getDisplayName() {
		return this.displayname;
	}
	
	public Type getType() {
		return this.userType;
	}
	
	public String getTypeString() {
		if(this.userType.equals(Type.ADMIN))
			return "ADMIN";
		else
			return "USER";
	}
	
	public static Type stringToType(String typeString) {
		if(typeString.equals("ADMIN"))
			return Type.ADMIN;
		else
			return Type.USER;
	}
	
}
