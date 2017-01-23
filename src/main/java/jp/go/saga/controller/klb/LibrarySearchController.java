package jp.go.saga.controller.klb;

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
public class LibrarySearchController {


	private static final Logger logger = LoggerFactory.getLogger(LibrarySearchController.class);

	@Autowired
	LibraryService libraryService;

	@Autowired
	CodeService codeService;

	// 教材検索初期表示
	@RequestMapping("/read/viewSearchLibrary.do")
	public String initSearchLibrary(@RequestParam Map<String, Object> paramMap,
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
			paramMap.put("SEARCH_GRADE_FROM",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
			paramMap.put("SEARCH_GRADE_TO",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
		}

		// 学年コードリスト
		model.put("GRADE_CODE_LIST", gradeCodeList);
		// 教科など
		model.put("CATEGORY_LIST", StringUtil.getStr(String.valueOf(session.getAttribute("CATEGORY_LIST")), ""));

		// 画面入力値を再表示
		model.put("SEARCH_GRADE_FROM", StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_FROM"), ""));
		model.put("SEARCH_GRADE_TO",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_GRADE_TO"), ""));
		model.put("SEARCH_CATEGORY", StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CATEGORY"), ""));
		// メインからカテゴリIDが存在する場合、初期表示する
		if("".equals( StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CATEGORY"), ""))
			&& !"".equals( StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_CATEGORY_ID"), ""))){
			model.put("SEARCH_CATEGORY", StringUtil.getStr(MapUtils.getString(paramMap, "SELECT_CATEGORY_ID"), ""));
		}
		model.put("SEARCH_TAG_1",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TAG_1"), ""));
		model.put("SEARCH_TITLE",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_TITLE"), ""));
		model.put("SEARCH_LIBRARY_ID",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_LIBRARY_ID"), ""));
		model.put("SEARCH_CONTENT_INTRO",StringUtil.getStr(MapUtils.getString(paramMap, "SEARCH_CONTENT_INTRO"), ""));

		model.put("GAMEN_ID", Constants.GAMEN_ID_SEARCH);
		model.put("MENU_ID", StringUtil.getStr(MapUtils.getString(paramMap, "MENU_ID"), ""));

		session.setAttribute("BEFORE_GAMEN_ID", session.getAttribute("GAMEN_ID").toString());
		session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_SEARCH);

		return "/read/search";
	}


}
