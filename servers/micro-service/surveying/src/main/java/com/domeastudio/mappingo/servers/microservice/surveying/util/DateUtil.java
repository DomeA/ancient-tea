package com.domeastudio.mappingo.servers.microservice.surveying.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * @param early <String>
     * @param late  <String>
     * @return int
     * @throws ParseException
     */
    public static int getMonthSpace(String early, String late)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(early));
        c2.setTime(sdf.parse(late));

        return c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
    }

    /**
     * @param early <String>
     * @param late  <String>
     * @return int
     * @throws ParseException
     */
    public static int getDateSpace(String early, String late)
            throws ParseException {

        Calendar calst = Calendar.getInstance();
        ;
        Calendar caled = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calst.setTime(sdf.parse(early));
        caled.setTime(sdf.parse(late));

        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

    public static int getDateSpace(Date early, Date late) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
                .getTime().getTime() / 1000)) / 3600 / 24;
        return days;
    }

    public static String dateToString(String formatStr, Date date, String type) {
        String str = null;
        DateFormat format = new SimpleDateFormat(formatStr);
        if (type.compareTo("SHORT") == 0) {
            // 07-1-18
            format = DateFormat.getDateInstance(DateFormat.SHORT);
            str = format.format(date);
        } else if (type.compareTo("MEDIUM") == 0) {
            // 2007-1-18
            format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            str = format.format(date);
        } else if (type.compareTo("FULL") == 0) {
            // 2007年1月18日 星期四
            format = DateFormat.getDateInstance(DateFormat.FULL);
            str = format.format(date);
        }
        return str;
    }

    public static Date stringToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            // Fri Feb 24 00:00:00 CST 2012
            date = format.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        // 2012-02-24
        // date = String.valueOf(str);
    }
}
