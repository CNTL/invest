var tldatagrid = {
	id : "",
	options : null,
	allColumns : [],
	defoptions : {
		showHeader:true,
		showFooter:true,
		rownumbers:true,
		singleSelect:false,
		ctrlSelect:true,
		selectOnCheck:true,
		checkOnSelect:true,
		pagination:true,
		pageNumber:1,
		pageSize:50,
		pageList:[50,100,200],
		sortOrder:"desc",
		remoteSort:true,
		border:false,
		fit:true,
		fitColumns:false,
		resizeHandle:'right'
	},
	//init方法只允许在初始化时执行一次
	init : function(id,opts){
		tldatagrid.id = id;
		tldatagrid.setAllColumns(opts);
		if(opts.searchFields && opts.searchFields.fields && opts.searchFields.fields.length>0){
			e5searchBox.init(opts.searchFields);
		}
		
		tldatagrid.options = $.extend({},tldatagrid.defoptions,opts);
		//tldatagrid.setDispalyFields();
		tldatagrid.initdatagrid(opts);
	},
	setLoader : function(){
		tldatagrid.options.loader = function(param,success,error){
			param.queryconditions = "";//e5_queryform.getQuery();
			param.searchfields = JSON.stringify(tldatagrid.options.searchFields);			
			param.selectfields = JSON.stringify({fields:tldatagrid.getSelectFields()});
			param.sumfields = JSON.stringify({fields:tldatagrid.getSumableFields()});
			param.primarykey = tldatagrid.options.itemID;
			param.tbview = tldatagrid.options.tbview;
			if (!tldatagrid.options.url) {
				return false;
			}
			$.ajax({
				type : tldatagrid.options.method,
				url : tldatagrid.options.url+(tldatagrid.options.url.indexOf('?')>=0?"&":"?")+"action=getdata",
				data : param,
				dataType : "json",
				success : function (data) {
					success(data);
				},
				error : function (XMLHttpRequest, textStatus, errorThrown) {
					error.apply(this, arguments);
				}
			});
		};
	},
	initdatagrid : function(options){
		tldatagrid.options = $.extend({},tldatagrid.defoptions,options);
		tldatagrid.setLoader();
		var dg = $('#'+tldatagrid.id);
		dg.datagrid(tldatagrid.options);

		var pager = dg.datagrid('getPager');
		pager.pagination({
			buttons:[{
				iconCls:'icon-xls',
				handler:function(){
					tldatagridExport.showMsg("<div style='text-align:center;'><h4><b>正在准备数据...</b></h4></div><div style='text-align:center;margin-top:20px;'><img src='../images/loadingbar.gif' border='0' alt='正在做导出数据的操作'/></div>");
					
					var docs = dg.datagrid("getSelections");
					var docIds = "";
					for(var i=0;i<docs.length;i++){
						if(docIds!="") docIds+=",";
						docIds += docs[i].SYS_DOCUMENTID;
					}
					var param = {};
					param.queryconditions = "";//e5_queryform.getQuery();
					param.searchfields = JSON.stringify(tldatagrid.options.searchFields);			
					param.selectfields = JSON.stringify({fields:tldatagrid.getSelectFields()});
					param.sumfields = JSON.stringify({fields:tldatagrid.getSumableFields()});
					param.primarykey = tldatagrid.options.itemID;
					param.tbview = tldatagrid.options.tbview;
					param.selectedID = docIds;					
					if (!tldatagrid.options.url) {
						return false;
					}
					$.ajax({
						type : tldatagrid.options.method,
						url : tldatagrid.options.url+(tldatagrid.options.url.indexOf('?')>=0?"&":"?")+"action=export",
						data : param,
						dataType : "json",
						success : function (data) {
							tldatagridExport.showMsgDL(data.xlspath);
						},
						error : function (XMLHttpRequest, textStatus, errorThrown) {
							top.$.messager.alert('导出Excel',"导出失败...<br />");
						}
					});
				}
			},{
				iconCls:'icon-setting',
				handler:function(){
					//top.$.messager.alert('设置',"功能未实现");
					showDialog(780,560,"true", "ListPageSetting.do?action=setting","列表设置");
				}
			}]
		});
	},
	reload : function(){
		var param = {};
		param.queryconditions = e5_queryform.getQuery();
		param.searchfields = JSON.stringify(tldatagrid.options.searchFields);			
		param.selectfields = JSON.stringify({fields:tldatagrid.getSelectFields()});
		param.sumfields = JSON.stringify({fields:tldatagrid.getSumableFields()});
		param.primarykey = tldatagrid.options.itemID;
		$("#"+tldatagrid.id).datagrid("load",param);
	},
	setDispalyFields : function(){
		$.ajax({
			type : "get",
			url : "ListPageSetting.do?action=get",
			data : {PERSONSETTING_CFGITEM:getListPageFlag()},
			dataType : "json",
			success : function (data) {
				if(data!=null){
					var codes = data.configvalue;
					if(codes!=""){
						var fieldArr= new Array();
						fieldArr = codes.split(",");
						var cols = [[]];
						var fcols = [[]];
						for(var i=0;i<fieldArr.length;i++){
							//for(var j=0;j<tldatagrid.allColumns.length;j++){
							//	if(fieldArr[i]==tldatagrid.allColumns[j].field){
							//		cols[0].push(tldatagrid.allColumns[j]);
							//	}
							//}
							var inFclos = false;
							for(var f=0;f<options.frozenColumns[0].length;f++){
								if(fieldArr[i]==options.frozenColumns[0][f].field){
									fcols[0].push(options.frozenColumns[0][f]);
									inFclos = true;
								}
							}
							if(!inFclos){
								for(var f=0;f<options.columns[0].length;f++){
									if(fieldArr[i]==options.columns[0][f].field){
										cols[0].push(options.columns[0][f]);
									}
								}
							}
						}
						tldatagrid.options.frozenColumns = fcols;
						tldatagrid.options.columns = cols;
					}
				}
				tldatagrid.initdatagrid(tldatagrid.options);
			},
			error : function (XMLHttpRequest, textStatus, errorThrown) {
				tldatagrid.initdatagrid(tldatagrid.options);
			}
		});
	},
	setAllColumns : function(defOpts){
		tldatagrid.allColumns = [];
		if(defOpts.frozenColumns.length>0){
			for(var i=0;i<defOpts.frozenColumns.length;i++){
				for(var j=0;j<defOpts.frozenColumns[i].length;j++){
					//if(!defOpts.frozenColumns[i][j].checkbox){
						var hasField = false;
						for(var k=0;k<tldatagrid.allColumns.length;k++){
							if(tldatagrid.allColumns[k].field==defOpts.frozenColumns[i][j].field){
								hasField = true;
							}
						}
						if(!hasField){
							tldatagrid.allColumns.push(defOpts.frozenColumns[i][j]);
						}
					//}
				}
			}
		}
		if(defOpts.columns.length>0){
			for(var i=0;i<defOpts.columns.length;i++){
				for(var j=0;j<defOpts.columns[i].length;j++){
					//if(!defOpts.columns[i][j].checkbox){
						var hasField = false;
						for(var k=0;k<tldatagrid.allColumns.length;k++){
							if(tldatagrid.allColumns[k].field==defOpts.columns[i][j].field){
								hasField = true;
							}
						}
						if(!hasField){
							tldatagrid.allColumns.push(defOpts.columns[i][j]);
						}
					//}
				}
			}
		}
	},
	getSumableFields : function(){
		var defOpts = tldatagrid.options;
		var selectfields = [];
		if(defOpts.frozenColumns.length>0){
			for(var i=0;i<defOpts.frozenColumns.length;i++){
				for(var j=0;j<defOpts.frozenColumns[i].length;j++){
					if(defOpts.frozenColumns[i][j].sumable){
						var hasField = false;
						for(var k=0;k<selectfields.length;k++){
							if(selectfields[k].code==defOpts.frozenColumns[i][j].field){
								hasField = true;
							}
						}
						if(!hasField){
							selectfields.push({name:defOpts.frozenColumns[i][j].title,code:defOpts.frozenColumns[i][j].field});
						}
					}
				}
			}
		}
		if(defOpts.columns.length>0){
			for(var i=0;i<defOpts.columns.length;i++){
				for(var j=0;j<defOpts.columns[i].length;j++){
					if(defOpts.columns[i][j].sumable){
						var hasField = false;
						for(var k=0;k<selectfields.length;k++){
							if(selectfields[k].code==defOpts.columns[i][j].field){
								hasField = true;
							}
						}
						if(!hasField){
							selectfields.push({name:defOpts.columns[i][j].title,code:defOpts.columns[i][j].field});
						}
					}
				}
			}
		}
		return selectfields;
	},
	getDispalyFields : function(containCK){
		var defOpts = tldatagrid.options;
		var selectfields = [];
		if(defOpts.frozenColumns.length>0){
			for(var i=0;i<defOpts.frozenColumns.length;i++){
				for(var j=0;j<defOpts.frozenColumns[i].length;j++){
					if(defOpts.frozenColumns[i][j].checkbox){
						if(containCK){
							var hasField = false;
							for(var k=0;k<selectfields.length;k++){
								if(selectfields[k].code==defOpts.frozenColumns[i][j].field){
									hasField = true;
								}
							}
							if(!hasField){
								selectfields.push({name:defOpts.frozenColumns[i][j].title,code:defOpts.frozenColumns[i][j].field});
							}
						}
					}else{
						var hasField = false;
						for(var k=0;k<selectfields.length;k++){
							if(selectfields[k].code==defOpts.frozenColumns[i][j].field){
								hasField = true;
							}
						}
						if(!hasField){
							selectfields.push({name:defOpts.frozenColumns[i][j].title,code:defOpts.frozenColumns[i][j].field});
						}
					}
				}
			}
		}
		if(defOpts.columns.length>0){
			for(var i=0;i<defOpts.columns.length;i++){
				for(var j=0;j<defOpts.columns[i].length;j++){
					if(defOpts.columns[i][j].checkbox){
						if(containCK){
							var hasField = false;
							for(var k=0;k<selectfields.length;k++){
								if(selectfields[k].code==defOpts.columns[i][j].field){
									hasField = true;
								}
							}
							if(!hasField){
								selectfields.push({name:defOpts.columns[i][j].title,code:defOpts.columns[i][j].field});
							}
						}
					}else{
						var hasField = false;
						for(var k=0;k<selectfields.length;k++){
							if(selectfields[k].code==defOpts.columns[i][j].field){
								hasField = true;
							}
						}
						if(!hasField){
							selectfields.push({name:defOpts.columns[i][j].title,code:defOpts.columns[i][j].field});
						}
					}
				}
			}
		}
		return selectfields;
	},
	getSelectFields : function(){
		var selectfields = tldatagrid.getDispalyFields(false);
		for(var i=0;i<tldatagrid.options.otherfields.length;i++){
			var hasField = false;
			for(var k=0;k<selectfields.length;k++){
				if(selectfields[k].code==tldatagrid.options.otherfields[i].code){
					hasField = true;
				}
			}
			if(!hasField){
				selectfields.push(tldatagrid.options.otherfields[i]);
			}
		}
		return selectfields;
	}
}