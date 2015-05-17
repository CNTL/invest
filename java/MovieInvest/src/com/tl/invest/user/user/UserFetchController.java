package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;

import com.tl.common.ResourceMgr;
import com.tl.common.StringUtils;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.invest.constant.DicTypes;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
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
		} else if("jobTypes".equals(action)){//职位
			jobTypes(response);
		}
	}
	
	private void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//取得租户代号
		String name = get(request, "q"); //固定参数名，q
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		int i = 0;
		int maxCount = 10;
		
		//String sql = "SELECT id, code, name, perNickName,head,point FROM user where perNickName like '%"+name+"%' or name like '%"+name+"%'  or code like '%"+name+"%'";
		String sql = "SELECT id, code, name, perNickName,head,headcard,point FROM user where perNickName like '%"+name+"%'  and deleted=0 ";
		
		DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	//按数据库类型获得不同的查询个数限制语句
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql);
	    	 
			while (rs.next()) {
				if (i++ > 0) result.append(",");
				result.append("{\"id\":\"").append(rs.getInt("id"))
					.append("\",\"code\":\"").append((rs.getString("code")!=null)?rs.getString("code"):"")
					.append("\",\"point\":\"").append((rs.getString("point")!=null)?rs.getString("point"):"")
					.append("\",\"name\":\"").append((rs.getString("perNickName")!=null)?rs.getString("perNickName"):"")
					.append("\",\"head\":\"").append(StringUtils.convertUrlChar((rs.getString("head")!=null)?rs.getString("head"):""))
					.append("\",\"headcard\":\"").append(StringUtils.convertUrlChar((rs.getString("headcard")!=null)?rs.getString("headcard"):""))
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
	private void jobTypes(HttpServletResponse response) throws Exception {
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] areas = dicReader.getDics(DicTypes.DIC_JOB_TYPE.typeID());
		
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary area : areas) {
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":"+area.getId());
			sb1.append(",\"pid\":"+area.getPid());
			sb1.append(",\"name\":\""+area.getName()+"\"");
			sb1.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"jobTypes\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
}