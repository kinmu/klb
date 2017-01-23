package jp.go.saga.service.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.common.service.CommonService;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class MenuService {
	@Autowired
	CommonDao dao;
	@Autowired
	CommonService commonService;
	
	/**
	 * 大分類を基準としてList<Map>形を順序通りに加工
	 * List<Map> -> List<List<Map>>
	 * @param paramMap
	 * @return
	 */
	public List selectAfrsMenuList(Map<String, Object> paramMap){
		List<List<Map>> resultList = new ArrayList<List<Map>> ();
		List<Map> selectedList = dao.queryForList("menu.selectAfrsMenuList", paramMap);
		
		String tmpMenuLargeId = null;
		List<Map> tmpList = new ArrayList();
		
		int seq = 1;
		for(Map mapCurr : selectedList){
			String MENU_LARGE_ID = MapUtils.getString(mapCurr, "MENU_LARGE_ID");
			if(seq == 1) {
				tmpMenuLargeId = MENU_LARGE_ID;
			}

			if((tmpMenuLargeId != null && MENU_LARGE_ID.equals(tmpMenuLargeId) == false) || seq == selectedList.size()){
				resultList.add(tmpList);
				tmpList = new ArrayList();
				
				tmpMenuLargeId = MENU_LARGE_ID;
			}
			
			tmpList.add(mapCurr);
			
			seq++;
		}
		
		return resultList;
	}

	public List selectMenuList(Map<String, Object> paramMap){
		paramMap.put("PST_DIV", "1");		
		return dao.queryForList("menu.selectMenuList", paramMap);
	}

	public List selectLeftMenuList(Map<String, Object> paramMap){
		paramMap.put("PST_DIV", "2");
		
		return dao.queryForList("menu.selectLeftMenuList", paramMap);
	}
	
	public List selectTopMenuList(Map<String, Object> paramMap){
		paramMap.put("PST_DIV", "3");
		return dao.queryForList("menu.selectTopMenuList", paramMap);
	}
}