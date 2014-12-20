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
function delProject(id){
	if(!id || id<=0) return;
	if(!window.confirm("确定要删除该项目么？？")){
		return false;
	}
	var dataUrl = "../project/ProjectFetcher.do?action=del&id="+id;
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
var stageDlgId = 0;
function setStage(id){
	if(!id || id<=0) return;
	stageDlgId = $.layer({
		type: 2,   //0-4的选择,
		title: "项目进度",
		maxmin: false,
		border: [10, 0.2, '#000'],
		closeBtn: [1, true],
		shadeClose: false,
		fix: true,
		zIndex : 1000,
		area: ['750px', '600px'],
		iframe: {
			src: '../user/ProjectStage.do?projId='+id,
			scrolling: 'auto'
		}
	});
}

function closeStageDlg(){
	layer.close(stageDlgId);
}