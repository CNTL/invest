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
        	    			sb.push("<img style=\"width:180px;margin-bottom:60px;\"   id=\"" + d.id + "\" src=\""+rootPath+"static/image/iTunes.jpg"+"\"></a>");
        	    			
        	    		}else{
        	    			sb.push(d.video);
        	    		}
        	    		if(d.video==""){
        	    			sb.push("<div style=\"height:20px;overflow:hidden;\"><span class=\"mp3\">"+rootPath+d.photo+"</span></div>");
        	    			
        	    		}
        	    	 
        	    		sb.push(" </div>");
    	    		});
    	    		sb.push("</div>");
	        		sb.push("</dd>");
	        		sb.push("</dl>");
	        		sb.push("<div class=\"clearfix\"></div>");
    	    		$(".body-container").append(sb.join(""));
    	    	 	$(".mp3").jmp3({  
    	        		showfilename: "false",
    	        	 	backcolor: "#F5F5F5",  
    	        		forecolor: "00ff00",  
    	        		width: 150,  
    	        		showdownload: "false"
    	        	 }); 
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