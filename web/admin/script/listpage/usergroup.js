var options = {
	itemID:"id",
	tbview:"sys_role",
	url:'../workspace/tblist.do',
	method:'post',
	sortName:"orderNo",
	toolbar:'#toolbar',
	rowStyler : function(index,row){
		//if (row.AI_Debt<=0) return 'background-color:green;color:#fff;';
	},
	onDblClickRow : function(rowIndex,rowData){
	},
	frozenColumns : [[{title:"复选框",field:"ck",width:60,sortable:false,checkbox:true},
						{title:"ID",field:"id",width:60,sortable:true}
					]],
	columns : [[
				{title:"名称",field:"name",width:160,sortable:true},
				{title:"备注",field:"memo",width:260,sortable:true},
	            {title:"创建时间",field:"created",width:120,sortable:true}				
			]],
	otherfields : [{name:"排序码",code:"orderNo"}]	
};