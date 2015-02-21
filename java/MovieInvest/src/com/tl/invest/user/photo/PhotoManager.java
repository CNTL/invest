package com.tl.invest.user.photo;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.kernel.constant.SysTableLibs;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.context.TBID;

/** 
 * @created 2014年12月12日 下午6:56:11 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({"deprecation", "unchecked"})
public class PhotoManager {
	public List<UserPhoto> getPhotos(int groupID) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.photo.UserPhoto as a");
		querySql.append(" where a.groupId=" + groupID);
		querySql.append(" order by a.createTime desc");
		//List list = DAOHelper.find(querySql.toString() , start, length);
		List<UserPhoto> list = DAOHelper.find(querySql.toString());
        return list;
	}
	
	public List<UserPhotogroup> getPhotoGroups(int userID) throws Exception{
		StringBuilder querySql = new StringBuilder("select a from com.tl.invest.user.photo.UserPhotogroup as a");
		querySql.append(" where a.userId=" + userID);
		querySql.append(" order by a.createTime desc");
		//List list = DAOHelper.find(querySql.toString() , start, length);
		List<UserPhotogroup> list = DAOHelper.find(querySql.toString());
        return list;
	}
	
	@SuppressWarnings("rawtypes")
	public UserPhotogroup getGroupInfo(int id) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.photo.UserPhotogroup as a where a.id = "+String.valueOf(id));
		if(list.size()==0){
			return null;
		}
		else{
			return (UserPhotogroup) list.get(0);
		}
        
	}
	/** 
	* @author  leijj 
	* 功能： 创建图册
	* @param photogroup
	* @throws Exception 
	*/ 
	public int savePhotoGroup(UserPhotogroup photogroup) throws Exception{
		int id = 0;
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(photogroup.getId() <= 0){
	    		id = (int)TBID.getID(SysTableLibs.TB_PHOTOGROUP.getTableCode());
	    		photogroup.setId(id);
		        dao.save(photogroup,s);
	    	} else {
	    		dao.update(photogroup,s);
	    		id = photogroup.getId();
	    	}
	    	t.commit();
        }
        catch (Exception e)
        {
        	if(t != null ) t.rollback();
        	
        	if(e instanceof Exception ) throw (Exception)e;
        	
            throw new Exception("update UserPhotogroup error.", e);
        }
        finally
        {
        	dao.closeSession(s);
        }
	    return id;
	}
	/** 
	* @author  leijj 
	* 功能： 删除图册相关图片
	* @param id
	* @throws Exception 
	*/ 
	public void delPhotoByGroup(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.photo.UserPhoto as photogroup where photogroup.groupId = :groupId", 
        		   new Integer(id), Hibernate.INTEGER);
	}
	/** 
	* @author  leijj 
	* 功能： 删除图册
	* @param id
	* @throws Exception 
	*/ 
	public void delPhotoGroup(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.photo.UserPhotogroup as photogroup where photogroup.id = :id", 
        		   new Integer(id), Hibernate.INTEGER);
	}
	
	/** 
	* @author  leijj 
	* 功能： 新增照片
	* @param photo
	* @throws Exception 
	*/ 
	public int savePhoto(UserPhoto photo) throws Exception{
		int id = 0;
	    DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	if(photo.getId() <= 0){
	    		id = (int)TBID.getID(SysTableLibs.TB_USERPHOTO.getTableCode());
	    		photo.setId(id);
		        dao.save(photo,s);
	    	} else {
	    		id = photo.getId();
	    		dao.update(photo,s);
	    	}
	    	t.commit();
        } catch (Exception e) {
        	if(t != null ) t.rollback();
        	
        	if(e instanceof Exception) throw (Exception)e;
        	
            throw new Exception("update UserPhotogroup error.", e);
        } finally {
        	dao.closeSession(s);
        }
	    return id;
	}
	
	/** 
	* @author  leijj 
	* 功能： 删除图册相关图片
	* @param id
	* @throws Exception 
	*/ 
	public void delPhotoById(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.invest.user.photo.UserPhoto as a where a.id = :id", 
        		   new Integer(id), Hibernate.INTEGER);
	}
	@SuppressWarnings("rawtypes")
	public UserPhoto getPhotoInfo(int id) throws Exception{
		List list = DAOHelper.find("select a from com.tl.invest.user.photo.UserPhoto as a where a.id = :id", 
        		new Integer(id), Hibernate.INTEGER);
		if(list.size()==0){
			return null;
		}
		else{
			return (UserPhoto) list.get(0);
		}
        
	}
}