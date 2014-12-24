package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.constant.DicTypes;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
/** 
 * @created 2014��12��18�� ����9:52:04 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PeopleController extends UserMainController {
	User user = null;
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("detail".equals(action)){//�û���ҳ
			detail(request, response, model);
		} else if("photo".equals(action)){//�û�ͼ��
			photo(request, response, model);
		} else if("video".equals(action)){//�û���Ƶ
			video(request, response, model);
		}
		
	}
	
	private void detail(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		int id = getInt(request, "id", 0);
		if(id > 0){
			UserManager userManager = (UserManager)Context.getBean(UserManager.class);
			user = userManager.getUserByID(id);
			if(user!=null){
				DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
				int perJob = Integer.valueOf(user.getPerJob());//ְҵ
				Dictionary dic = dicReader.getDic(DicTypes.DIC_JOB_TYPE.typeID(), perJob);
				user.setPerJobName(dic.getName());
				model.put("user", user);
			} else {
				model.put("user", new User());
			}
		}
	}
	private void video(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		//�̳�ʵ��
		int groupID = getInt(request, "groupID");
		model.put("groupID", groupID);
		this.detail(request, response, model);
	}
	private void photo(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		//�̳�ʵ��
		int groupID = getInt(request, "groupID");
		model.put("groupID", groupID);
		this.detail(request, response, model);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", (user !=null ? user.getName():"") + "����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", user !=null ? user.getName():"����ӳ��");
	}
}