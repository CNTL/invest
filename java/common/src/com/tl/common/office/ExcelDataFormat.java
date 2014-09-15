package com.tl.common.office;

import com.tl.common.office.ExcelDataFormat;

public enum ExcelDataFormat {
	/**
	 * General
	 */
	General(0) {
		@Override
		public String getFormatString() {
			return "@";
		}

		@Override
		public String getExcelFormatString() {
			return "General";
		}
	},
	/**
	 * @
	 */
	General1(49) {
		@Override
		public String getFormatString() {
			return "@";
		}

		@Override
		public String getExcelFormatString() {
			return "@";
		}
	},
	/**
	 * 0
	 */
	Integer(1){

		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "0";
		}
		
	},
	/**
	 * 0.00
	 */
	Number2Scale(2){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "0.00";
		}
		
	},
	/**
	 * 0%
	 */
	Percentage(9){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "0%";
		}
		
	},
	/**
	 * 0.00%
	 */
	Percentage2Scale(10){

		@Override
		public String getFormatString() {
			return "0.0000";
		}

		@Override
		public String getExcelFormatString() {
			return "0.00%";
		}
		
	},
	/**
	 * 0.00E+00
	 */
	Scientific(11){
		@Override
		public String getFormatString() {
			return "@";
		}

		@Override
		public String getExcelFormatString() {
			return "0.00E+00";
		}
	},
	/**
	 * ##0.0E+0
	 */
	Scientific1(48){
		@Override
		public String getFormatString() {
			return "@";
		}

		@Override
		public String getExcelFormatString() {
			return "##0.0E+0";
		}
	},
	/**
	 * # ?/?
	 */
	Fraction(12){
		@Override
		public String getFormatString() {
			return "@";
		}

		@Override
		public String getExcelFormatString() {
			return "# ?/?";
		}
	},
	/**
	 * # ?/?
	 */
	Fraction1(13){
		@Override
		public String getFormatString() {
			return "@";
		}

		@Override
		public String getExcelFormatString() {
			return "# ??/??";
		}
	},
	/**
	 * #,##0
	 */
	Money(3){

		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "#,##0";
		}
		
	},
	/**
	 * #,##0;-#,##0
	 */
	Money1(37){

		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "#,##0;-#,##0";
		}
		
	},
	/**
	 *	#,##0;[红色]-#,##0
	 */
	MoneyRed(38){

		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "#,##0;[红色]-#,##0";
		}
		
	},
	/**
	 * #,##0.00
	 */
	Money2Scale(4){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "#,##0.00";
		}
		
	},
	/**
	 * #,##0.00;-#,##0.00
	 */
	Money2Scale1(39){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "#,##0.00;-#,##0.00";
		}
		
	},
	/**
	 * #,##0.00;[红色]-#,##0.00
	 */
	MoneyRed2Scale(40){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "#,##0.00;[红色]-#,##0.00";
		}
		
	},
	/**
	 * RMB#,##0;RMB-#,##0
	 */
	MoneyRMB(5){
		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "RMB#,##0;RMB-#,##0";
		}
	},
	/**
	 * RMB#,##0.00;RMB-#,##0.00
	 */
	MoneyRMB2Scale(7){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "RMB#,##0.00;RMB-#,##0.00";
		}
		
	},
	/**
	 * RMB#,##0;[红色]RMB-#,##0
	 */
	MoneyRMBRed(6){
		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "RMB#,##0;[红色]RMB-#,##0";
		}
	},
	/**
	 * RMB#,##0.00;[红色]RMB-#,##0.00
	 */
	MoneyRMBRed2Scale(8){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "RMB#,##0.00;[红色]RMB-#,##0.00";
		}
		
	},
	/**
	 * $#,##0_);($#,##0)
	 */
	MoneyDollar(23){
		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "$#,##0_);($#,##0)";
		}
	},
	/**
	 * $#,##0.00_);($#,##0.00)
	 */
	MoneyDollar2Scale(25){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "$#,##0.00_);($#,##0.00)";
		}
		
	},
	/**
	 * $#,##0_);[红色]($#,##0)
	 */
	MoneyDollarRed(24){
		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "$#,##0_);[红色]($#,##0)";
		}
	},
	/**
	 * $#,##0.00_);[红色]($#,##0.00)
	 */
	MoneyDolarRed2Scale(26){

		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "$#,##0.00_);[红色]($#,##0.00)";
		}
		
	},
	/**
	 * _ * #,##0_ ;_ * -#,##0_ ;_ * "-"_ ;_ @_
	 */
	Money2(41){
		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "_ * #,##0_ ;_ * -#,##0_ ;_ * \"-\"_ ;_ @_";
		}
	},
	/**
	 * _ * #,##0.00_ ;_ * -#,##0.00_ ;_ * "-"??_ ;_ @_
	 */
	Money2Scale2(43){
		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * \"-\"??_ ;_ @_";
		}
	},
	/**
	 * _ RMB* #,##0_ ;_ RMB* -#,##0_ ;_ RMB* "-"_ ;_ @_
	 */
	MoneyRMB2(42){
		@Override
		public String getFormatString() {
			return "0";
		}

		@Override
		public String getExcelFormatString() {
			return "_ RMB* #,##0_ ;_ RMB* -#,##0_ ;_ RMB* \"-\"_ ;_ @_";
		}
	},
	/**
	 * _ RMB* #,##0.00_ ;_ RMB* -#,##0.00_ ;_ RMB* "-"??_ ;_ @_
	 */
	MoneyRMB2Scale2(44){
		@Override
		public String getFormatString() {
			return "0.00";
		}

		@Override
		public String getExcelFormatString() {
			return "_ RMB* #,##0.00_ ;_ RMB* -#,##0.00_ ;_ RMB* \"-\"??_ ;_ @_";
		}
	},
	/**
	 * h:mm:ss
	 */
	Time(21){

		@Override
		public String getFormatString() {
			return "HH:mm:ss";
		}

		@Override
		public String getExcelFormatString() {
			return "h:mm:ss";
		}
		
	},
	/**
	 * h:mm AM/PM
	 */
	Time1(18){

		@Override
		public String getFormatString() {
			return "HH:mm";
		}

		@Override
		public String getExcelFormatString() {
			return "h:mm AM/PM";
		}
		
	},
	/**
	 * h:mm:ss AM/PM
	 */
	Time2(19){

		@Override
		public String getFormatString() {
			return "HH:mm:ss";
		}

		@Override
		public String getExcelFormatString() {
			return "h:mm:ss AM/PM";
		}
		
	},
	/**
	 * h:mm
	 */
	Time3(20){

		@Override
		public String getFormatString() {
			return "HH:mm";
		}

		@Override
		public String getExcelFormatString() {
			return "h:mm";
		}
		
	},
	/**
	 * mm:ss
	 */
	Time4(45){

		@Override
		public String getFormatString() {
			return "mm:ss";
		}

		@Override
		public String getExcelFormatString() {
			return "mm:ss";
		}
		
	},
	/**
	 * [h]:mm:ss
	 */
	Time5(46){

		@Override
		public String getFormatString() {
			return "HH:mm:ss";
		}

		@Override
		public String getExcelFormatString() {
			return "[h]:mm:ss";
		}
		
	},
	/**
	 * mm:ss.0
	 */
	Time6(47){

		@Override
		public String getFormatString() {
			return "mm:ss.0";
		}

		@Override
		public String getExcelFormatString() {
			return "mm:ss.0";
		}
		
	},
	/**
	 * h"时\"mm\"分\"ss\"秒\"
	 */
	TimeCN(33){

		@Override
		public String getFormatString() {
			return "HH:mm:ss";
		}

		@Override
		public String getExcelFormatString() {
			return "h\"时\"mm\"分\"ss\"秒\"";
		}
		
	},
	/**
	 * h\"时\"mm\"分\"
	 */
	TimeCN1(32){

		@Override
		public String getFormatString() {
			return "HH:mm";
		}

		@Override
		public String getExcelFormatString() {
			return "h\"时\"mm\"分\"";
		}
		
	},
	/**
	 * 上午/下午h\"时\"mm\"分\"
	 */
	TimeCN2(55){

		@Override
		public String getFormatString() {
			return "HH:mm";
		}

		@Override
		public String getExcelFormatString() {
			return "上午/下午h\"时\"mm\"分\"";
		}
		
	},
	/**
	 * 上午/下午h\"时\"mm\"分\"ss\"秒\"
	 */
	TimeCN3(56){

		@Override
		public String getFormatString() {
			return "HH:mm:ss";
		}

		@Override
		public String getExcelFormatString() {
			return "上午/下午h\"时\"mm\"分\"ss\"秒\"";
		}
		
	},
	/**
	 * yyyy-m-d
	 */
	Date(14){
		@Override
		public String getFormatString() {
			return "yyyy-MM-dd";
		}

		@Override
		public String getExcelFormatString() {
			return "yyyy-m-d";
		}
	},
	/**
	 * d-mmm-yy
	 */
	Date1(15){
		@Override
		public String getFormatString() {
			return "yyyy-MM-dd";
		}

		@Override
		public String getExcelFormatString() {
			return "d-mmm-yy";
		}
	},
	/**
	 * d-mmm
	 */
	Date2(16){
		@Override
		public String getFormatString() {
			return "MM-dd";
		}

		@Override
		public String getExcelFormatString() {
			return "d-mmm";
		}
	},
	/**
	 * mmm-yy
	 */
	Date3(17){
		@Override
		public String getFormatString() {
			return "yyyy-MM";
		}

		@Override
		public String getExcelFormatString() {
			return "mmm-yy";
		}
	},
	/**
	 * m-d-yy
	 */
	Date4(30){
		@Override
		public String getFormatString() {
			return "yyyy-MM-dd";
		}

		@Override
		public String getExcelFormatString() {
			return "m-d-yy";
		}
	},
	/**
	 * yyyy\"年\"m\"月\"
	 */
	DateCN1(57){
		@Override
		public String getFormatString() {
			return "yyyy-MM";
		}

		@Override
		public String getExcelFormatString() {
			return "yyyy\"年\"m\"月\"";
		}
	},
	/**
	 * m\"月\"d\"日\"
	 */
	DateCN2(58){
		@Override
		public String getFormatString() {
			return "MM-dd";
		}

		@Override
		public String getExcelFormatString() {
			return "m\"月\"d\"日\"";
		}
	},
	/**
	 * yyyy\"年\"m\"月\"d\"日\"
	 */
	DateCN(31){
		@Override
		public String getFormatString() {
			return "yyyy-MM-dd";
		}

		@Override
		public String getExcelFormatString() {
			return "yyyy\"年\"m\"月\"d\"日\"";
		}
	},
	/**
	 * yyyy-m-d h:mm
	 */
	Timestamp(22){

		@Override
		public String getFormatString() {
			return "yyyy-MM-dd HH:mm";
		}

		@Override
		public String getExcelFormatString() {
			return "yyyy-m-d h:mm";
		}
		
	}
	
	;
	
	
	private short format;
	private ExcelDataFormat(int format){
		this.format = (short)format;
	}
	
	public short getFormat(){
		return this.format;
	}
	
	public abstract String getFormatString();
	public abstract String getExcelFormatString();
	
	public boolean isDateFormat(){
		boolean isDate = false;
		switch (this.format) {
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 30:
			case 31:
			case 32:
			case 33:
			case 45:
			case 46:
			case 47:
			case 55:
			case 56:
			case 57:
			case 58:
				isDate = true;
				break;
			default:
				break;
		}
		return isDate;
	}
	
	public boolean isGeneral(){
		boolean isGeneral = false;
		switch (this.format) {
			case 0:
			case 49:
				isGeneral = true;
				break;
			default:
				break;
		}
		return isGeneral;
	}
	
	public ExcelDataFormat getDataFormat(int format){
		for (ExcelDataFormat f : ExcelDataFormat.values()) {
			if(f.getFormat() == format){
				return f;
			}
		}
		return null;
	}
}
