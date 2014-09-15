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
	 * 根据名称生成id返回
	 * @param idname	表名
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname) throws TLException {
		return getID(idname,"","", 1);
	}
	
	/**
	 * 根据名称生成id返回
	 * @param idname 表明
	 * @param prefix 前缀
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname,String prefix) throws TLException {
		return getID(idname,prefix,"", 1);
	}
	
	/**
	 * 根据名称生成id返回
	 * @param idname	表名
	 * @param prefix	前缀
	 * @param suffix	后缀
	 * @param increment	增量
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
	 * 根据名称生成id返回
	 * @param idname	表明
	 * @param session	
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname,DBSession session) throws Exception{
		return getID(idname, "", "", 1, session);
	}
	
	/**
	 * 根据名称生成id返回，指定增量
	 * @param sess
	 * @param idname	表名
	 * @param prefix
	 * @param suffix
	 * @param increment
	 * @return
	 * @throws Exception
	 */
	public static long getID(String idname,String prefix,String suffix, int increment,DBSession session) throws TLException{
		if(StringUtils.isEmpty(idname)) throw new TLException("获取表的ID：表名不能为空"); 
		idname = idname.toUpperCase();
		IDConfig ic = (IDConfig) idconfig.get(idname+"$"+prefix+"$"+suffix);

		try {
			// 如果还没有这种id，则注册为normal类型
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
			// 查不到，说明是一种新ID
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
	 * 注册一种ID及其配置，以后可以通过TBID.getID(tcode)生成ID
	 * @param name id名 (not null)
	 * @param type id类型 (not null)
	 * @param prefix
	 * @param suffix
	 * @param param 需要的参数 (null safe)
	 */	
	private static IDConfig registerID(String name, String type,String prefix,String suffix, String param) {
		if (name == null || type == null)
			throw new NullPointerException();

		IDConfig aid = new IDConfig(name, type,prefix,suffix, param);
		idconfig.put(name+"$"+prefix+"$"+suffix, aid);
		return aid;
	}
	/**
	 * 在sysid表中插入一条新记录，登记一种新ID
	 * @param dbsession
	 * @param name	id名
	 * @throws SQLException
	 */
	private static void registerIDInDB(String name,String prefix,String suffix,int increment, DBSession dbsession)
			throws SQLException {
		dbsession.executeUpdate(insertsql, new Object[] { name,prefix,suffix,increment });
	}
	
	
	// ----------------------------------------------------------
	/**
	 * 定义了一种ID并包含其配置信息
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
