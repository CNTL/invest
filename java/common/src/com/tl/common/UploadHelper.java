package com.tl.common;
/** 
 * @created 2014年9月6日 下午10:07:13 
 * @author  leijj
 * 类说明 ： 
 */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件上传辅助类
 * @author Gong Lijie
 * 2014-7-7
 */
@SuppressWarnings("unchecked")
public class UploadHelper {
	public static String rootPath(HttpServletRequest request) throws Exception {
		return request.getSession().getServletContext().getRealPath("/");
	}
	/**
	 * 文件上传，指定在服务器端保存的路径。文件名不改变
	 * @param request
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static File upload(HttpServletRequest request, String path) throws Exception {
		return upload(request, path, "");
	}
	
	/**
	 * 文件上传，指定在服务器端保存的路径，文件名增加前缀
	 * @param request
	 * @param path
	 * @param prefix
	 * @return
	 * @throws Exception
	 */
	public static File upload(HttpServletRequest request, String path, String prefix) throws Exception {
		FileItem file = getFileItem(request);
		
		String fileName = file.getName();
		String fn = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		
		return writeFile(file, path + File.separator + prefix + fn);
	}
	
	/**
	 * 文件上传，指定在服务器端保存的路径，文件保存为指定的名称
	 * @param request
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static File uploadNewName(HttpServletRequest request, String path, String fileName) throws Exception {
		FileItem file = getFileItem(request);
		
		return writeFile(file, path + File.separator + fileName);
	}
	
	//从request中读出上传的文件
	private static FileItem getFileItem(HttpServletRequest request) throws FileUploadException {
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		upload.setHeaderEncoding("UTF-8");
		upload.setSizeMax(10000000);

		List<FileItem> items = upload.parseRequest(request);
		FileItem file = (FileItem) items.get(0);
		return file;
	}
	
	//保存文件
	private static File writeFile(FileItem file, String name) throws IOException {
		File newFile = new File(name);
		InputStream in = null;
		try {
	    	if(!newFile.getParentFile().exists())
	    	{
	    		newFile.getParentFile().mkdirs();
	    	}
			in = file.getInputStream();
			FileUtils.writeFile(in, newFile);
		} finally {
			ResourceMgr.closeQuietly(in);
		}
		return newFile;
	}
	
	//保存文件
	public static File writeFile(InputStream in, String name) throws IOException {
		File newFile = new File(name);
		try {
	    	if(!newFile.getParentFile().exists())
	    	{
	    		newFile.getParentFile().mkdirs();
	    	}
			FileUtils.writeFile(in, newFile);
		} finally {
			ResourceMgr.closeQuietly(in);
		}
		return newFile;
	}
	
	//bmp、gif、png转换为jpg
	public static void ImgConvert(File file, String fileExt, String result) throws Exception {
		if(fileExt.equals("bmp")) {
	    	Image img = ImageIO.read(file);  
	    	BufferedImage tag = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);  
	    	tag.getGraphics().drawImage(img.getScaledInstance(img.getWidth(null), img.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);  
	    	//FileOutputStream out = new FileOutputStream(result);  
	    	// JPEGImageEncoder可适用于其他图片类型的转换  
	    	//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
	    	//encoder.encode(tag);  
	    	//out.close(); 	    	
			ImageIO.write(tag, /*"GIF"*/ fileExt /* format desired */ , new File(result) /* target */ );
		} else {  
			file.canRead();  
	        BufferedImage src = ImageIO.read(file);  
	        ImageIO.write(src, fileExt, new File(result)); 
		}
	}
}