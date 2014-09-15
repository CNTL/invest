package com.tl.db.cfg;

import com.tl.db.dialect.DB2Dialect;
import com.tl.db.dialect.Dialect;
import com.tl.db.dialect.MySQLDialect;
import com.tl.db.dialect.OracleDialect;
import com.tl.db.dialect.PostgreSQLDialect;
import com.tl.db.dialect.SQLServerDialect;
import com.tl.db.dialect.SybaseDialect;

public enum DBType {
	ORACLE("oracle") {
		@Override
		public Dialect dialcet() {
			return oracleDialect;
		}
	} ,
	SQLSERVER("sqlserver") {
		@Override
		public Dialect dialcet() {
			return sqlServerDialect;
		}
	},
	MYSQL("mysql") {
		@Override
		public Dialect dialcet() {
			return mysqlDialect;
		}
	},
	SYBASE("sybase") {
		@Override
		public Dialect dialcet() {
			return sybaseDialect;
		}
	},
	DB2("db2") {
		@Override
		public Dialect dialcet() {
			return db2Dialect;
		}
	},
	POSTGRESQL("postgresql") {
		@Override
		public Dialect dialcet() {
			return postgreSQLDialect;
		}
	};
	
	private String typeName;
	private DBType(String typeName){
		this.typeName = typeName;
	}
	
	public String typeName(){
		return this.typeName;
	}
	
	public abstract Dialect dialcet();
	
	
	private static final Dialect oracleDialect = new OracleDialect();
	private static final Dialect sqlServerDialect = new SQLServerDialect();
	private static final Dialect mysqlDialect = new MySQLDialect();
	private static final Dialect sybaseDialect = new SybaseDialect();
	private static final Dialect postgreSQLDialect = new PostgreSQLDialect();
	private static final Dialect db2Dialect = new DB2Dialect();
}
