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
	 * ȡһ���ֵ����������
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
		//������
		Dictionary cat = getBareDic(typeID, dicID, db);
		return cat;
	}
	
	private static String m_SelectAllSQL = 	"select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()
			+" where dic_typeid=? and dic_valid=1 order by dic_level, dic_order";
	/**
	 * ȡĳ�ֵ����͵������ֻȡ������
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
	 * ȡĳ�ֵ����ֱ������
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
	 * �Ѷ�������ֱ������һ����ȡ��
	 * �������´����޸�
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
		if (!deleted) {// ����ʾɾ����
			sql = m_SelectMultiSubSQL.replaceAll("@PARENTID@", DictionaryHelper.join(dicID, ','));
		} else {// ��ʾȫ����
			sql = m_SelectALLMultiSubSQL.replaceAll("@PARENTID@", DictionaryHelper.join(dicID, ','));
		}
		
		return query(dicType.getId(), sql, new Object[] { dicType.getId() }, db);
	}
	
	private static String m_SelectSQL2 = "select * from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_id=?";
	
	/**
	 * ȡһ���ֵ��������(ɾ����δɾ����)
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
			//�õ�������
			dicArray = getBareDics(typeID, db);
		}finally{
			try{db.close();}catch(Exception e1){e1.printStackTrace();}
		}
		return dicArray;
	}

	@Override
	public Dictionary[] getChildrenDics(int typeID, int dicID)
			throws TLException {
		//������ɾ������
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
			//�õ�������
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
			if(type.getIsSys()==sys || type.getValid()==1){
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
	 * ����ID����������
	 */
	void create(DictionaryType type, int id) throws TLException
	{
		try
		{
			DAO dao = new DAO();
			type.setId(id);
			//���͵�˳�򣬴���ʱʹ��ID��ͬ
			//��Ϊ���IDһ�������е����������ģ����Կ��Ա�֤�½��������������
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
		//�µ�ID
		int id = (int)TBID.getID(SysTableLibs.TB_SYS_DICTIONARY.getTableCode());
		dic.setId(id);
		dic.setLastModified(new Date());
		dic.setOrder(id);
		DBSession db = Context.getDBSession();
		try{
			db.beginTransaction();
			//1)����Level = parentLevel + 1
			if(dic.getPid() == 0)
				dic.setLevel(0);
			else{
				Dictionary parent = this.getDic(dic.getType(),dic.getPid(),db);
				dic.setLevel(parent.getLevel()+1);
			}
			
			//2)save
			String sql = getSQL(dic, m_InsertSQL);
			db.executeUpdate(sql, getValueArray(dic), getFieldTypes());
			
			//3)���ü�������			
			upateCascade(dic,db);
			
			//4)����и��ڵ����childcount+1
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
		//1.ȡ���ɵ��ֵ�����Ƚ�
		Dictionary oldDic = getDic(dic.getType(), dic.getId());
		//����DBSession
		DBSession db = Context.getDBSession();
		try
		{
			db.beginTransaction();
			DictionaryType type = getType(dic.getType());
			//2.�޸ı��ֵ���
			//��oldDic���б��棬����ǰ�Ȱѿɸı��ֵ���ϡ�ע�ⲻ�ܸı�Level
			db.executeUpdate(getSQL(type, m_UpdateSQL), getValueArray(dic), getFieldTypes());
			
			//3.��������Ƿ����仯�����б仯����Ҫ�޸���������
			if (!dic.getName().equals(oldDic.getName()))
				updateChildCascadeName(dic, db);
			
			//5.�����ύ
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
		
		//Ŀ��IDΪ0����ʾ�ŵ�����
		if (destDicID == 0) {
			dic = getDic(typeID, srcDicID);
			//2.��¼���
			dLevel = 0 - dic.getLevel();
			//�����ƶ���Ĳ�Ρ����򡢸�ID
			dic.setLevel(0);//���Ϊ�������һ��
			srcParentID = dic.getPid();
		}
		else{
			//1.ȡ����������Ƚ�
			Dictionary parentDic = getDic(typeID, destDicID);
			dic = getDic(typeID, srcDicID);
			srcParentID = dic.getPid();
			
			//2.��¼���
			dLevel = parentDic.getLevel() - dic.getLevel() + 1;
	
			//�����ƶ���Ĳ�Ρ����򡢸�ID
			dic.setLevel(parentDic.getLevel() + 1);//���Ϊ�������һ��
		}
		
		dic.setOrder(nextDispOrder(dic.getType(),destDicID));
		dic.setPid(destDicID);//���ø���

		DictionaryType dicType = getType(typeID);

		//����DBSession
		DBSession db = Context.getDBSession();
		try{
			db.beginTransaction();
			//3.�޸ı���
			db.executeUpdate(getSQL(dicType, m_UpdateSQL), getValueArray(dic), getFieldTypes());
			
			//4.�������/����Ƿ����仯�����б仯����Ҫ�޸���������			
			//�����ƶ�����¼���
			updateChildCascade(dic,destDicID, dLevel,db);
			
			//5.�ƶ�һ����ʱ��Դ����childcount-1��Ŀ�길��childcount+1			
			updateChildCount(typeID,srcParentID,-1,db);
			updateChildCount(typeID,destDicID,1,db);
			
			//6.�����ύ
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

		//ȡ����
		DictionaryType dicType = getType(dic.getType());

		//׼��sql��䣬�Լ��������Ͳ�������
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
		//����ɾ�����ֵ���
		return getChildrenDics(typeID, dicID, true);
	}
	
	private Dictionary[] getChildrenDics(int typeID, int dicID,boolean deleted) throws TLException
	{
		//ȡ������		
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
			//1) ȡ������������(����������)
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
	 * ִ���ֵ����ɾ���ͻָ�����
	 * ��Ϊ��ɾ���ͻָ�ʱ����Ҫͬʱ�޸�������ԱȽϸ��ӡ�
	 * @param typeID
	 * @param dicID
	 * @param delete
	 * @throws E5Exception
	 */
	private void catDelete(int typeID, int dicID, int delete) throws TLException
	{
		//����DBSession
		DBSession db = Context.getDBSession();
		try
		{
			DictionaryType type = getType(typeID);

			//1.ȡ���ֵ�����Ϣ����Ҫ�Ǽ���ID����
			Dictionary dic = getBareALLDic(typeID,dicID,db);	
			//����ǰȡҪ�������б�
			Dictionary[] dics = null;
			if(delete != 1)
				dics = getAllChildrenDicsByCascadeID(typeID,dic.getCascadeId(),true,db);
			
			//2.���ݼ���ID��ƴ���޸������DELETEFLAG��SQL			
			String strSQL = getSQL(type, m_DeleteSQL);		
			StringBuffer cascadeCond = new StringBuffer();
			cascadeCond = cascadeCond.append(dic.getCascadeId()).append(Dictionary.separator).append("%");

			db.beginTransaction();//����

			//ɾ��(��ָ�)����:���ݼ���ID			
			db.executeUpdate(strSQL, new Object[]{new Integer(delete==1?0:1), cascadeCond.toString() });
			//ɾ������ָ�������
			strSQL = getSQL(type, m_DeleteSelfSQL);		
			db.executeUpdate(strSQL, new Object[]{new Integer(delete==1?0:1), dicID});
			
			//3.ά��child_count�ֶ�
			//���㸸
			if(dic.getPid()!=0)
				calculateChildCount(typeID, dic.getPid(), db);

			//�����Լ�
			calculateChildCount(typeID, dicID, db);
			//����ǻָ���������������
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
	 * ׼�������޸ĵ�SQL��䣬�Լ�SQL����еĲ����Ͳ�������
	 * @param dicType
	 * @param dic
	 * @param fields ���޸Ĵ��ݵ����Ա�ʶ
	 * @param params SQL����еĲ���
	 * @param types SQL����еĲ�������
	 * @return
	 */
	private String prepaire(DictionaryType dicType, Dictionary dic, int[] fields, 
			Object[] params, int[] types)
	{
		String strTableName = SysTableLibs.TB_SYS_DICTIONARY.getTableCode();
		if (strTableName == null)
			strTableName =SysTableLibs.TB_SYS_DICTIONARY.getTableCode();
		//ƴ�޸����
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
	 * �޸���������Ĳ�Σ��ó���ķ�ʽ�ݹ�����޸�
	 * ע��÷����ж�ÿһ��ֻ��һ�β��ң�������ÿ�����һ�����
	 * ����αȽ���ʱ�����ݵ�int[]���ܱȽϴ�
	 */
	private void updateTransfer(DictionaryType dicType, String strSQL, int[] dicID,
			Object[] params, int[] types, DBSession db) 
	throws SQLException, TLException
	{
		//�޸ĵ�ǰ���
		String sql = strSQL.replaceAll("@PARENTID@", DictionaryHelper.join(dicID, ','));
		db.executeUpdate(sql, params, types);
		
		//����������,��û�������ˣ��򷵻�
		Dictionary[] dicArr = getBareSubDics(dicType, dicID, db,false);
		
		if (dicArr == null) return;

		//������ID��ȡ����
		int[] childrenID = new int[dicArr.length];
		for (int i = 0; i < dicArr.length; i++)
			childrenID[i] = dicArr[i].getId();
		
		//�ݹ飺�޸���������
		updateTransfer(dicType, strSQL, childrenID, params, types, db);
	}
	
	/**
	 * �ƶ��ڵ�ʱ���޸ļ�������(�Լ��������¼�)
	 * @param dicType ����
	 * @param dicID   ��ǰID
	 * @param destDicID Ŀ�� =0 ����>1����ID
	 * ��������ƻ��߲�η����˱仯������Ҫ�仯�ӵļ������� 
	 */
	private void updateChildCascade(Dictionary dic, int destDicID,int dlevel,DBSession db)
		throws TLException
	{	
		int typeID = dic.getType();
		//int dicID   = dic.getId();
		
		//1) ���õ�ǰ��ļ������ơ�����ID
		//Category cat = getCat(catType,catID);
		dic.setPid(destDicID);
		
		String   oname= dic.getCascadeName();  //��ǰ����ʷ��������
		String   oid  = dic.getCascadeId();    //��ǰ����ʷ����ID
		
		upateCascade(dic,db);
		
		String   name = dic.getCascadeName();   //��ǰ��ļ�������
		String   id   = dic.getCascadeId();   //��ǰ����ļ���ID
		
		//2) ȡ����������������ǵ����ƺ�ID
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
			
		    //3) ��������� ��������		
			updateCascade(typeID,dicIDs,cascadeName,cascadeID,dlevel,db);			
		}
		//4) ���ø��±���(�����ӵ�����¸��±���)
		//CatExtCascadeHelper.updateExtChildCascade(catType,catID,destCatID,db.getConnection());
	}
	
	private static String NEXT_DISPORDEDR = "select max(dic_order) disp_order from "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" where dic_pid=?"; 
	/**
	 * ȡ��order 
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
	 * ���ݼ���ID������������
	 * @param typeID
	 * @param cascadeID Ϊnull��ʾ��ѯȫ���ڵ�
	 * @param deleted true/��ѯ������ɾ���Ľڵ�,false/��ѯ������ɾ���Ľڵ�
	 * @return Dictionary[] ��������ڵ�
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
	 * �޸�����ʱ���޸������ӽڵ㼶������
	 * @param dic
	 * @param db
	 * @throws TLException
	 */
	private void updateChildCascadeName(Dictionary dic,DBSession db)
	throws TLException
	{	
		//��ǰ����ʷ��������
		String   oname    = dic.getCascadeName();  
		String ocascadeID = dic.getCascadeId();
		
		upateCascade(dic,db);
		
		//2) ȡ����������������ǵ����ƺ�ID
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
			
		    //3) ��������� ��������		
			updateCascade(dic.getType(),dicIDs,cascadeName,null,0,db);			
		}
	
	}
	
	/**
	 * ����һ���ֵ���ļ�������
	 * @param dic
	 * @throws E5Exception
	 */
	private void upateCascade(Dictionary dic,DBSession db) throws TLException
	{
		//1) ���õ�ǰ��ļ������ơ�����ID
		String   name = "";   //��ǰ��ļ�������
		String   id   = "";   //��ǰ��ļ���ID
		
		
		//��
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
		
		//���µ�ǰ��,������level��level��move�������¹���,level=0��ʾ������
		updateCascade(dic.getType(),new int[]{dic.getId()},new String[]{name},new String[]{id},0,db);
	}
	private static String UPDATE_CASCADE_ALL_SQL  = "UPDATE "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" set dic_CascadeName=?,dic_CascadeID=?,dic_level=dic_level+? WHERE dic_id=?";
	private static String UPDATE_CASCADE_NAME_SQL = "UPDATE "+SysTableLibs.TB_SYS_DICTIONARY.getTableCode()+" set dic_CascadeName=? WHERE dic_id=?";
	/**
	 * ���������ֵ���ļ������ơ�����ID,������Ϊ���úõĶ�Ӧ��ϵ����ȡֵ��
	 * @param typeID 
	 * @param dicID
	 * @param cascadeName
	 * @param cascadeID
	 * @param level - ���
	 */
	private void updateCascade(int typeID,int dicID[],String cascadeName[],String cascadeID[],int level,DBSession db)
		throws TLException
	{
		Connection conn  = db.getConnection();
		PreparedStatement pstmt = null;
		try
		{
			//֧��ֻ����cascadeName
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
	 * �����ֵ���ڵ㺢�ӽڵ�����
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
	
	//���¼���child_count�ֶ�
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
				//ע���������͵�ת��
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
	 * �����ֵ���õ����ݿ����
	 */
	private String getSQL(Dictionary dic, String templateSQL) throws TLException
	{
		return getSQL(dic.getType(),templateSQL);
	}
	
	/**
	 * ��������ID�õ����ݿ����
	 */
	private String getSQL(int typeID, String templateSQL) throws TLException
	{
		return getSQL(getType(typeID), templateSQL);
	}

	/**
	 * �������͵õ����ݿ����
	 */
	private String getSQL(DictionaryType type, String templateSQL) throws TLException
	{
		if (type == null)
			throw new TLException("No Category Type!");
		return getSQL(SysTableLibs.TB_SYS_DICTIONARY.getTableCode(), templateSQL);
	}

	/**
	 * �������Ͷ�Ӧ�ı����õ����ݿ����
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
	
	//ͨ�ò�ѯ
	private Dictionary[] query(int typeID, String sql, Object[] params)
			throws TLException {
		DBSession db = Context.getDBSession();
		try {
			return query(typeID, sql, params, db);
		} finally {
			ResourceMgr.closeQuietly(db);
		}
	}
	//ͨ�ò�ѯ����DBSession����
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
	
	//����Dic��ͨ�ò�ѯ
	private Dictionary querySingle(int typeID, String sql, Object[] params)
			throws TLException {
		Dictionary[] dics = query(typeID, sql, params);
		
		return (dics == null) ? null : dics[0];
		
	}
	//����Dic��ͨ�ò�ѯ����DBSession����
	private Dictionary querySingle(int typeID, String sql, Object[] params, DBSession db)
			throws TLException {
		Dictionary[] dics = query(typeID, sql, params, db);
		
		return (dics == null) ? null : dics[0];
	}

}
