package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.WebUtil;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.sys.dic.Dictionary;

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
		String loginCurrentUrl = request.getParameter("url");
		//String loginCurrentMenu = WebUtil.getCookie(request, "loginCurrentMenu");//��¼��һ���˵�
		model.put("loginCurrentUrl", loginCurrentUrl);
		RecruitManager recruitManager = new RecruitManager();
		Dictionary[] types = recruitManager.types();
		model.put("types", types);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��¼");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
}
