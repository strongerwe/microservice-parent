package cn.stronger.we.commons.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 日期时间工具类
 * @department Platform Center
 * @date 2024-07-28 00:12
 */
public class DateTimeTools extends DateUtils {

    /**
     * 时间格式 通用常量
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String TIME_FORMAT = "HH:mm:ss";
    public static String DATE_TIME_S_FORMAT = "yyyyMMddHHmmss";
    public static String DATE_YMD_FORMAT = "yyyy年MM月dd日";
    public static String DATE_TIME_YMD_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
    public static String START_TIME_SECOND = "00:00:00";
    public static String LAST_TIME_SECOND = "23:59:59";

    private static final DateTimeFormatter SLASH_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final DateTimeFormatter CHINESE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy年M月d日");
    public static final DateTimeFormatter CHINESE_FULL_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy年M月d日 HH时mm分ss秒");
    private static final DateTimeFormatter STRIKE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter STRIKE_TIME_FORMATTER_T = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * 获取当前日期、时间
     *
     * @return 系统当前的日期/时间（Date）
     */
    public static Date msGetCurrentDate() {
        return new Date();
    }

    /**
     * 日期/时间格式化显示（年、月、日、时、分、秒、毫秒、星期）
     *
     * @param dtmDate   需要格式化的日期（java.util.Date）
     * @param strFormat 该日期的格式串
     * @return 格式化后的字符串（String）
     */
    public static String msFormatDateTime(Date dtmDate,
                                          String strFormat) {
        if (StringTools.isBlank(strFormat)) {
            strFormat = DATE_TIME_FORMAT;
        }
        if (dtmDate == null) {
            return "";
        }
        SimpleDateFormat myFormat = new SimpleDateFormat(strFormat);
        return myFormat.format(dtmDate.getTime());
    }

    /**
     * 返回格式化的当前日期/时间
     *
     * @param strFormat 格式串
     * @return 当前日期/时间格式化后的字符串（String）
     */
    public static String getFormatCurrentDate(String strFormat) {
        return msFormatDateTime(msGetCurrentDate(), strFormat);
    }

    /**
     * 获取系统当前日期时间，格式为yyyyMMddHHmmss
     *
     * @return 返回计算后的时间（String）
     */
    public static String getCurrentDateTimes() {
        return getFormatCurrentDate(DATE_TIME_S_FORMAT);
    }

    /**
     * 获取系统当前时间，格式为HH:mm:ss
     *
     * @return 返回计算后的时间（String）
     */
    public static String getCurrentTime() {
        return getFormatCurrentDate(TIME_FORMAT);
    }

    /**
     * 获取系统当前日期，格式为yyyy-MM-dd
     *
     * @return 返回计算后的日期（String）
     */
    public static String getCurrentDate() {
        return getFormatCurrentDate(DATE_FORMAT);
    }

    /**
     * 获取系统当前日期的 ****年**月**日
     *
     * @return s
     */
    public static String getCurrentYMDDate() {
        return getFormatCurrentDate(DATE_YMD_FORMAT);
    }

    /**
     * 获取系统当前日期时间的 ****年**月**日 **时**分**秒
     *
     * @return s
     */
    public static String getCurrentYMDDatetime() {
        return getFormatCurrentDate(DATE_TIME_YMD_FORMAT);
    }

    /**
     * 获取日期的 ****年**月**日
     *
     * @param date date
     * @return s
     */
    public static String getYMDDate(Date date) {
        return msFormatDateTime(date, DATE_YMD_FORMAT);
    }

    /**
     * 将long值转
     *
     * @param millisecond millisecond
     * @return s
     */
    public static String formatDateAgo(long millisecond) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = millisecond / (long) dd;
        long hour = (millisecond - day * (long) dd) / (long) hh;
        long minute = (millisecond - day * (long) dd - hour * (long) hh) / (long) mi;
        long second = (millisecond - day * (long) dd - hour * (long) hh - minute * (long) mi) / (long) ss;
        StringBuilder sb = new StringBuilder();
        if (millisecond >= 0L && millisecond < 1000L) {
            sb.append(millisecond).append("毫秒");
        } else {
            if (day > 0L) {
                sb.append(day).append("天");
            }

            if (hour > 0L) {
                sb.append(hour).append("时");
            }

            if (minute > 0L) {
                sb.append(minute).append("分");
            }

            if (second > 0L) {
                sb.append(second).append("秒");
            }
        }
        return sb.toString();
    }

    /**
     * 取得给定日期数天前（后）的日期函数
     *
     * @param dateStr 需要进行加减的日期("yyyy-MM-dd hh:mm:ss")
     * @param amount  需要计算的间隔天数
     * @return 返回计算后的日期（String）
     */
    public static String addDays(String dateStr, int amount) throws ParseException {
        String strFormat = DATE_TIME_FORMAT;
        Date date = null;
        date = parseDate(dateStr, strFormat);
        date = addDays(date, amount);
        return DateFormatUtils.format(date, strFormat);
    }

    public static String addDay(String dateStr, int amount) throws ParseException {
        String strFormat = DATE_FORMAT;
        Date date = null;
        date = parseDate(dateStr, strFormat);
        date = addDays(date, amount);
        return DateFormatUtils.format(date, strFormat);
    }

    /**
     * 获得昨天
     *
     * @return s
     */
    public static Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获得日期的星期
     *
     * @param strDate 需要计算星期的日期(yyyy-MM-dd)
     * @return 计算后的星期（int）
     * @throws ParseException e
     */
    public static int msGetWeeks(String strDate) throws ParseException {
        String strFormat = "yyyy-MM-dd";
        Date dtDate = null;
        int intDay = 0;
        SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
        Calendar cal = Calendar.getInstance();

        dtDate = myFormatter.parse(strDate);
        cal.setTime(dtDate);
        intDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (intDay == 0) {
            intDay = 7;
        }
        return intDay;
    }

    /**
     * 获得日期的星期
     *
     * @param strDate 需要计算星期的日期(yyyy-MM-dd)
     * @return 计算后的星期（int）
     * @throws ParseException e
     */
    public static String msGetWeeksStr(String strDate) throws ParseException {
        int i = msGetWeeks(strDate);
        String[] weekdays = new String[]{"", "星期一", "星期二", "星期三",
                "星期四", "星期五", "星期六", "星期日"};
        return weekdays[i];
    }

    /**
     * 返回日期的最后一秒
     *
     * @param date 日期
     * @return 日期的最后一秒
     */
    public static String getDateLastSecond(String date) {
        return date + " " + LAST_TIME_SECOND;
    }

    /**
     * 返回日期的00:00:00
     *
     * @param date 日期
     * @return s
     */
    public static String getDateStartSecond(String date) {
        return date + " " + START_TIME_SECOND;
    }

    /**
     * 根据年月日计算年龄
     *
     * @param date 出生日期（格式：yyyy-MM-dd）
     * @return 年龄
     */
    public static int getAgeByDate(String date) {
        // 先截取到字符串中的年、月、日
        String[] strArr = date.trim().split("-");
        int selectYear = Integer.parseInt(strArr[0]);
        int selectMonth = Integer.parseInt(strArr[1]);
        int selectDay = Integer.parseInt(strArr[2]);
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = 0;
        if (yearMinus > 0) {
            if (monthMinus <= 0 && dayMinus < 0) {
                age = yearMinus - 1;
            } else {// 今年已过生日
                age = yearMinus;
            }
        }
        return age;
    }

    /**
     * "yyyy-MM-dd HH:mm:ss:SSS"
     *
     * @param dateTime dateTime
     * @param format   format
     * @return s
     */
    public static String formatLocalDateTims(LocalDateTime dateTime, String format) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return dateTime.format(fmt);
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @param dateTime dateTime
     * @return s
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatLocalDateTims(dateTime, DATE_TIME_FORMAT);
    }

    /**
     * 时间戳转LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        Objects.requireNonNull(timestamp);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }

    /**
     * LocalDateTime转时间戳
     */
    public static Long toTimestamp(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime);
        return localDateTime.toInstant(ZoneOffset.of("+08:00")).toEpochMilli();
    }

    /**
     * 字符串转时间戳
     * <p>
     * 支持格式：1. yyyy年M月d日 HH时mm分ss秒 2. yyyy年M月d日 3. yyyy/MM/dd HH:mm:ss 4. yyyy-MM-dd HH:mm:ss
     */
    public static LocalDateTime stringToDate(String stringDate) {
        LocalDateTime localDateTime = null;
        if (stringDate.contains("时")) {
            localDateTime = LocalDateTime.parse(stringDate, CHINESE_FULL_TIME_FORMATTER);
        } else if (stringDate.contains("年")) {
            LocalDate localDate = LocalDate.parse(stringDate, CHINESE_TIME_FORMATTER);
            localDateTime = localDate.atStartOfDay();
        } else if (stringDate.contains("/")) {
            localDateTime = LocalDateTime.parse(stringDate, SLASH_TIME_FORMATTER);
        } else if (stringDate.contains("-") && !stringDate.contains("T")) {
            localDateTime = LocalDateTime.parse(stringDate, STRIKE_TIME_FORMATTER);
        } else if (stringDate.contains("-") && stringDate.contains("T")) {
            localDateTime = LocalDateTime.parse(stringDate, STRIKE_TIME_FORMATTER_T);
        }
        return localDateTime;
    }
}
