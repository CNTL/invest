$(document).ready(function () {
	myVideo.init();
});
var myVideo = {
	init : function(){
		myVideo.initVideo();
	},
	initVideo : function(){
		var groupID = $("#groupID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/video.do?a=getVideos&groupID=" + groupID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	myVideo.assemble(data);
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
    		var video = item.video;
    		if(photo == null || photo.length == 0)
    			photo = "../user/photo/img/framels_hover.jpg";
    		if(video == null || photo.length == 0)
    			video = "";
    		
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
				                    '<a href="'+video+'" target="_black"><img name="photoList" id="' + id + '" src="'+rootPath+photo+'"></a>' +
				                '</div>' +
				                '<div class="title">' +
				                    '<a href="'+video+'" target="_black" style="text-decoration:none;">'+item.name+'</a>' +
				                '</div>' +
				            '</div>' +
				        '</div>' + suffix;
    		$(".block1").append(html);
    	});
	}
}