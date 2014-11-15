package com.tl.invest.proj;

import com.tl.common.DateUtils;
import com.tl.common.StringUtils;
import com.tl.invest.common.MoneyHelper;
import com.tl.kernel.DataType;
public enum ProjectFields {
	created("proj_created","����ʱ��",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setCreated(StringUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	lastModified("proj_lastModified","����޸�ʱ��",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setLastModified(StringUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	
	deleted("proj_deleted","�Ƿ�ɾ��",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setDeleted(Short.parseShort(value));
		}
	},
	approveStatus("proj_approveStatus","����״̬",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setApproveStatus(Short.parseShort(value));
		}
	},
	approveUser("proj_approveUser","������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setApproveUser(Integer.parseInt(value));
		}
	},
	approveTime("proj_approveTime","����ʱ��",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setApproveTime(DateUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	status("proj_status","״̬",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setStatus(Integer.parseInt(value));
		}
	},
	locktime("proj_locktime","����ʱ��",DataType.TIMESTAMP) {
		@Override
		public void setValue(Project project, String value) {
			project.setLocktime(DateUtils.toDate(value, "yyyy-MM-dd HH:mm:ss"));
		}
	},
	pid("proj_pid","��ID",DataType.LONG) {
		@Override
		public void setValue(Project project, String value) {
			project.setPid(Long.parseLong(value));
		}
	},
	name("proj_name","��Ŀ����",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setName(value);
		}
	},
	userID("proj_userID","������ID",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setUserId(Integer.parseInt(value));
		}
	},
	type("proj_type","��Ŀ����",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setType(Integer.parseInt(value));
		}
	},
	timeType("proj_timeType","ʱ������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setTimeType(Integer.parseInt(value));
		}
	},
	countDay("proj_countDay","����",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountDay(Integer.parseInt(value));
		}
	},
	beginDate("proj_beginDate","��ʼ����",DataType.DATE) {
		@Override
		public void setValue(Project project, String value) {
			project.setBeginDate(DateUtils.toDate(value, "yyyy-MM-dd"));
		}
	},
	endDate("proj_endDate","��������",DataType.DATE) {
		@Override
		public void setValue(Project project, String value) {
			project.setEndDate(DateUtils.toDate(value, "yyyy-MM-dd"));
		}
	},
	
	imgURL("proj_imgURL","����ͼƬ��ַ",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setImgUrl(value);
		}
	},
	videoURL("proj_videoURL","��Ƶ��ַ",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setVideoUrl(value);
		}
	},
	summary("proj_summary","��Ŀ���",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setSummary(value);
		}
	},
	content("proj_content","��ϸ��Ϣ",DataType.VARCHAR) {
		@Override
		public void setValue(Project project, String value) {
			project.setContent(value);
		}
	},
	
	amountGoal("proj_amountGoal","Ŀ����",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			project.setAmountGoal(MoneyHelper.toMoney(value));
		}
	},
	amountRaised("proj_amountRaised","�ѳＯ���",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			project.setAmountRaised(MoneyHelper.toMoney(value));
		}
	},
	amountPaid("proj_amountPaid","��֧�����",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			project.setAmountPaid(MoneyHelper.toMoney(value));
		}
	},

	countLove("proj_countLove","��������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountLove(Integer.parseInt(value));
		}
	},
	countView("proj_countView","�鿴����",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountView(Integer.parseInt(value));
		}
	},
	countSubject("proj_countSubject","������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			project.setCountSubject(Integer.parseInt(value));
		}
	},
	countSupport("proj_countSupport","֧������",DataType.INTEGER) {
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
