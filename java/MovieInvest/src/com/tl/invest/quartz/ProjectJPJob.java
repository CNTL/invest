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

/** 竞拍项目job
 * @author wang.yq
 *
 */
public class ProjectJPJob{
	Log log = Context.getLog("invest");
	
	protected void execute() throws Exception {
		log.info("开始执行定时任务：ProjectJPJob");
		try {
			ProjectExt[] proj_list = service.getJPOverProjectExts();
			for (ProjectExt pro : proj_list) {
				//得到项目的所有支持者
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
						sb.append("竞拍项目：<a href=\"../project/Project.do?id=57\">"+pro.getName()+"</a>已经竞拍结束，恭喜您以"+canPayer.getUserName()+"元的价格竞拍成功!请在48小时内进行支付！<a href=\"../project/Project.do?id=57\">前往支付页面</a>");
					}
					else{
						sb.append("<a href=\"../project/Project.do?id=57\">"+pro.getName()+"</a>已经竞拍结束，竞拍成功者 "+canPayer.getUserName()+",以"+String.valueOf(canPayer.getAmount())+"元价格竞拍成功！");
					}
					
					msg.setMsgContent(sb.toString());
					//发送消息
					userMsgManager.save(msg);
				}
				//设置竞拍者可以支付
				service.setJPProjectPayUser(pro, canPayer.getUserId());
				Project project = service.get(pro.getId());
				if(project!=null){
					project.setNotified(1);
					 
					service.save( project );
				}
				
			}

		} catch (Exception e) {
			log.info("执行定时任务错误：ProjectJPJob");
			log.info("errorinfo："+e.getMessage());
		}
		
		
		log.info("结束执行定时任务：ProjectJPJob");
	}
	 
	
	/**得到竞拍成功的人,如果没有最后一个人为成功者
	 * @param support_list
	 * @return
	 * @throws TLException
	 */
	private ProjSupportExt getCanPaySupport(ProjSupportExt[] support_list) throws TLException{
		ProjSupportExt supportExt = null;
		if(support_list!=null && support_list.length>0){
			for (ProjSupportExt support : support_list) {
				if(supportExt==null){
					supportExt = support;//默认取最大值
				}
				
				if(support.getCanpay()==1){
					supportExt = support;//默认取最大值
				}
			}
		}
		
		return supportExt;
	}

	private ProjectService service  = new ProjectService();
	private UserMsgManager userMsgManager = new UserMsgManager();

}
