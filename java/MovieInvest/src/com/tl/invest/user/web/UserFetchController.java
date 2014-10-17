package com.tl.invest.user.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.ResourceMgr;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;

/** 
 * @created 2014年9月3日 上午8:52:51 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({"rawtypes"})
public class UserFetchController extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("find".equals(action)){//查找用户
			find(request, response);
		}
	}
	
	private void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//取得租户代号
		String name = get(request, "q"); //固定参数名，q
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		int i = 0;
		int maxCount = 20;
		
		String sql = "SELECT id, code, name, nickName FROM user where code like ?";
		
		DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql, new Object[]{name + "%"});
			while (rs.next()) {
				if (i++ > 0) result.append(",");
				result.append("{key:\"").append(rs.getInt("id"))
					.append("\",value:\"").append(rs.getString("code"))
					.append("\"}");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(conn);
		}
		result.append("]");
		
		output(result.toString(), response);
	}
}
