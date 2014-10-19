package com.tl.kernel.sys.dic;

import java.io.Serializable;

public class DictionaryType implements Serializable {
	private static final long serialVersionUID = 5582532594728691263L;
	
	private int id;
	private String name;
	private String code;
	private int isSys;
	private int maxLevel;
	private int order;
	private int valid;
	private String memo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIsSys() {
		return isSys;
	}
	public void setIsSys(int isSys) {
		this.isSys = isSys;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
