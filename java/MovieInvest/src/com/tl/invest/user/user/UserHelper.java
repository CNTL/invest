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
import com.tl.invest.constant.DicTypes;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

/** 
 * @created 2014年11月2日 下午11:47:44 
 * @author  leijj
 * 类说明 ： 
 */
public class UserHelper {
	public static Map<String, List<String>> pMap = new HashMap<String, List<String>>();
	static {
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		List<String> actor = new ArrayList<String>();//演员
		List<String> earStage = new ArrayList<String>();//前期制作
		List<String> laterStage = new ArrayList<String>();//后期制作
		List<String> other = new ArrayList<String>();//其他
		try {
			Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_RECRUIT_TYPE.typeID());
			if(jobTypes != null && jobTypes.length > 0){
				for (Dictionary job : jobTypes) {
					if("前期拍摄".equals(job.getName())){
						Dictionary[] subJobTypes = dicReader.getSubDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), job.getId());
						if(subJobTypes != null && subJobTypes.length > 0){
							for (Dictionary subJob : subJobTypes) {
								if("演员".equals(subJob.getName())){
									actor.add(subJob.getName());
								} else {
									earStage.add(subJob.getName());
								}
							}
						}
					} else if("后期制作".equals(job.getName())){
						Dictionary[] subJobTypes = dicReader.getSubDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), job.getId());
						if(subJobTypes != null && subJobTypes.length > 0){
							for (Dictionary subJob : subJobTypes) {
								laterStage.add(subJob.getName());
							}
						}
					} else {
						Dictionary[] subJobTypes = dicReader.getSubDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), job.getId());
						if(subJobTypes != null && subJobTypes.length > 0){
							for (Dictionary subJob : subJobTypes) {
								other.add(subJob.getName());
							}
						}
					}
				}	
			}
			
		} catch (TLException e) {
			e.printStackTrace();
		}
		
		pMap.put("演员", actor);
		pMap.put("前期制作", earStage);
		pMap.put("后期制作", laterStage);
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