package com.tl.kernel;

public enum EditType {
	INPUT							( 0, "������д�����У�"),
	INPUT_MULTI					( 3, "������д�����У�"),
	INPUT_AUTOCOMPLETE			( 1, "������д�����У�����д��ʾ��"),
	INPUT_AUTOCOMPLETE_KV			( 2, "������д�����У�����д��ʾ����ֵ�ԣ�") ,
	
	SELECT							( 4, "��ѡ��������select��"),
	SELECT_DYNAMIC					( 5, "��ѡ��������select����̬ȡֵ��"), 
	RADIO							( 6, "��ѡ����ѡ��radio��"),
	RADIO_DYNMIC					( 7, "��ѡ����ѡ��radio����̬ȡֵ��"),
	SELECT_MULTI					( 8, "��ѡ��������select��"),
	SELECT_MULTI_DYNAMIC			( 9, "��ѡ��������select����̬ȡֵ��"),
	CHECKBOX						(10, "��ѡ����ѡ��checkbox��"),
	CHECKBOX_DYNAMIC				(11, "��ѡ����ѡ��checkbox����̬ȡֵ��"),
	CATEGORY						(12, "���ࣨ��������"),
	CATEGORY_MULTI					(13, "���ࣨ���������ɶ�ѡ��"),
	CATEGORY_SELECT				(14, "���ࣨ������select��ֻ�����ڵ�����ࣩ"),
	TREE_ORG						(16, "���ţ���������"),
	TREE_ORG_MULTI					(17, "���ţ����������ɶ�ѡ��"),
	TREE_ORG_PERMISSION			(18, "���ţ���������Ȩ�ޣ�"),
	TREE_ORG_MULTI_PERMISSION		(19, "���ţ���������Ȩ�ޣ��ɶ�ѡ��"),
	TREE_USER						(20, "�û����û�����"),
	TREE_USER_MULTI				(21, "�û����û������ɶ�ѡ��"),
	TREE_USER_PERMISSION			(22, "�û����û�����Ȩ�ޣ�"),
	TREE_USER_MULTI_PERMISSION	(23, "�û����û�����Ȩ�ޣ��ɶ�ѡ��"),
	TREE_ROLE						(20, "��ɫ����ɫ����"),
	TREE_ROLE_MULTI				(21, "��ɫ����ɫ�����ɶ�ѡ��"),
	TREE_ROLE_PERMISSION			(22, "��ɫ����ɫ����Ȩ�ޣ�"),
	TREE_ROLE_MULTI_PERMISSION	(23, "��ɫ����ɫ����Ȩ�ޣ��ɶ�ѡ��"),
	INPUT_EMAIL					(24, "�����ʼ���ר���ı����Զ����и�ʽ��֤��"),
	INPUT_PHONE					(25, "�̶��绰��ר���ı����Զ����и�ʽ��֤��"),
	INPUT_MOBILE					(26, "�ֻ���ר���ı����Զ����и�ʽ��֤��"),
	INPUT_ADDRESS					(27, "��ַ���ֿ���д��ʽ����Ϊ��ʡ,��,��/��,�ֵ�,¥�ţ�"),
	INPUT_DATE_SPLIT				(28, "���ڣ��ֿ���д��ʽ����Ϊ����,��,�գ�"),
	CHECKBOX_YESNO					(29, "��/�񣬹�ѡ��ʽ"),
	;
	
	private int id;
	private String typeName;
	private EditType(int id, String typeName){
		this.id = id;
		this.typeName = typeName;
	}
	
	public String typeName(){
		return this.typeName;
	}
	
	public int id(){
		return this.id;
	}
}
