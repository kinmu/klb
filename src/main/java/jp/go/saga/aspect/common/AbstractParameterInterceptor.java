package jp.go.saga.aspect.common;

public abstract class AbstractParameterInterceptor {

	public static final Object IS_NOT_A_TARGET = new Object();
	
	public abstract Object getModifiedArgument(Object argument) throws Exception;
}
