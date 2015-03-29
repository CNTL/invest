$(function(){
	$(".project_view .main .content .desc img").css("max-width","640px");
	$(".project_view .main .content .desc p img").css("max-width","608px");
	$(".project_view .main .content .desc p").css("text-indent","2em").css("font-size","14px");
	
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
	
	$(".support div.returncontent").each(function(i,n){
		$(this).html($(this).html().replace(/[\r\n]/g,"<br />"));
		//处理图片
		var modeid = $(this).attr("modeid");
		var imgs = $(this).attr("imgs");
		var imgArry = imgs.split(";");
		var html = "";
		for(var i in imgArry){
			if(imgArry[i]!=""){
				html += "<a href=\"javascript:;\" onclick=\"showModePhotosPage("+modeid+","+i+");\"><img style=\"width:80px;height:58px;margin-left:5px;\" src=\""+webroot+imgArry[i]+"\" /></a>";
			}
		}
		if(html != ""){
			html = "<div id=\"support_imgs_"+modeid+"\" style=\"width:100%;height:60px;margin-top:5px;margin-bottom:5px;\">"+html+"</div>";
			$(this).after(html);
			//layer.photosPage({
			//	parent: '#support_imgs_'+modeid,
			//	title: '直接获取页面元素包含的所有图片'
			//	//id: 112 //相册id，可选
			//	//start: 0
			//});
		}
	});
	
	$(".supportBtn").on("click",function(){
		var modeid = $(this).attr("modeid");
		var userId = $(this).attr("userid");
		//if(userId <= 0){
			//window.location.href = "../login.jsp";
		//}else{
			window.location.href = "../project/Pay.do?id="+modeid;
		//}
	});
	
	if(curstage == "1"){
		$(".project_view .main .content .progress li.s1").addClass("s1_1");
	}else if(curstage == "2"){
		$(".project_view .main .content .progress li.s2").addClass("s2_1");
	}else if(curstage == "5"){
		$(".project_view .main .content .progress li.s3").addClass("s3_1");
	}
	
	$("#jpForm").validationEngine("attach",{
		autoPositionUpdate:false,//是否自动调整提示层的位置
		scroll:false,//屏幕自动滚动到第一个验证不通过的位置
		focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
		promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
	});
	if($("#amountJP").length>0){
		var oldValue = $("#amountJP").attr("oldvalue");
		var goal = $("#amountJP").attr("goal");
		if(parseFloat(oldValue)<parseFloat(goal)){
			oldValue = goal;
		}
		$("#amountJP").attr("placeholder","输入竞拍金额");
	}
	var max = 0;
	$("#price-list").find("div .pgs").each(function(i,n){
		 var price = parseFloat($(this).attr("data-price"));
		 if(max <price){
			 max = price;
		 }
	});
	
	$("#price-list").find("div .pgs").each(function(i,n){
		 var price = parseFloat($(this).attr("data-price"));
		 var widthper = 100*(price/max).toString()+"%";
		 $(this).css("width",widthper);
	});	
});

function addFavorite(id){
	if(!id || id<=0) return;
	var dataUrl = "../project/ProjectFetcher.do?action=addfavorite&id="+id;
	var loading = -1;
	$.ajax({url: dataUrl, async:true, dataType:"json",
		beforeSend:function(XMLHttpRequest){
			 
		},
		success: function(datas) {
			if(datas.success){
				window.location.reload();
			}else{
				
				$.messager.alert(datas.msg);
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			 
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			 
			$.messager.alert("数据提交失败！");
			
		}
	});
}

function showModePhotosPage(modeid,start){
	layer.photos({
		html: $("#returncontent_"+modeid).html(),//自定义的html，显示在层右侧
		tab: function(obj){}, //图片切换操作的回调，obj返回了图片pid和name
		page: { //直接获取页面指定区域的图片，他与上述异步不可共存，只能择用其一。
			parent: '#support_imgs_'+modeid,  //图片的父元素选择器，如'#imsbox',
			start: start, //初始显示的图片序号，默认0
			title: '' //相册标题
		}
	});
}

function jingpai(id){
	if(validJpForm()){
		$.messager.confirm("竞拍注意事项","你确定要竞拍吗？点击“确定”按钮，表示接受竞拍协议<a target=\"_blank\" href=\"../help/RegDoc.do\" style=\"color:#55acef;\">《项目服务协议》</a>",confirmjingpai,id);
	}
}
function confirmjingpai(id){
	var oldValue = $("#amountJP").attr("oldvalue");
	var goal = $("#amountJP").attr("goal");
	if(parseFloat(oldValue)<parseFloat(goal)){
		oldValue = goal;
	}
	var value = $("#amountJP").val();
	
	if(parseFloat(oldValue)>=parseFloat(value)){
	
		$.messager.alert("竞拍金额不能小于：￥"+oldValue);
		return false;
	}
	var anonymous = 0;
	if($("#anonymousJP").attr("checked")!=undefined) anonymous = 1;
	
	if(!id || id<=0) return;
	var dataUrl = "../project/ProjectFetcher.do?action=auction&id="+id;
	 
	$.ajax({url: dataUrl, async:true, dataType:"json",
		data :{id:id,amount:value,anonymous:anonymous},
		beforeSend:function(XMLHttpRequest){
			 
		},
		success: function(datas) {
			if(datas.success){
				$.messager.popup(datas.msg,function(){window.location.reload();});
			}else{
				 
				$.messager.alert(datas.msg);
			}
		},
		complete: function(XMLHttpRequest, textStatus){
			 
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			
			$.messager.popup("数据提交失败！");
		}
	});
}
function validJpForm() {
	if (!$("#jpForm").validationEngine("validate")){
		//验证提示
		$("#jpForm").validationEngine("updatePromptsPosition");
		return false;
	}
	return true;
}