package jp.go.saga.aspect.common;

import java.lang.annotation.Annotation;

import javax.xml.bind.ValidationException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

public class ParameterAdvice implements MethodInterceptor{

	private AbstractParameterInterceptor[] parameterInterceptors;

	private static final Logger logger = LoggerFactory.getLogger(ParameterAdvice.class);

	@Override
	public final Object invoke(MethodInvocation invocation) throws Throwable {

		Annotation[][] aaa = invocation.getMethod().getParameterAnnotations();

		logger.debug("#### invoke 実行Start ####");
		try {
			int argIdx = 0;
			for(Annotation[] aa : aaa){
				for(Annotation a : aa){
					// 実行されるメソッドにて@RequestParamタイプのオブジェクトを検索
					if(a instanceof RequestParam){
						Object argument = invocation.getArguments()[argIdx];

						logger.debug("#### @RequestParamタイプ ####"+ String.valueOf(argument));

						for(AbstractParameterInterceptor p : parameterInterceptors){
							Object o = p.getModifiedArgument(argument);
							if(o != AbstractParameterInterceptor.IS_NOT_A_TARGET){
								argument = o;
							}
						}
					}
				}
				argIdx++;
			}

		} catch (ValidationException ve) {
			logger.error("### KLB ValidationException ### : ", ve);

			throw ve;

		} catch (Exception e) {
			logger.error("### KLB EXCEPTION ### : ", e);

			throw e;
		}

		logger.debug("#### invoke 実行 End !! ####");
		//return invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
		return invocation.proceed();
	}


	public AbstractParameterInterceptor[] getParameterInterceptors() {
		return parameterInterceptors;
	}

	public void setParameterInterceptors(AbstractParameterInterceptor[] parameterInterceptors) {
		this.parameterInterceptors = parameterInterceptors;
	}
}