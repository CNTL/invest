package com.tl.invest.doc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
 


import javax.servlet.http.HttpServletResponse;

import com.tl.invest.workspace.Entry;
import com.tl.kernel.web.BaseController;

public class DocMainController extends Entry {

	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//�̳�ʵ��
		String loginCurrentUrl = request.getParameter("url");
 
		model.put("loginCurrentUrl", loginCurrentUrl);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��¼");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	 
	 
}
