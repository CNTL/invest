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
		if(userId <= 0){
			window.location.href = "../login.jsp";
		}else{
			window.location.href = "../project/Pay.do?id="+modeid;
		}
	});
	
	if(curstage == "1"){
		$(".project_view .main .content .progress li.s1").addClass("s1_1");
	}else if(curstage == "2"){
		$(".project_view .main .content .progress li.s2").addClass("s2_1");
	}else if(curstage == "5"){
		$(".project_view .main .content .progress li.s3").addClass("s3_1");
	}
});

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