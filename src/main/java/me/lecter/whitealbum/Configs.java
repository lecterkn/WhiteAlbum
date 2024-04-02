package me.lecter.whitealbum;

public class Configs {
	
	private String webhook_url;
	private String avatar_url;
	private String varification_code;
	private boolean autocheck_accounts;

	public String getWebhook_url() {
		return this.webhook_url;
	}

	public void setWebhook_url(String webhook_url) {
		this.webhook_url = webhook_url;
	}

	public String getAvatar_url() {
		return this.avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public boolean isAutocheck_accounts() {
		return this.autocheck_accounts;
	}

	public void setAutocheck_accounts(boolean autocheck_accounts) {
		this.autocheck_accounts = autocheck_accounts;
	}

	public String getVarification_code() {
		return this.varification_code;
	}

	public void setVarification_code(String varification_code) {
		this.varification_code = varification_code;
	}
}
