package com.tl.invest.user.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.constant.DicTypes;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
/** 
 * @created 2014年12月18日 上午9:52:04 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PeopleController extends UserMainController {
	User user = null;
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("detail".equals(action)){//用户主页
			detail(request, response, model);
		} else if("photo".equals(action)){//用户图册
			photo(request, response, model);
		} else if("video".equals(action)){//用户视频
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
				int perJob = Integer.valueOf(user.getPerJob());//职业
				Dictionary dic = dicReader.getDic(DicTypes.DIC_JOB_TYPE.typeID(), perJob);
				user.setPerJobName(dic.getName());
				model.put("user", user);
			} else {
				model.put("user", new User());
			}
		}
		model.put("viewType", "detail");
	}
	private void video(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		this.detail(request, response, model);
		int groupID = getInt(request, "groupID");
		model.put("groupID", groupID);
		model.put("viewType", "video");
	}
	private void photo(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		this.detail(request, response, model);
		int groupID = getInt(request, "groupID");
		model.put("groupID", groupID);
		model.put("viewType", "photo");
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", (user !=null ? user.getName():"") + "合众映画");
		model.put("keywords", "合众映画");
		model.put("description", user !=null ? user.getName():"合众映画");
	}
}