package jp.go.saga.controller.klb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jp.go.saga.common.Constants;
import jp.go.saga.common.view.bean.FileModel;
import jp.go.saga.service.klb.AnalyzeService;
import jp.go.saga.service.klb.CodeService;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.DateUtils;
import bsmedia.system.util.StringUtil;

@Controller
public class AnalyzeController {

	@Autowired
	CodeService codeService;
	@Autowired
	AnalyzeService analyzeService;


	/*
	 * KLB311_統計情報照会画面を初期表示
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping("/analyze/viewUseConditionPage.do")
	public String viewUseConditionPage(@RequestParam Map<String, Object> paramMap, ModelMap model) throws Throwable {

		//管理者ではない場合、システムエラーとする
		if (!paramMap.get(Constants.AUTH_CODE).equals(Constants.AUTH_CODE_ADMIN)) {
			return "redirect:/internalError.jsp";
		}

		//今日の日付を取得
		String today = DateUtils.getToday("yyyyMMdd");

		//統計情報タイプリストを取得（checkbox用）
		paramMap.put("CODE_ID", Constants.CODE_KLB_ANALYZE_TYPE);
		model.put("analyzeTypeList", codeService.getCode(paramMap));
		model.put("checkedTypeList", new String[]{"01"});

		//出力パラメータを設定
		Map<String,Object> mapA = new HashMap<String, Object>();
		mapA.put("ANALYZE_DATE_FROM", DateUtils.addDate(today, -7, false));
		mapA.put("ANALYZE_DATE_TO",   DateUtils.addDate(today, -1, false));
		mapA.put("TODAY",             today);
		model.put("paramInfo", mapA);

		//KLB311_統計情報照会画面に遷移する
		return "/analyze/useCondition";
	}


	/*
	 * 統計情報を抽出しファイルを生成する
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping("/analyze/createUseConditionFile.do")
	public String createUseConditionFile(@RequestParam Map<String, Object> paramMap,
										 @RequestParam(value="ANALYZE_TYPE", required=true) String[] analyzeTypeArray,
										 ModelMap model) throws Throwable {

		//管理者ではない場合、システムエラーとする
		if (!paramMap.get(Constants.AUTH_CODE).equals(Constants.AUTH_CODE_ADMIN)) {
			return "redirect:/internalError.jsp";
		}

		try {
			//統計データを抽出し、EXCELファイルに書き込み
			analyzeService.createAnalyzeData(paramMap, analyzeTypeArray);

			//生成したEXCELファイルを合わせてZIPに圧縮する
			analyzeService.createAnalyzeZipFile(paramMap);
		}
		catch (Exception e) {
			e.printStackTrace();

			//今回生成した統計情報ファイルを削除する
			analyzeService.deleteCurrAnalyzeFile(paramMap);

			//システムエラー画面に遷移する
			return "redirect:/internalError.jsp";
		}

		//統計情報タイプリストを取得（checkbox用）
		paramMap.put("CODE_ID", Constants.CODE_KLB_ANALYZE_TYPE);
		model.put("analyzeTypeList", codeService.getCode(paramMap));
		model.put("checkedTypeList", analyzeTypeArray);

		//出力パラメータを設定
		Map<String,Object> mapA = new HashMap<String, Object>();
		mapA.put("ANALYZE_DATE_FROM", paramMap.get("ANALYZE_DATE_FROM"));
		mapA.put("ANALYZE_DATE_TO",   paramMap.get("ANALYZE_DATE_TO"));
		mapA.put("TODAY",             DateUtils.getToday("yyyyMMdd"));
		mapA.put("ANALYZE_ZIP_FILE_NAME", paramMap.get("ANALYZE_ZIP_FILE_NAME").toString());
		model.put("paramInfo", mapA);

		//KLB311_統計情報照会画面に遷移する
		return "/analyze/useCondition";
	}


	/*
	 * 統計情報ZIPファイルをダウンロードする
	 */
	@Secured({Constants.ROLE_TEACHER})
	@RequestMapping("/analyze/downloadAnalyzeZipFile.do")
	public String downloadAnalyzeZipFile(@RequestParam Map<String, Object> paramMap, ModelMap model) throws Throwable {

		// 3/24 脆弱性対応　ファイル格納パスをパラメータとして渡さない

		//統計ファイル格納するフォルダ名を取得
		String printLocation = ApplicationProperty.get("excel.print.location");
		if (printLocation.lastIndexOf("/") != printLocation.length()-1) {
			printLocation =  printLocation + "/";
		}
		printLocation = printLocation + StringUtil.getStr(MapUtils.getString(paramMap, "ANALYZE_ZIP_FILE_NAME"), "").replaceAll(".zip", "") + "/"+
							StringUtil.getStr(MapUtils.getString(paramMap, "ANALYZE_ZIP_FILE_NAME"), "");

		String downloadFileName = "統計情報_" + paramMap.get("ANALYZE_DATE_FROM") + "～" + paramMap.get("ANALYZE_DATE_TO") + ".zip";
		File zipFile = new File(printLocation);
		model.put("FILE_MODEL", new FileModel(zipFile, downloadFileName));
		return "fileDownloadView";
	}
}
