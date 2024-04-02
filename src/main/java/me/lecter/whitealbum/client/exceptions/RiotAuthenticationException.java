package me.lecter.whitealbum.client.exceptions;

public class RiotAuthenticationException extends RiotException {
	
	public RiotAuthenticationException(String msg) {
		super(msg);
	}

	public RiotAuthenticationException() {
		this("Failed to authenticate. Make sure username and password are correct");
	}
}
