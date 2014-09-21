var options = {
	itemID:"id",
	tbview:"sys_role",
	url:'../workspace/tblist.do',
	method:'post',
	sortName:"orderNo",
	rule:"deleted=0",
	showFooter:false,
	toolbar: [{
				iconCls: 'icon-add',
				handler: function(){
					tldialog.show("新增","http://m.163.com",680,460);
				}
			},'-',{
				iconCls: 'icon-edit',
				handler: function(){
					tldialog.show("修改","http://m.baidu.com",680,460);
				}
			}],
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
	            {title:"创建时间",field:"created",width:130,sortable:true}				
			]],
	otherfields : [{name:"排序码",code:"orderNo"}]	
};