package com.tl.common;
/** 
 * @created 2014年8月29日 上午8:56:05 
 * @author  leijj
 * 类说明 ： 
 */

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
@SuppressWarnings("rawtypes")
public class AttachHelper {
	public static final String ATTACH_NAME = "附件存储";

	/**
	 * 上传附件到存储设备
	 * @param request
	 * @param response
	 * @param savePath
	 * @return
	 * @throws Exception
	 */
	public static List<String> upload(HttpServletRequest request, HttpServletResponse response, String savePath) throws Exception {
		List items = getFileItem(request, response);
		List<String> pathList = new ArrayList<String>();
		FileItem file = null; // 上传的文件
		for (int i = 0; i < items.size(); i++)
		{
			file = (FileItem) items.get(i);
			if(file == null) break;
			String fileName = file.getName();
			if(fileName != null && fileName.length() > 0){
				String fn = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				String realFile = "";
				if(fn != null && fn.length() > 0){
					realFile = getTimestamp() + "_" + fn;
				}
				
				if (!file.isFormField())
				{
					//当前输入流
					InputStream in = file.getInputStream();
					if(in != null){
						upload(savePath + File.separator + realFile, in);
						pathList.add(savePath + File.separator + realFile);
						in.close();
					}
				}
			}
		}
		return pathList;
	}
	
	public static String getFormEL(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List items = getFileItem(request, response);
		if(items == null || items.size() <= 0) return "";
		
		Map<String, Object> formEL = new HashMap<String, Object>();
		Iterator itemIt = items.iterator();
		while (itemIt.hasNext()) {
			FileItem item = (FileItem) itemIt.next();
			if(item == null) break;
			item.getString("UTF-8");
			if (item.isFormField()) {
			    String fileName = item.getFieldName();
			    String value = item.getString("GBK");
			    if(fileName != null && fileName.length() > 0){
			    	formEL.put(fileName, value);//对表单字段赋值
			    }
		    }
		}
		JSONObject json=JSONObject.fromObject(formEL);
		return json.toString();
	}
	
	/**
	 * 获取页面传来的所有附件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static List getFileItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setHeaderEncoding("UTF-8");
			upload.setSizeMax(50000000);
			return upload.parseRequest(request);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据上传路径上传附件
	 * @param savePath
	 * @param is
	 * @throws Exception
	 */
	public static void upload(String savePath, InputStream is) throws Exception{
		if(is == null){
			return;
		}
		//StorageDeviceManager storageDeviceManager = SysFactory.getStorageDeviceManager();
		//storageDeviceManager.write(AttachHelper.ATTACH_NAME, savePath, is);//上传附件到指定位置
	}
	
	/**
	 * 获取年月日时分秒时间戳
	 * @return
	 */
	public static String getTimestamp(){
		Date dNow = new Date();
		//保存目录名称
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		//保存文件名称后缀
		SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");
		
		StringBuilder sb = new StringBuilder();
		//当前日期
		sb.append(format.format(dNow));
		//当前时分秒
		sb.append(format2.format(dNow));
		return sb.toString();
	}
}