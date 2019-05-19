package com.zzlbe.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author liushuai
 */
public class DateUtil {

    private static final LocalDateTime CLOCK_12 = getLocalDateTimeByHour(12);
    private static final ThreadLocal<DateFormat> FORMAT_YEAR_2_MIN =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    public String getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public String getDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public String getDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public String getDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 根据 hour 获取 Date
     */
    public static String getDateStr(LocalDateTime localDateTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(localDateTime.getYear());
        sb.append("-");
        sb.append(localDateTime.getMonthValue());
        sb.append("-");
        sb.append(localDateTime.getDayOfMonth());

        return sb.toString();
    }

    /**
     * 根据 hour 获取 Date
     */
    public static Date getDateByHour(int hour) {
        LocalDateTime localDateTime = LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());

        return Date.from(zdt.toInstant());
    }

    /**
     * 根据 LocalDateTime 获取 Date
     */
    public static Date getDateByHour(LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());

        return Date.from(zdt.toInstant());
    }

    /**
     * 根据 LocalDateTime 获取 Date
     */
    public static LocalDateTime getLocalDateTimeByDate(Date date) {
        Instant instant = date.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 根据 hour 获取时间 LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeByHour(int hour) {

        return LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 上午 ? true : false
     */
    public static boolean isMorning() {

        return LocalDateTime.now().isBefore(CLOCK_12);
    }

    /**
     * date1 after date2
     */
    public static boolean after(Date date1, Date date2) {

        return getLocalDateTimeByDate(date1).isAfter(getLocalDateTimeByDate(date2));
    }

    /**
     * date1 before date2
     */
    public static boolean before(Date date1, Date date2) {

        return getLocalDateTimeByDate(date1).isBefore(getLocalDateTimeByDate(date2));
    }

    /**
     * 上午 ? true : false
     */
    public static boolean isMorning(LocalDateTime localDateTime) {

        return localDateTime.isBefore(CLOCK_12);
    }

    /**
     * 下午 ? true : false
     */
    public static boolean isAfternoon() {

        return LocalDateTime.now().isAfter(CLOCK_12);
    }

    /**
     * 下午 ? true : false
     */
    public static boolean isAfternoon(LocalDateTime localDateTime) {

        return localDateTime.isAfter(CLOCK_12);
    }

    public static Date getDateByStr(String date) {
        try {
            return FORMAT_YEAR_2_MIN.get().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateByStr2(String date)//截图字符串、拼接
    {
        return date.substring(0, 19).replace("T", " ");
    }

    public static String getDateByStr3(Date date) {//时间格式转换 //Tue Apr 16 16:51:35 CST 2019
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeFormat = sdf.format(date);
        return timeFormat;
    }


    public static void main(String[] args) throws InterruptedException {
        Date date1 = new Date();
        Thread.sleep(1000);
        Date date2 = new Date();

        System.out.println(before(date1, date2));
        System.out.println(after(date1, date2));
    }
}
