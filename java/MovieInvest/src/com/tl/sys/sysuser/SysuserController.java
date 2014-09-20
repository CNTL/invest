package com.tl.sys.sysuser;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tl.common.DateUtils;
import com.tl.common.ParamInitUtils;
import com.tl.invest.sys.user.SysUser;
import com.tl.kernel.web.BaseController;

/** 
 * @created 2014年8月24日 下午7:10:34 
 * @author  leijj
 * 类说明 ： 
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class SysuserController extends BaseController {
	private SysuserManager sysuserManager = null;
	
	public SysuserManager getSysuserManager() {
		return sysuserManager;
	}

	public void setSysuserManager(SysuserManager sysuserManager) {
		this.sysuserManager = sysuserManager;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("list".equals(action)){
			String json = querySysusers(request, response);
			output(json, response);
		} else if("edit".equals(action)){
			SysUser sysuser = setSysuser(request);
			sysuserManager.create(sysuser);
			String viewName = "common/After";
			model.put("callMode", "0");
			model.put("needRefresh", "true");
			model.put("@VIEWNAME@", viewName);
			//userManager.create(user);
		} else if("del".equals(action)){
			sysuserManager.delete(ParamInitUtils.getInt(request.getParameter("id")));
			output("true", response);
		}
	}
	private SysUser setSysuser(HttpServletRequest request){
		SysUser sysuser = new SysUser();
		sysuser.setId(ParamInitUtils.getInt(request.getParameter("id")));
		sysuser.setUsername(ParamInitUtils.getString(request.getParameter("username")));
		sysuser.setCode(ParamInitUtils.getString(request.getParameter("code")));
		sysuser.setPwd(ParamInitUtils.getString(request.getParameter("pwd")));
		sysuser.setCreatetime(DateUtils.getTimestamp());
		return sysuser;
	}
	
	public String querySysusers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataTableParam param = DataTableUtil.getParam(request);
		SysUser sysuser = setSysuser(request);
		List users = sysuserManager.querySysusersStr(param.getiDisplayStart(), param.getiDisplayLength(), sysuser);
		int count = sysuserManager.getCount();
		String json = tojson(users, count ,param.getsEcho());
		return json;
	}   
	public String tojson(List list, int count, String sEcho)     {  
			String json = null; // 返回的		json数据    
			String aaData=JSONArray.fromObject(list).toString();     
			json =  "{\"sEcho\":"+sEcho+",\"iTotalRecords\":"+count+",\"iTotalDisplayRecords\":"+count+",\"data\":"+aaData+"}";      
			return json;     
	}
 
}