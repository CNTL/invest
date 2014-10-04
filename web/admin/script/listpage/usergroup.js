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
					tldialog.show("新增","./org/UserFetcher.do?action=role",680,460);
				}
			},'-',{
				iconCls: 'icon-edit',
				handler: function(){
					var ids = tldatagrid.getSelectedIDs();
					if(ids.indexOf(",")!=-1){
						top.$.messager.alert('提示消息',"只能选择一条记录进行修改。");
					}else if(ids==""){
						top.$.messager.alert('提示消息',"需要选择一条记录进行修改。");
					}else{
						tldialog.show("修改","./org/UserFetcher.do?action=role&id="+tldatagrid.getSelectedIDs(),680,460);
					}
				}
			},'-',{
				iconCls: 'icon-remove',
				handler: function(){
					var ids = tldatagrid.getSelectedIDs();
					if(ids==""){
						top.$.messager.alert('提示消息',"请选择需要删除的角色。");
					}else{
						tldialog.show("删除","./org/UserFetcher.do?action=del&item=role&id="+tldatagrid.getSelectedIDs(),360,240);
					}
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