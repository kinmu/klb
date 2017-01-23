/*
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package bsmedia.system.web.taglib;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import bsmedia.system.config.ApplicationProperty;


/**
 * Tag example's following : <app:paging name="USERS" action="/user/getuserlist"/>
 * @author
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
@SuppressWarnings("serial")
public class CustomTextViewTag extends HtmlAttributesSupport {


	private String template;

	private String maxRow;

	
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}
	
	@Override
	public int doEndTag() throws JspException{
		
		try {
			JspWriter out = pageContext.getOut();
			
			String result = generate();
			out.println(result);
		} catch (Exception e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	public String generate() {


		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("bodyContent",getModifiedBodyContent());
		model.put("name",getName());

		StringWriter writer = new StringWriter();

		VelocityEngine velocityEngine = (VelocityEngine) WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext()).getBean("velocityEngine");

		try {
			VelocityEngineUtils.mergeTemplate(velocityEngine, getTemplate(), model, writer);
		} catch (VelocityException e) {
			e.printStackTrace();
		}

		return writer.toString();
	}
	
	public String getModifiedBodyContent(){
		String bodyContent = getBodyContent().getString();
		
		if(StringUtils.isEmpty(bodyContent)){
			return "";
		}
		
		bodyContent = bodyContent.replaceAll("(\\r)?(\\n)(\\r)?", "<br/>").replaceAll("\\s", "&nbsp;");
		
		if(StringUtils.isEmpty(getMaxRow())){
			return bodyContent;
		}
		
		int maxRow = Integer.parseInt(getMaxRow());
		String[] tmp = bodyContent.split("<br/>");
		
		
		if(tmp.length > maxRow){
			StringBuilder result = new StringBuilder();
			for(int i = 0 ; i < maxRow ; i++){
				result.append(tmp[i]).append("<br/>");
			}			
			result.append(".......");
			
			return result.toString();
			
		} else {
			
			return bodyContent;
			
		}		
	}


	/**
	 * @return Returns the templateLocation.
	 */
	public String getTemplate() {

		if (template == null) {
			return ApplicationProperty.get("page.template.location") + "textViewtTag.vm";
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

	public String getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(String maxRow) {
		this.maxRow = maxRow;
	}	

}
