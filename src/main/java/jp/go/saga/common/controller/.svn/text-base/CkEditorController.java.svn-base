package jp.go.saga.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import jp.go.saga.common.repository.CommonDao;
import jp.go.saga.common.service.FileService;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.util.DateUtils;

@Controller
public class CkEditorController {
	@Autowired
	FileService fileService;

	@Autowired
	CommonDao dao;

	@ResponseBody
	@RequestMapping("/ckEditor/mathImageUpload.do")
	public String mathImageUpload(@RequestParam Map<String,Object> paramMap) throws Throwable{
		byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(MapUtils.getString(paramMap, "image"));

		String fileId = MapUtils.getString(paramMap, "FILE_ID");

		if(StringUtils.isEmpty(fileId)){
			fileId = String.valueOf(dao.queryForInteger("file.selectAttachFileID")+1);
		}

		String ATTACHFILE_SAVA = (String) paramMap.get("save");
		String ATTACHFILE_NAME = (String) paramMap.get("name");
		String ATTACHFILE_TYPE = (String) paramMap.get("type");

		String preUri = ApplicationProperty.get("sso.url.root");
		String returnFileName = "";

		if(ATTACHFILE_SAVA!=null && ATTACHFILE_NAME!=null && ("JPG".equalsIgnoreCase(ATTACHFILE_TYPE) || "PNG".equalsIgnoreCase(ATTACHFILE_TYPE) )){
			Map<String,Object> nParamMap = paramMap;
			Map<String,Object> fileMap = new HashMap<String,Object>();

			String filePath = ApplicationProperty.get("upload.path");

			if(filePath.lastIndexOf("/") == filePath.length()-1) {
				filePath = filePath + DateUtils.getToday("yyyy") + "/" + DateUtils.getToday("MM") + "/";
			} else {
				filePath = filePath + "/" + DateUtils.getToday("yyyy") + "/" + DateUtils.getToday("MM") + "/";
			}
			File uFile = new File(filePath);
			if(!!uFile.exists()) {
				uFile.mkdirs();
			}

			File folder = new File(filePath);
			File fileName = new File(folder, ATTACHFILE_NAME + "." + ATTACHFILE_TYPE);
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(bytes);
			fos.close();

			nParamMap.put("ORIGINALFILE_NAME", ATTACHFILE_NAME+"."+ATTACHFILE_TYPE);
			nParamMap.put("FILE_TYPE", ATTACHFILE_TYPE);
			nParamMap.put("FILE_SIZE", 0);
			nParamMap.put("ATTACHFILE_NAME", ATTACHFILE_NAME+"."+ATTACHFILE_TYPE);
			nParamMap.put("ATTACHFILE_PATH", filePath);
			nParamMap.put("ATTACHFILE_ID", fileId);

			dao.insert("file.insertAttachFileInfo", nParamMap);

			//returnFileName = preUri + "/downloadAttachFile.do?ATTACHFILE_NAME=" + ATTACHFILE_NAME+"."+ATTACHFILE_TYPE;
			returnFileName = "/klb/downloadAttachFile.do?ATTACHFILE_NAME=" + ATTACHFILE_NAME+"."+ATTACHFILE_TYPE;
		} else {
			returnFileName = "";
		}

		return returnFileName;
	}

	@RequestMapping(value = "/ckEditor/fileUpload.do")
	public String fileUpload(@RequestParam Map<String,Object> paramMap, @RequestParam("upload") MultipartFile[] file) throws Throwable {
		String FILE_ID = fileService.uploadFile(paramMap, file);

		paramMap.put("ATTACHFILE_ID", FILE_ID);
		Map<String, Object> fileInfo = fileService.selectAttachFileMap(paramMap);

		//String preUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		String preUri = ApplicationProperty.get("sso.url.root");
		String ATTACHFILE_NAME = MapUtils.getString(fileInfo, "ATTACHFILE_NAME");
		// String returnFileName = "/getStream.do?ATTACHFILE_NAME=" + ATTACHFILE_NAME;
		String returnFileName = preUri + "/downloadAttachFile.do?ATTACHFILE_NAME=" + ATTACHFILE_NAME;

		return "redirect:/ckeditor.jsp?CKEditorFuncNum=2&returnFileName=" + returnFileName;
	}
}