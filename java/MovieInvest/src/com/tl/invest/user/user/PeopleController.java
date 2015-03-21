package com.tl.invest.user.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.StringUtils;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.photo.PhotoManager;
import com.tl.invest.user.photo.UserPhoto;
import com.tl.invest.user.photo.UserPhotogroup;
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
//				if(user.getPerJob() != null && !"".equals(user.getPerJob())){
//					int perJob = Integer.valueOf(user.getPerJob());//ְҵ
//					Dictionary dic = dicReader.getDic(DicTypes.DIC_JOB_TYPE.typeID(), perJob);
//					user.setPerJobName(dic.getName());
//				}
				if(!StringUtils.isEmpty(user.getCity())){
					user.setCity(dicReader.getDic(DicTypes.DIC_AREA.typeID(), Integer.parseInt(user.getCity(), 10)).getName());
				}
				
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
		PhotoManager photoManager = (PhotoManager)Context.getBean(PhotoManager.class);
		UserPhotogroup group = photoManager.getGroupInfo(groupID);
		List<UserPhoto> photos = photoManager.getPhotos(groupID);
		List<UserPhoto> list = new ArrayList<UserPhoto>();
		for (Iterator iterator = photos.iterator(); iterator.hasNext();) {
			UserPhoto userPhoto = (UserPhoto) iterator.next();
			String photpurlString = userPhoto.getPhoto().replace("\\", "/");
			userPhoto.setPhoto(photpurlString);
			list.add(userPhoto);
			
		}
		model.put("photos", list);
		model.put("groupID", groupID);
		if(group!=null){
			model.put("groupName", group.getGroupName());
		}
		
		model.put("viewType", "photo");
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		String name = "";
		if(user != null && user.getType() == 0){//����
			name = user.getPerNickName();
		} else if(user != null && user.getType() == 1){//����
			name = user.getOrgShortname();
		}
		model.put("title", name + "����ӳ��");
		model.put("keywords", "����ӳ��");
		model.put("description", name + "����ӳ��");
	}
}