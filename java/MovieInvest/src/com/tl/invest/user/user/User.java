package com.tl.invest.user.user;

// Generated Nov 17, 2014 3:11:25 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.tl.common.StringUtils;
import com.tl.invest.constant.DicTypes;
import com.tl.invest.constant.JobSection;
import com.tl.invest.user.bankcard.UserBankcard;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.dic.Dictionary;
import com.tl.kernel.sys.dic.DictionaryReader;

/**
 * User generated by hbm2java
 */
public class User {

	private int id;
	private Integer type;
	private String code;
	private String email;
	private String password;
	private String name;
	private Integer name_show;
	private String identityCard;
	private String head;
	private String intro;
	private Integer intro_show;
	private String background;
	private Integer isRealNameIdent;
	private Timestamp createTime;
	private String qqOpenId;
	private String xlWeiboCode;
	private String perNickName;
	private String perPostAddr;
	private String perPostCode;
	private String province;
	private String provinceName;
	private String city;
	private String cityName;
	private String perJob;
	private String perJobName;
	private String perJobPID;
	private String per4Type;
	private String perPhone;
	private String orgShortname;
	private String orgFullname;
	private String organization;
	private String orgBusinessLicense;
	private String location;
	private String coordinate;
	private String orgNature;
	private String orgTrade;
	private String orgScale;
	private String orgScaleName;
    private String orgHomePage;
	private int deleted;
	private int gender;
//	private Integer firstType;
//	private Integer secondType;
	private String typeName;
	private Integer perOrder;
	private String lastSessionID;
	private Integer point;
	private Integer ageTypeID;
	private String ageTypeName;
	private Integer height;
	private Integer height_show;
	private Integer weight;
	private Integer weight_show;
	private String school;
	private Integer school_show;
	private String professional;
	private Integer professional_show;
	private Integer degreeid;
	private String degree;
	private Integer degree_show;
	
	public String getOrgScaleName() {
		if(StringUtils.isEmpty(this.getOrgScale())){
			return "";
		}
		if(this.getOrgScale().equals("1")){
			return "10人以下";
		}else if(this.getOrgScale().equals("2")){
			return "10-50人";
		}else{
			return "50人以上";
		}
		 
	}
	
	public String getProvinceName() {
		String provinceName = "";
		try {
			 
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
			int typeid = 0;
			try {
				typeid  = Integer.parseInt(this.getProvince(), 10);
			} catch (Exception e) {
				 
			}
			if(typeid>0){
			 
				Dictionary dic = reader.getDic(DicTypes.DIC_AREA.typeID(),typeid);
				if(dic!=null){
					provinceName =  dic.getName();
				}
			}
			 
		} catch (Exception e) {
			return provinceName;
		}
		
		 
		return provinceName;
		 
	}

	public String getCityName() {
		String cityName = "";
		try {
			 
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
			int typeid = 0;
			try {
				typeid  = Integer.parseInt(this.getCity(), 10);
			} catch (Exception e) {
				 
			}
			if(typeid>0){
			 
				Dictionary dic = reader.getDic(DicTypes.DIC_AREA.typeID(),typeid);
				if(dic!=null){
					cityName =  dic.getName();
				}
			}
			 
		} catch (Exception e) {
			return cityName;
		}
		return cityName;
	}

	public String getPerJobPID() {
		//找到所有分类的父ID
		if(this.getType()==1){//机构直接返回0
			return "0";
		}
		StringBuilder sb = new StringBuilder();
		String perJobPID = "";
		try {
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
			String[] typeids = this.perJob.split(",");
			if(typeids!=null && typeids.length>0){
				for (int i = 0; i < typeids.length; i++) {
					int typeid = 0;
					try {
						typeid  = Integer.parseInt(typeids[i], 10);
					} catch (Exception e) {
					}
					if(typeid>0){
					 
						Dictionary dic = reader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(),typeid);
						if(dic!=null){
							String pidString =String.valueOf(dic.getPid());
							//防止重复父ID
							String tempsb = sb.toString();
							int index = tempsb.indexOf(pidString);
							if(index<0){
								sb.append(pidString);
								sb.append(",");
							}
							
						}
					}
				}
			}
			
			 
		} catch (Exception e) {
			
		}
		perJobPID = sb.toString();
		if(perJobPID.endsWith(",")){
			perJobPID = perJobPID.substring(0,perJobPID.length()-1);
		}
		return perJobPID;
	}
	public void setPer4Type(String per4Type){
		this.per4Type =per4Type;
	}
	public String getPer4Type() {
		//获得影人属于演员、前期排石、后期制作、其他影人中的哪一类
		if(getType()==1) return "";
		String per4Type = "";
		//1.获取所有影聘分类的父ID
		String[] pidget = getPerJob().split(",");
		StringBuilder sb = new StringBuilder();
		if(pidget!=null&&pidget.length>0){
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
			
			for (int i = 0; i < pidget.length; i++) {
				Dictionary dic = null;
				Dictionary dicparent = null;
				int type = Integer.parseInt(pidget[i], 10);
				try {
					dic = reader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(),type);
					
					if(dic!=null){
						dicparent = reader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(),dic.getPid());
						//演员
						if(dic.getName().equals(JobSection.SECTION_ACTOR.typeName())){
							sb.append(JobSection.SECTION_ACTOR.typeID());
							sb.append(",");
						}else if(dicparent.getName().equals(JobSection.SECTION_AFTER.typeName())){
							sb.append(JobSection.SECTION_AFTER.typeID());
							sb.append(",");
						}
						else if(dicparent.getName().equals(JobSection.SECTION_FOWARD.typeName())){
							sb.append(JobSection.SECTION_FOWARD.typeID());
							sb.append(",");
						}
						else{
							sb.append(JobSection.SECTION_OTHER.typeID());
							sb.append(",");
						}
					}
				} catch (Exception e) {
					 
				}
			}
		}
		per4Type = sb.toString();
		if(per4Type.endsWith(",")){
			per4Type = per4Type.substring(0,per4Type.length()-1);
		}
		return per4Type;
	}

	public void setPerJobPID(String perJobPID) {
		this.perJobPID = perJobPID;
	}
	public Integer getName_show() {
		return name_show;
	}

	public void setName_show(Integer name_show) {
		this.name_show = name_show;
	}

	public Integer getIntro_show() {
		return intro_show;
	}

	public void setIntro_show(Integer intro_show) {
		this.intro_show = intro_show;
	}

	public Integer getAgeTypeID() {
		return ageTypeID;
	}

	public void setAgeTypeID(Integer ageTypeID) {
		this.ageTypeID = ageTypeID;
		getAgeTypeName();
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHeight_show() {
		return height_show;
	}

	public void setHeight_show(Integer height_show) {
		this.height_show = height_show;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getWeight_show() {
		return weight_show;
	}

	public void setWeight_show(Integer weight_show) {
		this.weight_show = weight_show;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Integer getSchool_show() {
		return school_show;
	}

	public void setSchool_show(Integer school_show) {
		this.school_show = school_show;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public Integer getProfessional_show() {
		return professional_show;
	}

	public void setProfessional_show(Integer professional_show) {
		this.professional_show = professional_show;
	}

	public Integer getDegreeid() {
		return degreeid;
	}

	public void setDegreeid(Integer degreeid) {
		this.degreeid = degreeid;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Integer getDegree_show() {
		return degree_show;
	}

	public void setDegree_show(Integer degree_show) {
		this.degree_show = degree_show;
	}

	public String getAgeTypeName() {
		String ageTypeNameString = "";
		try {
			 
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader"); 
			if(this.getAgeTypeID()>0){
			 
				Dictionary dic = reader.getDic(DicTypes.DIC_AGE_TYPE.typeID(),this.getAgeTypeID());
				if(dic!=null){
					ageTypeNameString =  dic.getName();
				}
			}
			 
		} catch (Exception e) {
			return ageTypeNameString;
		}
		
		 
		return ageTypeNameString;
		 
	}

	public void setPerJobName(String perJobName) {
		this.perJobName = perJobName;
	}

	
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	private Timestamp lastLoginTime;
	
	public Integer getPerOrder() {
		return perOrder;
	}

	public void setPerOrder(Integer order) {
		this.perOrder = order;
	}

	public int getDeleted() {
		return deleted;
		
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	private List<UserBankcard> bankcards;
	public User() {
	}

	public User(int id) {
		this.id = id;
	}

	public User(int id, Integer type, String code, String email,
			String password, String name, String identityCard, String head,
			String intro, String background, Integer isRealNameIdent,
			Timestamp createTime, String qqOpenId, String xlWeiboCode,
			String perNickName, String perPostAddr, String perPostCode,
			String province, String city, String perJob, String perPhone,
			String orgShortname, String orgFullname, String organization,
			String orgBusinessLicense, String location, String orgNature,
			String orgTrade, String orgScale, String orgHomePage) {
		this.id = id;
		this.type = type;
		this.code = code;
		this.email = email;
		this.password = password;
		this.name = name;
		this.identityCard = identityCard;
		this.head = head;
		this.intro = intro;
		this.background = background;
		this.isRealNameIdent = isRealNameIdent;
		this.createTime = createTime;
		this.qqOpenId = qqOpenId;
		this.xlWeiboCode = xlWeiboCode;
		this.perNickName = perNickName;
		this.perPostAddr = perPostAddr;
		this.perPostCode = perPostCode;
		this.province = province;
		this.city = city;
		this.perJob = perJob;
		this.perPhone = perPhone;
		this.orgShortname = orgShortname;
		this.orgFullname = orgFullname;
		this.organization = organization;
		this.orgBusinessLicense = orgBusinessLicense;
		this.location = location;
		this.orgNature = orgNature;
		this.orgTrade = orgTrade;
		this.orgScale = orgScale;
		this.orgHomePage = orgHomePage;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getBackground() {
		return this.background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public Integer getIsRealNameIdent() {
		return this.isRealNameIdent;
	}

	public void setIsRealNameIdent(Integer isRealNameIdent) {
		this.isRealNameIdent = isRealNameIdent;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getQqOpenId() {
		return this.qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public String getXlWeiboCode() {
		return this.xlWeiboCode;
	}

	public void setXlWeiboCode(String xlWeiboCode) {
		this.xlWeiboCode = xlWeiboCode;
	}

	public String getPerNickName() {
		return this.perNickName;
	}

	public void setPerNickName(String perNickName) {
		this.perNickName = perNickName;
	}

	public String getPerPostAddr() {
		return this.perPostAddr;
	}

	public void setPerPostAddr(String perPostAddr) {
		this.perPostAddr = perPostAddr;
	}

	public String getPerPostCode() {
		return this.perPostCode;
	}

	public void setPerPostCode(String perPostCode) {
		this.perPostCode = perPostCode;
	}

	public String getProvince() {
		
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
		getProvinceName();
	}

	public String getCity() {
		
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
		getCityName();
	}

	public String getPerJobName() {
		String perJobNameString = "";
		try {
			if(!StringUtils.isEmpty(this.getPerJob())){
				DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader");
				String[] recIDs = this.getPerJob().split(",");
				for (int i = 0; i < recIDs.length; i++) {
					int id = Integer.parseInt(recIDs[i], 10);
					if(id>0){
						//机构的类型不一样
						int type = DicTypes.DIC_RECRUIT_TYPE.typeID();
						if(getType()==1){
							type = DicTypes.DIC_ORG_TYPE.typeID();
						}
						
						Dictionary dic = reader.getDic(type,id);
						if(dic!=null){
							perJobNameString = perJobNameString+ dic.getName();
							if(i<recIDs.length-1){
								perJobNameString = perJobNameString+",";
							}
						}
						
					}
				}
			}
		} catch (Exception e) {
			return perJobNameString;
		}
		
		 
		return perJobNameString;
		
	}


	public String getPerJob() {
		return this.perJob;
	}

	public void setPerJob(String perJob) {
		this.perJob = perJob;
		getPerJobName();
		getPer4Type();
	}

	public String getPerPhone() {
		return this.perPhone;
	}

	public void setPerPhone(String perPhone) {
		this.perPhone = perPhone;
	}

	public String getOrgShortname() {
		return this.orgShortname;
	}

	public void setOrgShortname(String orgShortname) {
		this.orgShortname = orgShortname;
	}

	public String getOrgFullname() {
		return this.orgFullname;
	}

	public void setOrgFullname(String orgFullname) {
		this.orgFullname = orgFullname;
	}

	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgBusinessLicense() {
		return this.orgBusinessLicense;
	}

	public void setOrgBusinessLicense(String orgBusinessLicense) {
		this.orgBusinessLicense = orgBusinessLicense;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getOrgNature() {
		return this.orgNature;
	}

	public void setOrgNature(String orgNature) {
		this.orgNature = orgNature;
	}

	public String getOrgTrade() {
		return this.orgTrade;
	}

	public void setOrgTrade(String orgTrade) {
		this.orgTrade = orgTrade;
	}

	public String getOrgScale() {
		return this.orgScale;
	}

	public void setOrgScale(String orgScale) {
		this.orgScale = orgScale;
		if(getType()==1){
			getOrgScaleName();
		}
		
	}

	public String getOrgHomePage() {
		return this.orgHomePage;
	}

	public void setOrgHomePage(String orgHomePage) {
		this.orgHomePage = orgHomePage;
	}

	public List<UserBankcard> getBankcards() {
		return bankcards;
	}

	public void setBankcards(List<UserBankcard> bankcards) {
		this.bankcards = bankcards;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

//	public Date getBirthdate() {
//		return birthdate;
//	}
//
//	public void setBirthdate(Date birthdate) {
//		this.birthdate = birthdate;
//	}

//	public Integer getFirstType() {
//		return firstType;
//	}
//
//	public void setFirstType(Integer firstType) {
//		this.firstType = firstType;
//	}
//
//	public Integer getSecondType() {
//		return secondType;
//	}
//
//	public void setSecondType(Integer secondType) {
//		this.secondType = secondType;
//	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLastSessionID() {
		return lastSessionID;
	}

	public void setLastSessionID(String lastSessionID) {
		this.lastSessionID = lastSessionID;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}