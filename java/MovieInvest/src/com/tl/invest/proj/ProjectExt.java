package com.tl.invest.proj;

import java.math.BigDecimal;

public class ProjectExt extends Project {
	private static final long serialVersionUID = 1952823748636779652L;
	
	private BigDecimal finishPer;
	private String surplus;
	private String typeName;
	private String typeNickName;
	private String userName;
	public BigDecimal getFinishPer() {
		return finishPer;
	}
	public void setFinishPer(BigDecimal finishPer) {
		this.finishPer = finishPer;
	}
	public String getSurplus() {
		return surplus;
	}
	public void setSurplus(String surplus) {
		this.surplus = surplus;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTypeNickName() {
		return typeNickName;
	}
	public void setTypeNickName(String typeNickName) {
		this.typeNickName = typeNickName;
	}
}
