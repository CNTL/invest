package com.tl.sys.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;

/** 
 * @created 2014��9��3�� ����8:52:51 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({"rawtypes"})
public class UserFetchController  extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("type".equals(action)){//��ȡ�û�ע������
			getTypes(request, response);
		} else if("find".equals(action)){//�����û�
			//find(request, response);
		}
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡע������
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void getTypes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		TypeManager typeManager = (TypeManager) Context.getBean(TypeManager.class);
		List types = typeManager.getAll();
		JSONObject jsonArray = JSONObject.fromObject(types);  
		output(jsonArray.toString(), response);
	}
}
