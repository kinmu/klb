package jp.go.saga.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.saga.common.view.bean.FileModel;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 添付ファイルダインロード
 * @author Donguk, YOON
 *
 */
public class FileDownloadView extends AbstractView {
	private static final Logger logger = LoggerFactory.getLogger(FileDownloadView.class);

	private static final String CHARSET = "utf-8";

	public FileDownloadView() {
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FileModel fileModel = (FileModel)model.get("FILE_MODEL");
		File file = fileModel.getFile();
		FileInputStream is = fileModel.getInputStream();
		String orgFileName = fileModel.getOrgFileName();

		try{
		MagicMatch mm = Magic.getMagicMatch(orgFileName.getBytes(), false);
			response.setContentType(mm.getMimeType()+"; charset="+CHARSET);
		}catch(MagicMatchNotFoundException me){
			response.setContentType("application/octet-stream; charset="+CHARSET);
		}


		//response.setCharacterEncoding(CHARSET);
		response.setHeader("Content-Transfer-Encoding", "binary");

		String userAgent = request.getHeader("User-Agent");
		// IE9 "MSIE 9.0"
		if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5
	      response.setHeader("Content-Disposition", "filename="+URLEncoder.encode(orgFileName, "UTF-8") + ";");
	    } else if (userAgent != null && userAgent.indexOf("MSIE") > -1) { // MS IE
	    	orgFileName = URLEncoder.encode(orgFileName, "UTF-8");
	    	orgFileName = orgFileName.replaceAll("\\+", "%20");
	    	response.setHeader("Content-Disposition", "attachment; filename="+orgFileName + ";");
	      //response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(orgFileName, "UTF-8") + ";");
	    } else if (userAgent.indexOf("Trident") > -1) {		// IE11
			orgFileName = URLEncoder.encode(orgFileName, "UTF-8").replaceAll("\\+", "%20");
	    	response.setHeader("Content-Disposition", "attachment; filename="+orgFileName + ";");
	    } else { // etc browser
	      response.setHeader("Content-Disposition", "attachment; filename="+new String(orgFileName.getBytes(CHARSET), "latin1") + ";");
	    }

		response.setHeader("Cache-Control","no-cache,no-store");


		OutputStream out = response.getOutputStream();
        FileInputStream fis = is;

        //BufferedInputStream in = null;
        //byte[] buf;

        try
        {
        	/*
        	if(fis == null || (file.getName().indexOf(".pdf") != -1)){
        		// pdfファイルの場合
        		if(file.getName().indexOf(".pdf") != -1){
        			in = new BufferedInputStream(
        					new FileInputStream(file));
        			buf = new byte[in.available()];
        			in.read(buf);
        			out.write(buf);
        			out.flush();

        			in.close();
        			out.close();

        		} else {
        			fis = new FileInputStream(file);
        		}
        	}
        	*/

        	if(fis == null){
        		fis = new FileInputStream(file);
        	}

            FileCopyUtils.copy(fis,out);
        } catch (FileNotFoundException fnfe ) {
        	logger.debug("ファイルが見つかりません(FileNotFoundException) :" + file.getPath());
            // fnfe.printStackTrace();
            throw new FileNotFoundException();

        } catch(IOException ioe) {
        	logger.debug("ダウンロードファイルを作成中、ファイル書き込みエラー :" + file.getPath());
            ioe.printStackTrace();
            throw new IOException();
        }
        finally
        {
            if(fis != null) fis.close();
            //if(in != null) in.close();
            if(out != null) out.close();
            // Cookieを生成、クライアントで削除
			//Cookie cookie = new Cookie("downloaded","downloaded");
			//cookie.setMaxAge(60*60*24);
            //response.addCookie(cookie);
        }
	}
}
