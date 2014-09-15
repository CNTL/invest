package com.tl.sys.org;

import java.util.List;

import org.hibernate.HibernateException;



/**
 * @created 04-����-2005 14:52:30
 * @version 1.0
 * @updated 11-����-2005 12:42:13
 */
public interface UserManager{

	/**
	 * ����û�
	 * @param user    �û�����
	 * 
	 */
	public void create(User user) throws Exception;

	/**
	 * �����û���Ϣ
	 * @param user    �û�����
	 * @throws HibernateException
	 * 
	 */
	public void update(User user) throws Exception;

	/**
	 * ɾ���û�
	 * @param userID    �û�ID
	 * @throws HibernateException
	 * 
	 */
	public void delete(int userID) throws Exception;
	
	/**
	 * ��ȡ�����û�
	 * @return
	 * @throws Exception
	 */
	public List<User> getAll() throws Exception;

	/**
	 * �Բ������û�ID����ָ���û���������
	 * 
	 * @param userID �û�ID����
	 */
	public void sortUsers(int[] userID) throws Exception;

}