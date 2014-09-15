package com.tl.sys.org;

import java.util.List;

import org.hibernate.HibernateException;



/**
 * @created 04-七月-2005 14:52:30
 * @version 1.0
 * @updated 11-七月-2005 12:42:13
 */
public interface UserManager{

	/**
	 * 添加用户
	 * @param user    用户对象
	 * 
	 */
	public void create(User user) throws Exception;

	/**
	 * 更新用户信息
	 * @param user    用户对象
	 * @throws HibernateException
	 * 
	 */
	public void update(User user) throws Exception;

	/**
	 * 删除用户
	 * @param userID    用户ID
	 * @throws HibernateException
	 * 
	 */
	public void delete(int userID) throws Exception;
	
	/**
	 * 获取所有用户
	 * @return
	 * @throws Exception
	 */
	public List<User> getAll() throws Exception;

	/**
	 * 对参数的用户ID数组指定用户进行排序
	 * 
	 * @param userID 用户ID数组
	 */
	public void sortUsers(int[] userID) throws Exception;

}