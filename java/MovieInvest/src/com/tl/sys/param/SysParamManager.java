package com.tl.sys.param;

 
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tl.common.StringUtils;
import com.tl.kernel.context.TLException;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.DAOHelper;
import com.tl.sys.org.EUID;
 
 
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class SysParamManager {
	
	/** 增加系统参数
	 * @param sysParam
	 * @throws TLException
	 */
	public void  createSysParam(SysParam sysParam) throws TLException{
		
		if(sysParam==null){
			throw new TLException("SysParam is null");
		}
		
		if(StringUtils.isEmpty(sysParam.getKeyname())){
			throw new TLException("SysParam keyname is null");
		}
		
		if(StringUtils.isEmpty(sysParam.getKeyvalue())){
			throw new TLException("SysParam keyvalue is null");
		}
		
		DAO dao = new DAO();
	    Session s   = null;
	    Transaction t = null;
	    try
        {
	    	s = dao.getSession();
            t = dao.beginTransaction(s);
	    	
	    	if(sysParam.getId() <= 0){
	    		int ID = (int)EUID.getID("SysParamID");
	    		sysParam.setId(ID);
		        dao.save(sysParam,s);
	    	} else {
	    		dao.update(sysParam,s);
	    	}
	    	t.commit();
        }
        catch (Exception e)
        {
        	if(t != null ) t.rollback();
        	
        	if(e instanceof Exception ) throw (TLException)e;
        	
            throw new TLException("update sysparam error.", e);
        }
        finally
        {
        	dao.closeSession(s);
        }
	}
	
 
	/** 根据编码获得参数
	 * @param keyname
	 * @return
	 * @throws Exception
	 */
	public SysParam getSysParamByName(String keyname) throws Exception{
        
		List list = DAOHelper.find("select a from com.tl.sys.param.SysParam as a where a.keyname = :code", 
        		keyname, Hibernate.STRING);
        if(list.size() > 0)
            return (SysParam)list.get(0);
        else
            return null;
	}
	
	/** 删除系统参数
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception{
		DAOHelper.delete("delete from com.tl.sys.param.SysParam as a where a.id = " + id);
	}
	
	/**获得记录总数
	 * @return
	 * @throws Exception
	 */
	public int getCount() throws Exception{
		return DAOHelper.getCount("sysparam");
	}
	
	/**获得所有参数
	 * @return
	 * @throws Exception
	 */
	public List getAll() throws Exception{
		List list = DAOHelper.find("select a from com.tl.sys.param.SysParam as a");
        if(list.size() > 0)
            return list;
        else
            return null;
	}
	
	/** 显示分页数据
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @return
	 * @throws Exception
	 */
	public List<SysParam> querySysParamList(int iDisplayStart, int iDisplayLength) throws Exception{
		List list = DAOHelper.find("select a from com.tl.sys.param.SysParam as a order by id asc" , iDisplayStart, iDisplayLength);
        if(list.size() > 0)
            return list;
        else
            return null;
        
	}
}
