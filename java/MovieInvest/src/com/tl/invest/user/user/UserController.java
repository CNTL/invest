package com.tl.invest.user.user;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tl.common.DateUtils;
import com.tl.common.HeadImage;
import com.tl.common.ImageHelper;
import com.tl.common.ParamInitUtils;
import com.tl.common.UploadHelper;
import com.tl.common.UserEncrypt;
import com.tl.common.WebUtil;
import com.tl.invest.user.bankcard.BankcardManager;
import com.tl.invest.user.bankcard.UserBankcard;
import com.tl.invest.user.works.UserWorks;
import com.tl.invest.user.works.WorksManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAOHelper;
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
		} else if("find".equals(action)){//�����û�
			find(request, response);
		} else if("findLogin".equals(action)){//���ҵ�¼�û���Ϣ
			findLogin(request, response);
		} else if("create".equals(action)){//�û�ע��
			String json = create(request, response);
			output(json, response);
		} else if("pwdEdit".equals(action)){//�޸�����
			String json = pwdEdit(request, response);
			output(json, response);
		} else if("complete".equals(action)){//���Ƹ�����Ϣ���޸ģ�
			String json = complete(request, response);
			output(json, response);
		} else if("relAuth".equals(action)){//ʵ����֤���������п���Ϣ��
			String json = relAuth(request, response);
			output(json, response);
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
		} else if ("uploadAtt".equals(action)) {
			String json = upload(request);
			output(json, response);
		}
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
		JSONObject jsonArray = JSONObject.fromObject(user);  
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
		
		JSONObject jsonArray = JSONObject.fromObject(user);  
		output(jsonArray.toString(), response);
	}
	/** 
	* @author  leijj 
	* ���ܣ� �û�ע��
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		user.setName(ParamInitUtils.getString(request.getParameter("name")));
		user.setCode(ParamInitUtils.getString(request.getParameter("code")));
		//user.setType(ParamInitUtils.getString(request.getParameter("type")));
		user.setType(ParamInitUtils.getInt(request.getParameter("TypeId")));
		user.setPassword(ParamInitUtils.getString(request.getParameter("password")));
		user.setCreateTime(DateUtils.getTimestamp());
		userManager.create(user);
		return "ok";
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
	* ���ܣ� ���Ƹ������� 
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String complete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setPerNickName(ParamInitUtils.getString(request.getParameter("nickName")));
		user.setEmail(ParamInitUtils.getString(request.getParameter("email")));
		user.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		user.setPerPostAddr(ParamInitUtils.getString(request.getParameter("postAddr")));
		
		userManager.update(user);
		return "ok";
	}
	
	/** 
	* @author  leijj 
	* ���ܣ� ʵ����֤
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String relAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setName(ParamInitUtils.getString(request.getParameter("name")));//����
//		user.setType(ParamInitUtils.getString(request.getParameter("type")));//ע������
		user.setType(ParamInitUtils.getInt(request.getParameter("TypeId")));
		user.setPerProvince(ParamInitUtils.getString(request.getParameter("province")));//���ڵ�
		user.setPerCity(ParamInitUtils.getString(request.getParameter("city")));//���ڵ�
		user.setPerJob(ParamInitUtils.getString(request.getParameter("job")));//ְҵ
		user.setPerPhone(ParamInitUtils.getString(request.getParameter("phone")));//�ֻ���
		user.setIdentityCard(ParamInitUtils.getString(request.getParameter("identityCard")));//����֤
		user.setOrganization(ParamInitUtils.getString(request.getParameter("organization")));//��ҵ��֯����֤����
		user.setOrgBusinessLicense(ParamInitUtils.getString(request.getParameter("businessLicense")));//��ҵӪҵִ��ɨ���
		
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
		//openingBank//������
		userManager.update(user);
		return "ok";
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
			int width=120;
			//��ȡ���źͼ��в���
			String cutPos = request.getParameter("cut_pos");
			String[] pos = cutPos.split(",");
			//���Ͻ�����
			int x = Integer.parseInt(pos[0]);
			int y = Integer.parseInt(pos[1]);
			//���ź��ͼƬ����
			int picWidth = Integer.parseInt(pos[2]);
			int picHeight = Integer.parseInt(pos[3]);
			String fileName = request.getParameter("cut_url");
			String srcFileName = fileName;
			String destFile = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + fileName.substring(fileName.lastIndexOf("."));
			ImageHelper.ZoomTheImage(srcFileName, destFile,picWidth,picHeight);
			HeadImage headImage = new HeadImage(x,y,width,width);
			headImage.setSrcPath(destFile);  
			headImage.setSubPath(destFile);
	        ImageHelper.cut(headImage);
	        //User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
	        String rootPath = request.getSession().getServletContext().getRealPath("/");
			//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
			
	        User user = userManager.getUserByID(SessionHelper.getUserID(request));
	        String head = destFile.substring(rootPath.length());
	        head = head.replace("\\", "/");
	        user.setHead(head);
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
}