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
import com.tl.invest.constant.JobSection;
import com.tl.invest.constant.RecruitCat;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;
import com.tl.kernel.sys.dic.DictionaryType;


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
		List<String> earStage = new ArrayList<String>();//前期拍摄
		List<String> laterStage = new ArrayList<String>();//后期制作
		List<String> other = new ArrayList<String>();//其他
		try {
			Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_RECRUIT_TYPE.typeID());
			if(jobTypes != null && jobTypes.length > 0){
				for (Dictionary job : jobTypes) {
					if(JobSection.SECTION_FOWARD.typeName().equals(job.getName())){
						Dictionary[] subJobTypes = dicReader.getSubDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), job.getId());
						if(subJobTypes != null && subJobTypes.length > 0){
							for (Dictionary subJob : subJobTypes) {
								if(JobSection.SECTION_ACTOR.typeName().equals(subJob.getName())){
									actor.add(subJob.getName());
								} else {
									earStage.add(subJob.getName());
								}
							}
						}
					} else if(JobSection.SECTION_AFTER.typeName().equals(job.getName())){
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
		
		pMap.put(JobSection.SECTION_ACTOR.typeName(), actor);
		pMap.put(JobSection.SECTION_FOWARD.typeName(), earStage);
		pMap.put(JobSection.SECTION_AFTER.typeName(), laterStage);
		pMap.put(JobSection.SECTION_OTHER.typeName(), other);
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
	
	/** 获得职业类型
	 * @param firsttype
	 * @param secondtype
	 * @return
	 * @throws TLException
	 */
	public static int getPreJob(int firsttype,int secondtype) throws TLException {
		
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		Dictionary dicFirst = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), firsttype);
		Dictionary dicSecond = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), secondtype);
		
		//职业分类
		 //演员
		Dictionary dic1 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), JobSection.SECTION_ACTOR.typeName());
		//前期拍摄
		Dictionary dic2 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), JobSection.SECTION_FOWARD.typeName());
		//后期制作
		Dictionary dic3 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), JobSection.SECTION_AFTER.typeName());
		//其他影人
		Dictionary dic4 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), JobSection.SECTION_OTHER.typeName());
		
		if(dicFirst.getName().equals(JobSection.SECTION_FOWARD.typeName())){
			 if(dicSecond.getName().equals(JobSection.SECTION_ACTOR.typeName())){
				 return dic1.getId();
			 }
			 else{
				 return dic2.getId();
			 }
		}
		else if(dicFirst.getName().equals(JobSection.SECTION_AFTER.typeName())){
			return dic3.getId();
		}
		else {
			return dic4.getId();
		}
	}
	
	/**根据影人分（演员、前期拍摄、后期制作、其他影人）获取对应的影人分类
	 * @return
	 * @throws TLException
	 */
	public static List<Dictionary> getTypeCats(int typeid) throws TLException{
		
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		List<Dictionary> list = new ArrayList<Dictionary>();
		if(typeid == JobSection.SECTION_ACTOR.typeID()){
			
		}
		if(typeid == JobSection.SECTION_FOWARD.typeID()){
			Dictionary dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_FOWARD.typeID());
			Dictionary[] dics =dicReader.getChildrenDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_FOWARD.typeID());

			dic.setSubDics(dics);
			list.add(dic);
		}
		if(typeid == JobSection.SECTION_AFTER.typeID()){
			Dictionary dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_AFTER.typeID());
			Dictionary[] dics =dicReader.getChildrenDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_AFTER.typeID());

			dic.setSubDics(dics);
			list.add(dic);
		}
		if(typeid == JobSection.SECTION_OTHER.typeID()){
			Dictionary dic = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_PUB.typeID());
			Dictionary[] dics =dicReader.getChildrenDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_PUB.typeID());

			dic.setSubDics(dics);
			list.add(dic);
			
			Dictionary dic2 = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_CARTOON.typeID());
			Dictionary[] dics2 =dicReader.getChildrenDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), RecruitCat.REC_CARTOON.typeID());

			dic2.setSubDics(dics2);
			list.add(dic2);
		}
		
		return list;
	}
}