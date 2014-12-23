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
 * @created 2014��11��13�� ����8:45:56 
 * @author  leijj
 * ��˵�� �� qq��¼��
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
		if("qqAfterLogin".equals(action)){//qq��¼����
			qqAfterLogin(request, response, model);
		} else if("weiboAfterLogin".equals(action)){//����΢����¼����
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
				// ���ǵ���վ��CSRF�����˻����û�ȡ������Ȩ
				// ��һЩ����ͳ�ƹ���
				System.out.println("û�л�ȡ����Ӧ����");
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();
				// ���û�ȡ����accessToken ȥ��ȡ��ǰ�õ�openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				session.setAttribute("access_token", accessToken);
				session.setAttribute("token_expirein", String.valueOf(tokenExpireIn));
				session.setAttribute("openID", openID);
				if(openID != null && openID.length() > 0){
					User user = manager.getUserByQQ(openID);
					model.put("openID", openID);
					if(user != null){//�����������û���Ϣ��ֱ����ת����վ��ҳ
						String viewName = WebUtil.getRoot(request);
						model.put("nickName", user.getPerNickName());
						model.put("@VIEWNAME@", viewName);
					} else {
						UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
						UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
						if (userInfoBean.getRet() == 0) {// ��ȡ����qq��¼���û���Ϣ
							String viewName = WebUtil.getRoot(request) + "/userout/userLoginRel"; //��ת��qq�����û��˻��Ľ���
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
		if(user != null){//�����������û���Ϣ��ֱ����ת����վ��ҳ
			String viewName = WebUtil.getRoot(request) + "/user/userSetting";
			model.put("nickName", user.getPerNickName());
			model.put("@VIEWNAME@", viewName);
		} else {
			String viewName = WebUtil.getRoot(request) + "/user/userRegisterApi"; //��ת��qq�����û��˻��Ľ���
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
			this.log.info("���ô���");
		} catch (JSONException e) {
			this.log.info("����json�ַ�������");
		} catch (Exception e) {
			this.log.info("��¼����");
		}
	}
}