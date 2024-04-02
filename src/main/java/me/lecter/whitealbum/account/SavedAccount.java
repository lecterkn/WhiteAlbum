package me.lecter.whitealbum.account;

public class SavedAccount {
	
	private String username;
	private String access_token;
	private String entitlements_token;

	private SavedAccount(String username, String access_token, String entitlements_token) {
		this.username = username;
		this.access_token = access_token;
		this.entitlements_token = entitlements_token;
	}

	public static SavedAccount create(String username, String access_token, String entitlements_token) {
		return new SavedAccount(username, access_token, entitlements_token);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccess_token() {
		return this.access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getEntitlements_token() {
		return this.entitlements_token;
	}

	public void setEntitlements_token(String entitlements_token) {
		this.entitlements_token = entitlements_token;
	}
}
