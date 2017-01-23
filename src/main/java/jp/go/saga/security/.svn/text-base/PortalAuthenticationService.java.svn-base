package jp.go.saga.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import jp.go.saga.common.Constants;
import jp.go.saga.common.repository.CommonDao;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import bsmedia.system.config.ApplicationProperty;

@Service
@SuppressWarnings("rawtypes")
public class PortalAuthenticationService {
	
	@Autowired
	CommonDao dao;
	@Autowired
	HttpSession session;
	
	@Autowired
	RoleHierarchy roleHierarchy;
	
	/**
	 * 基本user情報
	 * @param paramMap
	 * @return
	 */
	public Map<String,Object> getUserDetails(CommonUser commonUser, Object SCH_CD) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put(Constants.USER_ID, commonUser.getName());
		paramMap.put(Constants.SCH_CD, SCH_CD);
		paramMap.put(Constants.AID, this.getUserAid(paramMap));
		
		try{
			@SuppressWarnings("unchecked")
			Map<String,Object> resultMap = dao.queryForMap("userDetails.selectUserDetailsByLoginId",paramMap);
			
			if(resultMap != null) {
				// ユーザーID
				resultMap.put(Constants.USER_ID, commonUser.getName());
				// 県コード
				resultMap.put(Constants.PRFC_CD, ApplicationProperty.get("master.code.ken"));	
				
				if(SCH_CD == null) {			
					resultMap.put(Constants.HTTP_IW_ROUTE, commonUser.getUserDetails().get(Constants.HTTP_IW_ROUTE));
					resultMap.put(Constants.SSL_CLIENT_VERIFY, commonUser.getUserDetails().get(Constants.SSL_CLIENT_VERIFY));
					resultMap.put(Constants.SSL_CLIENT_S_DN_CN, commonUser.getUserDetails().get(Constants.SSL_CLIENT_S_DN_CN));
					resultMap.put(Constants.HTTP_IW_MAILPASS, commonUser.getUserDetails().get(Constants.HTTP_IW_MAILPASS));
				} else {
					resultMap.put(Constants.HTTP_IW_ROUTE, commonUser.getUserDetails().get(Constants.HTTP_IW_ROUTE));
					resultMap.put(Constants.SSL_CLIENT_VERIFY, commonUser.getUserDetails().get(Constants.SSL_CLIENT_VERIFY));
					resultMap.put(Constants.SSL_CLIENT_S_DN_CN, commonUser.getUserDetails().get(Constants.SSL_CLIENT_S_DN_CN));
					resultMap.put(Constants.HTTP_IW_MAILPASS, commonUser.getUserDetails().get(Constants.HTTP_IW_MAILPASS));
				}
			}
			
			return resultMap;		
		}catch(UncategorizedSQLException e){
			return null;
		}
	}
	
	public Map<String,Object> getUserDetails2(String schoolId, String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put(Constants.USER_ID, userId);
		paramMap.put(Constants.SCH_CD, schoolId);
		paramMap.put(Constants.AID, this.getUserAid(paramMap));
		
		try{
			@SuppressWarnings("unchecked")
			Map<String,Object> resultMap = dao.queryForMap("userDetails.selectUserDetailsByLoginId",paramMap);
			
			if(resultMap != null) {
				resultMap.put(Constants.USER_ID, userId);
				resultMap.put(Constants.PRFC_CD, ApplicationProperty.get("master.code.ken"));	
			}
			
			return resultMap;		
		}catch(UncategorizedSQLException e){
			return null;
		}
	}
	
	private String getUserAid(Map<String,Object> paramMap) {
		try {
			return (String) dao.queryForObject("userDetails.selectUserAid",paramMap);
		} catch(UncategorizedSQLException e){
			return null;
		}
	}

	/**
	 * user role 情報
	 */
	public Collection<? extends GrantedAuthority> getUserRoles(Map userDetails) {
		if(userDetails == null){
			return null;
		}
		
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		
		String userType = MapUtils.getString(userDetails,"USER_TYPE");
		
		if("T".equals(userType)){
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_TEACHER));
			/*
			if(isLectureInCharge(userDetails)){
				roles.add(new SimpleGrantedAuthority(Constants.ROLE_TEACHER_IN_CHARGE));
			}
			*/
		} else if("S".equals(userType)){
			roles.add(new SimpleGrantedAuthority(Constants.ROLE_STUDENT));
		}
		
		return roleHierarchy.getReachableGrantedAuthorities(roles);
	}

	/**
	 * 学校リスト
	 * @param username userId
	 * @return 学校情報
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getAffiliatedSchoolList(Object AID){
		return dao.queryForList("userDetails.selectAffiliatedSchoolListBySchoolId", AID);
	}
	
	/**
	 * 学校情報
	 * @param schoolId
	 * @return 学校情報
	 */
	public Map selectSchoolDetail(Map paramMap){
		return dao.queryForMap("userDetails.selectSchoolDetail", paramMap);
	}
}