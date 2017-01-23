package jp.go.saga.common.service.portal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.go.saga.common.Constants;
import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.common.service.FileService;
import jp.go.saga.common.view.bean.FileModel;
import jp.go.saga.io.FileIO;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.FileUtils;

@Service
public class PortalFileService implements FileService {
	private static final Logger logger = LoggerFactory.getLogger(PortalFileService.class);

	@Autowired
	CommonDao dao;
	@Autowired
	FileIO fileIO;

	@Override
	public String uploadFile(Map<String,Object> paramMap, MultipartFile[] file) throws Throwable{

		// 修正
		String fileId = MapUtils.getString(paramMap, "FILE_ID");

		// 新規
		if(StringUtils.isEmpty(fileId)){
			fileId = String.valueOf(dao.queryForInteger("file.selectAttachFileID")+1);
		}

		int countFile = 0;
		for(MultipartFile f: file){
			if(f.isEmpty()) continue;

			Map<String,Object> nParamMap = paramMap;
			Map<String,Object> fileMap = fileIO.saveFile(f, null, null);

			nParamMap.put("ORIGINALFILE_NAME",fileMap.get(FileIO.ORIGINAL_FILE_NAME));
			nParamMap.put("FILE_TYPE",fileMap.get(FileIO.FILE_EXT));
			nParamMap.put("FILE_SIZE",fileMap.get("fileSize"));
			nParamMap.put("ATTACHFILE_NAME",fileMap.get(FileIO.SAVED_FILE_NAME));
			nParamMap.put("ATTACHFILE_PATH", fileMap.get(Constants.FILE_PATH));
			nParamMap.put("ATTACHFILE_ID", fileId);

			dao.insert("file.insertAttachFileInfo", nParamMap);
			countFile++;
		}

		if (countFile==0) fileId = "";

		return fileId;
	}



	@Override
	public String scormFileUpload(Map<String,Object> paramMap, File file) throws Throwable{

		Map<String,Object> nParamMap = paramMap;
		if(file== null) return "";
		String fileId = String.valueOf(dao.queryForInteger("file.selectAttachFileID")+1);

		Map<String,Object> fileMap = fileIO.saveScormFile(file);

		nParamMap.put("ORIGINALFILE_NAME",fileMap.get(FileIO.ORIGINAL_FILE_NAME));
		nParamMap.put("FILE_TYPE",fileMap.get(FileIO.FILE_EXT));
		nParamMap.put("FILE_SIZE",fileMap.get("fileSize"));
		nParamMap.put("ATTACHFILE_NAME",fileMap.get(FileIO.SAVED_FILE_NAME));
		nParamMap.put("ATTACHFILE_PATH", fileMap.get(Constants.FILE_PATH));
		nParamMap.put("ATTACHFILE_ID", fileId);

		dao.insert("file.insertAttachFileInfo", nParamMap);
		fileMap.put("fileId", fileId);

		return fileId;
	}


	@Override
	public Map<String,Object> uploadFileOne(Map<String,Object> paramMap, MultipartFile file) throws Throwable{

		Map<String,Object> nParamMap = paramMap;
		if(file.isEmpty()) return nParamMap;
		String fileId = String.valueOf(dao.queryForInteger("file.selectAttachFileID")+1);

		Map<String,Object> fileMap = fileIO.saveFile(file, null, null);

		nParamMap.put("ORIGINALFILE_NAME",fileMap.get(FileIO.ORIGINAL_FILE_NAME));
		nParamMap.put("FILE_TYPE",fileMap.get(FileIO.FILE_EXT));
		nParamMap.put("FILE_SIZE",fileMap.get("fileSize"));
		nParamMap.put("ATTACHFILE_NAME",fileMap.get(FileIO.SAVED_FILE_NAME));
		nParamMap.put("ATTACHFILE_PATH", fileMap.get(Constants.FILE_PATH));
		nParamMap.put("ATTACHFILE_ID", fileId);

		dao.insert("file.insertAttachFileInfo", nParamMap);
		fileMap.put("fileId", fileId);

		return fileMap;
	}

	@Override
	public Map<String, Object> procUnzip(Map<String,Object> paramMap, MultipartFile file_zip) throws Throwable  {

		if(file_zip.isEmpty()) return paramMap;

		//압출(zip)파일 업로드
		Map<String,Object> zipFileMap = this.uploadFileOne(paramMap, file_zip);
		//unzip처리
		List<Map<String, Object>> unzipList = fileIO.unZip(zipFileMap);

		Map<String,Object> returnFileMap = new HashMap<String,Object>();
		for (Map fileinfo:unzipList) {
			if(fileinfo.isEmpty()) continue;
			Map<String,Object> nParamMap = paramMap;

			String fileId = String.valueOf(dao.queryForInteger("file.selectAttachFileID")+1);

			nParamMap.put("ORIGINALFILE_NAME", fileinfo.get("ORIGINALFILE_NAME"));
			nParamMap.put("FILE_TYPE", fileinfo.get("FILE_TYPE"));
			nParamMap.put("FILE_SIZE", fileinfo.get("FILE_SIZE"));
			nParamMap.put("ATTACHFILE_NAME", fileinfo.get("ATTACHFILE_NAME"));
			nParamMap.put("ATTACHFILE_PATH", fileinfo.get("ATTACHFILE_PATH"));
			nParamMap.put("ATTACHFILE_ID", fileId);
			dao.insert("file.insertAttachFileInfo", nParamMap);

			returnFileMap.put((String)fileinfo.get("ORIGINALFILE_NAME"), fileinfo.get("ATTACHFILE_NAME"));
		}

		returnFileMap.put("fileId" , zipFileMap.get("fileId"));
		System.out.println(returnFileMap);
		return returnFileMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public File getAttachFile(String FILE_ID) throws Throwable {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ATTACHFILE_ID", FILE_ID);
		map.put("KLBPATH", ApplicationProperty.get("upload.path"));
		map.put("LCMSPATH", ApplicationProperty.get("upload.path.lcms"));
		Map<String,Object> info = dao.queryForMap("file.selectAttachFileList", map);

		String ATTA_FILE_PATH = (String)info.get("ATTACHFILE_PATH");
		String ATTA_FILE_NM = (String)info.get("ATTACHFILE_NAME");

		return new File(ATTA_FILE_PATH + ATTA_FILE_NM);
	}

	@SuppressWarnings("unchecked")
	@Override
	public FileModel getAttachFileByAttaFileName(String ATTA_FILE_NM) throws Throwable {
		// Assert.notNull(ATTA_FILE_NM);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ATTACHFILE_NAME", ATTA_FILE_NM);
		map.put("KLBPATH", ApplicationProperty.get("upload.path"));
		map.put("LCMSPATH", ApplicationProperty.get("upload.path.lcms"));
		Map<String,Object> info = dao.queryForMap("file.selectAttachFileList",map);
//		String ATTA_FILE_PATH = (info.get("ATTACHFILE_PATH")==null)?"":(String)info.get("ATTACHFILE_PATH");

		String ATTA_FILE_PATH_klb = (info.get("ATTACHFILE_PATH")==null)?"":(String)info.get("ATTACHFILE_PATH");
		String lcmsPath = (String)ApplicationProperty.get("upload.path.lcms");
		String klbPath = (String)ApplicationProperty.get("upload.path");
		String ATTA_FILE_PATH = ATTA_FILE_PATH_klb.replace(lcmsPath,klbPath);
		String ORGN_FILE_NM = MapUtils.getString(info, "ORIGINALFILE_NAME");

		return new FileModel(new File(ATTA_FILE_PATH + ATTA_FILE_NM), ORGN_FILE_NM);
	}

// 2015.01.06 jungsr delete
//	@SuppressWarnings("unchecked")
//	@Override
//	public FileModel getAttachFileOnlinetestScorm(Map<String,Object> paramMap) throws Throwable {
//
//		//탬플릿 폼더 복사
//		String scormPath = ApplicationProperty.get("scorm.path.onlinetest");
//		String scormTargetPath = scormPath+"/online_test_"+paramMap.get("ONLINE_TEST_ID")+"/";
//		FileUtils.copyTransferFolder(scormPath+"/templete/", scormTargetPath);
//		FileUtils.createDir(new File(scormTargetPath+"resources/"));
//
//		//시험지 상제정보
//		ObjectMapper om = new ObjectMapper();
//		List testDetail = onlinetestStudentService.selectOnlineTestDoDetailPreview(paramMap);
//		String testDetailJSON = om.writeValueAsString(testDetail);
//		FileOutputStream outputStream  = new FileOutputStream(new File(scormTargetPath+"selectOnlineTestDoDetail.txt"));
//		OutputStreamWriter output = new OutputStreamWriter(outputStream, "UTF-8");
//		BufferedWriter writer = new BufferedWriter(output);
//		writer.write(testDetailJSON);
//		writer.close();
//		outputStream.close();
//		output.close();
//
//		paramMap.put("KLBPATH1", ApplicationProperty.get("sso.url.root")+"/downloadAttachFile.do");
//		paramMap.put("LCMSPATH1", ApplicationProperty.get("sso.url.root.lcms")+"/downloadAttachFile.do");
//		paramMap.put("KLBPATH2", ApplicationProperty.get("sso.url.root")+"/getStream.do");
//		paramMap.put("LCMSPATH2", ApplicationProperty.get("sso.url.root.lcms")+"/getStream.do");
//
//		//문제 정보
//		List<HashMap<String,Object>> testDetailList = (List<HashMap<String,Object>>)onlinetestStudentService.selectTestQuestionDetailList(paramMap);
//
//		List questionList = new ArrayList();
//
//		for (HashMap<String,Object> resultMap : testDetailList) {
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("QUESTION_ID", resultMap.get("QUESTION_ID"));
//
//			//첨부파일 저장
//			resultMap.put("QUESTION_BODY", this.setSaveImg((String)resultMap.get("QUESTION_BODY"), scormTargetPath));  //문제
//			if ("03".equals(resultMap.get("MEDIA_TYPE_CODE"))) {
//				this.saveScormImgVod((String)resultMap.get("ATTACHFILE_NAME"), scormTargetPath+"resources/", (String) resultMap.get("MEDIA_TYPE_CODE"));
//				resultMap.put("ATTACHFILE_NAME", (String)resultMap.get("ATTACHFILE_NAME")+".mp4");
//			}else if("04".equals(resultMap.get("MEDIA_TYPE_CODE"))){
//				this.saveScormImgVod((String)resultMap.get("ATTACHFILE_NAME"), scormTargetPath+"resources/", (String) resultMap.get("MEDIA_TYPE_CODE"));
//				resultMap.put("ATTACHFILE_NAME", (String)resultMap.get("ATTACHFILE_NAME")+".mp3");
//			}else {
//				this.saveScormImg((String)resultMap.get("ATTACHFILE_NAME"), scormTargetPath+"resources/");
//			}
//			resultMap.put("QUESTSTMT", this.setSaveImg((String)resultMap.get("QUESTSTMT"), scormTargetPath));  //제시문
//			resultMap.put("HINT_CONTENTS", this.setSaveImg((String)resultMap.get("HINT_CONTENTS"), scormTargetPath));  //힘트
//			resultMap.put("SOLVE_CONTENTS", this.setSaveImg((String)resultMap.get("SOLVE_CONTENTS"), scormTargetPath));  //해설
//
//			map.put("KLBPATH1", ApplicationProperty.get("sso.url.root")+"/downloadAttachFile.do");
//			map.put("LCMSPATH1", ApplicationProperty.get("sso.url.root.lcms")+"/downloadAttachFile.do");
//			map.put("KLBPATH2", ApplicationProperty.get("sso.url.root")+"/getStream.do");
//			map.put("LCMSPATH2", ApplicationProperty.get("sso.url.root.lcms")+"/getStream.do");
//			List<HashMap<String,Object>> questionExamList = (List<HashMap<String,Object>>)onlinetestStudentService.selectQuestionExamInfoList(map);
//			List questionExamListReal = new ArrayList();
//
//			for (HashMap<String,Object> resultExamMap : questionExamList) {
//				resultExamMap.put("EXAM_BODY", this.setSaveImg((String)resultExamMap.get("EXAM_BODY"), scormTargetPath));
//				questionExamListReal.add(resultExamMap);
//			}
//			String questionExamJSON = om.writeValueAsString(questionExamListReal);
//			FileOutputStream outputExamStream  = new FileOutputStream(new File(scormTargetPath+"selectTestExamList_"+resultMap.get("QUESTION_ID")+".txt"));
//			OutputStreamWriter outputExam = new OutputStreamWriter(outputExamStream, "UTF-8");
//			BufferedWriter writerExam = new BufferedWriter(outputExam);
//			writerExam.write(questionExamJSON);
//			writerExam.close();
//			outputExamStream.close();
//			outputExam.close();
//
//			questionList.add(resultMap);
//		}
//
//		String testQuestionJSON = om.writeValueAsString(questionList);
//		FileOutputStream outputQuestionStream  = new FileOutputStream(new File(scormTargetPath+"selectTestQuestionDetailList.txt"));
//		OutputStreamWriter outputQuestion = new OutputStreamWriter(outputQuestionStream, "UTF-8");
//		BufferedWriter writerQuestion = new BufferedWriter(outputQuestion);
//		writerQuestion.write(testQuestionJSON);
//		writerQuestion.close();
//		outputQuestionStream.close();
//		outputQuestion.close();
//
//		//압축처리
//		this.setZip(scormTargetPath, scormPath+"/online_test_"+paramMap.get("ONLINE_TEST_ID")+".zip");
//
//		return new FileModel(new File(scormPath+"/online_test_"+paramMap.get("ONLINE_TEST_ID")+".zip"), "online_test_"+paramMap.get("ONLINE_TEST_ID")+".zip");
//
//	}
// 2015.01.06 jungsr delete


    private void setZip(String targetDir,String  zfileNm) throws Exception {

        FileOutputStream foutput;

        net.sf.jazzlib.ZipOutputStream zoutput;


        // 압축할 폴더를 파일 객체로 생성한다.
        File file = new File(targetDir);
        logger.debug("File Path : " + file.getAbsolutePath());

        // 폴더 안에 있는 파일들을 파일 배열 객체로 가져온다.
        File[] fileArray = file.listFiles();
        // Zip 파일을 만든다.
        File zfile = new File(zfileNm);
        // Zip 파일 객체를 출력 스트림에 넣는다.
        foutput = new FileOutputStream(zfile);

        // 집출력 스트림에 집파일을 넣는다.
        zoutput = new net.sf.jazzlib.ZipOutputStream((OutputStream)foutput);

        this.makeZipFile(fileArray, zoutput, "");

        zoutput.close();
        foutput.close();
        if (zoutput != null) {
            zoutput.close();
        }
        if (foutput != null) {
            foutput.close();
        }
    }

    private static void makeZipFile(File[] files, net.sf.jazzlib.ZipOutputStream zipOut, String targetDir) throws Exception{

        byte[] buffer = new byte[64*1024];

        //디렉토리 내의 파일들을 읽어서 zip Entry로 추가합니다.
        for (int i = 0; i < files.length; i++) {

                File compPath = new File(files[i].getPath());

                //목록에서 디렉토리가 존재할경우 재귀호출하여 하위디렉토리까지 압축한다.
                if(compPath.isDirectory()){
                        File[] subFiles = compPath.listFiles();
                        makeZipFile(subFiles, zipOut, targetDir+compPath.getName()+"/");
                        continue;
                }

                FileInputStream in = new FileInputStream(compPath);

                // ZIP OutputStream에 ZIP entry를 추가
                // ZIP파일 내의 압축될 파일이 저장되는 경로이다...(주의!!!)
//                zipOut.putNextEntry(new net.sf.jazzlib.ZipEntry(targetDir+"/"+files[i].getName()));
                zipOut.putNextEntry(new net.sf.jazzlib.ZipEntry(targetDir+files[i].getName()));

                // 파일을 Zip에 쓴다.
                int data;

                while ((data = in.read(buffer)) > 0) {
                        zipOut.write(buffer, 0, data);
                }

                //하나의 파일을 압축하였다.
                zipOut.closeEntry();
                in.close();

        }

    }

	/**
	 * downloadAttachFile.do 이미지 src 추출해서 저장함
	 * @param str
	 * @return
	 */
	private String setSaveImg(String str, String targetPath) {

		if (str == null) return "";

		Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
		List<String> imgList = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(str);

		while (matcher.find()) {
			String srcURI = matcher.group(1);
			if (srcURI.indexOf("downloadAttachFile.do") > 0 || srcURI.indexOf("getStream.do") > 0) {
				String[] srcURIArr = srcURI.split("ATTACHFILE_NAME=");
				imgList.add(srcURIArr[1]);

				str = str.replace(srcURI, "klb_resources/"+srcURIArr[1]);
			}
		}

		for (String imgUrl : imgList) {
			if (!"".equals(imgUrl)) {
				this.saveScormImg(imgUrl, targetPath+"klb_resources/");
			}
		}
		return str;
	}

	/**
	 * 스콤 이미지 복사
	 * @param attaFileName
	 * @param targetPath
	 */
	private void saveScormImg(String attaFileName, String targetPath) {
		if (attaFileName == null) return;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ATTACHFILE_NAME", attaFileName);
		map.put("KLBPATH", ApplicationProperty.get("upload.path"));
		map.put("LCMSPATH", ApplicationProperty.get("upload.path.lcms"));
		Map<String,Object> info = dao.queryForMap("file.selectAttachFileList",map);
		if (info.get("ATTACHFILE_NAME").toString().indexOf(".") == -1) {
			attaFileName = attaFileName+"."+info.get("FILE_TYPE");
		}
		String ATTA_FILE_PATH = (info.get("ATTACHFILE_PATH")==null)?"":(String)info.get("ATTACHFILE_PATH");
		FileUtils.copy(ATTA_FILE_PATH + attaFileName, targetPath+attaFileName);
		System.out.println("#######################"+ATTA_FILE_PATH + attaFileName);
		System.out.println("#######################"+targetPath + attaFileName);
	}

	/**
	 * 스콤 이미지 복사2
	 * @param attaFileName
	 * @param targetPath
	 */
	private void saveScormImgVod(String attaFileName, String targetPath, String mediaTypeCode) {
		if (attaFileName == null) return;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ATTACHFILE_NAME", attaFileName);
		map.put("KLBPATH", ApplicationProperty.get("upload.path"));
		map.put("LCMSPATH", ApplicationProperty.get("upload.path.lcms"));
		Map<String,Object> info = dao.queryForMap("file.selectAttachFileList2",map);
		String ATTA_FILE_PATH = ApplicationProperty.get("scorm.path.vod");
		System.out.println("#######################"+ATTA_FILE_PATH + attaFileName);
		System.out.println("#######################"+targetPath + attaFileName);
		if("03".equals(mediaTypeCode)){
			attaFileName = attaFileName+".mp4";
		}else if("04".equals(mediaTypeCode)){
			attaFileName = attaFileName+".mp3";
		}
		FileUtils.copy(ATTA_FILE_PATH + attaFileName, targetPath+attaFileName);
	}


	/**
	 * Delete AttachFile
	 * @return 1: success , 2: failure
	 */
	@Override
	public int deleteAttachFileByAttaFileName(Map<String,Object> paramMap) {
		try{
			dao.delete("file.deleteAttachFileByAttaFileName", paramMap);
			return 1;
		} catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public int deleteAttachFileById(String attaFileId) {
		try{
			dao.delete("file.deleteAttachFileById", attaFileId);
			return 1;
		} catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Override
	public List<?> selectAttachFileList(Map<String,Object> paramMap){
		paramMap.put("KLBPATH", ApplicationProperty.get("upload.path"));
		paramMap.put("LCMSPATH", ApplicationProperty.get("upload.path.lcms"));
		return dao.queryForList("file.selectAttachFileList", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> selectAttachFileMap(Map<String,Object> paramMap){
		if(paramMap.get("ATTACHFILE_ID") == null && paramMap.get("ATTACHFILE_NAME") == null){
			logger.warn("ATTACHFILE_ID is null");
			logger.warn("ATTA_FILE_NM is null");
		}
		paramMap.put("KLBPATH", ApplicationProperty.get("upload.path"));
		paramMap.put("LCMSPATH", ApplicationProperty.get("upload.path.lcms"));
		return dao.queryForMap("file.selectAttachFileList", paramMap);
	}

	// Byte[]를 파일로 저장하기 (엑셀의 첨부된 그림을 저장하기 위한 용도)
	@Override
	public Map<String,Object> uploadFileForByte(Map<String,Object> paramMap, byte[] data) throws Throwable {
		String fileId = "";
		String uploadPath = MapUtils.getString(paramMap, "uploadPath");
		Map<String,Object> resultMap = new HashMap<String,Object>();

		if(data.length > 0) {
			fileId = String.valueOf(dao.queryForInteger("file.selectAttachFileID")+1);
		}

		Map<String,Object> nParamMap = paramMap;
		Map<String,Object> fileMap = fileIO.saveFileForByte(paramMap, data);

		nParamMap.put("ORIGINALFILE_NAME",fileMap.get(FileIO.ORIGINAL_FILE_NAME));
		nParamMap.put("FILE_TYPE",fileMap.get(FileIO.FILE_EXT));
		nParamMap.put("ATTACHFILE_NAME",fileMap.get(FileIO.SAVED_FILE_NAME));
		nParamMap.put("ATTACHFILE_PATH", uploadPath);
		nParamMap.put("ATTACHFILE_ID", fileId);

		dao.insert("file.insertAttachFileInfo", nParamMap);

		resultMap.put("ORIGINALFILE_NAME",fileMap.get(FileIO.ORIGINAL_FILE_NAME));
		resultMap.put("FILE_TYPE",fileMap.get(FileIO.FILE_EXT));
		resultMap.put("ATTACHFILE_NAME",fileMap.get(FileIO.SAVED_FILE_NAME));

		return resultMap;
	}

	// 교육교재의 동영상
	@Override
	public String uploadFileForVOD(Map<String,Object> paramMap) throws Throwable {
		String fileId = "";
		//String uploadPath = MapUtils.getString(paramMap, "uploadPath");
		Map<String,Object> resultMap = new HashMap<String,Object>();

		//if(data.length > 0) {
			fileId = String.valueOf(dao.queryForInteger("file.selectAttachFileID")+1);
		//}

		Map<String,Object> nParamMap = paramMap;
		//Map<String,Object> fileMap = fileIO.saveFileForByte(paramMap, data);

		//nParamMap.put("ORIGINALFILE_NAME",fileMap.get(FileIO.ORIGINAL_FILE_NAME));
		//nParamMap.put("FILE_TYPE",fileMap.get(FileIO.FILE_EXT));
		//nParamMap.put("ATTACHFILE_NAME",fileMap.get(FileIO.SAVED_FILE_NAME));
		//nParamMap.put("ATTACHFILE_PATH", uploadPath);
		nParamMap.put("ATTACHFILE_ID", fileId);

		dao.insert("file.insertAttachFileInfo", nParamMap);

		//resultMap.put("ORIGINALFILE_NAME",fileMap.get(FileIO.ORIGINAL_FILE_NAME));
		//resultMap.put("FILE_TYPE",fileMap.get(FileIO.FILE_EXT));
		//resultMap.put("ATTACHFILE_ID",fileId);

		return fileId;
	}

	@Override
	public Object updateOriginalFile(Map<String,Object> paramMap) throws Throwable{
		return dao.update("file.updateOriginalFile", paramMap);
	}


	public List<?> selectMonthFileList(Map<String,Object> paramMap) throws Throwable{
		 	return dao.queryForList("file.selectMonthFileList", paramMap);
	}

	public List<HashMap> selectFileList(Map<String,Object> paramMap) throws Throwable{
	 	return dao.queryForList("file.selectFileList", paramMap);
	}

	public int updateFilePath(Map<String,Object> paramMap) throws Throwable{
		return  dao.update("file.updateFilePath", paramMap);
	}
}