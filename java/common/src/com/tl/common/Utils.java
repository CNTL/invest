package com.tl.common;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tl.common.Properties;
import com.tl.common.StringUtils;
import com.tl.common.Utils;

public class Utils {

	private static final Pattern PATTERN_XML_ESCAPES= Pattern.compile(".*[&|\"|\'|<|>].*");
	private static final Map<String, Locale> AVAILABLE_LOCALES		= new HashMap<String, Locale>();
	private static final char[][] XML_ESCAPE_CHARS					= new char[63][];
	
	/** ���ַ��� */
	public static final String EMPTY_STRING				= "";
	/** ���ַ��� */
	public static final String DEFAULT_ENCODING			= "UTF-8";
	/** ��ǰ����ϵͳƽ̨ */
	public static final String OS_PLATFORM				= getOSName();
	/** ��ǰ����ϵͳƽ̨�Ƿ�Ϊ Windows */
	public static final boolean IS_WINDOWS_PLATFORM		= isWindowsPlatform();
	/** ��ǰ����ϵͳƽ̨�Ļ��з� */
	public static final String NEWLINE_CHAR				= IS_WINDOWS_PLATFORM ? "\r\n" : "\n";
	
	static
	{
		Locale[] locales = Locale.getAvailableLocales();
		for(Locale locale : locales)
			AVAILABLE_LOCALES.put(locale.toString(), locale);
		
		XML_ESCAPE_CHARS[38] = "&amp;"	.toCharArray();
		XML_ESCAPE_CHARS[60] = "&lt;"	.toCharArray();
		XML_ESCAPE_CHARS[62] = "&gt;"	.toCharArray();
		XML_ESCAPE_CHARS[34] = "&quot;"	.toCharArray();
		XML_ESCAPE_CHARS[39] = "&apos;"	.toCharArray();
	}
	
	/** ��ȡϵͳ֧�ֵ����� {@link Locale} */
	public final static Map<String, Locale> getAvailableLocales()
	{
		return AVAILABLE_LOCALES;
	}
	
	/** ��ȡϵͳ֧�ֵ�ָ�����Ƶ� {@link Locale} */
	public final static Locale getAvailableLocale(String locale)
	{
		return AVAILABLE_LOCALES.get(locale);
	}
	
	/** �û������� XML �����ַ� */
	public final static String escapeXML(String str)
	{
		if(!PATTERN_XML_ESCAPES.matcher(str).matches())
			return str;
		
		char[] src		 = str.toCharArray();
		StringBuilder sb = new StringBuilder(src.length);
		
		for(char c : src)
		{
			if(c > '>' || c < '"')
				sb.append(c);
			else
			{
				char[] dest = XML_ESCAPE_CHARS[c];
				
				if(dest == null)
					sb.append(c);
				else
					sb.append(dest);
			}
		}
		
		return sb.toString();
	}
	
	/** ����������ʽ��ת���ַ����������� ignores �����а������ַ��� */
	public static final String escapeRegexChars(String str, char ... ignores)
	{
		final char ESCAPE_CHAR	 = '\\';
		final char[] REGEX_CHARS = {'.', ',', '?', '+', '-', '*', '^', '$', '|', '&', '{', '}', '[', ']', '(', ')', '\\'};
		
		char[] regex_chars = REGEX_CHARS;
		
		if(ignores.length > 0)
		{
			Set<Character> cs = new HashSet<Character>(REGEX_CHARS.length);
			
			for(int i = 0; i < REGEX_CHARS.length; i++)
				cs.add(REGEX_CHARS[i]);
			for(int i = 0; i < ignores.length; i++)
				cs.remove(ignores[i]);
				
			int i		= 0;
			regex_chars = new char[cs.size()];
			Iterator<Character> it = cs.iterator();
			
			while(it.hasNext())
				regex_chars[i++] = it.next();				
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			
			for(int j = 0; j < regex_chars.length; j++)
			{
				if(c == regex_chars[j])
				{
					sb.append(ESCAPE_CHAR);
					break;
				}
			}
			
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	
	/** ����ת���������ӿ� */
	public static interface TypeHandler<T>
	{
		T handle(String v);
	}
	
	
	/** String -> Any���ַ���ת��Ϊ 8 �ֻ����������͡������װ�� {@link Date}�� �� {@link String} 
	 * 
	 * @param type	: Ŀ�����͵� {@link Class} ����
	 * @param v		: Ҫת�����ַ���
	 * @return		: ת����������ת�����ɹ����� null
	 * @throws 		: ���Ŀ�����Ͳ�֧���׳� {@link IllegalArgumentException}
	 * 
	 */
	public static final <T> T str2Object(Class<T> type, String v)
	{
		return str2Object(type, v, null);
	}
	
	/** String -> Any����� handler Ϊ null ����ַ���ת��Ϊ 8 �ֻ����������͡������װ�ࡢ {@link Date} �� {@link String}��
	 * 			      ��� handler ��Ϊ null ���� handler ִ��ת�� 
	 * 
	 * @param type	: Ŀ�����͵� {@link Class} ����
	 * @param v		: Ҫת�����ַ���
	 * @param handler	: ����ת��������
	 * @return		: ת����������ת�����ɹ����� null
	 * @throws 		: ���Ŀ�����Ͳ�֧���׳� {@link IllegalArgumentException}
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T str2Object(Class<T> type, String v, TypeHandler<T> handler)
	{
		Object param = null;
		
		if(handler != null)
			return handler.handle(v);
		
		if(type == String.class)
			param =  StringUtils.safeTrimString(v);
		else if(type == int.class)
			param =  StringUtils.toInt(v,0);
		else if(type == long.class)
			param =  StringUtils.toLong(v,0L);
		else if(type == byte.class)
			param =  StringUtils.toByte(v,(byte)0);
		else if(type == char.class)
			param =  StringUtils.toChar(v, Character.MIN_VALUE);
		else if(type == float.class)
			param =  StringUtils.toFloat(v,0F);
		else if(type == double.class)
			param =  StringUtils.toDouble(v,0D);
		else if(type == short.class)
			param =  StringUtils.toShort(v,(short)0);
		else if(type == boolean.class)
			param =  StringUtils.toBoolean(v,false);
		else if(type == Integer.class)
			param =  StringUtils.toInt(v);
		else if(type == Long.class)
			param =  StringUtils.toLong(v);
		else if(type == Byte.class)
			param =  StringUtils.toByte(v);
		else if(type == Character.class)
			param =  StringUtils.toChar(v);
		else if(type == Float.class)
			param =  StringUtils.toFloat(v);
		else if(type == Double.class)
			param =  StringUtils.toDouble(v);
		else if(type == Short.class)
			param =  StringUtils.toShort(v);
		else if(type == Boolean.class)
			param =  StringUtils.toBoolean(v);
		else if(Date.class.isAssignableFrom(type))
			param =  StringUtils.toDate(v);
		else
			throw new IllegalArgumentException(String.format("object type '%s' not valid", type));
		
		return (T)param;
	}
	
	
	/** Any -> Object[] <br>
	 * 
	 *  obj == null					: ���� Object[] {null} <br>
	 *  obj Ϊ��������				: ǿ��ת��Ϊ Object[], ���������� <br>
	 *  obj Ϊ������������			: ���� Object[], ��Ԫ������Ϊ�������͵İ�װ�� <br>
	 *  obj Ϊ {@link Collection}	: ͨ�� toArray() �������� Object[] <br>
	 *  obj Ϊ {@link Iterable}		: ���� {@link Iterable}, �����ذ���������Ԫ�ص� Object[] <br>
	 *  obj Ϊ {@link Iterator}		: ���� {@link Iterator}, �����ذ���������Ԫ�ص� Object[] <br>
	 *  obj Ϊ {@link Enumeration}	: ���� {@link Enumeration}, �����ذ���������Ԫ�ص� Object[] <br>
	 *  obj Ϊ��ͨ����				: ���� Object[] {obj} <br>
	 * 
	 * @param obj	: �κζ���
	 * 
	 */
	public static final Object[] object2Array(Object obj)
	{
		Object[] array;
		
		if(obj == null)
			array = new Object[] {obj};
		else if(obj.getClass().isArray())
		{
			Class<?> clazz = obj.getClass().getComponentType();
			
			if(Object.class.isAssignableFrom(clazz))
				array = (Object[])obj;
			else
			{
				int length = Array.getLength(obj);
				
				if(length > 0)
				{
					array = new Object[length];
					
					for(int i = 0; i < length; i++)
						array[i] = Array.get(obj, i);
				}
				else
					array = new Object[0];
			}
		}
		else if(obj instanceof Collection<?>)
			array = ((Collection<?>)obj).toArray();
		else if(obj instanceof Iterable<?>)
		{
			List<Object> list = new ArrayList<Object>();
			Iterator<?> it = ((Iterable<?>)obj).iterator();
			
			while(it.hasNext())
				list.add(it.next());
			
			array = list.toArray();
		}
		else if(obj instanceof Iterator)
		{
			List<Object> list = new ArrayList<Object>();
			Iterator<?> it = (Iterator<?>)obj;
			
			while(it.hasNext())
				list.add(it.next());
			
			array = list.toArray();
		}
		else if(obj instanceof Enumeration<?>)
		{
			List<Object> list = new ArrayList<Object>();
			Enumeration<?> it = (Enumeration<?>)obj;
			
			while(it.hasMoreElements())
				list.add(it.nextElement());
			
			array = list.toArray();
		}
		else
			array = new Object[] {obj};
		
		return array;
	}
	
	
	/** ��ȡ clazz �� {@link ClassLoader} �������Ϊ null �򷵻ص�ǰ�̵߳� Context {@link ClassLoader} */
	public static final ClassLoader getClassLoader(Class<?> clazz)
	{
		ClassLoader loader = clazz.getClassLoader();
		
		if(loader == null)
			loader = Thread.currentThread().getContextClassLoader();
		
		return loader;
	}
	
	/** ��������Ϊ  className �� {@link Class} �����������ʧ���򷵻� null */
	public static final Class<?> loadClass(String className)
	{
		Class<?> clazz		= null;
		ClassLoader loader	= getClassLoader(Utils.class);
		
		try
		{
			clazz = loader.loadClass(className);
		}
		catch(ClassNotFoundException e)
		{
			
		}
		
		return clazz;
	}

	/** �� {@linkplain Class#forName(String)} ���� {@link Class} �����������ʧ���򷵻� null */
	public static final Class<?> classForName(String name)
	{
		Class<?> clazz = null;
		
		try
		{
			clazz = Class.forName(name);
		}
		catch(ClassNotFoundException e)
		{
			
		}
		
		return clazz;
	}

	/** �� {@linkplain Class#forName(String, boolean, ClassLoader)} ���� {@link Class} �����������ʧ���򷵻� null */
	public static final Class<?> classForName(String name, boolean initialize, ClassLoader loader)
	{
		Class<?> clazz = null;
		
		try
		{
			clazz = Class.forName(name, initialize, loader);
		}
		catch(ClassNotFoundException e)
		{
			
		}
		
		return clazz;
	}

	/** ��ȡ clazz ��Դ������ resPath ���·���� URL ���� */
	public static final URL getClassResource(Class<?> clazz, String resPath)
	{
		URL url = clazz.getResource(resPath);
		
		if(url == null)
		{
			ClassLoader loader = clazz.getClassLoader();
			if(loader != null) url = loader.getResource(resPath);
			
			if(url == null)
			{
				loader = Thread.currentThread().getContextClassLoader();
				if(loader != null) url = loader.getResource(resPath);
			}
		}

		return url;
	}

	/** ��ȡ clazz ��Դ������ resPath ���·���� URL �����б� */
	public static final List<URL> getClassResources(Class<?> clazz, String resPath)
	{
		List<URL> urlList		= new ArrayList<URL>();
		Enumeration<URL> urls	= null;
		
		try
		{
    		ClassLoader loader = clazz.getClassLoader();
    		if(loader != null) urls = loader.getResources(resPath);
    		
    		if(urls == null || !urls.hasMoreElements())
    		{
    			loader = Thread.currentThread().getContextClassLoader();
    			if(loader != null) urls = loader.getResources(resPath);
    		}
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
		
		if(urls != null)
		{
			while(urls.hasMoreElements())
				urlList.add(urls.nextElement());
		}

		return urlList;
	}

	/** ��ȡ clazz ��Դ������ resPath �� {@link InputStream} */
	public static final InputStream getClassResourceAsStream(Class<?> clazz, String resPath)
	{
		InputStream is = clazz.getResourceAsStream(resPath);
		
		if(is == null)
		{
			ClassLoader loader = clazz.getClassLoader();
			if(loader != null) is = loader.getResourceAsStream(resPath);
			
			if(is == null)
			{
				loader = Thread.currentThread().getContextClassLoader();
				if(loader != null) is = loader.getResourceAsStream(resPath);
			}
		}

		return is;
	}

	/** ��ȡ clazz ��Դ������ resPath ���·���� URL ����·���������ľ���·���� UTF-8 ���룩 */
	public static final String getClassResourcePath(Class<?> clazz, String resPath)
	{
		return getClassResourcePath(clazz, resPath, DEFAULT_ENCODING);
	}

	/** ��ȡ clazz ��Դ������ resPath ���·���� URL ����·���������ľ���·���� pathEnc ���룩 */
	public static final String getClassResourcePath(Class<?> clazz, String resPath, String pathEnc)
	{
		String path = null;

		try
		{
			URL url = getClassResource(clazz, resPath);
			
			if(url != null)
			{
				path = url.getPath();
				path = URLDecoder.decode(path, pathEnc);
			}
		}
		catch(UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}

		return path;
	}

	/** ��ȡ clazz ��Դ������ resPath ���·���� URL ����·���б������ľ���·���� UTF-8 ���룩 */
	public static final List<String> getClassResourcePaths(Class<?> clazz, String resPath)
	{
		return getClassResourcePaths(clazz, resPath, DEFAULT_ENCODING);
	}

	/** ��ȡ clazz ��Դ������ resPath ���·���� URL ����·���б������ľ���·���� pathEnc ���룩 */
	public static final List<String> getClassResourcePaths(Class<?> clazz, String resPath, String pathEnc)
	{
		List<String> pathList = new ArrayList<String>();

		try
		{
			List<URL> urlList = getClassResources(clazz, resPath);
			
			for(URL url : urlList)
			{
				String path = URLDecoder.decode(url.getPath(), pathEnc);
				pathList.add(path);
			}
		}
		catch(UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}

		return pathList;
	}

	/** ��ȡ clazz ��Դ�����ĵ�ǰ URL ����·�������صľ���·���� pathEnc ���룩 */
	public static final String getClassPath(Class<?> clazz)
	{
		return getClassResourcePath(clazz, ".");
	}

	/** ��ȡ resource ��Դ�� locale ���ػ��ļ�������Ϊ key ���ַ�����Դ�������� params ���� */
	public static final String getResourceMessage(Locale locale, String resource, String key, Object ... params)
	{
		ResourceBundle bundle = ResourceBundle.getBundle(resource, locale);
		String msg = bundle.getString(key);

		if(params != null && params.length > 0)
			msg = MessageFormat.format(msg, params);

		return msg;
	}

	/** ��ȡ resource ��Դ��Ĭ�ϱ��ػ��ļ�������Ϊ key ���ַ�����Դ�������� params ���� */
	public static final String getResourceMessage(String resource, String key, Object ... params)
	{
		return getResourceMessage(Locale.getDefault(), resource, key, params);
	}

	/** ��ȡ e �쳣�Ķ�ջ�б�����Ķ�ջ������ levels ָ�� */
	public static final List<String> getExceptionMessageStack(Throwable e, int levels)
	{
		List<String> list = new ArrayList<String>();

		if(levels == 0)
			levels = Integer.MAX_VALUE;

		for(int i = 0; i < levels; ++i)
		{
			StringBuilder sb = new StringBuilder();

			if(i > 0)
				sb.append("Caused by -> ");

			sb.append(e.getClass().getName());
			String msg = e.getLocalizedMessage();
			if(msg != null)
				sb.append(": ").append(msg);

			list.add(sb.toString());

			e = e.getCause();
			if(e == null)
				break;
		}

		return list;
	}

	/** ��ȡ e �쳣��������ջ�б� */
	public static final List<String> getExceptionMessageStack(Throwable e)
	{
		return getExceptionMessageStack(e, 0);
	}

	/** ��� e �쳣�� levels ���ջ�б� ps �� */
	public static final void printExceptionMessageStack(Throwable e, int levels, PrintStream ps)
	{
		List<String> list = getExceptionMessageStack(e, levels);

		for(String msg : list)
			ps.println(msg);
	}

	/** ��� e �쳣�� levels ���ջ�б���׼�������� */
	public static final void printExceptionMessageStack(Throwable e, int levels)
	{
		printExceptionMessageStack(e, levels, System.err);
	}

	/** ��� e �쳣��������ջ�б� ps �� */
	public static final void printExceptionMessageStack(Throwable e, PrintStream ps)
	{
		printExceptionMessageStack(e, 0, ps);
	}

	/** ��� e �쳣��������ջ�б���׼�������� */
	public static final void printExceptionMessageStack(Throwable e)
	{
		printExceptionMessageStack(e, 0);
	}
	
	
	/** ��ȡ��ǰ JVM ���̵� ID */
	public static final int getProcessId()
	{
		return Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
	}

	/** ��ȡ��ǰ JVM ���̵� Java �汾 */
	public static final String getJavaVersion()
	{
		return System.getProperty("java.version");
	}

	/** ��ȡ��ǰ����ϵͳ������ */
	public static final String getOSName()
	{
		return System.getProperty("os.name");
	}

	/** ��鵱ǰ����ϵͳ�Ƿ�Ϊ Windows ϵ�� */
	public static final boolean isWindowsPlatform()
	{
		return File.pathSeparatorChar == ';';
	}

	/** ��ƴ��������ַ����Ƚ��� */
	public static class PinYinComparator implements Comparator<String>
	{
		@Override
		public int compare(String o1, String o2)
		{
			java.text.Collator cmp = java.text.Collator.getInstance(java.util.Locale.CHINA);
			return cmp.compare(o1, o2);
		}
	}

	/** ���ļ����ƽ����ļ�ɸѡ���ļ������������캯������ name ָ���ļ�����������ʽ */
	public static class FileNameFileFilter implements FileFilter
	{
		protected static final int FLAGS = IS_WINDOWS_PLATFORM ? Pattern.CASE_INSENSITIVE : 0;

		Pattern pattern;

		public FileNameFileFilter(String name)
		{
			String exp = name;
			exp = exp.replace('.', '#');
			exp = exp.replaceAll("#", "\\\\.");
			exp = exp.replace('*', '#');
			exp = exp.replaceAll("#", ".*");
			exp = exp.replace('?', '#');
			exp = exp.replaceAll("#", ".?");
			exp = "^" + exp + "$";

			pattern = Pattern.compile(exp, FLAGS);
		}

		@Override
		public boolean accept(File file)
		{
			Matcher matcher = pattern.matcher(file.getName());
			return matcher.matches();
		}
	}

	
	public static String getPath(String file){
		String path = "";
		
		String classPath = (new Properties()).getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		classPath = classPath.replace("\\", "/");
		if(classPath.indexOf("/WEB-INF/")==-1){
			path = classPath.substring(1, classPath.lastIndexOf("/"));
		}else {
			path = classPath.substring(1, classPath.indexOf("/WEB-INF/")+8);
		}
	  		
//		File directory = new File(System.getProperty("user.dir")); //�趨Ϊ��ǰ�ļ���   
//		try{
//			path = directory.getAbsolutePath();
//		    //System.out.println(directory.getCanonicalPath());//��ȡ��׼��·��   
//		    //System.out.println(directory.getAbsolutePath());//��ȡ����·��   
//		}catch(Exception e){}
		
		path = ((path.endsWith("/") || path.endsWith("\\")) ? path.substring(0,path.length()-1) : path)+File.separator
				+((file.startsWith("/") || file.startsWith("\\")) ? file.substring(1,file.length()) : file);
		if(File.separator.equals("\\")) 
			path = path.replaceAll("/", "\\\\");
		else
			path = path.replaceAll("\\\\", "/");
		if(!IS_WINDOWS_PLATFORM) path = "/"+path;
		return path;
	}
}
