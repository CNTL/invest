package com.tl.invest.user.recruit;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.tl.common.DateJsonValueProcessor;
import com.tl.common.DateUtils;
import com.tl.common.JsonDateValueProcessor;
import com.tl.common.ParamInitUtils;
import com.tl.common.WebUtil;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��11��14�� ����2:02:54 
 * @author  leijj
 * ��˵�� �� ��Ƹ������
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RecruitController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("datas".equals(action)){
			initDatas(response);
		} else if("save".equals(action)){//������Ƹ��Ϣ
			String json = save(request, response);
			output(json, response);
		} else if("detail".equals(action)){//��ȡ��Ƹ����
			int id = ParamInitUtils.getInt(request.getParameter("id"));
			model.put("id", id);
			model.put("mainType", ParamInitUtils.getInt(request.getParameter("mainType")));
			response.sendRedirect(WebUtil.getRoot(request) + "recruit/DetailMain.do?a=detail&id=" + id);
		} else if("edit".equals(action)){//��ȡ�޸���Ϣ
			int id = ParamInitUtils.getInt(request.getParameter("id"));
			model.put("id", id);
			model.put("mainType", ParamInitUtils.getInt(request.getParameter("mainType")));
			response.sendRedirect(WebUtil.getRoot(request) + "recruit/Edit.do?a=detail&id=" + id);
		}else if("checkRecruit".equals(action)){
			checkRecruit(request,response);
		}else if("orderRecruit".equals(action)){
			orderRecruit(request,response);
		}else if("deleteRecruit".equals(action)){
			deleteRecruit(request,response);
		}else if("getRecruit".equals(action)){
			getRecruit(request,response);
		}
	}
	
	/** ����ְλ����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void checkRecruit(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Integer id = getInt(request, "id",0);
		Integer ispub = getInt(request, "ispub",0);
		try{
			UserRecruit recruit = new UserRecruit();
			recruit = recruitManager.getRecruitByID(id);
			recruit.setIsPub(ispub);
			recruitManager.save(recruit);
			output("ok", response);
		}catch(Exception e){
			output("error", response);
		}
		
	}
	
	/** ����ְλ����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void orderRecruit(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Integer id = getInt(request, "id",0);
		Integer order = getInt(request, "order",0);
		try{
			UserRecruit recruit = new UserRecruit();
			recruit = recruitManager.getRecruitByID(id);
			recruit.setJobOrder(order);
			recruitManager.save(recruit);
			output("ok", response);
		}catch(Exception e){
			output("error", response);
		}
		
	}
	
	/** ɾ��ְλ
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void deleteRecruit(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Integer id = getInt(request, "id",0);
		Integer order = getInt(request, "order",0);
		try{
			UserRecruit recruit = new UserRecruit();
			recruit = recruitManager.getRecruitByID(id);
			recruit.setDeleted(1);
			recruitManager.save(recruit);
			output("ok", response);
		}catch(Exception e){
			output("error", response);
		}
		
	}
	
	/** ���һ��ְλ
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getRecruit(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Integer id = getInt(request, "id",0);
		try {
			UserRecruit recruit = new UserRecruit();
			recruit = recruitManager.getRecruitByID(id);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
			JSONObject jsonArray = JSONObject.fromObject(recruit,jsonConfig);  
			output(jsonArray.toString(), response);
		} catch (Exception e) {
			output("error", response);
		}
		
		
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ������Ƹ��Ϣ
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int id = getInt(request, "id");
		UserRecruit recruit = new UserRecruit();
		if(id > 0){
			recruit = recruitManager.getRecruitByID(id);
		}
		int firstType = getInt(request, "firstType");
		int secondType = getInt(request, "secondType");
		String province = get(request, "province","");
		String city = get(request, "city","");

		String typeName = "";
		Dictionary dic = null;
		if(secondType > 0){
			dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), secondType);
			typeName = dic.getCascadeName();
		} else if(firstType > 0){
			dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), firstType);
			typeName = dic.getCascadeName();
		}
		
		recruit.setUserId(user.getId());
		recruit.setUserName(user.getName());
		//jobName jobPictrue salary working eduReq isFulltime jobAttract
		recruit.setJobName(ParamInitUtils.getString(request.getParameter("jobName")));
		recruit.setJobPictrue(ParamInitUtils.getString(request.getParameter("jobPictrue")));
		recruit.setSalary(ParamInitUtils.getString(request.getParameter("salary")));
		recruit.setDays(ParamInitUtils.getString(request.getParameter("days")));
		recruit.setWorking(ParamInitUtils.getString(request.getParameter("working")));
		recruit.setEduReq(ParamInitUtils.getString(request.getParameter("eduReq")));
		recruit.setIsFulltime(ParamInitUtils.getInt(request.getParameter("isFulltime")));
		recruit.setJobAttract(ParamInitUtils.getString(request.getParameter("jobAttract")));
		recruit.setContent(ParamInitUtils.getString(request.getParameter("content")));
		recruit.setLinkman(ParamInitUtils.getString(request.getParameter("linkman")));
		recruit.setLinkPhone(ParamInitUtils.getString(request.getParameter("linkPhone")));
		recruit.setLinkEmail(ParamInitUtils.getString(request.getParameter("linkEmail")));
		recruit.setFirstType(firstType);
		recruit.setSecondType(secondType);
		recruit.setTypeName(typeName);
		recruit.setProvince(province);
		recruit.setCity(city);

		//recruit.setPubTime(DateUtils.getTimestamp());
		 
		 
		recruit.setCreatetime(DateUtils.getTimestamp());
		recruitManager.save(recruit);
		return "ok";
	}
	private void initDatas(HttpServletResponse response) throws Exception {
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_RECRUIT_TYPE.typeID());
		
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary job : jobTypes) {
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":"+job.getId());
			sb1.append(",\"pid\":"+job.getPid());
			sb1.append(",\"name\":\""+job.getName()+"\"");
			sb1.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"jobTypes\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
	private DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
}