package com.tl.invest.user.photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.tl.common.ParamInitUtils;
import com.tl.common.StringUtils;
import com.tl.common.UploadHelper;
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
@SuppressWarnings({"rawtypes"})
public class PhotoController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getPhotos".equals(action)){//获取用户列表
			getPhotos(request, response);
		} else if("getPhotoGroups".equals(action)){//获取用户列表
			getPhotoGroups(request, response);
		} else if("savePhotoGroup".equals(action)){//保存相册信息
			savePhotoGroup(request, response);
		} else if("uploadPhoto".equals(action)){//上传相册头图
			uploadPhoto(request, response);
		} else if("getGroupInfo".equals(action)){//获取图册组图信息
			getGroupInfo(request, response, model);
		} else if("delPhotoGroup".equals(action)){//删除图册组图信息
			delPhotoGroup(request, response, model);
		} else if("updatePhoto".equals(action)){//删除图片信息
			updatePhoto(request, response, model);
		} else if("delPhoto".equals(action)){//删除图片信息
			delPhoto(request, response, model);
		} else if("getPhotoInfo".equals(action)){//删除图片信息
			getPhotoInfo(request, response, model);
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
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		List<UserPhotogroup> photogroups = photoManager.getPhotoGroups(user.getId());
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
					sb.append(user.getCode());
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
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		int id = ParamInitUtils.getInt(request.getParameter("id"));
		UserPhoto photo = new UserPhoto();
		if(id > 0) photo = photoManager.getPhotoInfo(id);
		
		photo.setUserId(user.getId());
		photo.setUserName(user.getName());
		photo.setGroupName(ParamInitUtils.getString(request.getParameter("groupName")));
		photo.setIntro(ParamInitUtils.getString(request.getParameter("intro")));
		photo.setPhotoName(ParamInitUtils.getString(request.getParameter("photoName")));
		photo.setCreateTime(DateUtils.getTimestamp());
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