package jp.go.saga.service.klb.batch;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.io.FileIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.DateUtils;

@Service("batchService")
public class BatchService {

	@Autowired
	CommonDao dao;
	@Autowired
	FileIO fileIO;


	/*
	 * 前日のログイン統計情報を生成する
	 */
	public void insertAnalyzeLogin() {

		// 3/31 lim 複数のサーバ上でバッチが起動される場合を顧慮し、
		// ログイン日付をキーとして検索し
		// データが存在する場合は処理を行わない。
		int count = 0;

		count = dao.queryForInteger("batch.selectCountAnalyzeLogin") ;

		if(count == 0){
			dao.insert("batch.insertAnalyzeLogin");
		}
	}

	/*
	 * 過去のログイン履歴情報を削除する
	 */
	public void deleteLoginHistory() {

		//ログイン履歴保持日を取得
		String keep_day = ApplicationProperty.get("login.history.timeover");

		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("KEEP_DAY", keep_day);

		dao.delete("batch.deleteLoginHistory", paramMap);
	}

	/*
	 * 過去（一週間経過）の教材仮データを削除する
	 */
	public void deleteLibraryTempFile() {

		//一週間前の日付を取得
		String prevWEEK = DateUtils.addWeek(DateUtils.getToday("yyyyMMdd"),-1,false);

		//過去（一週間経過）の一括登録用の仮データを削除する
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("PREV_WEEK", prevWEEK);
		dao.delete("register.deleteMultiLibraryData", paramMap);
		dao.delete("register.deleteMultiLibraryDataFile", paramMap);
		dao.delete("register.deleteMultiLibraryDataLink", paramMap);

		//過去（一週間経過）の一括登録用仮ファイルを削除する
		File rootDirMulti = new File(ApplicationProperty.get("upload.path.multi"));
		if (rootDirMulti.exists()) {
			File[] childDir = rootDirMulti.listFiles();
			for (File dir : childDir) {
				if (dir.isDirectory()) {
					String dirName = dir.getName();
					if (dirName.length() == 14 && dirName.compareTo(prevWEEK+"000000") < 0) {
						fileIO.deleteFolder(dir);
					}
				}
			}
		}

		//過去（一週間経過）の統計情報出力用ファイルを削除する
		File rootDirAnalyze = new File(ApplicationProperty.get("excel.print.location"));
		if (rootDirAnalyze.exists()) {
			File[] childDir = rootDirAnalyze.listFiles();
			for (File dir : childDir) {
				if (dir.isDirectory()) {
					String dirName = dir.getName();
					if (dirName.length() == 14 && dirName.compareTo(prevWEEK+"000000") < 0) {
						fileIO.deleteFolder(dir);
					}
				}
			}
		}
	}

	/*
	 * 過去の排他情報を削除する
	 */
	public void deleteExclusiveControlInfo() {

		//排他情報保持時間を取得
		String keep_hour = ApplicationProperty.get("exclusive.control.timeover");

		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("KEEP_HOUR", keep_hour);

		//保持時間が過ぎた排他情報を削除する
		dao.delete("batch.deleteExclusiveControlInfoTimeOver", paramMap);
	}

}

