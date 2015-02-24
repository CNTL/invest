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
 * @created 2014��9��3�� ����8:52:51 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({"rawtypes"})
public class UserFetchController extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("find".equals(action)){//�����û�
			find(request, response);
		} else if("jobTypes".equals(action)){//ְλ
			jobTypes(response);
		}
	}
	
	private void find(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ȡ���⻧����
		String name = get(request, "q"); //�̶���������q
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		int i = 0;
		int maxCount = 10;
		
		String sql = "SELECT id, code, name, perNickName,head FROM user where perNickName like '%"+name+"%' or name like '%"+name+"%'  or code like '%"+name+"%'";
		
		DBSession conn = null;
		IResultSet rs = null;
		try {
	    	conn = Context.getDBSession();
	    	//�����ݿ����ͻ�ò�ͬ�Ĳ�ѯ�����������
	    	sql = conn.getDialect().getLimitString(sql, 0, maxCount);
	    	
	    	rs = conn.executeQuery(sql);
	    	 
			while (rs.next()) {
				if (i++ > 0) result.append(",");
				result.append("{\"id\":\"").append(rs.getInt("id"))
					.append("\",\"code\":\"").append((rs.getString("code")!=null)?rs.getString("code"):"")
					.append("\",\"name\":\"").append((rs.getString("perNickName")!=null)?rs.getString("perNickName"):"")
					.append("\",\"head\":\"").append(StringUtils.convertUrlChar((rs.getString("head")!=null)?rs.getString("head"):""))
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