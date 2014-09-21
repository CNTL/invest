package com.tl.common.office;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.office.Excel2003Reader;
import com.tl.common.DateUtils;
import com.tl.common.log.Log;
import com.tl.common.log.LogFactory;

public class Excel2003Reader {
		
	private Log log = LogFactory.getLog("tl"); 
	
	HSSFWorkbook workbook = null;
	
	private static Excel2003Reader reader;
	public synchronized static Excel2003Reader getInstance(String xlsPath){
		if (reader == null) reader = new Excel2003Reader(xlsPath);
    	return reader;
	}
	
	private Excel2003Reader(String xlsPath){
		try {
			FileInputStream excelFIS = new FileInputStream(xlsPath);
			workbook = new HSSFWorkbook(excelFIS);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void getSheetDatas(){
		getSheetDatas(0);
	}
	
	public void getSheetDatas(String sheetName){
		int sheetIndex = workbook.getSheetIndex(sheetName);
		getSheetDatas(sheetIndex);
	}
	
	public void getSheetDatas(int sheetIndex){
		HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		log.info("FirstRowNum:"+sheet.getFirstRowNum());
		log.info("LastRowNum:"+sheet.getLastRowNum());
		int rowCount = sheet.getPhysicalNumberOfRows();
		log.info("rowCount:"+rowCount);
		for (int rowNumber = 0; rowNumber < rowCount; rowNumber++) {
			HSSFRow row = sheet.getRow(rowNumber);
			if (row == null)  continue;
			int cellCount = row.getPhysicalNumberOfCells();
			String detail = "\t¹²"+cellCount+"ÁÐ\t";
			for (int cellNumber = 0; cellNumber < cellCount; cellNumber++) {
				HSSFCell cell = row.getCell(cellNumber);
				HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
				if(cell==null) continue;
				String value = "";
				String formatString =  cell.getCellStyle().getDataFormatString();
				short format = cell.getCellStyle().getDataFormat();
				switch (cell.getCellType()) {
				
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						value = cell.getCellFormula();
						break;
						
					case HSSFCell.CELL_TYPE_NUMERIC:
						if(HSSFDateUtil.isCellDateFormatted(cell)){
							if(format ==0x13|| format==0x15){
								formatString = "HH:mm:ss";
							}else if(format==0x12 || format==0x14){
								formatString = "HH:mm";
							}else if(format==0x2d || format==0x2e || format==0x2f){
								formatString = "mm:ss";
							}else if(format==0xe || format==0xf){
								formatString = "yyyy-MM-dd";
							}else if(format==0x10){
								formatString = "MM-dd";
							}else if(format==0x11){
								formatString = "yyyy-MM-dd HH:mm";
							}else if(format==0x16){
								formatString = "yyyy-MM";
							}else {
								formatString = "yyyy-MM-dd HH:mm:ss";
							}
							Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
							value = DateUtils.toStr(date, formatString);
						}else{
							value = new DecimalFormat(formatString).format(cell.getNumericCellValue());
						}
						//value = String.valueOf(cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = cell.getBooleanCellValue() ? "true" : "false";
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "error";
						break;
					default:
						break;
				}
				
				detail +=value+"\t";
			}
			log.info((rowNumber+1)+":"+detail);
		}
	}
}
