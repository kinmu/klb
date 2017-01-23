package jp.go.saga.common.message;

public abstract class MessageProperty {
	
	public MessageProperty(){}
	
	public abstract String getMessage(String key);
}