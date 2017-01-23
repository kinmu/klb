package jp.go.saga.service.klb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bsmedia.system.util.StringUtil;

@Service
public class LibraryService {

	private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

	/* TODO test */
	private static final Long PICKUP_BASE_DATE = 201604010000L;

	private static final String DATE_FORMAT_YYYYMMDD_HHMI = "yyyyMMdd hh:mm";

	private static final String DATE_FORMAT_YYYYMMDD_SLASH = "yyyy/MM/dd";

	@Autowired
	CommonDao dao;

	/* 登録教材番号（library_id）をキーに検索*/
	@SuppressWarnings("rawtypes")
	public Map selectLibraryByLibraryId (Map<String, Object> paramMap) throws Throwable {
		return dao.queryForMap("library.selectLibraryByLibraryId", paramMap);
	}

	/* 新しい教材(及び　注目の教材)取得 */
	public List<?> selectNewLibraryList(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForPaginatedList("library.selectNewLibraryList", paramMap);
	}

	/* よく利用されている教材取得 */
	public List<?> selectDownloadLibraryList(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForPaginatedList("library.selectDownloadLibraryList", paramMap);
	}

	/* 注目の教材取得*/
	public List<?> selectPickupLibraryList(Map<String, Object> paramMap, String pickupBaseDate) throws Throwable {

		// システム日付
		String time = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMI).format(new Date());
		Long now = Long.valueOf( time.replaceAll(":", "").replaceAll(" ", ""));// ":",空白除去
		Long pickupDate = Long.valueOf(pickupBaseDate);

		if(pickupDate == null){
			pickupDate = PICKUP_BASE_DATE;
		}
		// 運用経過1年「2016/04/01 00:00」までの場合、ダウンロードの多い直近の2か月間で取得

		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMI);

		if(now >= pickupDate){
			// ピックアップ対象の公開開始日が1年前の前後２か月
			paramMap.put("PICKUP_TERM", "BEFORE_YEAR");

			// 「システム日付から-13か月」取得
			cal.setTime(df.parse(time));
			cal.add(Calendar.MONTH, -13);
			paramMap.put("PICKUP_START_TIME", new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_SLASH).format(cal.getTime()));
			logger.debug("PICKUP_START_TIME"+ StringUtil.getStr(MapUtils.getString(paramMap, "PICKUP_START_TIME"), ""));

			// 「システム日付から-11か月」取得
			cal.setTime(df.parse(time));
			cal.add(Calendar.MONTH, -11);
			paramMap.put("PICKUP_END_TIME", new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_SLASH).format(cal.getTime()));
			logger.debug("PICKUP_END_TIME"+ StringUtil.getStr(MapUtils.getString(paramMap, "PICKUP_END_TIME"), ""));

		} else {
			// ピックアップ対象の公開開始日が直近２か月
			paramMap.put("PICKUP_TERM", "BEFORE_2MONTH");

			// 「システム日付から直近２か月まえ」取得
			cal.setTime(df.parse(time));
			cal.add(Calendar.MONTH, -2);
			paramMap.put("PICKUP_START_TIME", new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_SLASH).format(cal.getTime()));
			logger.debug("PICKUP_START_TIME"+ StringUtil.getStr(MapUtils.getString(paramMap, "PICKUP_START_TIME"), ""));

			// 「システム日付」取得
			cal.setTime(df.parse(time));
			paramMap.put("PICKUP_END_TIME", new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_SLASH).format(cal.getTime()));
			logger.debug("PICKUP_END_TIME"+ StringUtil.getStr(MapUtils.getString(paramMap, "PICKUP_END_TIME"), ""));
		}

		return dao.queryForPaginatedList("library.selectPickupLibraryList", paramMap);
	}

	/* あなたが今まで見た教材取得 */
	public List<?> selectHistoryLibraryList(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForPaginatedList("library.selectHistoryLibraryList", paramMap);
	}

	/* 教材などのカテゴリをキーに教材取得  */
	public List<?> selectLibraryListByCategoryId(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForPaginatedList("library.selectLibraryListByCategoryId", paramMap);
	}

	/* メイン画面キーワード検索  */
	public List<?> selectSearchKeywordAndCategory(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForPaginatedList("library.selectSearchKeywordAndCategory", paramMap);
	}

	/* 教材を探すの詳細検索  */
	public List<?> selectSearchByAllConditions(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForPaginatedList("library.selectSearchByAllConditions", paramMap);
	}

	/* 教材閲覧数のカウントアップ */
	public Integer updateLibraryReadCount(Map<String, Object> paramMap) throws Throwable {
		return dao.update("library.updateLibraryReadCount", paramMap);
	}

	/* 教材ダウンロード数のカウントアップ*/
	public Integer updateLibraryDownloadCount(Map<String, Object> paramMap) throws Throwable {
		return dao.update("library.updateLibraryDownloadCount", paramMap);
	}

	/* 教材閲覧履歴の検索*/
	@SuppressWarnings("rawtypes")
	public Map selectUsedHistoryByReadDateAndLoginId(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForMap("library.selectUsedHistoryByReadDateAndLoginId", paramMap);
	}

	/* 教材添付ファイルをファイル名で検索*/
	@SuppressWarnings("rawtypes")
	public Map selectFileByLibraryIdAndSno(Map<String, Object> paramMap) throws Throwable {
		return dao.queryForMap("library.selectFileByLibraryIdAndSno", paramMap);
	}

	/* 教材閲覧履歴の登録*/
	public void insertUsedHistory(Map<String, Object> paramMap) throws Throwable {
		//ユーザ種別が「H：ヘルプデスク」の場合は登録しない
		if (!paramMap.get("USER_TYPE").equals("H")) {
			dao.insert("library.insertUsedHistory", paramMap);
		}
	}

	/* 教材閲覧履歴のダウンロード数カウントアップ*/
	public Integer updateUsedHistory(Map<String, Object> paramMap) throws Throwable {
		return dao.update("library.updateUsedHistory", paramMap);
	}

}
