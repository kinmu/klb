package jp.go.saga.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.sasl.AuthenticationException;

import jp.go.saga.common.Constants;
import jp.go.saga.common.message.MessageProperty;
import net.sf.jazzlib.ZipEntry;
import net.sf.jazzlib.ZipInputStream;

import org.apache.commons.collections.MapUtils;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.DateUtils;
import bsmedia.system.util.FileUtils;
import bsmedia.system.util.StringUtil;

@Component
public class FileIO {

	@Autowired
	MessageProperty messageProperty;

    static final int COMPRESSION_LEVEL = 8;
    static final int BUFFER_SIZE = 64*1024;
    static final char FS = File.separatorChar;

	private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

	/**
	 * 元ファイル名
	 */
	public static final String ORIGINAL_FILE_NAME = "fileNameOri";
	/**
	 * ファイルの拡張子
	 */
	public static final String FILE_EXT = "fileNameExt";
	/**
	 * 保存されたファイル名
	 */
	public static final String SAVED_FILE_NAME = "fileNameToSave";
	/**
	 * 保存されたファイルのサイズ
	 */
	public static final String SAVED_FILE_SIZE = "fileSize";
	/**
	 * 保存されたファイルのPATH
	 */
	public static final String SAVED_FILE_PATH = "savedfilePath";

	/**
	 * MultipartFile形のファイルを保存する。
	 * @param file MultipartFile
	 * @return
	 * @throws Throwable
	 */
	public Map<String,Object> saveFile(MultipartFile file, String fileNameToSave, String filePathToSave) throws Throwable{

		String filePath = ApplicationProperty.get("upload.path");
		String fileNameOri =file.getOriginalFilename();
		String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
		String fileNameSave = "";
		if (StringUtil.isEmpty(fileNameToSave)) {
			fileNameSave = DateUtils.getToday("yyyyMMddHHmmss") + System.currentTimeMillis() +"."+ fileNameExt;
		} else {
			fileNameSave = fileNameToSave + "." + fileNameExt;
		}

		System.out.println(file.getContentType()+"#################################");
		if ("application/x-msdownload".equals(file.getContentType()) ) {
			throw new AuthenticationException(messageProperty.getMessage("F012"));
		}

		if (filePath.lastIndexOf("/") != filePath.length()-1) {
			filePath = filePath + "/";
		}

		if (StringUtil.isEmpty(filePathToSave)) {
			filePath = filePath + DateUtils.getToday("yyyy") + "/" + DateUtils.getToday("MM") + "/";
		} else {
			if (filePathToSave.lastIndexOf("/") != filePathToSave.length()-1) {
				filePathToSave = filePathToSave + "/";
			}
			filePath = filePathToSave;
		}

		File uFile = new File(filePath);
		if(!uFile.exists()) {
			uFile.mkdirs();
		}

		File uploadFile = new File(filePath, fileNameSave);

		file.transferTo(uploadFile);

		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(SAVED_FILE_PATH,		filePath);
		resultMap.put(ORIGINAL_FILE_NAME,	fileNameOri);
		resultMap.put(FILE_EXT,				fileNameExt);
		resultMap.put(SAVED_FILE_NAME,		fileNameSave);
		resultMap.put(SAVED_FILE_SIZE,		file.getSize());

		return resultMap;
	}

	/**
	 * MultipartFile形のファイルを保存する。
	 * @param file MultipartFile
	 * @return
	 * @throws Throwable
	 */
	public Map<String,Object> saveScormFile(File file) throws Throwable{
		String filePath = ApplicationProperty.get("upload.path");
		String fileNameOri =file.getName();
		String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
		String fileNameToSave = DateUtils.getToday("yyyyMMddHHmmss") + System.currentTimeMillis() +"."+ fileNameExt;


		if(filePath.lastIndexOf("/") == filePath.length()-1) {
			filePath = filePath + DateUtils.getToday("yyyy") + "/" + DateUtils.getToday("MM") + "/";
		} else {
			filePath = filePath + "/" + DateUtils.getToday("yyyy") + "/" + DateUtils.getToday("MM") + "/";
		}
		File uFile = new File(filePath);
		if(!!uFile.exists()) {
			uFile.mkdirs();
		}


		File uploadFile = new File(filePath, fileNameToSave);

		FileUtils.copy(file.getPath(), uploadFile.getPath() );

		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(Constants.FILE_PATH, filePath);
		resultMap.put(ORIGINAL_FILE_NAME, fileNameOri);
		resultMap.put(FILE_EXT, fileNameExt);
		resultMap.put(SAVED_FILE_NAME, fileNameToSave);
		resultMap.put(SAVED_FILE_SIZE, file.length());

		return resultMap;
	}

	/**
	 * DBにて取得したファイルパス/元ファイル名を基づいてファイルオブジェクトをリターン
	 * @param resultMap ファイルパス/元ファイル名が格納されているMapオブジェクト
	 * @return ファイル
	 */
	public File getFileByAttachFileResultMap(Map<String,Object> resultMap){

		String filePath = StringUtil.getStr((String)resultMap.get(SAVED_FILE_PATH),"");
		String fileName = StringUtil.getStr((String)resultMap.get(SAVED_FILE_NAME),"");

		return new File(filePath,fileName);
	}

	// Byte[]를 파일로 저장하기
	public Map<String,Object> saveFileForByte(Map<String,Object> paramMap, byte[] data) throws Throwable {
		String filePath = MapUtils.getString(paramMap, "uploadPath");
		String fileNameOri = MapUtils.getString(paramMap, "filename");
		String fileNameExt = fileNameOri.lastIndexOf(".") != -1 ? fileNameOri.substring(fileNameOri.lastIndexOf(".")+1).toLowerCase() : "";
		String fileNameToSave = DateUtils.getToday("yyyyMMddHHmmss") + System.currentTimeMillis() +"."+ fileNameExt;


		if(filePath.lastIndexOf("/") == filePath.length()-1) {
			filePath = filePath + DateUtils.getToday("yyyy") + "/" + DateUtils.getToday("MM") + "/";
		} else {
			filePath = filePath + "/" + DateUtils.getToday("yyyy") + "/" + DateUtils.getToday("MM") + "/";
		}
		File uFile = new File(filePath);
		if(!!uFile.exists()) {
			uFile.mkdirs();
		}


		FileOutputStream out = new FileOutputStream(filePath+fileNameToSave);
		out.write(data);
		out.close();

		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put(Constants.FILE_PATH, filePath);
		resultMap.put(ORIGINAL_FILE_NAME, fileNameOri);
		resultMap.put(FILE_EXT, fileNameExt);
		resultMap.put(SAVED_FILE_NAME, fileNameToSave);

		return resultMap;
	}

	/**
	 * zipファイルを解凍する
	 * @param zipFileMap（zipファイルアップロード情報）
	 * @param charset 文字コード（解凍後日本語文字化け防止用）
	 * @throws Throwable
	 */
	public void unZip(Map<String,Object> zipFileMap, String charset) throws Throwable {

		//zipファイルであるか確認する
		if ((zipFileMap.get(SAVED_FILE_NAME).toString().toUpperCase()).indexOf(".ZIP") == -1) {
			throw new Exception("解凍対象ファイルがZIP形式ではありません。");
        }

		byte[] buffer = new byte[BUFFER_SIZE];

		String outputDir = zipFileMap.get(SAVED_FILE_PATH).toString();
		File uploadZipFile = getFileByAttachFileResultMap(zipFileMap);

		ZipFile zip = null;

		try {
			//文字コードを指定することで文字化けを回避
			zip = new ZipFile(uploadZipFile, charset);

			Enumeration<?> zipEnum = zip.getEntries();

			while (zipEnum.hasMoreElements()) {

				//解凍するアイテムを取得
				org.apache.tools.zip.ZipEntry entry = (org.apache.tools.zip.ZipEntry)zipEnum.nextElement();

				//解凍対象がディレクトリの場合
				if (entry.isDirectory()) {
					File dir = new File(outputDir, entry.getName());
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				//解凍対象がファイルの場合
				else {
					String fileName = entry.getName();

					//zip内にフォルダ存在場合、フォルダを生成して置く
					if (fileName.lastIndexOf("/") != -1) {
						File dir = new File(outputDir, entry.getName().substring(0,fileName.lastIndexOf("/")));
						if (!dir.exists()) {
							dir.mkdirs();
						}
					}

					File file = new File(outputDir, fileName);

					//同一名称のファイルがすでに存在の場合
					if (file.exists()) {
						continue;
					}

					FileOutputStream fos = null;
					InputStream is = null;
					try {
						fos = new FileOutputStream(file);
						is = zip.getInputStream(entry);

						int size = 0;
						while ((size = is.read(buffer)) != -1) {
							fos.write(buffer, 0, size);
						}
						fos.flush();
					}
					catch (Exception e) {
						e.printStackTrace();
						throw new Exception("ZIPファイルの解凍途中にエラーが発生しました。");
					}
					finally {
						if (fos != null) {
							fos.close();
						}
						if (is != null) {
							is.close();
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("ZIPファイルの解凍途中にエラーが発生しました。");
		}
		finally {
			if (zip != null) {
				zip.close();
			}
		}
	}

	/**
	 * zip파일 압출풀기
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public List<Map<String, Object>> unZip(Map<String,Object> zipFileMap) throws IOException {

        String targetZip = zipFileMap.get("FILE_PATH")+""+zipFileMap.get("fileNameToSave");

        logger.debug("Current System File Seperator is : >> " + zipFileMap);

        String foutputPath = ApplicationProperty.get("upload.path")+zipFileMap.get("fileNameToSave")+".dir"+FS;
        File zipDir = new File(foutputPath);
        if (!zipDir.exists()) {
            zipDir.mkdirs();
        }
        long beginTime = System.currentTimeMillis();
        byte[] buffer = new byte[BUFFER_SIZE];

        File file = new File(targetZip);

        FileInputStream finput = new FileInputStream(file);
        ZipInputStream zinput = new ZipInputStream((InputStream)finput);
        ZipEntry entry;


		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
        //Zip 파일 타입이 아니라면 실행을 끝낸다
        if ((targetZip.toUpperCase()).indexOf(".ZIP") == -1) {
            return resultList;
        }

        try {
        	int unzipFileCnt = 0;
            while ((entry = zinput.getNextEntry()) != null) {

            	HashMap<String, Object> sMap = new HashMap<String, Object>();

                String outputFileNm = entry.getName().replace('/', File.separatorChar);
                logger.debug("2.outputFileNm : " + outputFileNm);

        		String fileNameExt = outputFileNm.lastIndexOf(".") != -1 ? outputFileNm.substring(outputFileNm.lastIndexOf(".")+1).toLowerCase() : "";
        		String fileNameToSave = DateUtils.getToday("yyyyMMddHHmmss") + System.currentTimeMillis() +unzipFileCnt+"."+ fileNameExt;

                File entryFile = new File(foutputPath + fileNameToSave);
                logger.debug("3.Entry File : " + entryFile.toString());

                FileOutputStream foutput = new FileOutputStream(entryFile);
                logger.debug("4.FileOutputStream : " + foutput.toString());

                logger.debug("Uncompress the Zip File........");

                int cnt;

                while ((cnt = zinput.read(buffer)) != -1) {
                    foutput.write(buffer, 0, cnt);
                }

                logger.debug("7. Final Output File : " + foutput.toString());

        		sMap.put("ORIGINALFILE_NAME", outputFileNm);
        		sMap.put("FILE_TYPE", "ZIP");
        		sMap.put("ATTACHFILE_NAME", fileNameToSave);
        		sMap.put("ATTACHFILE_PATH", foutputPath);
        		sMap.put("FILE_SIZE", entryFile.length());
        		resultList.add( sMap );

                foutput.flush();
                if (foutput != null) {
                    foutput.close();
                }
                unzipFileCnt++;

            }
        } catch (Exception e) {
            logger.debug("Zip.unZip().... Error");
            e.printStackTrace();
        }
        long msec = System.currentTimeMillis() - beginTime;
        logger.debug("Unzipe Time Check :: >> " + msec/1000 + "." + (msec % 1000) + " sec. elapsed...");

		return resultList;
	}

	/**
	 * zip파일 압출풀기
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public List<Map<String, Object>> unZip(String zipFile,String unZipPath) throws IOException {

        String targetZip = zipFile;

        String foutputPath = unZipPath;
        File zipDir = new File(foutputPath);
        if (!zipDir.exists()) {
            zipDir.mkdirs();
        }
        long beginTime = System.currentTimeMillis();
        byte[] buffer = new byte[BUFFER_SIZE];

        File file = new File(targetZip);

        FileInputStream finput = new FileInputStream(file);
        ZipInputStream zinput = new ZipInputStream((InputStream)finput);
        ZipEntry entry;


		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
        //Zip 파일 타입이 아니라면 실행을 끝낸다
        if ((targetZip.toUpperCase()).indexOf(".ZIP") == -1) {
            return resultList;
        }

        try {
        	int unzipFileCnt = 0;
            while ((entry = zinput.getNextEntry()) != null) {

            	logger.debug(">>>>>>>>>>>>>>>>>>>... Name : "+entry.getName());
        		if(entry.getName().lastIndexOf('/')>0){
        			String mkDirNm = entry.getName().substring(0,entry.getName().lastIndexOf('/'));
            		logger.debug(">>>>>>>>>>>> isDirectory Name : "+entry.getName());
					new File(foutputPath + ""+ mkDirNm).mkdirs();
				}

            	HashMap<String, Object> sMap = new HashMap<String, Object>();

                String outputFileNm = entry.getName().replace('/', File.separatorChar);
                logger.debug("2.outputFileNm : " + outputFileNm);

                File entryFile = new File(foutputPath + outputFileNm);
                logger.debug("3.Entry File : " + entryFile.toString());

                FileOutputStream foutput = new FileOutputStream(entryFile);
                logger.debug("4.FileOutputStream : " + foutput.toString());

                logger.debug("Uncompress the Zip File........");

                int cnt;

                while ((cnt = zinput.read(buffer)) != -1) {
                    foutput.write(buffer, 0, cnt);
                }

                logger.debug("7. Final Output File : " + foutput.toString());

        		sMap.put("ORIGINALFILE_NAME", outputFileNm);
        		sMap.put("FILE_TYPE", "ZIP");
        		sMap.put("ATTACHFILE_NAME", outputFileNm);
        		sMap.put("ATTACHFILE_PATH", foutputPath);
        		sMap.put("FILE_SIZE", entryFile.length());
        		resultList.add( sMap );

                foutput.flush();
                if (foutput != null) {
                    foutput.close();
                }
                unzipFileCnt++;

            }
        } catch (Exception e) {
            logger.debug("Zip.unZip().... Error");
            e.printStackTrace();
        }
        long msec = System.currentTimeMillis() - beginTime;
        logger.debug("Unzipe Time Check :: >> " + msec/1000 + "." + (msec % 1000) + " sec. elapsed...");

		return resultList;
	}


	/**
	 * FileからMultipartFileを生成
	 *
	 * @param filePath ファイル格納先
	 * @param filename ファイル名
	 * @return Fileから生成したMultipartFile
	 * @throws Throwable
	 */
	public MultipartFile createMultipartFile(String filePath, Object filename) throws Throwable {
		if (StringUtil.isEmpty(filename)) {
			return null;
		}
		String realFileName = "";
		String fileNameWithDir = filename.toString();
		FileInputStream fis = new FileInputStream(new File(filePath + fileNameWithDir));
		if (fileNameWithDir.indexOf("/") != -1) {
			realFileName = fileNameWithDir.substring(fileNameWithDir.lastIndexOf("/")+1);
		} else {
			realFileName = fileNameWithDir;
		}
		return new MockMultipartFile(realFileName, realFileName, "text/plain", fis);
	}


	/**
	 * フォルダ内の全ファイルおよびディレクトリを削除する
	 * @param f フォルダObject
	 */
	public void deleteFolder(File f) {

		if(f.exists()) {
			//ファイルの場合
			if(f.isFile()) {
				f.delete();
			}
			//ディレクトリの場合
			else if(f.isDirectory()){
				//対象ディレクトリ内のファイルおよびディレクトリの一覧を取得
				File[] files = f.listFiles();

				//ファイルおよびディレクトリをすべて削除
				for(int i=0; i<files.length; i++) {
					//自身をコールし、再帰的に削除する
					deleteFolder( files[i] );
				}
				//自ディレクトリを削除する
				f.delete();
			}
		}
	}


	/*
	 * ZIPファイルを作成する
	 */
	public File createZipFile(String targetDir, String zipFileName, String charset) throws Throwable {

		if (targetDir.lastIndexOf("/") != targetDir.length()-1) {
			targetDir = targetDir + "/";
		}

		//圧縮するフォルダ内のファイルを取得する
		File dir = new File(targetDir);
		File[] fileArray = dir.listFiles();

		//ZIPファイルを生成する
		File zipfile = new File(targetDir + zipFileName);

		FileOutputStream foutput = new FileOutputStream(zipfile);
		org.apache.tools.zip.ZipOutputStream zoutput = new org.apache.tools.zip.ZipOutputStream(foutput);

		//ZIP内ファイルの文字化けを防ぐため、文字コードを指定して置く
		zoutput.setEncoding(charset);

		try {
			//生成したZIPファイルにファイルを追加する
			makeZip(fileArray, zoutput, targetDir);
		}
		finally {
			zoutput.close();
			foutput.close();
		}

        return zipfile;
    }

	private static void makeZip(File[] files, org.apache.tools.zip.ZipOutputStream zipOut, String targetDir) throws Throwable {

		byte[] buffer = new byte[64*1024];

		//圧縮対象ファイルを読込みzip Entryに追加
		for (int i=0; i<files.length; i++) {

			File compPath = new File(files[i].getPath());

			//フォルダが存在する場合、フォルダ内ファイルも圧縮対象にする
			if (compPath.isDirectory()) {
				File[] subFiles = compPath.listFiles();
				makeZip(subFiles, zipOut, targetDir+compPath.getName()+"/");
				continue;
			}

			//ZIP OutputStreamにZIP entryを追加
			zipOut.putNextEntry(new org.apache.tools.zip.ZipEntry(files[i].getName()));

			//読込みしたファイルをZIPに書き込み
			int data = 0;
			FileInputStream in = new FileInputStream(compPath);
			while ((data = in.read(buffer)) > 0) {
				zipOut.write(buffer, 0, data);
			}

			zipOut.closeEntry();
			in.close();
        }
    }
}