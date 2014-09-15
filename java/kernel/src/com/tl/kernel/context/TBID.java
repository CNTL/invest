package com.tl.kernel.context;

import java.sql.SQLException;
import java.util.HashMap;

import com.tl.common.ResourceMgr;
import com.tl.common.StringUtils;
import com.tl.db.DBSession;

public class TBID {
	private static HashMap<String, IDConfig> idconfig = new HashMap<String, IDConfig>();
	private static final int MAX_RETRY = 10;
	private static final String selectsql = "select idvalue from SYSID where tbcode=? and idprefix=? and idsuffix=?";
	private static final String insertsql = "insert into SYSID (tbcode, idprefix, idsuffix,idvalue, increment) values (?, ?, ?, 1, ?)";
	private static final String updatesql = "update SYSID set idvalue=?,increment=? where tbcode=? and idprefix=? and idsuffix=? and idvalue=? and increment=?";
	
	/**
	 * ������������id����
	 * @param idname	����
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname) throws TLException {
		return getID(idname,"","", 1);
	}
	
	/**
	 * ������������id����
	 * @param idname ����
	 * @param prefix ǰ׺
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname,String prefix) throws TLException {
		return getID(idname,prefix,"", 1);
	}
	
	/**
	 * ������������id����
	 * @param idname	����
	 * @param prefix	ǰ׺
	 * @param suffix	��׺
	 * @param increment	����
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname,String prefix,String suffix, int increment) throws TLException {
		DBSession dbsession = null;
		try {
			dbsession = Context.getDBSession();
			return getID(idname, prefix,suffix,increment,dbsession);
		} finally {
			ResourceMgr.closeQuietly(dbsession);
		}
	}
	
	/**
	 * ������������id����
	 * @param idname	����
	 * @param session	
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname,DBSession session) throws Exception{
		return getID(idname, "", "", 1, session);
	}
	
	/**
	 * ������������id���أ�ָ������
	 * @param sess
	 * @param idname	����
	 * @param prefix
	 * @param suffix
	 * @param increment
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname,String prefix,String suffix, int increment,DBSession session) throws TLException{
		if(StringUtils.isEmpty(idname)) throw new TLException("��ȡ���ID����������Ϊ��"); 
		idname = idname.toUpperCase();
		IDConfig ic = (IDConfig) idconfig.get(idname+"$"+prefix+"$"+suffix);

		try {
			// �����û������id����ע��Ϊnormal����
			if (ic == null) {
				ic = registerID(idname, "normal",prefix,suffix, null);
			}

			if ("sequence".equals(ic.type)) {
				return session.getSequenceNextValue(ic.param);
			}

			return normalGenerate(session,idname,prefix,suffix,increment);
		} catch (SQLException e) {
			throw new TLException(e);
		}
	}
	
	private static long normalGenerate(DBSession dbsession,String idname,String prefix,String suffix,int increment) throws TLException{
		return normalGenerate(dbsession,idname,prefix,suffix,increment, MAX_RETRY);
	}
	
	private static long normalGenerate(DBSession dbsession,String idname,String prefix,String suffix,
			int increment,int retryCounter) throws TLException{
		try {
			long oldvalue = dbsession.getLong(selectsql,new Object[] { idname,prefix,suffix });
			// �鲻����˵����һ����ID
			if (oldvalue == 0) {
				registerIDInDB(idname,prefix,suffix,increment,dbsession);
				oldvalue = 1;
			}

			long newvalue = oldvalue + increment;
			Object[] params = { new Long(newvalue),increment, idname,prefix,suffix, new Long(oldvalue),increment };
			int rt = dbsession.executeUpdate(updatesql, params);

			if (rt > 0) {
				return oldvalue;
			}

			if (retryCounter > 0) {
				retryCounter--;
				return normalGenerate(dbsession, idname,prefix,suffix,increment,retryCounter);
			} else {
				throw new TLException("Concurrency update too frequent! (Retryed "+ MAX_RETRY + " times)");
			}
		} catch (SQLException e) {
			throw new TLException(e);
		}
	}
	
	/**
	 * ע��һ��ID�������ã��Ժ����ͨ��TBID.getID(tcode)����ID
	 * @param name id�� (not null)
	 * @param type id���� (not null)
	 * @param prefix
	 * @param suffix
	 * @param param ��Ҫ�Ĳ��� (null safe)
	 */	
	private static IDConfig registerID(String name, String type,String prefix,String suffix, String param) {
		if (name == null || type == null)
			throw new NullPointerException();

		IDConfig aid = new IDConfig(name, type,prefix,suffix, param);
		idconfig.put(name+"$"+prefix+"$"+suffix, aid);
		return aid;
	}
	/**
	 * ��sysid���в���һ���¼�¼���Ǽ�һ����ID
	 * @param dbsession
	 * @param name	id��
	 * @throws SQLException
	 */
	private static void registerIDInDB(String name,String prefix,String suffix,int increment, DBSession dbsession)
			throws SQLException {
		dbsession.executeUpdate(insertsql, new Object[] { name,prefix,suffix,increment });
	}
	
	
	// ----------------------------------------------------------
	/**
	 * ������һ��ID��������������Ϣ
	 * 
	 */
	private static class IDConfig {
		String name;
		String prefix;
		String suffix;
		String type;
		String param;
		
		public IDConfig(String name, String type,String prefix,String suffix, String param) {
			super();
			this.name = name;
			this.type = type;
			this.param = param;
			this.prefix = prefix;
			this.suffix = suffix;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj) {
			if (obj instanceof IDConfig) {
				return (name+"$"+prefix+"$"+suffix).equals(((IDConfig) obj).name+"$"+((IDConfig) obj).prefix+"$"+((IDConfig) obj).suffix);
			}
			return false;
		}

		/**
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return (name+"$"+prefix+"$"+suffix).hashCode();
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return name+"$"+prefix+"$"+suffix;
		}
	}
}
