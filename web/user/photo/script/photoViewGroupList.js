$(document).ready(function () {
	photoGroup.initGroup();
	
});
function formatCaptions($target) {
	 
	return "<h3>图片描述：" + $target.attr("title") + "</h3>";
}
var photoGroup = {
	initGroup : function(){
		var userID = $("#curUserID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/photo.do?a=getPhotoGroups&userID=" + userID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	        	if(!data||typeof Object.prototype.toString.call(data) == "[object Array]"||!data.length)return;
	        	photoGroup.assemble(data);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	assemble : function(result) {
		
    	$.each(result, function(i,item){
			$.ajax({
		        type:"GET", //请求方式  
		        url:"../user/photo.do?a=getPhotos&groupID="+item.id, //请求路径  
		        cache: false,
		        dataType: 'JSON',   //返回值类型  
		        async:false,
		        success:function(data){
		        	var sb = [];
	        		sb.push("<dl id=\""+item.id+"\">");
	        		sb.push("<dt>"+item.groupName+"</dt>");
	        		sb.push("<dd>");
	        		sb.push("<div>");
		        	if(data){
		        		$.each(data,function(p,n){
		        			
			        		 
		    				var imgurl = rootPath+n.photo;
		    				sb.push("<a href=\""+imgurl+"\" class=\"boxer\" title=\""+n.intro+"\" data-gallery=\"gallery\" >");
		    				sb.push("<img style=\"height:200px;margin-bottom:5px;\" data-id=\""+n.id+"\" src=\""+imgurl+"\" alt=\""+n.intro+"\" /> ");
		    				sb.push("</a>");
			        		
			        		
			        		
		        		});
		        		
		        	}
		        	sb.push("</div>");
	        		sb.push("</dd>");
	        		sb.push("</dl>");
	        		$(".body-container").append(sb.join(""));
	        		$(".boxer").boxer({
	        		    formatter: formatCaptions
	        		});
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					  
				}
		    });
    		
    	});
    	
	}
}