package com.tl.common;
/** 
 * @created 2014��8��29�� ����8:56:05 
 * @author  leijj
 * ��˵�� �� 
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
	public static final String ATTACH_NAME = "�����洢";

	/**
	 * �ϴ��������洢�豸
	 * @param request
	 * @param response
	 * @param savePath
	 * @return
	 * @throws Exception
	 */
	public static List<String> upload(HttpServletRequest request, HttpServletResponse response, String savePath) throws Exception {
		List items = getFileItem(request, response);
		List<String> pathList = new ArrayList<String>();
		FileItem file = null; // �ϴ����ļ�
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
					//��ǰ������
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
			    	formEL.put(fileName, value);//�Ա��ֶθ�ֵ
			    }
		    }
		}
		JSONObject json=JSONObject.fromObject(formEL);
		return json.toString();
	}
	
	/**
	 * ��ȡҳ�洫�������и���
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
	 * �����ϴ�·���ϴ�����
	 * @param savePath
	 * @param is
	 * @throws Exception
	 */
	public static void upload(String savePath, InputStream is) throws Exception{
		if(is == null){
			return;
		}
		//StorageDeviceManager storageDeviceManager = SysFactory.getStorageDeviceManager();
		//storageDeviceManager.write(AttachHelper.ATTACH_NAME, savePath, is);//�ϴ�������ָ��λ��
	}
	
	/**
	 * ��ȡ������ʱ����ʱ���
	 * @return
	 */
	public static String getTimestamp(){
		Date dNow = new Date();
		//����Ŀ¼����
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		//�����ļ����ƺ�׺
		SimpleDateFormat format2 = new SimpleDateFormat("HHmmss");
		
		StringBuilder sb = new StringBuilder();
		//��ǰ����
		sb.append(format.format(dNow));
		//��ǰʱ����
		sb.append(format2.format(dNow));
		return sb.toString();
	}
}