package jp.go.saga.service.klb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import jp.go.saga.common.Constants;
import jp.go.saga.common.message.MessageProperty;
import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.io.FileIO;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bsmedia.app.common.RESecurityPattern;
import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.DateUtils;
import bsmedia.system.util.ObjectUtils;
import bsmedia.system.util.StringUtil;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class RegisterService {

	@Autowired
	CommonDao dao;
	@Autowired
	FileIO fileIO;
	@Autowired
	MessageProperty message;


	/*
	 * 教材を新規登録する
	 */
	public void registerSingleLibrary(Map<String,Object> paramMap, MultipartFile coverImageFile, List<MultipartFile> libraryFile, String[] linkArray) throws Throwable {

		//教材IDを新しく発行する（例：KLB0000012345）
		String libraryID = dao.queryForString("register.createLibraryID");
		paramMap.put("LIBRARY_ID", libraryID);

		//添付ファイルを教材格納フォルダにアップロードする
		uploadAttachFile(paramMap, coverImageFile, libraryFile, null);

		//教材基本情報を登録する
		dao.insert("register.insertLibraryInfo", paramMap);

		//教材のカテゴリ、タグ、リンク情報を登録する
		insertCategoryTagLink(paramMap, linkArray);

		//教材添付ファイル情報を登録する
		if (!StringUtil.isEmpty(paramMap.get("libraryFileInfoList"))) {
			List<Map<String,Object>> uploadFileInfoList = (List<Map<String,Object>>)paramMap.get("libraryFileInfoList");
			for (int sno=0; sno<uploadFileInfoList.size(); sno++) {
				paramMap.put("SNO",				sno+1);
				paramMap.put("FILE_NAME_BEF",	uploadFileInfoList.get(sno).get(FileIO.ORIGINAL_FILE_NAME));
				paramMap.put("FILE_NAME_AFT",	uploadFileInfoList.get(sno).get(FileIO.SAVED_FILE_NAME));
				paramMap.put("FILE_SIZE",		uploadFileInfoList.get(sno).get(FileIO.SAVED_FILE_SIZE));
				dao.insert("register.insertLibraryFile", paramMap);
			}
			paramMap.remove("SNO");
			paramMap.remove("FILE_NAME_BEF");
			paramMap.remove("FILE_NAME_AFT");
			paramMap.remove("FILE_SIZE");
		}

		//教材閲覧回数情報を登録する
		dao.insert("register.insertLibraryRead", paramMap);
	}


	/*
	 * 画面入力のタグをコンマで分けDB登録する
	 */
	private void insertCategoryTagLink(Map<String,Object> paramMap, String[] linkArray) throws Throwable {

		//教材カテゴリ情報を登録する
		String[] categoryArray = paramMap.get("CATEGORY_ID_JOIN").toString().split(",");
		for (String categoryID : categoryArray) {
			if (!StringUtil.isEmpty(categoryID)) {
				paramMap.put("CATEGORY_ID", categoryID);
				dao.insert("register.insertLibraryCategory", paramMap);
			}
		}
		paramMap.remove("CATEGORY_ID");

		//教材タグ情報を登録する
		if (!StringUtil.isEmpty(paramMap.get("TAG_JOIN"))) {
			int sno=0;
			String[] tagArray = paramMap.get("TAG_JOIN").toString().replaceAll("、",",").replaceAll("，",",").replaceAll("､",",").split(",");
			for (String tag : tagArray) {
				if (!StringUtil.isEmpty(tag)) {
					sno++;
					paramMap.put("SNO", sno);
					paramMap.put("TAG", tag);
					dao.insert("register.insertLibraryTag", paramMap);
				}
			}
			paramMap.remove("SNO");
			paramMap.remove("TAG");
		}

		//ウェブリンク先情報を登録する
		if (linkArray != null) {
			int sno = 1;
			for (int i=0; i<linkArray.length; i++) {
				if (!StringUtil.isEmpty(linkArray[i])) {
					paramMap.put("SNO",				sno);
					paramMap.put("LINK_ADDRESS",	linkArray[i]);
					dao.insert("register.insertLibraryLink", paramMap);
					sno++;
				}
			}
			paramMap.remove("SNO");
			paramMap.remove("LINK_ADDRESS");
		}
	}


	/*
	 * 修正対象教材情報を取得する
	 */
	public Map<String,Object> selectLibraryInfoForUpdate(Map<String,Object> paramMap) throws Throwable {

		Map model = new HashMap();

		//教材基本情報を取得する
		model.put("libraryInfo", dao.queryForMap("register.selectLibraryInfoForUpdate", paramMap));

		//添付ファイル情報を取得する
		List<?> fileList = dao.queryForList("register.selectLibraryFileForUpdate",paramMap);
		if (!fileList.isEmpty()) {
			model.put("MAX_FILE_SNO", ((Map)fileList.get(fileList.size()-1)).get("SNO"));
		} else {
			model.put("MAX_FILE_SNO", "0");
		}
		model.put("UPLOADED_FILE_NUM", fileList.size());
		model.put("fileList", fileList);

		//リンク先情報を取得する
		model.put("linkList", dao.queryForList("register.selectLibraryLinkForUpdate",paramMap));

		return model;
	}


	/*
	 * 教材情報を更新する
	 */
	public void updateLibraryInfo(Map<String,Object> paramMap, String[] delFileNameArray, String[] linkArray) throws Throwable {

		//教材情報の履歴を生成する
		createLibraryHistory(paramMap);

		//上書きする情報は削除して置く
		dao.delete("register.deleteLibraryCategory",	paramMap);		//教材カテゴリ情報
		dao.delete("register.deleteLibraryTag",			paramMap);		//教材タグ情報
		dao.delete("register.deleteLibraryLink",		paramMap);		//教材リンク情報

		//教材基本情報を更新する
		dao.update("register.updateLibraryInfo", paramMap);

		//教材のカテゴリ、タグ、リンク情報を登録する
		insertCategoryTagLink(paramMap, linkArray);

		//画面から削除されたファイルのみDBから削除する
		if (delFileNameArray != null) {
			for (String delFileName : delFileNameArray) {
				if (!StringUtil.isEmpty(delFileName)) {
					paramMap.put("FILE_NAME_AFT", delFileName);
					dao.delete("register.deleteLibraryFile", paramMap);
				}
			}
		}

		//画面から追加添付したファイル情報を登録する
		List<Map<String,Object>> uploadFileInfoList = (List<Map<String,Object>>)paramMap.get("libraryFileInfoList");
		for (int sno=0; sno<uploadFileInfoList.size(); sno++) {
			paramMap.put("SNO",				Integer.parseInt(paramMap.get("MAX_FILE_SNO").toString()) + sno + 1);
			paramMap.put("FILE_NAME_BEF",	uploadFileInfoList.get(sno).get(FileIO.ORIGINAL_FILE_NAME));
			paramMap.put("FILE_NAME_AFT",	uploadFileInfoList.get(sno).get(FileIO.SAVED_FILE_NAME));
			paramMap.put("FILE_SIZE",		uploadFileInfoList.get(sno).get(FileIO.SAVED_FILE_SIZE));
			dao.insert("register.insertLibraryFile", paramMap);
		}
	}


	/*
	 * 登録された教材リストを取得する
	 */
	public List<?> selectRegisteredLibraryList(Map<String,Object> paramMap) throws Throwable {

		return dao.queryForPaginatedList("register.selectRegisteredLibraryList", paramMap);
	}


	/*
	 * 画面から選択した教材の添付ファイルを一括削除する
	 */
	public void deleteLibraryFile(Map<String,Object> paramMap) throws Throwable {

		List<String> backupFileList = new ArrayList<String>();

		//削除対象の添付ファイル情報リストを取得する
		List<Map<String,String>> fileInfoList =  dao.queryForList("register.selectFileListForDelete", paramMap);

		for (Map<String,String> fileInfo : fileInfoList) {
			backupFileList.add(fileInfo.get("FILE_SRC"));
		}

		paramMap.put("backupFileList", backupFileList);

		//教材ファイルを退避して置く
		backupLibraryFile(paramMap);
	}


	/*
	 * 画面選択の複数教材の教材情報を一括削除する（論理削除）
	 */
	public int deleteLibraryInfo(Map<String,Object> paramMap) throws Throwable {

		//画面選択した教材の件数分、教材情報履歴を生成する
		createLibraryHistory(paramMap);

		//教材情報を削除する
		return dao.update("register.deleteLibraryInfo", paramMap);
	}


	/*
	 * 一括登録教材情報の読込結果を取得する（画面表示用）
	 */
	public List<Map<String,Object>> selectMultiLibraryExcelInfo(Map<String,Object> paramMap) throws Throwable {

		return dao.queryForList("register.selectMultiLibraryExcelData", paramMap);
	}


	/*
	 * 一括登録教材情報の読込結果を取得する（教材登録用）
	 */
	public List<Map<String,Object>> selectMultiLibraryForRegist(Map<String,Object> paramMap) throws Throwable {

		return dao.queryForList("register.selectMultiLibraryForRegist", paramMap);
	}


	/*
	 * 教材情報履歴を生成する
	 */
	private void createLibraryHistory(Map<String,Object> paramMap) throws Throwable {

		//教材情報の履歴を生成する
		dao.insert("register.insertLibraryInfoHistory",		paramMap);		//教材基本情報
		dao.insert("register.insertLibraryCategoryHistory", paramMap);		//教材カテゴリ情報
		dao.insert("register.insertLibraryTagHistory",		paramMap);		//教材タグ情報
		dao.insert("register.insertLibraryFileHistory",		paramMap);		//教材ファイル情報
		dao.insert("register.insertLibraryLinkHistory",		paramMap);		//教材リンク情報
	}


	/*
	 * 添付ファイルをアップロード
	 */
	public void uploadAttachFile(Map<String,Object> paramMap, MultipartFile coverImageFile, List<MultipartFile> libraryFile, String[] delFileNameArray) throws Throwable {

		String filePath = "";
		int beforeFileSnoMax = 0;

		//教材IDを取得
		String libraryID = paramMap.get("LIBRARY_ID").toString();

		//教材登録の場合
		if (paramMap.get("REGIST_STATUS").equals(Constants.REGIST_STATUS_INSERT)) {
			//ファイル格納PATHの編集を行う
			filePath = ApplicationProperty.get("upload.path");
			if (filePath.lastIndexOf("/") != filePath.length()-1) {
				filePath = filePath + "/";
			}
			filePath = filePath+libraryID.substring(0,7)+"/"+libraryID.substring(7,9)+"/"+libraryID.substring(9,11)+"/";
			paramMap.put("LIBRARY_FILE_PATH", filePath);
		}
		//教材修正の場合
		else {
			List<String> backupFileList = new ArrayList<String>();
			paramMap.put("backupFileList", backupFileList);

			//前回教材情報からファイル登録番号最大値を取得する
			beforeFileSnoMax = Integer.parseInt(paramMap.get("MAX_FILE_SNO").toString());

			//前回教材情報からファイル格PATHを取得する
			filePath = paramMap.get("LIBRARY_FILE_PATH").toString();

			//削除された表紙イメージを退避対象とする
			if (paramMap.get("COVER_IMAGE_DEL_FLAG").equals(Constants.FLAG_ON)) {
				backupFileList.add(filePath + libraryID + "_thumb.jpg");
			}

			//画面から削除された教材ファイルを退避対象とする
			if (delFileNameArray != null) {
				for (String delFileName: delFileNameArray) {
					if (!StringUtil.isEmpty(delFileName)) {
						backupFileList.add(filePath + delFileName);
					}
				}
			}

			//退避対象ファイルの退避処理を行う
			backupLibraryFile(paramMap);
		}

		List fileInfoList = new ArrayList();
		List<String> uploadFileNameList = new ArrayList<String>();
		paramMap.put("libraryFileInfoList", fileInfoList);
		paramMap.put("uploadFileNameList", uploadFileNameList);

		//表紙イメージをアップロード
		if (coverImageFile != null && !coverImageFile.isEmpty()) {
			Map<String,Object> fileMap = null;
			try {
				fileMap = uploadCoverImageFile(coverImageFile, libraryID, filePath);
			} catch (IIOException e) {
				//支援できないイメージ形式（CMYK）である場合
				throw new Throwable(e.getMessage());
			}
			paramMap.put("IMAGE", 		fileMap.get(FileIO.ORIGINAL_FILE_NAME));
			paramMap.put("THUMBNAIL",	fileMap.get(FileIO.SAVED_FILE_NAME));
			uploadFileNameList.add(fileMap.get(FileIO.SAVED_FILE_NAME).toString());
		}

		//教材ファイルをアップロード
		int sno = 1;
		for (MultipartFile file: libraryFile) {
			if (file != null && !file.isEmpty()) {
				Map<String,Object> fileMap = fileIO.saveFile(file, libraryID+"_"+(sno+beforeFileSnoMax), filePath);
				fileInfoList.add(fileMap);
				uploadFileNameList.add(fileMap.get(FileIO.SAVED_FILE_NAME).toString());
				sno++;
			}
		}
	}


	/*
	 * 表紙イメージのサムネイル作成
	 */
	private Map<String,Object> uploadCoverImageFile(MultipartFile f, String libraryID, String filePath) throws Throwable {

		//サムネイルファイル名を設定
		String thumb_file_name = libraryID + "_thumb.jpg";

		//表紙イメージファイルをサーバにアップロード
		Map<String,Object> fileMap = fileIO.saveFile(f, libraryID+"_cover", filePath);

		//サムネイル作成
		File origin_file = new File(fileMap.get(FileIO.SAVED_FILE_PATH)+""+fileMap.get(FileIO.SAVED_FILE_NAME));
		File thumb_file = new File(fileMap.get(FileIO.SAVED_FILE_PATH)+thumb_file_name);
		try {
			int thumbnail_width = 180;
			int thumbnail_height = 200;
			BufferedImage buffer_original_image = ImageIO.read(origin_file);
			BufferedImage buffer_thumbnail_image = new BufferedImage(thumbnail_width, thumbnail_height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = buffer_thumbnail_image.createGraphics();
			graphic.drawImage(buffer_original_image, 0, 0, thumbnail_width, thumbnail_height, null);
			ImageIO.write(buffer_thumbnail_image, "jpg", thumb_file);

			fileMap.put(FileIO.SAVED_FILE_NAME, thumb_file_name);

		} catch (IIOException e) {
			e.printStackTrace();
			throw new IIOException("添付した表紙イメージは支援できない形式です。");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//表紙イメージファイルをサーバから削除
			if (origin_file != null && origin_file.exists()) {
				origin_file.delete();
			}
		}

		return fileMap;
	}


	/*
	 * 更新・削除対象の教材ファイルを退避して置く
	 */
	private void backupLibraryFile(Map<String,Object> paramMap) throws Throwable {

		List<String> backupFileList = (List<String>)paramMap.get("backupFileList");

		//削除対象ファイルを退避して置く
		if (!backupFileList.isEmpty()) {
			for (String backupFile : backupFileList) {
				File old_file = new File(backupFile);
				File new_file = new File(backupFile + ".backup");
				if (old_file.exists()) {
					old_file.renameTo(new_file);
				}
			}
		}
	}

	/*
	 * 退避したファイルのROLLBACKを行う
	 */
	public void rollbackLibraryFile(Map<String,Object> paramMap) throws Throwable {

		//今回新規アップロードしたファイルを物理削除する
		if (paramMap.get("uploadFileNameList") != null) {
			List<String> uploadFileNameList = (List<String>)paramMap.get("uploadFileNameList");
			if (!uploadFileNameList.isEmpty()) {
				for (String uploadFileName : uploadFileNameList) {
					File uploadFile = new File(paramMap.get("LIBRARY_FILE_PATH") + uploadFileName);
					if (uploadFile.exists()) {
						uploadFile.delete();
					}
				}
			}
		}

		//退避したファイルを元に戻す
		if (paramMap.get("backupFileList") != null) {
			List<String> backupFileList = (List<String>)paramMap.get("backupFileList");
			if (!backupFileList.isEmpty()) {
				for (String backupFile : backupFileList) {
					File old_file = new File(backupFile + ".backup");
					File new_file = new File(backupFile);
					if (old_file.exists()) {
						old_file.renameTo(new_file);
					}
				}
			}
		}
	}


	/*
	 * 退避ファイルの物理削除を行う
	 */
	public void deleteBackupLibraryFile(Map<String,Object> paramMap) throws Throwable {

		if (paramMap.get("backupFileList") != null) {
			List<String> bakcupFileList = (List<String>)paramMap.get("backupFileList");
			if (!bakcupFileList.isEmpty()) {
				for (String backupFile : bakcupFileList) {
					File file = new File(backupFile + ".backup");
					if (file.exists()) {
						file.delete();
					}
				}
			}
		}
	}


	/*
	 * 一括登録用の教材情報一覧EXCELファイルを読込する
	 */
	public void readMultiLibraryExcelFile(Map<String,Object> paramMap, MultipartFile excelFile, boolean isEmptyZipFile) throws Throwable {

		int focusLine = 0;
		int focusCell = 0;
		int insertCnt = 0;



		//一括登録番号を設定
		String multiRegistNum = DateUtils.getToday("yyyyMMddHHmmss");
		paramMap.put("REGIST_DATE", multiRegistNum);

		//ファイルアップロード先PATHを設定
		String multiFilePath = ApplicationProperty.get("upload.path.multi");
		if (multiFilePath.lastIndexOf("/") != multiFilePath.length()-1) {
			multiFilePath = multiFilePath + "/";
		}
		String filePath = multiFilePath + multiRegistNum + "/";
		paramMap.put("MULTI_FILE_PATH", filePath);

		//添付した教材情報EXCELファイルをサーバにアップロード
		Map<String,Object> excelFileMap = fileIO.saveFile(excelFile, multiRegistNum, filePath);

		if ("xlsx".equals(excelFileMap.get(FileIO.FILE_EXT))) {
			try {
				//アップロードしたEXCELファイルを取得
				File excel = fileIO.getFileByAttachFileResultMap(excelFileMap);

				//EXCELファイルのワークブック生成
				XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excel));

				//EXCEL入力情報取得
				XSSFSheet sheet = wb.getSheetAt(0); 						//最前シートを取得
//				int rows = sheet.getPhysicalNumberOfRows(); 				//入力row数を取得
//				int cells = sheet.getRow(2).getPhysicalNumberOfCells();		//3行目のサンプル入力欄タイトルのcell数を取得

//				//サンプル欄のタイトル数が37個かチェック
//				if (cells != 37) {
//					throw new Exception(message.getMessage("X000"));
//				}
//
//				//最大登録可能教材数（100個）をチェック
//				if (rows > 110) {
//					throw new Exception(message.getMessage("X002")+":"+rows);
//				}

				//教材添付可能数を取得
				int fileNoMax = Integer.parseInt(ApplicationProperty.get("attach.file.max"));

				//EXCELの11行から110行まで（100個）の入力値を読込
				for (int r=10; r<=109; r++) {
					focusLine = r + 1;
					XSSFRow row = sheet.getRow(r);

					if (row != null) {
						//2番目セルのカテゴリ①が未入力の場合、該当行は登録しない
						if (row.getCell(1)==null || row.getCell(1).toString().equals("")) {
							continue;
						}

						int file_sno = 0;
						int link_sno = 0;
						String file_error_flg = "";

						//1~37番の各cellの入力値を取得する
						for (int c=0; c<=(16+(fileNoMax*2)); c++) {

							//読込対象外のセルを飛ばす
							if (c == 7 || c == 14) {
								continue;
							}

							focusCell = c + 1;
							String value = "";

							//該当cellの入力値を取得
							XSSFCell cell = row.getCell(c);

							if (cell != null) {
								 value = cell.toString();

								//script入力有無チェック
								if (value.toLowerCase().indexOf("script") > -1) {
									throw new Exception((r+1)+"行目にHTMLタグ等のスクリプト形式データが入力されています。");
								}
							}

							if (c == 0)		paramMap.put("ROW_NO",			r - 9);
							if (c == 1)		paramMap.put("CATEGORY_ID_1",	StringUtil.cutStr(value,3,true));
							if (c == 2)		paramMap.put("CATEGORY_ID_2",	StringUtil.cutStr(value,3,true));
							if (c == 3)		paramMap.put("CATEGORY_ID_3",	StringUtil.cutStr(value,3,true));
							if (c == 4)		paramMap.put("CATEGORY_ID_4",	StringUtil.cutStr(value,3,true));
							if (c == 5)		paramMap.put("CATEGORY_ID_5",	StringUtil.cutStr(value,3,true));
							if (c == 6)		paramMap.put("GRADE_FROM",		StringUtil.cutStr(value,2,true));
							if (c == 8)		paramMap.put("GRADE_TO",		StringUtil.cutStr(value,2,true));
							if (c == 9)		paramMap.put("TAG_JOIN",		StringUtil.concatCutLetter( StringUtil.removeBlank(value), 99));
							if (c == 10)	paramMap.put("TITLE",			StringUtil.concatCutLetter( StringUtil.trimBlank(value), 99));
							if (c == 11)	paramMap.put("CONTENT_INTRO",	StringUtil.concatCutLetter( value, 999));
							if (c == 12)	paramMap.put("CLOSE_FLG",		StringUtil.cutStr(value,1,true));
							if (c == 13)	paramMap.put("OPEN_TERM_FROM",	StringUtil.removeBlank(value));
							if (c == 15)	paramMap.put("OPEN_TERM_TO",	StringUtil.removeBlank(value));
							if (c == 16) {
								String fileName = StringUtil.trimBlank(value);
								paramMap.put("IMAGE", fileName);
								if (!StringUtil.isEmpty(fileName)) {
									//イメージファイル拡張子チェック
									if (!RESecurityPattern.validateImgFileUploadExtPattern(new String[]{fileName})) {
										file_error_flg = "X007";
									} else {
										if(fileName.length() > 100 ){
											fileName = StringUtil.concatCutLetter( fileName, 99);
											file_error_flg = "X011";
										}
									}
								}
							}
							if (16 < c && c <= (16 + fileNoMax)) {
								String fileName = StringUtil.trimBlank(value);
								if (!StringUtil.isEmpty(fileName)) {

									//教材ファイルが添付されてない場合
									if(isEmptyZipFile){
										file_error_flg = "X010";
									} else {
										file_sno++;
										paramMap.put("SNO", c - 16);
										//ファイル名100桁超えチェック
										if(fileName.length() > 100 ){
											fileName = StringUtil.concatCutLetter( fileName, 99);
											file_error_flg = "X011";
										}
										paramMap.put("FILE_NAME", fileName);
										dao.insert("register.insertMultiLibraryExcelFile", paramMap);
										//ファイル拡張子チェック
										if (RESecurityPattern.validateNotFileUploadExtPattern(new String[]{fileName})) {
											file_error_flg = "X008";
										}
									}
								}
							}
							if ((16 + fileNoMax) < c && c <= (16 + fileNoMax + fileNoMax)) {
								String link = StringUtil.removeBlank(value);
								if (!StringUtil.isEmpty(link)) {
									link_sno++;
									paramMap.put("SNO", c - 16 - fileNoMax);

									// リンクに全角が混在してないかチェック
									if(StringUtil.isIncludeZenStr(link)){
										file_error_flg = "X013";
									}

									//リンク300桁超えチェック
									if(link.length() > 300 ){
										link = StringUtil.concatCutLetter( link, 297);
										file_error_flg = "X012";
									}

									paramMap.put("LINK_ADDRESS", link);
									dao.insert("register.insertMultiLibraryExcelLink", paramMap);
								}
							}
						}

						if ("".equals(file_error_flg) && ( file_sno + link_sno) == 0) {
							file_error_flg = "X003";
						}

						paramMap.put("FILE_CNT", file_sno);
						paramMap.put("LINK_CNT", link_sno);
						paramMap.put("FILE_ERROR", file_error_flg);

						//一括登録用仮テーブルに登録する
						dao.insert("register.insertMultiLibraryExcel", paramMap);

						insertCnt++;
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception(focusLine+"行の"+focusCell+"番セルの読込途中にエラーが発生しました。");
			}
		}
		else {
			throw new Exception("教材情報一覧ファイルにはxlsx形式のファイルを添付して下さい。");
		}

		if (insertCnt == 0) {
			throw new Exception("EXCELファイルに教材情報が入力されていません。");
		}
	}


	/*
	 * 教材情報一覧EXCELファイルの記載内容が正しいか確認する
	 */
	public boolean checkMultiLibraryExcelData(Map<String,Object> paramMap) {

		//読込行の中、入力エラーが存在するか確認する
		String ngFlg = dao.queryForString("register.checkMultiLibraryExcelData", paramMap);

		//エラー行が存在しない場合は、正常（true）を返す
		return StringUtil.isEmpty(ngFlg)? true:false;
	}


	/*
	 * 一括登録用の教材ZIPファイルをアップロードし解凍する
	 */
	public boolean uploadMultiLibraryZipFile(Map<String,Object> paramMap, MultipartFile attachFile) throws Throwable {

		//一括登録用ファイルのアップロードPATHを取得
		String filePath = paramMap.get("MULTI_FILE_PATH").toString();

		//添付した教材ZIPファイルをサーバにアップロード
		Map<String,Object> zipFileMap = fileIO.saveFile(attachFile, paramMap.get("REGIST_DATE").toString(), filePath);

		//教材の圧縮ファイルを解凍（文字コードを指定し解凍後ファイル名の文字化けを防ぐ）
		fileIO.unZip(zipFileMap, "MS932");

		//EXCELファイルに記載した教材ファイル名リストを取得
		List<Map<String,Object>> fileNameList = dao.queryForList("register.selectMultiLibraryFileNameList", paramMap);

		//解凍ファイルにEXCEL記載のファイルが存在するか確認する
		long fileSize = 0;
		boolean isFileExist = true;
		int next_row_no = 0;
		String notExistFileNo = "";
		int listSize = fileNameList.size();

		for (int i=0; i<listSize; i++) {

			Map<String,Object> fileNameMap = fileNameList.get(i);

			File f = new File(filePath + fileNameMap.get("FILE_NAME"));

			if (f.exists()) {
				fileSize = fileSize + f.length();
			} else {
				isFileExist = false;
				notExistFileNo = notExistFileNo + (notExistFileNo.equals("")?"":",") + fileNameMap.get("SNO");
			}

			int curr_row_no = Integer.parseInt(fileNameMap.get("ROW_NO").toString());
			if (i < listSize - 1) {
				next_row_no = Integer.parseInt(fileNameList.get(i+1).get("ROW_NO").toString());
			}

			if (curr_row_no != next_row_no || i == (listSize - 1)) {
				if (!StringUtil.isEmpty(notExistFileNo) || fileSize > 0) {
					paramMap.put("ROW_NO",            curr_row_no);
					paramMap.put("FILE_SIZE",         fileSize);
					paramMap.put("NOT_EXIST_FILE_NO", notExistFileNo);
					dao.update("register.updateMultiLibraryFileExistInfo", paramMap);
				}
				notExistFileNo = "";
				fileSize = 0;
			}
		}

		return isFileExist;
	}


	/*
	 * 括登録用の仮データを使い、教材新規登録を行う
	 */
	public boolean registerMultiLibrary(Map<String,Object> paramMap, Map<String,Object> libraryInfo) throws Throwable {

		//入力パラメータのユーザ情報を複写する
		ObjectUtils.copyMap(paramMap, libraryInfo);

		//教材データ格納ファオルを取得
		String multiFilePath = libraryInfo.get("FILE_PATH").toString();

		//表紙イメージファイルを取得
		MultipartFile coverImageFile = fileIO.createMultipartFile(multiFilePath, libraryInfo.get("IMAGE"));

		//教材ファイル
		List<Map<String,Object>> fileList = dao.queryForList("register.selectMultiLibraryFileForRegist", libraryInfo);
		List<MultipartFile> libraryFile = new ArrayList<MultipartFile>();
		for (int i=0; i<fileList.size(); i++) {
			libraryFile.add(fileIO.createMultipartFile(multiFilePath, fileList.get(i).get("FILE_NAME")));
		}

		//教材リンク
		List<Map<String,Object>> linkList = dao.queryForList("register.selectMultiLibraryLinkForRegist", libraryInfo);
		String[] linkArray = new String[linkList.size()];
		for (int i=0; i<linkList.size(); i++) {
			linkArray[i] = linkList.get(i).get("LINK_ADDRESS").toString();
		}

		try {
			//教材新規登録を行う
			registerSingleLibrary(libraryInfo, coverImageFile, libraryFile, linkArray);
		}
		catch (Exception e) {
			//ファイルのROLLBACKを行う
			rollbackLibraryFile(libraryInfo);

			e.printStackTrace();
			return false;
		}

		//登録済の教材IDを保存する
		paramMap.put("LIBRARY_ID", libraryInfo.get("LIBRARY_ID"));
		dao.update("register.updateMultiLibraryInsertResult", paramMap);
		paramMap.remove("LIBRARY_ID");

		return true;
	}


	/*
	 * 該当ユーザが読込した一括登録用仮データを削除する
	 */
	public void deleteCurrMultiLibraryData(Map<String,Object> paramMap) throws Throwable {

		//削除対象の一括登録番号リストを取得する
		List<Map<String,Object>> delList = dao.queryForList("register.selectMultiLibraryDataForDelete", paramMap);

		//一括登録用の仮データを削除する
		dao.delete("register.deleteMultiLibraryData", paramMap);
		dao.delete("register.deleteMultiLibraryDataFile", paramMap);
		dao.delete("register.deleteMultiLibraryDataLink", paramMap);

		//処理ファイルを削除する
		for (Map<String,Object> delMap : delList) {
			File dir = new File(ApplicationProperty.get("upload.path.multi")+delMap.get("REGIST_DATE"));
			if (dir.exists() && dir.isDirectory()) {
				fileIO.deleteFolder(dir);
			}
		}
	}


	/*
	 * 検索条件入力パラメータを出力パラメータにcopyする
	 */
	public void copySearchParam(Map<String,Object> fromMap, Map<String,Object> toMap) {
		Iterator entries = fromMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry)entries.next();
			String keyName = (String)entry.getKey();
			if (keyName.equals("page") || keyName.equals("scrollTop")) {
				toMap.put(keyName, entry.getValue());
			}
			else if (keyName.startsWith("SEARCH_")) {
				toMap.put(keyName, entry.getValue());
			}
		}
	}

}
