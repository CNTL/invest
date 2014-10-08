$(function () {
	var prefixPath = "../";
	if(typeof(apphost) != "undefined") prefixPath = apphost;
	easyloader.locale = "zh_CN";
	easyloader.base = prefixPath+"js/jquery-easyui/";
	easyloader.theme = "bootstrap";
	
	var tlModules = {
		bootstrap : {
			js : prefixPath+"js/bootstrap/js/bootstrap.min.js",
			css : prefixPath+"js/bootstrap/css/bootstrap.css"
		},
		tldatagrid : {
			js : prefixPath+"admin/script/listpage/tl.datagrid.js",
			css : prefixPath+"js/jquery-easyui/themes/icon.css"
		},
		tldialog : {
			js : prefixPath+"admin/script/listpage/tl.dialog.js"
		},
		easyuicolor : {
			css : prefixPath+"js/jquery-easyui/themes/color.css"
		}
	};
	easyloader.modules = $.extend({}, tlModules, easyloader.modules);
	using(['bootstrap','parser', 'layout','tree','messager','datagrid', 'dialog','menu','tldatagrid','tldialog','easyuicolor'], function () {
		$("#easyui-layout").layout();
		$("#content-layout").layout();
		$("#tree-menu").menu({onClick:treeMenuItemOnClick});
		$("#dic-tree").tree({
			url:'../dic/DicFetcherManager.do?action=tree&sys=0',
			method:"POST",
			onBeforeLoad:function(node,param){
				if(node != null){
					param.root = node.attributes.typeid<=0 ? node.id : node.attributes.typeid;
					param.id = node.attributes.typeid<=0 ? 0 : node.id;
				}
			},
			onSelect : function(node){
				showList(node);
			},
			onContextMenu : function(e,node){
				e.preventDefault();
				$('#dic-tree').tree('select', node.target);
				initTreeMenuItem(node);
				$("#tree-menu").menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}
		});		
		showList(null);
		autoLayoutHeight();
		$(window).resize(reDraw);
	});
});
function treeMenuItemOnClick(item){
	//top.$.messager.alert('debugger',item.name);
	var node = $('#dic-tree').tree('getSelected');
	if(node==null) return false;
	var id = node.id,typeid = node.attributes.typeid;
	var title,width=520,modal=true,minimizable=false,maximizable=false,
		href= "../dic/" + (typeid == -1 ? "DicTypeEdit" : "DicEdit") +".jsp?typeid="+typeid+"&id="+id,
		height=320;
	var toolbars = [{text:'保存',iconCls:'icon-save',handler:function(){
		tldialog.submit("../dic/DicTypeEdit.jsp");
	}},{text:'关闭',iconCls:'icon-cancel',handler:function(){
		tldialog.close();
	}}];
	if(item.name=="new-type"){
		title="新增分类类型";
	}else if(item.name=="new-cat"){
		title="新增分类";
		height=480;
	}else if(item.name=="edit"){
		title="修改";
		height= typeid==0 ? 320 : 480;
	}else if(item.name=="remove"){
		toolbars[0].text = "确定";
		title="删除";
		href = "../dic/delete.jsp?typeid="+typeid+"&id="+id;
		height = 180;
		width = 360;
	}
	tldialog.show(title,href,width,height,modal,minimizable,maximizable,toolbars);
}
function initTreeMenuItem(node){
	//$("#tree-menu").menu('appendItem',{name:'new_type',text: '新建分类类型',iconCls: 'icon-add',onclick: function(){alert('提示：新菜单项！')}});
	var showItems = $('.tree-menu-type');
	var hideItems = $('.tree-menu-cat');
	if(node == null || node.attributes.typeid == -1){
		showItems = $('.tree-menu-type');
		hideItems = $('.tree-menu-cat');
	}else{
		showItems = $('.tree-menu-cat');
		hideItems = $('.tree-menu-type');
	}
	for(var i=0;i<showItems.length;i++){
		$('#tree-menu').menu('showItem',showItems[i]);
	}
	for(var i=0;i<hideItems.length;i++){
		$('#tree-menu').menu('hideItem',hideItems[i]);
	}
}

function showList(node){
	if(node == null || node.attributes.typeid == -1){
		showDicTypeList();
		$('#content-layout').layout('remove','north');
		$('#content-layout').layout('panel','center').panel("setTitle","分类类型");
	}
	else{
		$('#content-layout').layout('add',{region: 'north',height:60,title:'分类类型信息'});
		$('#content-layout').layout('panel','center').panel("setTitle","子分类");
		if(node.attributes.typeid == 0){
			$('#content-layout').layout('panel','north').panel("setTitle","分类类型信息");
			showDicList(node.id,0);
		}else{
			$('#content-layout').layout('panel','north').panel("setTitle","分类信息");
			showDicList(node.attributes.typeid,node.id);
		}
	}
	tldatagrid.init("datagrid",options);
	//top.$.messager.alert('debugger',options.rule);
}

function showDicTypeList(){
	options.itemID = "dt_id";
	options.tbview = "sys_dictionarytype";
	options.sortName = "dt_order";
	options.rule = "dt_valid=1 and dt_sys=0";
	options.frozenColumns = [[{title:"复选框",field:"ck",width:60,sortable:false,checkbox:true},
								{title:"ID",field:"dt_id",width:60,sortable:true}
							]];
	options.columns = [[
						{title:"名称",field:"dt_name",width:120,sortable:true},
						{title:"简码",field:"dt_code",width:120,sortable:true},
						{title:"备注",field:"dt_memo",width:180,sortable:true},
						{title:"排序码",field:"dt_order",width:60,sortable:true}			
					]];
}

function showDicList(typeid,pid){
	options.itemID = "dic_id";
	options.tbview = "sys_dictionary";
	options.sortName = "dic_order";
	options.rule = "dic_valid=1 and dic_typeid="+typeid+" and dic_pid="+pid;
	options.frozenColumns = [[{title:"复选框",field:"ck",width:60,sortable:false,checkbox:true},
								{title:"ID",field:"dic_id",width:60,sortable:true}
							]];
	options.columns = [[
						{title:"名称",field:"dic_name",width:120,sortable:true},
						{title:"简码",field:"dic_code",width:120,sortable:true},
						{title:"TEXT",field:"dic_text",width:120,sortable:true},
						{title:"VALUE",field:"dic_value",width:120,sortable:true},
						{title:"子项数",field:"dic_childCount",width:60,sortable:true},
						{title:"备注",field:"dic_memo",width:180,sortable:true},
						{title:"排序码",field:"dic_order",width:60,sortable:true},
						{title:"修改时间",field:"dic_LastModified",width:130,sortable:true}				
					]];
}

function autoLayoutHeight() {
	var winH = $(window).height();
	
	var main = $("#main");
	var mainTop = main.offset().top;
	var h = winH-mainTop>0 ? winH-mainTop : 0;
	main.height(h);
	$("#easyui-layout").height(main.height()-$("#ribbon").height()-10);
	//$("#content-layout").height(main.height()-$("#ribbon").height()-5);
	$("#easyui-layout").layout("resize");
	//$("#content-layout").layout("resize");
}
function reDraw() {
	autoLayoutHeight();
}