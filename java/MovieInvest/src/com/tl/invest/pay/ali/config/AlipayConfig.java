package com.tl.invest.pay.ali.config;

import com.tl.invest.constant.ParamItem;
import com.tl.invest.constant.ParamProject;
import com.tl.kernel.context.Context;
import com.tl.kernel.sys.param.ParameterReader;

/* *
 *������AlipayConfig
 *���ܣ�����������
 *��ϸ�������ʻ��й���Ϣ������·��
 *�汾��3.3
 *���ڣ�2012-08-10
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
	
 *��ʾ����λ�ȡ��ȫУ����ͺ��������ID
 *1.������ǩԼ֧�����˺ŵ�¼֧������վ(www.alipay.com)
 *2.������̼ҷ���(https://b.alipay.com/order/myOrder.htm)
 *3.�������ѯ���������(PID)��������ѯ��ȫУ����(Key)��

 *��ȫУ����鿴ʱ������֧�������ҳ��ʻ�ɫ��������ô�죿
 *���������
 *1�������������ã������������������������
 *2���������������ԣ����µ�¼��ѯ��
 */

public class AlipayConfig {
	private static ParameterReader reader = (ParameterReader)Context.getBean(ParameterReader.class);
	//�����������������������������������Ļ�����Ϣ������������������������������
	// ���������ID����2088��ͷ��16λ��������ɵ��ַ���
	private static String partner = "";
	// �̻���˽Կ
	private static String key = "";

	//�����������������������������������Ļ�����Ϣ������������������������������
	

	// �����ã�����TXT��־�ļ���·��
	//private static String log_path = "D:\\";

	// �ַ������ʽ Ŀǰ֧�� gbk �� utf-8
	public static String input_charset = "gbk";
	
	// ǩ����ʽ �����޸�
	public static String sign_type = "MD5";
	
	public static String getPartner(){
		return reader.getParameter(ParamProject.PAY_THIRD, ParamItem.PAY_ALI_PARTNER).getValue();
	}
	public static String getKey(){
		return reader.getParameter(ParamProject.PAY_THIRD, ParamItem.PAY_ALI_KEY).getValue();
	}
	public static String getSellerEmail(){
		return reader.getParameter(ParamProject.PAY_THIRD, ParamItem.PAY_ALI_SELLER_EMAIL).getValue();
	}
	public static String getReturnUrl(){
		return reader.getParameter(ParamProject.PAY_THIRD, ParamItem.PAY_ALI_RETRUN_URL).getValue();
	}
	public static String getNotifyUrl(){
		return reader.getParameter(ParamProject.PAY_THIRD, ParamItem.PAY_ALI_NOTIFY_URL).getValue();
	}
}
