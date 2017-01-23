package jp.go.saga.security;

@SuppressWarnings("serial")
public class LoginException extends RuntimeException{

	public LoginException() {
	}

	public LoginException(String msg) {

		super(msg);
	}
}
