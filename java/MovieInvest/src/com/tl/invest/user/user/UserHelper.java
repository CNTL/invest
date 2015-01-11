package com.tl.invest.user.user;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.baidu.ueditor.define.FileType;
import com.tl.common.DateUtils;
import com.tl.common.PathFormat;
import com.tl.common.UploadHelper;

/** 
 * @created 2014年11月2日 下午11:47:44 
 * @author  leijj
 * 类说明 ： 
 */
public class UserHelper {
	public static Map<String, List<String>> pMap = new HashMap<String, List<String>>();
	static {
		List<String> actor = new ArrayList<String>();
		actor.add("演员");
		pMap.put("演员", actor);
		
		List<String> earStage = new ArrayList<String>();
		earStage.add("导演");
		earStage.add("制片人");
		pMap.put("前期制作", earStage);
		
		List<String> laterStage = new ArrayList<String>();
		laterStage.add("动画");
		pMap.put("后期制作", laterStage);
		
		List<String> other = new ArrayList<String>();
		other.add("其他");
		pMap.put("其他影人", other);
	}

	public static String saveAffix(HttpServletRequest request, String prefix){
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
			String savePath =  UploadHelper.rootPath(request) + prefix + DateUtils.getSysDate() + "/" + 
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
	
	public static List<String> personTypeList(String key){
		return pMap.get(key);
	}
	
	public boolean isPType(String key, String value) {
		List<String> ptList = pMap.get(key);
		if(ptList == null || ptList.size() == 0) return false;
		for(String pt : ptList){
			if(pt.equals(value)){
				return true;
			}
		}
		return false;
	}
}