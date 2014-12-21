package com.tl.invest.user.video;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年11月14日 下午2:02:54 
 * @author  leijj
 * 类说明 ： 视频控制类
 */
@SuppressWarnings({"rawtypes"})
public class UserVideoController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getVideoGroups".equals(action)){//获取用户列表
			getVideoGroups(request, response);
		} else if("getGroupInfo".equals(action)){//获取视频组图信息
			getGroupInfo(request, response, model);
		} else if("saveVideoGroup".equals(action)){//保存图册
			saveVideoGroup(request, response);
		} else if("delVideoGroup".equals(action)){//删除图册
			userVideoManager.delVideoGroup(ParamInitUtils.getInt(request.getParameter("id")));
			output("ok", response);
		} else if("getVideos".equals(action)){//获取用户列表
			getVideos(request, response);
		} else if("getVideoInfo".equals(action)){//获取视频信息
			getVideoInfo(request, response, model);
		} else if("saveVideo".equals(action)){//保存视频
			saveVideo(request, response);
		} else if("delVideo".equals(action)){//删除图册
			userVideoManager.delVideo(ParamInitUtils.getInt(request.getParameter("id")));
			output("ok", response);
		}
	}
	private void getVideoGroups(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		List<UserVideogroup> videogroups = userVideoManager.getVideoGroups(user.getId());
		JSONArray jsonArray = JSONArray.fromObject(videogroups);  
		output(jsonArray.toString(), response);
	}
	/** 
	* @author  leijj 
	* 功能： 
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void getGroupInfo(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserVideogroup videogroup = userVideoManager.getGroupInfo(id);
		JSONObject json = JSONObject.fromObject(videogroup);
		output(json.toString(), response);
	}
	/** 
	* @author  leijj 
	* 功能： 保存图册信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void saveVideoGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		UserVideogroup videogroup = new UserVideogroup();
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		if(id > 0) videogroup = userVideoManager.getGroupInfo(id);
		
		videogroup.setUserId(user.getId());
		videogroup.setUserName(user.getName());
		videogroup.setGroupName(ParamInitUtils.getString(request.getParameter("groupName")));
		videogroup.setGroupIntro(ParamInitUtils.getString(request.getParameter("groupIntro")));
		videogroup.setGroupPhoto(ParamInitUtils.getString(request.getParameter("groupPhoto")));
		videogroup.setCreateTime(DateUtils.getTimestamp());
		id = userVideoManager.saveVideoGroup(videogroup);
		output(String.valueOf(id), response);
	}
	/** 
	* @author  leijj 
	* 功能： 根据相册id获取相册照片
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void getVideos(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int groupID = getInt(request, "groupID");
		List<UserVideo> videos = userVideoManager.getVideos(groupID);
		JSONArray jsonArray = JSONArray.fromObject(videos);  
		output(jsonArray.toString(), response);
	}
	/** 
	* @author  leijj 
	* 功能： 
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void getVideoInfo(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserVideogroup videogroup = userVideoManager.getGroupInfo(id);
		JSONObject json = JSONObject.fromObject(videogroup);
		output(json.toString(), response);
	}
	/** 
	* @author  leijj 
	* 功能： 保存图册信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void saveVideo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		UserVideo userVideo = new UserVideo();
		userVideo.setUserId(user.getId());
		userVideo.setUserName(user.getName());
		userVideo.setGroupId(getInt(request, "groupID"));
		userVideo.setName(ParamInitUtils.getString(request.getParameter("videoName")));
		userVideo.setPhoto(ParamInitUtils.getString(request.getParameter("photo")));
		userVideo.setVideo(ParamInitUtils.getString(request.getParameter("videoUrl")));
		userVideo.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		userVideo.setCreateTime(DateUtils.getTimestamp());
		userVideoManager.saveVideo(userVideo);
		output(String.valueOf(userVideo.getGroupId()), response);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private UserVideoManager userVideoManager = (UserVideoManager)Context.getBean(UserVideoManager.class);
}