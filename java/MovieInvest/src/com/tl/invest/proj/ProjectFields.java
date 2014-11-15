package com.tl.invest.proj;

import com.tl.common.DateUtils;
import com.tl.common.StringUtils;
import com.tl.invest.common.MoneyHelper;
import com.tl.kernel.DataType;
public enum ProjectFields {
	created("proj_created","创建时间",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setCreated(StringUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	lastModified("proj_lastModified","最后修改时间",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setLastModified(StringUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	
	deleted("proj_deleted","是否删除",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setDeleted(Short.parseShort(value));
		}
	},
	approveStatus("proj_approveStatus","审批状态",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setApproveStatus(Short.parseShort(value));
		}
	},
	approveUser("proj_approveUser","审批人",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setApproveUser(Integer.parseInt(value));
		}
	},
	approveTime("proj_approveTime","审批时间",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setApproveTime(DateUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	status("proj_status","状态",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setStatus(Integer.parseInt(value));
		}
	},
	locktime("proj_locktime","锁定时间",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setLocktime(DateUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	pid("proj_pid","父ID",DataType.LONG) {
		@Override
		public void setValue(Project project, String value) {
			project.setPid(Long.parseLong(value));
		}
	},
	name("proj_name","项目名称",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setName(value);
		}
	},
	userID("proj_userID","创建人ID",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setUserId(Integer.parseInt(value));
		}
	},
	type("proj_type","项目类型",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setType(Integer.parseInt(value));
		}
	},
	timeType("proj_timeType","时间类型",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setTimeType(Integer.parseInt(value));
		}
	},
	countDay("proj_countDay","天数",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountDay(Integer.parseInt(value));
		}
	},
	beginDate("proj_beginDate","开始日期",DataType.DATE) {
		@Override
		public void setValue(Project project, String value) {
			project.setBeginDate(DateUtils.toDate(value, "yyyy-MM-dd"));
		}
	},
	endDate("proj_endDate","结束日期",DataType.DATE) {
		@Override
		public void setValue(Project project, String value) {
			project.setEndDate(DateUtils.toDate(value, "yyyy-MM-dd"));
		}
	},
	
	imgURL("proj_imgURL","封面图片地址",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setImgUrl(value);
		}
	},
	videoURL("proj_videoURL","视频地址",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setVideoUrl(value);
		}
	},
	summary("proj_summary","项目简介",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setSummary(value);
		}
	},
	content("proj_content","详细信息",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setContent(value);
		}
	},
	
	amountGoal("proj_amountGoal","目标金额",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			project.setAmountGoal(MoneyHelper.toMoney(value));
		}
	},
	amountRaised("proj_amountRaised","已筹集金额",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			project.setAmountRaised(MoneyHelper.toMoney(value));
		}
	},
	amountPaid("proj_amountPaid","已支付金额",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			project.setAmountPaid(MoneyHelper.toMoney(value));
		}
	},

	countLove("proj_countLove","分享人数",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountLove(Integer.parseInt(value));
		}
	},
	countView("proj_countView","查看人数",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountView(Integer.parseInt(value));
		}
	},
	countSubject("proj_countSubject","话题数",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountSubject(Integer.parseInt(value));
		}
	},
	countSupport("proj_countSupport","支持人数",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountSupport(Integer.parseInt(value));
		}
	}
	;
	
	private String fieldCode;
	private String fieldName;
	private DataType dataType;
	private ProjectFields(String code,String name,DataType dataType){
		this.fieldCode = code;
		this.fieldName = name;
		this.dataType = dataType;
	}
	
	public String fieldCode(){
		return fieldCode;
	}
	public String fieldName(){
		return fieldName;
	}
	public DataType dataType() {
		return dataType;
	}
	
	public abstract void setValue(Project project,String value);	
}
