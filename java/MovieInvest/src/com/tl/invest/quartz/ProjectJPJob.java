package com.tl.invest.quartz;

import org.codehaus.jackson.annotate.JsonTypeInfo.As;

import com.tl.common.DateUtils;
import com.tl.common.log.Log;
import com.tl.invest.proj.ProjSupportExt;
import com.tl.invest.proj.Project;
import com.tl.invest.proj.ProjectExt;
import com.tl.invest.proj.service.ProjectService;
import com.tl.invest.user.msg.UserMsg;
import com.tl.invest.user.msg.UserMsgManager;
import com.tl.invest.user.user.UserManager;
import com.tl.kernel.context.Context;
import com.tl.kernel.context.TLException;

/** ������Ŀjob
 * @author wang.yq
 *
 */
public class ProjectJPJob{
	Log log = Context.getLog("invest");
	
	protected void execute() throws Exception {
		log.info("��ʼִ�ж�ʱ����ProjectJPJob");
		try {
			ProjectExt[] proj_list = service.getJPOverProjectExts();
			for (ProjectExt pro : proj_list) {
				//�õ���Ŀ������֧����
				ProjSupportExt[] support_list = service.getJPPayProjectSupports(pro.getId(), " sp_id desc ", null);
				ProjSupportExt canPayer = getCanPaySupport(support_list);
				for (ProjSupportExt support : support_list) {
					StringBuilder sb = new StringBuilder();
					UserMsg msg = new UserMsg();
					msg.setCreateTime(DateUtils.getTimestamp());
					msg.setMsgFromId(pro.getUserId());
					msg.setMsgFrom(pro.getUserName());
					msg.setMsgToId(support.getUserId());
					msg.setMsgTo(support.getUserName());
					msg.setMsgIsRead(0);
					if(canPayer.getUserId()== support.getUserId()){
						sb.append("������Ŀ��<a href=\"../project/Project.do?id=57\">"+pro.getName()+"</a>�Ѿ����Ľ�������ϲ����"+canPayer.getUserName()+"Ԫ�ļ۸��ĳɹ�!����48Сʱ�ڽ���֧����<a href=\"../project/Project.do?id=57\">ǰ��֧��ҳ��</a>");
					}
					else{
						sb.append("<a href=\"../project/Project.do?id=57\">"+pro.getName()+"</a>�Ѿ����Ľ��������ĳɹ��� "+canPayer.getUserName()+",��"+String.valueOf(canPayer.getAmount())+"Ԫ�۸��ĳɹ���");
					}
					
					msg.setMsgContent(sb.toString());
					//������Ϣ
					userMsgManager.save(msg);
				}
				//���þ����߿���֧��
				service.setJPProjectPayUser(pro, canPayer.getUserId());
				Project project = service.get(pro.getId());
				if(project!=null){
					project.setNotified(1);
					 
					service.save( project );
				}
				
			}

		} catch (Exception e) {
			log.info("ִ�ж�ʱ�������ProjectJPJob");
			log.info("errorinfo��"+e.getMessage());
		}
		
		
		log.info("����ִ�ж�ʱ����ProjectJPJob");
	}
	 
	
	/**�õ����ĳɹ�����,���û�����һ����Ϊ�ɹ���
	 * @param support_list
	 * @return
	 * @throws TLException
	 */
	private ProjSupportExt getCanPaySupport(ProjSupportExt[] support_list) throws TLException{
		ProjSupportExt supportExt = null;
		if(support_list!=null && support_list.length>0){
			for (ProjSupportExt support : support_list) {
				if(supportExt==null){
					supportExt = support;//Ĭ��ȡ���ֵ
				}
				
				if(support.getCanpay()==1){
					supportExt = support;//Ĭ��ȡ���ֵ
				}
			}
		}
		
		return supportExt;
	}

	private ProjectService service  = new ProjectService();
	private UserMsgManager userMsgManager = new UserMsgManager();

}
