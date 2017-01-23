/**
 * Project Name : 기능경기시스템
 * Copyright(c) 2010 by SkySoft
 * Create on 2010. 9. 2.
 */
package bsmedia.system.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


/**
 *
 * @author mindeng77
 */
public class DateUtils {

    // 1 : Calendar.YEAR
    // 2 : Calendar.MONTH
    // 3 : Calendar.WEEK_OF_YEAR
    // 5 : Calendar.DATE
    // 7 : Calendar.DAY_OF_WEEK
    // 11 : Calendar.HOUR_OF_DAY
    // 12 : Calendar.MINUTE
    // 13 : Calendar.SECOND

    /** default date pattern : 'yyyy-MM-dd' */
    public final static String defaultPattern     = "yyyy-MM-dd";
    /** default date pattern : 'yyyy-MM-dd HH:mm:ss' */
    public final static String defaultFullPattern = "yyyy-MM-dd HH:mm:ss";

    /** default date delim : '-' */
    public final static String defaultDateDelim   = "-";

    /** default time delim : ':' */
    public final static String defaultTimeDelim   = ":";

    /**
     * 오늘 날짜를 기본 패턴 날짜 형식으로 문자 변환하여 반환한다.
     * @return 오늘 일짜
     */
    public static String getToday() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DateUtils.defaultPattern, Locale.KOREA);
        return dateFormatter.format(new Date());
    }

    /**
     * 오늘 날짜를 기본 확장형 패턴 날짜 형식으로 문자 변환하여 반환한다.
     * @return 오늘 날짜
     */
    public static String getFullToday() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DateUtils.defaultFullPattern, Locale.KOREA);
        return dateFormatter.format(new Date());
    }

    /**
     * 오늘 날짜를 사용자가 정의한 날짜 형식으로 문자 변환하여 반환한다.
     * @param pattern 사용자 정의 날짜 형식
     * @return 오늘 날짜
     */
    public static String getToday(String pattern) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern, Locale.KOREA);
        return dateFormatter.format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss 형식 문자로 된 날짜를 Date 형식으로 변환하여 반환한다.
     * @param date yyyy-MM-dd HH:mm:ss 형식 날짜
     * @return Date Object
     */
    public static Date getCnvrDate(String date) {
        return DateUtils.getCnvrDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 정의된 형식의 문자 날짜를 Date 형식으로 변환하여 반환한다.
     * @param date 정의된 형식의 문자 날짜
     * @param format 정의된 형식
     * @return Date object
     */
    public static Date getCnvrDate(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
        return formatter.parse(date, new ParsePosition(0));
    }

    /**
     * yyyyMMdd 형식 문자로 된 날짜를 Date 형식으로 변환하여 반환한다.
     * @param date yyyyMMdd 형식 날짜
     * @return Date Object
     */
    public static Date getCnvrDateAtShort(String date) {
        return DateUtils.getCnvrDate(date, "yyyyMMdd");
    }

    /**
     * yyyy-MM-dd 형식 문자로 된 날짜를 Date 형식으로 변환하여 반환한다.
     * @param date yyyy-MM-dd 형식 날짜
     * @return Date Object
     */
    public static Date getCnvrDateAtShortDelim(String date) {
        String dateDelim = DateUtils.defaultDateDelim;
        return DateUtils.getCnvrDate(date, "yyyy" + dateDelim + "MM" + dateDelim + "dd");
    }

    /**
     * yyyy-MM-dd HH:mm:ss 형식 문자로 된 날짜를 사용자가 정의한 날짜 형식으로 문자 변환하여 반환한다.
     * @param date yyyy-MM-dd HH:mm:ss 형식 날짜
     * @param format 사용자가 정의 날짜 형식
     * @return 사용자가 정의한 날짜 형식 문자
     */
    public static String getCnvrDateToStr(String date, String format) {
        return DateUtils.getCnvrDateToStr(date, "yyyy-MM-dd HH:mm:ss", format);
    }

    /**
     * 정의된 형식의 문자로 된 날짜를 사용자가 재정의한 형식의 문자로 변환하여 반환한다.
     * @param date 문자로 구성된 날짜
     * @param originFormat 정의된 형식
     * @param targetFormat 재정의한 형식
     * @return 재정의한 형식의 문자
     */
    public static String getCnvrDateToStr(String date, String originFormat, String targetFormat) {
        SimpleDateFormat originFormatter = new SimpleDateFormat(originFormat, Locale.KOREA);
        SimpleDateFormat targetFormatter = new SimpleDateFormat(targetFormat, Locale.KOREA);
        Date originDate = originFormatter.parse(date, new ParsePosition(0));
        return targetFormatter.format(originDate);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 형식 문자로 된 날짜를 yyyyMMdd 날짜 형식으로 문자 변환하여 반환한다.
     * @param date yyyy-MM-dd HH:mm:ss 형식 날짜
     * @return yyyyMMdd 날짜 형식 문자
     */
    public static String getCnvrDateToStrShort(String date) {
        return DateUtils.getCnvrDateToStr(date, "yyyy-MM-dd HH:mm:ss", "yyyyMMdd");
    }

    /**
     * 정의된 형식의 문자로 된 날짜를 yyyyMMdd 날짜 형식으로 문자 변환하여 반환한다.
     * @param date 문자로 구성된 날짜
     * @param format 정의된 형식
     * @return yyyyMMdd 날짜 형식 문자
     */
    public static String getCnvrDateToStrShort(String date, String format) {
        return DateUtils.getCnvrDateToStr(date, format, "yyyyMMdd");
    }

    /**
     * yyyy-MM-dd HH:mm:ss 형식 문자로 된 날짜를 yyyy-MM-dd 날짜 형식으로 문자 변환하여 반환한다.
     * @param date yyyy-MM-dd HH:mm:ss 형식 날짜
     * @return yyyy-MM-dd 날짜 형식 문자
     */
    public static String getCnvrDateToStrShortDelim(String date) {
        String dateDelim = DateUtils.defaultDateDelim;
        String targetFormat = "yyyy" + dateDelim + "MM" + dateDelim + "dd";
        return DateUtils.getCnvrDateToStr(date, "yyyy-MM-dd HH:mm:ss", targetFormat);
    }

    /**
     * 정의된 형식의 문자로 된 날짜를 yyyy-MM-dd 날짜 형식으로 문자 변환하여 반환한다.
     * @param date 문자로 구성된 날짜
     * @param format 정의된 형식
     * @return yyyy-MM-dd 날짜 형식 문자
     */
    public static String getCnvrDateToStrShortDelim(String date, String format) {
        String dateDelim = DateUtils.defaultDateDelim;
        String targetFormat = "yyyy" + dateDelim + "MM" + dateDelim + "dd";
        return DateUtils.getCnvrDateToStr(date, format, targetFormat);
    }

    /**
     * 날짜에 포함되어 있는 구분자를 제거한다.
     * @param date 날짜
     * @return 구분자가 제거된 날짜
     */
    public static String removeDelim(String date) {
        return date.replaceAll("-", "").replaceAll("/", "").replaceAll(" ", "").replaceAll(":", "");
    }

    /**
     * 날짜에 포함되어 있는 구분자를 제거한다.
     * @param date 날짜
     * @param delim 지우고자 하는 구분자
     * @return 구분자가 제거된 날짜
     */
    public static String removeDelim(String date, String delim) {
        return date.replaceAll(delim, "");
    }

    /**
     * 날짜에 포함되어 있는 구분자를 제거한다.
     * @param date 날짜
     * @param delim 지우고자 하는 구분자 배열
     * @return 구분자가 제거된 날짜
     */
    public static String removeDelim(String date, String[] delim) {
        for (String i : delim) {
            date.replaceAll(i, "");
        }
        return date;
    }

    /**
     * 날짜의 해당 요일을 찾아 반환한다.
     * @param date 날짜
     * @param type 타입 (KOR, CHN, ENG, KORFULL, CHNFULL, ENGFULL ..)
     * @return 날짜의 해당 요일
     */
    public static String getDayOfWeek(String date, String type) {
        date = DateUtils.removeDelim(date);
        if (date.length() < 8) { throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date); }

        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.set(1, Integer.parseInt(date.substring(0, 4)));
        calendar.set(2, Integer.parseInt(date.substring(4, 6)) - 1);
        calendar.set(5, Integer.parseInt(date.substring(6, 8)));
        int weekNum = calendar.get(7);

        // 타입에 따른 요일 명
        String[] korName = { "일", "월", "화", "수", "목", "금", "토" };
        String[] chnName = { "日", "月", "火", "水", "木", "金", "土" };
        String[] engName = { "Sun", "Mon", "Tue", "Wen", "Thur", "Fri", "Sat" };
        String[] engNameFull = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

        // 반환 시켜줄 변수
        String dayOfWeek = "";

        // 타입을 소문자로 변환하여 대소문자에도 대처한다.
        type = type.toLowerCase();
        if ("kor".equals(type)) {
            dayOfWeek = korName[weekNum - 1];
        } else if ("chn".equals(type)) {
            dayOfWeek = chnName[weekNum - 1];
        } else if ("eng".equals(type)) {
            dayOfWeek = engName[weekNum - 1];
        } else if ("korfull".equals(type)) {
            dayOfWeek = korName[weekNum - 1] + "요일";
        } else if ("chnfull".equals(type)) {
            dayOfWeek = chnName[weekNum - 1] + "瞭日";
        } else if ("engfull".equals(type)) {
            dayOfWeek = engNameFull[weekNum - 1];
        } else if ("1".equals(StringUtils.right(type, 1)) && StringUtils.isNumeric(type)) {
            dayOfWeek = StringUtils.leftPad(String.valueOf(weekNum), type.length(), "0");
        } else if ("0".equals(StringUtils.right(type, 1)) && StringUtils.isNumeric(type)) {
            dayOfWeek = StringUtils.leftPad(String.valueOf(weekNum - 1), type.length(), "0");
        } else {
            dayOfWeek = String.valueOf(weekNum);
        }

        return dayOfWeek;
    }

    /**
     * 날짜에 사용자 정의 시간을 더한다.
     * @param date 날짜
     * @param calendarField 시간 유형
     * @param amount 더할 시간
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String add(String date, int calendarField, int amount, boolean delimAt) {
        date = DateUtils.removeDelim(date);
        int dateLen = date.length();
        if (dateLen < 8) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date);
        } else if (dateLen > 14) {
            date = date.substring(0, 14);
        }

        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.set(1, Integer.parseInt(date.substring(0, 4)));
        calendar.set(2, Integer.parseInt(date.substring(4, 6)) - 1);
        calendar.set(5, Integer.parseInt(date.substring(6, 8)));

        switch (date.length()) {
            case 14:
                calendar.set(13, Integer.parseInt(date.substring(12, 14)));
            case 12:
                calendar.set(12, Integer.parseInt(date.substring(10, 12)));
            case 10:
                calendar.set(11, Integer.parseInt(date.substring(8, 10)));
        }
        calendar.add(calendarField, amount);

        String dateFormat = "";
        String dateDelim = DateUtils.defaultDateDelim;
        String timeDelim = DateUtils.defaultTimeDelim;
        switch (date.length()) {
            case 14:
                dateFormat = delimAt ? "yyyy" + dateDelim + "MM" + dateDelim + "dd HH" + timeDelim + "mm" + timeDelim + "ss" : "yyyyMMddHHmmss";
                break;
            case 12:
                dateFormat = delimAt ? "yyyy" + dateDelim + "MM" + dateDelim + "dd HH" + timeDelim + "mm" : "yyyyMMddHHmm";
                break;
            case 10:
                dateFormat = delimAt ? "yyyy" + dateDelim + "MM" + dateDelim + "dd HH" : "yyyyMMddHH";
                break;
            case 8:
                dateFormat = delimAt ? "yyyy" + dateDelim + "MM" + dateDelim + "dd" : "yyyyMMdd";
                break;
        }
        return new SimpleDateFormat(dateFormat, Locale.KOREA).format(calendar.getTime());
    }

    /**
     * 날짜에 사용자 정의 시간을 더한다.
     * @param date 날짜
     * @param calendarField 시간 유형
     * @param amount 더할 시간
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String add(String date, String calendarField, int amount, boolean delimAt) {
        int calendarFieldType = -1;
        calendarField = calendarField.toLowerCase();
        if ("year".equals(calendarField) || "yyyy".equals(calendarField) || "yy".equals(calendarField)) {
            calendarFieldType = 1;
        } else if ("month".equals(calendarField) || "mm".equals(calendarField)) {
            calendarFieldType = 2;
        } else if ("week".equals(calendarField)) {
            calendarFieldType = 3;
        } else if ("date".equals(calendarField) || "dd".equals(calendarField)) {
            calendarFieldType = 5;
        } else if ("hour".equals(calendarField) || "hh".equals(calendarField)) {
            calendarFieldType = 11;
        } else if ("minute".equals(calendarField) || "mm24".equals(calendarField)) {
            calendarFieldType = 12;
        } else if ("secound".equals(calendarField) || "ss".equals(calendarField)) {
            calendarFieldType = 13;
        } else {
            throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + calendarField);
        }
        return DateUtils.add(date, calendarFieldType, amount, delimAt);
    }

    /**
     * 날짜에 년을 더한다.
     * @param date 날짜
     * @param amount 더할 년도
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String addYear(String date, int amount, boolean delimAt) {
        return DateUtils.add(date, 1, amount, delimAt);
    }

    /**
     * 날짜에 월을 더한다.
     * @param date 날짜
     * @param amount 더할 개월
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String addMonth(String date, int amount, boolean delimAt) {
        return DateUtils.add(date, 2, amount, delimAt);
    }

    /**
     * 날짜에 주를 더한다.
     * @param date 날짜
     * @param amount 더할 주 (7일)
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String addWeek(String date, int amount, boolean delimAt) {
        return DateUtils.add(date, 3, amount, delimAt);
    }

    /**
     * 날짜에 일을 더한다.
     * @param date 날짜
     * @param amount 더할 일
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String addDate(String date, int amount, boolean delimAt) {
        return DateUtils.add(date, 5, amount, delimAt);
    }

    /**
     * 날짜에 시간을 더한다.
     * @param date 날짜
     * @param amount 더할 시간
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String addHour(String date, int amount, boolean delimAt) {
        if (DateUtils.removeDelim(date).length() < 10) { throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date); }
        return DateUtils.add(date, 11, amount, delimAt);
    }

    /**
     * 날짜에 분을 더한다.
     * @param date 날짜
     * @param amount 더할 분
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String addMinute(String date, int amount, boolean delimAt) {
        if (DateUtils.removeDelim(date).length() < 12) { throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date); }
        return DateUtils.add(date, 12, amount, delimAt);
    }

    /**
     * 날짜에 초를 더한다.
     * @param date 날짜
     * @param amount 더할 초
     * @param delimAt 반환 시 구분자 포함 여부
     * @return 변경된 날짜
     */
    public static String addSecond(String date, int amount, boolean delimAt) {
        if (DateUtils.removeDelim(date).length() < 14) { throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date); }
        return DateUtils.add(date, 13, amount, delimAt);
    }

    /**
     * 숫자로 구성된 날짜에 구분자를 포함한 날짜 형식으로 변환한다.
     * @param date 날짜
     * @param format 날짜 형식 (yyyyMMdd, yyMMdd, yyyyMM, yyMM, MMdd)
     * @param delim 구분자
     * @return 형식을 갖춘 날짜
     */
    public static String addDelim(String date, String format, String delim) {
        String returnDate;
        if (StringUtils.isNumeric(date) && StringUtils.isNotBlank(delim)) {
            if ("yyyyMMdd".equals(format) && StringUtils.isNotEmpty(date) && date.length() == 8) {
                returnDate = date.substring(0, 4) + delim + date.substring(4, 6) + delim + date.substring(6);
            } else if ("yyMMdd".equals(format) && StringUtils.isNotEmpty(date) && date.length() == 6) {
                returnDate = date.substring(0, 2) + delim + date.substring(2, 4) + delim + date.substring(4);
            } else if ("yyyyMM".equals(format) && StringUtils.isNotEmpty(date) && date.length() == 6) {
                returnDate = date.substring(0, 4) + delim + date.substring(4);
            } else if (("yyMM".equals(format) || "MMdd".equals(format)) && StringUtils.isNotEmpty(date) && date.length() == 4) {
                returnDate = date.substring(0, 2) + delim + date.substring(2);
            } else {
                throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date + ", " + format);
            }
        } else {
            throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date);
        }
        return returnDate;
    }

    /**
     * 입력받은 날짜 달의 마지막 날짜 구하기
     * @param date 문자로 구성된 날짜
     * @return 달의 마지막 날짜
     */
    public static int getLastDayOfMonth(String date) {
        date = DateUtils.removeDelim(date);
        if (date.length() < 8) { throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + date); }

        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, 1);
        return calendar.getActualMaximum(5);
    }

    /**
     * 입력받은 날짜 달의 마지막 날짜 구하기
     * @param date 문자로 구성된 날짜
     * @return 달의 마지막 날짜
     */
    public static int getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.setTime(date);
        return calendar.getActualMaximum(5);
    }

    /**
     * 두 날짜 간의 간격 일을 구한다.
     * @param originDate A 날짜
     * @param targetDate B 날짜
     * @return 두 날짜 간의 간격 일
     */
    public static int getIntavalDays(String originDate, String targetDate) {
        originDate = DateUtils.removeDelim(originDate);
        targetDate = DateUtils.removeDelim(targetDate);
        if (originDate.length() < 8 || targetDate.length() < 8) { throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + originDate + ", " + targetDate); }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        long originTime = formatter.parse(originDate.substring(0, 8), new ParsePosition(0)).getTime();
        long targetTime = formatter.parse(targetDate.substring(0, 8), new ParsePosition(0)).getTime();

        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        if (originTime > targetTime) {
            calendar.setTimeInMillis(originTime - targetTime);
            return calendar.get(5) - 1;
        } else {
            calendar.setTimeInMillis(targetTime - originTime);
            return -(calendar.get(5) - 1);
        }
    }

    /**
     * 두 날짜 간의 간격 월을 구한다.
     * @param originDate A 날짜
     * @param targetDate B 날짜
     * @return 두 날짜 간의 간격 월
     * @throws java.text.ParseException
     */
    public static int getIntavalMonths(String originDate, String targetDate) throws java.text.ParseException {
        originDate = DateUtils.removeDelim(originDate);
        targetDate = DateUtils.removeDelim(targetDate);
        if (originDate.length() < 8 || targetDate.length() < 8) { throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + originDate + ", " + targetDate); }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date originDateObject = null, targetDateObject = null;
        try {
            originDateObject = formatter.parse(originDate);
            targetDateObject = formatter.parse(targetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatterYY = new SimpleDateFormat("yyyy", Locale.KOREA);
        SimpleDateFormat formatterMM = new SimpleDateFormat("MM", Locale.KOREA);

        int originYY = Integer.parseInt(formatterYY.format(originDateObject));
        int originMM = Integer.parseInt(formatterMM.format(originDateObject));
        int targetYY = Integer.parseInt(formatterYY.format(targetDateObject));
        int targetMM = Integer.parseInt(formatterMM.format(targetDateObject));

        int intavalMonths = 0;
        intavalMonths += (targetYY - originYY) * 12;
        intavalMonths += (targetMM - originMM);

        return intavalMonths;
    }

    /**
     * 입력받은 두 날짜를 비교하여 이전/이후/같은날 (-1, 1, 0)인지 반환한다.
     * @param originDate A 날짜
     * @param targetDate B 날짜
     * @param calendarField 시간 유형
     * @return 이전/이후/같은날 (-1, 1, 0)
     */
    public static int compareTo(String originDate, String targetDate, int calendarField) {
        String errorMsg = "잘못된 입력 형식입니다. :: " + calendarField + ", " + originDate + ", " + targetDate;
        originDate = DateUtils.removeDelim(originDate);
        targetDate = DateUtils.removeDelim(targetDate);

        int originDateLength = originDate.length();
        int targetDateLength = targetDate.length();

        Calendar originCalendar = Calendar.getInstance(Locale.KOREA);
        Calendar targetCalendar = Calendar.getInstance(Locale.KOREA);
        switch (calendarField) {
            case 13:
                if (originDateLength < 14 || targetDateLength < 14) { throw new IllegalArgumentException(errorMsg); }
                originCalendar.set(13, Integer.parseInt(originDate.substring(12, 14)));
                targetCalendar.set(13, Integer.parseInt(targetDate.substring(12, 14)));
            case 12:
                if (originDateLength < 12 || targetDateLength < 12) { throw new IllegalArgumentException(errorMsg); }
                originCalendar.set(12, Integer.parseInt(originDate.substring(10, 12)));
                targetCalendar.set(12, Integer.parseInt(targetDate.substring(10, 12)));
            case 11:
                if (originDateLength < 10 || targetDateLength < 10) { throw new IllegalArgumentException(errorMsg); }
                originCalendar.set(11, Integer.parseInt(originDate.substring(8, 10)));
                targetCalendar.set(11, Integer.parseInt(targetDate.substring(8, 10)));
            case 5:
                if (originDateLength < 8 || targetDateLength < 8) { throw new IllegalArgumentException(errorMsg); }
                originCalendar.set(5, Integer.parseInt(originDate.substring(6, 8)));
                targetCalendar.set(5, Integer.parseInt(targetDate.substring(6, 8)));
            case 2:
                if (originDateLength < 6 || targetDateLength < 6) { throw new IllegalArgumentException(errorMsg); }
                originCalendar.set(2, Integer.parseInt(originDate.substring(4, 6)));
                targetCalendar.set(2, Integer.parseInt(targetDate.substring(4, 6)));
            case 1:
                if (originDateLength < 4 || targetDateLength < 4) { throw new IllegalArgumentException(errorMsg); }
                originCalendar.set(1, Integer.parseInt(originDate.substring(0, 4)));
                targetCalendar.set(1, Integer.parseInt(targetDate.substring(0, 4)));
        }
        if (originCalendar.equals(targetCalendar)) {
            return 0;
        } else if (originCalendar.before(targetCalendar)) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * 입력받은 두 날짜를 비교하여 이전/이후/같은날 (-1, 1, 0)인지 반환한다.
     * @param originDate A 날짜
     * @param targetDate B 날짜
     * @param calendarField 시간 유형
     * @return 이전/이후/같은날 (-1, 1, 0)
     */
    public static int compareTo(String originDate, String targetDate, String calendarField) {
        calendarField = calendarField.toLowerCase();
        if ("year".equals(calendarField) || "yyyy".equals(calendarField) || "yy".equals(calendarField)) {
            return DateUtils.compareTo(originDate, targetDate, 1);
        } else if ("month".equals(calendarField) || "mm".equals(calendarField)) {
            return DateUtils.compareTo(originDate, targetDate, 2);
        } else if ("date".equals(calendarField) || "dd".equals(calendarField)) {
            return DateUtils.compareTo(originDate, targetDate, 5);
        } else if ("hour".equals(calendarField) || "hh".equals(calendarField)) {
            return DateUtils.compareTo(originDate, targetDate, 11);
        } else if ("minute".equals(calendarField) || "mm24".equals(calendarField)) {
            return DateUtils.compareTo(originDate, targetDate, 12);
        } else if ("secound".equals(calendarField) || "ss".equals(calendarField)) {
            return DateUtils.compareTo(originDate, targetDate, 13);
        } else {
            throw new IllegalArgumentException("잘못된 입력 형식입니다. :: " + calendarField);
        }
    }

    /**
     * 입력 받는 날짜 문자열이 유효한지 확인한다.
     * @param date 문자로 구성된 날짜
     * @param format 날짜 형식
     * @return 날짜 유효 여부
     */
    public static boolean isValid(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);
        Date dateObject = null;
        try {
            dateObject = formatter.parse(date);
        } catch (java.text.ParseException e) {
            return false;
        }

        if (!formatter.format(dateObject).equals(date)) {
            return false;
        } else {
            return true;
        }
    }
}
