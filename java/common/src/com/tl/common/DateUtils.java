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
	
	/** ���� date ���� value �������ڣ����ʱ����Ϣ�� */
	public final static Date addDate(Date date, int value)
	{
		return addDate(date, value, true);
	}

	/** ���� date ���� value �������ڣ�trimTime ָ���Ƿ����ʱ����Ϣ */
	public final static Date addDate(Date date, int value, boolean trimTime)
	{
		return addTime(date, Calendar.DATE, value, trimTime);

	}

	/** ���� date ���� value �� field ʱ�䵥Ԫ������ڣ������ʱ����Ϣ�� */
	public final static Date addTime(Date date, int field, int value)
	{
		return addTime(date, field, value, false);
	}

	/** ���� date ���� value �� field ʱ�䵥Ԫ������ڣ�trimTime ָ���Ƿ�ȥ��ʱ����Ϣ */
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
	
	/** String -> java.util.Date�� str �ĸ�ʽ�� format  ���� */
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

	/** String -> java.util.Date���ɺ��������ж� str �ĸ�ʽ */
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
	
	/** String -> java.util.Date���� Patterns ָ�����ܵ����ڸ�ʽ */
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
	
	/** String -> java.util.Date���� SHORT_DATE_PATTERN ָ�����ܵ����ڸ�ʽ */
	public final static Date toShortDate(String str)
	{
		return toDate(str, SHORT_DATE_PATTERN);
	}

	/** String -> java.util.Date���� LONG_DATE_PATTERN ָ�����ܵ����ڸ�ʽ */
	public final static Date toLongDate(String str)
	{
		return toDate(str, LONG_DATE_PATTERN);
	}

	/** String -> java.util.Date���� LONG_DATE_PATTERN_WITH_MILSEC ָ�����ܵ����ڸ�ʽ */
	public final static Date toLongDateWithMilliSecond(String str)
	{
		return toDate(str, LONG_DATE_PATTERN_WITH_MILSEC);
	}

	/** java.util.Date -> String��str �ĸ�ʽ�� format  ���� */
	public final static String toStr(Date date, String format)
	{
		DateFormat df	= new SimpleDateFormat(format);
		return df.format(date);
	}
	//SimpleDateFormat���̰߳�ȫ
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
	 * ȡ��ǰʱ�䣬��ʽΪyyyy-MM-dd HH:mm:ss:SSS
	 * @return
	 */
    public static String format() {
		return getDateFormat().format(new Date());
	}
    
	/**
	 * ȡ��ǰʱ�䣬�������ʽ
	 * @return
	 */
    public static String format(String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	/**
	 * �Ѵ��������ת�����ַ�������ʽΪyyyy-MM-dd HH:mm:ss:SSS
	 * @param calendar
	 * @return
	 */
	public static String format(Calendar calendar){
		return getDateFormat().format(calendar.getTime());
	}
	/**
	 * �Ѵ��������ת�����ַ������������ʽ
	 * @param calendar
	 * @return
	 */
	public static String format(Calendar calendar, String format){
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(calendar.getTime());
	}
	/**
	 * �Ѵ��������ת�����ַ�������ʽΪyyyy-MM-dd HH:mm:ss:SSS
	 * @param date
	 * @return
	 */
	public static String format(Date date){
		return getDateFormat().format(date);
	}
	/**
	 * �Ѵ��������ת�����ַ������������ʽ
	 * @param date
	 * @return
	 */
	public static String format(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * �Ѵ��������ת�����ַ������������ʽ
	 * @param date
	 * @return
	 */
	public static String format(Timestamp time, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}
	/**
	 * ȡ��ǰ���ڣ�ʱ�䲿����0
	 * @return
	 */
	public static Date getDate(){
		Calendar ca = Calendar.getInstance();
		return clearTime(ca).getTime();
	}
	
	/**
	 * �Ѵ����������ʱ�䲿����0
	 * @param theDate
	 * @return
	 */
	public static Date getDate(Date theDate){
		Calendar ca = Calendar.getInstance();
		ca.setTime(theDate);
		return clearTime(ca).getTime();
	}
	/**
	 * ����������ȡǰn���ʱ��,ʱ�䲿����0
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
	 * ȡǰn���ʱ��,ʱ�䲿����0
	 * @param days
	 * @return Calendar
	 */
	public static Calendar getCalendarBefore(int days){
		return getCalendarBefore(Calendar.getInstance(), days);
	}
	/**
	 * ����������ȡǰn���ʱ��,ʱ�䲿����0
	 * @param theDate
	 * @param days
	 * @return Calendar
	 */
	public static Calendar getCalendarBefore(Calendar ca, int days){
		ca.add(Calendar.DATE, -1 * days);
		return clearTime(ca);
	}
	/**
	 * ���һ�����ڵ�ʱ�䲿��
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
	    sdf.applyPattern("yyyy-MM"); // 201203��ʽ  
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
		    sdf.applyPattern("yyyy"); // 201203��ʽ  
		    return sdf.format(new Date());
	 }
	 public static String MonthNum(){
		 SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);  
		 sdf.applyPattern("MM"); // 201203��ʽ  
		 return sdf.format(new Date());
	 }
	 public static String DayNum(){
		 SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);  
		 sdf.applyPattern("dd"); // 201203��ʽ  
		 return sdf.format(new Date());
	 }
	/**
	 * ����Timestamp���͵ĵ�ǰʱ��
	 * @return
	 */
	public static Timestamp getTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * ��ȡ������ʱ����ʱ���
	 * @return
	 */
	public static String getSysTime(){
		Date dNow = new Date();
		//����Ŀ¼����
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		//�����ļ����ƺ�׺
		SimpleDateFormat formatT = new SimpleDateFormat("HHmmss");
		
		StringBuilder sb = new StringBuilder();
		//��ǰ����
		sb.append(format.format(dNow));
		//��ǰʱ����
		sb.append(formatT.format(dNow));
		return sb.toString();
	}
	
	/**
	 * ��ȡ������ʱ����ʱ���
	 * @return
	 */
	public static String getSysDate(){
		Date dNow = new Date();
		//����Ŀ¼����
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		StringBuilder sb = new StringBuilder();
		//��ǰ����
		sb.append(format.format(dNow));
		return sb.toString();
	}
	/**
	 * ��ȡ��ǰ���ڼ���ǰ������
	 * @param date
	 * @return
	 */
	public static String getSysDateYears(int years) {
		StringBuilder date = new StringBuilder();
		String yearNum = YearNum();
		int yearsNum = Integer.valueOf(yearNum) - years;
		String monthNum = MonthNum();
		String dayNum = DayNum();
		date.append(yearsNum).append("-").append(monthNum).append("-").append(dayNum);
		return date.toString();
	}
}