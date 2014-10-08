var tldialog = {
	openWin : null,
	show : function(title, href, width, height, modal, minimizable, maximizable,buttons,toolbars) {
		openWin = top.$('<div id="tl_dlg_win" class="easyui-dialog" closed="true"></div>').appendTo(top.document.body);
		openWin.dialog({
			title: title,
			width: width === undefined ? 600 : width,
			height: height === undefined ? 400 : height,
			content: '<iframe id="tl_dlg_win_iframe" scrolling="yes" frameborder="0"  src="' + href + '" style="width:100%;height:99%;"></iframe>',
			modal: modal === undefined ? true : modal,
			minimizable: minimizable === undefined ? false : minimizable,
			maximizable: maximizable === undefined ? false : maximizable,
			shadow: false,
			cache: false,
			closed: false,
			collapsible: false,
			resizable: true,
			loadingMessage: '正在加载数据，请稍等片刻......',
			onClose : function(){ 
				openWin.dialog("destroy"); 
			},
			buttons : buttons
		});
		tldialog.autoHeight();
		top.$("#tl_dlg_win").resize(tldialog.autoHeight);
	},
	submit : function(url){
		var f = top.$("#tl_dlg_win_iframe");//.contents().find("#tlform");
		f[0].contentWindow.tlform.submit(url);
	},
	close : function(){
		//top.$.messager.alert('debugger',"tldialog.close()");
		openWin.dialog("close"); 
		if(openWin != null) openWin = null;
	},
	closeRefresh:function(){
		tldatagrid.reload();
		tldialog.close();
	},
	autoHeight : function(){
		var PH = top.$("#tl_dlg_win").height();
		top.$("#tl_dlg_win_iframe").height(PH-6);
	}
};