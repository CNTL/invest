$(document).ready(function() {
	initCaches();
	
	$("#btnRefreshALL").on("click",refreshAllChaches)
	$("#btnRefresh").on("click",refreshChaches)
});

function initCaches(){
	var dataUrl = "../admin/SysCacheFetcher.do?action=list";
	var loading = -1;
	$.ajax({url: dataUrl, async:true, dataType:"json",
		beforeSend:function(XMLHttpRequest){
			loading = layer.msg("正在获取缓存列表...", 0, 16);
		},
		success: function(datas) {
			for(var i=0;i<datas.length;i++){
				var cache = "<label for='"+datas[i].class+"'><input id='"+datas[i].class+"' value='"+datas[i].class+"' type='checkbox' />"+datas[i].name+"</label><br />";
				$("#caches").append(cache);
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			layer.close(loading);
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			layer.close(loading);
			layer.alert('获取数据失败！', 3);
		}
	});
}

function refreshChaches(){
	var checkedCaches = "";
	$("#caches input[type=checkbox]").each(function(i,n){
		if($(this).is(":checked")){
			if(checkedCaches != "") checkedCaches += ",";
			checkedCaches += $(this).attr("id");
		}
	});
	if(checkedCaches != ""){
		doRefresh(checkedCaches);
	}
}

function refreshAllChaches(){
	var checkedCaches = "";
	$("#caches input[type=checkbox]").each(function(i,n){
		if(checkedCaches != "") checkedCaches += ",";
		checkedCaches += $(this).attr("id");
	});
	if(checkedCaches != ""){
		doRefresh(checkedCaches);
	}
}

function doRefresh(caches){
	var dataUrl = "../admin/SysCacheFetcher.do?action=refresh&caches="+caches;
	var loading = -1;
	$.ajax({url: dataUrl, async:true, dataType:"json",
		beforeSend:function(XMLHttpRequest){
			loading = layer.msg("正在刷新缓存...", 0, 16);
		},
		success: function(datas) {
			layer.alert(datas.msg);
		},
		complete: function(XMLHttpRequest, textStatus){
			layer.close(loading);
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			layer.close(loading);
			layer.alert('刷新缓存失败！', 3);
		}
	});
}