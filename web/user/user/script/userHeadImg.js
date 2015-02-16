$(document).ready(function () {
	//初始化
	headImg.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#submit").attr("disabled", true);
				headImg.submit();
			}
		}
	});
//	$("#img-list img").hover(
//	  function () {
//		    $(this).addClass("imgover");
//	  },
//	  function () {
//	    $(this).removeClass("imgover");
//	  }
//	);
	
	$("#img-list img").each(function(i,n){
		$(this).click(function(){
			 var url = $(this).attr("src").replace("../","");
			
			if($(this).attr("id")=="default1"){
				$(this).addClass("imgover");
				$("#default2").removeClass("imgover");
				$("#headImg").val(url);
				setDefaultImg();
			}
			else if ($(this).attr("id")=="default2"){
				$(this).addClass("imgover");
				$("#default1").removeClass("imgover");
				$("#headImg").val(url);
				setDefaultImg();
			}
				
		});
	});
});

function setDefaultImg(){
	document.getElementById('coverIMG_div').style.display = '';
	document.getElementById('cut_url').value = $("#headImg").val();
	$("#coverIMG_div").empty();
	if($("#headImg").val()!=""){
		$("#coverIMG_div").html("<img src=\""+rootPath +$("#headImg").val()+"\" class=\"img-circle\" border=\"0\" style=\"width:150px;height:150px;\" />");
		$("#coverIMG_div").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"headImg.delCoverImg();\">删除</a></div>");
	}
}
var headImg = {
	init : function(){
		headImg.initUploadify("uploadify","queueItemCount","headImg","uploadErrorMsg",true,headImg.imgUploaded);
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			var headSrc=data.head;
	    			if(headSrc != null && headSrc != "")
	    				document.getElementById('nowPhoto').src = rootPath + headSrc;
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				AlertInfo(100,30,"上传失败。");
			}
	    });
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/user.do?a=saveImg", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			AlertInfo(150,30,"设置成功。",function(r){
	    				if (r){
	    					window.location.href=window.location.href; 
	    				}
	    			});   			 
	    		} else {
	    			AlertInfo(150,30,"设置失败。");
	    		}
	    		$("#submit").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				
				AlertInfo(150,30,"保存失败");
			}
	    });
	},
	imgUploaded : function(){
		document.getElementById('coverIMG_div').style.display = '';
		document.getElementById('cut_url').value = $("#headImg").val();
		$("#coverIMG_div").empty();
		if($("#headImg").val()!=""){
			$("#coverIMG_div").html("<img src=\""+rootPath+$("#headImg").val()+"\" border=\"0\" style=\"width:150px;height:100px;\" />");
			$("#coverIMG_div").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"headImg.delCoverImg();\">删除</a></div>");
		}
	},
	delCoverImg : function(){
		$("#coverIMG_div").css("display","none");
		$("#coverIMG_div").empty();
		$("#queueItemCount").val("0");
		$("#headImg").val("");
		$("#uploadErrorMsg").val("");
	},
	initUploadify : function(el,countCtrl,imgCtrl,errorCtrl,auto,successInvok){
		var sessionid= '${pageContext.session.id}';
		$("#"+el).uploadify({
			scriptAccess:'always',
			auto:auto,
			height: 36,
			swf: '../js/plugin/uploadify-3.2.1/uploadify.swf',
			uploader: '../Upload.do?jsessionid=' + sessionid,
			width: 90,
			cancelImg: '../js/plugin/uploadify-3.2.1/uploadify-cancel.png',
			buttonText: '选择图片',
			'fileTypeDesc': '选择图片',
			'fileTypeExts': '*.jpg;*.jpeg;*.gif;*.png;*.bmp' ,
			fileSizeLimit: 0,
			removeCompleted: true,
			multi :false,//设置为true时可以上传多个文件。
			queueSizeLimit :2,//当允许多文件生成时，设置选择文件的个数，默认值：999 。
			'onUploadStart': function(file) {//上传开始时触发（每个文件触发一次）
				//动态更新设备额值 
				//deviceName  = device.val();
				//向后台传值 
				var formdata = {
				//	"deviceName":device.val(),
				//	"DocID":currentDocIDs,
				//	"DocLibID":currentDocLibIDs,
				//	"ATT_TOPIC":$("#topic").val(),
					"folder":"upload/user/headImg"
				};
				$("#"+el).uploadify("settings", "formData", formdata);  
			} ,
			onUploadSuccess : function(file,data,response) {//上传完成时触发（每个文件触发一次）
				if(data==""){
				}else{
					var d=data.replace("\n", ' ');
					var result = eval('('+data+')');
					if(result.success){
						$("#"+imgCtrl).val(result.path);
					}else{
						$("#"+errorCtrl).val(result.msg);
					}
				}
				$("#"+countCtrl).val("0");
				if(typeof(successInvok) == "function") successInvok();
			}, 
			onSelect : function(file) {//当每个文件添加至队列后触发 
			}, 
			onDialogClose : function(queueData) {
				var queueItemCount = queueData.queueLength;
				if(queueItemCount >1){
				   $('#'+el).uploadify('cancel');//删除第一个
				   queueItemCount = queueItemCount -1;
				}
				$("#"+countCtrl).val(queueItemCount);
				$(".uploadify-queue").css("display","none");
			},
			onDialogOpen : function() {
			},
			onClearQueue : function(queueItemCount) {
			} ,
			onSelectError : headImg.uploadify_onSelectError,
			onUploadError : headImg.uploadify_onUploadError
		});
	},
	uploadify_onSelectError : function(file, errorCode, errorMsg) {
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
    },
	uploadify_onUploadError : function(file, errorCode, errorMsg, errorString) {
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
}

//校验上传文件
function checkPic() {
	var location = document.getElementById('headImg').value;
	if (location == "") {
		alert("请先选择图片文件");
		return false;
	}
	var point = location.lastIndexOf(".");
	var type = location.substr(point).toLowerCase() ;
	if (type == ".jpg" || type == ".gif" || type == ".png" || type == ".jpeg" || type == ".bmp") {
	//if (type == ".jpg") {
		img = document.createElement("img");
		img.src = location;
		if (img.fileSize > 1024000) {
			alert("图片尺寸请不要大于100KB");
			return false;
		} else {
			document.getElementById("nowDiv").style.display="none";
			return true;
		}
	} else {
		 
		alert("只能上传jpg、jpeg、gif、png、bmp格式的图片");
		//alert("只能上传jpg格式的图片");
		return false;
	}
	document.getElementById("nowDiv").style.display="none";
	return true;
}
//图片上传后的回调函数
function callback(url, width, height) {
	//document.getElementById('cut_img').width = width;
	//document.getElementById('cut_img').height = height;
	document.getElementById('cut_img').src = url;
	document.getElementById('cut_url').value = url;
	document.getElementById('hide').style.display = '';
	imageinit();
	gripinit();
}
function hide(){
	document.getElementById('hide').style.display='none';
}