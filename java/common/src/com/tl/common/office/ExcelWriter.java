package com.tl.common.office;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.tl.common.office.ExcelCell;
import com.tl.common.office.ExcelCellStyle;
import com.tl.common.office.ExcelRow;
import com.tl.common.office.ExcelSheet;
import com.tl.common.office.ExcelWriter;
import com.tl.common.log.Log;
import com.tl.common.log.LogFactory;
import com.tl.common.office.ExcelCellStyle.AlignStyle;
import com.tl.common.office.ExcelCellStyle.BorderStyle;
import com.tl.common.office.ExcelCellStyle.Color;
import com.tl.common.office.ExcelCellStyle.FontTypeOffset;
import com.tl.common.office.ExcelCellStyle.VAlignStyle;

public class ExcelWriter {
	private Log log = LogFactory.getLog("tl");
	SXSSFWorkbook workbook = null;
	Sheet sheet = null;
	
	private static ExcelWriter writer;
	public synchronized static ExcelWriter getInstance(){
		if (writer == null) writer = new ExcelWriter();
    	return writer;
	}
	private ExcelWriter(){
		workbook = new SXSSFWorkbook();
	}
	
	public void write(String xlsPath,ExcelSheet excelSheet){
		createExcel(excelSheet);
		FileOutputStream fos = null;
		 try {
			fos=new FileOutputStream(new File(xlsPath));
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally{
			if(fos!=null)
				try {
					fos.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
		}
	}
	
	private void createExcel(ExcelSheet excelSheet){
		sheet = workbook.createSheet(excelSheet.getName());
		ExcelRow[] excelRows = excelSheet.getRows();
		if(excelRows!=null && excelRows.length>0){
			for (int i = 0; i < excelRows.length; i++) {
				createRow(i,excelRows[i]);
			}
		}
		if(excelSheet.getFreezeCellCount()>0 || excelSheet.getFreezeRowCount()>0){
			sheet.createFreezePane(excelSheet.getFreezeCellCount(), excelSheet.getFreezeRowCount(),
					excelSheet.getFreezeShowCellCount(), excelSheet.getFreezeShowRowCount());
		}
	}
	
	private void createRow(int index,ExcelRow excelRow){
		Row row = sheet.createRow(index);
		ExcelCell[] excelCells = excelRow.getCells();
		if(excelCells!=null && excelCells.length>0){
			String detail = "";
			ExcelCell excelCell;
			Cell cell;
			CellStyle style;
			ExcelCellStyle cellStyle;
			for (int i = 0; i < excelCells.length; i++) {
				excelCell = excelCells[i];				
				style = workbook.createCellStyle();
				cellStyle = excelCell.getStyle();
				style.setDataFormat(excelCell.getStyle().getFormat().getFormat());
				if(cellStyle.getAlign() != AlignStyle.GENERAL)style.setAlignment(cellStyle.getAlign().HSSFValue());
				if(cellStyle.getBorderBottom() != BorderStyle.NONE) style.setBorderBottom(cellStyle.getBorderBottom().HSSFValue());
				if(cellStyle.getBorderLeft() != BorderStyle.NONE) style.setBorderLeft(excelCell.getStyle().getBorderLeft().HSSFValue());
				if(cellStyle.getBorderRight() != BorderStyle.NONE) style.setBorderRight(excelCell.getStyle().getBorderRight().HSSFValue());
				if(cellStyle.getBorderTop() != BorderStyle.NONE) style.setBorderTop(excelCell.getStyle().getBorderRight().HSSFValue());
				if(cellStyle.getBorderBottomColor() != Color.AUTOMATIC)style.setBottomBorderColor(cellStyle.getBorderBottomColor().index());
				if(cellStyle.getBorderLeftColor() != Color.AUTOMATIC)style.setLeftBorderColor(excelCell.getStyle().getBorderLeftColor().index());
				if(cellStyle.getBorderRightColor() != Color.AUTOMATIC)style.setRightBorderColor(excelCell.getStyle().getBorderRightColor().index());
				if(cellStyle.getBorderTopColor() != Color.AUTOMATIC)style.setTopBorderColor(excelCell.getStyle().getBorderTopColor().index());
				if(cellStyle.getValign() != VAlignStyle.CENTER)style.setVerticalAlignment(excelCell.getStyle().getValign().HSSFValue());
				if(excelCell.getStyle().getFillPattern()!=null){
					style.setFillPattern(excelCell.getStyle().getFillPattern().HSSFValue());
					style.setFillBackgroundColor(excelCell.getStyle().getBackgroundColor().index());
					style.setFillForegroundColor(excelCell.getStyle().getForegroundColor().index());
				}
				style.setWrapText(excelCell.getStyle().isWrapText());
				Font font = workbook.createFont();
				font.setBoldweight(excelCell.getStyle().getFontBoldweight().index());
				font.setCharSet(excelCell.getStyle().getFontCharset().index());
				font.setColor(excelCell.getStyle().getFontColor().index());
				font.setFontHeightInPoints(excelCell.getStyle().getFontHeight());
				font.setFontName(excelCell.getStyle().getFontName());
				font.setItalic(excelCell.getStyle().isFontItalic());
				font.setStrikeout(excelCell.getStyle().isFontStrikeout());
				if(cellStyle.getFontTypeOffset() != FontTypeOffset.NONE) font.setTypeOffset(excelCell.getStyle().getFontTypeOffset().index());
				//font.setUnderline(excelCell.getStyle().getFontUnderline().index());
				style.setFont(font);
				cell = row.createCell(i, excelCell.getType().type());
				cell.setCellStyle(style);
				switch (excelCell.getType()) {
					case STRING:
						cell.setCellValue(excelCell.getString());
						if(i == 0) detail = excelCell.getString();
						break;
					case NUMERIC:
						cell.setCellValue(excelCell.getDouble());
						if(i == 0) detail = String.valueOf(excelCell.getDouble());
						break;
					default:
						cell.setCellValue(excelCell.getString());	
						if(i == 0) detail = excelCell.getString();
						break;
				}
			}
			
			log.info("已经写入第 "+index+" 行："+detail);
		}
	}
}
