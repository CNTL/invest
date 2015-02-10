package com.tl.invest.user.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tl.invest.sys.mu.Menu;
import com.tl.invest.workspace.Entry;
import com.tl.kernel.context.Context;
import com.tl.sys.common.SessionHelper;

/** 
 * @created 2014��11��30�� ����3:44:57 
 * @author  leijj
 * ��˵�� �� 
 */
@SuppressWarnings({ "unchecked", "rawtypes"})
public class UserMainController extends Entry {
	@Override
	protected String getCurrentMenu() {
		return "Ӱ��";
	}
	@Override
	protected void setOtherData(HttpServletRequest request,
			HttpServletResponse response,Map model) throws Exception {
		//�̳�ʵ��
		int groupID = getInt(request, "groupID");
		model.put("groupID", groupID);
	}
	@Override
	protected void setMetaData(HttpServletRequest request,Map model) {
		model.put("title", "����ӳ��--��Ŀ");
		model.put("keywords", "����ӳ��");
		model.put("description", "����ӳ��");
	}
	@Override
	protected Menu[] getInfoMenus(HttpServletRequest request, int infoType) {
		String class1 = "", class2 = "", class3 = "", class4 = "", class5 = "", class6 = "", class7 = "", class8 = "", class9 = "";
		switch (infoType) {
		case 1:
			class1 = "select";
			break;
		case 2:
			class2 = "select";
			break;
		case 3:
			class3 = "select";
			break;
		case 4:
			class4 = "select";
			break;
		case 5:
			class5 = "select";
			break;
		case 6:
			class6 = "select";
			break;
		case 7:
			class7 = "select";
			break;
		case 8:
			class8 = "select";
			break;
		case 9:
			class9 = "select";
			break;
		default:
			break;
		}
		int type = 0;
		try {
			User user = userManager.getUserByCode(SessionHelper.getUserCode(request));
			if(user != null) type = user.getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Menu> list = new ArrayList<Menu>();
		if(type == 0){//�����û���¼��
			list.add(new Menu(1, "��������","/user/BasicInfo.do?infoType=1", class1, -999,0));
			list.add(new Menu(2, "�޸�����","/user/PwdChange.do?infoType=2", class2, -999,0));
			list.add(new Menu(3, "ͷ�����", "/user/HeadImg.do?infoType=3", class3,-999,0));
			list.add(new Menu(4, "ʵ����֤", "/user/RelAuth.do?infoType=4", class4,-999,0));
			list.add(new Menu(5, "��ϸ����", "/org/DetailInfo.do?infoType=5&type=0", class5,-999,0));
			list.add(new Menu(6, "����ͼ��", "/user/PhotoGroupMa.do?infoType=6", class6,-999,0));
			list.add(new Menu(7, "������Ƶ", "/user/VideoGroupMa.do?infoType=7", class7,-999,0));
//			list.add(new Menu(8, "��������", "/resume/userResume.do?infoType=8", class8,-999,0));
			list.add(new Menu(9, "�ռ���ַ", "/user/Address.do?infoType=9", class9,-999,0));
		} else if(type == 1){//�����û���¼��
			list.add(new Menu(1, "��������", "/org/BasicInfo.do?infoType=1", class1, -999,0));
			list.add(new Menu(2, "�޸�����", "/user/PwdChange.do?infoType=2", class2, -999,0));
			list.add(new Menu(3, "ͷ�����", "/user/HeadImg.do?infoType=3", class3,-999,0));
			list.add(new Menu(4, "������֤", "/org/RelAuth.do?infoType=4", class4,-999,0));
			list.add(new Menu(5, "��ϸ����", "/org/DetailInfo.do?infoType=5&type=1", class5,-999,0));
			list.add(new Menu(6, "����ͼ��", "/user/PhotoGroupMa.do?infoType=6", class6,-999,0));
			list.add(new Menu(7, "������Ƶ", "/user/VideoGroupMa.do?infoType=7", class7,-999,0));
			list.add(new Menu(8, "�ռ���ַ", "/org/Address.do?infoType=8", class8,-999,0));
		}
		
		return (Menu[]) list.toArray(new Menu[0]);
	}
	private UserManager userManager = (UserManager)Context.getBean(UserManager.class);
}