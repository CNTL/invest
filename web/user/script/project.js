$(function(){			
	var menuid = "menu_"+menu;
	$("#"+menuid+" a").addClass("current");
	
	$(".returncontent").on("mouseover",function(){
		layer.tips($(this).attr("data"), this,
			{style: ['background-color:#78BA32; color:#fff', '#78BA32'],
			maxWidth:250,
			guide:0,
			time: 0,
			closeBtn:false
			}
		)
	}).on("mouseout",function(){
		layer.closeTips()
	});
	
	$(".address").on("mouseover",function(){
		var html = "<span style=\"margin-right:8px;\">收件人:</span>"+$(this).attr("user")+"<br />";
		html += "<span style=\"margin-right:8px;\">联系电话:</span>"+$(this).attr("phone")+"<br />";
		html += "<span style=\"margin-right:8px;\">地址:</span>"+$(this).attr("address")+"<br />";
		html += "<span style=\"margin-right:8px;\">邮编:</span>"+$(this).attr("zipcode");
		layer.tips(html, this,
			{style: ['background-color:#78BA32; color:#fff', '#78BA32'],
			maxWidth:250,
			guide:0,
			time: 0,
			closeBtn:false
			}
		)
	}).on("mouseout",function(){
		layer.closeTips()
	});
	
	$(".moneyFormat").each(function(i,n){
		var value = $(this).text();
		if(value){
			if(value.endWith(".00")){
				value = value.substring(0,value.length-3);
				$(this).text(formatMoney(value,0,"",",","."));
			}else{
				$(this).text(formatMoney(value,2,"",",","."));
			}
		}
	});
});

function delFavorite(id){
	if(!id || id<=0) return;
	if(!window.confirm("确定要删除该收藏么？？")){
		return false;
	}
	var dataUrl = "../project/ProjectFetcher.do?action=delfavorite&id="+id;
	var loading = -1;
	$.ajax({url: dataUrl, async:true, dataType:"json",
		beforeSend:function(XMLHttpRequest){
			loading = layer.msg("正在提交数据...", 0, 16);
		},
		success: function(datas) {
			if(datas.success){
				window.location.reload();
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			layer.close(loading);
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			layer.close(loading);
			layer.alert('数据提交失败！', 3);
		}
	});
}

function delProject(id){
	if(!id || id<=0) return;
	if(!window.confirm("确定要删除该项目么？？")){
		return false;
	}
	var dataUrl = "../project/ProjectFetcher.do?action=del&id="+id;
	var loading = -1;
	$.ajax({url: dataUrl, async:true, dataType:"json",
		beforeSend:function(XMLHttpRequest){
			
		},
		success: function(datas) {
			if(datas.success){
				window.location.reload();
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}
var stageDlgId = 0;
function setStage(id){
	if(!id || id<=0) return;
	var iframe = $("<iframe></iframe>");
	iframe.css({
		"width":"100%",
		"height":"600px",
		"border":"0",
		"margin":"0",
		"overflow":"auto"
	});
	iframe.attr("src","../user/ProjectStage.do?projId="+id);
	$("#prostage").empty();
	$("#prostage").append(iframe);
	$("#prostage").show();
	
	$("#prostage").dialog({
		  title       : "设置项目阶段",
		  dialogClass : "modal-lg"
	});
	$(".modal-body").css({"padding":"0"});
}
function viewSupportList(id){
	if(!id || id<=0) return;
	var iframe = $("<iframe></iframe>");
	iframe.css({
		"width":"100%",
		"height":"600px",
		"border":"0",
		"margin":"0",
		"overflow":"auto"
	});
	iframe.attr("src","../project/Support.do?list=1&id="+id);
	$("#supportlist").empty();
	$("#supportlist").append(iframe);
	$("#supportlist").show();
	
	$("#supportlist").dialog({
		  title       : "查看项目支持者",
		  dialogClass : "modal-lg"
	});
	$(".modal-body").css({"padding":"0"});
}
function showSupporter(id){
	if(!id || id<=0) return;
}

function closeStageDlg(){
	layer.close(stageDlgId);
}