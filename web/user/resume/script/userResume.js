$(document).ready(function () {
	resume.init();
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				resume.submit();
			}
		}
	});
});
var resume = {
	init : function(){
		resume.editorInit();
		resume.initUploadify("uploadify","queueItemCount","affix","uploadErrorMsg",true,resume.imgUploaded);
		$("#affixBtn").click(function() {
			var path = 'resume';
			resume.ajaxFileUpload("affix", '../user/Upload.do?a=uploadAtt&savePath=' + path + '&field=', path);
	    });
		var id = $("#id").val();
		if(id == null && id.length == 0) return;
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/resume.do?a=curResume&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null && data.length == 1){
	    			$("#name").val(data[0].name);
	    			$("#content").val(data[0].content);
	    			$("#contentTxt").val(data[0].content);
	    			$("#affix").val(data[0].affix);
	    			resume.imgUploaded();
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	editorInit : function(){
		var editor = CKEDITOR.replace("contentTxt",
				{
					toolbar :
					[
						//加粗     斜体，     下划线      穿过线      下标字        上标字
						['Bold','Italic','Underline','Strike','Subscript','Superscript'],
						//数字列表          实体列表            减小缩进    增大缩进
						['NumberedList','BulletedList','-','Outdent','Indent'],
						//左对齐             居中对齐          右对齐          两端对齐
						['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
						//超链接 取消超链接 锚点
						['Link','Unlink','Anchor'],
						//图片    flash    表格       水平线            表情       特殊字符        分页符
						['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
						'/',
						//样式       格式      字体    字体大小
						['Styles','Format','Font','FontSize'],
						//文本颜色     背景颜色
						['TextColor','BGColor'],
						//全屏           显示区块
						['Maximize', 'ShowBlocks','-']
					]
				}
			);
	},
	submit : function(){
		$("#content").val(CKEDITOR.instances.contentTxt.getData());
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/resume.do?a=saveResume", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '保存简历成功！', function(r){
	    				if (r){
	    					window.location.href= "../resume/myresume.do?infoType=1"; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('保存简历失败！',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('消息', errorThrown);
			}
	    });
	},
	imgUploaded : function(){
		var c_img_t = 525;
		var c_img_l = 300;
		$("#coverIMG_div").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		$("#coverIMG_div").empty();
		var affix = $("#affix").val();
		if(affix != ""){
			var lastIndex = affix.lastIndexOf("\\");
			var name = affix.substring(lastIndex + 1, affix.length);
			$("#coverIMG_div").html("<a href=\"" + rootPath + affix +"\" border=\"0\" style=\"width:300px;height:20px;\" >"+name+"</a>");
			$("#coverIMG_div").append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:void();\" " +
					"style=\"background:url(../img/delete.png) no-repeat left;padding-right: 20px;\" onclick=\"resume.delCoverImg();\">" +
					"&nbsp;&nbsp;&nbsp;&nbsp;删除</a>");
		}
	},
	delCoverImg : function(){
		$("#coverIMG_div").css("display","none");
		$("#coverIMG_div").empty();
		$("#queueItemCount").val("0");
		$("#affix").val("");
		$("#uploadErrorMsg").val("");
	},
	initUploadify : function(el,countCtrl,imgCtrl,errorCtrl,auto,successInvok){
		var sessionid= "";//getCookie("JSESSIONID");
		$("#"+el).uploadify({
			scriptAccess:'always',
			auto:auto,
			height: 36,
			swf: '../js/plugin/uploadify-3.2.1/uploadify.swf',
			uploader: '../Upload.do?jsessionid=' + sessionid,
			width: 90,
			cancelImg: '../js/plugin/uploadify-3.2.1/uploadify-cancel.png',
			buttonText: '选择附件',
			'fileTypeDesc': '选择附件',
			'fileTypeExts': '*.docx;*.doc' ,
			fileSizeLimit: 0,
			removeCompleted: true,
			multi :false,//设置为true时可以上传多个文件。
			queueSizeLimit :2,//当允许多文件生成时，设置选择文件的个数，默认值：999 。
			'onUploadStart': function(file) {//上传开始时触发（每个文件触发一次）
				//向后台传值 
				var formdata = {
					"folder":"upload/user/resume"
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
			onSelectError : resume.uploadify_onSelectError,
			onUploadError : resume.uploadify_onUploadError
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
    },
	ajaxFileUpload : function(id, uploadUrl, uploadPath) {
		var flag = resume.checkPic(id);
		if(flag) {
	        var elementIds=[id]; //flag为id、name属性名
	        $.ajaxFileUpload({
	            url: uploadUrl + id, 
	            type: 'post',
	            secureuri: false, //一般设置为false
	            fileElementId: id + "File", // 上传文件的id、name属性名
	            dataType: 'text', //返回值类型，一般设置为json、application/json
	            elementIds: elementIds, //传递参数到服务器
	            success: function(data, status){
	            	if(data != null && data.length > 0){
	            		var srcUrl = resume.imgPath(data, uploadPath);
	            		alert(srcUrl);
	                	if(srcUrl.length > 0){
	                		$("#" + id).val(srcUrl);
	                		alert(rootPath + srcUrl)
	                		$("#" + id + "Img").attr("href", rootPath + srcUrl);
	                    	$("#" + id + "Img").parent().show();
	                	} else {
	                		$("#" + id + "Img").parent().hide();
	                		$.messager.alert('消息！',"上传失败，请重新上传！");
	                	}
	            	} else {
	            		$("#" + id + "Img").parent().hide();
	            		$.messager.alert('消息！',"上传失败，请重新上传！");
	            	}
	            	
	            },
	            error: function(data, status, e){ 
	            	$.messager.alert('消息！', e);
	            }
	        });
		}
    },
    imgPath : function(path, uploadPath){
    	var start = path.indexOf("upload/user/"+uploadPath);
		var srcUrl = "";
		srcUrl = path.substr(start, path.length - start - 6);
		srcUrl.replace(srcUrl, '</pre>');
		return srcUrl;
    },
    //校验上传文件
    checkPic : function(id) {
    	var location = document.getElementById(id + "File").value;
    	if (location == "") {
    		$.messager.alert('消息', "请先选简历附件");
    		return false;
    	}
    	var point = location.lastIndexOf(".");
    	var type = location.substr(point).toLowerCase() ;
    	if (type == ".doc" || type == ".docx" || type == ".pdf" || type == ".ppt") {
    	//if (type == ".jpg") {
    		img = document.createElement("img");
    		img.src = location;
    		if (img.fileSize > 10*1024*1024) {
    			$.messager.alert('消息', "附件大小请不要大于10M");
    			return false;
    		} else {
    			return true;
    		}
    	} else {
    		$.messager.alert('消息', "只能上传doc、docx、pdf、ppt格式的附件");
    		//alert("只能上传jpg格式的图片");
    		return false;
    	}
    	return true;
    }
}
