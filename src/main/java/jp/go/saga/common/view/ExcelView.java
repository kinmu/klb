package jp.go.saga.common.view;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.saga.common.Constants;
import jp.go.saga.common.view.bean.JXlsBean;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 *  
 * @author Donguk, YOON
 *
 */
public class ExcelView extends AbstractView {

    @Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JXlsBean jXlsBean = (JXlsBean)model.get(Constants.JXLS_RESULT);
    	String templateFileName = jXlsBean.getTemplateFileName();
    	String downloadFileName = jXlsBean.getDownloadFileName();
    	Map<String,Object> resultMap = jXlsBean.getResultMap();
    	String templeteLocation = jXlsBean.getTempleteLocation();

    	
        InputStream template = Thread.currentThread().getContextClassLoader().getResourceAsStream(templeteLocation + "/" + templateFileName);
        byte[] result = generateOutput(template, resultMap);

        response.setContentType("application/vnd.ms-excel");
        response.setContentLength(result.length);
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control","no-cache,no-store");
		
		String userAgent = request.getHeader("User-Agent");
		
		if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5
	      response.setHeader("Content-Disposition", "filename="+URLEncoder.encode(downloadFileName, "UTF-8") + ";");
	    } else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE
	      response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(downloadFileName, "UTF-8") + ";");
	    } else if (userAgent.indexOf("Trident") > -1) {		// IE11 
	    	response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(downloadFileName, "UTF-8").replaceAll("\\+", "%20") + ";");
	    } else { // etc browser
	      response.setHeader("Content-Disposition", "attachment; filename="+new String(downloadFileName.getBytes("UTF-8"), "latin1") + ";");
	    }

        OutputStream out = response.getOutputStream();
        FileCopyUtils.copy(result,out);
        out.close();
    }

    private byte[] generateOutput(InputStream template, Map<String,Object> resultMap) throws Exception {
        XLSTransformer transformer = new XLSTransformer();
        HSSFWorkbook resultWorkbook = (HSSFWorkbook) transformer.transformXLS(template, resultMap);
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        resultWorkbook.write(os);

        return os.toByteArray();
    }
}
