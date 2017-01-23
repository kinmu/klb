package jp.go.saga.common.message;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import bsmedia.system.config.ApplicationProperty;

@Service
public class SimpleMessageProperty extends MessageProperty {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageProperty.class);
	
	static Properties props;


	public SimpleMessageProperty() {
		init();
	}

	private void init(){
		props =  new Properties();
		try {
			String locale = ApplicationProperty.get("message.locale");
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/message/"+locale+".properties"));
		} catch (Exception e) {
			if (logger.isWarnEnabled())
				logger.warn(e.toString());
			else
				e.printStackTrace();
		}
	}
	
	@Override
	public String getMessage(String key) {
		try{
			return props.getProperty(key);
		}catch(NullPointerException ne){
			init();
			return props.getProperty(key);
		}
	}

	/*
	public String getMessage(String key, String[] args) {
		try{
			
			return String.format(props.getProperty(key), args);
		}catch(NullPointerException ne){
			init();
			return String.format(props.getProperty(key), args);
		}
	}
	*/
}