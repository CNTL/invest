package com.tl.invest.user.msg;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.tl.common.DateJsonValueProcessor;
import com.tl.common.DateUtils;
import com.tl.common.JsonDateValueProcessor;
import com.tl.common.ParamInitUtils;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
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
		else if("readMsg".equals(action)){//阅读消息
			readMsg(request, response);
		}
		else if("readMsgAll".equals(action)){//阅读所有消息
			readMsgAll(request, response);
		}
		else if("getMyMsgsCount".equals(action)){//得到消息会话数
			getMyMsgsCount(request, response);
		}
		
		
	}
	
	
	/** 阅读消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void readMsg(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		try {
			userMsgManager.readMsg(user.getId(), getInt(request, "userID",0));
			output("ok", response);
		} catch (Exception e) {
			output("error", response);
		}
	} 
	
	/** 阅读所有消息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void readMsgAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		try {
			userMsgManager.readMsgAll(user.getId());
			output("ok", response);
		} catch (Exception e) {
			output("error", response);
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
		Integer pageIndex = getInt(request, "pageIndex", 1);
		List<Map<String, String>> result = userMsgManager.getMyMsgs(user,pageIndex);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		String json = JSONArray.fromObject(result,jsonConfig).toString();
		
		output(json, response);
	}
	
	private void getMyMsgsCount(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int count = userMsgManager.getMyMsgsCount(user.getId());
		output(String.valueOf(count), response);
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
		int msg_toID = getInt(request, "msguserid",0);
		List<Map<String, String>> result = userMsgManager.getTalkList(user, msg_toID);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		String json = JSONArray.fromObject(result,jsonConfig).toString();
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
		userMsg.setMsgFromId(user.getUserID());
		userMsg.setMsgFrom(user.getUserName());
		userMsg.setMsgToId(ParamInitUtils.getInt(request.getParameter("msgTo_ID")));
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
