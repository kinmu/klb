/**
 * Project Name : 기능경기시스템
 * Copyright(c) 2010 by SkySoft
 * Create on 2010. 7. 21.
 */
package bsmedia.system.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 계산관련된 유틸
 * @author mindeng77
 */
public class MathUtil {

	/**
	 * 1제곱
	 */
	public static final int POW1 = 1;

	/**
	 * 2제곱
	 */
	public static final int POW2 = 2;

	/**
	 * 3제곱
	 */
	public static final int POW3 = 3;

	/**
	 * 4제곱
	 */
	public static final int POW4 = 4;

	private MathUtil() {
		// 객체 생성 방지
	}

	/**
	 * 수학의 ∑(Sigma) 연산을 처리한다.<br>
	 * 첫째항과 마지막항, 거듭제곱 값이 parameter로 넘어온경우 처리한다.<br>
	 * ex) <li>1^1 +2^1 +3^1 + .....+ 10^1 - getSigma(1,10,MathUtil.POW1) ==> 55</li> <li>1^2 +2^2 +3^2 + .....+ 10^2 - getSigma(1,10,MathUtil.POW2) ==>385</li> <br>
	 *
	 * @param firstTerm : 첫째 항
	 * @param lastTerm : 마지막 항
	 * @param power : 거듭제곱 값
	 * @return int : 계산처리 후 결과값
	 */
	public static int getSigma(int firstTerm, int lastTerm, int power) {
		int intSigmaResult = 0;
		try {
			if (power == POW1) {
				// 거듭제곱이 없는 경우
				intSigmaResult = (lastTerm * (lastTerm + 1)) / 2;
			} else if (power == POW2) {
				// 2 제곱일 경우
				intSigmaResult = ((lastTerm * (lastTerm + 1)) * (2 * lastTerm + 1)) / 6;
			} else if (power == POW3) {
				// 3 제곱일 경우
				intSigmaResult = (int) Math.pow((lastTerm * (lastTerm + 1)) / 2, 2);
			} else if (power == POW4) {
				// 4 제곱일 경우
				intSigmaResult = ((lastTerm * (lastTerm + 1)) * ((2 * lastTerm) + 1) * ((3 * (int) Math.pow(lastTerm, 2)) + (3 * lastTerm) - 1)) / 30;
			}

			// 시작 항이 1보다 큰경우
			if (firstTerm > 1) {

				firstTerm = firstTerm - 1;
				firstTerm = (firstTerm <= 0) ? 1 : firstTerm;

				if (power == POW1) {
					// 거듭제곱이 없는 경우
					intSigmaResult = intSigmaResult - ((firstTerm * (firstTerm + 1)) / 2);
				} else if (power == POW2) {
					// 2 제곱일 경우
					intSigmaResult = intSigmaResult - (((firstTerm * (firstTerm + 1)) * (2 * firstTerm + 1)) / 6);
				} else if (power == POW3) {
					// 3 제곱일 경우
					intSigmaResult = intSigmaResult - ((int) Math.pow((firstTerm * (firstTerm + 1)) / 2, 2));
				} else if (power == POW4) {
					// 4 제곱일 경우
					intSigmaResult = intSigmaResult - (((firstTerm * (firstTerm + 1)) * ((2 * firstTerm) + 1) * ((3 * (int) Math.pow(firstTerm, 2)) + (3 * firstTerm) - 1)) / 30);
				}
			}

		} catch (Exception e) {
			throw new NumberFormatException();
		}

		return intSigmaResult;
	}

	/**
	 * 수학의 ∑(Sigma) 연산을 처리한다.<br>
	 * 첫째항, 마지막항, 공차값이 parameter로 넘어온경우 처리한다.<br>
	 * ex) 1 + 3 + 5 + 7 + 9 - getSigmaDifference(1,10,2) ==> 25
	 *
	 * @param firstTerm : 첫째 항
	 * @param lastTerm : 마지막 항
	 * @param commonDifference : 공차 (등차수열(또는 등차급수)에서 서로 이웃하는 두 항의 차)
	 * @return int : 계산처리 후 결과값
	 */
	public static int getSigmaDifference(int firstTerm, int lastTerm, int commonDifference) {

		int intSigmaResult = 0;
		int intProgression = 0;

		try {
			for (int i = firstTerm; i <= lastTerm; i++) {

				// 수열의 일반항 공식을 이용하여 계산 대상값을 추출한다.
				intProgression = firstTerm + ((i - 1) * commonDifference);

				if (intProgression <= lastTerm) {
					intSigmaResult += intProgression;
				}
			}
		} catch (Exception e) {
			throw new NumberFormatException();
		}

		return intSigmaResult;
	}

	/**
	 * 수학의 ∑(Sigma) 연산을 처리한다.<br>
	 * 첫째항, 마지막항, 공비값이 parameter로 넘어온경우 처리한다.<br>
	 * <br>
	 * ※ 공비<br>
	 * 등비수열(또는 등비급수)에서, 어떤 항의 그 앞의 항에 대한 비<br>
	 * ∑ From k=1 To n a&circ;k = a&circ;1 + a&circ;2 + .... a&circ;n 에서 a값<br>
	 * <br>
	 *
	 * ex) 2^1 + 2^2 + 2^3 + 2^4 - getSigmaRatio(1,4,2) ==> 30<br>
	 *
	 * @param firstTerm : 첫째 항
	 * @param lastTerm : 마지막 항
	 * @param commonRatio : 공비
	 * @return int : 계산처리 후 결과값
	 */
	public static int getSigmaRatio(int firstTerm, int lastTerm, int commonRatio) {

		int intSigmaResult = 0;

		try {
			intSigmaResult = (commonRatio * ((int) Math.pow(commonRatio, lastTerm) - 1)) / (commonRatio - 1);

			if (firstTerm > 1) {
				firstTerm = firstTerm - 1;
				firstTerm = (firstTerm <= 0) ? 1 : firstTerm;
				intSigmaResult = intSigmaResult - ((commonRatio * ((int) Math.pow(commonRatio, firstTerm) - 1)) / (commonRatio - 1));
			}

		} catch (Exception e) {
			throw new NumberFormatException();
		}

		return intSigmaResult;
	}

	/**
	 * 입력받은 Data를 원하는 자리수 까지 절사 하여 String 형으로 리턴한다<br>
	 * ex) <li>getFloor(&quot;1234.5678&quot;,-3) ==&gt; 1234.567</li> <li>getFloor(&quot;1234&quot;,3) ==&gt; 1000</li> <br>
	 *
	 * @param strDigit : 처리할 대상 값
	 * @param position : 소수점 절사 대상 위치
	 * @return String : 절사처리 후 결과 값
	 */
	public static String getFloor(String strDigit, int position) {

		String strTmpDigit = ""; // 지정된 자리수 만큼의 수를 담을 임시변수

		// 소수점이 존재하는지 Check
		int intDotIdx = strDigit.lastIndexOf(".");
		int intAbsolute = Math.abs(position);

		if ((intDotIdx > -1) && (position < 0)) { // 소수점이 존재할 경우

			// 소수점 이하의 자리수가 절사 대상 위치보다 같거나 클경우만 처리
			if (strDigit.substring(intDotIdx + 1).length() >= intAbsolute) {
				strTmpDigit = strDigit.substring(0, ((intDotIdx + 1) + intAbsolute));
			} else {
				strTmpDigit = strDigit;
			}

		} else if (position > 0) { // 소수점이 존재하지 않을 경우

			// 문자의 길이
			int intDigitLen = strDigit.length();

			// 문자의 길이가 절사 대상 위치보다 같거나 클경우만 처리
			if (intDigitLen >= intAbsolute) {

				strTmpDigit = strDigit.substring(0, intDigitLen - intAbsolute);

				for (int i = 0; i < intAbsolute; i++) {
					strTmpDigit += "0";
				}

			} else {
				strTmpDigit = strDigit;
			}
		}

		return strTmpDigit;
	}

	/**
	 * 입력받은 Data를 원하는 자리수 까지 반올림 하여 String 형으로 리턴한다<br>
	 * ex) <li>getRound(&quot;1234.5678&quot;,-3) ==&gt; 1234.57</li> <li>getRound(&quot;1234&quot;,3) ==&gt; 1000</li> <br>
	 *
	 * @param strDigit : 처리할 대상 값
	 * @param position : 반올림 대상 위치
	 * @return String : 반올림처리 후 결과 값
	 */
	public static String getRound(String strDigit, int position) {

		String strTmpDigit = ""; // 지정된 자리수 만큼의 수를 담을 임시변수
		StringBuffer sb = new StringBuffer(); // 문자열 가공용 변수

		// 소수점이 존재하는지 Check
		int intDotIdx = strDigit.lastIndexOf(".");
		int intAbsolute = Math.abs(position);

		if ((intDotIdx > -1) && (position < 0)) { // 소수점이 존재할 경우

			// 소수점 이하의 자리수가 반올림 대상 위치보다 같거나 클경우만 처리
			if (strDigit.substring(intDotIdx + 1).length() >= intAbsolute) {

				// Format 문자열을 가공
				sb.append("#");
				if (intAbsolute > 1) {
					sb.append(".");
					for (int i = 1; i < intAbsolute; i++) {
						sb.append("#");
					}
				}

				DecimalFormat df = new DecimalFormat(sb.toString());
				strTmpDigit = df.format(Double.parseDouble(strDigit));

			} else {
				strTmpDigit = strDigit;
			}

		} else if (position > 0) { // 소수점이 존재하지 않을 경우

			// 문자의 길이
			int intDigitLen = strDigit.length();

			// 문자의 길이가 반올림 대상 위치보다 같거나 클경우만 처리
			if (intDigitLen >= intAbsolute) {

				// 반올림 대상 위치
				int intTargetIdx = (intDigitLen - intAbsolute);

				// 반올림 대상 위치에 해당하는 문자를 가져와 반올림 처리한다.
				int intTmpDigit = Character.getNumericValue(strDigit.charAt(intTargetIdx));
				intTmpDigit = (intTmpDigit >= 5) ? intTmpDigit + 1 : 0;
				String strReplaceVal = (intTmpDigit == 0) ? "0" : Integer.toString(intTmpDigit);

				// 문자열 치환
				sb.append(strDigit.substring(0, intTargetIdx));
				for (int i = 0; i < strDigit.length(); i++) {
					if (i >= intTargetIdx) {
						if (i == intTargetIdx) {
							sb.append(strReplaceVal);
						} else {
							sb.append("0");
						}
					}
				}
			}

			strTmpDigit = sb.toString();
		}

		return strTmpDigit;
	}

	/**
	 *
	 * 입력받은 Long형 Data를 원하는 자리수 까지 절사하여 Long으로 리턴한다<br>
	 * ex) getLongFloor(9223372036854775807,3)==&gt; 9223372036854775000<br>
	 *
	 * @param longDigit : 처리할 대상 값
	 * @param position : 절사 대상 위치
	 * @return Long : 절사처리 후 결과 값
	 */
	public static long getLongFloor(long longDigit, int position) {

		StringBuffer sb = new StringBuffer();
		String strDigit = Long.toString(longDigit);

		// 문자의 길이
		int intDigitLen = strDigit.length();

		// 문자의 길이가 절사 대상 위치보다 같거나 클경우만 처리
		if (intDigitLen >= position) {
			sb.append(strDigit.substring(0, intDigitLen - position));

			for (int i = 0; i < position; i++) {
				sb.append("0");
			}

		} else {
			sb.append(strDigit);
		}
		try {
			return Long.parseLong(sb.toString());
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * 입력받은 double 형 Data를 원하는 자리수 까지 절사 하여 double 형으로 리턴한다<br>
	 * ex) getDoubleFloor(1234.5678,3) ==> 1234.567<br>
	 *
	 * @param dblDigit : 처리할 대상 값
	 * @param position : 소수점 절사 대상 위치
	 * @return double : 절사처리 후 결과 값
	 */
	public static double getDoubleFloor(double dblDigit, int position) {

		double dblCal = Math.pow(10, position);
		try {
			BigDecimal dblDigitB = new BigDecimal(dblDigit + "");
			BigDecimal dblCalB = new BigDecimal(dblCal + "");

			dblDigitB = dblDigitB.multiply(dblCalB);
			int tempValue = (int) dblDigitB.doubleValue();
			BigDecimal tempValueB = new BigDecimal(tempValue + "");
			return tempValueB.divide(dblCalB).doubleValue();
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * 입력받은 int 형 Data를 원하는 자리수 까지 절사하여 int 형으로 리턴한다<br>
	 * ex) getIntegerFloor(1234,2) ==> 1200<br>
	 *
	 * @param intDigit : 처리할 대상 값
	 * @param position : 절사 대상 위치
	 * @return int : 절사처리 후 결과 값
	 */
	public static int getIntegerFloor(int intDigit, int position) {

		StringBuffer sb = new StringBuffer();
		String strDigit = Integer.toString(intDigit);

		// 문자의 길이
		int intDigitLen = strDigit.length();

		// 문자의 길이가 절사 대상 위치보다 같거나 클경우만 처리
		if (intDigitLen >= position) {
			sb.append(strDigit.substring(0, intDigitLen - position));

			for (int i = 0; i < position; i++) {
				sb.append("0");
			}

		} else {
			sb.append(strDigit);
		}

		try {
			return Integer.parseInt(sb.toString());
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * 입력받은 long형 Data를 원하는 자리수 까지 반올림 하여 long 형으로 리턴한다<br>
	 * ex) getLongRound(9223372036854771807,3) ==> 9223372036854771900<br>
	 *
	 * @param longDigit : 처리할 대상 값
	 * @param position : 반올림 대상 위치
	 * @return long : 반올림처리 후 결과 값
	 */
	public static long getLongRound(long longDigit, int position) {

		String strDigit = Long.toString(longDigit);
		StringBuffer sb = new StringBuffer();

		// 문자의 길이
		int intDigitLen = strDigit.length();

		// 문자의 길이가 반올림 대상 위치보다 같거나 클경우만 처리
		if (intDigitLen >= position) {

			// 반올림 대상 위치
			int intTargetIdx = (intDigitLen - position);

			// 반올림 대상 위치에 해당하는 문자를 가져와 반올림 처리한다.
			int intTmpDigit = Character.getNumericValue(strDigit.charAt(intTargetIdx));
			intTmpDigit = (intTmpDigit >= 5) ? intTmpDigit + 1 : 0;
			String strReplaceVal = (intTmpDigit == 0) ? "0" : Integer.toString(intTmpDigit);

			// 문자열 치환
			sb.append(strDigit.substring(0, intTargetIdx));

			for (int i = 0; i < strDigit.length(); i++) {
				if (i >= intTargetIdx) {
					if (i == intTargetIdx) {
						sb.append(strReplaceVal);
					} else {
						sb.append("0");
					}
				}
			}
		}

		try {
			return Long.parseLong(sb.toString());
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * 입력받은 double형 Data를 원하는 자리수 까지 반올림 하여 double 형으로 리턴한다<br>
	 * ex) getDoubleRound(1234.5678,3) ==> 1234.57<br>
	 *
	 * @param dblDigit : 처리할 대상 값
	 * @param position : 반올림 대상 위치
	 * @return double : 반올림처리 후 결과 값
	 */
	public static double getDoubleRound(double dblDigit, int position) {

		double dblCal = Math.pow(10, position);
		try {
			return (int) (dblDigit * (int) dblCal + 0.5) / dblCal;
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}

	/**
	 * 입력받은 int 형 Data를 원하는 자리수 까지 반올림 하여 int 형으로 리턴한다<br>
	 * ex) getIntRound(4567,2) ==> 4570<br>
	 *
	 * @param intDigit : 처리할 대상 값
	 * @param position : 반올림 대상 위치
	 * @return int : 반올림처리 후 결과 값
	 */
	public static int getIntRound(int intDigit, int position) {

		String strDigit = Integer.toString(intDigit);
		StringBuffer sb = new StringBuffer();

		// 문자의 길이
		int intDigitLen = strDigit.length();

		// 문자의 길이가 반올림 대상 위치보다 같거나 클경우만 처리
		if (intDigitLen >= position) {

			// 반올림 대상 위치
			int intTargetIdx = (intDigitLen - position);

			// 반올림 대상 위치에 해당하는 문자를 가져와 반올림 처리한다.
			int intTmpDigit = Character.getNumericValue(strDigit.charAt(intTargetIdx));
			intTmpDigit = (intTmpDigit >= 5) ? intTmpDigit + 1 : 0;
			String strReplaceVal = (intTmpDigit == 0) ? "0" : Integer.toString(intTmpDigit);

			// 문자열 치환
			sb.append(strDigit.substring(0, intTargetIdx));
			for (int i = 0; i < strDigit.length(); i++) {
				if (i >= intTargetIdx) {
					if (i == intTargetIdx) {
						sb.append(strReplaceVal);
					} else {
						sb.append("0");
					}
				}
			}
		}

		try {
			return Integer.parseInt(sb.toString());
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}
}
