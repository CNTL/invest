var options = {
	itemID:"id",//主键
	tbview:"user",//表名或试图名称
	url:'../workspace/tblist.do',//
	method:'post',
	sortName:"id",
	rule:"deleted=0",
	showFooter:false,
	toolbar: [{
				text:'认证用户',
				iconCls: 'icon-ok',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					if(docIds>0){
						tldialog.show("认证用户","./userCheck.jsp?id="+docIds,800,700);
					}
					else{
						alert("请选择一条记录。")
					}
					
				}
			},'-',{
				text:'删除用户',
				iconCls: 'icon-remove',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					if(docIds>0){
						if(confirm("你确定要删除吗？")){
							var url = "../user/user.do?a=deleteUser&id="+docIds;
							alert(url);
							$.get(url, function(data){
								tldialog.closeRefresh();
			        		});
						}
					}
					else{
						alert("请选择一条记录。")
					}
					 
				}
			},'-',{
				text:'首页排序',
				iconCls: 'icon-filter',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					if(docIds>0){
						tldialog.show("","./userOrder.jsp?id="+docIds,800,700);
					}
					else{
						alert("请选择一条记录。")
					}
					 
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
				{title:"序号",field:"perOrder",width:60,sortable:true},
	            {title:"创建时间",field:"createTime",width:130,sortable:true}				
			]],
	otherfields : []	
};
