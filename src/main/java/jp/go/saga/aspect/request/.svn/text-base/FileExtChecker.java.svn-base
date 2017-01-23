package jp.go.saga.aspect.request;

import javax.xml.bind.ValidationException;

import jp.go.saga.aspect.common.AbstractParameterInterceptor;

import org.springframework.web.multipart.MultipartFile;

import bsmedia.app.common.RESecurityPattern;

/**
 * 添付ファイルの拡張子チェック
 *
 */
public class FileExtChecker extends AbstractParameterInterceptor{

	@Override
	public Object getModifiedArgument(Object argument) throws Exception {
		if (argument instanceof MultipartFile[]) {
			for (MultipartFile s : (MultipartFile[]) argument) {
				if (s != null && !s.isEmpty()) {
					validateFileExtByFileName(s.getName());
				}
			}
		}
		return AbstractParameterInterceptor.IS_NOT_A_TARGET;
	}

	private void validateFileExtByFileName(String value) throws ValidationException {

		// Cross Site Scriptingチェック
		if (RESecurityPattern.validateNotFileUploadExtPattern(new String[]{value})) {
			throw new ValidationException("この拡張子は使えません。");
		}
	}
}
