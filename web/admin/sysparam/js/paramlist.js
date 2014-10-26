var options = {
	itemID:"dic_id",
	tbview:"sys_dictionary",
	url:'../workspace/tblist.do',
	method:'post',
	sortName:"dic_order",
	rule:"dic_valid=1",
	showFooter:false,
	/*toolbar: [{
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
			}],	*/
	rowStyler : function(index,row){
		//if (row.AI_Debt<=0) return 'background-color:green;color:#fff;';
	},
	onDblClickRow : function(rowIndex,rowData){
	},
	frozenColumns : [[{title:"复选框",field:"ck",width:60,sortable:false,checkbox:true},
								{title:"ID",field:"dic_id",width:60,sortable:true}
							]],
	columns : [[
				{title:"名称",field:"dic_name",width:120,sortable:true},
				{title:"简码",field:"dic_code",width:120,sortable:true},
				{title:"TEXT",field:"dic_text",width:120,sortable:true},
				{title:"VALUE",field:"dic_value",width:120,sortable:true},
				{title:"子项数",field:"dic_childCount",width:60,sortable:true},
				{title:"备注",field:"dic_memo",width:180,sortable:true},
				{title:"排序码",field:"dic_order",width:60,sortable:true},
				{title:"修改时间",field:"dic_LastModified",width:130,sortable:true}				
			]],
	otherfields : []	
};