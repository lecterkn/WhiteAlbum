package me.lecter.whitealbum.client.exceptions;

public class RiotUnknownException extends RiotException {
	
	public RiotUnknownException(String msg) {
		super(msg);
	}

	public RiotUnknownException() {
		this("Got unknown error");
	}
}
