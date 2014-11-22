package com.tl.invest.user.video;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserHelper;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年11月14日 下午2:02:54 
 * @author  leijj
 * 类说明 ： 视频控制类
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserVideoController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("list".equals(action)){//获取列表
			list(request, response, model);
		} else if("save".equals(action)){//保存视频
			String json = save(request, response);
			output(json, response);
		} else if("detail".equals(action)){//视频细览
			detail(request, response, model);
		}
	}
	/** 
	* @author  leijj 
	* 功能： 获取招聘信息列表
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void list(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int curPage = ParamInitUtils.getInt(request.getParameter("curPage"));
		int length = ParamInitUtils.getInt(request.getParameter("length"));
		Message message = userVideoManager.getMessageList(curPage, length);
		model.put("length", length);
		model.put("msg", message);
		model.put("@VIEWNAME@", WebUtil.getRoot(request) + "user/video/userVideoList");//跳转至Coupon.jsp页面
	}
	/** 
	* @author  leijj 
	* 功能： 保存招聘信息
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		String savePath = UserHelper.saveAffix(request, "upload/user/video/");
		UserVideo userVideo = new UserVideo();
		userVideo.setUserId(user.getId());
		userVideo.setUserName(user.getName());
		userVideo.setPhoto(savePath);
		userVideo.setVideo(ParamInitUtils.getString(request.getParameter("video")));
		userVideo.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		userVideo.setCreateTime(DateUtils.getTimestamp());
		userVideoManager.save(userVideo);
		return "ok";
	}
	/** 
	* @author  leijj 
	* 功能： 获取招聘信息列表
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void detail(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserVideo userVideo = userVideoManager.getUserVideoByID(id);
		model.put("userVideo", userVideo);
		model.put("@VIEWNAME@", WebUtil.getRoot(request) + "user/video/userVideoDetail");//跳转至Coupon.jsp页面
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private UserVideoManager userVideoManager = (UserVideoManager)Context.getBean(UserVideoManager.class);
}