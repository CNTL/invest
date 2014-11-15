package com.tl.invest.proj;

import com.tl.invest.common.MoneyHelper;
import com.tl.kernel.DataType;

public enum ProjModeFields {
	projID("mode_projID","所属项目ID",DataType.LONG) {
		@Override
		public void setValue(ProjMode mode, String value) {
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
			mode.setPrice(MoneyHelper.toMoney(value));
		}
	},
	countGoal("mode_countGoal","目标数",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setCountGoal(Integer.parseInt(value));
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
			mode.setFreight(MoneyHelper.toMoney(value));
		}
	},
	deleted("mode_deleted","是否删除",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setDeleted(Integer.parseInt(value));
		}
	},
	status("mode_status","状态",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
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
