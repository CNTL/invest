$(document).ready(function () {
	//初始化
	photo.init();
});
var pagei = null;
var photo = {
	init : function(){
		$("#showPhoto").hide();	//初始化时不显示大图
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
	assemble : function(result) {
    	$.each(result, function(i,item){
    		var id = item.id;
    		var photo = item.photo;
    		
    		if(photo == null || photo.length == 0)
    			photo = "user/photo/img/framels_hover.jpg";
    		//添加图片的缩略图
    		var prefix = '<div class="box" style="width:220px;">';
    		var suffix = '';
    		if((i + 1)%4==0) {
    			prefix = '<div class="box box_last" style="width:220px;">'; 
    			suffix = '<div class="clear"></div>';
    		}
    		var html = prefix +
				            '<div class="people" style="border: 1px #858585 solid;">' +
				                '<div class="pic" style="width:100%;">' +
				                    '<a><img onclick="photo.clickThumb('+id+')" name="photoList" id="' + id + '" src="'+rootPath+photo+'"/></a>' +
				                '</div>' +
				                '<div class="title">' +
				                    '<a style="text-decoration:none;">'+item.photoName+'</a>' +
				                '</div>' +
				             '</div>' +
				        '</div>' + suffix;
    		$(".block1").append(html);
    	});
    	
    	$(".block1 div:has(a)").addClass("thumb");
    	
    	$.each(result, function(i,item){
    		var myimg = new Image();
    		myimg.src = $(".block1 a img").get(i).src;
    		$(".block1 div:has(a):eq("+i+")").addClass("pt");
    	});
    	$("#bgblack").css("opacity",0.9);	//显示大图的方块背景设置为透明
    	
    	$("#close").click(function(){
    		//点击按钮close，则关闭大图面板（采用动画）
    		//$("#showPhoto").add("#showPic").fadeOut(400);
    		window.location.href=window.location.href; 
    	});
	},
	clickThumb : function(){
		$(".block1 div a:has(img)").click(function(){
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
		$("#prev").click(function(){
			var currentImg = $("#showPic").find("img");
			var myID = currentImg.attr("id");
			var prevPhoto = photo.getPrevPhoto(myID);
			if(prevPhoto == null) return;
			var prevId = prevPhoto.id;
			var prevSrc = prevPhoto.src;
			if(prevId == null || prevId.length == 0) return;
			$("#showPic").find("img").attr("id",prevId);
			$("#showPic").find("img").attr("src",prevSrc);
			$("#showPic").find("img").attr("style","width:450px; height:450px;");
		});
		
		$("#next").click(function(){
			var currentImg = $("#showPic").find("img");
			var myID = currentImg.attr("id");
			var nextPhoto = photo.getNextPhoto(myID);
			if(nextPhoto == null) return;
			var nextId = nextPhoto.id;
			var nextSrc = nextPhoto.src;
			if(nextId == null || nextId.length == 0) return;
			$("#showPic").find("img").attr("id",nextId);
			$("#showPic").find("img").attr("src",nextSrc);
			$("#showPic").find("img").attr("style","width:450px; height:450px;");
		});
		$("#showPic").find("img").click(function(){
			//点击大图，同样显示下一幅
			var currentImg = $("#showPic").find("img");
			var myID = currentImg.attr("id");
			var nextPhoto = photo.getNextPhoto(myID);
			if(nextPhoto == null) return;
			var nextId = nextPhoto.id;
			var nextSrc = nextPhoto.src;
			if(nextId == null || nextId.length == 0) return;
			$("#showPic").find("img").attr("id",nextId);
			$("#showPic").find("img").attr("src",nextSrc);
			$("#showPic").find("img").attr("style","width:450px; height:450px;");
		});
	},
	getPrevPhoto : function(myID){
		var photo = [];
		var flag = false;
		$("img[name='photoList']").each(function(){
		    var curId =  $(this).attr("id");
		    if(curId == myID){//获取前一个元素
		    	flag = true;
		    } else if(!flag){//取前一个元素的值
		    	photo.id = curId;
				photo.src = $(this).attr("src");
		    }
		});
		return photo;
	},
	getNextPhoto : function(myID){
		var photo = [];
		var flag = false;
		$("img[name='photoList']").each(function(){
			var curId = $(this).attr("id");
			if(flag) {//获取后一个元素
				photo.id = curId;
				photo.src = $(this).attr("src");
		    }
		    if(curId == myID){
		    	flag = true;
		    } else {
		    	flag = false;
		    }
		});
		return photo;
	},
}
function myReload(){
	window.location.href=window.location.href;
}