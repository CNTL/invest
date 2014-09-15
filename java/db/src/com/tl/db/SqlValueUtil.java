package com.tl.db;

/**
 * �ṩ����SqlValue����ֵ�Ĺ��߷���
 * 
 */
public class SqlValueUtil {

	/**
	 * �Ѹ���sql���ʽ��װΪSqlValue���Ͷ�����toSqlString()�����ظñ��ʽ��
	 * 
	 * @param sql sql���ʽ������һ��ֵ
	 * @return SqlValue���Ͷ�����toSqlString()�����ز������ʽ��
	 */
	public static SqlValue wrap( String sql ) {
		return new SqlValueImpl( sql );
	}

	// -------------------------------------------------------------------------

	private static class SqlValueImpl implements SqlValue {

		private String sql;

		/**
		 * @param sql
		 */
		public SqlValueImpl( String sql ) {
			if ( sql == null )
				throw new NullPointerException();
			this.sql = sql;
		}

		/**
		 * @see com.tl.db.SqlValue#toSqlString()
		 */
		public String toSqlString() {
			return sql;
		}

		public boolean equals( Object obj ) {
			if ( obj instanceof SqlValue ) {
				return sql.equals( ( ( SqlValue ) obj ).toSqlString() );

			}
			return false;
		}

		public int hashCode() {
			return sql.hashCode();
		}

		public String toString() {
			return sql;
		}
	}

}
