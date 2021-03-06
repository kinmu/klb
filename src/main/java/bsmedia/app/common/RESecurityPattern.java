package bsmedia.app.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.regexp.RE;

import bsmedia.system.util.StringUtil;

/****************************************************
 * <PRE>
 * @Project Name	: KLB
 * @File Name		: RESecurityPattern.java
 * @Comment			: RE 클래스에 생성 시 다음의 패턴을 참조하여 유효성체크를 수행한다.
 * 					  참조: JCF3 유효성체크 적용 가이드.docx
 * @Author			: eunjung
 * @Creation Date	: 2011. 12. 30.오후 2:22:40
 * Copyright ⓒb&s media All Right Reserved
 * </PRE>
 ****************************************************/

public class RESecurityPattern {
	// ----------------------- Common Pattern --------------------
	public static final String DATE_PATTERN_01 = "([\\d{4}|\\d{4}])/(\\d{1,2})/(\\d{1,2})";
	public static final String DATE_PATTERN_02 = "([\\d{2}|\\d{4}])/(\\d{1,2})/(\\d{1,2})";
	public static final String DATE_PATTERN_03 = "([\\d{4}|\\d{4}])-(\\d{1,2})-(\\d{1,2})";
	public static final String DATE_PATTERN_04 = "([\\d{2}|\\d{4}])-(\\d{1,2})-(\\d{1,2})";
	public static final String ALNUM_PATTERN = "([:alnum:])";
	public static final String ALPHA_PATTERN = "([:alpha:])";
	public static final String DIGIT_PATTERN = "([:digit:])";
	public static final String GRAPH_PATTERN = "([:graph:])";
	public static final String LOWER_PATTERN = "([:lower:])";
	public static final String UPPER_PATTERN = "([:upper:])";
	public static final String PRINT_PATTERN = "([:print:])";
	public static final String HEXADECIMAL_DIGITS_PATTERN = "([:xdigit:])";
	public static final String EMAIL_ADDRESS_PATTERN = "(\\w[:print:]@[:alnum:].[:alpha:])";

	// ----------------------- Vulnerability Pattern -----------------
	public static final String CHAR_REPEAT_PATTERN = "[:alnum:]b{2,}[:alnum:]";

	public static final String CROSS_SITE_SCRIPTING_PATTERN = "([:print:]script[:print:])";
	public static final String SQL_INJECTION_PATTERN_01 = "'[:space:](or)|(OR)[:space:][:alnum:]=[:alnum:]";
	public static final String SQL_INJECTION_PATTERN_02 = "\"[:space:](or)|(OR)[:space:]";
	public static final String SERVER_SIDE_INCLUDE_PATTERN = "<!--#[:alnum:]";

	public static final String SPECIAL_CHARACTER_FILTERING_PATTERN = "(['\"<>()&$#@*!~|;%])";
	// ----------------------- ID/PW Pattern ----------------------
	public static final String ALNUM_DIGITCONTOL_PATTERN = "[:alnum:]{5,}[:alnum:]";

	// ----------------------- 확장자 체크 ----------------------
	public static final String NOT_FILE_UPLOAD_EXT_PATTERN = "(\\.exe)$|(\\.msi)$|(\\.cmd)$|(\\.vbs)$|(\\.bat)$|(\\.js)$|(\\.html)$|(\\.htm)$|(\\.c)$|(\\.class)$|(\\.jar)$|(\\.jsp)$|(\\.asp)$|(\\.php)$|(\\.pl)$|(\\.inc)$|(\\.shtml)$|(\\.msc)$";
	public static final String FILE_UPLOAD_EXT_PATTERN = "(\\.ppt)$|(\\.pptx)$|(\\.xls)$|(\\.xlsx)$|(\\.doc)$|(\\.docx)$|(\\.pdf)$|(\\.hwp)$|(\\.jpg)$|(\\.jpeg)$|(\\.gif)$|(\\.png)$|(\\.zip)$|(\\.rar)$|(\\.alz)$";
	public static final String IMG_FILE_UPLOAD_EXT_PATTERN = "(\\.jpg)$|(\\.jpeg)$|(\\.gif)$|(\\.GIF)$|(\\.png)$|(\\.PNG)$";


	//우리가 사용할것은 여기부터 ----------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {
		String[] input = { "<script>alert(document.cookie);</script>",
							"<script>alert('xss취약점발견');</script>",
							">'><%00script>alert('Watchfire XSS Test Successful')</script>",
							"<!--#echo var='document_url'-->",
							"javascript:alert(document.cookie)" };	//체크할 값

		boolean crossSiteScripting = RESecurityPattern.validateCrossSiteScripting(input); //위 정의된 정규식 패턴 사용법 예제
		boolean serverSideInclude = RESecurityPattern.validateServerSideInclude(input); //Server Side Include 검증
		System.out.println("crossSiteScripting=="+crossSiteScripting+"\n");
		System.out.println("serverSideInclude==="+serverSideInclude+"\n");
	}

	/**
	 * 메소드 설명 : 위 정의된 정규식 패턴 사용법 예제
	 * 등록된 패턴을 기반으로 RESecurityValidationUtils 클래스에 함수를 정의한다.
	 * Cross Site Scripting 검증
	 * @param input
	 * @return
	 */
	public final static boolean validateCrossSiteScripting(String[] input) {
		boolean result = false;

		if(input != null){
			RE validation_pattern = new RE(RESecurityPattern.CROSS_SITE_SCRIPTING_PATTERN);
			System.out.println("--------------- Cross Site Scripting ----------------");

			for (int i = 0; i < input.length; i++) {
				String temp = input[i];
//				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));

				if(temp.indexOf("\" onMouseOver") >-1) {
					result = true;
				}

				if(temp.indexOf("onMouseOver=") >-1) {
					result = true;
				}

				if(temp.indexOf("';") >-1) {
					result = true;
				}

				if(temp.indexOf(";alert(") >-1) {
					result = true;
				}

				if(temp.indexOf("<script>") >-1) {
					result = true;
				}

				if(!result)
				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));

//				System.out.println("result: " + result+"\n");

				if (result)
					return true;
			}
		}
		return result;
	}

	/**
	 * METHOD Comment : SQL Injection 검증
	 * @param input
	 * @return
	 */
	public final static boolean validateSQLInjection(String[] input) {
		boolean result = false;
		if(input != null){
//			RE validation_pattern = new RE(RESecurityPattern.SQL_INJECTION_PATTERN_01);
			System.out.println("--------------- SQL Injection 01 ----------------");

			for (int i = 0; i < input.length; i++) {
				String temp = input[i];
//				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));

				if(temp.indexOf("AND 1=1") >-1) {
					result = true;
				}

				if(temp.indexOf("AND 1=2") >-1) {
					result = true;
				}

				if(temp.indexOf("AND '1'=") >-1) {
					result = true;
				}

				if(temp.indexOf("AND \"1\"=") >-1) {
					result = true;
				}

				if(temp.indexOf("OR 1=1") >-1) {
					result = true;
				}

				if(temp.indexOf("OR '1'=") >-1) {
					result = true;
				}

				if(temp.indexOf("OR \"1\"=") >-1) {
					result = true;
				}

				if(temp.indexOf("1=1--") >-1) {
					result = true;
				}

				if(temp.indexOf("1=1 --") >-1) {
					result = true;
				}

				if(temp.indexOf("1=1#") >-1) {
					result = true;
				}

				if(temp.indexOf("1=1 #") >-1) {
					result = true;
				}

				if(temp.indexOf("'1'='1' --") >-1) {
					result = true;
				}

				if(temp.indexOf("\"1\"=\"1\" --") >-1) {
					result = true;
				}

				if(!result){
					// or 1=1 または OR 1=1の場合、チェックする
					 Pattern p = Pattern.compile("(?i).*OR[\\s]+[\\w]+[\\s]*=[\\s]*[\\w]+.*");
					 Matcher m = p.matcher(StringUtil.replaceInjectionAttack(temp));
					 result = m.matches();
				}

				if (result)
					return true;
			}

			/*
			validation_pattern = new RE(RESecurityPattern.SQL_INJECTION_PATTERN_02);
			result = false;
			System.out.println("--------------- SQL Injection 02 ----------------"+"\n");

			for (int i = 0; i < input.length; i++) {
				String temp = input[i];
				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));

//				System.out.println("result: " + result+"\n");

				if (result)
					return true;
			}
			*/
		}
		return result;
	}

	/**
	 * METHOD Comment : Server Side Include 검증
	 * @param input
	 * @return
	 */
	public final static boolean validateServerSideInclude(String[] input) {
		boolean result = false;
		if(input != null){
			RE validation_pattern = new RE(RESecurityPattern.SERVER_SIDE_INCLUDE_PATTERN);
			System.out.println("--------------- Server Side Include ----------------"+"\n");

			for (int i = 0; i < input.length; i++) {
				String temp = input[i];
				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));

				System.out.println("result: " + result+"\n");

				if (result)
					return true;
			}
		}
		return result;
	}

	/**
	 * METHOD Comment : 파일 확장자 검증
	 * @param input
	 * @return
	 */
	public final static boolean validateNotFileUploadExtPattern(String[] input) {
		boolean result = false;
		if(input != null){
			RE validation_pattern = new RE(RESecurityPattern.NOT_FILE_UPLOAD_EXT_PATTERN);
			System.out.println("--------------- NOT_FILE_UPLOAD_EXT_PATTERN ----------------"+"\n");

			for (int i = 0; i < input.length; i++) {
				String temp = input[i];
				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));

				System.out.println("result: " + result+"\n");

				if (result)
					return true;
			}
		}
		return result;
	}

	/**
	 * METHOD Comment : 파일 확장자 검증
	 * @param input
	 * @return
	 */
	public final static boolean validateFileUploadExtPattern(String[] input) {
		boolean result = false;
		if(input != null){
			RE validation_pattern = new RE(RESecurityPattern.FILE_UPLOAD_EXT_PATTERN);
			System.out.println("--------------- FILE_UPLOAD_EXT_PATTERN ----------------");

			for (int i = 0; i < input.length; i++) {
				String temp = input[i];
				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));
				System.out.println(temp + " result: " + result);
			}
		}
		return result;
	}


	/**
	 * METHOD Comment : 파일 확장자 검증
	 * @param input
	 * @return
	 */
	public final static boolean validateImgFileUploadExtPattern(String[] input) {
		boolean result = false;
		if(input != null){
			RE validation_pattern = new RE(RESecurityPattern.IMG_FILE_UPLOAD_EXT_PATTERN);
			System.out.println("--------------- IMG_FILE_UPLOAD_EXT_PATTERN ----------------");

			for (int i = 0; i < input.length; i++) {
				String temp = input[i];
				result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));
				System.out.println(temp + " result: " + result);
			}
		}
		return result;
	}
	/**
	 * METHOD Comment : 이메일 정규식
	 * @param useStr
	 * @return
	 */
	public final static boolean isValidEmail(String input){
		boolean result = false;
		if(input != null){
			RE validation_pattern = new RE(RESecurityPattern.EMAIL_ADDRESS_PATTERN);
			result = validation_pattern.match(StringUtil.replaceInjectionAttack(input));
			System.out.println("--------------- EMAIL_ADDRESS_PATTERN ----------------");
			System.out.println("result: " + result+"\n");

			if (result)
				return true;
		}

		return result;
	}

	/**
	 * METHOD Comment : ID와 PW가 동일한지 검증
	 * @param id
	 * @param pw
	 * @return
	 */
	public final static boolean validateSameIdPwComparison(String id, String pw) {
		boolean result = id.equals(pw);
		System.out.println("--------------- id와 pw patten check ----------------");
		System.out.println("result: " + result+"\n");
		return result;
	}

	/**
	 * METHOD Comment : PW Pattern Check
	 * @param input
	 * @return
	 */
	public final static boolean validateAlnumDigitcontolPattern(String input) {
		RE validation_pattern = new RE(RESecurityPattern.ALNUM_DIGITCONTOL_PATTERN);
		boolean result = false;
		System.out.println("--------------- PW Pattern Check ----------------");
		result = validation_pattern.match(StringUtil.replaceInjectionAttack(input));
		System.out.println("result: " + result+"\n");

		if (result)
			return true;
		return result;
	}

	//----------------------------------------------------------------------------------------------------------여기까지

	//여기부턴 참고
	/**
	 * METHOD Comment : 공통 특수문자 포함 문자열 검증
	 * @param input
	 * @return
	 */
	public final static boolean validateSpecialCharacter(String[] input) {
		RE validation_pattern = new RE(RESecurityPattern.SPECIAL_CHARACTER_FILTERING_PATTERN);
		boolean result = false;
		System.out.println("--------------- Special Character Filtering ----------------"+"\n");

		for (int i = 0; i < input.length; i++) {
			String temp = input[i];
			result = validation_pattern.match(StringUtil.replaceInjectionAttack(temp));

			System.out.println("result: " + result+"\n");

			if (result)
				return true;
		}
		return result;
	}
}
