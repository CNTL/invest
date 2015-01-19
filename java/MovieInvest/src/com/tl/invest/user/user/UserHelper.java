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
 * @created 2014��11��2�� ����11:47:44 
 * @author  leijj
 * ��˵�� �� 
 */
public class UserHelper {
	public static Map<String, List<String>> pMap = new HashMap<String, List<String>>();
	static {
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		List<String> actor = new ArrayList<String>();//��Ա
		List<String> earStage = new ArrayList<String>();//ǰ������
		List<String> laterStage = new ArrayList<String>();//��������
		List<String> other = new ArrayList<String>();//����
		try {
			Dictionary[] jobTypes = dicReader.getDics(DicTypes.DIC_RECRUIT_TYPE.typeID());
			if(jobTypes != null && jobTypes.length > 0){
				for (Dictionary job : jobTypes) {
					if("ǰ������".equals(job.getName())){
						Dictionary[] subJobTypes = dicReader.getSubDics(DicTypes.DIC_RECRUIT_TYPE.typeID(), job.getId());
						if(subJobTypes != null && subJobTypes.length > 0){
							for (Dictionary subJob : subJobTypes) {
								if("��Ա".equals(subJob.getName())){
									actor.add(subJob.getName());
								} else {
									earStage.add(subJob.getName());
								}
							}
						}
					} else if("��������".equals(job.getName())){
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
		
		pMap.put("��Ա", actor);
		pMap.put("ǰ������", earStage);
		pMap.put("��������", laterStage);
		pMap.put("����Ӱ��", other);
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
	
	/** ���ְҵ����
	 * @param firsttype
	 * @param secondtype
	 * @return
	 * @throws TLException
	 */
	public static int getPreJob(int firsttype,int secondtype) throws TLException {
		
		DictionaryReader dicReader = (DictionaryReader) Context.getBean(DictionaryReader.class);
		Dictionary dicFirst = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), firsttype);
		Dictionary dicSecond = dicReader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(), secondtype);
		
		//ְҵ����
		 //��Ա
		Dictionary dic1 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), "��Ա");
		//ǰ������
		Dictionary dic2 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), "ǰ������");
		//��������
		Dictionary dic3 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), "��������");
		//����Ӱ��
		Dictionary dic4 = dicReader.getDicByName(DicTypes.DIC_JOB_TYPE.typeID(), "����Ӱ��");
		
		if(dicFirst.getName().equals("ǰ������")){
			 if(dicSecond.getName().equals("��Ա")){
				 return dic1.getId();
			 }
			 else{
				 return dic2.getId();
			 }
		}
		else if(dicFirst.getName().equals("��������")){
			return dic3.getId();
		}
		else {
			return dic4.getId();
		}
	}
}