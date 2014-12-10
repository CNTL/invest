package com.tl.invest.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.tl.common.log.Log;
import com.tl.invest.constant.TableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;
import com.tl.kernel.context.TLException;

public class AddressManager {
	Log log = Context.getLog("invest");
	
	public void save(Address address){
		DAO d = new DAO();
	    try {
			if(address.getId()>0){
				d.update(address);
			}else {
				address.setId(TBID.getID(TableLibs.TB_USERADDRESS.getTableCode()));
				d.save(address);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void save(Address address,Session s){
		DAO d = new DAO();
	    try {
			if(address.getId()>0){
				d.update(address,s);
			}else {
				address.setId(TBID.getID(TableLibs.TB_USERADDRESS.getTableCode()));
				d.save(address,s);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void delete(long id,Session s){
		Address address = s == null ? get(id) : get(id, s);
		if(address == null){
			log.error("id="+id+"的收件地址不存在");
			return;
		}
		address.setDeleted(1);
		if(s == null){
			save(address);
		}else {
			save(address, s);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Address get(long id){
		Address address = null;
		DAO d = new DAO();
		List addresses = d.find("select a from com.tl.invest.user.Address as a where a.id = ?", new Object[]{id});
		if(!addresses.isEmpty()){
			address = (Address)addresses.get(0);
		}		
		return address;
	}
	
	@SuppressWarnings("rawtypes")
	public Address get(long id,Session s){
		Address address = null;
		DAO d = new DAO();
		List addresses = d.find("select a from com.tl.invest.user.Address as a where a.id = ?", new Object[]{id}, s);
		if(!addresses.isEmpty()){
			address = (Address)addresses.get(0);
		}
		return address;
	}
	
	@SuppressWarnings("rawtypes")
	public Address[] getAddresses(int userId,Session s) throws TLException{
		List<Address> list = new ArrayList<Address>();
		String hql = "select a from com.tl.invest.user.Address as a where a.userId=? and a.deleted=0 order by a.type desc,a.order,a.id";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List addresses = d.find(hql, new Object[]{userId}, s);
		if(!addresses.isEmpty()){
			for(int i=0;i<addresses.size();i++){
				list.add((Address)addresses.get(i));
			}
		}
		if (list.size() == 0) return null;
		
		return (Address[]) list.toArray(new Address[0]);
	}
}
