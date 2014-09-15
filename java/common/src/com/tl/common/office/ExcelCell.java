package com.tl.common.office;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;

import com.tl.common.office.ExcelCellStyle;
import com.tl.common.office.ExcelCellType;
import com.tl.common.office.ExcelDataFormat;
import com.tl.common.DateUtils;

public class ExcelCell {	
	private ExcelCellType type;
	private ExcelCellStyle style;
	private Object value;
	private int countCells = 1;
	private int countRows = 1;
	
	@SuppressWarnings("unused")
	private ExcelCell(){
		
	}
	
	public ExcelCell(ExcelCellType type){
		this.type = type;
		this.style = new ExcelCellStyle();
		this.countCells = 1;
		this.countRows = 1;
	}
	
	public ExcelCell(ExcelCellType type,ExcelCellStyle style){
		this.type = type;
		this.style = style;
		this.countCells = 1;
		this.countRows = 1;
	}
	
	public ExcelCell(int type,ExcelCellStyle style){
		this.type = ExcelCellType.STRING;
		this.style = style;
		for (ExcelCellType excelCellType : ExcelCellType.values()) {
			if(excelCellType.type() == type){
				this.type = excelCellType;
				break;
			}
		}
		for (ExcelDataFormat f : ExcelDataFormat.values()) {
			if(f.getFormat() == style.getFormat().getFormat()){
				this.style.setFormat(f);
				break;
			}
		}
	}
	
	public int getCountCells() {
		return countCells;
	}

	public void setCountCells(int countCells) {
		this.countCells = countCells;
	}

	public int getCountRows() {
		return countRows;
	}

	public void setCountRows(int countRows) {
		this.countRows = countRows;
	}
	
	public ExcelCellType getType() {
		return type;
	}
	void setType(ExcelCellType type) {
		this.type = type;
	}
	
	public ExcelCellStyle getStyle(){
		return style;
	}
	
	public void setStyle(ExcelCellStyle style){
		this.style = style;
	}
	
	public Object getValue(){
		return value;
	}
	
	public String getString(){
		String s = "";
		if(value == null) return "";
		switch (this.type) {
			case BLANK:
			case ERROR:
			case BOOLEAN:
			case FORMULA:
			case STRING:
				s = String.valueOf(value);
				break;
			case NUMERIC:
				double d = Double.parseDouble(String.valueOf(value));
				if(this.style.getFormat().isDateFormat()){
					if(d>=0){
						Date date = DateUtil.getJavaDate(d);
						s = DateUtils.toStr(date, this.style.getFormat().getFormatString());
					}
				}else{
					try{
						if(this.style.getFormat().isGeneral()){
							s = String.valueOf(value);
						}else{
							s = new DecimalFormat(this.style.getFormat().getFormatString()).format(d);
						}
					}catch(Exception e){
						s = String.valueOf(value);
					}
				}
				break;
	
			default:
				break;
		}
		
		return s;
	}
	
	public void setString(String value){
		this.value = value;
	}
	
	public double getDouble(){
		return Double.parseDouble(String.valueOf(value));
	}
	
	public void setDouble(double value){
		this.value = value;
	}
	
	public void setInt(int value){
		this.value = value;
	}
	
	public void setFloat(float value){
		this.value = value;
	}
	
	public void setBoolean(boolean value){
		this.value = value;
	}
	
	/**
	 * yyyy-MM-dd
	 * @param value
	 */
	public void setDate(Date value){
		this.style.setFormat(ExcelDataFormat.Date);
		this.value = DateUtil.getExcelDate(DateUtils.toDate(DateUtils.toStr(value, this.style.getFormat().getFormatString())));
	}
	
	/**
	 * 
	 * @param value 2014-09-12
	 */
	public void setDate(String value){
		this.style.setFormat(ExcelDataFormat.Date);
		this.value = DateUtil.getExcelDate(DateUtils.toDate(value));
	}
	
	public void setDate(String value,ExcelDataFormat format){
		this.style.setFormat(format);
		this.value = DateUtil.getExcelDate(DateUtils.toDate(value,format.getFormatString()));
	}
	
	/**
	 * HH:mm:ss
	 * @param value
	 */
	public void setTime(Date date){
		this.style.setFormat(ExcelDataFormat.Time);
		this.value = DateUtil.convertTime(DateUtils.toStr(date, this.style.getFormat().getFormatString()));
	}
	
	/**
	 * 
	 * @param value 格式：HH:mm:ss
	 */
	public void setTime(String value){
		this.style.setFormat(ExcelDataFormat.Time);
		this.value = DateUtil.convertTime(value);
	}
	
	/**
	 * 
	 * @param value
	 * @param format 格式：HH:mm:ss 或者 HH:mm
	 */
	public void setTime(String value,ExcelDataFormat format){
		this.style.setFormat(format);
		this.value = DateUtil.convertTime(value);
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 * @param value
	 */
	public void setTimestamp(Date value){
		this.style.setFormat(ExcelDataFormat.Timestamp);
		this.value = DateUtil.getExcelDate(DateUtils.toDate(DateUtils.toStr(value, this.style.getFormat().getFormatString())));		
	}
	/**
	 * 
	 * @param value 如：2014-07-17 22:36:52.256
	 */
	public void setTimestamp(String value){
		this.style.setFormat(ExcelDataFormat.Timestamp);
		this.value = DateUtil.getExcelDate(DateUtils.toDate(value));
	}
	
	public void setTimestamp(String value,ExcelDataFormat format){
		this.style.setFormat(format);
		this.value = DateUtil.getExcelDate(DateUtils.toDate(value,format.getFormatString()));
	}
}
