package me.lecter.whitealbum.client.exceptions;

public class RiotRatelimitException extends RiotException {
	
	public RiotRatelimitException(String msg) {
		super(msg);
	}

	public RiotRatelimitException() {
		this("rate limited");
	}
}
