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
	
	/**获取首页项目
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
	 * 影聘
	 */
	private RecruitManager recruitManager =null;
	public RecruitManager getRecruitManager() {
		return recruitManager;
	}
	public void setRecruitManager(RecruitManager recruitManager) {
		this.recruitManager = recruitManager;
	}
	/**
	 * 影人
	 */
	private UserManager userManager = null;
	
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	/**
	 * 项目
	 */
	private ProjectService projectService = null;
	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	/**
	 * 解决在json中日期转换的问题。
	 * 类说明  
	 * 
	 * @简述： Timestamp 处理器
	 */
	public class DateJsonValueProcessor implements JsonValueProcessor{
	    
	     /**
	     * 字母 日期或时间元素 表示 示例 <br>
	     * G Era 标志符 Text AD <br>
	     * y 年 Year 1996; 96 <br>
	     * M 年中的月份 Month July; Jul; 07 <br>
	     * w 年中的周数 Number 27 <br>
	     * W 月份中的周数 Number 2 <br>
	     * D 年中的天数 Number 189 <br>
	     * d 月份中的天数 Number 10 <br>
	     * F 月份中的星期 Number 2 <br>
	     * E 星期中的天数 Text Tuesday; Tue<br>
	     * a Am/pm 标记 Text PM <br>
	     * H 一天中的小时数（0-23） Number 0 <br>
	     * k 一天中的小时数（1-24） Number 24<br>
	     * K am/pm 中的小时数（0-11） Number 0 <br>
	     * h am/pm 中的小时数（1-12） Number 12 <br>
	     * m 小时中的分钟数 Number 30 <br>
	     * s 分钟中的秒数 Number 55 <br>
	     * S 毫秒数 Number 978 <br>
	     * z 时区 General time zone Pacific Standard Time; PST; GMT-08:00 <br>
	     * Z 时区 RFC 822 time zone -0800 <br>
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
