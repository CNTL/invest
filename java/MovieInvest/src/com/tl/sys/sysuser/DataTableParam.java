package com.tl.sys.sysuser;
/** 
 * @created 2014��8��25�� ����8:16:00 
 * @author  leijj
 * ��˵�� �� dataTable��ҳʵ����
 */
public class DataTableParam {
    // DataTable����������˴���
    public String sEcho;

    // �����ı�
    public String sSearch;

    // ÿҳ��ʾ������
    public int iDisplayLength;

    // ��ҳʱÿҳ�������
    public int iDisplayStart;

    // ����
    public int iColumns;

    // �����е�����
    public int iSortingCols;

    // ���ŷָ����е���
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