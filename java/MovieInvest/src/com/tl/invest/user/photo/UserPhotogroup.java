package com.tl.invest.user.photo;

// Generated Nov 17, 2014 3:11:25 PM by Hibernate Tools 3.4.0.CR1

/**
 * UserPhotogroup generated by hbm2java
 */
public class UserPhotogroup {

	private int id;
	private Integer userId;
	private String userName;
	private String groupName;
	private String groupIntro;

	public UserPhotogroup() {
	}

	public UserPhotogroup(int id) {
		this.id = id;
	}

	public UserPhotogroup(int id, Integer userId, String userName,
			String groupName, String groupIntro) {
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.groupName = groupName;
		this.groupIntro = groupIntro;
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

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupIntro() {
		return this.groupIntro;
	}

	public void setGroupIntro(String groupIntro) {
		this.groupIntro = groupIntro;
	}

}