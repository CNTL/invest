package com.tl.kernel.context;

/**
 * ʵ��Spring��ApplicationContextAware�ӿ�
 * <BR>�����������Spring��context.xml��
 * <BR>��web container����ʱ��ȡSpring��ApplicationContext
 * <BR>Ȼ�󸳸�com.founder.e5.context.Context
 * 
 * @created 2014��9��7�� ����4:15:57 
 * @author  leijj
 * @version 1.0
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextAware implements ApplicationContextAware {
	private ApplicationContext mycontext;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		mycontext = arg0;
	}
	public void setToContext()
	{
		Context.setApplicationContext(mycontext);
	}
}