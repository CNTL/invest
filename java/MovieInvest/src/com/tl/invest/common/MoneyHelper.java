package com.tl.invest.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.tl.common.StringUtils;

public class MoneyHelper {
public static BigDecimal ZERO = toMoney("0.00");
	
	public static BigDecimal toMoney(String money) {
		money = StringUtils.isEmpty(money) ? "0" : money;
		BigDecimal bigDecimal = toMoney(BigDecimal.valueOf(Float.parseFloat(money)));
		return bigDecimal;
	}
	
	public static BigDecimal toMoney(Double money) {
		BigDecimal bigDecimal = toMoney(BigDecimal.valueOf(money));
		return bigDecimal;
	}
	
	public static BigDecimal toMoney(float money){
		BigDecimal bigDecimal = toMoney(BigDecimal.valueOf(money));
		return bigDecimal;
	}
	
	
	public static BigDecimal toMoney(BigDecimal money) {
		if (money==null) {
			money = BigDecimal.ZERO;
		}
		money.setScale(2, BigDecimal.ROUND_HALF_UP);
		DecimalFormat df = new DecimalFormat("0.00");
		return new BigDecimal(df.format(money));
	}
	
	public static BigDecimal getBigDecimal(String money,int scale){
		money = StringUtils.isEmpty(money) ? "0" : money;
		BigDecimal bigDecimal = getBigDecimal(BigDecimal.valueOf(Float.parseFloat(money)),scale);
		return bigDecimal;
	}
	
	public static BigDecimal getBigDecimal(Double money,int scale) {
		BigDecimal bigDecimal = getBigDecimal(BigDecimal.valueOf(money),scale);
		return bigDecimal;
	}
	
	public static BigDecimal getBigDecimal(float money,int scale){
		BigDecimal bigDecimal = getBigDecimal(BigDecimal.valueOf(money),scale);
		return bigDecimal;
	}
	
	public static BigDecimal getBigDecimal(BigDecimal d,int scale){
		if (d==null) {
			d = BigDecimal.ZERO;
		}
		d.setScale(scale, BigDecimal.ROUND_HALF_UP);
		DecimalFormat df = new DecimalFormat(getScaleZeros(scale));
		return new BigDecimal(df.format(d));
	}
	
	private static String getScaleZeros(int scale){
		String z = "0";
		for(int i=0;i<scale;i++){
			if(i==0) z += ".";
			z += "0";
		}
		return z;
	}
}
