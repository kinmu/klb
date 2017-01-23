package jp.go.saga.common.service;

import java.util.Map;

import jp.go.saga.common.Constants;
import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.security.CommonUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	@Autowired
	CommonDao dao;
	
	/**
	 * 注意 - sequenceNameを外部から取得してはいけない。
	 * 
	 * @param sequenceName シーケンス名
	 * @return
	 */
	public Integer selectSequenceBySequenceName(String sequenceName){
		return dao.queryForInteger("common.selectSequenceBySequenceName", sequenceName);
	}
	
	/**
	 * @return Spring Security CommonUser Object
	 */
	public CommonUser getCommonUser(){
		return (CommonUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	/**
	 * @return Spring Security UserDetails Object
	 */
	public Map<String,Object> getUserDetails(){
		return getCommonUser().getUserDetails();
	}
	
	/**
	 * @return USER_ID
	 */
	public String getUserId(){
		return (String)getUserDetails().get(Constants.USER_ID);
	}
}
