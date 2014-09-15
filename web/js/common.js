/*
 * 设置datatables加载参数
 * @id 表格id
 * @url 服务的获取数据url
 * @aoColumns 列的定义
 * @querys 查询的参数
 * @option 重载默认参数
 */
function search(id, url, aoColumns, querys,option) { 
	
	var o_option = {
			"bPaginate" : true,  
	        "bAutoWidth" : false,  
	        "bProcessing": true, 
	        "bStateSave": true,
	        "bFilter": false,  
	        "bJQueryUI": true,       
	        "sPaginationType": "full_numbers",  
	        "sSearch": "搜索",    
	        "oLanguage": {                          //汉化  
	            "sLengthMenu": "每页显示 _MENU_ 条记录",  
	            "sZeroRecords": "没有检索到数据",  
	            "sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",  
	            "sInfoEmtpy": "没有数据",  
	            "sProcessing": "正在加载数据...",  
	            "oPaginate": {  
	                "sFirst": "首页",  
	                "sPrevious": "前页",  
	                "sNext": "后页",  
	                "sLast": "尾页"  
	            }  
	        },   
	        "bServerSide": true,  
	        "sServerMethod": "POST",  
	        "sAjaxSource": url,
	        "fnServerParams": getServerParams,
	        "fnServerData": getServerData, 
	        "aoColumns": aoColumns
	};
	//重载默认参数
	if(option!=null){
		o_option = $.extend({}, o_option, option);
	}
	var dt = $('#' + id).dataTable(o_option);
	
	/*
	 * 组装查询参数,内部函数
	 */
	function getServerParams(aoData){
		if(querys != null && querys.length > 0){
			for(var i = 0; i < querys.length; i++){
				var query = querys[i];
				if(query != "" && query.indexOf("=") > 0){
					var queryArray= new Array();
					queryArray = query.split("=");
					if(queryArray != null && queryArray.length == 2){
						aoData.push({"name": queryArray[0], "value": queryArray[1]});  
					}
						
				}
			}
		}
	}

	/*
	 * 获取服务端数据，内部函数
	 */
	function getServerData(sSource, aoData, fnCallback ){
		$.ajax({                     
	        "dataType": 'json',           
	        "type": "post",                    
	        "url": sSource,                    
	        "data": aoData,                        
	        "success": function(resp) {
				 fnCallback(resp);
				 clickRow(id);
			}
	  });             
	}
}


//添加一行  
function addItem(id) {  
    $("#" + id).dataTable().fnAddData(  
        //[$("#xm").val(), $("#xb").val(), $("#age").val()]  
    );  

    clickRow(id);//每添加一行则添加每行单击变色事件  
}  
//添加每行单击变色事件  
var clickRowIndex,valueArray;
//删除选中行  
function deleItem(id) { 
    $("#" + id).dataTable().fnDeleteRow(clickRowIndex);//删除指定行号的行  
}  

//清空所有行  
function clearTable(id) {  
    $("#" + id).dataTable().fnClearTable();  
}  
  
function clickRow(id) {  
    $("#" + id +" tbody tr").click(function (e) {
        var rowIndex = $(this).context._DT_RowIndex;//获得单击行行号  
        var values = $(this).context.innerText;
        var patt = new RegExp(/.+/g);
        valueArray = values.match(patt);//values.replace(/\\\r\\n\\r\\n/g, ",");
        clickRowIndex = rowIndex;  
        $(this).addClass("mySelected").siblings().removeClass("mySelected");  
    });  
}
function openwindow(url,name,iWidth,iHeight)
{
	var url; //转向网页的地址;
	var name; //网页名称，可为空;
	var iWidth; //弹出窗口的宽度;
	var iHeight; //弹出窗口的高度;
	//var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	//var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	
	//window.open(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
	
	var dialog = art.dialog({id: 'N369012',title: name,width:iWidth,height:iHeight});

	// jQuery ajax   
	$.ajax({
	    url: url,
	    success: function (data) {
	        dialog.content(data);
	        dialog.show();
	    },
	    cache: false
	});
}