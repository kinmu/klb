package bsmedia.system.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class BaseHRefTag extends TagSupport {

	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer buf = new StringBuffer("<base href=\"");
		buf.append(request.getScheme());
		buf.append("://");
		buf.append(request.getServerName());

		if ("http".equals(request.getScheme()) && (80 == request.getServerPort())) {
			;
		} else if ("https".equals(request.getScheme()) && (443 == request.getServerPort())) {
			;
		} else {
			buf.append(":");
			buf.append(request.getServerPort());
		}

		buf.append(request.getContextPath());
		buf.append("/");
		buf.append("\"");
		buf.append(">");

		JspWriter out = pageContext.getOut();

		try {
			out.write(buf.toString());
		} catch (IOException e) {
			throw new JspException(e.toString());
		}

		return EVAL_BODY_INCLUDE;
	}
}
