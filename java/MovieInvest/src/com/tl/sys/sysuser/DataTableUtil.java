package com.tl.sys.sysuser;

import javax.servlet.http.HttpServletRequest;

import com.tl.common.ParamInitUtils;

/** 
 * @created 2014��8��26�� ����9:14:29 
 * @author  leijj
 * ��˵�� �� 
 */
public class DataTableUtil {
	public static DataTableParam getParam(HttpServletRequest request){
		DataTableParam param = new DataTableParam();
		request.getParameter("username");
		param.setsEcho(ParamInitUtils.getString(request.getParameter("sEcho")));
		param.setsSearch(ParamInitUtils.getString(request.getParameter("sSearch")));
		param.setiDisplayLength(ParamInitUtils.getInt(request.getParameter("iDisplayLength")));
		param.setiDisplayStart(ParamInitUtils.getInt(request.getParameter("iDisplayStart")));
		param.setiColumns(ParamInitUtils.getInt(request.getParameter("iColumns")));
		param.setiSortingCols(ParamInitUtils.getInt(request.getParameter("iSortingCols")));
		param.setsColumns(ParamInitUtils.getString(request.getParameter("sColumns")));
		return param;
	}
}
