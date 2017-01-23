package jp.go.saga.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jp.go.saga.common.AsyncResponseMap;
import jp.go.saga.common.service.FileService;
import jp.go.saga.io.ModifiedCommonsMultipartResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.FileUtils;
import bsmedia.system.util.StringUtil;

@Controller
public class FileManageController {
	// private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	@Autowired
	FileService fileService;
	@Autowired
    ModifiedCommonsMultipartResolver multipartResolver;
	@Autowired
	AsyncResponseMap asyncResponseMap;


	@RequestMapping(value = "/fileManage/fileManageListNoLayout.do")
	public void fileManageListNoLayout(@RequestParam Map<String,Object> paramMap, ModelMap model) throws Throwable {
		model.put("filePath",ApplicationProperty.get("upload.path"));
		model.put("results",fileService.selectMonthFileList(paramMap));
	}

	@RequestMapping("/fileManage/moveFileProc.do")
	public @ResponseBody  Map<String,Object> survtrgtsearchList(@RequestParam Map<String,Object> paramMap, HttpSession session) throws Throwable {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String sYear = StringUtil.getStr((String)paramMap.get("sYear"),"");
		String sMonth = StringUtil.getStr((String)paramMap.get("sMonth"),"");


		String uploadPath = ApplicationProperty.get("upload.path");
		List fileResult = new ArrayList();


		if(!sYear.equals("")&&!sMonth.equals("")){

			List<HashMap> fileList = fileService.selectFileList(paramMap);

			for(HashMap cont : fileList) {

				fileResult.add("######################################################################");
				String ATTACHFILE_ID = String.valueOf(cont.get("ATTACHFILE_ID"));
				String FILE_SEQ = String.valueOf(cont.get("FILE_SEQ"));
				String FILE_TYPE = StringUtil.getStr((String)cont.get("FILE_TYPE"),"");
				String ATTACHFILE_PATH = StringUtil.getStr((String)cont.get("ATTACHFILE_PATH"),"");
				String ATTACHFILE_NAME = StringUtil.getStr((String)cont.get("ATTACHFILE_NAME"),"");
				String FILE_SIZE =String.valueOf(cont.get("FILE_SIZE"));
				String Y = (String)cont.get("Y");
				String M = (String)cont.get("M");

				if(FILE_TYPE.toLowerCase().equals("zip")&&ATTACHFILE_NAME.toLowerCase().indexOf(".zip")==-1){////압축이 풀린 폴더는 복사하지 않는다.
					//do nothing
					 fileResult.add("압축이 풀린 폴더 입니다.");
				} else {


						String sourceFilePath = "";
						String targetFilePath =  "";
						if(ATTACHFILE_PATH.lastIndexOf("/") == ATTACHFILE_PATH.length()-1) {
							sourceFilePath = ATTACHFILE_PATH;
							targetFilePath = ATTACHFILE_PATH + Y + "/" + M + "/" ;
						} else {
							sourceFilePath = ATTACHFILE_PATH + "/";
							targetFilePath = ATTACHFILE_PATH + "/" + Y + "/" + M + "/";
						}
					if(sourceFilePath.indexOf("/" + Y + "/" + M + "/")==-1) {
						fileResult.add("sourceFile : " + sourceFilePath + ATTACHFILE_NAME);
						fileResult.add("targetFile : " + targetFilePath + ATTACHFILE_NAME);

						File sFile = new File(sourceFilePath + ATTACHFILE_NAME);
						if(sFile.exists()) {
							File tempFile = new File(targetFilePath + ATTACHFILE_NAME);
							if(!tempFile.exists()) {//복사가 안되어 있다면

								File targetFolder = new File(targetFilePath);
								if(!targetFolder.exists()){//폴더 생성
									targetFolder.mkdirs();
								}
								//파일복사
								 fileResult.add(">>>파일을 복사 합니다.");
								FileUtils.copyTransferFile(sourceFilePath + ATTACHFILE_NAME ,targetFilePath + ATTACHFILE_NAME);

								File tFile = new File(targetFilePath + ATTACHFILE_NAME);
								if(sFile.exists()&&(sFile.length() == tFile.length())) {//복사가 잘 되었으면
									 Map<String,Object> uParam = new HashMap<String,Object>();
									 uParam.put("ATTACHFILE_ID", ATTACHFILE_ID);
									 uParam.put("FILE_SEQ", FILE_SEQ);
									 uParam.put("ATTACHFILE_PATH", targetFilePath);
									 fileResult.add(">>>DB업데이트를 합니다");
									 int resultNo = fileService.updateFilePath(uParam);
									 if(resultNo==1) {//DB업데이트가 잘 되었으면
										 fileResult.add(">>>DB에 반영이 되었습니다.");

										 /*
										 boolean dResult = sFile.delete();
										 if(dResult) {
											 fileResult.add(">>>소스파일이 삭제 되었습니다.");
										 } else {
											 fileResult.add("소스파일이 삭제에 실패 했습니다.");
										 }
										 */


									 }else {
										 fileResult.add("DB 업데이트에 실패 했습니다.");
									 }
								} else {
									fileResult.add("파일 복사에 실패 했습니다.");
								}
						   } else {
							   fileResult.add("이미 복사가 되었습니다.File");
						   }
					   } else {
						   fileResult.add("소스 파일이 존재 하지 않습니다.");
					   }
				 } else {
					 fileResult.add("이미 복사가 되었습니다.DB");
				 }
			  }
				fileResult.add("######################################################################");
			}

			resultMap.put("result", "이동되었습니다.로그확인" + uploadPath + "klbFileMoveHistoryLog_" + sYear + "_" + sMonth + ".txt" );
		} else{
			resultMap.put("result", "이동에 실패 했습니다.파라메터를 확인해주세요.");
		}


		//로그릉 남긴다.
		try {
			if(fileResult.size()>0) {
				this.logSave(uploadPath + "klbFileMoveHistoryLog_" + sYear + "_" + sMonth + ".txt" , fileResult);
			}
		}catch(Exception e){}

		return resultMap;
	}

	//로그 파일을 저장한다.
	private void logSave(String logFile,List logData)throws Exception  {

        File file = new File(logFile);
        OutputStream out = new FileOutputStream(file);
        StringBuffer fileCont = new StringBuffer();
        for(int i=0;i<logData.size();i++) {
        	fileCont.append(((String)logData.get(i)) + "\n");
        }
        out.write(fileCont.toString().getBytes());
        out.close();
	}



}