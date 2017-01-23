package jp.go.saga.aspect.request;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.go.saga.aspect.common.AbstractParameterInterceptor;
import jp.go.saga.common.Constants;
import jp.go.saga.security.CommonUser;
import jp.go.saga.security.LoginException;
import jp.go.saga.security.PortalAuthenticationService;
import jp.go.saga.service.klb.MainService;
import jp.go.saga.service.klb.ManagerService;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.StringUtil;

public class UserInfoSetter extends AbstractParameterInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoSetter.class);

	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpSession session;
	@Autowired
	MainService mainService;
	@Autowired
	PortalAuthenticationService authenticationService;
	@Autowired
	ManagerService managerService;


	@SuppressWarnings("unchecked")
	@Override
	public Object getModifiedArgument(Object argument) {
		if(argument instanceof Map == false){
			return AbstractParameterInterceptor.IS_NOT_A_TARGET;
		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String,Object> userDetails = principal instanceof CommonUser ? ((CommonUser)principal).getUserDetails() : null;
		String userType = MapUtils.getString(userDetails, "USER_TYPE");
		String schoolId = String.valueOf(userDetails.get(Constants.SCH_CD));


		if(userDetails != null) {
			logger.debug("original userDetails>>>>>>>>"+userDetails);

			// 兼務対応処理（本務以外にログインIDのない、学校で接続した場合）
			// http://127.0.0.1:8080/klb/main.do?schoolId=308&userId=3000218
			if ("T".equalsIgnoreCase(userType)) {
				schoolId = StringUtil.getStr(MapUtils.getString((Map<String,Object>)argument, "schoolId"),"");
				String userId = StringUtil.getStr(MapUtils.getString((Map<String,Object>)argument, "userId"),"");

				logger.debug("## Request User Info >> USER_ID ["+userId+"], SCHOOL_ID["+schoolId+"]");

				if(!"".equals(schoolId) && !"".equals(userId) ) {
					Map<String,Object> changeUserDetails = authenticationService.getUserDetails2(schoolId, userId);
					session.setAttribute("changeUserDetails", changeUserDetails);
					session.setAttribute("CHANGE_GLOGBAL_LECTURE", "Y");
					session.setAttribute("CHANGE_GLOGBAL_USER", "Y");
					session.setAttribute("yearList",null);
				}

				if(!"".equals(schoolId)) {
					Map<String,Object> schoolMap = new HashMap<String, Object>();
					schoolMap.put("SCH_CD", schoolId);
					Map<String, Object> changeUserSchoolInfo = authenticationService.selectSchoolDetail(schoolMap);
					userDetails.put(Constants.SCHOOL_TYPE, changeUserSchoolInfo.get(Constants.SCHOOL_TYPE));
				}

				if (session.getAttribute("changeUserDetails")!=null) {
					logger.debug("change userDetails>>>>>>>>"+session.getAttribute("changeUserDetails"));

					userDetails = (Map<String,Object>) session.getAttribute("changeUserDetails");
					((CommonUser)principal).setUserDetails(userDetails);
				}
			}
			String YEAR = "";
			String CURRENT_DATE = (String)((Map<String,Object>)argument).get("CURRENT_DATE");

			Calendar calendar = Calendar.getInstance();
			int nowyear = calendar.get(Calendar.YEAR);

			if(CURRENT_DATE!=null&&CURRENT_DATE.length()==8) {
				int year = Integer.parseInt(CURRENT_DATE.substring(0,4));
				if(Integer.parseInt(CURRENT_DATE.substring(4,6)) < 4){
					year = year -1 ;
				}
				YEAR = Integer.toString(year);
			} else {

				if(session.getAttribute(Constants.YEAR)==null) {
					// 1~3월 이전년도 -1, 4~12월 현재년도
					int year = calendar.get(Calendar.YEAR);
					int month = calendar.get(Calendar.MONTH)+1;
					if(month < 4){
						year = year -1 ;
					}
					YEAR = Integer.toString(year);

				} else {
					YEAR = (String)session.getAttribute(Constants.YEAR);
				}

			}

			// セッションに格納するデータを設定
			session.setAttribute(Constants.USER_ID, userDetails.get(Constants.LOGIN_ID));

			if(Integer.parseInt(YEAR) < nowyear){
				session.setAttribute(Constants.BEFORE_YEAR, "Y");
				((Map<String,Object>)argument).put(Constants.BEFORE_YEAR, "Y");
			}else{
				session.setAttribute(Constants.BEFORE_YEAR, "N");
				((Map<String,Object>)argument).put(Constants.BEFORE_YEAR, "N");
			}

			// メニューIDをセッションから取得
			HttpSession reqSession = request.getSession();
			logger.debug("request.getSession() MENU_ID : "+ String.valueOf(reqSession.getAttribute("MENU_ID")));

			// メニューIDが存在する場合のみ、sessionへ格納
			String menu_id = StringUtil.getStr(MapUtils.getString((Map<String,Object>)argument, "MENU_ID"),"");
			logger.debug(String.valueOf(session.getAttribute("MENU_ID")));
			if(!menu_id.isEmpty()){
				session.setAttribute("MENU_ID", menu_id);
			}
			logger.debug("session.getAttribute MENU_ID : "+ String.valueOf(session.getAttribute("MENU_ID")));

			((Map<String,Object>)argument).put(Constants.USER_ID, 		userDetails.get(Constants.LOGIN_ID));
			((Map<String,Object>)argument).put(Constants.USER_IP, 		request.getRemoteAddr());
			((Map<String,Object>)argument).put(Constants.USER_NM, 		userDetails.get(Constants.USER_NM));
			((Map<String,Object>)argument).put(Constants.SCH_CD,		userDetails.get(Constants.SCH_CD));
			((Map<String,Object>)argument).put(Constants.LOGIN_ID, 		userDetails.get(Constants.LOGIN_ID));
			((Map<String,Object>)argument).put(Constants.LOGIN_IP, 		userDetails.get(Constants.LOGIN_IP));
			((Map<String,Object>)argument).put(Constants.USER_TYPE, 	userDetails.get(Constants.USER_TYPE));
			((Map<String,Object>)argument).put(Constants.AID, 			userDetails.get(Constants.AID));
			((Map<String,Object>)argument).put(Constants.SCHOOL_TYPE, 	userDetails.get(Constants.SCHOOL_TYPE));
//			((Map<String,Object>)argument).put(Constants.YEAR, 			ApplicationProperty.get("system.schoolYear"));
			((Map<String,Object>)argument).put(Constants.YEAR, 			YEAR);

			((Map<String,Object>)argument).put("PRFC_CD", 				ApplicationProperty.get("master.code.ken"));
			// 学年
			((Map<String,Object>)argument).put(Constants.GRADE_CODE, 	userDetails.get(Constants.GRADE_CODE));

			// 教材ばるーんが指定した学校であるかチェック、対象外学校の場合「システムエラー画面へ」
			Map<String, Object> checkMap = managerService.checkSchoolAuthMaster((Map<String,Object>)argument);

			if( checkMap == null || checkMap.isEmpty() ){
				new LoginException("教材ばる～んが利用できない学校です。学校ID["+
						StringUtil.getStr(MapUtils.getString((Map<String,Object>)argument, Constants.SCH_CD),"")+"]");
			}

			((Map<String,Object>)argument).put("COMMITTEE_ID", StringUtil.getStr(MapUtils.getString(checkMap, "COMMITTEE_ID"), ""));
			((Map<String,Object>)argument).put("OFFICE_ID", StringUtil.getStr(MapUtils.getString(checkMap, "OFFICE_ID"), ""));

			// 1.USER_VIEWのユーザ種別が'S'学生の場合、ユーザ権限'06'学生を設定
			// 2.USER_VIEWのユーザ種別が'S'学生以外の場合、管理者テーブルを検索する。
			// 3.管理者の場合、'01'管理者を設定
			//   以外の場合、SCHOOLの学校種別でコード値を設定
			//   SCHOOLの学校種別が'06'教育事務所　⇒　ユーザ権限'02'教育事務所を設定
			//   SCHOOLの学校種別が'07','08'教育事務所　⇒　ユーザ権限'03'教育委員会を設定
			//   SCHOOLの学校種別が上記以外　⇒　ユーザ権限'05'教職員を設定

			// 学校種別
			String schoolType = StringUtil.getStr(String.valueOf(userDetails.get(Constants.SCHOOL_TYPE)),"");

			// ユーザ種別で判定
			// 学生の場合
			if (userDetails.get(Constants.USER_TYPE).toString().equals("S")) {
				((Map<String,Object>)argument).put(Constants.AUTH_CODE, Constants.AUTH_CODE_STUDENT);

				// 学生以外の場合
			} else {

				// 管理者情報を検索
				Map<String, Object> managerMap = managerService.selectManagerByLoginIdAndSchCd((Map<String,Object>)argument);

				if (managerMap != null) {
					((Map<String,Object>)argument).put(Constants.AUTH_CODE, Constants.AUTH_CODE_ADMIN);
				} else {

					// 教育事務所
					if (Constants.SCHOOL_TYPE_OFFICE.equals(schoolType)) {
						((Map<String,Object>)argument).put(Constants.AUTH_CODE, Constants.AUTH_CODE_OFFICE);
						// 教育委員会
					} else if (Constants.SCHOOL_TYPE_KEN_COMMIT.equals(schoolType) ||
							Constants.SCHOOL_TYPE_CITY_COMMIT.equals(schoolType)) {
						((Map<String,Object>)argument).put(Constants.AUTH_CODE, Constants.AUTH_CODE_COMMIT);
						// 教職員
					} else {
						((Map<String,Object>)argument).put(Constants.AUTH_CODE, Constants.AUTH_CODE_TEACHER);
					}
				}
			}

			// 教職員
			if ("T".equalsIgnoreCase(userType)) {

			// 学生
			} else if ("S".equalsIgnoreCase(userType)) {
				session.setAttribute(Constants.GLOBAL_GRADE_CODE, 			userDetails.get(Constants.GRADE_CODE));
				session.setAttribute(Constants.GLOBAL_GRADE_CLASS_ID, 		userDetails.get(Constants.GRADE_CLASS_ID));
				session.setAttribute(Constants.SCH_CD, 						userDetails.get(Constants.SCH_CD));
				session.setAttribute(Constants.AUTH_CODE, 					((Map<String,Object>)argument).get(Constants.AUTH_CODE));
			}
		}

		return argument;
	}
}