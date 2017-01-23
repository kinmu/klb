package jp.go.saga.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.saga.common.AsyncResponseMap;
import jp.go.saga.common.service.FileService;
import jp.go.saga.common.view.bean.FileModel;
import jp.go.saga.io.ModifiedCommonsMultipartResolver;
import jp.go.saga.service.klb.KlbCommonService;
import jp.go.saga.service.klb.LibraryService;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bsmedia.system.util.StringUtil;

@Controller
public class FileController {

	 private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	FileService fileService;
	@Autowired
    ModifiedCommonsMultipartResolver multipartResolver;
	@Autowired
	AsyncResponseMap asyncResponseMap;

	@Autowired
	LibraryService libraryService;

	@Autowired
	KlbCommonService klbCommonService;

	/**
	 * 添付ファイルダウンロード
	 */
	@RequestMapping(value = "/downloadAttachFile.do")
	public String downloadAttachFile(HttpServletResponse response, @RequestParam Map<String, Object> paramMap, @RequestParam("ATTACHFILE_NAME") String ATTACHFILE_NAME, ModelMap model) throws Throwable {
		if (!"".equals(StringUtil.getStr(ATTACHFILE_NAME,""))) {
			model.put("FILE_MODEL", fileService.getAttachFileByAttaFileName(ATTACHFILE_NAME));
			return "fileDownloadView";
		} else {
			return "";
		}
	}

	/**
	 * 添付ファイルダウンロード
	 * ダウンロードファイル名が日本語の場合、文字化け回避のため内部で再取得して表示させる。
	 * （ダウンロード表示名が日本語の場合、GETパラメータで渡すと文字化けが発生）
	 *
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/common/klbDownloadAttachFile.do")
	public String klbDownloadAttachFile(HttpServletResponse response
			, @RequestParam Map<String, Object> paramMap
			, ModelMap model) throws Throwable {

		// 2015/03/20 脆弱性対応-ファイル経路をパラメータで渡さないように修正

		// パラメータの教材IDで教材情報を取得
		Map libraryInfo =  klbCommonService.selectLibraryInfoByLibraryId(paramMap);

		// 教材情報のファイルパスと物理ファイル名でダウンロードファイル名を作成
		String downloadFile = StringUtil.getStr(MapUtils.getString(libraryInfo, "FILE_PATH"), "");

		// ファイルダウンロード処理
		if (!"".equals(downloadFile)) {

			// オリジナルファイル名取得
			Map libraryMap = libraryService.selectFileByLibraryIdAndSno(paramMap);

			String orgFileName = StringUtil.getStr(MapUtils.getString(libraryMap, "FILE_NAME_BEF"),"");
			downloadFile = downloadFile .concat( StringUtil.getStr(MapUtils.getString(libraryMap, "FILE_NAME_AFT"),""));

			logger.debug("ダウンロードファイル名 ：["+downloadFile+"]");
			logger.debug("表示用_オリジナルファイル名 ：["+orgFileName+"]");

			model.put("FILE_MODEL", new FileModel(new File(downloadFile), orgFileName));
			return "fileDownloadView";

		} else {
			logger.debug("#### ダウンロードファイル名が正しくありません。ERROR ");
			return "";

		}
	}

// 2015.01.06 jungsr delete
//	/**
//	 * 스콤 다운로드
//	 */
//	@RequestMapping(value = "/downloadOnlinetestScorm.do")
//	public String downloadOnlinetestScorm(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramMap, ModelMap model) throws Throwable {
//
//
//		model.put("FILE_MODEL", fileService.getAttachFileOnlinetestScorm(paramMap));
//		return "fileDownloadView";
//	}
// 2015.01.06 jungsr delete

	/**
	 * ファイルリスト(ATTACH_FILE_ID基準)
	 */
	@RequestMapping(value="/getAttachFileList.do")
	public String getAttachFileList(@RequestParam Map<String, Object> paramMap, @RequestParam("ATTACHFILE_ID") String ATTACHFILE_ID, ModelMap model) throws Throwable {
		if (!"".equals(StringUtil.getStr(ATTACHFILE_ID,""))) {
			model.put("attaFileResults", fileService.selectAttachFileList(paramMap));
			return "/common/attachfile/uploadedFileViewer";
		} else {
			return "";
		}
	}

	/**
	 * ファイル削除
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAttachFile.do")
	public @ResponseBody Map<String, Object> deleteAttachFile(@RequestParam("ATTACHFILE_NAME") String ATTACHFILE_NAME) throws Throwable {
		if (!"".equals(StringUtil.getStr(ATTACHFILE_NAME,""))) {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("ATTACHFILE_NAME", ATTACHFILE_NAME);
			fileService.deleteAttachFileByAttaFileName(paramMap);
			return asyncResponseMap.setDeleteOkMessage();
		} else {
			return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
		}
	}

	/**
	 * ファイル削除
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAttachFileById.do")
	public @ResponseBody Map<String, Object> deleteAttachFileById(@RequestParam("ATTACHFILE_ID") String ATTACHFILE_ID) throws Throwable {
		if (!"".equals(StringUtil.getStr(ATTACHFILE_ID,""))) {
			fileService.deleteAttachFileById(ATTACHFILE_ID);
			return asyncResponseMap.setDeleteOkMessage();
		} else {
			return asyncResponseMap.setErrorMessage().setSuccessFlag(false);
		}
	}

	/**
	 * 스트리밍 출력
	 */
	@RequestMapping(value = "/getStream.do")
	public void getStream(@RequestParam("ATTACHFILE_NAME") String ATTACHFILE_NAME, HttpServletResponse response, HttpServletRequest request) throws Throwable {
		if (!"".equals(ATTACHFILE_NAME)) {
			FileModel fileModel = fileService.getAttachFileByAttaFileName(ATTACHFILE_NAME);
			File file = fileModel.getFile();
			String filename = file.getName();
			String ext = filename.substring(filename.indexOf(".")+1,filename.length());

			if ("mp4".equalsIgnoreCase(ext)) {
				response.setContentType("video/mp4");
			} else if ("mp3".equalsIgnoreCase(ext)) {
				response.setContentType("audio/mp3");
			} else if ("ogg".equalsIgnoreCase(ext)) {
				response.setContentType("audio/ogg");
			} else if ("wav".equalsIgnoreCase(ext)) {
				response.setContentType("audio/wav");
			} else if ("png".equalsIgnoreCase(ext)) {
				response.setContentType("image/png");
			} else if ("jpg".equalsIgnoreCase(ext)) {
				response.setContentType("image/jpg");
			} else if ("gif".equalsIgnoreCase(ext)) {
				response.setContentType("image/gif");
			}

			response.setHeader("Content-Type", "application/octet-stream;");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Expires", "-1;");

			if(request.getHeader("User-Agent").contains("Firefox")) {
				response.setHeader("Content-Disposition","attachment;filename=\"" + new String(filename.getBytes("UTF-8"), "ISO-8859-1") + "\";");
			} else {
				response.setHeader("Content-Disposition","attachment;filename=\"" + URLEncoder.encode(filename, "utf-8") + "\";");
			}

			IOUtils.copy(new FileInputStream(file), response.getOutputStream());
			response.flushBuffer();
		}
	}

	@RequestMapping("/uploadFileForm.do")
	public String uploadFileForm(@RequestParam Map<String, Object> paramMap, @RequestParam(value="ATTACHFILE_ID", required=false) String ATTACHFILE_ID, ModelMap model) throws Throwable {
		// 현재 환경의 파일업로드 최대크기를 조회
		model.put("maxUploadSize", multipartResolver.getFileUpload().getSizeMax());

		if (!"".equals(ATTACHFILE_ID)) {
			model.put("attaFileResults", fileService.selectAttachFileList(paramMap));
		}

		return "/common/attachfile/uploadFileForm";
	}

	@RequestMapping("/uploadVodForm.do")
	public String uploadVodForm(@RequestParam Map<String, Object> paramMap, @RequestParam(value="ATTACHFILE_ID", required=false) String ATTACHFILE_ID, ModelMap model) throws Throwable {
		// 현재 환경의 파일업로드 최대크기를 조회
		model.put("maxUploadSize", multipartResolver.getFileUpload().getSizeMax());

		if (!"".equals(ATTACHFILE_ID)) {
			model.put("attaFileResults", fileService.selectAttachFileList(paramMap));
		}

		return "/common/attachfile/uploadVodForm";
	}
}