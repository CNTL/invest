package com.tl.invest.favorite;

import java.util.List;

import org.hibernate.Session;

import com.tl.common.log.Log;
import com.tl.invest.constant.TableLibs;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAO;
import com.tl.kernel.context.TBID;

public class FavoriteManager {
	Log log = Context.getLog("invest");
	
	public void save(Favorite favorite){
		DAO d = new DAO();
	    try {
			if(favorite.getId()>0){
				d.update(favorite);
			}else {
				favorite.setId(TBID.getID(TableLibs.TB_FAVORITE.getTableCode()));
				d.save(favorite);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	public void save(Favorite favorite,Session session){
		DAO d = new DAO();
	    try {
			if(favorite.getId()>0){
				d.update(favorite,session);
			}else {
				favorite.setId(TBID.getID(TableLibs.TB_FAVORITE.getTableCode()));
				d.save(favorite,session);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void delete(Favorite favorite) {
		DAO d = new DAO();
		try {
			if(favorite!=null)
				d.delete(favorite);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	public void delete(Favorite favorite,Session s) {
		DAO d = new DAO();
		try {
			if(favorite!=null)
				d.delete(favorite,s);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Favorite get(long id, Session s) {
		Favorite favorite = null;
		String sql = "select a from com.tl.invest.favorite.Favorite as a where a.id=?";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List favorites = d.find(sql, new Object[]{id}, s);
		if(!favorites.isEmpty()){
			favorite = (Favorite)favorites.get(0);
		}
		return favorite;
	}

	public Favorite get(long id) {
		return get(id, null);
	}
	
	@SuppressWarnings("rawtypes")
	public Favorite get(int userId,int libId,long itemId, Session s) {
		Favorite favorite = null;
		String sql = "select a from com.tl.invest.favorite.Favorite as a where a.userId=? and a.libId=? and a.itemId=?";
		DAO d = new DAO();
		if(s == null){
			s = d.getSession();
		}
		List favorites = d.find(sql, new Object[]{userId,libId,itemId}, s);
		if(!favorites.isEmpty()){
			favorite = (Favorite)favorites.get(0);
		}
		return favorite;
	}

	public Favorite get(int userId,int libId,long itemId) {
		return get(userId,libId,itemId, null);
	}
}
