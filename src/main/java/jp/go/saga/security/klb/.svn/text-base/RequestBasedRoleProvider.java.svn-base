package jp.go.saga.security.klb;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.go.saga.common.Constants;
import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.security.CommonUser;
import jp.go.saga.security.PortalAuthenticationService;
import jp.go.saga.security.PortalAuthenticationToken;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Controller Class 의 클래스 혹은 메서드의 @AdditionalRoleStatement 를 
 * 캐치해 현재 사용자가 Document의 소유자인지 여부를 판단.
 * @author gbun
 *
 */
@SuppressWarnings({"rawtypes"})
@Component
public class RequestBasedRoleProvider implements MethodInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(RequestBasedRoleProvider.class);
	
	@Autowired
	CommonDao dao;
	@Autowired
	PortalAuthenticationService authenticationService;


	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if(SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CommonUser == false){
			return invocation.proceed();
		}

		// 1. method
		AdditionalRoleStatement assr = invocation.getMethod().getAnnotation(AdditionalRoleStatement.class);
		// 2. class
		if(assr == null){
			assr = invocation.getClass().getAnnotation(AdditionalRoleStatement.class);
		}
		if(assr == null){
			return invocation.proceed();
		}
		
		Map paramMap = null;
		Annotation[][] aaa = invocation.getMethod().getParameterAnnotations();
		int argIdx = 0;
		for(Annotation[] aa : aaa){
			for(Annotation a : aa){
				if(a instanceof RequestParam){
					Object argument = invocation.getArguments()[argIdx];
					if(argument instanceof Map){
						paramMap = (Map)argument;
					}
				}
			}
			argIdx++;
		}	
		
		boolean isDocumentOwner = this.isDocumentOwner(assr.value(), paramMap);
		
		try {
			CommonUser commonUser = (CommonUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
			
			if(isDocumentOwner == true){
				authorities.add(new SimpleGrantedAuthority(Constants.ROLE_DOCUMENT_OWNER));
			} else {
				authorities.remove(new SimpleGrantedAuthority(Constants.ROLE_DOCUMENT_OWNER));
			}
			
			logger.debug("GRANDTED_AUTHORITIES: " + authorities);
			
			System.out.println("##########################");
			SecurityContextHolder.getContext().setAuthentication(new PortalAuthenticationToken(commonUser, authorities));
			SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
			
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
			System.out.println("##########################");
		}catch (Exception e){
			e.printStackTrace();
			//ignore			
		}		
		return invocation.proceed();
	}
	
	private boolean isDocumentOwner(String statementName, Map paramMap){
		int result = dao.queryForInteger(statementName, paramMap);
		return result > 0 ? true : false;
	}

}
