package com.tl.common.office;

import com.tl.common.office.ExcelCell;

public class ExcelRow {
	private int index;
	private int count;
	private ExcelCell[] cells;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ExcelCell[] getCells() {
		return cells;
	}
	public void setCells(ExcelCell[] cells) {
		this.cells = cells;
	}
}
