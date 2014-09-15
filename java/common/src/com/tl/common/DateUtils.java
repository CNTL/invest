package com.tl.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
	private static final String[] SHORT_DATE_PATTERN 				= {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy\\MM\\dd", "yyyyMMdd"};
	private static final String[] LONG_DATE_PATTERN 				= {"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy\\MM\\dd HH:mm:ss", "yyyyMMddHHmmss"};
	private static final String[] LONG_DATE_PATTERN_WITH_MILSEC 	= {"yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy\\MM\\dd HH:mm:ss.SSS", "yyyyMMddHHmmssSSS"};
	
	/** 返回 date 加上 value 天后的日期（清除时间信息） */
	public final static Date addDate(Date date, int value)
	{
		return addDate(date, value, true);
	}

	/** 返回 date 加上 value 天后的日期，trimTime 指定是否清除时间信息 */
	public final static Date addDate(Date date, int value, boolean trimTime)
	{
		return addTime(date, Calendar.DATE, value, trimTime);

	}

	/** 返回 date 加上 value 个 field 时间单元后的日期（不清除时间信息） */
	public final static Date addTime(Date date, int field, int value)
	{
		return addTime(date, field, value, false);
	}

	/** 返回 date 加上 value 个 field 时间单元后的日期，trimTime 指定是否去除时间信息 */
	public final static Date addTime(Date date, int field, int value, boolean trimTime)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, value);

		if(trimTime)
		{
        		c.set(Calendar.HOUR, 0);
        		c.set(Calendar.MINUTE, 0);
        		c.set(Calendar.SECOND, 0);
        		c.set(Calendar.MILLISECOND, 0);
		}

		return c.getTime();

	}
	
	/** String -> java.util.Date， str 的格式由 format  定义 */
	public final static Date toDate(String str, String format)
	{
		Date date = null;
		
		try
		{
			DateFormat df = new SimpleDateFormat(format);
			date = df.parse(StringUtils.safeTrimString(str));
		}
		catch(Exception e)
		{

		}

		return date;
	}

	/** String -> java.util.Date，由函数自身判断 str 的格式 */
	public final static Date toDate(String str)
	{
		Date date = null;

		try
		{
			final char SEPARATOR	= '-';
			final String[] PATTERN	= {"yyyy", "MM", "dd", "HH", "mm", "ss", "SSS"};
			String[] values			= StringUtils.safeTrimString(str).split("\\D");
			String[] element		= new String[values.length];
			
			int length = 0;
			for(String e : values)
			{
				e = e.trim();
				if(e.length() != 0)
				{
					element[length++] = e;
					if(length == PATTERN.length)
						break;
				}
			}

			if(length > 0)
			{
	       		StringBuilder value	= new StringBuilder();

				if(length > 1)
				{
        			for(int i = 0; i < length; ++i)
        			{
        				value.append(element[i]);
        				value.append(SEPARATOR);
        			}
				}
				else
				{
					String src	= element[0];
					int remain	= src.length();
					int pos		= 0;
					int i		= 0;

					for(i = 0; remain > 0 && i < PATTERN.length; ++i)
					{
						int p_length	= PATTERN[i].length();
						int v_length	= Math.min(p_length, remain);
						String v 		= src.substring(pos, pos + v_length);
						pos			+= v_length;
						remain 		-= v_length;

						value.append(v);
						value.append(SEPARATOR);
					}

					length = i;
				}

     			StringBuilder format = new StringBuilder();

     			for(int i = 0; i < length; ++i)
				{
					format.append(PATTERN[i]);
					format.append(SEPARATOR);
				}

				date = toDate(value.toString(), format.toString());
			}
		}
		catch(Exception e)
		{

		}

		return date;
	}
	
	/** String -> java.util.Date，由 Patterns 指定可能的日期格式 */
	private final static Date toDate(String str, String[] Patterns)
	{
		Date date = null;
		
		for(int i = 0; i < Patterns.length; ++i)
		{
			date = toDate(str, Patterns[i]);

			if( date != null)
				break;
		}

		return date;
	}
	
	/** String -> java.util.Date，由 SHORT_DATE_PATTERN 指定可能的日期格式 */
	public final static Date toShortDate(String str)
	{
		return toDate(str, SHORT_DATE_PATTERN);
	}

	/** String -> java.util.Date，由 LONG_DATE_PATTERN 指定可能的日期格式 */
	public final static Date toLongDate(String str)
	{
		return toDate(str, LONG_DATE_PATTERN);
	}

	/** String -> java.util.Date，由 LONG_DATE_PATTERN_WITH_MILSEC 指定可能的日期格式 */
	public final static Date toLongDateWithMilliSecond(String str)
	{
		return toDate(str, LONG_DATE_PATTERN_WITH_MILSEC);
	}

	/** java.util.Date -> String，str 的格式由 format  定义 */
	public final static String toStr(Date date, String format)
	{
		DateFormat df	= new SimpleDateFormat(format);
		return df.format(date);
	}
	//SimpleDateFormat非线程安全
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		}
	};
	private static DateFormat getDateFormat() {
		return (DateFormat) threadLocal.get();
	}

	/**
	 * 取当前时间，格式为yyyy-MM-dd HH:mm:ss:SSS
	 * @return
	 */
    public static String format() {
		return getDateFormat().format(new Date());
	}
    
	/**
	 * 取当前时间，按传入格式
	 * @return
	 */
    public static String format(String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	/**
	 * 把传入的日期转换成字符串，格式为yyyy-MM-dd HH:mm:ss:SSS
	 * @param calendar
	 * @return
	 */
	public static String format(Calendar calendar){
		return getDateFormat().format(calendar.getTime());
	}
	/**
	 * 把传入的日期转换成字符串，按传入格式
	 * @param calendar
	 * @return
	 */
	public static String format(Calendar calendar, String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(calendar.getTime());
	}
	/**
	 * 把传入的日期转换成字符串，格式为yyyy-MM-dd HH:mm:ss:SSS
	 * @param date
	 * @return
	 */
	public static String format(Date date){
		return getDateFormat().format(date);
	}
	/**
	 * 把传入的日期转换成字符串，按传入格式
	 * @param date
	 * @return
	 */
	public static String format(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * 把传入的日期转换成字符串，按传入格式
	 * @param date
	 * @return
	 */
	public static String format(Timestamp time, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}
	/**
	 * 取当前日期，时间部分置0
	 * @return
	 */
	public static Date getDate(){
		Calendar ca = Calendar.getInstance();
		return clearTime(ca).getTime();
	}
	
	/**
	 * 把传入的日期其时间部分置0
	 * @param theDate
	 * @return
	 */
	public static Date getDate(Date theDate){
		Calendar ca = Calendar.getInstance();
		ca.setTime(theDate);
		return clearTime(ca).getTime();
	}
	/**
	 * 按传入日期取前n天的时间,时间部分置0
	 * @param theDate
	 * @param days
	 * @return Calendar
	 */
	public static Calendar getCalendarBefore(Date theDate, int days){
		Calendar ca = Calendar.getInstance();
		ca.setTime(theDate);

		return getCalendarBefore(ca, days);
	}
	/**
	 * 取前n天的时间,时间部分置0
	 * @param days
	 * @return Calendar
	 */
	public static Calendar getCalendarBefore(int days){
		return getCalendarBefore(Calendar.getInstance(), days);
	}
	/**
	 * 按传入日期取前n天的时间,时间部分置0
	 * @param theDate
	 * @param days
	 * @return Calendar
	 */
	public static Calendar getCalendarBefore(Calendar ca, int days){
		ca.add(Calendar.DATE, -1 * days);
		return clearTime(ca);
	}
	/**
	 * 清除一个日期的时间部分
	 * @param ca
	 */
	private static Calendar clearTime(Calendar ca)
	{
		//ca.clear(Calendar.HOUR_OF_DAY);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.clear(Calendar.MINUTE);
		ca.clear(Calendar.SECOND);
		ca.clear(Calendar.MILLISECOND);
		return ca;
	}
	
	public static int daysNum(String dateArg){
	    Calendar calendar = new GregorianCalendar();  
	    SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);  
	    sdf.applyPattern("yyyy-MM"); // 201203格式  
	    try {  
	        System.out.println(sdf.parse(dateArg));  
	        calendar.setTime(sdf.parse(dateArg));  
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	    }  
	    int num2 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
	    return num2;
	 }
	 public static String YearNum(){
		    SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);  
		    sdf.applyPattern("yyyy"); // 201203格式  
		    return sdf.format(new Date());
	 }
	 public static String MonthNum(){
		 SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);  
		 sdf.applyPattern("MM"); // 201203格式  
		 return sdf.format(new Date());
	 }
	 public static String DayNum(){
		 SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);  
		 sdf.applyPattern("dd"); // 201203格式  
		 return sdf.format(new Date());
	 }
	/**
	 * 返回Timestamp类型的当前时间
	 * @return
	 */
	public static Timestamp getTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 获取年月日时分秒时间戳
	 * @return
	 */
	public static String getSysTime(){
		Date dNow = new Date();
		//保存目录名称
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		//保存文件名称后缀
		SimpleDateFormat formatT = new SimpleDateFormat("HHmmss");
		
		StringBuilder sb = new StringBuilder();
		//当前日期
		sb.append(format.format(dNow));
		//当前时分秒
		sb.append(formatT.format(dNow));
		return sb.toString();
	}
	
	/**
	 * 获取年月日时分秒时间戳
	 * @return
	 */
	public static String getSysDate(){
		Date dNow = new Date();
		//保存目录名称
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		StringBuilder sb = new StringBuilder();
		//当前日期
		sb.append(format.format(dNow));
		return sb.toString();
	}
}