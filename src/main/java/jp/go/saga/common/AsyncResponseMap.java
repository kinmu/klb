package jp.go.saga.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;

import jp.go.saga.common.message.MessageProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import bsmedia.system.config.ApplicationProperty;

@SuppressWarnings({"unchecked","rawtypes", "serial"})
public class AsyncResponseMap extends HashMap{
	
	@Autowired
	ServletContext ctx;
	@Autowired
	MessageProperty message;
	
	public AsyncResponseMap(){
		super();
	}

	/**
	 * @param urlForRedirect 保存/修正/削除 などの作業の後、リダイレクトされるURL
	 * @return ASyncResponseMap 
	 */
	public AsyncResponseMap setUrl(String urlForRedirect){
		Assert.notNull(urlForRedirect);
		try{
			//super.put(Constants.URL_FOR_REDIRECT, ctx.getContextPath() +"/" + urlForRedirect.replaceAll("^/", ""));
			System.out.println("#################sso.url.root##################");
			System.out.println(ApplicationProperty.get("sso.url.root"));
			System.out.println("###############################################");
			super.put(Constants.URL_FOR_REDIRECT, ApplicationProperty.get("sso.url.root") +"/" + urlForRedirect.replaceAll("^/", ""));
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
		return this;
	}
	
	/**
	 * @param urlForRedirect 保存/修正/削除 などの作業の後、リダイレクトされるURL
	 * @return ASyncResponseMap 
	 */
	public AsyncResponseMap setUrlIsParam(String urlForRedirect, @RequestParam Map<String, Object> paramMap){
		Assert.notNull(urlForRedirect);
		try{
			//super.put(Constants.URL_FOR_REDIRECT, ctx.getContextPath() +"/" + urlForRedirect.replaceAll("^/", ""));
			System.out.println("#################sso.url.root##################");
			System.out.println(ApplicationProperty.get("sso.url.root"));
			System.out.println("###############################################");
			super.put(Constants.URL_FOR_REDIRECT, ApplicationProperty.get("sso.url.root") +"/" + urlForRedirect.replaceAll("^/", ""));
			
			
			// Controller에서 Map형식의 파라미터를 가지고 와서 HashMap에 넣어준다.
			Iterator<String> iterator = paramMap.keySet().iterator();	
		    while (iterator.hasNext()) {
		        String key = (String) iterator.next();
//		        System.out.println("paramMap "+key+" : "+paramMap.get(key));
		        super.put(key,paramMap.get(key));
		    }	
		    
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
		return this;
	}
	
	public AsyncResponseMap setSuccessFlag(boolean success){
		super.put(Constants.SUCCESS_FLAG, success);
		return this;
	};
	
	/**
	 * 
	 * @param messageForAlert リダイレクトされる前に表示されるメッセージ
	 * @return ASyncResponseMap
	 */
	public AsyncResponseMap setMessage(String messageForAlert){
		Assert.notNull(messageForAlert);
		super.put(Constants.MSG_FOR_ALERT, messageForAlert);
		return this;
	}
	/**
	 * 保存完了メッセージ設定
	 * @return
	 */
	public AsyncResponseMap setSaveOkMessage(){
		super.put(Constants.MSG_FOR_ALERT,message.getMessage(Constants.saveOk) );
		return this;
	}
	/**
	 * 修正 完了メッセージ設定
	 * @return
	 */
	public AsyncResponseMap setModifyOkMessage(){
		super.put(Constants.MSG_FOR_ALERT,message.getMessage(Constants.modifyOk) );
		return this;
	}
	/**
	 * 削除 完了メッセージ設定
	 * @return
	 */
	public AsyncResponseMap setDeleteOkMessage(){
		super.put(Constants.MSG_FOR_ALERT,message.getMessage(Constants.deleteOk) );
		return this;
	}
	
	public AsyncResponseMap setErrorMessage(){
		super.put(Constants.MSG_FOR_ALERT,message.getMessage(Constants.error) );
		return this;
	}
}
