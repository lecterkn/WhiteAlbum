package me.lecter.whitealbum.client.body;

public class AuthBody {
	
	private String acr_values;
	private String claims;
	private String client_id;
	private String code_challenge;
	private String code_challenge_method;
	private String nonce;
	private String redirect_uri;
	private String response_type;
	private String scope;

	public AuthBody(String acr_values, String claims, String client_id, String code_challenge, String code_challenge_method, String nonce, String redirect_uri, String response_type, String scope) {
		this.acr_values = acr_values;
		this.claims = claims;
		this.client_id = client_id;
		this.code_challenge = code_challenge;
		this.code_challenge_method = code_challenge_method;
		this.nonce = nonce;
		this.redirect_uri = redirect_uri;
		this.response_type = response_type;
		this.scope = scope;
	}

	public AuthBody() {
		this("", "", "riot-client", "", "", "1", "http://localhost/redirect", "token id_token", "openid link ban lol_region account");
	}

	public String getAcr_values() {
		return this.acr_values;
	}

	public void setAcr_values(String acr_values) {
		this.acr_values = acr_values;
	}

	public String getClaims() {
		return this.claims;
	}

	public void setClaims(String claims) {
		this.claims = claims;
	}

	public String getClient_id() {
		return this.client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getCode_challenge() {
		return this.code_challenge;
	}

	public void setCode_challenge(String code_challenge) {
		this.code_challenge = code_challenge;
	}

	public String getCode_challenge_method() {
		return this.code_challenge_method;
	}

	public void setCode_challenge_method(String code_challenge_method) {
		this.code_challenge_method = code_challenge_method;
	}

	public String getNonce() {
		return this.nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getRedirect_uri() {
		return this.redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getResponse_type() {
		return this.response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
