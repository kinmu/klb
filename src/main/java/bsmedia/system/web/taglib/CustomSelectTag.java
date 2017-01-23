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
public class CustomSelectTag extends HtmlAttributesSupport {

	private static final long serialVersionUID = 5002459873278169113L;

	private String template;

	private Object items;
	private String itemValue;
	private String itemLabel;	
	private String selectedValue;
	private String defaultValue;
	private String defaultLabel;

	

	
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
		
		model.put("bodyContent",getBodyContent().getString());
		model.put("name",getName());
		model.put("items",items);
		model.put("itemValue",getItemValue());
		model.put("itemLabel",getItemLabel());
		model.put("selectedValue",selectedValue);
		
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
	 * @return Returns the templateLocation.
	 */
	public String getTemplate() {

		if (template == null) {
			return ApplicationProperty.get("page.template.location") + "selectTag.vm";
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

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}


	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	

}
