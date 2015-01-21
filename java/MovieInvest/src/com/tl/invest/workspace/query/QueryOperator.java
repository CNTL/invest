package com.tl.invest.workspace.query;

/**
 * �������壺�ĵ��������ơ�<br/>
 * ÿ������������������<br/>
 * QueryOperator.XXX.operator();<br/>
 * 
 * ���������·�ʽ�õ�������<br/>
 * QueryOperator operator = Enum.valueOf(QueryOperator.class, "XXX");<br/><br/>
 * 
 * @author rengy
 */
public enum QueryOperator {
	EQ("EQ") {
		@Override
		public String operator() {
			return "=";
		}
	},
	NEQ("NEQ") {
		@Override
		public String operator() {
			return "<>";
		}
	},
	IN("IN") {
		@Override
		public String operator() {
			return " in ";
		}
	},
	NIN("NIN") {
		@Override
		public String operator() {
			return " not in ";
		}
	},
	GT("GT") {
		@Override
		public String operator() {
			return ">";
		}
	},
	GE("GE") {
		@Override
		public String operator() {
			return ">=";
		}
	},
	LT("LT") {
		@Override
		public String operator() {
			return "<";
		}
	},
	LE("LE") {
		@Override
		public String operator() {
			return "<=";
		}
	},
	LIKE("LIKE"){
		@Override
		public String operator() {
			return " like ";
		}
	},
	LLIKE("LIKE"){
		@Override
		public String operator() {
			return " like ";
		}
	},
	RLIKE("LIKE"){
		@Override
		public String operator() {
			return " like ";
		}
	};
	
	private String operatorName;
	private QueryOperator(String name) {
		this.operatorName = name;
	}
	public String operatorName() {
		return this.operatorName;
	}
	public abstract String operator();
}
