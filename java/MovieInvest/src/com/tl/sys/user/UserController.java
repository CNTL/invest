package com.tl.sys.user;

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
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.web.BaseController;
import com.tl.kernel.web.SysSessionUser;
import com.tl.sys.common.SessionHelper;
import com.tl.sys.sysuser.DataTableParam;
import com.tl.sys.sysuser.DataTableUtil;

/**
 * 用户控制类
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
		if("list".equals(action)){//获取用户列表
			String json = queryUsers(request, response);
			output(json, response);
		} else if("find".equals(action)){//查找用户
			find(request, response);
		} else if("create".equals(action)){//用户注册
			String json = create(request, response);
			output(json, response);
		} else if("pwdEdit".equals(action)){//修改密码
			String json = pwdEdit(request, response);
			output(json, response);
		} else if("complete".equals(action)){//完善个人信息（修改）
			String json = complete(request, response);
			output(json, response);
		} else if("relAuth".equals(action)){//实名认证（增加银行卡信息）
			String json = relAuth(request, response);
			output(json, response);
		} else if("picBook".equals(action)){//个人图册上传
			User sysuser = setUser(request);
			userManager.create(sysuser);
			String viewName = "common/After";
			model.put("callMode", "0");
			model.put("needRefresh", "true");
			model.put("@VIEWNAME@", viewName);
			//userManager.create(user);
		} else if("works".equals(action)){//个人作品上传
			works(request, response);
		} else if("del".equals(action)){//删除用户
			userManager.delete(ParamInitUtils.getInt(request.getParameter("id")));
			output("true", response);
		} else if ("uploadImg".equals(action)) {
			uploadHeadImg(request, response, model);
		} else if ("saveImg".equals(action)) {
			saveHeadImg(request, response, model);
		} else {
			InitHeadImg(request, response, model);
		}
	}
	/** 
	* @author  leijj 
	* 功能： 根据用户code查找用户信息
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
	* 功能： 用户注册
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String create(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = new User();
		user.setName(ParamInitUtils.getString(request.getParameter("name")));
		user.setCode(ParamInitUtils.getString(request.getParameter("code")));
		user.setType(ParamInitUtils.getString(request.getParameter("type")));
		user.setTypeId(ParamInitUtils.getInt(request.getParameter("TypeId")));
		user.setPassword(ParamInitUtils.getString(request.getParameter("password")));
		user.setCreateTime(DateUtils.getTimestamp());
		userManager.create(user);
		return "ok";
	}
	/** 
	* @author  leijj 
	* 功能： 修改密码
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String pwdEdit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String password = get(request, "password");
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		String oldPassword = UserEncrypt.getInstance().decrypt(user.getPassword());
		if(!password.equals(oldPassword)) return "原密码不正确！";
		
		user.setPassword(password);
		userManager.update(user);
		return "ok";
	}
	
	/** 
	* @author  leijj 
	* 功能： 完善个人资料 
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String complete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setName(ParamInitUtils.getString(request.getParameter("name")));
		user.setNickName(ParamInitUtils.getString(request.getParameter("nickName")));
		user.setPhone(ParamInitUtils.getString(request.getParameter("phone")));
		user.setEmail(ParamInitUtils.getString(request.getParameter("email")));
		user.setCity(ParamInitUtils.getString(request.getParameter("city")));
		user.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		user.setWechat(ParamInitUtils.getString(request.getParameter("wechat")));
		user.setMicroblog(ParamInitUtils.getString(request.getParameter("microblog")));
		user.setJob(ParamInitUtils.getString(request.getParameter("job")));
		
		userManager.update(user);
		return "ok";
	}
	
	private String relAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setIdentityCard(ParamInitUtils.getString(request.getParameter("identityCard")));
		
		String[] bankNums = request.getParameterValues("bankNums");
		String[] openingBanks = request.getParameterValues("openingBanks");
		if(bankNums != null && bankNums.length > 0){
			for(int i = 0; i < bankNums.length ; i++){
				//保存银行卡信息
				Bankcard bankcard = new Bankcard();
				bankcard.setBankName(openingBanks[i]);
				bankcard.setOpeningBank(openingBanks[i]);
				bankcard.setBankNum(bankNums[i]);
				bankcard.setUserId(user.getId());
				bankcard.setUserName(user.getName());
				bankcard.setCreateTime(DateUtils.getTimestamp());
				bankcardManager.save(bankcard);
			}
		}
		//openingBank//开户行
		userManager.update(user);
		return "ok";
	}
	
	private String works(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		WorksManager worksManager = (WorksManager) Context.getBean(WorksManager.class);
		String content = request.getParameter("MSG_Content");
		Works works = new Works();
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
	
	private void InitHeadImg(HttpServletRequest request,HttpServletResponse response, Map model) throws Exception {
		SysSessionUser sessionUser = SessionHelper.getUser(request);
		User user = userManager.getUserByCode(sessionUser.getUserCode());
		
		String headSrc = user.getHead();
		model.put("id", user.getId());
		model.put("headSrc", headSrc);	
		model.put("@VIEWNAME@", "user/userHeadImg");
	}
	
	//上传的文件
	private void uploadHeadImg(HttpServletRequest request,HttpServletResponse response, Map model) throws Exception {
		String newPrefix = SessionHelper.getUserCode(request) + DateUtils.getSysTime();
		
		String newPath = UploadHelper.rootPath(request) + "upload/user/headImg/" + DateUtils.getSysDate();
		
		File img = UploadHelper.upload(request, newPath, newPrefix);
		
		String fileName = img.getName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		String result =newPath + File.separator + fileName.substring(0,fileName.lastIndexOf("."))+".jpg";
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
		
		response.sendRedirect("uploadHeadOk.html?" + result + "," + width + "," + height);
	}
	
	private void saveHeadImg(HttpServletRequest request,HttpServletResponse response, Map model) throws Exception {
		try {
			int width=120;
			//获取缩放和剪切参数
			String cutPos = request.getParameter("cut_pos");
			String[] pos = cutPos.split(",");
			//左上角坐标
			int x = Integer.parseInt(pos[0]);
			int y = Integer.parseInt(pos[1]);
			//缩放后的图片宽度
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
	        User user = userManager.getUserByID(SessionHelper.getUserID(request));
	        user.setHead(destFile);
	        userManager.update(user);
	        destFile = destFile.replaceAll("\\\\", "/");
	        model.put("headSrc", destFile);	
			model.put("@VIEWNAME@", "user/userHeadImg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String tojson(List list, int count, String sEcho)     {  
			String json = null; // 返回的		json数据    
			String aaData=JSONArray.fromObject(list).toString();     
			json =  "{\"sEcho\":"+sEcho+",\"iTotalRecords\":"+count+",\"iTotalDisplayRecords\":"+count+",\"data\":"+aaData+"}";      
			return json;     
	}
}
