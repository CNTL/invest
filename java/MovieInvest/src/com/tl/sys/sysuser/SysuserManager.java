package com.tl.sys.sysuser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tl.common.DateUtils;
import com.tl.common.UserEncrypt;
import com.tl.db.IResultSet;
import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;
import com.tl.sys.org.EUID;

/** 
 * @author wang.yq
 * @create 2014-9-18 19:13:09
 */
 
@SuppressWarnings({"rawtypes", "unchecked", "unused", "deprecation"})
public class SysuserManager {
	/**
	 * ����û�
	 * 
	 * @param user  Ҫ�������û�����
	 */
	public void create(Sysuser sysuser) throws Exception{
	    if(sysuser.getCode() == null || sysuser.getCode().trim().equals(""))
	        throw new Exception("Sysuser Code is Null.");
	    if(sysuser.getUsername() == null || sysuser.getUsername().trim().equals(""))
	        throw new Exception("Sysuser Name is Null.");
	    if(sysuser.getId() <= 0 && getSysuserByCode(sysuser.getCode()) != null)
	        throw new Exception("The Same Sysuser Code exist.");
	     
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	//����password,���ݿ��д洢��������
            sysuser.setPwd(UserEncrypt.getInstance().encrypt(sysuser.getPwd()));
	    	if(sysuser.getId() <= 0){
	    		int userID = (int)TBID.getID(SysTableLibs.TB_SYS_USER.getTableCode());
		        sysuser.setId(userID);
		        dao.save(sysuser,s);
	    	} else {
	    		dao.update(sysuser,s);
	    	}
	    	t.commit();
        }
        catch (Exception e)
        {
        	if(t != null ) t.rollback();
        	
        	if(e instanceof Exception ) throw (Exception)e;
        	
            throw new Exception("update user error.", e);
        }
        finally
        {
        	dao.closeSession(s);
        }
	}
	/**
	 * ����û�����ָ�����û�
	 * 
	 * @param userCode    �û�����
	 */
	public Sysuser getSysuserByCode(String code) throws Exception{
        List list = DAOHelper.find("select a from com.tl.sys.sysuser.Sysuser as a where a.code = :code and  deleted=0", 
        		code, Hibernate.STRING);
        if(list.size() > 0)
            return decrypt(list.get(0));
        else
            return null;
	}
	public void delete(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.sys.sysuser.Sysuser as a where a.id = " + id);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡϵͳ�û���
	* @return
	* @throws Exception 
	*/ 
	public int getCount() throws Exception{
		return DAOHelper.getCount(SysTableLibs.TB_SYS_USER.getTableCode());
	}
	/* ��ȡ���е��û�����
	 * @see com.tl.sys.org.UserManager#getAll()
	 */
	public List getAll() throws Exception{
		List list = DAOHelper.find("select a from com.tl.sys.sysuser.Sysuser as a");
        if(list.size() > 0)
            return list;
        else
            return null;
	}
	public List<Sysuser> querySysusersStr(int iDisplayStart, int iDisplayLength, Sysuser query) throws Exception{
		List<Sysuser> sysusers = new ArrayList<Sysuser>();
		List list = querySysusers(iDisplayStart, iDisplayLength, query);
        if(list.size() > 0){
        	for(int i = 0; i < list.size(); i++){
        		Sysuser sysuser = (Sysuser)list.get(i);
        		sysuser.setPwd(UserEncrypt.getInstance().decrypt(sysuser.getPwd()));
        		sysuser.setCreateTimeStr(DateUtils.format(sysuser.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
        		sysusers.add(sysuser);
        	}
        	 return sysusers;	
        } else
            return null;
        
	}
	public List<Sysuser> querySysusers(int iDisplayStart, int iDisplayLength, Sysuser query) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.sys.sysuser.Sysuser as a");
		if(query.getUsername() != null && !"".equals(query.getUsername())
				&& query.getCode() != null && !"".equals(query.getCode())){
			querySql.append(" where a.username like '%" + query.getUsername())
					.append("%' and a.code like '%" + query.getCode()).append("%'");
		} else if(query.getUsername() != null && !"".equals(query.getUsername())
				&& (query.getCode() == null || "".equals(query.getCode()))){
			querySql.append(" where a.username like '%" + query.getUsername()).append("%'");
		} else if((query.getUsername() == null || "".equals(query.getUsername()))
				&& query.getCode() != null && !"".equals(query.getCode())){
			querySql.append(" where a.code like '%" + query.getCode()).append("%'");
		}
		querySql.append(" order by createTime desc");
		List list = DAOHelper.find( querySql.toString(), iDisplayStart, iDisplayLength);
        if(list.size() > 0)
            return list;
        else
            return null;
        
	}
	
	/** ϵͳ�û���¼
	 * @return Boolean
	 */
	public Sysuser login(String code,String pwd) {
		Sysuser sysuser = null;
		 
		Object[] paramObjects = {code,UserEncrypt.getInstance().encrypt(pwd)};
		try {
			List list = DAOHelper.find("select a from com.tl.sys.sysuser.Sysuser as a where a.code=? and a.pwd=? and deleted=0",
					paramObjects);
			if(!list.isEmpty()){
				sysuser = (Sysuser)list.get(0);
			}
		} catch (Exception e) {
			 
		}
		return sysuser;
	}
	/**
	 * ���ݲ�ѯ�����װuser
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Sysuser setSysuser(IResultSet rs) throws SQLException{
		
		Sysuser sysuser = new Sysuser();
		sysuser.setId(rs.getInt("id"));
		sysuser.setUsername(rs.getString("username"));
		sysuser.setCode(rs.getString("code"));
		sysuser.setPwd(rs.getString("pwd"));
		sysuser.setEmail(rs.getString("email"));
		sysuser.setMobile(rs.getString("mobile"));
		sysuser.setGroupid(rs.getInt("groupid")); 
		sysuser.setDeleted(rs.getInt("deleted"));
		sysuser.setCreatetime(rs.getDate("deleted"));
		sysuser.setCreateTimeStr(DateUtils.format(rs.getTimestamp("createTime"), "yyyy-MM-dd HH:mm:ss"));
		return sysuser;
	}
	
	
	/** �����û�����
	 * @param _user
	 * @return
	 */
	private Sysuser decrypt(Object _user){
		Sysuser user = (Sysuser)_user;
		if(UserEncrypt.getInstance().isDecryptText(user.getPwd()))
			user.setPwd(UserEncrypt.getInstance().decrypt(user.getPwd()));
		return user;
	}
}
