package jp.go.saga.aspect.request;

import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;

import jp.go.saga.aspect.common.AbstractParameterInterceptor;
import jp.go.saga.common.message.MessageProperty;

import bsmedia.app.common.RESecurityPattern;

public class XssPatternChecker extends AbstractParameterInterceptor {

	@Autowired
	MessageProperty messageProperty;
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public Object getModifiedArgument(Object argument) throws Exception{
		if (argument instanceof Map) {
			Iterator<String> itr = (((Map<String,Object>) argument).keySet()).iterator();
			while (itr.hasNext()) {
				Object value = ((Map) argument).get(itr.next());
				if (value instanceof String) {
					validateXSS((String) value);
				}
			}
		}
		return AbstractParameterInterceptor.IS_NOT_A_TARGET;
	}
	
	private void validateXSS(String value) throws ValidationException {
		if (value instanceof String) {
			String[] tempValue = new String[]{(String) value};

			if (RESecurityPattern.validateCrossSiteScripting(tempValue)) {
				throw new ValidationException(messageProperty.getMessage("F000"));
			}
			
			if (RESecurityPattern.validateSQLInjection(tempValue)) {
				throw new ValidationException(messageProperty.getMessage("F000"));
			}

			if (RESecurityPattern.validateServerSideInclude(tempValue)) {
				throw new ValidationException(messageProperty.getMessage("F001"));
			}
		}
	}

}
