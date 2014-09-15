package com.tl.kernel.context;

/**
 * 实现Spring的ApplicationContextAware接口
 * <BR>这个类配置在Spring的context.xml中
 * <BR>在web container启动时获取Spring的ApplicationContext
 * <BR>然后赋给com.founder.e5.context.Context
 * 
 * @created 2014年9月7日 下午4:15:57 
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