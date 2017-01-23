package jp.go.saga.io;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * CommonsMultipartResolverとjquery.form.jsの交換性問題を解決
 * @author Donguk, YOON
 *
 */

public class ModifiedCommonsMultipartResolver extends CommonsMultipartResolver{

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
		 try {
			 return super.resolveMultipart(request);
		}
		catch (MaxUploadSizeExceededException e) {
			//ファイルサイズ超過エラーの場合でもエラーメッセージ表示させるためrequestを返し、controllerを呼び出す
			request.setAttribute("exception", e);
			return new DefaultMultipartHttpServletRequest(request, new LinkedMultiValueMap(), new HashMap(), new HashMap());
		}
	}

	@Override
	protected MultipartParsingResult parseFileItems(List<FileItem> fileItems, String encoding) {
		MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<String, MultipartFile>();
		Map<String, String[]> multipartParameters = new HashMap<String, String[]>();
		Map<String, String> multipartParameterContentTypes = new HashMap<String, String>();

		// Extract multipart files and multipart parameters.
		for (FileItem fileItem : fileItems) {
			/*
			 * 20130114 Donguk, YOON
			 * "FILE".equals(fileItem.getFieldName()) == false 追加
			 * jquery.form.jsにてFILEフィールドが空の場合、isFormFieldでファイル存在有無が把握できない為、
			 * フィールド名がFILEの場合は必ずMultipart取り扱いする。
			 */
			if (fileItem.isFormField() && "FILE".equals(fileItem.getFieldName().substring(0, 4)) == false) {
				String value;
				String partEncoding = determineEncoding(fileItem.getContentType(), encoding);
				if (partEncoding != null) {
					try {
						value = fileItem.getString(partEncoding);
					}
					catch (UnsupportedEncodingException ex) {
						if (logger.isWarnEnabled()) {
							logger.warn("Could not decode multipart item '" + fileItem.getFieldName() +
									"' with encoding '" + partEncoding + "': using platform default");
						}
						value = fileItem.getString();
					}
				}
				else {
					value = fileItem.getString();
				}
				String[] curParam = multipartParameters.get(fileItem.getFieldName());
				if (curParam == null) {
					// simple form field
					multipartParameters.put(fileItem.getFieldName(), new String[] {value});
				}
				else {
					// array of simple form fields
					String[] newParam = StringUtils.addStringToArray(curParam, value);
					multipartParameters.put(fileItem.getFieldName(), newParam);
				}
				multipartParameterContentTypes.put(fileItem.getFieldName(), fileItem.getContentType());
			}
			else {
				// multipart file field
				CommonsMultipartFile file = new CommonsMultipartFile(fileItem);
				multipartFiles.add(file.getName(), file);
				if (logger.isDebugEnabled()) {
					logger.debug("Found multipart file [" + file.getName() + "] of size " + file.getSize() +
							" bytes with original filename [" + file.getOriginalFilename() + "], stored " +
							file.getStorageDescription());
				}
			}
		}
		return new MultipartParsingResult(multipartFiles, multipartParameters, multipartParameterContentTypes);
	}

	/**
	 * CommonsFileUploadSupport内のprivate method
	 * @param contentTypeHeader
	 * @param defaultEncoding
	 * @return
	 */
	private String determineEncoding(String contentTypeHeader, String defaultEncoding) {
		if (!StringUtils.hasText(contentTypeHeader)) {
			return defaultEncoding;
		}
		MediaType contentType = MediaType.parseMediaType(contentTypeHeader);
		Charset charset = contentType.getCharSet();
		return (charset != null ? charset.name() : defaultEncoding);
	}

}
