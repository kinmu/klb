package jp.go.saga.controller.klb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jp.go.saga.common.AsyncResponseMap;
import jp.go.saga.service.klb.CodeService;
import jp.go.saga.service.klb.ManagerService;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bsmedia.system.util.StringUtil;

@Controller
public class ManagerController {


	private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@Autowired
	ManagerService managerService;

	@Autowired
	CodeService codeService;

	@Autowired
	AsyncResponseMap asyncResponseMap;

	/* 管理者一覧画面_初期表示(削除データがある場合、処理後再表示) */
	@RequestMapping("/manager/viewDeleteManager.do")
	public String viewDeleteManager(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 管理者情報を全件抽出し表示（登録日の降順）
		List<?> manageryList = managerService.selectManager(paramMap);

		// 検索結果の件数情報を設定
		if (manageryList.isEmpty()) {
			model.put("TOTAL_CNT", 0);
		} else {
			model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
			model.put("START_SNO", paramMap.get("START_SNO"));
			model.put("END_SNO", paramMap.get("END_SNO"));
		}

		// 画面表示情報をmodelへ格納
		model.put("LIST_SIZE", manageryList.size());
		model.put("LOGIN_ID", StringUtil.getStr(MapUtils.getString(paramMap, "LOGIN_ID"), ""));
		model.put("MANAGER_LIST", manageryList);
		model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));

		return "/manager/deleteManager";
	}

	/* 管理者一覧画面_初期表示(削除データがある場合、処理後再表示) */
	@RequestMapping("/manager/deleteManager.do")
	public @ResponseBody Map<?, ?>  deleteManager(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 削除データがある場合、削除処理を行う
		String delLoginId = StringUtil.getStr(MapUtils.getString(paramMap, "DEL_LOGIN_LIST"), "");
		int delNum = 0;

		if(delLoginId != null && !delLoginId.isEmpty()){

			logger.debug("削除管理者のログインIDと学校ID :"+ delLoginId);
			String[] delList = delLoginId.split(",");

			List<String> delLoginList = new ArrayList<String>();
			List<String> delSchoolList = new ArrayList<String>();
			for(int i = 0 ; i < delList.length; i++){
				if(i == 0 || i%2 == 0){
					logger.debug("削除管理者のログインID :"+ delList[i]);
					delLoginList.add(delList[i]);
				} else {
					logger.debug("削除管理者の学校ID :"+ delList[i]);
					delSchoolList.add(delList[i]);
				}
			}

			paramMap.put("DEL_LOGIN_LIST", delLoginList);
			paramMap.put("DEL_SCHOOL_LIST", delSchoolList);

			try{
				// 論理削除
				delNum = managerService.updateManager(paramMap);

			} catch (Throwable e){
				e.printStackTrace();
				return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
			}
		}


		return asyncResponseMap.setMessage(delNum+"件削除しました。").setUrlIsParam("/manager/viewDeleteManager.do", paramMap);
	}

	/* 管理者登録画面_初期表示 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/manager/viewRegisterManager.do")
	public String viewRegisterManager(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 学種リスト取得 code_id '0002'
		paramMap.put("CODE_ID", "0002");
		List<?> schoolTypeList = codeService.selectCodeMasterList(paramMap);

		// 画面で選択した「学種」が存在する場合、学種に該当する学校リストを取得
		List<?> schoolList = new ArrayList();
		if(!"".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_SCHOOL_TYPE"), ""))){
			schoolList = managerService.selectSchoolBySchoolType(paramMap);
		}

		// 画面で選択した「学種」「学校」に該当する教職員を検索
		List<?> teacherList = new ArrayList();
		if("SEARCH".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TEACHER"), ""))){
			teacherList = managerService.selectTeacherBySchoolTypeAndSchoolId(paramMap);
		}

		// 検索結果の件数情報を設定
		if (teacherList.isEmpty()) {
			model.put("TOTAL_CNT", 0);
		} else {
			model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
			model.put("START_SNO", paramMap.get("START_SNO"));
			model.put("END_SNO", paramMap.get("END_SNO"));
		}

		// 画面表示情報をmodelへ格納
		model.put("LIST_SIZE", teacherList.size());
		model.put("SCHOOL_TYPE_LIST", schoolTypeList);
		model.put("SCHOOL_LIST", schoolList);
		model.put("TEACHER_LIST", teacherList);
		model.put("SELECT_SCHOOL_TYPE", StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_SCHOOL_TYPE"), ""));
		model.put("SELECT_SCHOOL", StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_SCHOOL"), ""));
		model.put("TEACHER_NAME", StringUtil.getStr(MapUtils.getString(paramMap, "TEACHER_NAME"), ""));
		model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));

		return "/manager/registerManager";
	}

	/* 管理者登録画面_AJAX学校リスト取得 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/manager/searchSchool.do")
	public @ResponseBody Map teacherLetureList(@RequestParam Map<String,Object> paramMap, ModelMap model)  {

		if(!"".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_SCHOOL_TYPE"), ""))){
			List<?> schoolList = managerService.selectSchoolBySchoolType(paramMap);
			model.put("SCHOOL_LIST", schoolList);
		}

		return model;
	}

	/* 管理者登録画面_新規登録 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/manager/registerManager.do")
	public @ResponseBody Map<?, ?>  registerManager(@RequestParam Map<String, Object> paramMap, ModelMap model) throws Throwable{

		String teacherStr = StringUtil.getStr(MapUtils.getString(paramMap, "REGISTOR_TEACHER_LIST"),"");

		// メニューIDを設定


		int insNum = 0;
		if(teacherStr != null && !"".equals(teacherStr)){

			String[] teacherStrList = teacherStr.split(",");
			List loginIdList = new ArrayList();
			List schoolIdList = new ArrayList();
			for(int i = 0; i < teacherStrList.length; i++){
				// ログインIDを格納
				if(i == 0 || i%2 == 0){
					loginIdList.add(teacherStrList[i]);

				// 学校IDを格納
				}else{
					schoolIdList.add(teacherStrList[i]);
				}
			}

			paramMap.put("LOGIN_ID_LIST", loginIdList );
			paramMap.put("SCHOOL_ID_LIST", schoolIdList );

			try{
				insNum = managerService.insertManager(paramMap);
			}  catch (Throwable e){
				e.printStackTrace();
				return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
			}
		}

		// 登録後、管理者一覧画面へ遷移
		return asyncResponseMap.setMessage(insNum+"件登録しました。").setUrlIsParam("/manager/viewDeleteManager.do", paramMap);
	}
}
