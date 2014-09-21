var options = {
	itemID:"id",
	tbview:"sys_user",
	url:'../workspace/tblist.do',
	method:'post',
	sortName:"id",
	rule:"deleted=0",
	showFooter:false,
	toolbar: [{
				iconCls: 'icon-add',
				handler: function(){
					tldialog.show("新增","./userEdit.jsp?valueArray=",680,460);
				}
			},'-',{
				iconCls: 'icon-edit',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					tldialog.show("修改","./userEdit.jsp?valueArray="+docIds,680,460);
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
				{title:"姓名",field:"username",width:120,sortable:true},
				{title:"登陆名",field:"code",width:120,sortable:true},
				{title:"手机",field:"mobile",width:120,sortable:true},
				{title:"E-mail",field:"email",width:150,sortable:true},
	            {title:"创建时间",field:"createTime",width:130,sortable:true}				
			]],
	otherfields : []	
};