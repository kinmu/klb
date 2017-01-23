package jp.go.saga.controller.klb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jp.go.saga.common.Constants;
import jp.go.saga.service.klb.KlbCommonService;
import jp.go.saga.service.klb.LibraryService;
import jp.go.saga.service.klb.RegisterService;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bsmedia.system.util.StringUtil;

@Controller
public class LibraryDetailController {


	private static final Logger logger = LoggerFactory.getLogger(LibraryDetailController.class);

	@Autowired
	LibraryService libraryService;

	@Autowired
	RegisterService registerService;

	@Autowired
	KlbCommonService klbCommonService;


	/* 教材詳細画面 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/read/detailLibrary.do")
	public String viewDetailLibrary(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 教材閲覧履歴データを更新
		Map historyLibrary = libraryService.selectUsedHistoryByReadDateAndLoginId(paramMap);
		// 教材閲覧履歴が存在しない場合、登録（存在する場合は更新）
		if(historyLibrary == null || historyLibrary.isEmpty()){

			// 登録項目を設定：学年、学級
			paramMap.put("GRADE_CLASS_ID", StringUtil.getStr(String.valueOf( session.getAttribute(Constants.GLOBAL_GRADE_CLASS_ID)),""));
			paramMap.put("GRADE_CODE", StringUtil.getStr(String.valueOf( session.getAttribute(Constants.GLOBAL_GRADE_CODE)),""));

			// 閲覧履歴を登録
			libraryService.insertUsedHistory(paramMap);

			// 教材閲覧カウントアップ
			//(ユーザは１日１つの教材に対して１回のみ閲覧のカウントアップができる)
			if(libraryService.updateLibraryReadCount(paramMap) < 1){
				// TODO 教材閲覧更新失敗
			}

		} else {
			libraryService.updateUsedHistory(paramMap);
		}

		// 教材情報検索
		Map libraryInfo =libraryService.selectLibraryByLibraryId(paramMap);

		List<String> fileNameList = new ArrayList<String>();	// 添付ファイルリスト（サイズ編集用）
		List<String> realFileList = new ArrayList<String>();	// 変更後ファイル名リスト
		List<String> orgNameList = new ArrayList<String>();		// 変更前ファイル名リスト
		List<String> linkAddressList = new ArrayList<String>();	// リンク
		List<String> viewLinkList = new ArrayList<String>();	// 表示編集用リンク
		List<String> fileSnoList = new ArrayList<String>();		// 添付ファイルSNO

		if(libraryInfo != null && !libraryInfo.isEmpty()){

			// オリジナルフィアル名にMByte変換数値を設定
			orgNameList = Arrays.asList(StringUtil.getStr(MapUtils.getString(libraryInfo, "FILE_NAME_BEFORE"), "").split(","));
			List<String> fileSizeList = Arrays.asList(StringUtil.getStr(MapUtils.getString(libraryInfo, "FILE_SIZE"), "").split(","));

			for(int i = 0; i < orgNameList.size(); i++){
				String fileName = orgNameList.get(i);

				if(!fileName.isEmpty() && fileName.length() > 40){
					fileName = fileName.substring(0,39).concat("...");
				}

				for(int j = i; j <= i; j++ ){
					if(j < fileSizeList.size()){
						if(fileSizeList.get(j) == null || fileSizeList.get(j).isEmpty()){
							break;
						} else {
							Double dSize = Double.valueOf( fileSizeList.get(j));
							BigDecimal bd = null;
							if(dSize < (1024*1024)){
								Double d = Double.valueOf(dSize/1024);
								bd = new BigDecimal(d);
								BigDecimal newbd = bd.setScale(0, BigDecimal.ROUND_DOWN);
								fileName = fileName.concat(" ("+newbd+" KByte)");
							} else {
								Double d = Double.valueOf(dSize/(1024*1024));
								bd = new BigDecimal(d);
								BigDecimal newbd = bd.setScale(0, BigDecimal.ROUND_DOWN);
								fileName = fileName.concat(" ("+newbd+" MByte)");
							}
						}
					}
				}
				logger.debug("FILE_NAME["+(i+1)+"] : "+ fileName);
				fileNameList.add(fileName);

			}

			// リンクリストを設定
			linkAddressList = Arrays.asList(StringUtil.getStr(MapUtils.getString(libraryInfo, "LINK_ADDRESS"), "").split(","));
			for(String link : linkAddressList){
				String subLink = "";
				if(!"".equals(link) && link.length() > 54 ){
					subLink = link.substring(0, 53).concat("...");
				} else {
					subLink = link;
				}
				logger.debug("LINK : "+ subLink);
				viewLinkList.add(subLink);

			}

			// 物理ファイル名
			realFileList = Arrays.asList(StringUtil.getStr(MapUtils.getString(libraryInfo, "FILE_NAME_AFTER"), "").split(","));
			fileSnoList = Arrays.asList(StringUtil.getStr(MapUtils.getString(libraryInfo, "FILE_SNO"), "").split(","));
		}

		// modelに出力情報を設定
		model.put("libraryInfo", libraryInfo);
		model.put("fileNameList", fileNameList);
		model.put("realFileList", realFileList);
		model.put("linkAddressList",linkAddressList);
		model.put("viewLinkList",viewLinkList);
		model.put("fileSnoList",fileSnoList);
		model.put("LIBRARY_ID", StringUtil.getStr(MapUtils.getString(paramMap, "LIBRARY_ID"), ""));
		model.put("page",StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));

		// 教材の一覧用Formデータ
		model.put("SEARCH_TYPE",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TYPE"), ""));
		model.put("SORT_CODE",StringUtil.getStr(MapUtils.getString(paramMap, "SORT_CODE"), ""));
		model.put("SEARCH_CATEGORY",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CATEGORY"), ""));
		model.put("CONTENT_INTRO",StringUtil.getStr(MapUtils.getString(paramMap, "CONTENT_INTRO"), ""));
		model.put("KEY_WORD",StringUtil.getStr(MapUtils.getString(paramMap, "KEY_WORD"), ""));
		model.put("SELECT_CATEGORY_ID",StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_CATEGORY_ID"), ""));
		// 新しい教材～あなたが今まで見た教材用Formデータ
		model.put("GRADE_CODE_FROM",StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_FROM"), ""));
		model.put("GRADE_CODE_TO",StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_TO"), ""));
		model.put("CATEGORY_ID",StringUtil.getStr(MapUtils.getString(paramMap, "CATEGORY_ID"), ""));
		model.put("RESEARCH",StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""));
		model.put("PERIOD_CODE",StringUtil.getStr(MapUtils.getString(paramMap, "PERIOD_CODE"), ""));
		// 詳細検索
		model.put("SEARCH_TAG_1",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TAG_1"), ""));
		model.put("SEARCH_LIBRARY_ID",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_LIBRARY_ID"), ""));
		model.put("SEARCH_TITLE",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TITLE"), ""));
		model.put("SEARCH_CONTENT_INTRO",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CONTENT_INTRO"), ""));
		model.put("SEARCH_CATEGORY",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CATEGORY"), ""));
		model.put("SEARCH_GRADE_FROM",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_FROM"), ""));
		model.put("SEARCH_GRADE_TO",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_TO"), ""));

		// KLB211_登録教材一覧画面に戻るためのパラメータセット
		if (Constants.GAMEN_ID_REGISTER_LIST.equals(session.getAttribute("GAMEN_ID"))) {
			Map<String,Object> tempMap = new HashMap<String,Object>();
			registerService.copySearchParam(paramMap, tempMap);
			model.put("KLB211_param", tempMap);
		}

		// 画面情報をsessionへ設定
		session.setAttribute("BEFORE_GAMEN_ID", session.getAttribute("GAMEN_ID").toString());
		session.setAttribute("GAMEN_ID", session.getAttribute("GAMEN_ID").toString());

		return "/read/detailLibrary";
	}


	@RequestMapping("/read/updateExclusiveControl.do")
	public void updateExclusiveControl(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 排他テーブルを削除
		klbCommonService.deleteExclusiveControlInfo(paramMap);

		paramMap.put("LOGIN_ID", StringUtil.getStr(MapUtils.getString(paramMap, "USER_ID"), ""));

		@SuppressWarnings("rawtypes")
		Map historyLibraryMap = libraryService.selectUsedHistoryByReadDateAndLoginId(paramMap);

		// 1日に2回ダウンロードはカウントしない
		if(!"1".equals(StringUtil.getStr(MapUtils.getString(historyLibraryMap, "DOWNLOAD_FLAG"), ""))){
			// 閲覧履歴にダウンロードフラグを１に更新
			paramMap.put("DOWNLOAD_FLAG", "1");
			libraryService.updateUsedHistory(paramMap);
			// 教材のダウンロード数をカウントアップ
			libraryService.updateLibraryDownloadCount(paramMap);
		}

	}


}
