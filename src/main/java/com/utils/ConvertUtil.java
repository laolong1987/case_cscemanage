package com.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertUtil {
	
	/**
     * 将传入的对象转换为Boolean，当传入的对象为null时返回默认值
     *
     * @param o
     * @param flag
     * @return
     */
    public static Boolean safeToBoolean(Object o, Boolean flag) {
    	Boolean r = flag;
    	try {
    		if (o != null) {
                r = Boolean.valueOf(o.toString());
            }
		} catch (Exception e) {
			return false;
		}
        return r;
    }
    
    /**
     * 日期的字符串形式转成Date
     *
     * @param date   String 默认格式为yyyy-MM-dd
     * @param format 格式，null则表示默认
     * @return Date 日期
     */
    public static Date safeToDate(Object date, String format, Date defaultDate) {
        if (format == null)
            format = "yyyy-MM-dd";
        if (date == null){
        	return defaultDate;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date sysdate = null;
        try {
            sysdate = dateFormat.parse(date.toString());
        } catch (ParseException e) {
            return defaultDate;
        }
        return sysdate;
    }
    
    /**
     * 日期的字符串形式转成Date
     *
     * @param date   String 默认格式为yyyy-MM-dd
     * @param format 格式，null则表示默认
     * @return Date 日期
     */
    public static String safeToDateString(Date date, String format, String defaultDate) {
        if (format == null)
            format = "yyyy-MM-dd";
        if (date == null){
        	return defaultDate;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String sysdate = null;
        try {
            sysdate = dateFormat.format(date);
        } catch (Exception e) {
            return defaultDate;
        }
        return sysdate;
    }
    
    /**
     * 日期的字符串形式转成Date
     *
     * @return Date 日期
     */
    public static Timestamp safeToTimestamp(String date,Timestamp defaultDate) {
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null){
        	return defaultDate;
        }
        Timestamp sysdate = null;
        try {
        	sysdate = new Timestamp(format.parse(date).getTime());
        } catch (Exception e) {
            return defaultDate;
        }
        return sysdate;
    }
	
    /**
     * 将传入的对象转换为字符串，当传入的对象为null时返回默认值
     *
     * @param o
     * @param dv
     * @return
     */
    public static String safeToString(Object o, String dv) {
        String r = dv;
        if (o != null) {
            r = String.valueOf(o);
        }
        return r;
    }
    
	/**
     * 将object转换成double，当传入的对象是null时返回指定的值
     * @param o
     * @param dv
     * @return
     */
    public static Integer safeToInteger(Object o, Integer dv){
        Integer r = dv;
        if (o != null){
            try{
                r = new Integer(String.valueOf(o));
            }
            catch (Exception ex){}
        }
        return r;
    }

    /**
     * 将object转换成double，当传入的对象是null时返回指定的值
     * @param o   数值
     * @param dv  默认值
     * @return
     */
    public static Double safeToDouble(Object o, Double dv){
        Double r = dv;
        if (o != null){
            try{
                r = new Double(String.valueOf(o));
            }
            catch(Exception ex){}
        }
        return r;
    }

    /**
     * 将object转换成整型，当传入的对象是null时返回指定的值
     * @param o  数值
     * @param dv 默认值
     * @param round 有效小数位数
     * @return
     */
    public static Double safeToDouble(Object o, Double dv, int round){
        Double r = dv;
        if (o != null){
            try{
                r = new Double(String.valueOf(o));
            }
            catch(Exception ex){}
        }
        r = new BigDecimal(r).setScale(round, BigDecimal.ROUND_HALF_UP).doubleValue();
        return r;
    }

    /**
     * 将object转换成float，当传入的对象是null时返回指定的值
     * @param o   数值
     * @param dv  默认值
     * @return
     */
    public static Float safeToFloat(Object o, Float dv){
        Float r = dv;
        if (o != null){
            try{
                r = new Float(String.valueOf(o));
            }
            catch(Exception ex){}
        }
        return r;
    }

    /**
     * 将object转换成float，当传入的对象是null时返回指定的值
     * @param o  数值
     * @param dv 默认值
     * @param round 有效小数位数
     */
    public static Float safeToFloat(Object o, Float dv, int round){
        Float r = dv;
        if (o != null){
            try{
                r = new Float(String.valueOf(o));
            }
            catch(Exception ex){}
        }
        r = new BigDecimal(r).setScale(round, BigDecimal.ROUND_HALF_UP).floatValue();
        return r;
    }
    
    /*
     * 将文件名转换成ftp文件名
     */
    public static String getFtpFileName(String filename){
    	String formatStr = "yyyyMMddHHmm";
    	SimpleDateFormat format = new SimpleDateFormat(formatStr);
    	return (format.format(new Date()) + "_" + filename);
    }
    
    
}
