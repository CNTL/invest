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
		jqvalidation_cn : {
			js : prefixPath+"js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"
		},
		jqvalidation : {
			js : prefixPath+"js/plugin/jquery-validate/js/jquery.validationEngine.js",
			css : prefixPath+"js/plugin/jquery-validate/css/validationEngine.jquery.css"
		},
		tlsearchBox : {
			js : prefixPath+"admin/script/listpage/tl.searchbox.js",
			css : prefixPath+"admin/script/listpage/tl.searchbox.css"
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
	using(['bootstrap','jqvalidation_cn','jqvalidation','parser', 'layout','messager', 'datagrid','dialog','tlsearchBox','tldatagrid','tldialog','easyuicolor'], function () {
		//alert("Finish!");
		$("#easyui-layout").layout();
		tldatagrid.init("datagrid",options);
		//$("#searchList").click(search);
		//$("#toggleSearchAdvList").click(advanceSearch);
		//autoLayoutHeight();
		$(window).resize(reDraw);
		reDraw();
		//$("#divQueryCust").resize(reDraw);
		//initQueryForm();		 
	});
});

function autoLayoutHeight() {
	var winH = $(window).height();
	
	var main = $("#main");
	var mainTop = main.offset().top;
	var h = winH-mainTop>0 ? winH-mainTop : 0;
	main.height(h);
	$("#easyui-layout").height(main.height()-$("#ribbon").height()-10);
	$("#easyui-layout").layout("resize");
}

function search() {
	tldatagrid.reload();
}
function reDraw() {
	autoLayoutHeight();
	$('#datagrid').datagrid('resize');
}


function dateFormatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	if (y == 1800 && m == 1 && d == 1)
		return "";
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}
function dateParser(s) {
	if (!s)
		return dateParser('1800-01-01');
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}