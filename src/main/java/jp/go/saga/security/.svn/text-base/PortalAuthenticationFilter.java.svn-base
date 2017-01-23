package jp.go.saga.security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jp.go.saga.common.Constants;
import jp.go.saga.common.message.MessageProperty;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import bsmedia.system.config.ApplicationProperty;

public class PortalAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
	
	@Autowired
	MessageProperty messageProperty;

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

		CommonUser user = null;
		
		try {

			Map<String, Object> principalMap = getPrincipalMap(request);

			if (MapUtils.getString(principalMap, Constants.USER_ID) == null) {
				return null;
			}

			Set<GrantedAuthority> tmp = new HashSet<GrantedAuthority>();
			user = new CommonUser(MapUtils.getString(principalMap, Constants.USER_ID), tmp, principalMap, null);

			logWrite(request, "Y");
			
		} catch (Exception e) {

			logWrite(request, "N");
			
			throw new CredentialException(e.getMessage());
		} 
		
		return user;
	}
	
	/**
	 * FORM LOGIN
	 * @param request
	 * @return
	 */
	private Map<String,Object> getPrincipalMap(HttpServletRequest request){
		Map<String, Object> principal = new HashMap<String, Object>();

		logger.debug("################################################################################");
		logger.debug("# SAGA PROJECT LOGIN LOGIC START 1111111111111111");
		logger.debug("# ");		
		logger.debug("# * ENCODING INFO");
		logger.debug("# ");
		logger.debug("# file.encoding : " + System.getProperty("file.encoding"));			
		logger.debug("# file.client.encoding : " + System.getProperty("file.client.encoding"));			
		logger.debug("# client.encoding.override : " + System.getProperty("client.encoding.override"));			
		logger.debug("# ");
		logger.debug("# * HEADER INFO");
		logger.debug("# ");
		
		//# for debug
		Enumeration<?> e = request.getHeaderNames();
		while(e.hasMoreElements()) {
			
			String temp = (String)e.nextElement();
			
			logger.debug("# " + temp + " = " + request.getHeader(temp));
		}

		//String userId = principal.getName();
		String userId = request.getHeader(Constants.HTTP_UID);
		String certId = request.getHeader(Constants.SSL_CLIENT_S_DN_CN);
		String certRst = request.getHeader(Constants.SSL_CLIENT_VERIFY);
			
		logger.debug("# ");
		logger.debug("# * DEBUG MODE 11 : " + ApplicationProperty.get("system.debug.mode"));
		logger.debug("# ");
				
		// TODO テストの為、ダミー設定
		if("true".equals(ApplicationProperty.get("system.debug.mode"))) {

			logger.debug("# * SET TEST CODE 11");

			userId = request.getParameter("j_username");
			certId = ApplicationProperty.get("test_ssl_client_s_dn_cn");
			certRst = ApplicationProperty.get("test_ssl_client_verify");
			
			principal.put(Constants.USER_ID, userId);
			principal.put(Constants.HTTP_IW_ROUTE, ApplicationProperty.get("test_http_iw_route"));
			//証明書の認証結果
			principal.put(Constants.SSL_CLIENT_VERIFY, certRst);
			//証明書ID
			principal.put(Constants.SSL_CLIENT_S_DN_CN, userId);
			principal.put(Constants.HTTP_IW_MAILPASS, ApplicationProperty.get("test_http_iw_mailpass"));

			
			principal.put(Constants.IW_SSL_CLIENT_I_DN, "TEST_IW_SSL_CLIENT_I_DN");
			principal.put(Constants.IW_SSL_CLIENT_I_DN_CN, "TEST_IW_SSL_CLIENT_I_DN_CN");
			principal.put(Constants.IW_SSL_CLIENT_I_DN_O, "TEST_IW_SSL_CLIENT_I_DN_O");
			principal.put(Constants.IW_SSL_CLIENT_S_DN, "TEST_IW_SSL_CLIENT_S_DN");			

		} else {
			
			logger.debug("# * SET SSO CODE 11 ");
			
			userId = request.getHeader(Constants.HTTP_UID);
			
			// ヘッダ情報設定
			principal.put(Constants.USER_ID, request.getHeader(Constants.HTTP_UID));
			principal.put(Constants.HTTP_IW_ROUTE, request.getHeader(Constants.HTTP_IW_ROUTE));
			principal.put(Constants.SSL_CLIENT_VERIFY, certRst);
			principal.put(Constants.SSL_CLIENT_S_DN_CN, request.getHeader(Constants.SSL_CLIENT_S_DN_CN));
			principal.put(Constants.HTTP_IW_MAILPASS, request.getHeader(Constants.HTTP_IW_MAILPASS));
			
			
			principal.put(Constants.IW_SSL_CLIENT_I_DN, request.getHeader(Constants.IW_SSL_CLIENT_I_DN));
			principal.put(Constants.IW_SSL_CLIENT_I_DN_CN, request.getHeader(Constants.IW_SSL_CLIENT_I_DN_CN));
			principal.put(Constants.IW_SSL_CLIENT_I_DN_O, request.getHeader(Constants.IW_SSL_CLIENT_I_DN_O));
			principal.put(Constants.IW_SSL_CLIENT_S_DN, request.getHeader(Constants.IW_SSL_CLIENT_S_DN));
		}
		
		// ユーザーIDと証明書IDを比較し、お互い異なる場合、エラー処理する。
		logger.debug("# ");
		logger.debug("# * DEBUG MODE 111 : " + "certId=" + certId+", userId="+userId);
		logger.debug("# ");
		if(userId != null) {
			
			if(StringUtils.equals("SUCCESS", certRst)) {

				if(!StringUtils.equals(userId, certId)) {
					throw new CertException(messageProperty.getMessage("F010"));
				}
			}
		}

		return principal;
	}	

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return null;
	}

	private void logWrite(HttpServletRequest request, String rst) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		String currDate = sdf.format(new Date());

		String ssoInfo = String
				.format("<[%s],[%s],[%s],[%s=%s],[%s=%s],[%s=%s],[%s=%s],[%s=%s],[%s=%s],[%s=%s],[%s=%s],[%s=%s]>",
						currDate, request.getHeader(Constants.HTTP_UID),
						rst,
						Constants.HTTP_UID,
						request.getHeader(Constants.HTTP_UID),
						Constants.HTTP_IW_ROUTE,
						request.getHeader(Constants.HTTP_IW_ROUTE),
						Constants.IW_SSL_CLIENT_I_DN,
						request.getHeader(Constants.IW_SSL_CLIENT_I_DN),
						Constants.IW_SSL_CLIENT_I_DN_CN,
						request.getHeader(Constants.IW_SSL_CLIENT_I_DN_CN),
						Constants.IW_SSL_CLIENT_I_DN_O,
						request.getHeader(Constants.IW_SSL_CLIENT_I_DN_O),
						Constants.IW_SSL_CLIENT_S_DN,
						request.getHeader(Constants.IW_SSL_CLIENT_S_DN),
						Constants.SSL_CLIENT_S_DN_CN,
						request.getHeader(Constants.SSL_CLIENT_S_DN_CN),
						Constants.SSL_CLIENT_VERIFY,
						request.getHeader(Constants.SSL_CLIENT_VERIFY),
						Constants.HTTP_IW_MAILPASS,
						request.getHeader(Constants.HTTP_IW_MAILPASS));

		BufferedWriter bufferWriter = null;

		try {
			String filename = ApplicationProperty.get("log.path") + "/sso_header_" + currDate.substring(0, 8) + ".log";
			
			boolean newFileYn = false;
			
			File f = new File(filename);
			if(f.isFile()) {
				newFileYn = true;
			}
			
			bufferWriter = new BufferedWriter(new FileWriter(filename, newFileYn));

			bufferWriter.write(ssoInfo);
			bufferWriter.newLine();
			bufferWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (bufferWriter != null) {

				try {
					bufferWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}