$(document).ready(function () {
	//初始化
	photo.init();
});
var photo = {
	init : function(){
		$("#showPhoto").hide();	//初始化时不显示大图
		$("#uploadPhoto").click(photo.uploadPhoto);
		//photo.initPhoto();
		var groupID = $("#groupID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/photo.do?a=getPhotos&groupID=" + groupID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	photo.assemble(data);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	uploadPhoto : function(){
		var groupID = $("#groupID").val();
		window.open( "../user/photo/uploadImg.jsp?groupID=" + groupID, "_blank" ,
				"height=500,width=700,scrollbars=no,location=no");
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/photo.do?a=savePhotos", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','上传照片成功！');
	    		} else {
	    			$.messager.alert('修改失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	assemble : function(result) {
    	$.each(result, function(i,item){
    		var id = item.id;
    		var photo = item.photo;
    		
    		if(photo == null || photo.length == 0)
    			return;
    		
    		//添加图片的缩略图
    		$("#photos").append($("<div><a href='#'><img name='photoList' id='" + id + "' src='"+photo+"'></a></div>"));
    		
    	});
    	$("#photos div:has(a)").addClass("thumb");
    	
    	$.each(result, function(i,item){
    		var myimg = new Image();
    		myimg.src = $("#photos a img").get(i).src;
    		$("#photos div:has(a):eq("+i+")").addClass("pt");
    		//根据图片的比例（水平或者竖直），添加不同的样式
    		/*
    		if(myimg.width > myimg.height)
    			$("#photos div:has(a):eq("+i+")").addClass("ls");
    		else
    			$("#photos div:has(a):eq("+i+")").addClass("pt");
    		*/
    	});
    	$("#bgblack").css("opacity",0.9);	//显示大图的方块背景设置为透明
    	
    	$("#close").click(function(){
    		//点击按钮close，则关闭大图面板（采用动画）
    		$("#showPhoto").add("#showPic").fadeOut(400);
    	});
    	photo.clickThumb();
	},
	clickThumb : function(){
		$("#photos div a:has(img)").click(function(){
			//如果点击缩略图，则显示大图
			$("#showPhoto").css({
				//大图的位置根据窗口来判断
				"left":($(window).width()/2-500>20?$(window).width()/2-500:20),
				"top":($(window).height()/2-270>30?$(window).height()/2-270:30)
			}).add("#showPic").fadeIn(400);
			//根据缩略图的地址，获取相应的大图地址
			var myID = $(this).find("img").attr("id");
			var mySrc = $(this).find("img").attr("src");
			$("#showPic").find("img").attr("id",myID);
			$("#showPic").find("img").attr("src",mySrc);
			$("#showPic").find("img").attr("style","width:450px; height:450px;");
		});
		var currentImg;
		var bMargin;
		$("#prev").click(function(){
			var flag = false;
			var prevId="",prevSrc = "";
			currentImg = $("#showPic").find("img");
			var myID = currentImg.attr("id");
			var mySrc = currentImg.attr("src");
			$("img[name='photoList']").each(function(){
			    var curId =  $(this).attr("id");
			    if(curId == myID){//获取前一个元素
			    	flag = true;
			    } else {
			    	prevId=curId;
			    	prevSrc = $(this).attr("src");
			    }
			    if(flag) {
			    	return false;
			    }
			});
			if(prevId == null || prevId.length == 0) return;
			$("#showPic").find("img").attr("id",prevId);
			$("#showPic").find("img").attr("src",prevSrc);
			$("#showPic").find("img").attr("style","width:450px; height:450px;");
		});
		
		$("#next").click(function(){
			var flag = false;
			var nextId="",nextSrc = "";
			currentImg = $("#showPic").find("img");
			var myID = currentImg.attr("id");
			var mySrc = currentImg.attr("src");
			$("img[name='photoList']").each(function(){
				var curId =  $(this).attr("id");
				if(flag) {
					nextId = curId;
			    	nextSrc = $(this).attr("src");
			    	return false;
			    }
			    if(curId == myID){//获取前一个元素
			    	flag = true;
			    }
			});
			if(nextId == null || nextId.length == 0) return;
			$("#showPic").find("img").attr("id",nextId);
			$("#showPic").find("img").attr("src",nextSrc);
			$("#showPic").find("img").attr("style","width:450px; height:450px;");
		});
		
		$("#showPic").find("img").click(function(){
			//点击大图，同样显示下一幅
			$("#next").click();
		});
	}
}
function myReload(){
	window.location.href=window.location.href;
}