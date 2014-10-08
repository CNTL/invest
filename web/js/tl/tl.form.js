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
	using(['bootstrap','parser', 'layout','messager','datagrid', 'dialog','form',
			'validatebox','textbox','combo','combobox','combotree','combogrid','numberbox',
			'datebox','datetimebox','datetimespinner','calendar','spinner','numberspinner',
			'timespinner','slider','filebox',
			'tldatagrid','tldialog','easyuicolor'], function () {
	});
});

var tlform = {
	submit : function(url){
		top.$.messager.progress();
		$("form#tlform").form('submit',{
			url:url,
			onSubmit: function(param){
				var isValid = $(this).form('validate');
				if (!isValid){
					top.$.messager.alert('debugger',"validate no pass...");
					top.$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
				}
				return isValid;	// 返回false终止表单提交
			},
			success:function(data){
				top.$.messager.progress('close');
			}
		});
	}
};