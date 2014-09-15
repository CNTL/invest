package com.tl.sys.sysuser;
/** 
 * @created 2014年8月25日 下午8:16:00 
 * @author  leijj
 * 类说明 ： dataTable分页实体类
 */
public class DataTableParam {
    // DataTable请求服务器端次数
    public String sEcho;

    // 过滤文本
    public String sSearch;

    // 每页显示的数量
    public int iDisplayLength;

    // 分页时每页跨度数量
    public int iDisplayStart;

    // 列数
    public int iColumns;

    // 排序列的数量
    public int iSortingCols;

    // 逗号分割所有的列
    public String sColumns;

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getiColumns() {
		return iColumns;
	}

	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
}