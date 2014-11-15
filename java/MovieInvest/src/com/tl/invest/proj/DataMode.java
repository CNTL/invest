package com.tl.invest.proj;

import java.util.List;

public class DataMode {
	private List<ProjMode> newList;
	private List<ProjMode> editList;
	private long[] deleteList;
	public List<ProjMode> getNewList() {
		return newList;
	}
	public void setNewList(List<ProjMode> newList) {
		this.newList = newList;
	}
	public List<ProjMode> getEditList() {
		return editList;
	}
	public void setEditList(List<ProjMode> editList) {
		this.editList = editList;
	}
	public long[] getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(long[] deleteList) {
		this.deleteList = deleteList;
	}
}
