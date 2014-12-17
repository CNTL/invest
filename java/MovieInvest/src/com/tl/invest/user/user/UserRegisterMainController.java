package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.WebUtil;
import com.tl.invest.workspace.Entry;

/** 
 * @created 2014��12��14�� ����12:05:18 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class UserRegisterMainController extends Entry {
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//�̳�ʵ��
		String loginCurrentUrl = WebUtil.getCookie(request, "loginCurrentUrl");//��¼����תҳ��
		String loginCurrentMenu = WebUtil.getCookie(request, "loginCurrentMenu");//��¼��һ���˵�
		model.put("loginCurrentUrl", loginCurrentUrl);
		model.put("loginCurrentMenu", loginCurrentMenu);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��¼");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
}