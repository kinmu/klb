/**
 * Project Name :
 * Copyright(c) 2010 by SkySoft
 * Create on 2010. 9. 2.
 */
package bsmedia.system.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObjectUtils {
	@SuppressWarnings("unchecked")
	public static Object copy(Object obj){
		if(obj instanceof Map){
			Map<String,Object> copyObj = new HashMap<String,Object>();
			Set<?> keys = ((Map<String,Object>)obj).keySet();
			for (Object object : keys) {
				String key = (String)object;
				copyObj.put(key, ((Map<String,Object>)obj).get(key));
			}
			return copyObj;
		}
		return null;
	}


	public static void copyMap(Map<String,Object> fromMap, Map<String,Object> toMap) {
		Set<String> keys = fromMap.keySet();
		for (String key : keys) {
			toMap.put(key, fromMap.get(key));
		}
	}
}