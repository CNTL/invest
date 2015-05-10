package com.tl.invest.user.photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.tl.common.DateUtils;
import com.tl.common.ImageUtils;
import com.tl.common.ParamInitUtils;
import com.tl.common.StringUtils;
import com.tl.common.UploadHelper;
import com.tl.common.WebUtil;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014年11月14日 下午2:02:54 
 * @author  leijj
 * 类说明 ： 招聘控制类
 */
/**
 * @author wang.yq
 *
 */
@SuppressWarnings({"rawtypes"})
public class PhotoController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getPhotos".equals(action)){
			//获取某图册下的所有图片
			getPhotos(request, response);
		} else if("getPhotoGroups".equals(action)){
			//获取用户图册
			getPhotoGroups(request, response);
		} else if("savePhotoGroup".equals(action)){
			//保存图册信息
			savePhotoGroup(request, response);
		} else if("uploadPhoto".equals(action)){
			//上传图片
			uploadPhoto(request, response);
		} else if("getGroupInfo".equals(action)){
			//获取图册组图信息
			getGroupInfo(request, response, model);
		} else if("delPhotoGroup".equals(action)){
			//删除图册组图信息
			delPhotoGroup(request, response, model);
		} else if("updatePhoto".equals(action)){
			//修改图片信息
			updatePhoto(request, response, model);
		} else if("delPhoto".equals(action)){
			//删除图片信息
			delPhoto(request, response, model);
		} else if("getPhotoInfo".equals(action)){
			//获取图片信息
			getPhotoInfo(request, response, model);
		} 
		else if("saveCorpImage".equals(action)){
			//保存剪裁后的图片
			saveCorpImage(request, response);
		} 
	}
	
	/** 保存剪裁后的图片
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void saveCorpImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
	 
		String fileWebroot =  WebUtil.getPath(request);
		
		String imageRotate = request.getParameter("imageRotate");//图片的翻转角度
		String viewPortW = request.getParameter("viewPortW");//可见区宽
		String viewPortH = request.getParameter("viewPortH");//可见区高

		String imageSource = request.getParameter("imageSource");//图片源
		imageSource = imageSource.substring(imageSource.indexOf("upload"), imageSource.length());
		String webImageDest = imageSource;
		imageSource = imageSource.replaceAll("//", "\\");
		String imageX = request.getParameter("imageX");//图片X位置
		String imageY = request.getParameter("imageY");//图片Y位置
		String imageW = request.getParameter("imageW");//图片宽
		String imageH = request.getParameter("imageH");//图片高

		String selectorX = request.getParameter("selectorX");//选中区位置X
		String selectorY = request.getParameter("selectorY");//选中区位置Y
		String selectorW = request.getParameter("selectorW");//选中区位置宽
		String selectorH = request.getParameter("selectorH");//选中区位置高

//		System.out.println("viewPortW = " + viewPortW);
//		System.out.println("viewPortH = " + viewPortH);
//		System.out.println("imageSource = " + imageSource);
//		System.out.println("imageW = " + imageW);
//		System.out.println("imageH = " + imageH);
//		System.out.println("imageX = " + imageX);
//		System.out.println("imageY = " + imageY);
//		System.out.println("imageRotate = " + imageRotate);
//		System.out.println("selectorX = " + selectorX);
//		System.out.println("selectorY = " + selectorY);
//		System.out.println("selectorW = " + selectorW);
//		System.out.println("selectorH= " + selectorH);
//		System.out.println("fileWebroot = " + fileWebroot);
		
//		String suffix = imageSource
//				.substring(imageSource.lastIndexOf(".") + 1);
//		String imageDest = imageSource.substring(0, imageSource.lastIndexOf("."))+"head."
//				+ suffix;
		//替换原图
		String imageDest = imageSource;
//		System.out.println("imageDest = " + imageDest);
		 
		ImageUtils.cropzoom(fileWebroot + imageSource, fileWebroot
				+ imageDest, imageRotate, viewPortW, viewPortH, imageX,
				imageY, imageW, imageH, selectorX, selectorY, selectorW,
				selectorH);

		File cutImgfile = new File(fileWebroot + imageDest);
		
		if (cutImgfile.exists()) {
			
			output(webImageDest+"?time="+DateUtils.getSysTime(), response);
		} else {
			output(imageSource, response);
		}
	}
	
	/** 
	* @author  leijj 
	* 功能： 根据相册id获取相册照片
	* @param request
	* @param response
	* @throws Exception 
	*/ 
	private void getPhotos(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int groupID = getInt(request, "groupID");
		List<UserPhoto> photos = photoManager.getPhotos(groupID);
		JSONArray jsonArray = JSONArray.fromObject(photos);  
		output(jsonArray.toString(), response);
	}
	
	private void getPhotoGroups(HttpServletRequest request, HttpServletResponse response) throws Exception{
		int userID = getInt(request, "userID");
		if(userID == 0){
			User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
			if(user != null){
				userID = user.getId();
			}
		}
		if(userID == 0) return;
		List<UserPhotogroup> photogroups = photoManager.getPhotoGroups(userID);
		JSONArray jsonArray = JSONArray.fromObject(photogroups);  
		output(jsonArray.toString(), response);
	}
	/** 
	* @author  leijj 
	* 功能： 保存图册信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void savePhotoGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserPhotogroup photogroup = new UserPhotogroup();
		if(id > 0) photogroup = photoManager.getGroupInfo(id);
		
		photogroup.setUserId(user.getId());
		photogroup.setUserName(user.getName());
		photogroup.setGroupName(ParamInitUtils.getString(request.getParameter("groupName")));
		photogroup.setGroupIntro(ParamInitUtils.getString(request.getParameter("groupIntro")));
		photogroup.setGroupPhoto(ParamInitUtils.getString(request.getParameter("groupPhoto")));
		photogroup.setCreateTime(DateUtils.getTimestamp());
		id = photoManager.savePhotoGroup(photogroup);
		output(String.valueOf(id), response);
	}
	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success = true;
		String errorMsg = "上传失败";
		String folder = "";
		String fileName = "";
		String rltPath = "";

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		InputStream in;
		try {
			FileItem file = null; // 上传的文件
			List items = upload.parseRequest(request);// 获取所有上传的项
			for (int i = 0; i < items.size(); i++) {
				file = (FileItem) items.get(i);
				if (file.isFormField()) {
					String fieldName = file.getFieldName();
					if(fieldName.equalsIgnoreCase("folder")){
						folder = file.getString("UTF-8");
						folder = StringUtils.isEmpty(folder)?"":folder;
					}
				}
			}
			folder = File.separator.equals("\\") ? folder.replaceAll("/", "\\\\") : folder.replaceAll("\\\\", "/");
			for (int i = 0; i < items.size(); i++) {
				file = (FileItem) items.get(i);
				if (!file.isFormField()) {
					String fileExt = "";
					fileName = file.getName();
					//long size = file.getSize();
					Date dNow = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyy");// 保存目录名称
					SimpleDateFormat format1 = new SimpleDateFormat("MM-dd");// 保存目录名称
					SimpleDateFormat format3 = new SimpleDateFormat("HHmmssss");// 文件名称
					StringBuilder sb = new StringBuilder();
					sb.append(StringUtils.isEmpty(folder)?"":(folder+File.separator));
					sb.append("upload\\photo\\");
					sb.append(user == null ? "" : user.getCode());
					sb.append("\\");
					sb.append(format.format(dNow));// 当前日期
					sb.append(File.separator+format1.format(dNow));
					String folderVPath = sb.toString();
					sb.append(File.separator + format3.format(dNow));
					sb.append("_"+SessionHelper.getUserCode(request));// 当前用户
					if (fileName.lastIndexOf(".") >= 0) {
						fileExt = fileName.substring(fileName.lastIndexOf("."));
					}
					sb.append(fileExt);
					rltPath = sb.toString();
					
					in = file.getInputStream();// 当前输入流
					String filePath = UploadHelper.rootPath(request)+rltPath;
					File nfile = new File(filePath);
					if(nfile.exists()){
						success = false;
						errorMsg = "文件已经存在"+rltPath;
					} else {
						String folderPath = UploadHelper.rootPath(request)+folderVPath;
						CreateFolders(folderPath);//创建目录
						nfile.createNewFile();//创建文件
						
						OutputStream  outputStream = new FileOutputStream(filePath);
						int byteCount = -1;
						byte[] bytes = new byte[4096];
						while ((byteCount = in.read(bytes,0,bytes.length)) != -1){
							outputStream.write(bytes, 0, byteCount);
						}
						in.close();
						outputStream.close();
						errorMsg = "上传成功";
						rltPath = rltPath.replace("\\", "/");
						savePhoto(request, response, user, rltPath);
					}
				}
			}
		} catch (Exception e1) {
			success = false ;
			errorMsg = "添加附件失败:"+e1.getMessage();
		}
		
		map.put("success", success);
		map.put("path", rltPath);
		map.put("msg", errorMsg);
		
		JSONObject json=JSONObject.fromObject(map);
		output(json.toString(), response);
	}
	private void savePhoto(HttpServletRequest request, HttpServletResponse response, User user, String photoPath) throws Exception{
		int groupID = getInt(request, "groupID");
		String photoName = photoPath.substring(photoPath.lastIndexOf("\\") + 1, photoPath.lastIndexOf("."));
		UserPhoto photo = new UserPhoto();
		photo.setGroupId(groupID);
		//photo.setGroupName(groupName);
		photo.setUserId(user.getId());
		photo.setUserName(user.getName());
		photo.setCreateTime(DateUtils.getTimestamp());
		photo.setIntro("");
		photo.setPhoto(photoPath);
		photo.setPhotoName(photoName);
		photoManager.savePhoto(photo);
	}
	private void CreateFolders(String folders) {
        StringTokenizer st = new StringTokenizer(folders, File.separator);
        StringBuilder sb = new StringBuilder();
        String osname = System.getProperty("os.name");
        if (osname.compareToIgnoreCase("linux") == 0)
            sb.append(File.separator);
 
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            File file = new File(sb.toString());
            if (!file.exists())
                file.mkdir();
            sb.append(File.separator);
        }
 
    }
	private void getGroupInfo(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserPhotogroup group = photoManager.getGroupInfo(id);
		JSONObject json = JSONObject.fromObject(group);
		output(json.toString(), response);
	}
	private void delPhotoGroup(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		photoManager.delPhotoGroup(id);
		photoManager.delPhotoByGroup(id);
		output("ok", response);
	}
	/** 
	* @author  leijj 
	* 功能： 保存图册信息
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void updatePhoto(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserPhoto photo = new UserPhoto();
		if(id > 0) photo = photoManager.getPhotoInfo(id);
		photo.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		id = photoManager.savePhoto(photo);
		output(String.valueOf(id), response);
	}
	private void getPhotoInfo(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserPhoto photo = new UserPhoto();
		if(id > 0) photo = photoManager.getPhotoInfo(id);
		JSONObject json = JSONObject.fromObject(photo);
		output(json.toString(), response);
	}
	private void delPhoto(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		photoManager.delPhotoById(id);
		output("ok", response);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private PhotoManager photoManager = (PhotoManager)Context.getBean(PhotoManager.class);
}