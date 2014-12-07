var options = {
	itemID:"proj_id",
	tbview:"view_project",
	url:'../workspace/tblist.do',
	method:'post',
	sortName:"proj_id",
	rule:"1=1",
	showFooter:false,
	toolbar: [],	
	rowStyler : function(index,row){
		//if (row.AI_Debt<=0) return 'background-color:green;color:#fff;';
	},
	onDblClickRow : function(rowIndex,rowData){
		var oktool = {text:'通过',iconCls:'icon-ok',handler:function(){
			tldialog.submit("../admin/ProjectFetcher.do?action=pass&id="+rowData.proj_id,function(data){
				tldialog.closeRefresh();
			});
		}};
		var notool = {text:'不通过',iconCls:'icon-no',handler:function(){
			tldialog.submit("../admin/ProjectFetcher.do?action=reject&id="+rowData.proj_id,function(data){
				tldialog.closeRefresh();
			});
		}};
		var colsetool = {text:'关闭',iconCls:'icon-cancel',handler:function(){
			tldialog.close();
		}};
		var toolbars = [];
		if(rowData.proj_approveStatus == 0){
			toolbars.push(oktool);
			toolbars.push(notool);
		} else if(rowData.proj_approveStatus == 2){
			toolbars.push(oktool);
		}
		toolbars.push(oktool);
		toolbars.push(colsetool);
		tldialog.show(rowData.proj_name,"../admin/Project.do?id="+rowData.proj_id,800,680,true,false,false,toolbars);
	},
	frozenColumns : [[{title:"复选框",field:"ck",width:60,sortable:false,checkbox:true},
						{title:"ID",field:"proj_id",width:60,sortable:true}
					]],
	columns : [[
				{title:"项目名称",field:"proj_name",width:200,sortable:false},
				{title:"类型",field:"typeName",width:80,sortable:false},
				{title:"创建人",field:"userName",width:70,sortable:false},
				{title:"目标天数",field:"proj_countDay",width:60,sortable:false},
				{title:"开始日期",field:"proj_beginDate",width:80,sortable:true},
				{title:"结束日期",field:"proj_endDate",width:80,sortable:true},
				{title:"目标金额",field:"proj_amountGoal",width:80,align:"right",sortable:false},
				{title:"已筹金额",field:"proj_amountRaised",width:80,align:"right",sortable:false},
				{title:"支持数",field:"proj_countSupport",width:60,sortable:false},
				{title:"状态",field:"proj_status",width:80,sortable:true,formatter:statusFormatter},
				{title:"是否删除",field:"proj_deleted",width:70,sortable:true,formatter:function(val,row){if(val==1){return "<span style='background:red;color:white'>已删除</span>";}else{return "--";}}},
				{title:"审批状态",field:"proj_approveStatus",width:80,sortable:true,formatter:function(val,row){if(val==1){return "<span style='background:green;color:white'>已审批</span>";}else if(val==2){return "<span style='background:red;color:white'>审批未通过</span>";}else{return "未审批";}}},
				{title:"审批人",field:"approveUserName",width:80,sortable:true},
				{title:"审批时间",field:"proj_approveTime",width:130,sortable:true},
				{title:"锁定时间",field:"proj_locktime",width:130,sortable:true},
	            {title:"创建时间",field:"proj_created",width:130,sortable:true}				
			]],
	otherfields : []	
};

function statusFormatter(val,row){
	if(val==0){
		return "未开始";
	}else if(val==1){
		return "众筹中";
	}else if(val==2){
		if(row.proj_amountRaised<row.proj_amountGoal){
			return "众筹失败";
		}else{
			return "众筹成功";
		}
	}else if(val==3){
		return "锁定";
	}

}