package com.tl.sys.param;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.tl.kernel.web.BaseController;
import com.tl.sys.sysuser.DataTableParam;
import com.tl.sys.sysuser.DataTableUtil;
import com.tl.sys.sysuser.SysuserManager;

/** 系统内参数
 * @author wang.yq
 * @create 2014-9-1
 */
@SuppressWarnings({"rawtypes","unused","unchecked"})
public class SysParamController extends BaseController{

	private SysParamManager sysParamManager = null;
	
	public SysParamManager getSysParamManager() {
		return sysParamManager;
	}

	public void setSysParamManager(SysParamManager sysParamManager) {
		this.sysParamManager = sysParamManager;
	}
	
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		 
		String action = request.getParameter("action");
		
		if(action.equals("create")){
			
			createSysParam(request,response);
		}
		else if(action.equals("delete")){
			
			delete(request,response);
			
		}else if(action.equals("list")){
			
			list(request,response);
		}
		else if(action.equals("query")){
			query(request,response);
		}
		else if(action.equals("page")){
			page(request,response);
		}
	}
	
	/** 创建系统参数
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void createSysParam(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		String ret = "true";
		try {
			SysParam sysParam = new SysParam(
					get(request, "keyname"), 
					get(request, "keyvalue"), 
					get(request, "detail"));
			
			sysParamManager.createSysParam(sysParam);
		} catch (Exception e) {
			log.error(e.getMessage());
			ret = "false";
		}
		
		output(ret, response);
	}
	
	/** 删除参数
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		String ret = "true";
		try {
			sysParamManager.delete(getInt(request, "id"));
		} catch (Exception e) {
			log.error(e.getMessage());
			ret = "false";
		}
		output(ret, response);
	}
	
	/** 查询
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void list(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		try {
			 
			List<SysParam> list = sysParamManager.getAll();
			
			output(JSONArray.fromObject(list).toString(), response);  
			
		} catch (Exception e) {
			log.error(e.getMessage());
			output("", response); 
		}
	}
	
	/** 查询参数
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void query(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		try {
			
			SysParam sysParam =  sysParamManager.getSysParamByName(get(request, "keyname"));
			JSONObject jsonArray = JSONObject.fromObject(sysParam);
			output(jsonArray.toString(), response);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			output("", response); 
		}
		
	}
	
	/** 分页数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void page(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		
		DataTableParam param = DataTableUtil.getParam(request);
		
		List<SysParam> list = sysParamManager.querySysParamList(param.getiDisplayStart(), param.getiDisplayLength());
		int count = sysParamManager.getCount();
		String json = tojson(list, count ,param.getsEcho());
		output(json, response);
	}
	
	/** 组装datatable数据格式
	 * @param list
	 * @param count
	 * @param sEcho
	 * @return
	 */
	private String tojson(List list, int count, String sEcho)     {  
		String json = null; // 返回的		json数据    
		String sysParamList=JSONArray.fromObject(list).toString();     
		json =  "{\"sEcho\":"+sEcho+",\"iTotalRecords\":"+count+",\"iTotalDisplayRecords\":"+count+",\"aaData\":"+sysParamList+"}";      
		return json;     
	}

}
