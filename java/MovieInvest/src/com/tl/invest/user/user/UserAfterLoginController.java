package com.tl.invest.user.user;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import weibo4j.Account;
import weibo4j.Users;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.tl.common.WebUtil;
import com.tl.invest.workspace.Entry;

/** 
 * @created 2014年11月13日 上午8:45:56 
 * @author  leijj
 * 类说明 ： qq登录后
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserAfterLoginController extends Entry {
	private UserManager manager = null;
	public UserManager getManager() {
		return manager;
	}

	public void setManager(UserManager manager) {
		this.manager = manager;
	}

	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("qqAfterLogin".equals(action)){//qq登录后处理
			qqAfterLogin(request, response, model);
		} else if("weiboAfterLogin".equals(action)){//新浪微博登录后处理
			weiboAfterLogin(request, response, model);
		}
	}
	
	protected void qqAfterLogin(HttpServletRequest request, HttpServletResponse response, Map model) throws IOException{
		String redirectUrl = request.getParameter("redirect_uri");
		if ((redirectUrl != null) && (!("".equals(redirectUrl)))) {
			response.sendRedirect(redirectUrl);
			return;
		}
		HttpSession session = request.getSession();
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
				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				session.setAttribute("access_token", accessToken);
				session.setAttribute("token_expirein", String.valueOf(tokenExpireIn));
				session.setAttribute("openID", openID);
				if(openID != null && openID.length() > 0){
					User user = manager.getUserByQQ(openID);
					model.put("openID", openID);
					if(user != null){//已做过关联用户信息，直接跳转到网站首页
						String viewName = WebUtil.getRoot(request);
						model.put("nickName", user.getPerNickName());
						model.put("@VIEWNAME@", viewName);
					} else {
						UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
						UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
						if (userInfoBean.getRet() == 0) {// 获取到了qq登录的用户信息
							String viewName = WebUtil.getRoot(request) + "/userout/userLoginRel"; //跳转到qq关联用户账户的界面
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
		if(user != null){//已做过关联用户信息，直接跳转到网站首页
			String viewName = WebUtil.getRoot(request) + "/user/userSetting";
			model.put("nickName", user.getPerNickName());
			model.put("@VIEWNAME@", viewName);
		} else {
			String viewName = WebUtil.getRoot(request) + "/user/userRegisterApi"; //跳转到qq关联用户账户的界面
			model.put("nickName", weibo);
			model.put("weiboName", weibo);
			model.put("@VIEWNAME@", viewName);
		}
	}

	protected void sinaAfterLogin(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String redirectUrl = request.getParameter("redirect_uri");
		if ((redirectUrl != null) && (!("".equals(redirectUrl)))) {
			response.sendRedirect(redirectUrl);
			return;
		}
		try {
			HttpSession session = request.getSession();
			
			weibo4j.Oauth oauth = new weibo4j.Oauth();
			String sinacode = request.getParameter("code");
			weibo4j.http.AccessToken accessTokenObj = oauth.getAccessTokenByCode(sinacode);
			String accessToken = accessTokenObj.getAccessToken();

			Account account = new Account();
			account.client.setToken(accessTokenObj.getAccessToken());
			session.setAttribute("oauth", accessTokenObj.getAccessToken());

			weibo4j.org.json.JSONObject uid = account.getUid();

			Users users = new Users();
			users.client.setToken(accessTokenObj.getAccessToken());
			weibo4j.model.User user = users.showUserById(uid.getString("uid"));
			String nickName = user.getScreenName();
			String head = user.getProfileImageUrl();
			if ((head == null) || ("".equals(head))) {
				head = null;
			}
			String seqId = user.getId();
			String loginname = nickName;

			session.setAttribute("accessToken", accessToken);
			session.setAttribute("head", head);
			session.setAttribute("seqId", seqId);
			session.setAttribute("weiboUser", loginname);
			response.sendRedirect("/mp/sso/partnerlogin.jsp?timestamp="
					+ Calendar.getInstance().getTimeInMillis());
			return;
		} catch (WeiboException e) {
			this.log.info("调用错误");
		} catch (JSONException e) {
			this.log.info("解析json字符串错误");
		} catch (Exception e) {
			this.log.info("登录错误");
		}
	}
}