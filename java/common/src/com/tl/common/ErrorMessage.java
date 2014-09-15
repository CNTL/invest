package com.tl.common;

/**
 * 对控制层的一个错误的描述,是一个(key, message)值对，
 * 其中key表示错误的类别标签，作为国际化标签，代表错误类型；
 * message是相对具体的信息，如参数值等。
 * @author  leijj 
 * @version 创建时间：2014-8-22
 */
public class ErrorMessage {
	/**
	 * 返回给View的属性中，代表错误信息的属性的名称。用法是：
	 * <BR> model.put(ErrorMessage.errors, alistvalue);
	 * <BR>这个表示错误信息的属性是一个List，是<code>ErrorMessage</code>
	 * 的列表。
	 * <BR>一个Controller可能一次性检测出多种错误，如
	 * flow.error.typeMismatch, flow.error.idError等。
	 * <BR>这些错误写在List中返回给客户端，进行显示处理。
	 * 
	 * <BR>当发生程序没有控制的异常时，<code>BaseController</code>的
	 * handleRequest会自动把异常信息写进List。
	 */
	public static final String errors = "errors";

	/**
	 * 缺省的错误的标签名
	 * <BR><code>handleRequest</code>中捕获的异常使用这个缺省错误标签来代表，
	 * 一般是handle方法中没有捕捉的异常，如数据库临时异常、IO异常等
	 */
	public static final String defaultErrorKey = "fail";
	
	private String key;
	private String message;

	public String getKey() {
		return key;
	}
	public String getMessage() {
		return message;
	}
	
	public ErrorMessage(String key, String message) {
		this.key = key;
		this.message = message;
	}	
}

