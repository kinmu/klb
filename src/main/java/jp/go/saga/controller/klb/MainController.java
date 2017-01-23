package jp.go.saga.controller.klb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import jp.go.saga.common.Constants;
import jp.go.saga.security.PortalAuthenticationService;
import jp.go.saga.service.klb.CategoryService;
import jp.go.saga.service.klb.CodeService;
import jp.go.saga.service.klb.KlbCommonService;
import jp.go.saga.service.klb.LibraryService;
import jp.go.saga.service.klb.MainService;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.StringUtil;

@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	MainService mainService;

	@Autowired
	PortalAuthenticationService authenticationService;

	@Autowired
	KlbCommonService klbCommonService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	LibraryService libraryService;

	@Autowired
	CodeService codeService;

	@RequestMapping("/")
	public String root() {
		return "redirect:/main.do";
	}

	@RequestMapping("/main.do")
	public String initMain(@RequestParam Map<String, Object> paramMap, HttpSession session, ModelMap model)
			throws Throwable {

		Properties property = ApplicationProperty.getInstance();

		/* *********************************
		 * カテゴリ情報取得 */

		// 学生の場合、学種別カテゴリソート順を取得
		if (Constants.AUTH_CODE_STUDENT.equals(StringUtil.getStr(
				String.valueOf(session.getAttribute(Constants.AUTH_CODE)), ""))
			|| "01".equals(Constants.USER_TYPE)
			|| "02".equals(Constants.USER_TYPE)
			|| "03".equals(Constants.USER_TYPE)) {

			// コードID、学年を設定
			paramMap.put("CODE_ID", "K005");//コードID
			paramMap.put("CODE", StringUtil.getStr(MapUtils.getString(paramMap, "GRADE_CODE"), ""));//コードに学年を設定

			// 検索条件学年の初期値を設定
			paramMap.put("GRADE_CODE_FROM",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));
			paramMap.put("GRADE_CODE_TO",
					StringUtil.getStr(String.valueOf(session.getAttribute(Constants.GLOBAL_GRADE_CODE)), ""));

			// 学種が高校'03'の場合、カテゴリのソート順を降順に設定
			if (Constants.SCHOOL_TYPE_HIGH.equals(StringUtil.getStr(MapUtils.getString(paramMap, "SCHOOL_TYPE"), ""))) {
				paramMap.put(Constants.PARENT_CATEGORY_DESC,
						StringUtil.getStr(MapUtils.getString(codeService.selectCodeByCodeIdAndCode(paramMap), "ITEM1"),
								""));
			}
		}
		// ユーザ情報をsessionへ格納

		// ユーザ権限
		session.setAttribute(Constants.AUTH_CODE, StringUtil.getStr(MapUtils.getString(paramMap, Constants.AUTH_CODE), ""));
		session.setAttribute("CATEGORY_LIST", categoryService.getCategoryListForListbox(paramMap));//カテゴリリスト
		session.setAttribute("GAMEN_ID", Constants.GAMEN_ID_MAIN);// メイン画面の画面」ID
		session.setAttribute("SCHOOL_TYPE", StringUtil.getStr(MapUtils.getString(paramMap, "SCHOOL_TYPE"), "")); //学校種別

		/* **********************************
		 * お知らせ */
		// 小・中・高・特別・そのた（学種が小・中・高・特別の以外）
		String schoolType = StringUtil.getStr(MapUtils.getString(paramMap, "SCHOOL_TYPE"), "");

		// フィアルパース
//		model.put("NEWS_PATH", property.getProperty("newsdir"));
		// 学種別お知らせ掲載期間を取得する："K008"
		String code = "";

		// 学種別お知らせ掲載期間を取得
		if (Constants.SCHOOL_TYPE_ELEMENT.equals(schoolType)) {
			code = schoolType;
		} else if (Constants.SCHOOL_TYPE_MIDDLE.equals(schoolType)) {
			code = schoolType;
		} else if (Constants.SCHOOL_TYPE_HIGH.equals(schoolType)) {
			code = schoolType;
		} else if (Constants.SCHOOL_TYPE_SPECIAL.equals(schoolType)) {
			code = schoolType;
		} else {
			code = "05";
		}

		// コードID、学種を設定
		paramMap.put("CODE_ID", "K008");//コードID
		paramMap.put("CODE", code);//コードに学種を設定

		Map<String, Object> newsMap = codeService.selectCodeByCodeIdAndCode(paramMap);

		// 現在日付
		String time = new SimpleDateFormat("yyyyMMdd HH:mm").format(new Date());
		Long sysTime = Long.valueOf(time.replaceAll(":", "").replaceAll(" ", ""));// ":",空白除去
		logger.debug("システム日付："+sysTime);

		if (newsMap != null && !newsMap.isEmpty()) {
			// 比較のため変換
			Long newsFrom = Long.valueOf(StringUtil.getStr(MapUtils.getString(newsMap, "ITEM2"), "").replaceAll("/", "").replaceAll(":", "").replaceAll(" ", ""));
			Long newsTo =  Long.valueOf(StringUtil.getStr(MapUtils.getString(newsMap, "ITEM3"), "").replaceAll("/", "").replaceAll(":", "").replaceAll(" ", ""));

			logger.debug("お知らせ掲載期間　FROM："+newsFrom);
			logger.debug("お知らせ掲載期間　TO："+newsTo);

			// お知らせの掲載期間であれば、お知らせ学校種別へ学校種別を設定
			if (sysTime >= newsFrom && sysTime <= newsTo) {
				model.put("NEWS_TYPE", code);// 該当学種のお知らせ表示
			} else {
				model.put("NEWS_TYPE", "");// 非表示
			}

		}

		/* *************************************
		 * 掲示板
		 */
		// 新しい教材
		model.put("NEW_LIBRARY_LIST", libraryService.selectNewLibraryList(paramMap));

		// よく利用されている教材
		model.put("DOWNLOAD_LIBRARY_LIST", libraryService.selectDownloadLibraryList(paramMap));

		// 注目の教材5件取得
		String sysOpenYearDate = String.valueOf(property.getProperty("pickup.baseDate")).replaceAll("null", "20160401");// システム開始1年後
		session.setAttribute("SYSOPEN_YEAR_DATE", sysOpenYearDate);
		model.put("PICKUP_LIBRARY_LIST", libraryService.selectPickupLibraryList(paramMap, sysOpenYearDate));

		// あなたが今まで見た教材5件取得
		model.put("HISTORY_LIBRARY_LIST", libraryService.selectHistoryLibraryList(paramMap));

		return "/main/libraryMain";
	}

	@RequestMapping("/main/main.do")
	public void main() {
	}

	@RequestMapping("/main/search.do")
	public String search(@RequestParam Map<String, Object> paramMap, ModelMap model) {

		return "/read/libraryList";

	}

	@RequestMapping("/internalError.do")
	public String internalError(@RequestParam(value = "code", required = false) String code, ModelMap model)
			throws Throwable {
		model.put("code", code);

		return "redirect:/internalError.jsp";
	}

	@RequestMapping("/loginError.do")
	public void loginError() {
	}

	@RequestMapping("/nullPointError.do")
	public void nullPointError() {
	}
}