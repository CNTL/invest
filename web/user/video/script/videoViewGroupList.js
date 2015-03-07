$(document).ready(function () {
	videoGroup.initGroup();
});
var videoGroup = {
	initGroup : function(){
		var userID = $("#curUserID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/video.do?a=getVideoGroups&userID=" + userID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	videoGroup.assemble(data);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	assemble : function(result) {
		 
    	$.each(result, function(i,item){
    		var groupID = item.id;
    		var groupName = item.groupName;
    		
    		 
    		/*
    		//添加图片的缩略图
    		//$("#photos").append($("<div><a href='#'><img onclick='videoGroup.clickThumb("+id+")' name='photoList' id='" + id + "' src='"+rootPath+photo+"'></a></div>"));
    		var prefix = '<div class="box" style="width:220px;">';
    		var suffix = '';
    		if((i + 1)%3==0) {
    			prefix = '<div class="box box_last" style="width:220px;">'; 
    			suffix = '<div class="clear"></div>';
    		}
    		var html = prefix +
				            '<div class="people" style="border: 1px #858585 solid;">' +
				                '<div class="pic" style="width:100%;">' +
				                    '<a href="#"><img onclick="videoGroup.clickThumb('+id+')" name="photoList" id="' + id + '" src="'+rootPath+photo+'"></a>' +
				                '</div>' +
				                '<div class="title">' +
				                    '<a href="#" style="text-decoration:none;" onclick="videoGroup.clickThumb('+id+')">'+item.groupName+'</a>' +
				                '</div>' +
				            '</div>' +
				        '</div>' + suffix;
				        */
    		
    		$.ajax({
    	        type:"GET", //请求方式  
    	        url:"../user/video.do?a=getVideos&groupID=" + groupID, //请求路径  
    	        cache: false,
    	        dataType: 'JSON',   //返回值类型  
    	        success:function(data){
    	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
    	        	var sb = [];
    	    		sb.push("<dl id=\""+groupID+"\">");
    	    		sb.push("<dt>"+groupName+"</dt>");
    	    		sb.push("<dd>");
    	    		sb.push("<div>");
    	    		$.each(data,function(p,d){
    	    			sb.push(" <div class=\"thumbnail\">");
    	    			sb.push("<h3 style=\"font-size:16px;font-weight:bold;\">"+d.name+"</h3>");
        	    		if(d.video==""){
        	    			sb.push("<img   id=\"" + d.id + "\" src=\""+rootPath+"user/photo/img/framels_hover.jpg"+"\"></a>")
        	    		}else{
        	    			sb.push(d.video);
        	    		}
        	    	 
        	    		sb.push(" </div>");
    	    		});
    	    		sb.push("</div>");
	        		sb.push("</dd>");
	        		sb.push("</dl>");
	        		sb.push("<div class=\"clearfix\"></div>");
    	    		$(".body-container").append(sb.join(""));
    	        } ,
    			error:function (XMLHttpRequest, textStatus, errorThrown) {
    				    
    			}
    	    });
    		
    	 
    	});
	},
	clickThumb : function(id){
		var userID = $("#curUserID").val();
		window.location.href="../user/VideoMain.do?a=video&id=" + userID + "&infoType=7&groupID=" + id;
	}
}