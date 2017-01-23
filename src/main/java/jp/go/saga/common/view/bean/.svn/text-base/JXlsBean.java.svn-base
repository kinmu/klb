package jp.go.saga.common.view.bean;

import java.util.Map;

public class JXlsBean {
	
	/**
	 * file name for client
	 */
	private String templateFileName;
	/**
	 * results for jxls templete
	 */
	private Map<String,Object> resultMap;
	/**
	 * jxls templete location
	 */
	private String templeteLocation;

	private String downloadFileName;

	/**
	 * 
	 * @param fileName file name for client
	 * @param resultMap results for jxls templete
	 * @param templeteLocation jxls templete location
	 */
	public JXlsBean(String templateFileName,Map<String,Object> resultMap,String templeteLocation, String downloadFileName){
		this.templateFileName = templateFileName;
		this.resultMap = resultMap;
		this.templeteLocation = templeteLocation;
		this.downloadFileName = downloadFileName;
	}
	
	public String getTemplateFileName() {
		return templateFileName;
	}
	public Map<String,Object> getResultMap() {
		return resultMap;
	}
	public String getTempleteLocation() {
		return templeteLocation;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
}