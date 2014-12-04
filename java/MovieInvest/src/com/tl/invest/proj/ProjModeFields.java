package com.tl.invest.proj;

import com.tl.common.StringUtils;
import com.tl.invest.common.MoneyHelper;
import com.tl.kernel.DataType;

public enum ProjModeFields {
	projID("mode_projID","所属项目ID",DataType.LONG) {
		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setProjId(Long.parseLong(value));
		}
	},
	name("mode_name","名称",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setName(value);
		}
	},
	price("mode_price","价格",DataType.FLOAT) {
		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setPrice(MoneyHelper.toMoney(value));
		}
	},
	countGoal("mode_countGoal","目标数",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setCountGoal(Integer.parseInt(value));
		}
	},
	countSupport("mode_countSupport","支持数",DataType.INTEGER){

		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setCountSupport(Integer.parseInt(value));
		}
		
	},
	order("mode_order","排序码",DataType.INTEGER){

		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setOrder(Integer.parseInt(value));
		}
		
	},
	imgURL("mode_imgURL","图片地址",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setImgURL(value);
		}
	},
	returnContent("mode_return","回报",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setReturnContent(value);
		}
	},
	returnTime("mode_returntime","回报时间",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setReturntime(value);
		}
	},
	freight("mode_freight","邮费",DataType.FLOAT) {
		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setFreight(MoneyHelper.toMoney(value));
		}
	},
	deleted("mode_deleted","是否删除",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setDeleted(Integer.parseInt(value));
		}
	},
	status("mode_status","状态",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			mode.setStatus(Integer.parseInt(value));
		}
	}
	;
	
	private String fieldCode;
	private String fieldName;
	private DataType dataType;
	private ProjModeFields(String code,String name,DataType dataType){
		this.fieldCode = code;
		this.fieldName = name;
		this.dataType = dataType;
	}
	
	public String fieldCode(){
		return fieldCode;
	}
	public String fieldName(){
		return fieldName;
	}
	public DataType dataType() {
		return dataType;
	}
	
	public abstract void setValue(ProjMode mode,String value);	
}
