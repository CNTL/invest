package com.tl.kernel.sys.dic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.tl.common.ResourceMgr;
import com.tl.db.DBSession;
import com.tl.db.IResultSet;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;

public class DictionaryManagerImpl implements DictionaryManager {

	private static String m_SelectSQL = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_id=? and dic_valid=1";
	private static String m_InsertSQL = "insert into "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+ "(dic_typeid,dic_pid,dic_code,dic_name,dic_level,dic_order,dic_memo,dic_LastModified,dic_valid,dic_childCount,dic_value,dic_text,dic_id)"
			+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String m_UpdateSQL = "update "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+"set dic_typeid=?,dic_pid=?, dic_code=?, dic_name=?, dic_level=?, dic_order=?, dic_memo=?, dic_LastModified=?, dic_valid=?, dic_childCount=? ,dic_value=? ,dic_text=? where dic_id=?";
	private static String m_SelectLikeSQL = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_name like ? and dic_valid=1";
	private static String m_SelectByCodeSQL = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_code=? and dic_valid=1";
	/**
	 * 取一个字典项的主属性
	 * @param typeID
	 * @param dicID
	 * @param db
	 * @return
	 * @throws TLException
	 */
	private Dictionary getBareDic(int typeID, int dicID, DBSession db)	throws TLException{
		return querySingle(typeID, m_SelectSQL, new Object[]{dicID}, db);
	}
	
	private Dictionary getDic(int typeID, int dicID,DBSession db) throws TLException	{
		//主属性
		Dictionary cat = getBareDic(typeID, dicID, db);
		return cat;
	}
	
	private static String m_SelectAllSQL = 	"select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_valid=1 order by dic_level, dic_order";
	/**
	 * 取某字典类型的所有项，只取主属性
	 * @param typeID
	 * @param db
	 * @return
	 * @throws TLException
	 */
	private Dictionary[] getBareDics(int typeID, DBSession db)
	throws TLException
	{
		return query(typeID, m_SelectAllSQL, new Object[]{typeID}, db);
	}

	private static String m_SelectSubSQL = 	"select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_pid=? and dic_typeid=? and dic_valid=1 order by dic_order";
	/**
	 * 取某字典项的直接子项
	 * @param typeID
	 * @param dicID
	 * @param db
	 * @return
	 * @throws TLException
	 */
	private Dictionary[] getBareSubDics(int typeID, int dicID, DBSession db)
	throws TLException
	{
		return query(typeID, m_SelectSubSQL, new Object[]{dicID, typeID}, db);
	}
	
	private static String m_SelectMultiSubSQL = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_pid in (@PARENTID@) and dic_valid=1 order by dic_level, dic_order";
	private static String m_SelectALLMultiSubSQL = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_pid in (@PARENTID@) order by dic_level, dic_order";	
	/**
	 * 把多个父项的直接子项一次性取出
	 * 用于向下传递修改
	 * @param dicType
	 * @param dicID
	 * @param db
	 * @return
	 * @throws TLException
	 */
	private Dictionary[] getBareSubDics(DictionaryType dicType, int[] dicID, DBSession db,boolean deleted)
	throws TLException
	{
		String sql;
		if (!deleted) {// 不显示删除的
			sql = m_SelectMultiSubSQL.replaceAll("@PARENTID@", DictionaryHelper.join(dicID, ','));
		} else {// 显示全部的
			sql = m_SelectALLMultiSubSQL.replaceAll("@PARENTID@", DictionaryHelper.join(dicID, ','));
		}
		
		return query(dicType.getId(), sql, new Object[] { dicType.getId() }, db);
	}
	
	private static String m_SelectSQL2 = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_id=?";
	
	/**
	 * 取一个字典的主属性(删除的未删除的)
	 * @param typeID
	 * @param dicID
	 * @param db
	 * @return
	 * @throws TLException
	 */
	private Dictionary getBareALLDic(int typeID, int dicID, DBSession db)
	throws TLException
	{
		return querySingle(typeID, m_SelectSQL2, new Object[]{dicID}, db);
	}
	
	@Override
	public Dictionary getDic(String typeName, int dicID) throws TLException {
		DictionaryType type = getType(typeName);
		return getDic(type.getId(), dicID);
	}

	@Override
	public Dictionary getDic(int typeID, int dicID) throws TLException {
		DBSession db = Context.getDBSession();
		try{
			return getDic(typeID,dicID,db);
		}finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
	}

	private static String m_SelectByNameSQL = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_name=? and dic_valid=1";
	
	@Override
	public Dictionary getDicByName(int typeID, String name) throws TLException {
		return querySingle(typeID, m_SelectByNameSQL, new Object[]{typeID, name});
	}

	@Override
	public Dictionary[] getDics(String typeName) throws TLException {
		DictionaryType type = getType(typeName);
		return getDics(type.getId());
	}

	@Override
	public Dictionary[] getDics(int typeID) throws TLException {
		DBSession db = Context.getDBSession();
		Dictionary[] dicArray = null;
		try{
			//得到主属性
			dicArray = getBareDics(typeID, db);
		}finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
		return dicArray;
	}

	@Override
	public Dictionary[] getChildrenDics(int typeID, int dicID)
			throws TLException {
		//不包含删除的项
		return getChildrenDics(typeID, dicID, false);
	}

	@Override
	public Dictionary[] getSubDics(String typeName, int dicID)
			throws TLException {
		DictionaryType type = getType(typeName);
		return getSubDics(type.getId(), dicID);
	}

	@Override
	public Dictionary[] getSubDics(int typeID, int dicID) throws TLException {
		if (typeID < 1) return null;
		
		Dictionary[] dicArray = null;
		DBSession db = Context.getDBSession();
		try {
			//得到主属性
			dicArray = getBareSubDics(typeID, dicID, db);
		} finally {
			ResourceMgr.closeQuietly(db);
		}
		return dicArray;
	}

	@Override
	public DictionaryType getType(int id) throws TLException {
		try{
			DAO dao = new DAO();
			return (DictionaryType) dao.get(DictionaryType.class, new Integer(id));
		}catch (Exception e){
			throw new TLException("[GetDictionaryType]", e);
		}
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public DictionaryType getType(String name) throws TLException {
		List list = null;
		try {
			list = DAOHelper.find("from DictionaryType as dictype where dictype.name=:name", name,
					Hibernate.STRING);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list==null || list.size() == 0) return null;
		return (DictionaryType) list.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public DictionaryType[] getTypes() throws TLException {
		DAO dao = new DAO();
		try
		{
			Session s = null;
			try
			{
				s = dao.getSession();
				List list = s.createCriteria(DictionaryType.class)
					.addOrder(Order.asc("order"))
					.addOrder(Order.asc("id"))
					.list();
				if (list.size() == 0) return null;
				return (DictionaryType[])list.toArray(new DictionaryType[0]);
			}
			finally
			{
				dao.closeSession(s);
			}
		}
		catch (HibernateException e)
		{
			throw new TLException("[GetDictionaryType]", e);
		}
	}

	@Override
	public DictionaryType[] getTypes(int sys) throws TLException {
		DictionaryType[] all = getTypes();
		if(all==null || all.length<=0) return null;
		List<DictionaryType> types = new ArrayList<DictionaryType>();
		for (DictionaryType type : all) {
			if(type.getIsSys()==sys && type.getValid()==1){
				types.add(type);
			}
		}
		
		if (types.size() == 0) return null;
		return (DictionaryType[])types.toArray(new DictionaryType[0]);
	}
	private static final String GET_CATEGORY_BY_CATIDS = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_id in (@dic_id@) order by dic_level, dic_order";
	
	@Override
	public Dictionary[] getDics(int typeID, String dicIDs) throws TLException {
		if(dicIDs == null || dicIDs.equals(""))
			return null;
		
		String temp = GET_CATEGORY_BY_CATIDS.replaceAll("@dic_id@", dicIDs);
		return query(typeID, temp, new Object[] { typeID });
	}
	private static String m_findSQL = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_name like ? or dic_code like ?";
	@Override
	public Dictionary[] findDics(int typeID, String name) throws TLException {
		List<Dictionary> list = new ArrayList<Dictionary>();
		
		name = "%" + name +"%";
		DBSession db = Context.getDBSession();
		try {
			IResultSet rs = db.executeQuery(getSQL(typeID, m_findSQL), new Object[]{name, name});
			while (rs.next())
				list.add(readRS(rs));
			rs.close();
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(db);
		}
		
		return list.toArray(new Dictionary[0]);
	}

	@Override
	public Dictionary getDicByCode(int typeID, String dicCode)
			throws TLException {
		return querySingle(typeID, m_SelectByCodeSQL, new Object[]{typeID, dicCode});
	}

	@Override
	public void createType(DictionaryType type) throws TLException {
		int nDSID;
		try
		{
			nDSID = (int)TBID.getID(SysTableLibs.TB_SYS_DICTIONARYTYPE.getTableCode());
		}
		catch (Exception e1)
		{
			throw new TLException("[createType]GetID", e1);
		}
		create(type, nDSID);
	}
	/**
	 * 给出ID，创建类型
	 */
	void create(DictionaryType type, int id) throws TLException
	{
		try
		{
			DAO dao = new DAO();
			type.setId(id);
			//类型的顺序，创建时使与ID相同
			//因为这个ID一定是已有的类型中最大的，所以可以保证新建的类型排序最大
			type.setOrder(id);	
			dao.save(type);
		}
		catch (HibernateException e)
		{
			throw new TLException("[DictionaryTypeCreate]", e);
		}
	}
	@Override
	public void updateType(DictionaryType type) throws TLException {
		try
		{
			DAO dao = new DAO();
			dao.update(type);
		}
		catch (HibernateException e)
		{
			throw new TLException("[DictionaryTypeUpdate]", e);
		}
	}

	private static String m_DeleteTypeSQL = "delete from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=?";
	@SuppressWarnings("deprecation")
	@Override
	public void deleteType(int id) throws TLException {
		DictionaryType type = getType(id);
		if (type == null) return;
		
		try {
			DAOHelper.delete("delete from DictionaryType as dictype where dictype.id=:ct",
					id,
					Hibernate.INTEGER);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		DBSession db = Context.getDBSession();
		try{
			db.beginTransaction();
			
			db.executeUpdate(getSQL(type, m_DeleteTypeSQL), new Object[]{new Integer(type.getId())});
			db.commitTransaction();
		}catch (SQLException e){
			try{db.rollbackTransaction();} catch(Exception e1) {e1.printStackTrace();}
			throw new TLException("[CatDelete]", e);
		}finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
	}

	@Override
	public Dictionary[] getDicsByName(int typeID, String dicName)
			throws TLException {
		return query(typeID, m_SelectLikeSQL, new Object[]{new Integer(typeID), "%" + dicName + "%"});
	}

	@Override
	public void createDic(Dictionary dic) throws TLException {		
		//新的ID
		int id = (int)TBID.getID(SysTableLibs.TB_SYS_DICTIONARY.getTableCode());
		dic.setId(id);
		dic.setLastModified(new Date());
		dic.setOrder(id);
		DBSession db = Context.getDBSession();
		try{
			db.beginTransaction();
			//1)设置Level = parentLevel + 1
			if(dic.getPid() == 0)
				dic.setLevel(0);
			else{
				Dictionary parent = this.getDic(dic.getType(),dic.getPid(),db);
				dic.setLevel(parent.getLevel()+1);
			}
			
			//2)save
			String sql = getSQL(dic, m_InsertSQL);
			db.executeUpdate(sql, getValueArray(dic), getFieldTypes());
			
			//3)设置级联名称			
			upateCascade(dic,db);
			
			//4)如果有父节点更新childcount+1
			if(dic.getPid()>0)
			{
				updateChildCount(dic.getType(),dic.getPid(),1,db);
			}
			
			db.commitTransaction();
			
		}catch (Exception e){
			try{db.rollbackTransaction();}catch(Exception ex){}
			throw new TLException("[CatCreate]", e);
		}
		finally
		{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
	}

	@Override
	public void updatedic(Dictionary dic) throws TLException {
		//1.取出旧的字典项，待比较
		Dictionary oldDic = getDic(dic.getType(), dic.getId());
		//调用DBSession
		DBSession db = Context.getDBSession();
		try
		{
			db.beginTransaction();
			DictionaryType type = getType(dic.getType());
			//2.修改本字典项
			//用oldDic进行保存，保存前先把可改变的值赋上。注意不能改变Level
			db.executeUpdate(getSQL(type, m_UpdateSQL), getValueArray(dic), getFieldTypes());
			
			//3.检查名称是否发生变化，若有变化则需要修改所有子项
			if (!dic.getName().equals(oldDic.getName()))
				updateChildCascadeName(dic, db);
			
			//5.事务提交
			db.commitTransaction();
		}
		catch (SQLException e)
		{
			try{db.rollbackTransaction();}catch(Exception e1){e1.printStackTrace();}
			throw new TLException("[CatCreate]", e);
		}
		finally
		{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
	}

	@Override
	public void move(int typeID, int srcDicID, int destDicID)
			throws TLException {
		Dictionary dic = null;
		int dLevel;
		int srcParentID = 0;
		
		//目标ID为0，表示放到根下
		if (destDicID == 0) {
			dic = getDic(typeID, srcDicID);
			//2.记录层差
			dLevel = 0 - dic.getLevel();
			//设置移动后的层次、排序、父ID
			dic.setLevel(0);//层次为父项的下一层
			srcParentID = dic.getPid();
		}
		else{
			//1.取出两个项，待比较
			Dictionary parentDic = getDic(typeID, destDicID);
			dic = getDic(typeID, srcDicID);
			srcParentID = dic.getPid();
			
			//2.记录层差
			dLevel = parentDic.getLevel() - dic.getLevel() + 1;
	
			//设置移动后的层次、排序、父ID
			dic.setLevel(parentDic.getLevel() + 1);//层次为父项的下一层
		}
		
		dic.setOrder(nextDispOrder(dic.getType(),destDicID));
		dic.setPid(destDicID);//设置父项

		DictionaryType dicType = getType(typeID);

		//调用DBSession
		DBSession db = Context.getDBSession();
		try{
			db.beginTransaction();
			//3.修改本项
			db.executeUpdate(getSQL(dicType, m_UpdateSQL), getValueArray(dic), getFieldTypes());
			
			//4.检查名称/层次是否发生变化，若有变化则需要修改所有子项			
			//发生移动便更新级联
			updateChildCascade(dic,destDicID, dLevel,db);
			
			//5.移动一个项时，源父项childcount-1，目标父项childcount+1			
			updateChildCount(typeID,srcParentID,-1,db);
			updateChildCount(typeID,destDicID,1,db);
			
			//6.事务提交
			db.commitTransaction();
		}catch (Exception e){
			e.printStackTrace();
			try{db.rollbackTransaction();}catch(Exception e1){e1.printStackTrace();}
			throw new TLException("[CatMove]", e);
		}finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
	}

	@Override
	public void updateTransfer(Dictionary dic, int[] fields) throws TLException {
		int size = fields.length;
		Object[] params = new Object[size];
		int[] types = new int[size];

		//取类型
		DictionaryType dicType = getType(dic.getType());

		//准备sql语句，以及参数，和参数类型
		String sql = prepaire(dicType, dic, fields, params, types);
		
		DBSession db = Context.getDBSession();
		try{
			db.beginTransaction();
			updateTransfer(dicType, sql, new int[]{dic.getId()}, params, types, db);
			db.commitTransaction();
		}catch (SQLException e){
			try{db.rollbackTransaction();}catch(Exception e1){e1.printStackTrace();}
			throw new TLException("[CatUpdateTransfer]", e);
		}finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
	}

	@Override
	public void deleteDic(int typeID, int dicID) throws TLException {
		catDelete(typeID, dicID, 1);
	}

	@Override
	public void deleteDic(String typeName, int dicID) throws TLException {
		deleteDic(getType(typeName).getId(), dicID);
	}

	@Override
	public void restoreDic(int typeID, int dicID) throws TLException {
		catDelete(typeID, dicID, 0);
	}

	private static String m_SelectDeletedSQL = 
			"select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_typeid=? and dic_valid=0 order by dic_level, dic_order";
	@Override
	public Dictionary[] getAllDeleted(int typeID) throws TLException {
		return query(typeID, m_SelectDeletedSQL, new Object[]{typeID});
	}

	@Override
	public Dictionary[] getChildrenDicsIncludeDeleted(int typeID, int dicID)
			throws TLException {
		//包含删除的字典项
		return getChildrenDics(typeID, dicID, true);
	}
	
	private Dictionary[] getChildrenDics(int typeID, int dicID,boolean deleted) throws TLException
	{
		//取出类型		
		String cascadeID = null;
		
		if(dicID>0){
			Dictionary dic = getDic(typeID,dicID);
			if(dic == null)
				return null;
			cascadeID = dic.getCascadeId();
		}		
		
		Dictionary dics[] = null;
		DBSession db = Context.getDBSession();
		try{
			//1) 取得所有子孙项(不包括本身)
			dics = getAllChildrenDicsByCascadeID(typeID,cascadeID,deleted,db);
			if(dics == null) return dics;			
		}
		finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}		
		return dics;
	}
	
	
	private static String m_DeleteSQL = "update "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" set dic_valid=? where dic_CascadeID like ?";
	private static String m_DeleteSelfSQL = "update "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+""
			+ " set dic_valid=? where dic_id=?";
	/**
	 * 执行字典项的删除和恢复动作
	 * 因为在删除和恢复时都需要同时修改子项，所以比较复杂。
	 * @param typeID
	 * @param dicID
	 * @param delete
	 * @throws E5Exception
	 */
	private void catDelete(int typeID, int dicID, int delete) throws TLException
	{
		//调用DBSession
		DBSession db = Context.getDBSession();
		try
		{
			DictionaryType type = getType(typeID);

			//1.取得字典项信息，主要是级联ID有用
			Dictionary dic = getBareALLDic(typeID,dicID,db);	
			//操作前取要操作的列表
			Dictionary[] dics = null;
			if(delete != 1)
				dics = getAllChildrenDicsByCascadeID(typeID,dic.getCascadeId(),true,db);
			
			//2.根据级联ID，拼出修改子项的DELETEFLAG的SQL			
			String strSQL = getSQL(type, m_DeleteSQL);		
			StringBuffer cascadeCond = new StringBuffer();
			cascadeCond = cascadeCond.append(dic.getCascadeId()).append(Dictionary.separator).append("%");

			db.beginTransaction();//事务

			//删除(或恢复)子项:根据级联ID			
			db.executeUpdate(strSQL, new Object[]{new Integer(delete==1?0:1), cascadeCond.toString() });
			//删除（或恢复）本身
			strSQL = getSQL(type, m_DeleteSelfSQL);		
			db.executeUpdate(strSQL, new Object[]{new Integer(delete==1?0:1), dicID});
			
			//3.维护child_count字段
			//计算父
			if(dic.getPid()!=0)
				calculateChildCount(typeID, dic.getPid(), db);

			//计算自己
			calculateChildCount(typeID, dicID, db);
			//如果是恢复操作，计算子孙
			for (int i = 0; dics != null && i < dics.length; i++)
				calculateChildCount(typeID, dics[i].getId(), db);							
			
			db.commitTransaction();
		}catch (Exception e){
			try{db.rollbackTransaction();} catch(Exception e1) {e1.printStackTrace();}
			throw new TLException("[CatDelete]", e);
		}finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
	}
	
	/**
	 * 准备传递修改的SQL语句，以及SQL语句中的参数和参数类型
	 * @param dicType
	 * @param dic
	 * @param fields 待修改传递的属性标识
	 * @param params SQL语句中的参数
	 * @param types SQL语句中的参数类型
	 * @return
	 */
	private String prepaire(DictionaryType dicType, Dictionary dic, int[] fields, 
			Object[] params, int[] types)
	{
		String strTableName = SysTableLibs.TB_SYS_DICTIONARY.getTableCode();
		if (strTableName == null)
			strTableName =SysTableLibs.TB_SYS_DICTIONARY.getTableCode();
		//拼修改语句
		StringBuffer sb = new StringBuffer(500);
		sb.append("update ").append(strTableName).append(" set ");

		int size = fields.length;
		for (int i = 0; i < size; i++)
		{
			switch (fields[i])
			{
				case Dictionary.TRANS_DICCODE:
					sb.append(Dictionary.FIELD_DICCODE).append("=?,");
					params[i] = dic.getCode();
					types[i] = java.sql.Types.VARCHAR;
					break;
				default:
					break;
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where dic_pid in (@PARENTID@)");
		
		return sb.toString();
	}
 	/**
	 * 修改所有子项的层次，用程序的方式递归调用修改
	 * 注意该方法中对每一层只做一次查找，而不是每个项查一次子项，
	 * 当层次比较深时，传递的int[]可能比较大
	 */
	private void updateTransfer(DictionaryType dicType, String strSQL, int[] dicID,
			Object[] params, int[] types, DBSession db) 
	throws SQLException, TLException
	{
		//修改当前层次
		String sql = strSQL.replaceAll("@PARENTID@", DictionaryHelper.join(dicID, ','));
		db.executeUpdate(sql, params, types);
		
		//找所有子项,若没有子项了，则返回
		Dictionary[] dicArr = getBareSubDics(dicType, dicID, db,false);
		
		if (dicArr == null) return;

		//把子项ID抽取出来
		int[] childrenID = new int[dicArr.length];
		for (int i = 0; i < dicArr.length; i++)
			childrenID[i] = dicArr[i].getId();
		
		//递归：修改所有子类
		updateTransfer(dicType, strSQL, childrenID, params, types, db);
	}
	
	/**
	 * 移动节点时，修改级联名称(自己和所有下级)
	 * @param dicType 类型
	 * @param dicID   当前ID
	 * @param destDicID 目的 =0 根，>1的项ID
	 * 父项的名称或者层次发生了变化，则需要变化子的级联名称 
	 */
	private void updateChildCascade(Dictionary dic, int destDicID,int dlevel,DBSession db)
		throws TLException
	{	
		int typeID = dic.getType();
		//int dicID   = dic.getId();
		
		//1) 设置当前项的级联名称、级联ID
		//Category cat = getCat(catType,catID);
		dic.setPid(destDicID);
		
		String   oname= dic.getCascadeName();  //当前项历史级联名称
		String   oid  = dic.getCascadeId();    //当前项历史级联ID
		
		upateCascade(dic,db);
		
		String   name = dic.getCascadeName();   //当前项的级联名称
		String   id   = dic.getCascadeId();   //当前分项的级联ID
		
		//2) 取得所有子项，设置他们的名称和ID
		Dictionary[] subs = getAllChildrenDicsByCascadeID(typeID,oid,true,db);

		if(subs!=null && subs.length>0)
		{
			int onlen = oname.length();
			int oilen = oid.length();
			
			int[]    dicIDs      = new int[subs.length];
			String[] cascadeID   = new String[subs.length];
			String[] cascadeName = new String[subs.length];
			
			for(int i=0;i<subs.length;i++)
			{
				dicIDs[i]      = subs[i].getId();
				cascadeID[i]   = id+subs[i].getCascadeId().substring(oilen,subs[i].getCascadeId().length());
				cascadeName[i] = name+subs[i].getCascadeName().substring(onlen,subs[i].getCascadeName().length());			
			}
			
		    //3) 构造成数组 批量更新		
			updateCascade(typeID,dicIDs,cascadeName,cascadeID,dlevel,db);			
		}
		//4) 调用更新别名(在无子的情况下更新本身)
		//CatExtCascadeHelper.updateExtChildCascade(catType,catID,destCatID,db.getConnection());
	}
	
	private static String NEXT_DISPORDEDR = "select max(dic_order) disp_order from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_pid=?"; 
	/**
	 * 取得order 
	 * @param typeID
	 * @param dicID
	 * @return
	 * @throws TLException
	 */
	private int nextDispOrder(int typeID,int dicID) throws TLException {
		DBSession db = Context.getDBSession();
		int order = 0;
		IResultSet rs = null;
		try
		{
			 rs = db.executeQuery(getSQL(typeID, NEXT_DISPORDEDR), 
					new Object[]{dicID}
					);
			
			if(rs.next())
				order = rs.getInt("disp_order") + 1;
		}
		catch (SQLException e)
		{
			throw new TLException(e);
		}
		finally
		{
			ResourceMgr.closeQuietly(rs);
			ResourceMgr.closeQuietly(db);
		}
		return order;
	}
	
	private static String GET_ALL_CHILDREN_BY_CASCADEID = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_typeid=? and dic_CascadeID like ? order by dic_level, dic_order ";
	private static String GET_ALL_NORMAL_CHILDREN_BY_CASCADEID = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_typeid=? and dic_CascadeID like ? And dic_valid=0 order by dic_level, dic_order ";
	
	/**
	 * 根据级联ID查找所有子项
	 * @param typeID
	 * @param cascadeID 为null表示查询全部节点
	 * @param deleted true/查询包括带删除的节点,false/查询不包括删除的节点
	 * @return Dictionary[] 所有子孙节点
	 */
	private Dictionary[] getAllChildrenDicsByCascadeID(int typeID,String cascadeID,boolean deleted,DBSession db)
		throws TLException
	{
		String sql = (deleted) ? GET_ALL_CHILDREN_BY_CASCADEID : GET_ALL_NORMAL_CHILDREN_BY_CASCADEID;

		String cond = new StringBuffer().append(cascadeID).append(Dictionary.separator).append("%").toString();
		Object[] params = new Object[]{typeID, cond};
		
		if (db == null) {
			return query(typeID, sql, params);
		} else {
			return query(typeID, sql, params, db);
		}
	}
	/**
	 * 修改名称时，修改所有子节点级联名称
	 * @param dic
	 * @param db
	 * @throws TLException
	 */
	private void updateChildCascadeName(Dictionary dic,DBSession db)
	throws TLException
	{	
		//当前项历史级联名称
		String   oname    = dic.getCascadeName();  
		String ocascadeID = dic.getCascadeId();
		
		upateCascade(dic,db);
		
		//2) 取得所有子项，设置他们的名称和ID
		Dictionary[] subs = getAllChildrenDicsByCascadeID(dic.getType(),ocascadeID,true,db);
	
		if(subs!=null && subs.length>0)
		{
			int onlen = oname.length();
		
			int[]    dicIDs      = new int[subs.length];
			String[] cascadeName = new String[subs.length];
			
			for(int i=0;i<subs.length;i++)
			{
				dicIDs[i]      = subs[i].getId();
				cascadeName[i] = dic.getCascadeName()+subs[i].getCascadeName().substring(onlen,subs[i].getCascadeName().length());			
			}
			
		    //3) 构造成数组 批量更新		
			updateCascade(dic.getType(),dicIDs,cascadeName,null,0,db);			
		}
	
	}
	
	/**
	 * 更新一个字典项的级联名称
	 * @param dic
	 * @throws E5Exception
	 */
	private void upateCascade(Dictionary dic,DBSession db) throws TLException
	{
		//1) 设置当前项的级联名称、级联ID
		String   name = "";   //当前项的级联名称
		String   id   = "";   //当前项的级联ID
		
		
		//根
		if(dic.getPid()<= 0){
			id   = dic.getId() + "";
			name = dic.getName();
		}
		else{
			Dictionary parent = this.getDic(dic.getType(),dic.getPid(),db);
			id   = parent.getCascadeId() + Dictionary.separator + dic.getId();
			name = parent.getCascadeName() + Dictionary.separator + dic.getName();
		}		
		dic.setCascadeId(id);
		dic.setCascadeName(name);
		
		//更新当前类,不更新level，level由move方法更新过了,level=0表示不更新
		updateCascade(dic.getType(),new int[]{dic.getId()},new String[]{name},new String[]{id},0,db);
	}
	private static String UPDATE_CASCADE_ALL_SQL  = "UPDATE "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" set dic_CascadeName=?,dic_CascadeID=?,dic_level=dic_level+? WHERE dic_id=?";
	private static String UPDATE_CASCADE_NAME_SQL = "UPDATE "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" set dic_CascadeName=? WHERE dic_id=?";
	/**
	 * 批量更新字典项的级联名称、及联ID,数组中为设置好的对应关系及其取值。
	 * @param typeID 
	 * @param dicID
	 * @param cascadeName
	 * @param cascadeID
	 * @param level - 层差
	 */
	private void updateCascade(int typeID,int dicID[],String cascadeName[],String cascadeID[],int level,DBSession db)
		throws TLException
	{
		Connection conn  = db.getConnection();
		PreparedStatement pstmt = null;
		try
		{
			//支持只更新cascadeName
			if(cascadeID == null)
				pstmt = conn.prepareStatement(getSQL(typeID,UPDATE_CASCADE_NAME_SQL));
			else
				pstmt = conn.prepareStatement(getSQL(typeID,UPDATE_CASCADE_ALL_SQL));
			
			for(int i=0;i<cascadeName.length;i++)
			{
				pstmt.setString(1,cascadeName[i]);
				if(cascadeID == null)
					pstmt.setInt(2,dicID[i]);
				else
				{
					pstmt.setString(2,cascadeID[i]);
					pstmt.setInt(3,level);
					pstmt.setInt(4,dicID[i]);	
				}
				
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
		}
		catch(Exception e)
		{
			throw new TLException("[Update Cascade]"+e.getMessage());
		}
		finally
		{
			try{pstmt.close();}catch(Exception e){};
			pstmt=null;
		}
	}
	
	private static String UPDATE_CHILDCOUNT = "UPDATE "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" set dic_childCount=dic_childCount+? Where dic_valid=1 AND dic_id=?";
	
	/**
	 * 更新字典项节点孩子节点数量
	 * @param catType
	 * @param parentID
	 * @param value
	 * @param db
	 * @throws SQLException
	 * @throws E5Exception
	 */
	private void updateChildCount(int typeID,int dicID,int value,DBSession db)
		throws SQLException,TLException
	{		
		db.executeUpdate(getSQL(typeID,UPDATE_CHILDCOUNT),new Object[]{value,dicID});
	}
	
	private static String SELECT_CHILD_COUNT = "select count(*) from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_pid=? and dic_valid=1";
	private static String CALCULATE_CHILD_COUNT = "update "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" set dic_childCount=? Where dic_id=?";
	
	//重新计算child_count字段
	private void calculateChildCount(int typeID,int dicID,DBSession db)throws SQLException,TLException
	{		
		int count = 0;
		IResultSet rs = db.executeQuery(getSQL(typeID,SELECT_CHILD_COUNT), new Object[]{dicID});
		if (rs.next()) 
			count = rs.getInt(1);
		rs.close();
		
		db.executeUpdate(getSQL(typeID,CALCULATE_CHILD_COUNT), new Object[]{new Integer(count), dicID});
	}
	
	private Object[] getValueArray(Dictionary dic)
	{
		Object[] objArr = new Object[]{
				new Integer(dic.getType()),
				new Integer(dic.getPid()),
				dic.getCode(),
				dic.getName(),
				new Integer(dic.getLevel()),
				new Integer(dic.getOrder()),
				dic.getMemo(),
				//注意日期类型的转换
				dic.getLastModified() == null ? new Timestamp(System.currentTimeMillis()) :
					new java.sql.Timestamp(dic.getLastModified().getTime()),				
				new Integer(dic.getValid()),
				new Integer(dic.getChildCount()),				
				dic.getValue(),
				dic.getText(),
				new Integer(dic.getId()),
		};
		return objArr;
		
	}
	
	private static int[] m_TypeArr = new int[]{
		Types.INTEGER,/*getType*/
		Types.INTEGER,/*getPID*/
		Types.VARCHAR,/*getCode*/
		Types.VARCHAR,/*getName*/
		Types.INTEGER,/*getLevel*/
		Types.INTEGER,/*getOrder*/
		Types.VARCHAR,/*getMemo*/
		Types.TIMESTAMP,/*getLastModified*/
		Types.INTEGER,/*getValid*/
		Types.INTEGER,/*getChildCount*/		
		Types.VARCHAR,/*getValue*/
		Types.VARCHAR,/*getText*/
		Types.INTEGER,/*getCatID*/
	};

	private int[] getFieldTypes()
	{
		return m_TypeArr;
	}
	
	private Dictionary readRS(IResultSet rs)
	throws TLException
	{
		try
		{
			Dictionary dic =new Dictionary();
			dic.setId(rs.getInt("dic_id"));
			dic.setType(rs.getInt("dic_typeid"));
			dic.setPid(rs.getInt("dic_pid"));
			dic.setName(rs.getString("dic_name"));
			dic.setCode(rs.getString("dic_code"));
			dic.setCascadeId(rs.getString("dic_CascadeID"));
			dic.setCascadeName(rs.getString("dic_CascadeName"));
			dic.setChildCount(rs.getInt("dic_childCount"));
			dic.setLevel(rs.getInt("dic_level"));
			dic.setText(rs.getString("dic_text"));
			dic.setValue(rs.getString("dic_value"));
			
			dic.setLastModified(rs.getDate("dic_LastModified"));
			dic.setOrder(rs.getInt("dic_order"));
			dic.setValid(rs.getInt("dic_valid"));
			dic.setMemo(rs.getString("dic_memo"));
			return dic;
		}
		catch (SQLException e)
		{
			throw new TLException(e);
		}
	}
	
	/**
	 * 根据字典项得到数据库语句
	 */
	private String getSQL(Dictionary dic, String templateSQL) throws TLException
	{
		return getSQL(dic.getType(),templateSQL);
	}
	
	/**
	 * 根据类型ID得到数据库语句
	 */
	private String getSQL(int typeID, String templateSQL) throws TLException
	{
		return getSQL(getType(typeID), templateSQL);
	}

	/**
	 * 根据类型得到数据库语句
	 */
	private String getSQL(DictionaryType type, String templateSQL) throws TLException
	{
		if (type == null)
			throw new TLException("No Category Type!");
		return getSQL(SysTableLibs.TB_SYS_DICTIONARY.getTableCode(), templateSQL);
	}

	/**
	 * 根据类型对应的表名得到数据库语句
	 */
	private String getSQL(String tbCode, String templateSQL) throws TLException
	{
		String sql;
		if (tbCode == null || "".equals(tbCode))
			sql = templateSQL;
		else
			sql = templateSQL.replaceAll(SysTableLibs.TB_SYS_DICTIONARY.getTableCode(), tbCode);
		return sql;		
	}
	
	//通用查询
	private Dictionary[] query(int typeID, String sql, Object[] params)
			throws TLException {
		DBSession db = Context.getDBSession();
		try {
			return query(typeID, sql, params, db);
		} finally {
			ResourceMgr.closeQuietly(db);
		}
	}
	//通用查询，带DBSession参数
	private Dictionary[] query(int typeID, String sql, Object[] params, DBSession db)
			throws TLException {
		List<Dictionary> list = new ArrayList<Dictionary>();
		
		IResultSet rs = null;
		try {
			rs = db.executeQuery(getSQL(typeID, sql), params);
			while (rs.next()) {
				list.add(readRS(rs));
			}
		} catch (SQLException e) {
			throw new TLException(e);
		} finally {
			ResourceMgr.closeQuietly(rs);
		}
		
		if (list.size() == 0) return null;
		
		return (Dictionary[]) list.toArray(new Dictionary[0]);
	}
	
	//单个Dic的通用查询
	private Dictionary querySingle(int typeID, String sql, Object[] params)
			throws TLException {
		Dictionary[] dics = query(typeID, sql, params);
		
		return (dics == null) ? null : dics[0];
		
	}
	//单个Dic的通用查询，带DBSession参数
	private Dictionary querySingle(int typeID, String sql, Object[] params, DBSession db)
			throws TLException {
		Dictionary[] dics = query(typeID, sql, params, db);
		
		return (dics == null) ? null : dics[0];
	}

}
