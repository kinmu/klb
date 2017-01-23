package jp.go.saga.service.klb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.io.FileIO;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.DateUtils;
import bsmedia.system.util.ObjectUtils;

@Service
@SuppressWarnings({"unchecked"})
public class AnalyzeService {

	@Autowired
	CommonDao dao;
	@Autowired
	FileIO fileIO;

	/*
	 * 統計情報を抽出し、EXCELファイルを生成する
	 */
	public void createAnalyzeData(Map<String,Object> paramMap, String[] analyzeTypeArray) throws Throwable {

		//今日の日時を取得
		String today = DateUtils.getToday("yyyyMMddHHmmss");

		//統計ファイル格納するフォルダ名を取得
		String printLocation = ApplicationProperty.get("excel.print.location");
		if (printLocation.lastIndexOf("/") != printLocation.length()-1) {
			printLocation =  printLocation + "/";
		}
		printLocation = printLocation + today + "/";

		//統計ファイル格納フォルダ生成
		File fileSaveDir = new File(printLocation);
		fileSaveDir.mkdirs();

		paramMap.put("ANALYZE_FILE_SAVE_DIR", printLocation);
		paramMap.put("ANALYZE_ZIP_FILE_NAME", today + ".zip");

		String classYear = String.valueOf(paramMap.get("ANALYZE_DATE_TO")).substring(0, 4);

		if(4 > Integer.parseInt(String.valueOf(paramMap.get("ANALYZE_DATE_TO")).substring(4, 6))){
			classYear = String.valueOf(Integer.parseInt(classYear)-1);
		}
		paramMap.put("ANALYZE_CLASS_YEAR", classYear);

		//画面から選択した統計情報種別の名称などの基本情報を取得する
		List<String> analyzeTypeList = Arrays.asList(analyzeTypeArray);
		paramMap.put("ANALYZE_TYPE_LIST", analyzeTypeList);
		List<Map<String,Object>> analyzeTypeInfoList = dao.queryForList("analyze.selectBasicInfo", paramMap);

		//画面選択した統計情報種別の件数分、データ抽出・EXCELファイル生成の処理を繰り返す
		for (Map<String,Object> analyzeTypeInfo : analyzeTypeInfoList) {

			//教材基本情報をEXCELファイル生成用パラメータにセットする
			Map<String,Object> resultMap = new HashMap<String,Object>();
			ObjectUtils.copyMap(analyzeTypeInfo, resultMap);

			//テンプレートファイル名
			String templateFileName = ApplicationProperty.get("excel.templete.location")
										+ "/" + analyzeTypeInfo.get("TEMPLATE_FILE_NAME");

			//出力ファイル名
			String printFileName =    printLocation
									+ analyzeTypeInfo.get("ANALYZE_TYPE_NAME") + "_"
									+ paramMap.get("ANALYZE_DATE_FROM") + "～"
									+ paramMap.get("ANALYZE_DATE_TO")   + ".xlsx";

			//SQL ID
			String sqlID = analyzeTypeInfo.get("SQL_ID").toString();

			//統計データを抽出し、EXCELファイル生成用パラメータにセットする
			paramMap.put("ANALYZE_TYPE_CODE", analyzeTypeInfo.get("ANALYZE_TYPE"));
			List<Map<String,Object>> resultList = dao.queryForList(sqlID, paramMap);
			resultMap.put("resultList", resultList);

			//統計データでEXCELファイルを生成する
			FileOutputStream fos = null;
			try {
				InputStream templateIS = Thread.currentThread().getContextClassLoader().getResourceAsStream(templateFileName);
				XLSTransformer transformer = new XLSTransformer();
				XSSFWorkbook resultWorkbook = (XSSFWorkbook) transformer.transformXLS(templateIS, resultMap);
				fos = new FileOutputStream(new File(printFileName));
				resultWorkbook.write(fos);
			}
			catch (Exception e) {
				e.printStackTrace();
				fileIO.deleteFolder(fileSaveDir);
				throw new Throwable(analyzeTypeInfo.get("ANALYZE_TYPE_NAME")+"のEXCELファイル生成途中エラーになりました。");
			}
			finally {
				fos.close();
			}
		}
	}


	/*
	 * 生成したEXCELファイルをZIPに圧縮する
	 */
	public File createAnalyzeZipFile(Map<String,Object> paramMap) throws Throwable {

		//EXCELファイル生成フォルダを取得
		String targetDir = paramMap.get("ANALYZE_FILE_SAVE_DIR").toString();

		//ZIPファイル名を生成
		String zipFileName = paramMap.get("ANALYZE_ZIP_FILE_NAME").toString();

		return fileIO.createZipFile(targetDir, zipFileName, "MS932");
	}


	/*
	 * 今回生成した統計情報ファイルを削除する
	 */
	public void deleteCurrAnalyzeFile(Map<String,Object> paramMap) {

		File dir = new File(paramMap.get("ANALYZE_FILE_SAVE_DIR").toString());
		fileIO.deleteFolder(dir);
	}

}