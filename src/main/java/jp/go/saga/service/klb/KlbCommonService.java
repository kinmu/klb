package jp.go.saga.service.klb;

import java.util.List;
import java.util.Map;

import jp.go.saga.common.Constants;
import jp.go.saga.common.klb.MapUtil;
import jp.go.saga.common.repository.CommonDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KlbCommonService {
	private static final Logger logger = LoggerFactory.getLogger(KlbCommonService.class);

	@Autowired
	CommonDao dao;

	@Autowired
	ManagerService managerService;

	/* 権限コード取得*/
//	public Map<String, Object> getKlbAuthenticateCode(Map<String, Object> paramMap) throws Throwable {
//
//		Map<String, Object> userDetailMap = null;
//		if (StringUtil.getStr(MapUtils.getString(paramMap, "USER_ID"), "").isEmpty()
//				|| StringUtil.getStr(MapUtils.getString(paramMap, "SCH_CD"), "").isEmpty()) {
//			// TODO 内部エラー?
//			new NullPointException("USER_ID、SCH_CDが　存在しません。");
//
//		} else {
//
//
//			// TODO 対象学校以外の場合、エラー
//
//
//			// TODO 教育委員会、教育事務所コード取得
//
//
//			// ユーザ情報取得
//			userDetailMap = selectUserDetailsByAidAndSchoolId(paramMap);
//
//			if (userDetailMap == null) {
//
//				new NullPointException("USER_ID「" + StringUtil.getStr(MapUtils.getString(paramMap, "USER_ID"), "")
//						+ "」に該当するデータが　存在しません。");
//				logger.error("USER_ID「" + StringUtil.getStr(MapUtils.getString(paramMap, "USER_ID"), "")
//						+ "」に該当するデータが　存在しません。");
//
//			} else {
//
//
//
//				// 学年
//				paramMap.put(Constants.GRADE_CODE
//						, StringUtil.getStr(MapUtils.getString(userDetailMap, "GRADE_CODE"), ""));
//
//				// 1.USER_VIEWのユーザ種別が'S'学生の場合、ユーザ権限'06'学生を設定
//				// 2.USER_VIEWのユーザ種別が'S'学生以外の場合、管理者テーブルを検索する。
//				// 3.管理者の場合、'01'管理者を設定
//				//   以外の場合、SCHOOLの学校種別でコード値を設定
//				//   SCHOOLの学校種別が'06'教育事務所　⇒　ユーザ権限'02'教育事務所を設定
//				//   SCHOOLの学校種別が'07','08'教育事務所　⇒　ユーザ権限'03'教育委員会を設定
//				//   SCHOOLの学校種別が上記以外　⇒　ユーザ権限'05'教職員を設定
//
//				// 学校種別
//				String schoolType = userDetailMap.get(Constants.SCHOOL_TYPE).toString();
//
//				// ユーザ種別で判定
//				// 学生の場合
//				if (userDetailMap.get("USER_TYPE").toString().equals("S")) {
//					paramMap.put(Constants.AUTH_CODE, Constants.AUTH_CODE_STUDENT);
//
//					// 学生以外の場合
//				} else {
//
//					// 管理者情報を検索
//					Map<String, Object> managerMap = managerService.selectManagerInfoByLoginId(paramMap);
//
//					if (managerMap != null) {
//						paramMap.put(Constants.AUTH_CODE, Constants.AUTH_CODE_ADMIN);
//					} else {
//
//						// 教育事務所
//						if (Constants.SCHOOL_TYPE_OFFICE.equals(schoolType)) {
//							paramMap.put(Constants.AUTH_CODE, Constants.AUTH_CODE_OFFICE);
//							// 教育委員会
//						} else if (Constants.SCHOOL_TYPE_KEN_COMMIT.equals(schoolType) ||
//								Constants.SCHOOL_TYPE_CITY_COMMIT.equals(schoolType)) {
//							paramMap.put(Constants.AUTH_CODE, Constants.AUTH_CODE_COMMIT);
//							// 教職員
//						} else {
//							paramMap.put(Constants.AUTH_CODE, Constants.AUTH_CODE_TEACHER);
//						}
//					}
//				}
//			}
//		}
//
//		return paramMap;
//	}

	/**
	 * ユーザビューからユーザ情報を取得（ログインID⇒AID、学校ID）
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectUserDetailsByAidAndSchoolId(Map<String, Object> paramMap) {
		return dao.queryForMap("userDetails.selectUserDetailsByAidAndSchoolId", paramMap);
	}

	/*
	 * 教材の排他情報を登録する
	 */
	@SuppressWarnings("unchecked")
	public void insertExclusiveControlInfo(Map<String, Object> paramMap) throws Throwable {

		//一括削除の場合
		if (paramMap.get("USE_STATUS").equals(Constants.USE_STATUS_DELETE)) {
			List<String> libraryIdList = MapUtil.jsonParamToList((String) paramMap.get("CHECKED_LIBRARY_ID"));
			for (String libraryID : libraryIdList) {
				paramMap.put("LIBRARY_ID", libraryID);
				dao.insert("klb_common.insertExclusiveControlInfo", paramMap);
			}
		}
		//一括削除以外の場合
		else {
			dao.insert("klb_common.insertExclusiveControlInfo", paramMap);
		}
	}

	/*
	 * 排他情報が登録されているか確認する
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectExclusiveControlInfo(Map<String, Object> paramMap) throws Throwable {

		//画面選択した削除対象の教材ID結合文字列をリスト化する
		if (paramMap.containsKey("CHECKED_LIBRARY_ID")) {
			paramMap.put("LIBRARY_ID_LIST", MapUtil.jsonParamToList((String) paramMap.get("CHECKED_LIBRARY_ID")));
		}

		return dao.queryForMap("klb_common.selectExclusiveControlInfo", paramMap);
	}

	/*
	 * 排他情報を削除する
	 */
	public void deleteExclusiveControlInfo(Map<String, Object> paramMap) throws Throwable {

		dao.delete("klb_common.deleteExclusiveControlInfo", paramMap);
	}

	/*
	 * 教材IDをキーに教材情報を検索する
	 * @param paramMap
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectLibraryInfoByLibraryId(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForMap("klb_common.selectLibraryInfoByLibraryId", paramMap);
	}

//
//	/*
//	 * 공통코드를 조회한다.
//	 * TKS_KLB_CODE_TYPE
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectTksKlbCodeList(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectTksKlbCodeList", paramMap);
//	}
//
//	/*
//	 * 학생 및 선생님 구분을 조회한다.
//	 */
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> selectUserViewByLoginId(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForMap("klb_common.selectUserViewByLoginId", paramMap);
//	}
//
//	/*
//	 * 특정 학교의 학생뷰에서 학년별로 조회가능한 반을 조회한다.
//	 * CODE_MASTER, CODE_ID='0018'
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectClassList(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectClassList", paramMap);
//	}
//
//	/*
//	 * 특정 학교의 학생뷰에서 학년별로 조회가능한 반을 조회한다.
//	 * CODE_MASTER, CODE_ID='0018'
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectClassListTitle(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectClassListTitle", paramMap);
//	}
//
//	/*
//	 * 특정 학교의 학생뷰에서 학년별로 조회가능한 반을 조회한다.
//	 * CODE_MASTER, CODE_ID='0018'
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectClassListre(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectClassListre", paramMap);
//	}
//
//	/*
//	 * 특정 학교의 학생뷰에서 학년별로 조회가능한 반을 조회한다.
//	 * CODE_MASTER, CODE_ID='0018'
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectClassList2(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectClassList2", paramMap);
//	}
//
//	/*
//	 * 특정 학교의 학생뷰에서 학년별로 조회가능한 반을 조회한다.
//	 * CODE_MASTER, CODE_ID='0018'
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectClassList3(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectClassList3", paramMap);
//	}
//
//	/*
//	 * 특정 학교의 학생뷰에서 학년별로 조회가능한 반을 조회한다.
//	 * CODE_MASTER, CODE_ID='0018'
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectClassList2re(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectClassList2re", paramMap);
//	}
//
//	/*
//	 * 학생명, 반을 조회한다.
//	 * TKS_KLB_CODE_TYPE
//	 */
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> selectStudentInfo(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForMap("klb_common.selectStudentInfo", paramMap);
//	}
//
//	// 교직원_강좌_학습자에서 조회
//	// 교무과에서 연계해줘야 하는 테이블 (TKS_STAFF_LECTURE_STUDENT)
//	@SuppressWarnings("unchecked")
//	public List<Map<String, Object>> selectStaffLectureStudent(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectStaffLectureStudent", paramMap);
//	}
//
//	/*
//	 * 시스템 오늘일자와 시간등을 가져온다.
//	 */
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> selectToday(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForMap("klb_common.selectToday", paramMap);
//	}
//
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> selectCurrDatetime(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForMap("klb_common.selectCurrDatetime", paramMap);
//	}
//
//	@SuppressWarnings("unchecked")
//	public Map<String, Object> selectCurrDatetimeMs(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForMap("klb_common.selectCurrDatetimeMs", paramMap);
//	}
//
//	// 특정 테이블/컬럼의 값을 사용중인 레코드 합계를 조회한다
//	public Object selectTableColumnUseSum(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForObject("klb_common.selectTableColumnUseSum", paramMap);
//	}
//
//	// 특정 테이블/컬럼의 값을 사용중인 레코드 합계를 조회한다 (대단원)
//	public Object selectTableColumnUpperUseSum(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForObject("klb_common.selectTableColumnUpperUseSum", paramMap);
//	}
//
//	// 특정 테이블/컬럼의 값을 사용중인 레코드 현황을 조회한다
//	public List<?> selectTableColumnUseList(Map<String, Object> paramMap) throws Throwable {
//		return dao.queryForList("klb_common.selectTableColumnUseList", paramMap);
//	}
//
//	// 시스템 현재 시간기준의 시간표 교시를 구한다.
//	public String selectCurrentPeriodId() throws Throwable {
//		return dao.queryForString("klb_common.selectCurrentPeriodId");
//	}
}