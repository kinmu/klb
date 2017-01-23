package jp.go.saga.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class PortalAuthenticationToken extends AbstractAuthenticationToken{

    private UserDetails principal;

	public PortalAuthenticationToken(UserDetails principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
	}

	@Override
	public Object getCredentials() {
		return null;
	}
	@Override
	public UserDetails getPrincipal() {
		return this.principal;
	}
}