package jp.go.saga.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Spring Security認証クラス
 * @author Donguk, YOON
 *
 */
@Component
public class CommonAuthenticationProvider implements AuthenticationProvider {

	@Autowired 
	PortalAuthenticationFactory authenticationFactory;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
			return SecurityContextHolder.getContext().getAuthentication(); 
		}
		try{
			return authenticationFactory.getAuthentication((CommonUser)authentication.getPrincipal(), null);
		}catch(ClassCastException ce){
			throw new UsernameNotFoundException("user name not found");
		}
	}

	
	@Override
	public boolean supports(Class<?> authentication) {
/*		if(authentication.isAssignableFrom(Principal.class) == false){
			throw new UsernameNotFoundException("user name not found.");
		}*/
		return true;
	}
	
}

