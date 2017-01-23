package jp.go.saga.controller.klb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jp.go.saga.common.Constants;
import jp.go.saga.service.klb.CodeService;
import jp.go.saga.service.klb.LibraryService;

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
public class LibraryListController {

	private static final Logger logger = LoggerFactory.getLogger(LibraryListController.class);

	@Autowired
	LibraryService libraryService;

	@Autowired
	CodeService codeService;

	/* 教材の一覧画面 */
	@RequestMapping("/read/viewSearchLibraryList.do")
	public String viewSearchLibraryList(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 変更したソート順が指定されたない場合、初期値(09)
		paramMap.put("SORT_CODE", StringUtil.getStr(MapUtils.getString(paramMap, "SORT_CODE"), "10"));

		// 表示用ソート順コードを取得
		paramMap.put("CODE_ID", Constants.CODE_KLB_REGISTER_LIST_SORT);
		List<?> sortCodeList = codeService.getCode(paramMap);

		// 学年表示用リスト取得
		paramMap.put("CODE_ID", Constants.CODE_PORTAL_GRADE);
		List<?> gradeCodeList = codeService.getGradeCode(paramMap);
		model.put("GRADE_CODE_LIST", gradeCodeList);

		// カテゴリ押下の検索または、ソート順で再検索
		if (!"".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_CATEGORY_ID"), ""))
				|| "CATEGORY".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TYPE"), ""))) {

			logger.debug("parmaMap.SELECT_CATEGORY_ID : ["
					+ StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_CATEGORY_ID"), "") + "]");

			session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_LIBRARY_LIST);

			// 画面再表示の場合、前画面IDに設定しない
			if (!StringUtil.getStr(String.valueOf(session.getAttribute("BEFORE_GAMEN_ID")), "").isEmpty()
					&& !StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), "").equals(
							StringUtil.getStr(String.valueOf(session.getAttribute("BEFORE_GAMEN_ID")), ""))) {
				session.setAttribute("BEFORE_GAMEN_ID", String.valueOf(session.getAttribute("GAMEN_ID")));
			}

			logger.debug("session.SELECT_CATEGORY_ID : ["
					+ StringUtil.getStr(String.valueOf(session.getAttribute("SELECT_CATEGORY_ID")), "") + "]");

			// カテゴリに該当する教材検索
			List<?> libraryList = libraryService.selectLibraryListByCategoryId(paramMap);

			// 検索結果の件数情報を設定
			if (libraryList.isEmpty()) {
				model.put("TOTAL_CNT", 0);
			} else {
				model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
				model.put("START_SNO", paramMap.get("START_SNO"));
				model.put("END_SNO", paramMap.get("END_SNO"));
			}

			// 画面表示情報をmodelへ格納
			model.put("LIST_SIZE", libraryList.size());
			model.put("SORT_CODE_LIST", sortCodeList);
//			model.put("MENU_ID", StringUtil.getStr(MapUtils.getString(paramMap, "MENU_ID"), ""));
			model.put("LIBRARY_LIST", libraryList);
			model.put("SELECT_CATEGORY_ID", StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_CATEGORY_ID"), ""));
			model.put("SELECT_PARENT_CATEGORY",
					StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_PARENT_CATEGORY"), ""));
			model.put("SORT_CODE", StringUtil.getStr(MapUtils.getString(paramMap, "SORT_CODE"), ""));
			model.put("SEARCH_TYPE", "CATEGORY");
			model.put("RESEARCH", "N_RESEARCH");
			model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));
			//入力した検索キーワードを結合する
			getKeyword(paramMap, session, model);

			return "/read/libraryList";

		} else {

			if (!StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), "").isEmpty()) {

				// メイン画面のキーワード検索または、ソート順で再検索
				if (Constants.GAMEN_ID_MAIN.equals(StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")),""))
						|| "KEY".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TYPE"), ""))) {

					String keyWord = StringUtil.getStr(MapUtils.getString(paramMap, "KEY_WORD"), "");
					if (!keyWord.isEmpty()) {
						List<String> keywordList = Arrays.asList(keyWord.replace("　", " ").split(" "));
						paramMap.put("KEYWORD_LIST", keywordList);
					}

					List<?> libraryList = libraryService.selectSearchKeywordAndCategory(paramMap);

					// 検索結果の件数情報を設定
					if (libraryList.isEmpty()) {
						model.put("TOTAL_CNT", 0);
					} else {
						model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
						model.put("START_SNO", paramMap.get("START_SNO"));
						model.put("END_SNO", paramMap.get("END_SNO"));
					}

					// 画面表示情報をmodelへ格納
					model.put("LIST_SIZE", libraryList.size());
					model.put("SORT_CODE_LIST", sortCodeList);
					model.put("LIBRARY_LIST", libraryList);
					model.put("SORT_CODE", StringUtil.getStr(MapUtils.getString(paramMap, "SORT_CODE"), ""));
					model.put("SEARCH_TYPE", "KEY");
					model.put("RESEARCH", "N_RESEARCH");
					model.put("SEARCH_CATEGORY", StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CATEGORY"), ""));
					model.put("CONTENT_INTRO", StringUtil.getStr(MapUtils.getString(paramMap, "CONTENT_INTRO"), ""));
					model.put("KEY_WORD", StringUtil.getStr(MapUtils.getString(paramMap, "KEY_WORD"), ""));
					model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));
					//入力した検索キーワードを結合する
					getKeyword(paramMap, session, model);

					session.setAttribute("BEFORE_GAMEN_ID",
							StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), ""));
					session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_LIBRARY_LIST);

					return "/read/libraryList";

					// 詳細検索または、ソート順で再検索
				} else if (Constants.GAMEN_ID_SEARCH.equals(StringUtil.getStr(
						String.valueOf(session.getAttribute("GAMEN_ID")), ""))
						|| "SEARCH".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TYPE"), ""))) {

					// パラメタのタグ編集
					if(!"".equals(StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TAG_1"), ""))){
						List<String> searchTagList = Arrays.asList( StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TAG_1"), "").replaceAll("　", " ").split(" "));
						paramMap.put("SEARCH_TAG_LIST", searchTagList);
					}

					paramMap.put("GRADE_CODE_FROM", StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_FROM"), ""));
					paramMap.put("GRADE_CODE_TO",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_TO"), ""));

					// 検索
					List<?> libraryList = libraryService.selectSearchByAllConditions(paramMap);

					// 検索結果の件数情報を設定
					if (libraryList.isEmpty()) {
						model.put("TOTAL_CNT", 0);
					} else {
						model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
						model.put("START_SNO", paramMap.get("START_SNO"));
						model.put("END_SNO", paramMap.get("END_SNO"));
					}

					// 画面表示情報をmodelへ格納
					model.put("LIBRARY_LIST", libraryList);
					model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));
					model.put("LIST_SIZE", libraryList.size());
					model.put("SORT_CODE_LIST", sortCodeList);
					model.put("LIBRARY_LIST", libraryList);
					model.put("SORT_CODE", StringUtil.getStr(MapUtils.getString(paramMap, "SORT_CODE"), ""));
					model.put("SEARCH_TYPE", "SEARCH");
					model.put("RESEARCH", StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""));
					model.put("SEARCH_GRADE_FROM", StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_FROM"), ""));
					model.put("SEARCH_GRADE_TO",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_TO"), ""));
					model.put("SEARCH_CATEGORY", StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CATEGORY"), ""));
					model.put("SEARCH_TAG_LIST",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TAG_LIST"), ""));
					model.put("SEARCH_TAG_1",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TAG_1"), ""));
					model.put("SEARCH_TITLE",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TITLE"), ""));
					model.put("SEARCH_LIBRARY_ID",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_LIBRARY_ID"), ""));
					model.put("SEARCH_CONTENT_INTRO",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CONTENT_INTRO"), ""));
					//入力した検索キーワードを結合する
					getKeyword(paramMap, session, model);

					session.setAttribute("BEFORE_GAMEN_ID",
							StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), ""));
					session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_LIBRARY_LIST);

					return "/read/libraryList";
				}
			}
		}

		return null;
	}

	/* 新着教材画面用 */
	@RequestMapping("/read/viewNewLibraryList.do")
	public String viewNewLibraryLis(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 表示用学年リスト取得
		paramMap.put("CODE_ID", Constants.CODE_PORTAL_GRADE);
		List<?> gradeCodeList = codeService.getGradeCode(paramMap);

		// 学年設定
		// 初回検索条件
		if (StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), "").isEmpty()) {
			paramMap.put("RESEARCH", "N_RESEARCH");
		}
		// 初回のみsessionから学年情報取得
		if ("N_RESEARCH".equals(StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""))) {
			paramMap.put("GRADE_CODE_FROM",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
			paramMap.put("GRADE_CODE_TO",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
		}

		// 新着教材検索
		List<?> libraryList = libraryService.selectNewLibraryList(paramMap);

		// 画面表示情報をmodelへ格納
		// 検索結果の件数情報を設定
		if (libraryList.isEmpty()) {
			model.put("TOTAL_CNT", 0);
		} else {
			model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
			model.put("START_SNO", paramMap.get("START_SNO"));
			model.put("END_SNO", paramMap.get("END_SNO"));
		}

		// 表示用ソート:sessionからカテゴリリスト
		model.put("LIST_SIZE", libraryList.size());
		model.put("LIBRARY_LIST", libraryList);
		model.put("CATEGORY_LIST", StringUtil.getStr(String.valueOf(session.getAttribute("CATEGORY_LIST")), ""));
		model.put("CATEGORY_ID", StringUtil.getStr(MapUtils.getString(paramMap, "CATEGORY_ID"), ""));
		model.put("GRADE_CODE_LIST", gradeCodeList);
		model.put("RESEARCH", StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""));
		model.put("GRADE_CODE_FROM", StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_FROM"), ""));
		model.put("GRADE_CODE_TO", StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_TO"), ""));
		model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));

		// session情報設定
		session.setAttribute("BEFORE_GAMEN_ID", StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), ""));
		session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_NEW_LIBRARY);

		return "/read/libraryList";
	}

	/* 注目の教材画面 */
	@RequestMapping("/read/viewPickupLibraryList.do")
	public String viewPickupLibraryList(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 表示用学年リスト取得
		paramMap.put("CODE_ID", Constants.CODE_PORTAL_GRADE);
		List<?> gradeCodeList = codeService.getGradeCode(paramMap);

		// 学年設定
		// 初回検索条件
		if (StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), "").isEmpty()) {
			paramMap.put("RESEARCH", "N_RESEARCH");
		}
		// 初回のみsessionから学年情報取得
		if ("N_RESEARCH".equals(StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""))) {
			paramMap.put("GRADE_CODE_FROM",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
			paramMap.put("GRADE_CODE_TO",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
		}

		// 注目の教材検索
		List<?> libraryList = libraryService.selectPickupLibraryList(paramMap,
				StringUtil.getStr(String.valueOf(session.getAttribute("SYSOPEN_YEAR_DATE")), ""));

		// 画面表示情報をmodelへ格納
		// 検索結果の件数情報を設定
		if (libraryList.isEmpty()) {
			model.put("TOTAL_CNT", 0);
		} else {
			model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
			model.put("START_SNO", paramMap.get("START_SNO"));
			model.put("END_SNO", paramMap.get("END_SNO"));
		}

		// 表示用ソート:sessionからカテゴリリスト
		model.put("LIST_SIZE", libraryList.size());
		model.put("RESEARCH", StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""));
		model.put("LIBRARY_LIST", libraryList);
		model.put("CATEGORY_LIST", StringUtil.getStr(String.valueOf(session.getAttribute("CATEGORY_LIST")), ""));
		model.put("GRADE_CODE_LIST", gradeCodeList);
		model.put("GRADE_CODE_FROM", StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_FROM"), ""));
		model.put("GRADE_CODE_TO", StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_TO"), ""));
		model.put("PICKUP_START_TIME", StringUtil.getStr(MapUtils.getString(paramMap, "PICKUP_START_TIME"), ""));
		model.put("PICKUP_END_TIME", StringUtil.getStr(MapUtils.getString(paramMap, "PICKUP_END_TIME"), ""));
		model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));

		session.setAttribute("BEFORE_GAMEN_ID", StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), ""));
		session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_PICKUP_LIBRARY);

		return "/read/libraryList";
	}

	/* よく利用されている教材画面 */
	@RequestMapping("/read/viewDownloadLibraryList.do")
	public String viewDownloadLibraryList(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 学年表示用リスト取得
		paramMap.put("CODE_ID", Constants.CODE_PORTAL_GRADE);
		List<?> gradeCodeList = codeService.getGradeCode(paramMap);

		// 初回検索条件
		if (StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), "").isEmpty()) {
			paramMap.put("RESEARCH", "N_RESEARCH");
		}
		// 初回のみsessionから学年情報取得
		if ("N_RESEARCH".equals(StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""))) {
			paramMap.put("GRADE_CODE_FROM",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
			paramMap.put("GRADE_CODE_TO",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
		}
		// 期間表示用リスト取得
		paramMap.put("CODE_ID", Constants.CODE_KLB_OPENED_PERIOD_SORT);
		List<?> periodCodeList = codeService.getCode(paramMap);

		// よく利用されている教材検索
		List<?> libraryList = libraryService.selectDownloadLibraryList(paramMap);

		// 画面表示情報をmodelへ格納
		// 検索結果の件数情報を設定
		if (libraryList.isEmpty()) {
			model.put("TOTAL_CNT", 0);
		} else {
			model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
			model.put("START_SNO", paramMap.get("START_SNO"));
			model.put("END_SNO", paramMap.get("END_SNO"));
		}
		model.put("LIST_SIZE", libraryList.size());
		model.put("RESEARCH", StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""));
		model.put("LIBRARY_LIST", libraryList);
		model.put("CATEGORY_LIST", StringUtil.getStr(String.valueOf(session.getAttribute("CATEGORY_LIST")), ""));
		model.put("CATEGORY_ID", StringUtil.getStr(MapUtils.getString(paramMap, "CATEGORY_ID"), ""));
		model.put("GRADE_CODE_LIST", gradeCodeList);
		model.put("PERIOD_CODE_LIST", periodCodeList);
		model.put("PERIOD_CODE", StringUtil.getStr(MapUtils.getString(paramMap, "PERIOD_CODE"), ""));
		model.put("GRADE_CODE_FROM", StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_FROM"), ""));
		model.put("GRADE_CODE_TO", StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE_TO"), ""));
		model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));

		session.setAttribute("BEFORE_GAMEN_ID", StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), ""));
		session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_DOWNLOAD_LIBRARY);

		return "/read/libraryList";
	}

	/* あなたが今まで見た教材画面  */
	@RequestMapping("/read/viewHistoryLibraryList.do")
	public String 教材履歴画面用(@RequestParam Map<String, Object> paramMap,
			HttpSession session,
			ModelMap model) throws Throwable {

		// 期間表示用リスト取得
		paramMap.put("CODE_ID", Constants.CODE_KLB_OPENED_PERIOD_SORT);
		List<?> periodCodeList = codeService.getCode(paramMap);

		// いままで見た教材検索
		List<?> libraryList = libraryService.selectHistoryLibraryList(paramMap);

		// 画面表示情報をmodelへ格納
		// 検索結果の件数情報を設定
		if (libraryList.isEmpty()) {
			model.put("TOTAL_CNT", 0);
		} else {
			model.put("TOTAL_CNT", paramMap.get("TOTAL_CNT"));
			model.put("START_SNO", paramMap.get("START_SNO"));
			model.put("END_SNO", paramMap.get("END_SNO"));
		}

		model.put("LIST_SIZE", libraryList.size());
		model.put("RESEARCH", StringUtil.getStr(MapUtils.getString(paramMap, "RESEARCH"), ""));
		model.put("LIBRARY_LIST", libraryList);
		model.put("CATEGORY_LIST", StringUtil.getStr(String.valueOf(session.getAttribute("CATEGORY_LIST")), ""));
		model.put("CATEGORY_ID", StringUtil.getStr(MapUtils.getString(paramMap, "CATEGORY_ID"), ""));
		model.put("PERIOD_CODE_LIST", periodCodeList);
		model.put("PERIOD_CODE", StringUtil.getStr(MapUtils.getString(paramMap, "PERIOD_CODE"), ""));
		model.put("page", StringUtil.getStr(MapUtils.getString(paramMap, "page"), ""));

		session.setAttribute("BEFORE_GAMEN_ID", StringUtil.getStr(String.valueOf(session.getAttribute("GAMEN_ID")), ""));
		session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_HISTORY_LIBRARY);

		return "/read/libraryList";
	}

	/* 入力した検索キーワードを結合する **/
	@SuppressWarnings({"unchecked"})
	public ModelMap getKeyword( Map<String, Object> paramMap,HttpSession session, ModelMap model ){

		// キーワード結合文字
		String keyword = "";

		/* ***** 簡易検索キーワード *****/
		String topKeyword = StringUtil.getStr(MapUtils.getString(paramMap, "KEY_WORD"), "");
		List<Map<String, Object>> grade_code_list =  (ArrayList<Map<String, Object>>)((Object)model.get("GRADE_CODE_LIST"));

		if(!"".equals(topKeyword)){
			keyword = keyword.concat(topKeyword).concat("、");
			logger.debug("検索キーワード【簡易キーワード】：" + keyword);
		}

		/* ***** 詳細検索キーワード *****/
		// 学年
		String grade_from = StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_FROM"), "");
		String grade_to = StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_TO"), "");
		if(!"".equals(grade_from)){
			for(Map<String, Object> map: grade_code_list){
				String grade_code = StringUtil.getStr(MapUtils.getString(map, "CODE"), "");
				if(grade_code.equals(grade_from)){
					keyword = keyword.concat(StringUtil.getStr(MapUtils.getString(map, "ITEM1"), "")).concat("～");
					break;
				}
			}
		}

		if(!"".equals(grade_to)){
			for(Map<String, Object> map: grade_code_list){
				String grade_code = StringUtil.getStr(MapUtils.getString(map, "CODE"), "");
				if(grade_code.equals(grade_to)){
					// 学年FROMが存在する場合、"～"を結合しない
					if(!"".equals(grade_from)){
						keyword = keyword.concat(StringUtil.getStr(MapUtils.getString(map, "ITEM1"), ""));
					}
					// "～"を結合
					else{
						keyword = keyword.concat("～").concat(StringUtil.getStr(MapUtils.getString(map, "ITEM1"), ""));
					}
					break;
				}
			}
		}

		if(!"".equals(grade_from) || !"".equals(grade_to)){
			keyword = keyword.concat("、");
			logger.debug("検索キーワード【学年】：" + keyword);
		}

		/* ***** カテゴリ検索キーワード *****/

		// カテゴリID
		String category_id = StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_CATEGORY_ID"), "");
		String search_category = StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CATEGORY"), "");

		// カテゴリ表示名を取得
		if(!"".equals(category_id) || !"".equals(search_category) ){
			List<Map<String, Object>> category_list =  (ArrayList<Map<String, Object>>)((Object)session.getAttribute("CATEGORY_LIST"));
			for (Map<String, Object> map : category_list){
				String list_category_id = StringUtil.getStr(MapUtils.getString(map, "CATEGORY_ID"), "");

				// カテゴリIDが一致する、表示名を取得
				if(list_category_id.equals(category_id) || list_category_id.equals(search_category)){
					keyword = keyword.concat(StringUtil.getStr(MapUtils.getString(map, "JOIN_CATEGORY_NAME2"), "")).concat("、");
					logger.debug("検索キーワード【カテゴリID】：" + keyword);
				}
			}
		}

		// タグ
		String search_tag_1 = StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TAG_1"), "");
		if(!"".equals(search_tag_1)){
			keyword = keyword.concat(search_tag_1).concat("、");
			logger.debug("検索キーワード【タグ】：" + keyword);
		}

		// 教材の名前
		String search_title = StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TITLE"), "");
		if(!"".equals(search_title)){
			keyword = keyword.concat(search_title).concat("、");
			logger.debug("検索キーワード【教材の名前】：" + keyword);
		}
		// 教材登録番号
		String search_library_id = StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_LIBRARY_ID"), "");
		if(!"".equals(search_library_id)){
			keyword = keyword.concat(search_library_id).concat("、");
			logger.debug("検索キーワード【教材登録番号】：" + keyword);
		}
		// 教材の説明
		String search_content_intro = StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CONTENT_INTRO"), "");
		if(!"".equals(search_content_intro)){
			keyword = keyword.concat(search_content_intro).concat("、");
			logger.debug("検索キーワード【教材の説明】：" + keyword);
		}


		/* **** 切取編集 ****/
		// 結合したキーワードを表示サイズに切取＋「…」結合
		if(keyword.length() > 88){
			keyword = keyword.substring(0, 87).concat("…");
			logger.debug("検索キーワード【…】：" + keyword);
		}
		// 最後の「、」を削除
		else {
			if(keyword.length() > 1){
				keyword = keyword.substring(0, keyword.length()-1);
			}else{
				keyword = "指定なし";
			}
			logger.debug("検索キーワード【、削除】：" + keyword);
		}

		// 結合したキーワードを設定
		model.put("KEYWORD", keyword);

		return model;

	}
}
