package com.tl.invest.user.user;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.tl.common.DateJsonValueProcessor;
import com.tl.common.DateUtils;
import com.tl.common.JsonDateValueProcessor;
import com.tl.common.ParamInitUtils;
import com.tl.common.UploadHelper;
import com.tl.common.UserEncrypt;
import com.tl.common.WebUtil;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.user.bankcard.BankcardManager;
import com.tl.invest.user.bankcard.UserBankcard;
import com.tl.invest.user.recruit.RecruitManager;
import com.tl.invest.user.works.UserWorks;
import com.tl.invest.user.works.WorksManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;
import com.tl.sys.sysuser.DataTableParam;
import com.tl.sys.sysuser.DataTableUtil;

/**
 * �û�������
 * @author leijj
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserController extends BaseController {
	private UserManager userManager = null;
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	private BankcardManager bankcardManager = null;
	public BankcardManager getBankcardManager() {
		return bankcardManager;
	}
	public void setBankcardManager(BankcardManager bankcardManager) {
		this.bankcardManager = bankcardManager;
	}
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("list".equals(action)){//��ȡ�û��б�
			String json = queryUsers(request, response);
			output(json, response);
		
		} else if("find".equals(action)){//�����û�findByID
			
			find(request, response);
		} else if("findLogin".equals(action)){//���ҵ�¼�û���Ϣ
			findLogin(request, response);
		} else if("pwdEdit".equals(action)){//�޸�����
			String json = pwdEdit(request, response);
			output(json, response);
		} else if("orgBasicInfo".equals(action)){//����������Ϣ
			orgBasicInfo(request, response);
		} else if("orgDetailInfo".equals(action)){//������ϸ��Ϣ
			orgDetailInfo(request, response);
		} else if("orgRelAuth".equals(action)){//������֤
			orgRelAuth(request, response);
		} else if("userBasicInfo".equals(action)){//�û�������Ϣ
			userBasicInfo(request, response);
		} else if("userRelAuth".equals(action)){//������֤
			userRelAuth(request, response);
		} else if("picBook".equals(action)){//����ͼ���ϴ�
			User sysuser = setUser(request);
			userManager.create(sysuser);
			String viewName = WebUtil.getRoot(request) + "common/After";
			model.put("callMode", "0");
			model.put("needRefresh", "true");
			model.put("@VIEWNAME@", viewName);
			//userManager.create(user);
		} else if("works".equals(action)){//������Ʒ�ϴ�
			works(request, response);
		} else if("del".equals(action)){//ɾ���û�
			userManager.delete(ParamInitUtils.getInt(request.getParameter("id")));
			output("true", response);
		} else if ("uploadImg".equals(action)) {
			uploadHeadImg(request, response, model);
		} else if ("saveImg".equals(action)) {
			String json = saveHeadImg(request);
			output(json, response);
		}else if ("saveHeadCardImg".equals(action)) {
			String json = saveHeadCardImg(request);
			output(json, response);
		}
		else if ("uploadAtt".equals(action)) {
			String json = upload(request);
			output(json, response);
		}else if ("getUser".equals(action)) {
			getUser(request,response);
		} else if ("checkUser".equals(action)) {
			checkUser(request,response);
		} else if ("deleteUser".equals(action)) {
			deleteUser(request,response);
		} else if ("typeDatas".equals(action)) {
			typeDatas(request,response);
		} else if ("cities".equals(action)) {
			cities(request,response);
		} else if("orderUser".equals(action)){
			orderUser(request,response);
		}
		 else if("setpoint".equals(action)){
			 setpoint(request,response);
		}else if("ageDatas".equals(action)){
			ageDatas(request,response);
		}
		else if("orgTypeDatas".equals(action)){
			orgTypeDatas(request,response);
		}
		
		
	}
	/**���û���
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void  setpoint(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int userID = getInt(request, "userid", 0);
		int point = getInt(request, "point",0);
		 
		userManager.setPoint(userID, point);
		
		output("ok", response);
	}
	/**����ID��ȡ�û�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = getInt(request, "id", 0);
		User user = userManager.getUserByID(id);
		user.setDeleted(1);
		userManager.update(user);
		output("ok", response);
	}
	/**����ID��ȡ�û�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void checkUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = getInt(request, "id", 0);
		int type = getInt(request, "type",0);
		User user = userManager.getUserByID(id);
	 
		int check = getInt(request, "check",0);
		user.setIsRealNameIdent(check);
		userManager.update(user);
		output("ok", response);
	}
	/**�����û�����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void orderUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = getInt(request, "id", 0);
		int order = getInt(request, "order",0);
		User user = userManager.getUserByID(id);
		user.setPerOrder(order);
		userManager.update(user);
		output("ok", response);
	}
	/**����ID��ȡ�û�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void getUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int id = getInt(request, "id", 0);
		User user = userManager.getUserByID(id);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		JSONObject jsonArray = JSONObject.fromObject(user,jsonConfig);  
		output(jsonArray.toString(), response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� �����û�code�����û���Ϣ
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private void find(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(ParamInitUtils.getString(request.getParameter("code")));
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		JSONObject jsonArray = JSONObject.fromObject(user,jsonConfig); 
		output(jsonArray.toString(), response);
	}
	 
	/** 
	* @author  leijj 
	* ���ܣ� ���ҵ�¼�û���Ϣ
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void findLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		List<UserBankcard> bankcards = bankcardManager.getByUserId(user.getId());
		if(bankcards != null && bankcards.size() > 0) 
			user.setBankcards(bankcards);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		JSONObject jsonArray = JSONObject.fromObject(user,jsonConfig);  
		output(jsonArray.toString(), response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� �޸�����
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String pwdEdit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String password = get(request, "password");
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		String oldPassword = UserEncrypt.getInstance().decrypt(user.getPassword());
		if(!password.equals(oldPassword)) return "ԭ���벻��ȷ��";
		
		user.setPassword(password);
		userManager.update(user);
		return "ok";
	}
	/** 
	* @author  leijj 
	* ���ܣ� ����������Ϣ
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void orgBasicInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setOrgShortname(ParamInitUtils.getString(request.getParameter("orgShortname")));
		user.setOrgFullname(ParamInitUtils.getString(request.getParameter("orgFullname")));
		user.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		user.setProvince(ParamInitUtils.getString(request.getParameter("province")));//ʡ��
		user.setCity(ParamInitUtils.getString(request.getParameter("city")));//����
		user.setPerJob(ParamInitUtils.getString(request.getParameter("orgType")));//��������
		userManager.update(user);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ������ϸ��Ϣ
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void orgDetailInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setOrgFullname(ParamInitUtils.getString(request.getParameter("orgFullname")));
		//user.setProvince(ParamInitUtils.getString(request.getParameter("province")));//ʡ��
		//user.setCity(ParamInitUtils.getString(request.getParameter("city")));//����
		user.setLocation(ParamInitUtils.getString(request.getParameter("location")));
		user.setCoordinate(ParamInitUtils.getString(request.getParameter("coordinate")));
		user.setOrgNature(ParamInitUtils.getString(request.getParameter("orgNature")));
		user.setOrgTrade(ParamInitUtils.getString(request.getParameter("orgTrade")));
		user.setOrgScale(ParamInitUtils.getString(request.getParameter("orgScale")));
		user.setOrgHomePage(ParamInitUtils.getString(request.getParameter("orgHomePage")));
		userManager.update(user);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ������֤
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void orgRelAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setOrgFullname(ParamInitUtils.getString(request.getParameter("orgFullname")));
		user.setName(ParamInitUtils.getString(request.getParameter("name")));
		user.setIdentityCard(ParamInitUtils.getString(request.getParameter("identityCard")));
		user.setPerPhone(ParamInitUtils.getString(request.getParameter("perPhone")));//�ֻ���
		user.setOrganization(ParamInitUtils.getString(request.getParameter("organization")));
		user.setOrgBusinessLicense(ParamInitUtils.getString(request.getParameter("orgBusinessLicense")));
		String[] bankNums = request.getParameterValues("bankNums");
		String[] openingBanks = request.getParameterValues("openingBanks");
		if(bankNums != null && bankNums.length > 0){
			for(int i = 0; i < bankNums.length ; i++){
				//�������п���Ϣ
				UserBankcard bankcard = new UserBankcard();
				bankcard.setBankName(openingBanks[i]);
				bankcard.setOpeningBank(openingBanks[i]);
				bankcard.setBankNum(bankNums[i]);
				bankcard.setUserId(user.getId());
				bankcard.setUserName(user.getName());
				bankcard.setCreateTime(DateUtils.getTimestamp());
				bankcardManager.save(bankcard);
			}
		}
		userManager.update(user);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� �û�������Ϣ
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void userBasicInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		//user.setBirthdate(DateUtils.format(""));
		user.setGender(ParamInitUtils.getInt(request.getParameter("gender")));
		user.setPerNickName(ParamInitUtils.getString(request.getParameter("perNickName")));
		user.setPerPostAddr(ParamInitUtils.getString(request.getParameter("perPostAddr")));
		user.setPerPostCode(ParamInitUtils.getString(request.getParameter("perPostCode")));
		user.setIntro(get(request, "intro",""));
		user.setIntro_show(getInt(request, "intro_show",0));
		user.setName_show(getInt(request, "name_show",0));
		user.setProvince(get(request, "province" ,""));
		user.setPerJob(get(request, "recIDs",""));
		user.setCity(get(request, "city" ,""));
		user.setAgeTypeID(getInt(request, "age",0));
		user.setHeight(getInt(request, "height",0));
		user.setHeight_show(getInt(request, "height_show",0));
		user.setWeight(getInt(request, "weight",0));
		user.setWeight_show(getInt(request, "weight_show",0));
		user.setSchool(get(request, "school",""));
		user.setSchool_show(getInt(request, "school_show",0));
		user.setProfessional(get(request, "professional",""));
		user.setProfessional_show(getInt(request, "professional_show",0));
		user.setDegreeid(getInt(request, "degreeid",0));
		user.setDegree(get(request, "degree",""));
		user.setDegree_show(getInt(request, "degree_show",0));
		userManager.update(user);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� ʵ����֤
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private void userRelAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setName(get(request, "name",""));//����
		user.setProvince(get(request, "province"));//ʡ��
		user.setCity(get(request, "city",""));//����
		user.setPerJob(get(request, "perJob",""));//ְҵ
		user.setPerPhone(get(request, "perPhone",""));//�ֻ���
		user.setIdentityCard(get(request, "identityCard",""));//���֤
		user.setOrganization(get(request, "organization",""));
		user.setOrgBusinessLicense(get(request, "orgBusinessLicense",""));
//		int firstType = getInt(request, "firstType");
//		int secondType = getInt(request, "secondType");
//		String typeName = "";
//		Dictionary dic = null;
//		if(secondType > 0){
//			dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), secondType);
//			typeName = dic.getCascadeName();
//		} else if(firstType > 0){
//			dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), firstType);
//			typeName = dic.getCascadeName();
//		}
//		user.setPerJob(String.valueOf(UserHelper.getPreJob(firstType,secondType )));
//		user.setFirstType(firstType);
//		user.setSecondType(secondType); 
//		user.setTypeName(typeName);
		/*
		String[] bankNums = request.getParameterValues("bankNums");
		String[] openingBanks = request.getParameterValues("openingBanks");
		if(bankNums != null && bankNums.length > 0){
			for(int i = 0; i < bankNums.length ; i++){
				//�������п���Ϣ
				UserBankcard bankcard = new UserBankcard();
				bankcard.setBankName(openingBanks[i]);
				bankcard.setOpeningBank(openingBanks[i]);
				bankcard.setBankNum(bankNums[i]);
				bankcard.setUserId(user.getId());
				bankcard.setUserName(user.getName());
				bankcard.setCreateTime(DateUtils.getTimestamp());
				bankcardManager.save(bankcard);
			}
		}
		*/
		//openingBank//������
		userManager.update(user);
		output("ok", response);
	}
	
	private String works(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		WorksManager worksManager = (WorksManager) Context.getBean(WorksManager.class);
		String content = request.getParameter("MSG_Content");
		UserWorks works = new UserWorks();
		works.setUserId(user.getId());
		works.setUserName(user.getName());
		works.setName(get(request, "name"));
		works.setContent(content);
		works.setCreateTime(DateUtils.getTimestamp());
		works.setWorksTime(DateUtils.getTimestamp());
		worksManager.save(works);
		return "ok";
	}
	
	private User setUser(HttpServletRequest request){
		User user = new User();
		user.setId(ParamInitUtils.getInt(request.getParameter("id")));
		user.setName(ParamInitUtils.getString(request.getParameter("name")));
		user.setCode(ParamInitUtils.getString(request.getParameter("code")));
		user.setPassword(ParamInitUtils.getString(request.getParameter("password")));
		user.setCreateTime(DateUtils.getTimestamp());
		return user;
	}
	
	public String queryUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataTableParam param = DataTableUtil.getParam(request);
		User user = setUser(request);
		List users = userManager.queryUsersStr(param.getiDisplayStart(), param.getiDisplayLength(), user);
		int count = DAOHelper.getCount("user");
		String json = tojson(users, count ,param.getsEcho());
		return json;
	}   
	
	//�ϴ����ļ�
	private void uploadHeadImg(HttpServletRequest request,HttpServletResponse response, Map model) throws Exception {
		String newPrefix = SessionHelper.getUserCode(request) + DateUtils.getSysTime();
		String newPath = UploadHelper.rootPath(request) + "upload/user/headImg/" + DateUtils.getSysDate();
		
		File img = UploadHelper.upload(request, newPath, newPrefix);
		
		String fileName = img.getName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		String result = newPath + File.separator + fileName.substring(0,fileName.lastIndexOf("."))+".jpg";
		BufferedImage image;
		
		if(!(fileExt.equals("jpg")) && !(fileExt.equals("jpeg"))) {
			UploadHelper.ImgConvert(img,fileExt,result);
	        File f=new File(result);
	        image = ImageIO.read(f);
	        img.delete();
		} else {
			image = ImageIO.read(img);
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		response.sendRedirect(WebUtil.getRoot(request) + "user/user/uploadHeadOk.html?" + result + "," + width + "," + height);
	}
	
	private String saveHeadImg(HttpServletRequest request) throws Exception {
		try {
			
	        User user = userManager.getUserByID(SessionHelper.getUserID(request));
	        //String head = destFile.substring(rootPath.length());
	        String head = request.getParameter("cut_url");
	        user.setHead(head);
	        userManager.update(user);
	        return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "�ϴ�ͷ��ʧ��:" + e.getMessage();
		}
	}
	private String saveHeadCardImg(HttpServletRequest request) throws Exception {
		try {
			
	        User user = userManager.getUserByID(SessionHelper.getUserID(request));
	        //String head = destFile.substring(rootPath.length());
	        String headcard = request.getParameter("cut_url1");
	        user.setHeadcard(headcard);
	        userManager.update(user);
	        return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return "�ϴ�ͷ��ʧ��:" + e.getMessage();
		}
	}
	private String upload(HttpServletRequest request){
		return UserHelper.saveAffix(request, "upload/user/relAuth/");
	}
	public String tojson(List list, int count, String sEcho)     {  
			String json = null; // ���ص�		json����    
			String aaData=JSONArray.fromObject(list).toString();     
			json =  "{\"sEcho\":"+sEcho+",\"iTotalRecords\":"+count+",\"iTotalDisplayRecords\":"+count+",\"data\":"+aaData+"}";      
			return json;     
	}
	
	private void typeDatas(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
	private void ageDatas(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_AGE_TYPE.typeID());
		
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
		sb.append("\"ageTypes\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
	private void orgTypeDatas(HttpServletRequest request, HttpServletResponse response) throws Exception{
		DictionaryReader dicReader = (DictionaryReader)Context.getBean(DictionaryReader.class);
		Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_ORG_TYPE.typeID());
		
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
		sb.append("\"orgTypes\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
	
	private void cities(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 
		Dictionary[] cities = recruitManager.getHotCitys();
		
		StringBuffer sb1 = new StringBuffer();
		for (Dictionary city : cities) {
			if(sb1.length()>0) sb1.append(",");
			sb1.append("{");
			sb1.append("\"id\":"+city.getId());
			sb1.append(",\"pid\":"+city.getPid());
			sb1.append(",\"name\":\""+city.getName()+"\"");
			sb1.append("}");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"cities\":["+sb1.toString()+"]");
		sb.append("}");
		
		output(sb.toString(), response);
	}
	private DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
	private RecruitManager recruitManager = (RecruitManager)Context.getBean(RecruitManager.class);
}