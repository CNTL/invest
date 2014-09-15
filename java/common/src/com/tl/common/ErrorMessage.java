package com.tl.common;

/**
 * �Կ��Ʋ��һ�����������,��һ��(key, message)ֵ�ԣ�
 * ����key��ʾ���������ǩ����Ϊ���ʻ���ǩ������������ͣ�
 * message����Ծ������Ϣ�������ֵ�ȡ�
 * @author  leijj 
 * @version ����ʱ�䣺2014-8-22
 */
public class ErrorMessage {
	/**
	 * ���ظ�View�������У����������Ϣ�����Ե����ơ��÷��ǣ�
	 * <BR> model.put(ErrorMessage.errors, alistvalue);
	 * <BR>�����ʾ������Ϣ��������һ��List����<code>ErrorMessage</code>
	 * ���б�
	 * <BR>һ��Controller����һ���Լ������ִ�����
	 * flow.error.typeMismatch, flow.error.idError�ȡ�
	 * <BR>��Щ����д��List�з��ظ��ͻ��ˣ�������ʾ����
	 * 
	 * <BR>����������û�п��Ƶ��쳣ʱ��<code>BaseController</code>��
	 * handleRequest���Զ����쳣��Ϣд��List��
	 */
	public static final String errors = "errors";

	/**
	 * ȱʡ�Ĵ���ı�ǩ��
	 * <BR><code>handleRequest</code>�в�����쳣ʹ�����ȱʡ�����ǩ������
	 * һ����handle������û�в�׽���쳣�������ݿ���ʱ�쳣��IO�쳣��
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

