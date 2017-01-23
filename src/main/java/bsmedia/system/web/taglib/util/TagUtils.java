package bsmedia.system.web.taglib.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;


/**
 * Notification !!<br/> This class is copied and modified from
 * org.apache.struts.taglib.TagUtils class of struts-1.2.4 Because of the
 * support the customed jsp tag libraries such as <app:code/>, <app:page/>, ...
 * And This class don't include and support original TagUtils class's methods.
 *
 *
 * @author Administrator
 *
 */

@SuppressWarnings({"rawtypes","unchecked"})
public class TagUtils {

	/**
	 * !! This method is copied from org.apache.struts.taglib.TagUtils class of struts-1.2.4
	 * Maps lowercase JSP scope names to their PageContext integer constant
	 * values.
	 */
	private static final Map scopes = new HashMap();

	/**
	 * !! This method is copied and modified from org.apache.struts.taglib.TagUtils class of struts-1.2.4
	 * Initialize the scope names map and the encode variable with the
	 * Java 1.4 method if available.
	 */
	static {

		scopes.put("page", new Integer(PageContext.PAGE_SCOPE));
		scopes.put("request", new Integer(PageContext.REQUEST_SCOPE));
		scopes.put("session", new Integer(PageContext.SESSION_SCOPE));
		scopes.put("application", new Integer(PageContext.APPLICATION_SCOPE));
	}

	/**
	 * !! This method is copied and modified from org.apache.struts.taglib.TagUtils class of struts-1.2.4
	 * Converts the scope name into its corresponding PageContext constant value.
	 * @param scopeName Can be "page", "request", "session", or "application" in any
	 * case.
	 * @return The constant representing the scope (ie. PageContext.REQUEST_SCOPE).
	 * @throws JspException if the scopeName is not a valid name.
	 */
	public static int getScope(String scopeName) throws JspException {
		Integer scope = (Integer) scopes.get(scopeName.toLowerCase());

		if (scope == null) {
			throw new JspException("Given Scope is not mapped.");
		}

		return scope.intValue();
	}

	/**
	 * !! This method is copied and modified from org.apache.struts.taglib.TagUtils class of struts-1.2.4
	 *
	 * <p>Locate and return the specified bean, from an optionally specified
	 * scope, in the specified page context. If no such bean is found,
	 * return <code>null</code> instead. If an exception is thrown, it will
	 * have already been saved via a call to <code>saveException</code>.</p>
	 *
	 * @param pageContext Page context to be searched
	 * @param name Name of the bean to be retrieved
	 * @param scopeName Scope to be searched (page, request, session, application)
	 *  or <code>null</code> to use <code>findAttribute()</code> instead
	 *
	 * @return JavaBean in the specified page context
	 * @exception JspException if an invalid scope name
	 *  is requested
	 * Use {@link org.apache.struts.taglib.TagUtils#lookup(PageContext,String,String)} instead.
	 */
	public static Object lookup(PageContext pageContext, String name, String scopeName) throws JspException {

		if (scopeName == null) {
			Object obj = pageContext.findAttribute(name);
			return obj;
		}

		try {
			return pageContext.getAttribute(name, getScope(scopeName));

		} catch (JspException e) {
			saveException(pageContext, e);
			throw e;
		}

	}

	/**
	 * !! This method is copied and modified from org.apache.struts.taglib.TagUtils class of struts-1.2.4
	 *
	 * Locate and return the specified property of the specified bean, from
	 * an optionally specified scope, in the specified page context.  If an
	 * exception is thrown, it will have already been saved via a call to
	 * <code>saveException()</code>.
	 *
	 * @param pageContext Page context to be searched
	 * @param name Name of the bean to be retrieved
	 * @param property Name of the property to be retrieved, or
	 *  <code>null</code> to retrieve the bean itself
	 * @param scope Scope to be searched (page, request, session, application)
	 *  or <code>null</code> to use <code>findAttribute()</code> instead
	 * @return property of specified JavaBean
	 *
	 * @exception JspException if an invalid scope name
	 *  is requested
	 * @exception JspException if the specified bean is not found
	 * @exception JspException if accessing this property causes an
	 *  IllegalAccessException, IllegalArgumentException,
	 *  InvocationTargetException, or NoSuchMethodException
	 */
	public static Object lookup(PageContext pageContext, String name, String property, String scope) throws JspException {

		// Look up the requested bean, and return if requested
		Object bean = lookup(pageContext, name, scope);
		if (bean == null) {
			JspException e = null;
			if (scope == null) {
				e = new JspException("Can't find the bean : " + name);
			} else {
				e = new JspException("Can't find the bean ," + name + ", given " + scope + " scope");
			}
			saveException(pageContext, e);
			throw e;
		}

		if (property == null) {
			return bean;
		}

		// Locate and return the specified property
		try {
			return PropertyUtils.getProperty(bean, property);

		} catch (IllegalAccessException e) {
			saveException(pageContext, e);
			throw new JspException(e);

		} catch (IllegalArgumentException e) {
			saveException(pageContext, e);
			throw new JspException(e);

		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t == null) {
				t = e;
			}
			saveException(pageContext, t);
			throw new JspException(e);

		} catch (NoSuchMethodException e) {
			saveException(pageContext, e);
			throw new JspException(e);
		}

	}

	/**
	 * Save the specified exception as a request attribute for later use.
	 *
	 * @param pageContext The PageContext for the current page
	 * @param exception The exception to be saved
	 */
	public static void saveException(PageContext pageContext, Throwable exception) {

		pageContext.setAttribute("jcf.EXCEPTION", exception, PageContext.REQUEST_SCOPE);

	}

	/**
	 * Return the form action converted into a server-relative URL.
	 */
	public static String getActionMappingURL(String action, String module, PageContext pageContext, boolean contextRelative) {

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer value = new StringBuffer(request.getContextPath());

		// Use our servlet mapping, if one is specified

		// This variable is struts 1.x.x's "Globals.SERVLET_KEY"
		String SERVLET_KEY = "org.apache.struts.action.SERVLET_MAPPING";

		String servletMapping = (String) pageContext.getAttribute(SERVLET_KEY, PageContext.APPLICATION_SCOPE);

		if (servletMapping != null) {

			String queryString = null;
			int question = action.indexOf("?");
			if (question >= 0) {
				queryString = action.substring(question);
			}

			String actionMapping = getActionMappingName(action);
			if (servletMapping.startsWith("*.")) {
				value.append(actionMapping);
				value.append(servletMapping.substring(1));

			} else if (servletMapping.endsWith("/*")) {
				value.append(servletMapping.substring(0, servletMapping.length() - 2));
				value.append(actionMapping);

			} else if (servletMapping.equals("/")) {
				value.append(actionMapping);
			}
			if (queryString != null) {
				value.append(queryString);
			}
		}

		// Otherwise, assume extension mapping is in use and extension is
		// already included in the action property
		else {
			if (!action.startsWith("/")) {
				value.append("/");
			}
			value.append(action);
		}

		return value.toString();
	}

	public static String getActionMappingName(String action) {

		String value = action;
		int question = action.indexOf("?");
		if (question >= 0) {
			value = value.substring(0, question);
		}

		int slash = value.lastIndexOf("/");
		int period = value.lastIndexOf(".");
		if ((period >= 0) && (period > slash)) {
			value = value.substring(0, period);
		}

		return value.startsWith("/") ? value : ("/" + value);
	}

}
