package com.tl.invest.user.video;

// Generated Nov 17, 2014 3:11:25 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * UserVideo generated by hbm2java
 */
public class UserVideo {

	private int id;
	private Integer userId;
	private String userName;
	private String name;
	private String photo;
	private String video;
	private String intro;
	private Date createTime;

	public UserVideo() {
	}

	public UserVideo(int id, Date createTime) {
		this.id = id;
		this.createTime = createTime;
	}

	public UserVideo(int id, Integer userId, String userName, String name,
			String photo, String video, String intro, Date createTime) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.name = name;
		this.photo = photo;
		this.video = video;
		this.intro = intro;
		this.createTime = createTime;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getVideo() {
		return this.video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
