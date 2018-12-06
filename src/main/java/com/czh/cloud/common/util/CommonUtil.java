package com.czh.cloud.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhehao.chen
 * @version: V1.0
 * @Description:
 * @date: 2018/11/29 14:53
 */
public class CommonUtil {

    final static public String YYYY_MM_DD = "yyyy-MM-dd";
    final static public String YYYY_MM_DD_HH_MM_ss = "yyyy-MM-dd HH:mm:ss";
    final static public String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    final static public String HH_MM_ss = "HH:mm:ss";
    final static public String YYYYMMDD = "yyyyMMdd";
    final static public String YYYYMMDD2 = "yyyy/MM/dd";
    final static public String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    final static public String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    final static public String HHMMss = "HHmmss";

    public static Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static String getFormatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getFormatDate(Calendar c, String format) {
        return getFormatDate(c.getTime(), format);
    }

    public static Date changeDate(Date date, int value, int kind) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(kind, value);
        return c.getTime();
    }

    public static boolean isNull(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static Map getMapFromQueryString(String queryString) {
        Map<String, String> map = new HashMap();
        if (!isNull(queryString)) {
            String[] params = queryString.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] param = params[i].split("=");
                if (param.length == 2) {
                    map.put(param[0], param[1]);
                }
            }
        }
        return map;
    }

}
