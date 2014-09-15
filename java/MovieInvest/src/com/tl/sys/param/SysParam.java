package com.tl.sys.param;

@SuppressWarnings("serial")
public class SysParam implements java.io.Serializable {
	/** id*/
	  
	private Integer id;
    
	/** 参数名称*/
  
	private String keyname;
    
	/** 参数值*/
  
	private String keyvalue;
    
	/** 参数描述*/
  
	private String detail;
    
	/**
	 * 构造函数
	 */
	public SysParam(){}
	
	/** 构造函数
	 * @param id
	 * @param keyName
	 * @param keyValue
	 * @param detail
	 */
	public SysParam(Integer id,String keyName,String keyValue,String detail){
		this.id = id;
		this.keyname = keyName;
		this.keyvalue = keyValue;
		this.detail = detail;
	}
	
	/** 构造函数

	 * @param keyName
	 * @param keyValue
	 * @param detail
	 */
	public SysParam(String keyName,String keyValue,String detail){
		
		this.keyname = keyName;
		this.keyvalue = keyValue;
		this.detail = detail;
	}
	/**
	 * @return 获得 id。
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置 id。
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return 获得 参数名称。
	 */
	public String getKeyname() {
		return keyname;
	}

	/**
	 * 设置 参数名称。
	 */
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	/**
	 * @return 获得 参数值。
	 */
	public String getKeyvalue() {
		return keyvalue;
	}

	/**
	 * 设置 参数值。
	 */
	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}
	/**
	 * @return 获得 参数描述。
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * 设置 参数描述。
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
