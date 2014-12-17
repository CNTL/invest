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
import com.tl.common.WebUtil;
import com.tl.invest.user.user.User;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��11��14�� ����2:02:54 
 * @author  leijj
 * ��˵�� �� ��Ƹ������
 */
@SuppressWarnings({"rawtypes"})
public class PhotoController extends BaseController {
	protected void handle(HttpServletRequest request, HttpServletResponse response, Map model) throws Exception {
		String action = request.getParameter("a");
		if("getPhotos".equals(action)){//��ȡ�û��б�
			getPhotos(request, response);
		} else if("getPhotoGroups".equals(action)){//��ȡ�û��б�
			getPhotoGroups(request, response);
		} else if("savePhotoGroup".equals(action)){//���������Ϣ
			savePhotoGroup(request, response);
		} else if("uploadPhoto".equals(action)){//�ϴ����ͷͼ
			uploadPhoto(request, response);
		} 
		
	}
	/** 
	* @author  leijj 
	* ���ܣ� �������id��ȡ�����Ƭ
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
	* ���ܣ� ����ͼ����Ϣ
	* @param request
	* @param response
	* @param model
	* @throws Exception 
	*/ 
	private void savePhotoGroup(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		UserPhotogroup photogroup = new UserPhotogroup();
		photogroup.setUserId(user.getId());
		photogroup.setUserName(user.getName());
		photogroup.setGroupName(ParamInitUtils.getString(request.getParameter("groupName")));
		photogroup.setGroupIntro(ParamInitUtils.getString(request.getParameter("groupIntro")));
		photogroup.setGroupPhoto(ParamInitUtils.getString(request.getParameter("groupPhoto")));
		photogroup.setCreateTime(DateUtils.getTimestamp());
		int id = photoManager.savePhotoGroup(photogroup);
		output(String.valueOf(id), response);
	}
	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success = true;
		String errorMsg = "�ϴ�ʧ��";
		String folder = "";
		String fileName = "";
		String rltPath = "";

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		InputStream in;
		try {
			FileItem file = null; // �ϴ����ļ�
			List items = upload.parseRequest(request);// ��ȡ�����ϴ�����
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
					SimpleDateFormat format = new SimpleDateFormat("yyyy");// ����Ŀ¼����
					SimpleDateFormat format1 = new SimpleDateFormat("MM-dd");// ����Ŀ¼����
					SimpleDateFormat format3 = new SimpleDateFormat("HHmmssss");// �ļ�����
					StringBuilder sb = new StringBuilder();
					sb.append(StringUtils.isEmpty(folder)?"":(folder+File.separator));
					sb.append("upload\\photo\\");
					sb.append(user.getCode());
					sb.append("\\");
					sb.append(format.format(dNow));// ��ǰ����
					sb.append(File.separator+format1.format(dNow));
					String folderVPath = sb.toString();
					sb.append(File.separator + format3.format(dNow));
					sb.append("_"+SessionHelper.getUserCode(request));// ��ǰ�û�
					if (fileName.lastIndexOf(".") >= 0) {
						fileExt = fileName.substring(fileName.lastIndexOf("."));
					}
					sb.append(fileExt);
					rltPath = sb.toString();
					
					in = file.getInputStream();// ��ǰ������
					String filePath = UploadHelper.rootPath(request)+rltPath;
					File nfile = new File(filePath);
					if(nfile.exists()){
						success = false;
						errorMsg = "�ļ��Ѿ�����"+rltPath;
					} else {
						String folderPath = UploadHelper.rootPath(request)+folderVPath;
						CreateFolders(folderPath);//����Ŀ¼
						nfile.createNewFile();//�����ļ�
						
						OutputStream  outputStream = new FileOutputStream(filePath);
						int byteCount = -1;
						byte[] bytes = new byte[4096];
						while ((byteCount = in.read(bytes,0,bytes.length)) != -1){
							outputStream.write(bytes, 0, byteCount);
						}
						in.close();
						outputStream.close();
						errorMsg = "�ϴ��ɹ�";
						savePhoto(request, response, user, WebUtil.getRoot(request) + rltPath);
					}
				}
			}
		} catch (Exception e1) {
			success = false ;
			errorMsg = "���Ӹ���ʧ��:"+e1.getMessage();
		}
		
		map.put("success", success);
		map.put("path", rltPath);
		map.put("msg", errorMsg);
		
		JSONObject json=JSONObject.fromObject(map);
		output(json.toString(), response);
	}
	private void savePhoto(HttpServletRequest request, HttpServletResponse response, User user, String photoPath) throws Exception{
		int groupID = getInt(request, "groupID");
		UserPhoto photo = new UserPhoto();
		photo.setGroupId(groupID);
		//photo.setGroupName(groupName);
		photo.setUserId(user.getId());
		photo.setUserName(user.getName());
		photo.setCreateTime(DateUtils.getTimestamp());
		photo.setIntro("");
		photo.setPhoto(photoPath);
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
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
	private PhotoManager photoManager = (PhotoManager)Context.getBean(PhotoManager.class);
}