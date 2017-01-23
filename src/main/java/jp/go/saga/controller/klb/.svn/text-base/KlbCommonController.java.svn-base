package jp.go.saga.controller.klb;

import java.io.File;
import java.util.Map;

import jp.go.saga.common.AsyncResponseMap;
import jp.go.saga.common.view.bean.FileModel;
import jp.go.saga.service.klb.KlbCommonService;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.StringUtil;

@Controller
public class KlbCommonController {
	private static final Logger logger = LoggerFactory.getLogger(KlbCommonController.class);

	@Autowired
	KlbCommonService			klbCommonService;
	@Autowired
	AsyncResponseMap			asyncResponseMap;

//	// 특정 테이블/컬럼의 값을 사용중인 레코드 합계를 조회한다
//	@Secured({ Constants.ROLE_TEACHER })
//	@RequestMapping("/common/selectTableColumnUseSum.do")
//	public @ResponseBody
//	Object selectTableColumnUseSum(@RequestParam Map<String, Object> paramMap) throws Throwable {
//		//paramMap.put("EXCEPT_TABLE_NAME_LIST", exceptTableNamelist);
//		return klbCommonService.selectTableColumnUseSum(paramMap);
//	}
//
//	// 특정 테이블/컬럼의 값을 사용중인 레코드 합계를 조회한다 (대단원)
//	@Secured({ Constants.ROLE_TEACHER })
//	@RequestMapping("/common/selectTableColumnUpperUseSum.do")
//	public @ResponseBody
//	Object selectTableColumnUpperUseSum(@RequestParam Map<String, Object> paramMap) throws Throwable {
//		return klbCommonService.selectTableColumnUpperUseSum(paramMap);
//	}
//
//	// 특정 테이블/컬럼의 값을 사용중인 레코드 합계를 조회한다
//	@Secured({ Constants.ROLE_TEACHER })
//	@RequestMapping("/common/selectTableColumnArrayUseSum.do")
//	public @ResponseBody
//	Object selectTableColumnArrayUseSum(@RequestParam Map<String, Object> paramMap, @RequestParam("EXCEPT_TABLE_NAME_LIST") String[] exceptTableNamelist) throws Throwable {
//		paramMap.put("EXCEPT_TABLE_NAME_LIST", exceptTableNamelist);
//		return klbCommonService.selectTableColumnUseSum(paramMap);
//	}
//
//	// 특정 테이블/컬럼의 값의 현황을 조회
//	@Secured({ Constants.ROLE_TEACHER })
//	@RequestMapping("/common/selectTableColumnUseList.do")
//	public @ResponseBody
//	List<?> selectTableColumnUseList(@RequestParam Map<String, Object> paramMap) throws Throwable {
//		return klbCommonService.selectTableColumnUseList(paramMap);
//	}
//
//	@RequestMapping("/common/selectCurrDatetime.do")
//	public @ResponseBody
//	Map<String, Object> selectCurrDatetime(@RequestParam Map<String, Object> paramMap) throws Throwable {
//		return klbCommonService.selectCurrDatetime(paramMap);
//	}


	/*
	 * 排他情報を登録する
	 */
	@RequestMapping(value = "/common/insertExclusiveControl.do")
	public void insertExclusiveControl(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {

		klbCommonService.insertExclusiveControlInfo(paramMap);
	}


	/*
	 * 排他情報のチェックを行う
	 */
	@RequestMapping(value = "/common/checkExclusiveControl.do")
	public @ResponseBody Map<String,Object> checkExclusiveControl(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {

		return klbCommonService.selectExclusiveControlInfo(paramMap);
	}


	/*
	 * 排他情報を削除する
	 */
	@RequestMapping(value = "/common/deleteExclusiveControl.do")
	public void deleteExclusiveControl(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {

		klbCommonService.deleteExclusiveControlInfo(paramMap);
	}


	/*
	 * 教材表紙イメージ（サムネイル）表示
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/common/downloadThumbnailImage.do")
	public String downloadThumbnailImage(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {
		// 2015・3・20 脆弱性対応（パス トラバーサル）
		// ファイルパースを含む経路の変わり該当教材IDをパラメータとして渡すように修正。
		// 教材IDで「ファイルパス(FILE_PATH)」と「サムネール名(THUMBNAIL)」を取得し、対象ファイルを読み込む

		Map libraryInfo =  klbCommonService.selectLibraryInfoByLibraryId(paramMap);

		// FILE_PATH + THUMBNAIL
		String imagePath = StringUtil.getStr(MapUtils.getString(libraryInfo, "FILE_PATH"), "") .concat(
				StringUtil.getStr(MapUtils.getString(libraryInfo, "THUMBNAIL"), ""));
		logger.debug("#### サムネルパース： "+imagePath);

		File file = new File(imagePath);
		model.put("FILE_MODEL", new FileModel(file, file.getName()));
		return "fileDownloadView";
	}

	/*
	 * サンプルファイルをダウンロード
	 */
	@RequestMapping("/common/downloadSampleFile.do")
	public String downloadSampleFile(@RequestParam Map<String,String> paramMap, ModelMap model) throws Throwable {
		String filePath = ApplicationProperty.get("upload.path");
		if (filePath.lastIndexOf("/") != filePath.length()-1) {
			filePath = filePath + "/";
		}
		// 3/24 脆弱性対応　ファイル名をパラメータとして渡さない
		File file = new File(filePath + "sample.xlsx");
		model.put("FILE_MODEL", new FileModel(file, file.getName()));
		return "fileDownloadView";
	}

}