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
			if(StringUtils.isEmpty(value)) value = "0";
			project.setApproveStatus(Short.parseShort(value));
		}
	},
	approveUser("proj_approveUser","������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
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
			if(StringUtils.isEmpty(value)) value = "0";
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
			if(StringUtils.isEmpty(value)) value = "0";
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
			if(StringUtils.isEmpty(value)) value = "0";
			project.setUserId(Integer.parseInt(value));
		}
	},
	payType("proj_payType","��Ŀ���",DataType.INTEGER){
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setPayType(Integer.parseInt(value));
		}
	},
	type("proj_type","��Ŀ����",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setType(Integer.parseInt(value));
		}
	},
	timeType("proj_timeType","ʱ������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setTimeType(Integer.parseInt(value));
		}
	},
	countDay("proj_countDay","����",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
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
			if(StringUtils.isEmpty(value)) value = "0";
			project.setAmountGoal(MoneyHelper.toMoney(value));
		}
	},
	amountRaised("proj_amountRaised","�ѳＯ���",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setAmountRaised(MoneyHelper.toMoney(value));
		}
	},
	amountPaid("proj_amountPaid","��֧�����",DataType.FLOAT) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setAmountPaid(MoneyHelper.toMoney(value));
		}
	},

	countLove("proj_countLove","��������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setCountLove(Integer.parseInt(value));
		}
	},
	countView("proj_countView","�鿴����",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setCountView(Integer.parseInt(value));
		}
	},
	countSubject("proj_countSubject","������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setCountSubject(Integer.parseInt(value));
		}
	},
	countSupport("proj_countSupport","֧������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setCountSupport(Integer.parseInt(value));
		}
	}
	,
	province("proj_province","ʡ",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setProvince(Integer.parseInt(value));
		}
	}
	,
	city("proj_city","��",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setCity(Integer.parseInt(value));
		}
	}
	,
	county("proj_county","��",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setCounty(Integer.parseInt(value));
		}
	}
	,
	order("proj_order","������",DataType.INTEGER) {
		@Override
		public void setValue(Project project, String value) {
			if(StringUtils.isEmpty(value)) value = "0";
			project.setOrder(Integer.parseInt(value));
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
