package com.tl.sys.user;

import java.util.List;

import com.tl.kernel.context.DAOHelper;

/** 
 * @created 2014��9��3�� ����8:59:02 
 * @author  leijj
 * ��˵�� �� �û�ע������
 */
@SuppressWarnings("rawtypes")
public class TypeManager {
	
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡ����ע������
	* @return
	* @throws Exception 
	*/ 
	public List getAll() throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.sys.user.Type as a");
		querySql.append(" order by createTime desc");
		List list = DAOHelper.find(querySql.toString());
        if(list.size() > 0)
            return list;
        else
            return null;
        
	}
}
