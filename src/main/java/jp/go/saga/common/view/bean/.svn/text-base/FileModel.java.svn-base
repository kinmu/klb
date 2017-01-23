package jp.go.saga.common.view.bean;

import java.io.File;
import java.io.FileInputStream;

public class FileModel {
	File file;
	FileInputStream inputStream;
	String orgFileName;
	
	public FileModel(File file){
		this.file = file;
		this.orgFileName = file.getName();
	}
	public FileModel(File file, String orgFileName) {
		this.file = file;
		this.orgFileName = orgFileName;
	}
	public FileModel(FileInputStream inputStream, String orgFileName) {
		this.inputStream = inputStream;
		this.orgFileName = orgFileName;
	}	
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public FileInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(FileInputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
}