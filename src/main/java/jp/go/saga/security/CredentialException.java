package jp.go.saga.security;

import java.util.Map;

import jp.go.saga.service.portal.LoginLogService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Exception between the credentials from SSO and the local user database.
 * @author Donguk, YOON
 *
 */
public class CredentialException extends RuntimeException {
	@Autowired
	LoginLogService loginLogService;

	public CredentialException() {
	}

	public CredentialException(String msg){
		super(msg);
	}

	public CredentialException(String msg, Map<String,Object> credentialMap) {
		super(msg);
		//loginLogService.insertLoginLogDetail(credentialMap);
	}
}
