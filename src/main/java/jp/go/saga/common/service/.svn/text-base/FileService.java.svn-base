package jp.go.saga.common.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.saga.common.view.bean.FileModel;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String uploadFile(Map<String,Object> paramMap, MultipartFile[] file) throws Throwable;
	public Map<String,Object> uploadFileOne(Map<String,Object> paramMap, MultipartFile file) throws Throwable;
	public String scormFileUpload(Map<String,Object> paramMap,File file) throws Throwable;
	public Map<String, Object> procUnzip(Map<String,Object> paramMap, MultipartFile file_zip) throws Throwable;
	public File getAttachFile(String FILE_ID) throws Throwable;
	public FileModel getAttachFileByAttaFileName(String attaFileName) throws Throwable;
	public int deleteAttachFileByAttaFileName(Map<String,Object> paramMap) throws Throwable;
	public int deleteAttachFileById(String attaFileId) throws Throwable;
	public List<?> selectAttachFileList(Map<String,Object> paramMap) throws Throwable;
	public Map<String,Object> selectAttachFileMap(Map<String,Object> paramMap) throws Throwable;
// 2015.01.06 jungsr delete
//	public FileModel getAttachFileOnlinetestScorm(Map<String,Object> paramMap) throws Throwable;
// 2015.01.06 jungsr delete


	// Byte[]를 파일로 저장하기 (엑셀의 첨부된 그림을 저장하기 위한 용도)
	public Map<String,Object> uploadFileForByte(Map<String,Object> paramMap, byte[] data) throws Throwable;
	// 교육교재의 동영상
	public String uploadFileForVOD(Map<String, Object> paramMap) throws Throwable;
	public Object updateOriginalFile(Map<String,Object> paramMap) throws Throwable;

	public List<?> selectMonthFileList(Map<String,Object> paramMap) throws Throwable;
	public List<HashMap> selectFileList(Map<String,Object> paramMap) throws Throwable;
	public int updateFilePath(Map<String,Object> paramMap) throws Throwable;


}