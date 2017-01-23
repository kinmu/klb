package jp.go.saga.security;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.go.saga.common.message.MessageProperty;
import jp.go.saga.service.klb.ManagerService;
import jp.go.saga.service.portal.LoginLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.DateUtils;

@Component
public class PortalAuthenticationFactory {

	private static final Logger logger = LoggerFactory.getLogger(PortalAuthenticationFactory.class);

	@Autowired
	MessageProperty messageProperty;

	@Autowired
	PortalAuthenticationService authService;

	@Autowired
	LoginLogService loginLogService;

	@Autowired
	HttpServletRequest request;

	@Autowired
	ManagerService managerService;

	/**
	 * USER_IDを使いUserDetailsを取得
	 * @param USER_ID USER_ID
	 * @param SCH_CD 学校切り替えの際、必要
	 * @return
	 */
	public UserDetails getUserDetails(CommonUser commonUser,Object SCH_CD){
		Map<String,Object> credentialMap = commonUser.getUserDetails();
		// user details
		Map<String,Object> userDetailsMap = authService.getUserDetails(commonUser,SCH_CD);

		if(userDetailsMap == null) {

			throw new CredentialException(messageProperty.getMessage("F006"));
		} else {
			credentialMap.put("USER_TYPE", userDetailsMap.get("USER_TYPE"));
		}


		// 学校情報を取得し、ユーザー情報に設定
		@SuppressWarnings("unchecked")
		Map<String, Object> schoolMap = authService.selectSchoolDetail(userDetailsMap);
		if(schoolMap != null){
			userDetailsMap.putAll(schoolMap);
		} else {
			logger.warn("A school infomation was not found. The access ID is : " + commonUser.getName());
		}

		//TODO
	    Iterator<String> iterator = userDetailsMap.keySet().iterator();

		logger.debug("# ");
		logger.debug("# * USER DETAIL INFO");
		logger.debug("# ");

	    while (iterator.hasNext()) {
	        String key = (String) iterator.next();

	        logger.debug("# "+ key + " = " + userDetailsMap.get(key));
	    }

		// authorities
		Collection<? extends GrantedAuthority> authorities = authService.getUserRoles(userDetailsMap);
		if(authorities.isEmpty()){

			throw new CredentialException(messageProperty.getMessage("F007"));
		}

		// Affiliated School Info List
		List<Map<String,Object>> affiliatedSchoolList = authService.getAffiliatedSchoolList(userDetailsMap.get("AID"));

		CommonUser user = new CommonUser(commonUser.getName(),authorities,userDetailsMap,affiliatedSchoolList);

		logger.debug("## LOGIN INFO ##");
		logger.debug(user == null ? null : user.toString());
		logger.debug("################");

		System.out.println(credentialMap);

		if("Y".equals(ApplicationProperty.get("log.successLog"))){
			credentialMap.put("LOGIN_FLAG" , "1");
			credentialMap.put("SCHOOL_ID"  , userDetailsMap.get("SCH_CD"));
			credentialMap.put("SCHOOL_TYPE", userDetailsMap.get("SCHOOL_TYPE"));
			credentialMap.put("GRADE_CODE" , userDetailsMap.get("GRADE_CODE"));

			// ログインIDとログイン日付をキーに「時間の分まで同じ」データがすでに存在する場合、登録しない
			// 現在システム時刻の前後3分ないが存在しない場合登録
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

			String dateFrom = DateUtils.add(sdf1.format(new Date()), "mm24", -3, false);
			String loginDate = dateFrom.substring(0, 8);
			String loginTimeFrom = dateFrom.substring(8, 12);

			String dateTo = DateUtils.add(sdf1.format(new Date()), "mm24", 3, false);
			String loginTimeTo = dateTo.substring(8, 12);

			logger.debug("#### システム日付："+loginDate);
			logger.debug("#### システム時間："+loginTimeFrom+"～"+loginTimeTo);

			credentialMap.put("LOGIN_DATE" ,loginDate);
			credentialMap.put("LOGIN_TIME_FROM" ,loginTimeFrom);
			credentialMap.put("LOGIN_TIME_TO" ,loginTimeTo);

			// 検索
			List<?> loginList = loginLogService.selectLoginLogByUserAndTime(credentialMap);

			// ログインIDとログイン日付をキーに「時間の分まで同じ」データがない場合、登録
			if(loginList == null || loginList.size() == 0){
				logger.debug("#### ログイン履歴データを登録 ####");
				loginLogService.insertLoginLogDetailForKLB(credentialMap, request);
			}
		}
		return user;
	}

	public Authentication getAuthentication(CommonUser commonUser,Object SCH_CD){
		UserDetails userDetails = this.getUserDetails(commonUser, SCH_CD);

		Authentication authentication = new PortalAuthenticationToken(userDetails, userDetails.getAuthorities());
		authentication.setAuthenticated(true);
		return authentication;
	}
}
