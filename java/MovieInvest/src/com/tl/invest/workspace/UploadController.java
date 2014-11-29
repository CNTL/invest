package com.tl.invest.workspace;

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

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.tl.common.StringUtils;
import com.tl.common.UploadHelper;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

public class UploadController extends BaseController {

	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
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
					SimpleDateFormat format1 = new SimpleDateFormat("MM-DD");// ����Ŀ¼����
					SimpleDateFormat format3 = new SimpleDateFormat("HHmmssss");// �ļ�����
					StringBuilder sb = new StringBuilder();
					sb.append(StringUtils.isEmpty(folder)?"":(folder+File.separator));
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
					}else {
						CreateFolders(UploadHelper.rootPath(request)+folderVPath);//����Ŀ¼
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
					}
				}
			}
		} catch (Exception e1) {
			success = false ;
			errorMsg = "��Ӹ���ʧ��:"+e1.getMessage();
		}
		
		map.put("success", success);
		map.put("path", rltPath);
		map.put("msg", errorMsg);
		
		JSONObject json=JSONObject.fromObject(map);
		output(json.toString(), response);
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

}
