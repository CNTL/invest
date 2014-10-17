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
 * @created 2014��10��16�� ����8:33:07 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings("rawtypes")
public class UserMsgController  extends BaseController {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getMyMsgs".equals(action)){//������Ϣ���շ������Ϣ�б�
			getMyMsgs(request, response);
		} else if("getTalkList".equals(action)){//����Ϣ�����˲��������Ϣ
			getTalkList(request, response);
		} else if("sendMsg".equals(action)){//������Ϣ
			sendMsg(request, response);
		} else if("delMsg".equals(action)){//����idɾ����Ϣ
			delMsg(request, response);
		} else if("delMsgs".equals(action)){//���ݽ�����ɾ����Ϣ
			delMsgs(request, response);
		}
	}
	/** 
	* @author  leijj 
	* ���ܣ� ������Ϣ���շ������Ϣ�б�
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
	* ���ܣ� ����Ϣ�����˲��������Ϣ
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
	* ���ܣ�����idɾ����Ϣ 
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
	* ���ܣ� ���ݽ�����ɾ����Ϣ
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
	* ���ܣ� ������Ϣ
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
