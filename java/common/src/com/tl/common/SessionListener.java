package com.tl.common;

import java.io.File;
import java.util.Set;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("用户"+arg0.getSession().getId()+"上线...");
	}

	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//session失效的时候删除所有该用户图片
		System.out.println("用户"+arg0.getSession().getId()+"下线...");
		Object obj = arg0.getSession().getAttribute("pics");
		if (obj != null) {
			Object[] pics = ((Set) obj).toArray();
			for (int i = 0; i < pics.length; i++) {
				String pic = pics[i].toString();
				String savePath = arg0.getSession().getServletContext()
						.getRealPath("");
				savePath = savePath + "/images/" + pic;
				File f = new File(savePath);
				if (f.exists()) {
					f.delete();
				}
			}
		}
	}
}
