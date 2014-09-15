package com.tl.common.office;

import com.tl.common.office.ExcelRow;

public class ExcelSheet {
	private String name;
	private int freezeCellCount = 0;
	private int freezeRowCount = 0;
	private int freezeShowCellCount = 0;
	private int freezeShowRowCount = 0;
	
	private ExcelRow[] rows;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFreezeCellCount() {
		return freezeCellCount;
	}
	public void setFreezeCellCount(int freezeCellCount) {
		this.freezeCellCount = freezeCellCount;
	}
	public int getFreezeRowCount() {
		return freezeRowCount;
	}
	public void setFreezeRowCount(int freezeRowCount) {
		this.freezeRowCount = freezeRowCount;
	}
	public int getFreezeShowCellCount() {
		return freezeShowCellCount;
	}
	public void setFreezeShowCellCount(int freezeShowCellCount) {
		this.freezeShowCellCount = freezeShowCellCount;
	}
	public int getFreezeShowRowCount() {
		return freezeShowRowCount;
	}
	public void setFreezeShowRowCount(int freezeShowRowCount) {
		this.freezeShowRowCount = freezeShowRowCount;
	}
	public ExcelRow[] getRows() {
		return rows;
	}
	public void setRows(ExcelRow[] rows) {
		this.rows = rows;
	}
	
	
}
