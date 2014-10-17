package com.tl.invest.user.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.User;
import com.tl.invest.user.UserManager;
import com.tl.invest.user.UserMsg;
import com.tl.invest.user.UserMsgManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年10月16日 上午8:33:07 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings("rawtypes")
public class UserMsgController  extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getMyMsgs".equals(action)){//根据消息接收方查出消息列表
			getMyMsgs(request, response);
		} else if("getTalkList".equals(action)){//按消息接收人查出所有消息
			getTalkList(request, response);
		} else if("sendMsg".equals(action)){//发送消息
			sendMsg(request, response);
		} else if("delMsg".equals(action)){//根据id删除消息
			delMsg(request, response);
		} else if("delMsgs".equals(action)){//根据接收者删除消息
			delMsgs(request, response);
		}
	}
	/** 
	* @author  leijj 
	* 功能： 根据消息接收方查出消息列表
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void getMyMsgs(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		String json = userMsgManager.getMyMsgs(user);
		output(json, response);
	}
	/** 
	* @author  leijj 
	* 功能： 按消息接收人查出所有消息
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void getTalkList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int msg_toID = ParamInitUtils.getInt(request.getParameter("msg_toID"));
		String json = userMsgManager.getTalkList(user, msg_toID);
		output(json, response);
	}
	/** 
	* @author  leijj 
	* 功能：根据id删除消息 
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void delMsg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		userMsgManager.delete(id);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* 功能： 根据接收者删除消息
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void delMsgs(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysSessionUser user = SessionHelper.getUser(request);
		int msg_toID = ParamInitUtils.getInt(request.getParameter("msg_toID"));
		userMsgManager.deleteByTo(user.getUserID(), msg_toID);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* 功能： 发送消息
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void sendMsg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysSessionUser user = SessionHelper.getUser(request);
		UserMsg userMsg = new UserMsg();
		userMsg.setMsgFromID(user.getUserID());
		userMsg.setMsgFrom(SessionHelper.getUserCode(request));
		userMsg.setMsgToID(ParamInitUtils.getInt(request.getParameter("msgTo_ID")));
		userMsg.setMsgTo(ParamInitUtils.getString(request.getParameter("msgTo")));
		userMsg.setMsgContent(ParamInitUtils.getString(request.getParameter("msgContent")));
		userMsg.setMsgIsRead(0);
		userMsg.setCreateTime(DateUtils.getTimestamp());
		userMsgManager.save(userMsg);
		output("ok", response);
	}
	
	UserManager userManager = (UserManager) Context.getBean(UserManager.class);
	UserMsgManager userMsgManager = (UserMsgManager) Context.getBean(UserMsgManager.class);
}
