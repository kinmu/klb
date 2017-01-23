/**
 * Copyright(c) 2010 by SkySoft
 * Create on 2010. 9. 24.
 */
package bsmedia.system.util;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mindeng77
 */
public class HttpUtil {

	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * Convenience method to set a cookie
	 *
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		if (log.isDebugEnabled()) {
			log.debug("Setting cookie '" + name + "' on path '" + path + "'");
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(true);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30); // 30 days

		response.addCookie(cookie);
	}

	/**
	 * Convenience method to get a cookie by name
	 *
	 * @param request
	 *            the current request
	 * @param name
	 *            the name of the cookie to find
	 *
	 * @return the cookie (if found), null if not found
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];

			if (thisCookie.getName().equals(name)) {
				// cookies with no value do me no good!
				if (!thisCookie.getValue().equals("")) {
					returnCookie = thisCookie;

					break;
				}
			}
		}

		return returnCookie;
	}

	/**
	 * Convenience method for deleting a cookie by name
	 *
	 * @param response
	 *            the current web response
	 * @param cookie
	 *            the cookie to delete
	 * @param path
	 *            the path on which the cookie was set (i.e. /appfuse)
	 */
	public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path) {
		if (cookie != null) {
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	/**
	 * Convenience method to get the application's URL based on request
	 * variables.
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}

	public static String createParameter(HttpServletRequest request, String[] excludedParameters) {
		StringBuffer sb = new StringBuffer();

		Enumeration<?> enums = request.getParameterNames();

		while (enums.hasMoreElements()) {
			String paramName = (String) enums.nextElement();

			if (contains(excludedParameters, paramName) != true) {
				String[] strs = request.getParameterValues(paramName);
				for(int i=0; i<strs.length; i++){
					sb.append(paramName);
					sb.append("=");
					sb.append(strs[i]);
					sb.append("&");
				}
			}
		}
		if(sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
			sb.deleteCharAt(sb.length()  - 1);
		}

		return sb.toString();
	}

	private static boolean contains(String[] array, String str) {

		for (int i = 0; i < array.length; i++) {
			String string = array[i];

			if (string.equals(str)) {
				return true;
			}
		}

		return false;
	}
}
