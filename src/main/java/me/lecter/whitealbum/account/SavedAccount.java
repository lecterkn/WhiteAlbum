package me.lecter.whitealbum.account;

public class SavedAccount {
	
	private String username;
	private String password;
	
	
	
	private SavedAccount(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public static SavedAccount create(String username, String password) {
		return new SavedAccount(username, password);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
}
