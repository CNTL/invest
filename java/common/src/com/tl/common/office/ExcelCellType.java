package com.tl.common.office;

import org.apache.poi.ss.usermodel.Cell;

public enum ExcelCellType {
	NUMERIC(Cell.CELL_TYPE_NUMERIC),
	STRING(Cell.CELL_TYPE_STRING),
	FORMULA(Cell.CELL_TYPE_FORMULA),
	BLANK(Cell.CELL_TYPE_BLANK),
	BOOLEAN(Cell.CELL_TYPE_BOOLEAN),
	ERROR(Cell.CELL_TYPE_ERROR);
	
	private int type;
	private ExcelCellType(int type){
		this.type = type;
	}
	
	public int type(){
		return type;
	}
}
