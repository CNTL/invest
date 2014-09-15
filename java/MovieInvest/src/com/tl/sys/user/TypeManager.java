package com.tl.sys.user;

import java.util.List;

import com.tl.kernel.context.DAOHelper;

/** 
 * @created 2014年9月3日 上午8:59:02 
 * @author  leijj
 * 类说明 ： 用户注册类型
 */
@SuppressWarnings("rawtypes")
public class TypeManager {
	
	/** 
	* @author  leijj 
	* 功能： 获取所有注册类型
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
