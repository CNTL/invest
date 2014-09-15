package com.tl.common.office;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tl.common.office.ExcelCell;
import com.tl.common.office.ExcelCellStyle;
import com.tl.common.office.ExcelCellType;
import com.tl.common.office.ExcelDataFormat;
import com.tl.common.office.ExcelReader;
import com.tl.common.office.ExcelRow;
import com.tl.common.office.ExcelSheet;
import com.tl.common.log.Log;
import com.tl.common.log.LogFactory;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelReader {
	private Log log = LogFactory.getLog("rengy"); 
	Workbook workbook = null;
	Sheet sheet = null;
	
	private static ExcelReader helper;
	public synchronized static ExcelReader getInstance(){
		if (helper == null) helper = new ExcelReader();
    	return helper;
	}
	private ExcelReader(){}
	
	
	public ExcelSheet read(String xlsPath){
		return read(xlsPath,0);
	}
	
	public ExcelSheet read(String xlsPath,String sheetName){
		initWorkbook(xlsPath);
		if(workbook!=null)
			sheet = workbook.getSheet(sheetName);
		return getSheetDatas();
	}
	
	public ExcelSheet read(String xlsPath,int sheetIndex){
		initWorkbook(xlsPath);
		if(workbook!=null)
			sheet = workbook.getSheetAt(sheetIndex);
		return getSheetDatas();
	}
	private void initWorkbook(String xlsPath){
		workbook = null;
		FileInputStream excelFIS = null;
		try {
			excelFIS = new FileInputStream(xlsPath);
			workbook = WorkbookFactory.create(excelFIS);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (InvalidFormatException e) {
			log.error(e.getMessage(), e);
		} finally {
			if(excelFIS!=null)
				try {
					excelFIS.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
		}
	}
		
	private ExcelSheet getSheetDatas(){
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setName(sheet.getSheetName());
		List<ExcelRow> rows = new ArrayList<ExcelRow>();
		int rowCount = sheet.getLastRowNum()+1;
		ExcelCellStyle style = new ExcelCellStyle();
		for (int rowNumber = 0; rowNumber < rowCount; rowNumber++) {
			Row row = sheet.getRow(rowNumber);
			if (row == null)  continue;
			ExcelRow excelRow = new ExcelRow();
			excelRow.setIndex(row.getRowNum());
			int cellCount = row.getLastCellNum()+1;
			excelRow.setCount(cellCount);
			List<ExcelCell> excelCells = new ArrayList<ExcelCell>();
			for (int cellNumber = 0; cellNumber < cellCount; cellNumber++) {
				Cell cell = row.getCell(cellNumber);
				if(cell==null){
					style.setFormat(ExcelDataFormat.General);
					ExcelCell excelCell = new ExcelCell(ExcelCellType.STRING, style);
					excelCell.setString("");
					excelCells.add(excelCell);
					continue;
				}
				style.setFormat(cell.getCellStyle().getDataFormat());
				ExcelCell excelCell = new ExcelCell(cell.getCellType(), style);
				CellRangeAddress ca = getCellRange(cell);
				if(ca!=null){
					excelCell.setCountCells(ca.getLastColumn() - ca.getFirstColumn() +1);
					excelCell.setCountRows(ca.getLastRow() - ca.getFirstRow() +1);
				}
				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						excelCell.setString(cell.getRichStringCellValue().getString());
						break;
					case Cell.CELL_TYPE_FORMULA:
						cell.setCellType(Cell.CELL_TYPE_STRING);  
						String value = cell.getRichStringCellValue().getString();
						if(value !=null){  
							value = value.replaceAll("#N/A","").trim();  
						}
						excelCell.setString(value);
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if(excelCell.getStyle().getFormat().isGeneral() || "@".equals(excelCell.getStyle().getFormat().getFormatString())){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							excelCell.setString(cell.getRichStringCellValue().getString());
						}else{
							excelCell.setDouble(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						excelCell.setBoolean(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						excelCell.setString("");
						break;
					case Cell.CELL_TYPE_ERROR:
						excelCell.setString("error");
						break;
					default:
						excelCell.setString("");
						break;
				}
				excelCells.add(excelCell);
			}
			excelRow.setCells( (ExcelCell[])excelCells.toArray(new ExcelCell[excelCells.size()]));
			
			rows.add(excelRow);
		}
		excelSheet.setRows((ExcelRow[])rows.toArray(new ExcelRow[rows.size()]));
		return excelSheet;
	}
	
	/**
	 * 合并单元格处理--加入list
	 * @param sheet
	 * @return
	 */
	public List<CellRangeAddress> getCellRangeAddresses(Sheet sheet){
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		// 获得一个 sheet 中合并单元格的数量
		int sheetmergerCount = sheet.getNumMergedRegions();
		// 遍历合并单元格
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格加入list中
			CellRangeAddress ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		return list;
	}
	
	public CellRangeAddress getCellRange(Cell cell){
		Sheet sheet = cell.getSheet();
		List<CellRangeAddress> list = getCellRangeAddresses(sheet);
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		for (CellRangeAddress ca : list) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getColumnIndex() <= lastC&& cell.getColumnIndex()>= firstC) {
				if (cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR) {
					return ca;
				}
			}
		}
		return null;
	}
}
