var options = {
	itemID:"id",//主键
	tbview:"user_recruit",//表名或试图名称
	url:'../workspace/tblist.do',//
	method:'post',
	sortName:"id",
	rule:"deleted=0",
	showFooter:false,
	toolbar: [{
				text:'发布',
				iconCls: 'icon-ok',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					if(docIds>0){
						tldialog.show("发布职位","./userRecruitCheck.jsp?id="+docIds,800,700);
					}
					else{
						alert("请选择一条记录。")
					}
					
				}
			},'-',{
				text:'删除',
				iconCls: 'icon-remove',
				handler: function(){
					var docIds = tldatagrid.getSelectedIDs();
					if(docIds>0){
						if(confirm("你确定要删除吗？")){
							var url = "../user/recruit.do?a=deleteRecruit&id="+docIds;
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
						tldialog.show("首页排序","./userRecruitOrder.jsp?id="+docIds,800,500);
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
				{title:"公司名称",field:"userName",width:200,sortable:true},
				{title:"职位",field:"jobName",width:100,sortable:true},
				{title:"职位分类",field:"jobCate",width:60,sortable:true},
	            {title:"省市",field:"province",width:60,sortable:true},
				{title:"城市",field:"city",width:120,sortable:true},
				{title:"地址",field:"address",width:120,sortable:true},
				{title:"是否发布",field:"isPub",width:120,sortable:true},
				{title:"首页排序",field:"jobOrder",width:150,sortable:true},
	            {title:"创建时间",field:"createtime",width:130,sortable:true}				
			]],
	searchFields : {
		fields:[
			{name:"公司名称",code:"userName",operator:"LIKE",quote:"true",edittype:"INPUT"},
			{name:"职位",code:"jobName",operator:"LIKE",quote:"true",edittype:"INPUT"},
			{name:"是否发布",code:"isPub",operator:"EQ",quote:"false",edittype:"SELECT",showall:"true",multiple:"false",datatype:"fixed",datas:[{value:"0",text:"未发布"},{value:"1",text:"已发布"}]}
		],
		moreFields:[]
	},
	otherfields : []	
};
