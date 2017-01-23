package jp.go.saga.controller.klb;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jp.go.saga.common.AsyncResponseMap;
import jp.go.saga.common.Constants;
import jp.go.saga.common.klb.MapUtil;
import jp.go.saga.service.klb.CategoryService;
import jp.go.saga.service.klb.CodeService;
import jp.go.saga.service.klb.KlbCommonService;
import jp.go.saga.service.klb.RegisterService;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.StringUtil;

@Controller
public class RegisterController {

	@Autowired
	HttpSession session;
	@Autowired
	CodeService codeService;
	@Autowired
	KlbCommonService klbCommonService;
	@Autowired
	RegisterService registerService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	AsyncResponseMap asyncResponseMap;


	/*
	 * KLB221_教材登録・修正画面の初期表示（登録の場合）
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/viewRegisterPage.do")
	public String viewRegisterPage(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {

		//カテゴリコードリストを取得する（checkbox用）
		model.put("categoryList", categoryService.getCategoryListForCheckbox(paramMap));

		//対象学年コードリストを取得する（ポータルDBから取得）（combobox用）
		model.put("gradeCodeList", codeService.getGradeCode(paramMap));

		//出力パラメータを設定
		Map<String,Object> mapA = new HashMap<String, Object>();
		mapA.put("REGIST_STATUS", 	Constants.REGIST_STATUS_INSERT);				//登録処理状態
		mapA.put("ATTACH_FILE_MAX",	ApplicationProperty.get("attach.file.max"));	//添付可能ファイル数
		model.put("paramInfo", 		mapA);

		//KLB221_教材登録・修正画面に遷移する
		return "/register/registerLibrary";
	}


	/*
	 * KLB221_教材登録・修正画面の「登録する」ボタン押下時
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/registerLibrary.do")
	public @ResponseBody Map<?, ?> registerLibrary(
										DefaultMultipartHttpServletRequest request,
										@RequestParam Map<String,Object> paramMap,
										@RequestParam(value="LINK", required=false) String[] linkArray) throws Throwable {

		//添付ファイルを取得する
		MultipartFile coverImageFile = request.getFile("COVER_IMAGE_FILE");
		List<MultipartFile> libraryFile = request.getFiles("LIBRARY_FILE");

		//multipartResolverがファイルサイズ超過エラーを返している場合、画面にエラーを表示する
		Exception multipart_exception = (Exception)request.getAttribute("exception");
		if(multipart_exception instanceof MaxUploadSizeExceededException) {
			return asyncResponseMap.setMessage("アップロード可能ファイルサイズ合計（500MB）を超過しています。").setSuccessFlag(false);
		}

		try {
			//教材を新規登録する
			registerService.registerSingleLibrary(paramMap, coverImageFile, libraryFile, linkArray);
		}
		catch (Throwable e) {
			//ファイルのROLLBACKを行う
			registerService.rollbackLibraryFile(paramMap);

			e.printStackTrace();
			return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
		}

		//登録教材一覧画面に遷移する
		return asyncResponseMap.setSaveOkMessage().setUrl("/register/viewRegisteredListPage.do");
	}


	/*
	 * KLB221_教材登録・修正画面の初期表示（修正の場合）
	 */
	@Transactional
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/viewUpdatePage.do")
	public String viewUpdatePage(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {

		//排他情報を登録する
		paramMap.put("USE_STATUS", Constants.USE_STATUS_UPDATE);
		klbCommonService.insertExclusiveControlInfo(paramMap);

		//修正対象教材情報を取得する
		Map<String,Object> libraryInfo = registerService.selectLibraryInfoForUpdate(paramMap);
		model.put("libraryInfo", libraryInfo.get("libraryInfo"));
		model.put("fileList", libraryInfo.get("fileList"));
		model.put("linkList", libraryInfo.get("linkList"));

		//カテゴリコードリストを取得する（checkbox用）
		model.put("categoryList", categoryService.getCategoryListForChecked(paramMap));

		//対象学年コードリストを取得する（ポータルDBから取得）（combobox用）
		model.put("gradeCodeList", codeService.getGradeCode(paramMap));

		//出力パラメータを設定
		Map<String,Object> mapA = new HashMap<String, Object>();
		registerService.copySearchParam(paramMap, mapA);
		mapA.put("REGIST_STATUS", 		Constants.REGIST_STATUS_UPDATE);				//処理区分
		mapA.put("MAX_FILE_SNO",		libraryInfo.get("MAX_FILE_SNO"));				//登録教材SNO最大値
		mapA.put("UPLOADED_FILE_NUM",	libraryInfo.get("UPLOADED_FILE_NUM"));			//登録済教材件数
		mapA.put("ATTACH_FILE_MAX",		ApplicationProperty.get("attach.file.max"));	//添付可能最大ファイル数
		model.put("paramInfo", mapA);

		//KLB221_教材登録・修正画面に遷移する
		return "/register/registerLibrary";
	}


	/*
	 * KLB221_教材登録・修正画面の「修正する」ボタン押下時
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/updateLibrary.do")
	public @ResponseBody Map<?, ?> updateLibrary(
										DefaultMultipartHttpServletRequest request,
										@RequestParam Map<String,Object> paramMap,
										@RequestParam(value="DEL_FILE_NAME", required=false) String[] delFileNameArray,
										@RequestParam(value="LINK", required=false) String[] linkArray) throws Throwable {

		//添付ファイルを取得する
		MultipartFile coverImageFile = request.getFile("COVER_IMAGE_FILE");
		List<MultipartFile> libraryFile = request.getFiles("LIBRARY_FILE");

		//multipartResolverがファイルサイズ超過エラーを返している場合、画面にエラーを表示する
		Exception multipart_exception = (Exception)request.getAttribute("exception");
		if(multipart_exception instanceof MaxUploadSizeExceededException) {
			return asyncResponseMap.setMessage("アップロード可能ファイルサイズ合計（500MB）を超過しています。").setSuccessFlag(false);
		}

		try {
			//添付ファイルで既存ファイルを入れ替える
			registerService.uploadAttachFile(paramMap, coverImageFile, libraryFile, delFileNameArray);

			//教材情報を更新する
			registerService.updateLibraryInfo(paramMap, delFileNameArray, linkArray);
		}
		catch (Throwable e) {
			//ファイルのROLLBACKを行う
			registerService.rollbackLibraryFile(paramMap);

			e.printStackTrace();
			if (StringUtil.isEmpty(e.getMessage())) {
				return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
			} else {
				return asyncResponseMap.setMessage(e.getMessage()).setSuccessFlag(false);
			}
		}
		finally {
			//排他情報を削除する
			paramMap.put("USE_STATUS", Constants.USE_STATUS_UPDATE);
			klbCommonService.deleteExclusiveControlInfo(paramMap);
		}

		//退避ファイルを物理削除する
		registerService.deleteBackupLibraryFile(paramMap);

		//KLB211_登録教材一覧画面に遷移する
		return asyncResponseMap.setSaveOkMessage().setUrl("/register/viewRegisteredListPage.do?"+createSearchParam(paramMap));
	}


	/*
	 * KLB211_登録教材一覧画面の初期表示
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/viewRegisteredListPage.do")
	public String viewRegisteredListPage(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {

		//入力パラメータを設定
		paramMap.put("SEARCH_CATEGORY_ID",		StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_CATEGORY_ID"),	""));
		paramMap.put("SEARCH_GRADE_FROM",		StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_GRADE_FROM"),		""));
		paramMap.put("SEARCH_GRADE_TO",			StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_GRADE_TO"),		""));
		paramMap.put("SEARCH_INS_DATE_FROM",	StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_INS_DATE_FROM"),	""));
		paramMap.put("SEARCH_INS_DATE_TO",		StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_INS_DATE_TO"),	""));
		paramMap.put("SEARCH_KEYWORD",			StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_KEYWORD"),		""));
		paramMap.put("SEARCH_OPEN_CONDITION",	StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_OPEN_CONDITION"),	"01"));
		paramMap.put("SEARCH_ALL_DATA_FLG",		StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_ALL_DATA_FLG"),	"0"));
		paramMap.put("SEARCH_INS_USER_NAME",	StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_INS_USER_NAME"),	""));
		paramMap.put("SEARCH_SORT_CODE", 		StringUtil.getStr(MapUtils.getString(paramMap,"SEARCH_SORT_CODE"),		"01"));
		paramMap.put("page", 					StringUtil.getStr(MapUtils.getString(paramMap,"page"),				"1"));

		//カテゴリコードリストを取得する（combobox用）
		model.put("categoryList", categoryService.getCategoryListForListbox(paramMap));

		//対象学年コードリストを取得する（ポータルDBから取得）（combobox用）
		model.put("gradeCodeList", codeService.getGradeCode(paramMap));

		//公開状況コードリストを取得する（radio用）
		paramMap.put("CODE_ID", Constants.CODE_KLB_OPEN_CONDITION);
		model.put("openConditionCodeList", codeService.getCode(paramMap));

		//登録教材リストソート順コードリストを取得する（combobox用）
		paramMap.put("CODE_ID", Constants.CODE_KLB_REGISTER_LIST_SORT);
		model.put("sortCodeList", codeService.getCode(paramMap));

		//登録した教材情報一覧を取得する
		List<?> libraryList = registerService.selectRegisteredLibraryList(paramMap);
		model.put("libraryList", libraryList);

		//出力パラメータを設定
		Map<String,Object> mapA = new HashMap<String, Object>();
		registerService.copySearchParam(paramMap, mapA);
		if (libraryList.isEmpty()) {
			mapA.put("TOTAL_CNT",	0);
		} else {
			mapA.put("TOTAL_CNT",	paramMap.get("TOTAL_CNT"));
			mapA.put("START_SNO",	paramMap.get("START_SNO"));
			mapA.put("END_SNO",		paramMap.get("END_SNO"));
		}
		mapA.put("LIST_SIZE", libraryList.size());
		mapA.put("AUTH_CODE", paramMap.get("AUTH_CODE"));
		model.put("paramInfo", mapA);

		//セッションに画面IDを設定して置く（教材詳細画面から戻る処理のため）
		session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_REGISTER_LIST);
		session.setAttribute("MENU_ID","klb_menu21");

		return "/register/registeredLibraryList";
	}


	/*
	 * KLB211_登録教材一覧画面の「一括削除する」ボタン押下時
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/deleteLibrary.do")
	public @ResponseBody Map<?, ?> deleteLibrary(@RequestParam Map<String,Object> paramMap) throws Throwable {

		int deletedNum = 0;

		try {
			//画面選択した削除対象の教材ID結合文字列をリスト化する
			paramMap.put("LIBRARY_ID_LIST", MapUtil.jsonParamToList((String)paramMap.get("CHECKED_LIBRARY_ID")));

			//選択教材の添付ファイルを削除する（論理削除）
			registerService.deleteLibraryFile(paramMap);

			//選択教材の教材情報を削除する
			deletedNum = registerService.deleteLibraryInfo(paramMap);
		}
		catch (Throwable e) {
			//ファイルのROLLBACKを行う
			registerService.rollbackLibraryFile(paramMap);

			e.printStackTrace();
			return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
		}
		finally {
			//排他情報を削除する
			paramMap.put("USE_STATUS", Constants.USE_STATUS_DELETE);
			klbCommonService.deleteExclusiveControlInfo(paramMap);
		}

		//論理削除（退避）したファイルを物理削除する
		registerService.deleteBackupLibraryFile(paramMap);

		//1ページ以後でページ内全件選択し削除した場合
		int page_num  = Integer.parseInt(paramMap.get("page").toString());
		int list_size = Integer.parseInt(paramMap.get("LIST_SIZE").toString());
		if (page_num > 1 && deletedNum == list_size) {
			//1個前のページ番号に変更する
			paramMap.put("page", String.valueOf(page_num - 1));
		}

		//KLB211_登録教材一覧画面に遷移する
		return asyncResponseMap.setMessage(deletedNum+"件削除しました。").setUrlIsParam("/register/viewRegisteredListPage.do", paramMap);
	}


	/*
	 * KLB231_教材一括登録画面の初期表示
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/viewRegisterMultiPage.do")
	public String viewRegisterMultiPage(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {

		//入力パラメータを設定
		paramMap.put("REGIST_STATUS", StringUtil.getStr(MapUtils.getString(paramMap,"REGIST_STATUS"), Constants.REGIST_STATUS_MULTI));
		paramMap.put("ERROR_FLG",     StringUtil.getStr(MapUtils.getString(paramMap,"ERROR_FLG"),     Constants.FLAG_OFF));

		//読込リストを取得
		List<Map<String,Object>> libraryInfoList = registerService.selectMultiLibraryExcelInfo(paramMap);
		model.put("libraryInfoList", libraryInfoList);

		//読込情報の中にエラーが存在するか確認する
		if (!libraryInfoList.isEmpty() &&
				!paramMap.get("REGIST_STATUS").equals(Constants.REGIST_STATUS_FILE_READ)) {
			paramMap.put("REGIST_DATE", libraryInfoList.get(0).get("REGIST_DATE"));
			boolean isOK = registerService.checkMultiLibraryExcelData(paramMap);
			paramMap.put("ERROR_FLG", isOK? Constants.FLAG_OFF:Constants.FLAG_ON);
		}

		//出力パラメータを設定
		Map<String,Object> mapA = new HashMap<String, Object>();
		mapA.put("ERROR_FLG",       paramMap.get("ERROR_FLG"));
		mapA.put("REGIST_STATUS",   paramMap.get("REGIST_STATUS"));
		mapA.put("TOTAL_CNT",       libraryInfoList.size());
		model.put("paramInfo", mapA);

		return "/register/registerMultiLibrary";
	}


	/*
	 * KLB231_教材一括登録画面の「読込する」ボタン押下
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/readMultiLibraryFile.do")
	public @ResponseBody Map<?, ?> readMultiLibraryFile(
											DefaultMultipartHttpServletRequest request,
											@RequestParam Map<String,Object> paramMap) throws Throwable {

		//添付ファイルを取得する
		MultipartFile excelFile = request.getFile("EXCEL_FILE");
		MultipartFile zipFile   = request.getFile("ZIP_FILE");

		//multipartResolverがファイルサイズ超過エラーを返している場合、画面にエラーを表示する
		Exception multipart_exception = (Exception)request.getAttribute("exception");
		if(multipart_exception instanceof MaxUploadSizeExceededException) {
			return asyncResponseMap.setMessage("[教材ファイル]には500MB以下のファイルを添付して下さい。").setSuccessFlag(false);
		}

		boolean isOK = false;

		// 教材ファイルが添付されてないか
		boolean isEmptyZipFile = false;
		if(zipFile == null || zipFile.isEmpty()){
			isEmptyZipFile = true;
		}

		try {
			//該当ユーザが読込した仮データを削除して置く
			registerService.deleteCurrMultiLibraryData(paramMap);

			//教材情報一覧EXCELファイルを読込する
			registerService.readMultiLibraryExcelFile(paramMap, excelFile, isEmptyZipFile);

			//EXCEL記載内容にエラーが存在するか確認する
			isOK = registerService.checkMultiLibraryExcelData(paramMap);

			if (isOK && zipFile != null && !zipFile.isEmpty()) {
				//ZIPフアイルをアップロードし、EXCELファイル記載内容と比較する
				isOK = registerService.uploadMultiLibraryZipFile(paramMap, zipFile);
			}
		}
		catch (Throwable e) {
			//該当ユーザが読込した仮データを削除する
			registerService.deleteCurrMultiLibraryData(paramMap);

			e.printStackTrace();
			if (StringUtil.isEmpty(e.getMessage())) {
				return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
			} else {
				return asyncResponseMap.setMessage(e.getMessage()).setSuccessFlag(false);
			}
		}

		//KLB231_教材一括登録画面に遷移する
		String paramStr = "?REGIST_STATUS="  + Constants.REGIST_STATUS_FILE_READ +
						  "&REGIST_DATE="    + paramMap.get("REGIST_DATE");
		if (isOK) {
			paramStr = paramStr + "&ERROR_FLG=" + Constants.FLAG_OFF;
			return asyncResponseMap.setUrl("/register/viewRegisterMultiPage.do"+paramStr);
		}
		else {
			paramStr = paramStr + "&ERROR_FLG=" + Constants.FLAG_ON;
			return asyncResponseMap.setMessage("読込に失敗しました。読込結果の確認の上、ファイルを修正して下さい。")
								   .setUrl("/register/viewRegisterMultiPage.do"+paramStr);
		}
	}


	/*
	 * KLB231_教材一括登録画面の「登録する」ボタン押下
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping(value = "/register/registerMultiLibrary.do")
	public @ResponseBody Map<?, ?> registerMultiLibrary(@RequestParam Map<String,Object> paramMap) throws Throwable {

		//入力パラメータを設定
		paramMap.put("REGIST_STATUS", Constants.REGIST_STATUS_INSERT);

		//読込完了情報を取得する
		List<Map<String,Object>> libraryInfoList = registerService.selectMultiLibraryForRegist(paramMap);

		int registCnt = 0;

		try {
			//読込件数分、各教材の登録処理を行う
			for (Map<String,Object> libraryInfo : libraryInfoList) {
				boolean result = registerService.registerMultiLibrary(paramMap, libraryInfo);
				if (result) {
					registCnt++;
				}
			}
		}
		catch (Throwable e) {
			e.printStackTrace();

			//KLB231_教材一括登録画面に戻る
			return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
		}

		//読込全件が登録成功した場合
		if (libraryInfoList.size() == registCnt) {
			//該当ユーザ読込分の仮データを削除する
			registerService.deleteCurrMultiLibraryData(paramMap);

			//KLB211_登録教材一覧画面に遷移する
			return asyncResponseMap.setSaveOkMessage().setUrl("/register/viewRegisteredListPage.do");
		}
		//全件登録失敗した場合
		else if (registCnt == 0) {
			//KLB231_教材一括登録画面に戻る
			return asyncResponseMap.setMessage("登録処理に失敗しました。").setSuccessFlag(false);
		}
		//一部登録成功した場合
		else {
			//KLB231_教材一括登録画面に遷移する
			return asyncResponseMap.setMessage("総"+libraryInfoList.size()+"件の内、"+registCnt+"件登録されました。")
								   .setUrl("/register/viewRegisterMultiPage.do");
		}
	}


	/*
	 * ファイル未添付の場合のエラー対策（ファイル未入力の場合はmultipartfileにObjectタイプが設定されるようにする）
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(MultipartFile.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				//setValue(text);
				setValue(null);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private String createSearchParam(Map<String,Object> paramMap) {
		String paramStr = "";
		Iterator entries = paramMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry)entries.next();
			String keyName = (String)entry.getKey();
			if (keyName.equals("page") || keyName.equals("scrollTop") || keyName.startsWith("SEARCH_")) {
				paramStr = paramStr + (paramStr.equals("")?"":"&") + keyName + "=" + entry.getValue();
			}
		}
		return paramStr;
	}

}
