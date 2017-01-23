package jp.go.saga.service.klb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {

	private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

	@Autowired
	CommonDao dao;


    /* **********************************************
	 * 管理者情報のサービス作成 *********************
	 ************************************************/

	/**
	 * 管理者情報を検索（ログインID）
	 * @param paramMap
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectManagerByLoginIdAndSchCd(Map<String, Object> paramMap){
		return dao.queryForMap("managerInfo.selectManagerByLoginIdAndSchCd", paramMap);
	}

	public List<?> selectManager(Map<String, Object> paramMap){
		return dao.queryForPaginatedList("managerInfo.selectManager", paramMap);
	}

	/* 管理者登録*/
	@SuppressWarnings("rawtypes")
	public int insertManager(Map<String, Object> paramMap) throws Throwable {

		// 登録対象の学校IDとログインID
		ArrayList loginIdList = (ArrayList)MapUtils.getObject(paramMap, "LOGIN_ID_LIST");
		ArrayList schoolIdList = (ArrayList)MapUtils.getObject(paramMap, "SCHOOL_ID_LIST");

		// ログインIDの件数分登録
		int insNum = 0;
		for(int i = 0; i < loginIdList.size(); i++){
			// TODO 存在する場合、更新（削除フラグを0に）

			paramMap.put("INS_LOGIN_ID", loginIdList.get(i));
			paramMap.put("INS_SCHOOL_ID", schoolIdList.get(i));

			// ログインIDで検索
			Map managerMap = selectManagerByLoginIdAndSchCd(paramMap);
			// 存在しない場合、登録
			if(managerMap == null || managerMap.isEmpty() ){

				dao.insert("managerInfo.insertManager", paramMap);
			// 存在する場合、更新
			} else {
				dao.update("managerInfo.updateManager", paramMap);
			}
			insNum++;
		}

		return insNum;

	}

	/* 管理者論理削除（件数分、削除フラグを'1'）*/
	@SuppressWarnings("rawtypes")
	public Integer updateManager(Map<String, Object> paramMap) throws Throwable {

		int updCnt = 0;

		// 学校IDは条件には設定しない（ログインIDのみで更新）
		ArrayList delLoginList = (ArrayList)MapUtils.getObject(paramMap, "DEL_LOGIN_LIST");
//		ArrayList delSchoolList = (ArrayList)MapUtils.getObject(paramMap, "DEL_SCHOOL_LIST");

		for(int i = 0; i < delLoginList.size(); i++){
			paramMap.put("DEL_USER_ID", delLoginList.get(i));
//			paramMap.put("DEL_SCHOOL_ID", delSchoolList.get(i));
			dao.update("managerInfo.updateManager", paramMap);
			updCnt++;
		}
		return updCnt;
	}

	/* 利用可能学校かチェック */
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkSchoolAuthMaster(Map<String, Object> paramMap){
		return dao.queryForMap("managerInfo.checkSchoolAuthMaster", paramMap);
	}

	/* SCHOOLマスタから学種リスト取得 */
	public List<?> selectSchoolBySchoolType(Map<String, Object> paramMap){
		return dao.queryForList("managerInfo.selectSchoolBySchoolType", paramMap);
	}

	/* USER_VIEWとSCHOOLから「学種」「学校」をキーに教職員リスト取得*/
	public List<?> selectTeacherBySchoolTypeAndSchoolId(Map<String, Object> paramMap){
		return dao.queryForPaginatedList("managerInfo.selectTeacherBySchoolTypeAndSchoolId", paramMap);
	}
}
