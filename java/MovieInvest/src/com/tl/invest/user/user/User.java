package com.tl.invest.user.user;

// Generated Nov 17, 2014 3:11:25 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.tl.invest.constant.DicTypes;
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
	private String identityCard;
	private String head;
	private String intro;
	private String background;
	private Integer isRealNameIdent;
	private Timestamp createTime;
	private String qqOpenId;
	private String xlWeiboCode;
	private String perNickName;
	private String perPostAddr;
	private String perPostCode;
	private String province;
	private String city;
	private String perJob;
	private String perJobName;
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
	private String orgHomePage;
	private int deleted;
	private int gender;
	private Date birthdate;
	private Integer firstType;
	private Integer secondType;
	private String typeName;
	private Integer perOrder;
	private String lastSessionID;
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
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPerJobName() {
		try {
			DictionaryReader reader = (DictionaryReader)Context.getBean("DictionaryReader");
			if(this.secondType>0){
				Dictionary dic = reader.getDic(DicTypes.DIC_RECRUIT_TYPE.typeID(),this.secondType);
				return dic.getName();
			}
		} catch (Exception e) {
			return perJobName;
		}
		return perJobName;
		
	}

	public void setPerJobName(String perJobName) {
		
		this.perJobName = perJobName;
	}

	public String getPerJob() {
		return this.perJob;
	}

	public void setPerJob(String perJob) {
		this.perJob = perJob;
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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Integer getFirstType() {
		return firstType;
	}

	public void setFirstType(Integer firstType) {
		this.firstType = firstType;
	}

	public Integer getSecondType() {
		return secondType;
	}

	public void setSecondType(Integer secondType) {
		this.secondType = secondType;
	}

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