package com.tl.sys.param;

@SuppressWarnings("serial")
public class SysParam implements java.io.Serializable {
	/** id*/
	  
	private Integer id;
    
	/** ��������*/
  
	private String keyname;
    
	/** ����ֵ*/
  
	private String keyvalue;
    
	/** ��������*/
  
	private String detail;
    
	/**
	 * ���캯��
	 */
	public SysParam(){}
	
	/** ���캯��
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
	
	/** ���캯��

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
	 * @return ��� id��
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * ���� id��
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return ��� �������ơ�
	 */
	public String getKeyname() {
		return keyname;
	}

	/**
	 * ���� �������ơ�
	 */
	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}
	/**
	 * @return ��� ����ֵ��
	 */
	public String getKeyvalue() {
		return keyvalue;
	}

	/**
	 * ���� ����ֵ��
	 */
	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}
	/**
	 * @return ��� ����������
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * ���� ����������
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
