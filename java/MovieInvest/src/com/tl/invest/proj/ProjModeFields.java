package com.tl.invest.proj;

import com.tl.invest.common.MoneyHelper;
import com.tl.kernel.DataType;

public enum ProjModeFields {
	projID("mode_projID","������ĿID",DataType.LONG) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setProjId(Long.parseLong(value));
		}
	},
	name("mode_name","����",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setName(value);
		}
	},
	price("mode_price","�۸�",DataType.FLOAT) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setPrice(MoneyHelper.toMoney(value));
		}
	},
	countGoal("mode_countGoal","Ŀ����",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setCountGoal(Integer.parseInt(value));
		}
	},
	imgURL("mode_imgURL","ͼƬ��ַ",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setImgURL(value);
		}
	},
	returnContent("mode_return","�ر�",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setReturnContent(value);
		}
	},
	returnTime("mode_returntime","�ر�ʱ��",DataType.VARCHAR) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setReturntime(value);
		}
	},
	freight("mode_freight","�ʷ�",DataType.FLOAT) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setFreight(MoneyHelper.toMoney(value));
		}
	},
	deleted("mode_deleted","�Ƿ�ɾ��",DataType.INTEGER) {
		@Override
		public void setValue(ProjMode mode, String value) {
			mode.setDeleted(Integer.parseInt(value));
		}
	},
	status("mode_status","״̬",DataType.INTEGER) {
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
