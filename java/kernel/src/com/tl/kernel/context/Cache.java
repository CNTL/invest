package com.tl.kernel.context;

/**
 * ����ӿ�
 * ÿ��ʵ�ֻ�����඼�̳д˽ӿ�
 */
public interface Cache {
	/**
	 * ����ˢ��
	 */
	public void refresh() throws Exception;
	
	/**
	 * ��������
	 */
	public void reset();
}
