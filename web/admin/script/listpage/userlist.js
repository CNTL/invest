var options = {
	itemID:"id",//主键
	tbview:"user",//表名或试图名称
	url:'../workspace/tblist.do',//
	method:'post',
	sortName:"id",
	rule:"deleted=0",
	showFooter:false,
	toolbar: [{
				iconCls: 'icon-add',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					tldialog.show("认证用户","./userCheck.jsp?id="+docIds,476,700);
				}
			},'-',{
				iconCls: 'icon-add',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					alert(docIds);
				}
			}],	
	rowStyler : function(index,row){
		//if (row.type<=0) return 'background-color:green;color:#fff;';
	},
	onDblClickRow : function(rowIndex,rowData){
	},
	frozenColumns : [[
						
					]],
	columns : [[
				{title:"复选框",field:"ck",width:60,sortable:false,checkbox:true},
				{title:"ID",field:"id",width:60,sortable:true},
				{title:"姓名",field:"name",width:60,sortable:true},
				{title:"用户编码",field:"code",width:60,sortable:true},
				{title:"昵称",field:"perNickName",width:60,sortable:true},
	            {title:"类型",field:"type",width:60,sortable:true},
				{title:"证件号码",field:"identityCard",width:120,sortable:true},
				{title:"是否认证",field:"isRealNameIdent",width:120,sortable:true},
				{title:"职业",field:"perJob",width:120,sortable:true},
				{title:"电话",field:"perPhone",width:150,sortable:true},
	            {title:"创建时间",field:"createTime",width:130,sortable:true}				
			]],
	otherfields : []	
};