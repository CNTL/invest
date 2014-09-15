package com.tl.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class StringUtils {
	private static final Pattern PATTERN_NUMERIC	= Pattern.compile("^0$|^\\-?[1-9]+[0-9]*$");
	private static final Pattern PATTERN_EMAIL_ADDR	= Pattern.compile("^[a-z0-9_\\-]+(\\.[_a-z0-9\\-]+)*@([_a-z0-9\\-]+\\.)+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)$");
	private static final Pattern PATTERN_IP_ADDR	= Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
	private static final Pattern PATTERN_LINK		= Pattern.compile("<a[^>]*href=\\\"[^\\s\\\"]+\\\"[^>]*>[^<]*<\\/a>");
	private static final Pattern PATTERN_HTTP_URL	= Pattern.compile("^(https?:\\/\\/)?([a-z]([a-z0-9\\-]*\\.)+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\\/[a-z0-9_\\-\\.~]+)*(\\/([a-z0-9_\\-\\.]*)(\\?[a-z0-9+_\\-\\.%=&amp;]*)?)?(#[a-z][a-z0-9_]*)?$");
	
	private static final String[] SHORT_DATE_PATTERN 				= {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy\\MM\\dd", "yyyyMMdd"};
	private static final String[] LONG_DATE_PATTERN 				= {"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy\\MM\\dd HH:mm:ss", "yyyyMMddHHmmss"};
	private static final String[] LONG_DATE_PATTERN_WITH_MILSEC 	= {"yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy\\MM\\dd HH:mm:ss.SSS", "yyyyMMddHHmmssSSS"};
	
	/** ���ַ��� */
	public static final String EMPTY_STRING				= "";
	
	/** ����ַ�����Ϊ null ����ַ��� */
	public final static boolean isNotEmpty(String str)
	{
		return str != null && str.length() != 0;
	}

	/** ����ַ�����Ϊ null �����ַ�����ֻ�����ո� */
	public final static boolean isTrimNotEmpty(String str)
	{
		boolean result = isNotEmpty(str);
		return result ? isNotEmpty(str.trim()) : result;
	}

	/** ����ַ���Ϊ null ����ַ��� */
	public final static boolean isEmpty(String str)
	{
		return !isNotEmpty(str);
	}

	/** ����ַ���Ϊ null �����ַ�����ֻ�����ո� */
	public final static boolean isTrimEmpty(String str)
	{
		boolean result = isEmpty(str);
		return result ?  result : isEmpty(str.trim());
	}
	
	/** ����ַ����Ƿ����������ʽ */
	public final static boolean isNumeric(String str)
	{
		return PATTERN_NUMERIC.matcher(str).matches();
	}

	/** ����ַ����Ƿ���ϵ����ʼ���ʽ */
	public final static boolean isEmailAddress(String str)
	{
		return PATTERN_EMAIL_ADDR.matcher(str).matches();
	}

	/** ����ַ����Ƿ���� IP ��ַ��ʽ */
	public final static boolean isIPAddress(String str)
	{
		return PATTERN_IP_ADDR.matcher(str).matches();
	}

	/** ����ַ����Ƿ���� HTML ������Ԫ�ظ�ʽ */
	public final static boolean isLink(String str)
	{
		return PATTERN_LINK.matcher(str).matches();
	}

	/** ����ַ����Ƿ���� URL ��ʽ */
	public final static boolean isURL(String str)
	{
		return PATTERN_HTTP_URL.matcher(str).matches();
	}
	
	
	
	/** ���ָ��ַ������ָ����" \t\n\r\f,;"�� */
	public final static String[] split(String str)
	{
		return split(str, " \t\n\r\f,;");
	}
	
	/** ���ָ��ַ������ָ������ delim ����ָ���� */
	public final static String[] split(String str, String delim)
	{
		StringTokenizer st	= new StringTokenizer(str, delim);
		String[] array		= new String[st.countTokens()];
		
		int i = 0;
		while(st.hasMoreTokens())
			array[i++] = st.nextToken();
		
		return array;
	}
	
	
	/** String -> Integer�����ת�����ɹ��򷵻� null */
	public final static Integer toInteger(String s)
	{
		Integer returnVal;
		try {
			returnVal = Integer.decode(safeTrimString(s));
		} catch(Exception e) {
			returnVal = null;
		}
		return returnVal;
	}
	
	/** String -> int�����ת�����ɹ��򷵻� 0 */
	public final static int toInt(String s)
	{
		return toInt(s, 0);
	}

	/** String -> int�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static int toInt(String s, int d)
	{
		int returnVal;
		try {
			returnVal = Integer.parseInt(safeTrimString(s));
		} catch(Exception e) {
			returnVal = d;
		}
		return returnVal;
	}
	
	/** String -> Short�����ת�����ɹ��򷵻� null */
	public final static Short toShort(String s)
	{
		Short returnVal;
		try {
			returnVal = Short.decode(safeTrimString(s));
		} catch(Exception e) {
			returnVal = null;
		}
		return returnVal;
	}

	/** String -> short�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static short toShort(String s, short d)
	{
		short returnVal;
		try {
			returnVal = Short.parseShort(safeTrimString(s));
		} catch(Exception e) {
			returnVal = d;
		}
		return returnVal;
	}
	
	/** String -> Long�����ת�����ɹ��򷵻� null */
	public final static Long toLong(String s)
	{
		Long returnVal;
		try {
			returnVal = Long.decode(safeTrimString(s));
		} catch(Exception e) {
			returnVal = null;
		}
		return returnVal;
	}

	/** String -> long�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static long toLong(String s, long d)
	{
		long returnVal;
		try {
			returnVal = Long.parseLong(safeTrimString(s));
		} catch(Exception e) {
			returnVal = d;
		}
		return returnVal;
	}
	
	/** String -> Float�����ת�����ɹ��򷵻� null */
	public final static Float toFloat(String s)
	{
		Float returnVal;
		try {
			returnVal = Float.valueOf(safeTrimString(s));
		} catch(Exception e) {
			returnVal = null;
		}
		return returnVal;
	}

	/** String -> float�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static float toFloat(String s, float d)
	{
		float returnVal;
		try {
			returnVal = Float.parseFloat(safeTrimString(s));
		} catch(Exception e) {
			returnVal = d;
		}
		return returnVal;
	}
	
	/** String -> Double�����ת�����ɹ��򷵻� null */
	public final static Double toDouble(String s)
	{
		Double returnVal;
		try {
			returnVal = Double.valueOf(safeTrimString(s));
		} catch(Exception e) {
			returnVal = null;
		}
		return returnVal;
	}
	
	/** String -> double�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static double toDouble(String s, double d)
	{
		double returnVal;
		try {
			returnVal = Double.parseDouble(safeTrimString(s));
		} catch(Exception e) {
			returnVal = d;
		}
		return returnVal;
	}
	
	/** String -> Byte�����ת�����ɹ��򷵻� null */
	public final static Byte toByte(String s)
	{
		Byte returnVal;
		try {
			returnVal = Byte.decode(safeTrimString(s));
		} catch(Exception e) {
			returnVal = null;
		}
		return returnVal;
	}

	/** String -> byte�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static byte toByte(String s, byte d)
	{
		byte returnVal;
		try {
			returnVal = Byte.parseByte(safeTrimString(s));
		} catch(Exception e) {
			returnVal = d;
		}
		return returnVal;
	}
	
	/** String -> Character�����ת�����ɹ��򷵻� null */
	public final static Character toChar(String s)
	{
		Character returnVal;
		try {
			returnVal = safeTrimString(s).charAt(0);
		} catch(Exception e) {
			returnVal = null;
		}
		return returnVal;
	}

	/** String -> char�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static char toChar(String s, char d)
	{
		char returnVal;
		try {
			returnVal = safeTrimString(s).charAt(0);
		} catch(Exception e) {
			returnVal = d;
		}
		return returnVal;
	}
	
	/** String -> Boolean�����ת�����ɹ��򷵻� null */
	public final static Boolean toBoolean(String s)
	{
		return Boolean.valueOf(safeTrimString(s));
	}

	/** String -> boolean�����ת�����ɹ��򷵻�Ĭ��ֵ d */
	public final static boolean toBoolean(String s, boolean d)
	{
		s = safeTrimString(s);
		
		if(s.equalsIgnoreCase("true"))
			return true;
		else if(s.equalsIgnoreCase("false"))
			return false;
		
		return d;
	}
	
	/** String -> java.util.Date�� str �ĸ�ʽ�� format  ���� */
	public final static Date toDate(String str, String format)
	{
		Date date = null;
		
		try
		{
			DateFormat df = new SimpleDateFormat(format);
			date = df.parse(safeTrimString(str));
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
			String[] values			= safeTrimString(str).split("\\D");
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
	
	/** �Ѳ��� str ת��Ϊ��ȫ�ַ�������� str = null�������ת��Ϊ���ַ��� */
	public final static String safeString(String str)
	{
		if(str == null)
			str = "";
		
		return str;
	}

	/** �Ѳ��� obj ת��Ϊ��ȫ�ַ�������� obj = null�������ת��Ϊ���ַ��� */
	public final static String safeString(Object obj)
	{
		if(obj == null)
			return "";
		
		return obj.toString();
	}

	/** �Ѳ��� str ת��Ϊ��ȫ�ַ�����ִ��ȥ��ǰ��ո���� str = null�������ת��Ϊ���ַ��� */
	public final static String safeTrimString(String str)
	{
		return safeString(str).trim();
	}
	
	/**
	 * ����ַ���ĩβ���ض��ַ�<br>
	 * ���ַ���ĩβ���Ǹ����ַ�����ʲô������<br>
	 * ע�⣺�÷����ı��˴����StringBuffer������ֵ
	 * 
	 * @param sb �ַ�������
	 * @param tail �û������ַ�
	 * @return �ַ������������ַ�����ʾ
	 */
	public static String trimTail( StringBuffer sb, char tail ) {
		if ( sb.length() > 0 && sb.charAt( sb.length() - 1 ) == tail )
			sb.deleteCharAt( sb.length() - 1 );
		return sb.toString();
	}
	
	/**
	 * ��ģ���е�ռλ���滻Ϊ�����ַ������滻һ�Σ�
	 * 
	 * @param template ģ���ַ���
	 * @param placeholder ռλ�������滻�ַ���
	 * @param replacement �滻�ַ���
	 * @return ģ�屻�滻����ַ���
	 */
	public static String replaceOnce( String template, String placeholder,
			String replacement ) {
		int pos = template.indexOf( placeholder );
		if ( pos < 0 ) {
			return template;
		} else {
			return new StringBuffer( template.substring( 0, pos ) ).append(
					replacement ).append(
					template.substring( pos + placeholder.length() ) ).toString();
		}
	}
}
