package com.tl.invest.workspace;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;
import net.sf.json.processors.JsonValueProcessor;

import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.service.ProjectService;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.web.BaseController;

public class MainListController extends BaseController {

	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		
		String actionString =get(request, "action","");
		
		if(actionString.equals("getIndexItems")){
			getIndexItems(request,response,model);
		}
	}
	
	/**��ȡ��ҳ��Ŀ
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	private void getIndexItems(HttpServletRequest request,
			HttpServletResponse response, Map model)throws Exception{
		Integer pageIndex = getInt(request, "pageIndex",0);
		Integer pageSize = 4;
	
		MainList mainList = new MainList();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        
		try
		{
			Integer startIndex = (pageIndex-1)*pageSize;
			
			ProjectExt[] projectExts = projectService.getProjectExts(pageSize, pageIndex,"proj_approveStatus in(-1,2)", null);
			if(projectExts!=null&&projectExts.length>0){
				ArrayList<ProjectExt> projectItems =new ArrayList<ProjectExt>();  
				for (ProjectExt e : projectExts) {
					projectItems.add(e);
				}
				mainList.setProjectItems(projectItems);
			}
			
			UserRecruit[] userRecruits = recruitManager.queryRecruits(startIndex, pageSize);
			if(userRecruits!=null && userRecruits.length>0){
				ArrayList<UserRecruit> userRecruitItems =new ArrayList<UserRecruit>(); 
				 for (UserRecruit e : userRecruits) {
					 userRecruitItems.add(e);
				}
				mainList.setUserRecruitItems(userRecruitItems);
			}
			
			User[] users=userManager.getPersons(startIndex, pageSize);
			if(users!=null && users.length>0){
				ArrayList<User> userItem =new ArrayList<User>();
				for (User e : users) {
					userItem.add(e);
				}
				mainList.setUserItem(userItem);
			}
            
        }catch(Exception e){    
           
        }
		
		String ttString = JSONObject.fromObject(mainList, jsonConfig).toString();
		output(ttString, response);
	}
	
	/**
	 * ӰƸ
	 */
	private RecruitManager recruitManager =null;
	public RecruitManager getRecruitManager() {
		return recruitManager;
	}
	public void setRecruitManager(RecruitManager recruitManager) {
		this.recruitManager = recruitManager;
	}
	/**
	 * Ӱ��
	 */
	private UserManager userManager = null;
	
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	/**
	 * ��Ŀ
	 */
	private ProjectService projectService = null;
	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	/**
	 * �����json������ת�������⡣
	 * ��˵��  
	 * 
	 * @������ Timestamp ������
	 */
	public class DateJsonValueProcessor implements JsonValueProcessor{
	    
	     /**
	     * ��ĸ ���ڻ�ʱ��Ԫ�� ��ʾ ʾ�� <br>
	     * G Era ��־�� Text AD <br>
	     * y �� Year 1996; 96 <br>
	     * M ���е��·� Month July; Jul; 07 <br>
	     * w ���е����� Number 27 <br>
	     * W �·��е����� Number 2 <br>
	     * D ���е����� Number 189 <br>
	     * d �·��е����� Number 10 <br>
	     * F �·��е����� Number 2 <br>
	     * E �����е����� Text Tuesday; Tue<br>
	     * a Am/pm ��� Text PM <br>
	     * H һ���е�Сʱ����0-23�� Number 0 <br>
	     * k һ���е�Сʱ����1-24�� Number 24<br>
	     * K am/pm �е�Сʱ����0-11�� Number 0 <br>
	     * h am/pm �е�Сʱ����1-12�� Number 12 <br>
	     * m Сʱ�еķ����� Number 30 <br>
	     * s �����е����� Number 55 <br>
	     * S ������ Number 978 <br>
	     * z ʱ�� General time zone Pacific Standard Time; PST; GMT-08:00 <br>
	     * Z ʱ�� RFC 822 time zone -0800 <br>
	     */
	    public static final String Default_DATE_PATTERN = "yyyy-MM-dd";
	    private DateFormat dateFormat;

	    /**
	     * 
	     */
	    public DateJsonValueProcessor(String datePattern) {
	        try {
	            dateFormat = new SimpleDateFormat(datePattern);
	        } catch (Exception e) {
	            dateFormat = new SimpleDateFormat(Default_DATE_PATTERN); 
	        }
	    }

	    /*
	     * (non-Javadoc)
	     * @see
	     * net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang
	     * .Object, net.sf.json.JsonConfig)
	     */
	    @Override
	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	        return process(value);
	    }

	    /*
	     * (non-Javadoc)
	     * @see
	     * net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang
	     * .String, java.lang.Object, net.sf.json.JsonConfig)
	     */
	    @Override
	    public Object processObjectValue(String key, Object value,JsonConfig jsonConfig) {
	        return process(value);
	    }

	    private Object process(Object value) {
	        if (value == null) {
	            return "";
	        } else {
	            return dateFormat.format((Timestamp) value);
	        }
	    }

	}

	
}
