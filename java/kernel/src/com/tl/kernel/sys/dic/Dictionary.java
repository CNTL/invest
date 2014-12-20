package com.tl.kernel.sys.dic;

import java.util.Date;

public class Dictionary implements Cloneable{
	/**
	 * 分类级联名称和级联ID使用的分隔符
	 */
	public static final char separator = '~';
	/**
	 * 是否使用级联名称的标志。
	 * 系统中不需要使用时，可以在分类修改等操作时节省不少时间
	 */
	public static boolean useCascadeName = true;
	public static String DEFAULT_TABLENAME = "sys_dictionary";
	
	public static final int TRANS_DICCODE 	= 0;
	public static final String FIELD_DICCODE 	= "dic_code";
	
	private int id;
	private int type;
	private int pid;
	private String name;
	private String code;
	private int level;
	private int childCount = 0;
	private String cascadeId;
	private String cascadeName;
	private String value;
	private String text;
	private int order;
	private int valid;
	private Date lastModified;	//最后修改时间
	private String memo;
	private Dictionary[] subDics;//子分类
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCascadeId() {
		return cascadeId;
	}
	public void setCascadeId(String cascadeId) {
		this.cascadeId = cascadeId;
	}
	public String getCascadeName() {
		return cascadeName;
	}
	public void setCascadeName(String cascadeName) {
		this.cascadeName = cascadeName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public static boolean isUseCascadeName() {
		return useCascadeName;
	}
	public static void setUseCascadeName(boolean useCascadeName) {
		Dictionary.useCascadeName = useCascadeName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public static char getSeparator() {
		return separator;
	}
	public int getChildCount() {
		return childCount;
	}
	void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	
	public Dictionary[] getSubDics() {
		return subDics;
	}
	public void setSubDics(Dictionary[] subDics) {
		this.subDics = subDics;
	}
	/**
	 * Clone分类对象
	 */
	public Dictionary clone()
	{
		try {
			Dictionary tmp = (Dictionary) super.clone();
			tmp.lastModified = (Date) lastModified.clone();
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
