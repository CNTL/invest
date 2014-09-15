
/* 
 * 该类是设置datagrid服务器分页的辅助类
 * 
 * @author wang.yq
 * @create 2014-9-8 00:01:18
 */

var GridUtils = function(){
	
	var o_options = {
		width: "100%",  
        height: 'auto',  
        striped: true,  
        idField:'id',  
        singleSelect:true,//是否单选  
        selectOnCheck: true,
        checkOnSelect: true,
        pagination:true 
	}
	
	/*
	 * @param tb 表格的jquery对象
	 * @param options datagrid选项
	 */
	var initGrid = function(tb,options){
		
		if(tb==null || tb.length==0){
			return false;
		}
		if(options!=null){
			o_options = $.extend({}, o_options, options);
		}
		
		//初始化参数
		tb.datagrid(o_options); 
		
		tb.datagrid('getPager').pagination({
            pageSize: 10, //每页显示的记录条数，默认为10  
            pageList: [10, 15, 20, 25], //可以设置每页记录条数的列表
            beforePageText: '第',//页数文本框前显示的汉字  
            afterPageText: '页    共 {pages} 页',  
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
            onSelectPage: function(pageNumber, pageSize) {
            	loadData(tb,o_options);//每次更换页面时触发更改 
           }
       	 });
	}
	/*
	 * @param tb 表格的jquery对象
	 * @param options datagrid选项
	 */
	var loadData = function(tb,options){
		
		if(tb==null || tb.length==0){
			return false;
		}
		
        var opts =tb.datagrid('options');
        var pager =tb.datagrid('getPager');
        var _pageNumber =opts.pageNumber;
        var _pageSize =opts.pageSize;
       
       
       var paramData = { 
       		pageNumber:_pageNumber,
       		pageSize:_pageSize
       		};
        $.ajax({
    		url:options.url,
    		dataType:"json",
    		async:false, 
    		data:paramData,
    		success:function(data) {
    			
    			tb.datagrid('loadData', data);
    			pager.pagination({
	           		total:data.total,//总数
	            	pageSize: _pageSize,//行数
	           		pageNumber: _pageNumber//页数
	          	});
    		}
    	});
	}
	
	return {
       
        init: function (tb,options) {
        	
        	initGrid(tb,options);
        	loadData(tb,options);
        	
        }

    };
	
}();

/**
 * 针对对话框进行封装
 */
var dialogUtils = function(){
	
	var o_options = {
			 title: "",
			 url:null,
             width: 600 ,
             height: 400,
             modal: false,
             minimizable:false,
             maximizable: false,
             shadow: false,
             cache: false,
             closed: false,
             collapsible: false,
             resizable: false,
             loadingMessage: '正在加载数据，请稍等片刻......'
		}
	
	//content: '<iframe scrolling="yes" style="width:100%;height:"'+" frameborder="0" scrolling="no"  src="' + href + '" ></iframe>',
	
	var initDialog = function(id,options){
		var n_id = id;
		if(id==null || tb.length==0){
			return false;
		}
		//防止重复ID
		if($("#"+id)!=null&&$("#"+id).length>0){
			
			n_id = n_id+$("#"+id).length.toString();
		}
		
		if(options!=null){
			o_options = $.extend({}, o_options, options);
		}
		
		var dlg = $("<div id='"+n_id.toString()+"' class='easyui-dialog'></div>");
		if(o_options.url!=null&&o_options.url.toString().length>0){
			dlg.append("<iframe id='"+n_id+"iframe' frameborder='0' scrolling='no' src='' style='width:100%;height:"+o_options.height+";'></iframe>");
		}
	
		$("body").append(dlg);
		
		dlg.dialog(o_options);
		

	}
	
	return {
	       
        init: function (id,options) {
        	
        	initDialog(id,options);
        	 
        }

    };
	
}();
