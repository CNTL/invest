package com.tl.invest.sys.mu;

import com.tl.common.StringUtils;

public class Menu implements java.io.Serializable {
	private static final long serialVersionUID = 5999089950711034862L;
	
	private int id;
	private int pid;
	private String name;
	private int tbId;
	private String url;
	private String rule;
	private String list;
	private String query;
	private String cssPath;
	private String jsPath;
	private String className;
	private int order;
	private int type;
	private int position;
	private int isDef;
	
	public Menu(){
		super();
	}
	
	public Menu(int id,String name,String url,String className,int type,int isDef){
		this.id = id;
		this.pid = 0;
		this.name = name;
		this.className = className;
		this.url = url;
		this.isDef = isDef;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}	
	public int getTbId() {
		return tbId;
	}
	public void setTbId(int tbId) {
		this.tbId = tbId;
	}
	public String getUrl() {
		if(this.type == -999) return url;
		String url_p = url;
		if(StringUtils.isNotEmpty(url_p)){
			if(url_p.indexOf("?id=") == -1 && url_p.indexOf("&id=") == -1){
				if(url_p.indexOf("?")>0){
					url_p += "&";
				}else {
					url_p += "?";
				}
				url_p += "id="+String.valueOf(id);
			}
			if(url_p.indexOf("?nav=") == -1 && url_p.indexOf("&nav=") == -1){
				if(url_p.indexOf("?")>0){
					url_p += "&";
				}else {
					url_p += "?";
				}
				url_p += "nav=menu-"+String.valueOf(pid)+"-"+String.valueOf(id);
			}
		}
		return url_p;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getCssPath() {
		return cssPath;
	}
	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}
	public String getJsPath() {
		return jsPath;
	}
	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getIsDef() {
		return isDef;
	}
	public void setIsDef(int isDef) {
		this.isDef = isDef;
	}
}
