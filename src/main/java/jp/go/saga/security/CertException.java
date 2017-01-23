package jp.go.saga.security;

@SuppressWarnings("serial")
public class CertException extends RuntimeException {

	public CertException() {}

	public CertException(String msg) {
		super(msg);
	}
}