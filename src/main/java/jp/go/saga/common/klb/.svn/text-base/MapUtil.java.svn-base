package jp.go.saga.common.klb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


public class MapUtil {
	
	@SuppressWarnings("rawtypes")
	public static void copyNamedParam(Map<String, Object> srcMap, Map<String, Object> copyMap, String searchKey) {
		Iterator iterator = copyMap.entrySet().iterator();
		String copyKeyName;
		while (iterator.hasNext()) {
			Entry entry = (Entry)iterator.next();
			copyKeyName = entry.getKey().toString();
			if (copyKeyName.matches("^("+ searchKey + ").*")) {
				srcMap.put(copyKeyName.replaceFirst(searchKey, ""), entry.getValue());
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static String printKeyName(Map<String, Object> srcMap) {
		Iterator iterator = srcMap.entrySet().iterator();
		String keyName = "";
		while (iterator.hasNext()) {
			Entry entry = (Entry)iterator.next();
			keyName += entry.getKey().toString() + "|";
		}		
		return keyName;
	}
	
	@SuppressWarnings("rawtypes")
	public static List jsonParamToList(String jsonStr) {
		
		ObjectMapper om = new ObjectMapper();
        
		List m = new ArrayList();
		try {
		// JSON 문자열을 Map or List Object 로 변환
		m = om.readValue(jsonStr, new TypeReference<List>(){});
		} catch (Exception e) {
		}
        return m;
	}

	// 테스트용 코드입니다.
	public static void main(String[] args) {
		String jsonStr = "[{\"a\":1,\"b\":2}, {\"c\":3,\"d\":4}]";
		
		try {
		List<?> list = jsonParamToList(jsonStr);
		
		System.out.println("json:" + list.get(0));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
}
