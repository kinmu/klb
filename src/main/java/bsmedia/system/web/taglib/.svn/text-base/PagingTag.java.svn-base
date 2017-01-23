/*
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package bsmedia.system.web.taglib;

import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import bsmedia.system.config.ApplicationProperty;
import bsmedia.system.ibatis.PaginatedArrayList;
import bsmedia.system.util.HttpUtil;
import bsmedia.system.web.taglib.util.TagUtils;

/**
 * Tag example's following : <app:paging name="USERS" action="/user/getuserlist"/>
 * @author
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
@SuppressWarnings("serial")
public class PagingTag extends TagSupport {

	private int indexSize = -1;

	private String template;

	private String name;

	private String property;

	private String action;

	private String scope;

	private String type = "";

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doStartTag() throws JspException {

		int page = 1;

		StringBuffer url = new StringBuffer(0);

		try {
			if (TagUtils.lookup(pageContext, name, scope) == null) {
				return SKIP_BODY; // Nothing to output
			}

			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			PaginatedArrayList paginatedList = (PaginatedArrayList) TagUtils.lookup(pageContext, name, scope);

			if (StringUtils.isNotEmpty(request.getParameter("page"))) {
				page = Integer.parseInt(request.getParameter("page"));
			}

			if (StringUtils.isNotEmpty(action)) {
				url.append(TagUtils.getActionMappingURL(action, null, pageContext, false));
			} else {
				//url.append(request.getContextPath());
				url.append(request.getAttribute("javax.servlet.forward.request_uri"));
			}

			String pagingIndex = generate(page, paginatedList.getTotalSize(), paginatedList.getPageSize(), url.toString(), request, type);

			JspWriter out = pageContext.getOut();
			out.println(pagingIndex);
		} catch (Exception e) {
			throw new JspException(e);
		}

		return SKIP_BODY;
	}

	public String generate(int page, int totalSize, int pageSize, String baseURL, HttpServletRequest request, String type) {

		if ((page <= 0) || (pageSize <= 0) || (getIndexSize() <= 0)) {
			return "";
		}

		if (indexSize < 1) {
			indexSize = getIndexSize();
		}

		StringBuffer url = new StringBuffer();

		url.append(baseURL);
		url.append("?");
		String[] excludedParameters = { "page" };
		url.append(HttpUtil.createParameter(request, excludedParameters));
		int totalPage = totalSize / pageSize;

		if ((totalSize % pageSize) > 0) {
			totalPage++;
		}

		if (totalPage <= 0) {
			totalPage = 1;
		}

		int currentPageCount = page / indexSize;

		if ((page % indexSize) > 0) {
			currentPageCount++;
		}

		int lastPageCount = totalPage / indexSize;

		if ((totalPage % indexSize) > 0) {
			lastPageCount++;
		}

		int startPage = ((currentPageCount - 1) * indexSize) + 1;
		int endPage = ((currentPageCount - 1) * indexSize) + indexSize;

		if (endPage > totalPage) {
			endPage = totalPage;
		}

		HashMap<String, Object> model = new HashMap<String, Object>();

		model.put("startPage", new Integer(startPage));
		model.put("page", new Integer(page));
		model.put("endPage", new Integer(endPage));
		model.put("url", url.toString());
		model.put("contextPath", request.getContextPath());
		model.put("indexSize", new Integer(getIndexSize()));
		model.put("currentPageCount", new Integer(currentPageCount));
		model.put("lastPageCount", new Integer(lastPageCount));
		model.put("totalPage", new Integer(totalPage));
		model.put("totalSize", new Integer(totalSize));
		model.put("type", type);

		StringWriter writer = new StringWriter();

		VelocityEngine velocityEngine = (VelocityEngine) WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).getBean("velocityEngine");

		try {
			VelocityEngineUtils.mergeTemplate(velocityEngine, getTemplate(), model, writer);
		} catch (VelocityException e) {
			e.printStackTrace();
		}

		return writer.toString();
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @return
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setProperty(String string) {
		property = string;
	}

	/**
	 * @param string
	 */
	public void setScope(String string) {
		scope = string;
	}

	/**
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param string
	 */
	public void setAction(String string) {
		action = string;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the indexSize.
	 */
	public int getIndexSize() {

		if (indexSize == -1) {
			return ApplicationProperty.getInt("page.index.size");
		}
		return indexSize;
	}

	/**
	 * @param indexSize
	 *            The indexSize to set.
	 */
	public void setIndexSize(int indexSize) {
		this.indexSize = indexSize;
	}

	/**
	 * @return Returns the templateLocation.
	 */
	public String getTemplate() {

		if (template == null) {
			return ApplicationProperty.get("page.template.location") + "paging_index.vm";
		}

		return template;
	}

	/**
	 * @param templateLocation
	 *            The templateLocation to set.
	 */
	public void setTemplate(String template) {
		this.template = template;
	}
}
