package bsmedia.system.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Component
public class XmlConverter {
	
	/**
	 * (String)XML -> Map
	 * @param xml 
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> convertXml(String xml) {
        XStream xStream = new XStream(new DomDriver());
        Map<String, Object> xmlMap = (Map<String,Object>) xStream.fromXML(xml);        
        return xmlMap;
    }

	/**
	 * Map -> (String)XML
	 * @param xml 
	 * @return (String)XML
	 */
	public String getXmlString(Map<String,Object> map){
		XStream xStream = new XStream(new DomDriver());
		return xStream.toXML(map);
	}
}