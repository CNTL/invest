package com.tl.invest.user.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.tl.invest.user.User;
import com.tl.invest.user.UserManager;
import com.tl.kernel.web.BaseController;

/** 
 * @created 2014��11��13�� ����8:45:56 
 * @author  leijj
 * ��˵�� �� qq��¼��
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserAfterLoginController extends BaseController {
	private UserManager manager = null;
	public UserManager getManager() {
		return manager;
	}

	public void setManager(UserManager manager) {
		this.manager = manager;
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("qqAfterLogin".equals(action)){//��ȡ�û��б�
			qqAfterLogin(request, response, model);
		}
	}
	
	protected void qqAfterLogin(HttpServletRequest request, HttpServletResponse response, Map model){
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			String accessToken = null;
			String openID = null;
			long tokenExpireIn = 0L;
			if (accessTokenObj.getAccessToken().equals("")) {
				// ���ǵ���վ��CSRF�����˻����û�ȡ������Ȩ
				// ��һЩ����ͳ�ƹ���
				System.out.println("û�л�ȡ����Ӧ����");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();
				request.getSession().setAttribute("access_token", accessToken);
				request.getSession().setAttribute("token_expirein", String.valueOf(tokenExpireIn));
				request.getSession().setAttribute("openID", openID);
				// ���û�ȡ����accessToken ȥ��ȡ��ǰ�õ�openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				if(openID != null && openID.length() > 0){
					User user = manager.getUserByQQ(openID);
					model.put("openID", openID);
					if(user != null){//�����������û���Ϣ��ֱ����ת����վ��ҳ
						String viewName = "/user/userSetting";
						model.put("nickName", user.getNickName());
						model.put("@VIEWNAME@", viewName);
					} else {
						UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
						UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
						if (userInfoBean.getRet() == 0) {// ��ȡ����qq��¼���û���Ϣ
							String viewName = "/user/userRegisterApi"; //��ת��qq�����û��˻��Ľ���
							model.put("nickName", userInfoBean.getNickname());
							model.put("@VIEWNAME@", viewName);
						} else {
							System.out.println("�ܱ�Ǹ������û����ȷ��ȡ��������Ϣ��ԭ���ǣ� " + userInfoBean.getMsg());
						}
					}
				}
			}
		} catch (QQConnectException e) {
			log.error(e.getMessage());
		}
	}
	
	protected void weiboAfterLogin(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		String weibo = request.getParameter("weibo");
		User user = manager.getUserByWeibo(weibo);
		model.put("nickName", weibo);
		model.put("weiboName", weibo);
		if(user != null){
			output("true", response);
		} else {
			output("false", response);
		}
	}
}