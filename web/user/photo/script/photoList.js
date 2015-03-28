$(document).ready(function () {
	
	$(".container h3").html("个人相册->"+$("#groupName").val());
	
	$(".boxer").boxer({
	    formatter: formatCaptions
	   
	});
	$("#uploadPhoto").click(function(){
		uploadPhoto();
	});
	
	$("#photogroups").click(function(){
		window.location.href= rootPath +"user/PhotoGroupMa.do?infoType=6&id="+$("#groupID").val();
	});
	
	initUploadify("uploadify","queueItemCount","headImg","uploadErrorMsg",true,imgUploaded);
	 
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

function initUploadify (el,countCtrl,imgCtrl,errorCtrl,auto,successInvok){
	setTimeout(function(){
	var sessionid= '${pageContext.session.id}';
	var groupID = $.query.get("groupID");
	$("#"+el).uploadify({
		scriptAccess:'always',
		auto:auto,
		height: 36,
		swf: '../js/plugin/uploadify-3.2.1/uploadify.swf',
		uploader: '../user/photo.do?a=uploadPhoto&groupID=' + groupID + '&jsessionid=' + sessionid,
		width: 90,
		cancelImg: '../js/plugin/uploadify-3.2.1/uploadify-cancel.png',
		buttonText: '上传',
		 
		'fileTypeDesc': '选择图片',
		'fileTypeExts': '*.jpg;*.jpeg;*.gif;*.png;*.bmp' ,
		fileSizeLimit: 0,
		removeCompleted: false,
		multi :false,//设置为true时可以上传多个文件。
		queueSizeLimit :2,//当允许多文件生成时，设置选择文件的个数，默认值：999 。
		'onUploadStart': function(file) {//上传开始时触发（每个文件触发一次）
			
		} ,
		onUploadSuccess : function(file,data,response) {//上传完成时触发（每个文件触发一次）
			
			if(typeof(successInvok) == "function") successInvok();
		}, 
		
		onSelectError : uploadify_onSelectError,
		onUploadError : uploadify_onUploadError
	});
	},10);
}
function uploadify_onSelectError (file, errorCode, errorMsg) {
    var msgText = "上传失败\n";
    switch (errorCode) {
        case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
            //this.queueData.errorMsg = "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
            msgText += "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
            break;
        case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
            msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";
            break;
        case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
            msgText += "文件大小为0";
            break;
        case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
            msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;
            break;
        default:
            msgText += "错误代码：" + errorCode + "\n" + errorMsg;
    }
    alert(msgText);
	return false;
}
function uploadify_onUploadError (file, errorCode, errorMsg, errorString) {
    // 手工取消不弹出提示
    if (errorCode == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED
            || errorCode == SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {
        return;
    }
    var msgText = "上传失败\n";
    switch (errorCode) {
        case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
            msgText += "HTTP 错误\n" + errorMsg;
            break;
        case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
            msgText += "上传文件丢失，请重新上传";
            break;
        case SWFUpload.UPLOAD_ERROR.IO_ERROR:
            msgText += "IO错误";
            break;
        case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
            msgText += "安全性错误\n" + errorMsg;
            break;
        case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
            msgText += "每次最多上传 " + this.settings.uploadLimit + "个";
            break;
        case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
            msgText += errorMsg;
            break;
        case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
            msgText += "找不到指定文件，请重新操作";
            break;
        case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
            msgText += "参数错误";
            break;
        default:
            msgText += "文件:" + file.name + "\n错误码:" + errorCode + "\n"
                    + errorMsg + "\n" + errorString;
    }
    alert(msgText);
	return false;;
}

function imgUploaded (){
	 
	window.location.reload();
	 
}