var options = {
	itemID:"dt_id",
	tbview:"sys_dictionarytype",
	url:'../workspace/tblist.do',
	method:'post',
	sortName:"dt_order",
	rule:"dt_valid=1 and dt_sys=0",
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
						{title:"ID",field:"dt_id",width:60,sortable:true}
					]],
	columns : [[
				{title:"名称",field:"dt_name",width:120,sortable:true},
				{title:"简码",field:"dt_code",width:120,sortable:true},
				{title:"备注",field:"dt_memo",width:180,sortable:true},
				{title:"排序码",field:"dt_order",width:60,sortable:true}			
			]],
	otherfields : []	
};