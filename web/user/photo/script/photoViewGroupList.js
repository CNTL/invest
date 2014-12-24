$(document).ready(function () {
	photoGroup.initGroup();
});
var photoGroup = {
	initGroup : function(){
		var userID = $("#userID").val();
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
    		var id = item.id;
    		var photo = item.groupPhoto;
    		if(photo == null || photo.length == 0)
    			photo = "user/photo/img/framels_hover.jpg";
    		//添加图片的缩略图
    		//$("#photos").append($("<div><a href='#'><img onclick='photoGroup.clickThumb("+id+")' name='photoList' id='" + id + "' src='"+rootPath+photo+"'></a></div>"));
    		var prefix = '<div class="box" style="width:220px;">';
    		var suffix = '';
    		if((i + 1)%4==0) {
    			prefix = '<div class="box box_last" style="width:220px;">'; 
    			suffix = '<div class="clear"></div>';
    		}
    		var html = prefix +
				            '<div class="people" style="border: 1px #858585 solid;">' +
				                '<div class="pic" style="width:100%;">' +
				                    '<a href="#"><img onclick="photoGroup.clickThumb('+id+')" name="photoList" id="' + id + '" src="'+rootPath+photo+'"></a>' +
				                '</div>' +
				                '<div class="title">' +
				                    '<a href="#" style="text-decoration:none;" onclick="photoGroup.clickThumb('+id+')">'+item.groupName+'</a>' +
				                '</div>' +
				             '</div>' +
				        '</div>' + suffix;
    		$(".block1").append(html);
    	});
	},
	clickThumb : function(id){
		var userID = $("#userID").val();
		window.location.href="../user/PhotoMain.do?a=photo&id=" + userID + "&infoType=6&groupID=" + id; 
	}
}