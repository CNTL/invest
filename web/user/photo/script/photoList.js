$(document).ready(function () {
	
	$(".container h3").html("个人相册->"+$("#groupName").val());
	
	$(".boxer").boxer({
	    formatter: formatCaptions
	   
	});
	$("#uploadPhoto").click(function(){
		uploadPhoto();
	});
	
	$("#photogroups").click(function(){
		window.location.href= rootPath +"user/PhotoGroupMa.do?id="+$("#groupID").val();
	});
	 
});

function formatCaptions($target) {
	 
	return "图片描述：<input id=\"photoIntro\" class=\"form-control\" type=\"text\" value=\"" + $target.attr("title") + "\"/><p><button class=\"btn btn-primary\" onclick=\"savePhotoIntro()\" style=\"float:right;margin:2px 5px 0 5px;\">保存</button>&nbsp;&nbsp;&nbsp;<button style=\"float:right;margin:2px 5px 0 5px;\" class=\"btn btn-danger\" onclick=\"delPhoto()\">删除</button></p>";
}

function savePhotoIntro(){
	var photointro = $("#photoIntro").val();
	var photourl = $("#boxer").find(".boxer-image").attr("src");
	var id = findPhotoID(photourl);
	var data = {
			id:id,
			intro:photointro
	};
	$.ajax({
        type:"POST", //请求方式  
        url:"../user/photo.do?a=updatePhoto", //请求路径  
        cache: false,
        data:data,  //传参 
        dataType: 'text',   //返回值类型  
        success:function(data){
    		if(data != null && data.length > 0){
    			$.messager.alert('消息',"修改成功！");
    			window.location.href=window.location.href; 
    		} else {
    			$.messager.alert('修改失败！',data);
    		}
        } ,
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('修改失败！',errorThrown);
		}
    });
	
}
function delPhoto(){
	 
	var photourl = $("#boxer").find(".boxer-image").attr("src");
	var id = findPhotoID(photourl);
	$.ajax({
        type:"GET", //请求方式  
        url:"../user/photo.do?a=delPhoto&id=" + id, //请求路径  
        cache: false,
        dataType: 'TEXT',   //返回值类型  
        success:function(data){
        	if(data != null && data == 'ok'){
        		window.location.href=window.location.href; 
    		} else {
    			$.messager.alert('删除图片失败！',data);
    		}
        } ,
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('删除图片失败！',errorThrown);
		}
    });
}

function findPhotoID(url){
	var photolist = $("#photo-list img");
	var id = 0;
	$.each(photolist,function(i,n){
		if($(this).attr("src")==url){
			id = $(this).attr("data-id");
		}
	});
	return id;
}
function uploadPhoto(){
	var groupID = $("#groupID").val();
	window.open( "../user/photo/uploadImg.jsp?groupID=" + groupID, "_blank" ,
			"height=500,width=700,scrollbars=no,location=no");
}

function myReload(){
	window.location.href=window.location.href;
}