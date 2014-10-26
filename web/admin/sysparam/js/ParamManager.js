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
			url:'../admin/ParamFetcherManager.do?action=mtree',
			method:"POST",
			onBeforeLoad:function(node,param){
				param.typeid = 0;
				param.id = 0;
				if(node != null){
					param.typeid = (node.attributes.istype==1) ? node.id : node.attributes.typeid;
					param.id = (node.attributes.typeid<0 || node.attributes.istype==1) ? 0 : node.id;
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
	var id = node.id,pid=node.attributes.pid,typeid = node.attributes.typeid,istype=node.attributes.istype;
	var title,width=520,modal=true,minimizable=false,maximizable=false,action="",height=320,
		submitUrl="../dic/Dictionary.do?action="+(item.name=="new-type"?"new-cat":item.name)+"&istype="+istype;
	
	var toolbars = [{text:'保存',iconCls:'icon-save',handler:function(){
		tldialog.submit(submitUrl,function(data){
			tldialog.closeRefresh();
			var refreshNode = node;
			if(item.name=="edit" || item.name=="remove"){
				refreshNode = $('#dic-tree').tree('getParent',node.target);
			}else if(typeof(node.children)== "undefined" || node.children.length==0){
				refreshNode = $('#dic-tree').tree('getParent',node.target);
			}
			$('#dic-tree').tree('reload',refreshNode.target);
		});
	}},{text:'关闭',iconCls:'icon-cancel',handler:function(){
		tldialog.close();
	}}];
	if(item.name=="new-type"){
		pid = istype==1 ? 0 : pid;
		id = istype==1 ? 0 : id;
		title="新增参数类型";
		action = "add";
	}else if(item.name=="new-cat"){
		pid = istype==1 ? 0 : pid;
		id = istype==1 ? 0 : id;
		title="新增参数";
		action = "add";
		height=480;
	}else if(item.name=="edit"){
		title="修改";
		action = istype==1 ? "typeedit" : "edit";
		height= istype==1 ? 320 : 480;
	}else if(item.name=="remove"){
		toolbars[0].text = "确定";
		title="删除";
		action = istype==1 ? "deltype" : "delete";
		height = 180;
		width = 360;
	}
	
	var href= "../dic/DicFetcherManager.do?action="+action+"&typeid="+typeid+"&pid="+pid+"&id="+id
	tldialog.show(title,href,width,height,modal,minimizable,maximizable,toolbars);
}
function initTreeMenuItem(node){
	//$("#tree-menu").menu('appendItem',{name:'new_type',text: '新建分类类型',iconCls: 'icon-add',onclick: function(){alert('提示：新菜单项！')}});
	var showItems = $('.tree-menu-type');
	var hideItems = $('.tree-menu-cat');
	if(node == null || node.attributes.istype == 1){
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
	if(node == null) return;
	if(node == null || node.attributes.istype == 1){
		options.rule = "dic_valid=1 and dic_typeid="+node.attributes.typeid+" and dic_pid>0";
		//$('#content-layout').layout('remove','north');
		//$('#content-layout').layout('panel','center').panel("setTitle","分类类型");
	}
	else{
		//$('#content-layout').layout('add',{region: 'north',height:60,title:'分类类型信息'});
		//$('#content-layout').layout('panel','center').panel("setTitle","子分类");
		//if(node.attributes.istype == 1){
		//	$('#content-layout').layout('panel','north').panel("setTitle","分类类型信息");
		//	showDicList(node.id,0);
		//}else{
		//	$('#content-layout').layout('panel','north').panel("setTitle","分类信息");
		//	showDicList(node.attributes.typeid,node.id);
		//}
		options.rule = "dic_valid=1 and dic_typeid="+node.attributes.typeid+" and dic_pid="+node.id;
	}
	tldatagrid.init("datagrid",options);
	//top.$.messager.alert('debugger',options.rule);
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