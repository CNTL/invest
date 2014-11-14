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
 * @created 2014年11月13日 上午8:45:56 
 * @author  leijj
 * 类说明 ： qq登录后
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
		if("qqAfterLogin".equals(action)){//获取用户列表
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
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
				System.out.println("没有获取到响应参数");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();
				request.getSession().setAttribute("access_token", accessToken);
				request.getSession().setAttribute("token_expirein", String.valueOf(tokenExpireIn));
				request.getSession().setAttribute("openID", openID);
				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				if(openID != null && openID.length() > 0){
					User user = manager.getUserByQQ(openID);
					model.put("openID", openID);
					if(user != null){//已做过关联用户信息，直接跳转到网站首页
						String viewName = "/user/userSetting";
						model.put("nickName", user.getNickName());
						model.put("@VIEWNAME@", viewName);
					} else {
						UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
						UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
						if (userInfoBean.getRet() == 0) {// 获取到了qq登录的用户信息
							String viewName = "/user/userRegisterApi"; //跳转到qq关联用户账户的界面
							model.put("nickName", userInfoBean.getNickname());
							model.put("@VIEWNAME@", viewName);
						} else {
							System.out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
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