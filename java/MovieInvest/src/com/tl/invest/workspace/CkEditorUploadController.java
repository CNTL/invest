package com.tl.invest.workspace;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.tl.common.StringUtils;
import com.tl.common.UploadHelper;
import com.tl.kernel.web.BaseController;
import com.tl.sys.common.SessionHelper;

public class CkEditorUploadController extends BaseController {

	@SuppressWarnings("rawtypes")
	@Override
	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Map model) throws Exception {
		
		Map<String,Object> map=new HashMap<String,Object>();
		boolean success = true;
		String errorMsg = "上传失败";
		String folder = get(request, "folder", "upload/CKEditor");
		int maxSize = getInt(request, "maxsize", 2);
		String fileName = "";
		String rltPath = "";
		
		folder = folder.replaceAll("\\|", "/");
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		InputStream in;
		PrintWriter out = response.getWriter();
		try {
			FileItem file = null; // 上传的文件
			List items = upload.parseRequest(request);// 获取所有上传的项
			for (int i = 0; i < items.size(); i++) {
				file = (FileItem) items.get(i);				
			}
			folder = File.separator.equals("\\") ? folder.replaceAll("/", "\\\\") : folder.replaceAll("\\\\", "/");
			for (int i = 0; i < items.size(); i++) {
				file = (FileItem) items.get(i);
				if (!file.isFormField()) {
					String fileExt = "";
					fileName = file.getName();
					
					String contentType = file.getContentType();
					if ((contentType.equals("image/pjpeg") || contentType.equals("image/jpeg"))  
			                && fileName.substring(fileName.length() - 4).toLowerCase().equals(".jpg")) {
						
			        }else if(contentType.equals("image/png") && fileName.substring(fileName.length() - 4).toLowerCase().equals(".png")){  
			              
			        }else if(contentType.equals("image/gif") && fileName.substring(fileName.length() - 4).toLowerCase().equals(".gif")){  
			              
			        }else if(contentType.equals("image/bmp") && fileName.substring(fileName.length() - 4).toLowerCase().equals(".bmp")){  
			              
			        }else{  
			        	out.println("<font color=\"red\" size=\"2\">*文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）</font>");
			        	return;
			        } 
					long size = file.getSize();
					if(size > maxSize * 1024*1024){
						out.println("<font color=\"red\" size=\"2\">*文件大小不得大于"+maxSize+"M</font>");  
			            return;  
					}
					
					Date dNow = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyy");// 保存目录名称
					SimpleDateFormat format1 = new SimpleDateFormat("MM-dd");// 保存目录名称
					SimpleDateFormat format3 = new SimpleDateFormat("HHmmssss");// 文件名称
					StringBuilder sb = new StringBuilder();
					sb.append(StringUtils.isEmpty(folder)?"":(folder+File.separator));
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
					}else {
						CreateFolders(UploadHelper.rootPath(request)+folderVPath);//创建目录
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
		if(!success){
			out.println("<font color=\"red\" size=\"2\">"+errorMsg+"</font>");
		}else {
			String appPath = "";
			String strContextPath = request.getContextPath();
			if ( strContextPath != null && !strContextPath.equals( "/" ) )
				appPath = strContextPath + "/";
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + get(request, "CKEditorFuncNum", "") + ",'" + appPath+rltPath.replaceAll("\\\\", "/") + "','')");
			out.println("</script>");
			return;
		}
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
