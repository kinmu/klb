package jp.go.saga.aspect.request;

import java.util.Map;

import javax.xml.bind.ValidationException;

import jp.go.saga.aspect.common.AbstractParameterInterceptor;
import jp.go.saga.common.Constants;
import jp.go.saga.common.message.MessageProperty;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("rawtypes")
public class ParameterEscaper extends AbstractParameterInterceptor {

	@Autowired
	MessageProperty messageProperty;
	
	@Override
	public Object getModifiedArgument(Object argument) throws Exception{
		if (argument instanceof Map) {
			// T_SORT_BY ESCAPE
			String T_SORT_BY = MapUtils.getString((Map)argument, Constants.T_SORT_BY);
			if(StringUtils.isNotEmpty(T_SORT_BY) && escape(T_SORT_BY) == null){
				((Map)argument).remove(Constants.T_SORT_BY);
			}
		}
		return AbstractParameterInterceptor.IS_NOT_A_TARGET;
	}
	
	private String escape(String value) throws ValidationException {
		if(value.matches("^[A-Za-z0-9_]+$") == false){
			return null;
		} else {
			return value;
		}
	}

}
