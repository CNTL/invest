package com.tl.invest.favorite;

import java.util.Date;

public class Favorite implements java.io.Serializable{
	private static final long serialVersionUID = 6293957851188845530L;
	
	private long id;
	private int userId;
	private int libId;
	private long itemId;
	private Date created;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLibId() {
		return libId;
	}
	public void setLibId(int libId) {
		this.libId = libId;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
}
