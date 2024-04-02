package me.lecter.whitealbum.client.body;

public class LoginBody {
	
	private String language;
	private String password;
	private String region;
	private boolean remember;
	private String type;
	private String username;

	public LoginBody(String language, String password, String region, boolean remember, String type, String username) {
		this.language = language;
		this.password = password;
		this.region = region;
		this.remember = remember;
		this.type = type;
		this.username = username;
	}

	public LoginBody(String username, String password) {
		this("en_US", password, (String)null, false, "auth", username);
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public boolean isRemember() {
		return this.remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
