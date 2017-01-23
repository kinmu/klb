package jp.go.saga.aspect.common;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

public class AnnotationBasedResourceInjector implements MethodInterceptor{
	
	@Autowired
	HttpServletRequest request;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Decorator decorator = invocation.getMethod().getAnnotation(Decorator.class);
		if(decorator != null){
			request.setAttribute("pageDecorator", decorator);
		}
		
		return invocation.proceed();
	}

}
