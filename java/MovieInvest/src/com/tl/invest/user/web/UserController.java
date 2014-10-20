package com.tl.invest.user.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.FileType;
import com.tl.common.DateUtils;
import com.tl.common.HeadImage;
import com.tl.common.ImageHelper;
import com.tl.common.ParamInitUtils;
import com.tl.common.UploadHelper;
import com.tl.common.UserEncrypt;
import com.tl.invest.user.Bankcard;
import com.tl.invest.user.BankcardManager;
import com.tl.invest.user.User;
import com.tl.invest.user.UserManager;
import com.tl.invest.user.Works;
import com.tl.invest.user.WorksManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.DAOHelper;
import com.tl.kernel.web.BaseController;
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
		} else if("findLogin".equals(action)){//查找登录用户信息
			findLogin(request, response);
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
			String json = saveHeadImg(request);
			output(json, response);
		} else if ("uploadAtt".equals(action)) {
			String json = upload(request);
			output(json, response);
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
	* 功能： 查找登录用户信息
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void findLogin(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		List<Bankcard> bankcards = bankcardManager.getByUserId(user.getId());
		if(bankcards != null && bankcards.size() > 0) 
			user.setBankcards(bankcards);
		
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
		user.setNickName(ParamInitUtils.getString(request.getParameter("nickName")));
		user.setEmail(ParamInitUtils.getString(request.getParameter("email")));
		user.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		user.setPostAddr(ParamInitUtils.getString(request.getParameter("postAddr")));
		
		userManager.update(user);
		return "ok";
	}
	
	/** 
	* @author  leijj 
	* 功能： 实名认证
	* @param request
	* @param response
	* @return
	* @throws Exception 
	*/ 
	private String relAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		user.setName(ParamInitUtils.getString(request.getParameter("name")));//姓名
		user.setType(ParamInitUtils.getString(request.getParameter("type")));//注册类型
		user.setTypeId(ParamInitUtils.getInt(request.getParameter("TypeId")));
		user.setProvince(ParamInitUtils.getString(request.getParameter("province")));//所在地
		user.setCity(ParamInitUtils.getString(request.getParameter("city")));//所在地
		user.setJob(ParamInitUtils.getString(request.getParameter("job")));//职业
		user.setPhone(ParamInitUtils.getString(request.getParameter("phone")));//手机号
		user.setIdentityCard(ParamInitUtils.getString(request.getParameter("identityCard")));//身份证
		user.setOrganization(ParamInitUtils.getString(request.getParameter("organization")));//企业组织机构证件照
		user.setBusinessLicense(ParamInitUtils.getString(request.getParameter("businessLicense")));//企业营业执照扫描件
		
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
	
	//上传的文件
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
		
		response.sendRedirect("uploadHeadOk.html?" + result + "," + width + "," + height);
	}
	
	private String saveHeadImg(HttpServletRequest request) throws Exception {
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
			return "上传头像失败:" + e.getMessage();
		}
	}
	private String upload(HttpServletRequest request){
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;
		FileItemStream fileStream = null;
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }
        
		try {
			FileItemIterator iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				fileStream = iterator.next();
				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return null;
			}
			String field = request.getParameter("field");
			String savePath =  UploadHelper.rootPath(request) + "upload/user/relAuth/" + DateUtils.getSysDate() + "/" + 
					field + "_" + DateUtils.getSysTime();
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
			savePath = savePath + suffix;
			savePath = PathFormat.parse(savePath, originFileName);

			InputStream is = fileStream.openStream();
			UploadHelper.writeFile(is, savePath);
			return savePath.substring(UploadHelper.rootPath(request).length(), savePath.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String tojson(List list, int count, String sEcho)     {  
			String json = null; // 返回的		json数据    
			String aaData=JSONArray.fromObject(list).toString();     
			json =  "{\"sEcho\":"+sEcho+",\"iTotalRecords\":"+count+",\"iTotalDisplayRecords\":"+count+",\"data\":"+aaData+"}";      
			return json;     
	}
}
