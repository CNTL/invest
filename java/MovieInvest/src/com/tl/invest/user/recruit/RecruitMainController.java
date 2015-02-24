package com.tl.invest.user.recruit;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.common.DateUtils;
import com.tl.common.Message;
import com.tl.common.ParamInitUtils;
import com.tl.common.StringUtils;
import com.tl.common.WebUtil;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��12��1�� ����1:30:20 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RecruitMainController extends Entry {
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("detail".equals(action)){//��ȡ��Ƹ��ϸ��Ϣ
			detail(request, response, model);
		} else if("queryNew".equals(action)){//��ȡ����9����Ƹ��Ϣ
			queryRecruitsNew(request, response, model, "queryNew");
		} else if("queryHot".equals(action)){//��ȡ����9����Ƹ��Ϣ
			queryRecruits(request, response, model, "queryHot");
		}else if("getRecruitUser".equals(action)){
			getRecruitUser(request,response,model);
		}
		else if("delRecruit".equals(action)){
			delRecruit(request,response,model);
		}
		else if("RecruitSubscibe".equals(action)){
			RecruitSubscibe(request,response,model);
		}else if("updateRecruitSubscibe".equals(action)){
			updateRecruitSubscibe(request,response,model);
		}else if("cancelRecruitSubscibe".equals(action)){
			cancelRecruitSubscibe(request,response,model);
		}
		else{//ֱ�ӽ�����Ƹ��Ϣ�б�
			Dictionary[] types = recruitManager.types();
			model.put("types", types);
			init(request,response,model);
		}
		
	}
	/** ����ְλ����
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void  updateRecruitSubscibe(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		RecruitSubscibe subscibe = new RecruitSubscibe();
		
		try {
			User user = userManager.getUserByID(getInt(request, "userid",0));
			subscibe.setId(getInt(request, "id",0));
			subscibe.setName(get(request, "name",""));
			subscibe.setUserId(user.getId());
			subscibe.setUserName(user.getName());
			subscibe.setMail(get(request, "mail",""));
			subscibe.setCityId(getInt(request, "cityid",0));
			subscibe.setCityName(get(request, "cityname",""));
			subscibe.setRecId(getInt(request, "recid",0));
			subscibe.setRecName(get(request, "recname",""));
			subscibe.setRate(getInt(request, "rate",0));
			subscibe.setDeleted(0);
			subscibe.setCreatetime(DateUtils.getTimestamp());
			subscibe.setPosttime(DateUtils.getTimestamp());
			recruitSubscibeManager.save(subscibe);
			output("ok", response);
		} catch (Exception e) {
			 
		}
	}
	/** ȡ��ְλ����
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void  cancelRecruitSubscibe(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		try {
			Integer id = getInt(request, "id",0);
			recruitSubscibeManager.cancle(id);
		} catch (Exception e) {
			 
		}
	}
	/** ����ְλ
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void init(HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		Integer userid = getInt(request, "userid",-1);
		if(userid!=-1){
			//��õ�ǰ��˾������ְλ��
			UserRecruit[] recruitLists = recruitManager.queryRecruitsByUserID(userid);
			model.put("recruitLists", recruitLists);
		}
		
	}
	
	/** ְλ����
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void RecruitSubscibe(HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] types = recruitManager.types();
		model.put("types", types);
		Dictionary[] cities = dicReader.getDics(DicTypes.DIC_RECRUIT_HOT_TYPE.typeID());
		model.put("cities", cities);
	}
	
	/** ����ְλID��ȡͶ��������
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void getRecruitUser(HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		int recid = getInt(request, "recid",-1);
		String result = recruitManager.getRecruitUserJson(recid);
		output(result, response);
	}
	
	/** ɾ��ְλ
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void delRecruit(HttpServletRequest request, HttpServletResponse response, Map model)throws Exception{
		int recid = getInt(request, "recid",-1);
		if(recid!=-1){
			try {
				UserRecruit userRecruit  = new UserRecruit();
				userRecruit = recruitManager.getRecruitByID(recid);
				userRecruit.setDeleted(1);
				recruitManager.save(userRecruit);
				output("ok", response);
			} catch (Exception e) {
				output("no", response);
			}
			
		}else{
			output("no", response);
		}
		
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��ѯ������Ƹ��Ϣ
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryRecruits(HttpServletRequest request, HttpServletResponse response, Map model, String queryType) throws Exception{
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] cities = dicReader.getDics(DicTypes.DIC_RECRUIT_HOT_TYPE.typeID());
		
		request.setCharacterEncoding("utf-8");
		String recruitType = get(request, "recruitType");//�Ƿ���ְλ����view-���������Ƹ��Ϣ��edit-�����ҵ�ְλ��Ϣ��
		int searchType = getInt(request, "searchType");//0=ְλ��1=��˾
		String key = get(request, "key");//��ѯ������ֵ
		String city = get(request, "city");//��ѯ������ֵ
		
		int curPage = getInt(request, "curPage", 1);
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = user == null ? 0 : user.getId();
		Message msg = recruitManager.queryRecruits(curPage, 9, userId, recruitType, queryType, searchType, key, city);
		Dictionary[] types = recruitManager.types();
		model.put("queryType", queryType);
		model.put("recruitType", recruitType);
		model.put("types", types);
		model.put("searchType", searchType);
		model.put("key", key);
		model.put("city", city);
	
		model.put("more", ParamInitUtils.getString(request.getParameter("more")));
		model.put("msg", msg);
		model.put("cities", cities);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ��ѯ������Ƹ��Ϣ
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void queryRecruitsNew(HttpServletRequest request, HttpServletResponse response, Map model, String queryType) throws Exception{
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] cities = dicReader.getDics(DicTypes.DIC_RECRUIT_HOT_TYPE.typeID());
		
		request.setCharacterEncoding("utf-8");
		String recruitType = "view";//get(request, "recruitType");//�Ƿ���ְλ����view-���������Ƹ��Ϣ��edit-�����ҵ�ְλ��Ϣ��
		int searchType = getInt(request, "searchType");//0=ְλ��1=��˾
		String key = get(request, "key");//��ѯ������ֵ
		Integer city = getInt(request, "city",-1);//��ѯ������ֵ
		Integer Days = getInt(request, "Days",-1); //����ʱ��
		Integer Degree = getInt(request, "Degree",-1);//���ѧ��
		Integer JobType = getInt(request, "JobType",-1);//��������
		Integer PubTime = getInt(request, "PubTime",-1);//����ʱ��
		 
		int curPage = getInt(request, "curPage", 1);
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int userId = user == null ? 0 : user.getId();
		Message msg = recruitManager.queryRecruits(curPage, 9, userId, recruitType, queryType, searchType, key, city,Days,Degree,JobType,PubTime);
		Dictionary[] types = recruitManager.types();
		model.put("queryType", queryType);
		model.put("recruitType", recruitType);
		model.put("types", types);
		model.put("searchType", searchType);
		model.put("key", key);
		model.put("city", city);
		
		model.put("more", ParamInitUtils.getString(request.getParameter("more")));
		model.put("msg", msg);
		model.put("cities", cities);
		model.put("Days", Days);
		model.put("Degree", Degree);
		model.put("JobType", JobType);
		model.put("PubTime", PubTime);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ��ȡ��Ƹ��Ϣ��ϸ��Ϣ
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void detail(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		//�ж��Ƿ������˸�������
		User  usercur = userManager.getUserByCode(SessionHelper.getUserCode(request));
		
		if(!isCoverdInfo(usercur)){
			model.put("isConverd", "0");
		}
		else{
			model.put("isConverd", "1");
		}
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		if(id == 0) return;
		UserRecruit recruit = recruitManager.getRecruitByID(id);
		if(recruit == null) return;
		recruit.setTime(DateUtils.format(recruit.getCreatetime(), "yyyy-MM-dd hh:mm:ss"));
		User user = userManager.getUserByID(recruit.getUserId());
		 
		String head = user.getHead();
		if(head == null || head.length() == 0) head = "user/headImg/default.bmp";
		user.setHead(WebUtil.getRoot(request).concat(head));
		String typeName = recruit.getTypeName();
		if(typeName != null && typeName.length() > 0){
			List<UserRecruit> simiRecruits = recruitManager.querySimiRecruits(typeName);
			model.put("simiRecruits", simiRecruits);
		}
		
		model.put("recruit", recruit);
		model.put("user", user);
		//response.sendRedirect(WebUtil.getRoot(request) + "user/recruit/recruitDetail.jsp");
	}
	private boolean isCoverdInfo(User user)throws Exception{
		
		boolean ret = false;
		if( (!StringUtils.isEmpty(user.getOrgFullname())) 
				&& (!StringUtils.isEmpty(user.getLocation())) 
				&& (!StringUtils.isEmpty(user.getTypeName()))
				&& (!StringUtils.isEmpty(user.getOrgScale()))){
			ret = true;
		}
		return ret;
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
	private RecruitSubscibeManager recruitSubscibeManager = (RecruitSubscibeManager)Context.getBean(RecruitSubscibeManager.class);
}