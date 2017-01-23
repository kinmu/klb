package bsmedia.system.util;

import java.text.NumberFormat;

/**
* @(#) StringUtil.java
*/
public class StringUtil {

	/**
	 * StringTokens
	 */
	public static String[] splitFullValue(String str,String token)
	{
		return str.split("\\"+token);
	}
	public static String[] StringTokens(String str,String token)
	{
		if(str != null && !str.equals(""))
		{
			java.util.StringTokenizer stz = new java.util.StringTokenizer(str,token);
			String[] data = new String[stz.countTokens()];
			int i=0;
			while (stz.hasMoreTokens())
			{
				data[i] = stz.nextToken();
				i++;
			}
			return data;
		}
		return null;
	}

	/**
	 * 소문자를 대문자로
	 */
	public static String upperEncorder(String token)
	{
		String enToken = "";
		int achar;

		for (int i = 0; i < token.length(); i++) {
			achar = token.charAt(i);
			if (achar >= 'a' && achar <= 'z')
				enToken += (char) ((achar - 'a') + 'A');
			else
				enToken += (char) achar;

		}
		return enToken;
	}

	/**
	 * 대문자를 소문자로
	 */
	public static String upperDecorder(String token) {
		String enToken = "";
		int achar;

		for (int i = 0; i < token.length(); i++) {
			achar = token.charAt(i);
			if (achar >= 'A' && achar <= 'Z')
				enToken += (char) ((achar - 'A') + 'a');
			else
				enToken += (char) achar;

		}
		return enToken;
	}
	/**
	 * isNotNull
	 */
	public static boolean isNotNull(String input[]) {
		boolean result = false;

		for(int i=0; i < input.length; i++){
			String temp = input[i];

			if (temp != null && !temp.equals("null") && !temp.equals(""))
				return true;
		}

		return result;
	}
	/**
	 */
	public static boolean isNull(String input[]) {
		return !isNotNull(input);
	}

	public static boolean isEmpty(Object obj){
		boolean result = false;

		if(obj == null || "".equals(obj)){
			return true;
		}

		return result;
	}


	public static String replace(String str, int sPos, int ePos, String replace)
    {
        StringBuffer result = new StringBuffer(str.substring(0, sPos));
        result.append(replace);
        result.append(str.substring(ePos));
        return result.toString();
    }

	public static String displayComma(String str) {
        if("".equals(str)){
            return str;
        } else if("0".equals(str)) {
            return str;
        } else {
            NumberFormat nf = NumberFormat.getNumberInstance();
            return nf.format(Double.parseDouble(str));
        }
    }

	/**
	 * replaceInjectionAttack
	 * 문자에 포함된 injection 공격문자들을 decimal 코드로 바꾼다.
	 */
	public static String replaceInjectionAttack(String str){
		StringBuffer buffer = new StringBuffer();

		if(str != null)
		{
			char[] charArray = str.toCharArray();
			for(int i = 0 ; i < charArray.length ; i++){
				if(charArray[i] == '<')
				{
					buffer.append("&#60;");
				}
				else if(charArray[i] == '>'){
					buffer.append("&#62;");
				}
				else if(charArray[i] == '"'){
					buffer.append("&#34;");
				}
				else if(charArray[i] == '#'){
					buffer.append("&#35;");
				}
				else if(charArray[i] == '$'){
					buffer.append("&#36;");
				}
				else if(charArray[i] == '%'){
					buffer.append("&#37;");
				}
				else if(charArray[i] == '&'){
					buffer.append("&#38;");
				}
				else if(charArray[i] == '\''){
					buffer.append("&#39;");
				}
				else if(charArray[i] == '('){
					buffer.append("&#40;");
				}
				else if(charArray[i] == ')'){
					buffer.append("&#41;");
				}
				else if(charArray[i] == '-'){
					buffer.append("&#45;");
				}
				else{
					buffer.append(charArray[i]);
				}
			}
		}
		return buffer.toString();
	}
	public static String trim(String str){
		if(str == null){
			return "";
		}
		return str.trim();
	}


	public static String getStr(String str,String defaultStr){
		if(str == null || "".equals(str) || "null".equals(str)){
			return defaultStr;
		}
		return str.trim();
	}

	/**
	 * 文字列内の全角・半角の空白を削除する
	 * @param str
	 * @return 全角・半角空白削除後文字列
	 */
	public static String removeBlank(String str) {
		if (isEmpty(str)) {
			return "";
		} else {
			return str.replaceAll("　", "").replaceAll(" ", "");
		}
	}

	/**
	 * 文字列前後の全角・半角の空白を削除する
	 * @param str
	 * @return 前後の全角・半角空白削除後文字列
	 */
	public static String trimBlank(String str) {
		if (isEmpty(str)) {
			return "";
		} else {
			int start = 0;
			int end = str.length();
			char[] charArray = str.toCharArray();
		    while (start < end && (charArray[start] == ' ' || charArray[start] == '　')) {
		    	start++;
		    }
		    while (start < end && (charArray[end-1] == ' ' || charArray[end-1] == '　')) {
		    	end--;
		    }
		    return (start>0 || end<str.length()) ? str.substring(start,end) : str;
		}
	}


	/**
	 * 頭から指定桁まで文字列を切取
	 * @param str
	 * @param idx 切り取りする桁数
	 * @param removeBlankFlg 文字列の全角・半角空白削除有無
	 * @return
	 */
	public static String cutStr(String str, int idx, boolean removeBlankFlg) {
		if (removeBlankFlg) {
			str = removeBlank(str);
		}
		if (str.length() > idx) {
			return str.substring(0,idx);
		} else {
			return str;
		}
	}

	/**
	 * 指定したサイズを超える場合、指定サイズで切取した後文字「…」を結合して返す
	 * @param str
	 * @param size (切取サイズ)
	 * @return 指定サイズで切取した後文字「…」を結合した文字列
	 */
	public static String concatCutLetter(String str, int size){
		if(str != null && !str.isEmpty() && str.length() > size){
			return str.substring(0, size).concat("…");
		} else {
			return str;
		}
	}

    /**
     * 指定した文字列が全角文字を含んでいるか判断する
     *
     * @param str 対象文字列
     * @return 全角文字が混在する場合、true
     */
	public static boolean isIncludeZenStr(String str){

		// 空白, nullの場合false
		if(str != null && !str.isEmpty()){
			// 全角を含んだ場合、サイズが一致しない
			if ((str.getBytes().length) != str.length()){
				return true;
			}
		}

		return false;
	}



}