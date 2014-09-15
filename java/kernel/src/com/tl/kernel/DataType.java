package com.tl.kernel;

import java.sql.Types;

public enum DataType {
	CHAR("CHAR") {
		@Override
		public int SqlType() {
			return Types.CHAR;
		}
	}, 
	VARCHAR("VARCHAR") {
		@Override
		public int SqlType() {
			return Types.VARCHAR;
		}
		
		
	}, 
	INTEGER("INTEGER") {
		@Override
		public int SqlType() {
			return Types.INTEGER;
		}
		@Override
		public EditType[] EditType(){
			return new EditType[]{
					EditType.INPUT,
					EditType.INPUT_AUTOCOMPLETE,
					EditType.INPUT_AUTOCOMPLETE_KV,
					EditType.SELECT,
					EditType.SELECT_DYNAMIC,
					EditType.CATEGORY_SELECT,
					EditType.CATEGORY,
					EditType.RADIO,
					EditType.RADIO_DYNMIC,
					EditType.CHECKBOX_YESNO,
					EditType.TREE_ORG,
					EditType.TREE_ORG_PERMISSION,
					EditType.TREE_USER,
					EditType.TREE_USER_PERMISSION,
					EditType.TREE_ROLE,
					EditType.TREE_ROLE_PERMISSION
			};
		}
	}, 
	LONG("LONG") {
		@Override
		public int SqlType() {
			return Types.BIGINT;
		}
	},
	FLOAT("FLOAT") {
		@Override
		public int SqlType() {
			return Types.FLOAT;
		}
	},
	DOUBLE("DOUBLE") {
		@Override
		public int SqlType() {
			return Types.DOUBLE;
		}
	},
	BLOB("BLOB") {
		@Override
		public int SqlType() {
			return Types.BLOB;
		}
	},
	CLOB("CLOB") {
		@Override
		public int SqlType() {
			return Types.CLOB;
		}
	},
	DATE("DATE") {
		@Override
		public int SqlType() {
			return Types.DATE;
		}
	},
	TIME("TIME") {
		@Override
		public int SqlType() {
			return Types.TIME;
		}
	},
	DATETIME("DATETIME"){
		@Override
		public int SqlType() {
			return Types.TIMESTAMP;
		}
	},
	TIMESTAMP("TIMESTAMP") {
		@Override
		public int SqlType() {
			return Types.TIMESTAMP;
		}
	},
	EXTFILE("EXTFILE") {
		@Override
		public int SqlType() {
			return Types.VARCHAR;
		}
		
		@Override
		public String placeholder() {
			return "bfilename(?,?)";
		}
	};
	
	private String name;
	private DataType(String name){
		this.name = name;
	}
	
	public String typeName(){
		return this.name;
	}
	
	public abstract int SqlType();
	public String placeholder(){
		return "?";
	}
	
	public EditType[] EditType(){
		return EditType.values();
	}
}
