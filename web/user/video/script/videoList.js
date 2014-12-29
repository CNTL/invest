$(document).ready(function () {
	myVideo.init();
});
var pagei = null;
var myVideo = {
	init : function(){
		$("#createVideo").click(myVideo.addVideo);
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
    		if((i + 1)%3==0) {
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
				                '<div class="tool">' +
				                	'<a class="view" title="编辑" style="cursor:pointer;background: url(../img/edit.png) no-repeat left;padding-left: 20px;" onclick="myVideo.editVideo('+id+');"></a>' +
			                        '<a class="share" title="删除" style="cursor:pointer;background: url(../img/delete.png) no-repeat left;padding-left: 20px;" onclick="myVideo.delVideo('+id+');"></a>' +
			                    '</div>' +
				            '</div>' +
				        '</div>' + suffix;
    		$(".block1").append(html);
    		
    	});
	},
	saveVideo : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/video.do?a=saveVideo", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data.length > 0){
	    			$.messager.confirm('消息', '创建视频成功！', function(r){
	    				if (r){
	    					window.location.href="../user/VideoMa.do?infoType=7&groupID=" + data; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('创建视频失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('创建视频组失败！',errorThrown);
			}
	    });
	},
	delVideo : function(id){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/video.do?a=delVideo&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'TEXT',   //返回值类型  
	        success:function(data){
	        	if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '删除视频成功！', function(r){
	    				if (r){
	    					window.location.href=window.location.href; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('删除视频失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('删除视频失败！',errorThrown);
			}
	    });
	},
	addVideo : function(){
		myVideo.openVideoFormDlg("新增", myVideo.getVideoFormHtml(""));
	},
	editVideo : function(id,userId){
		myVideo.openVideoFormDlg("修改", myVideo.getVideoFormHtml(id));
		if(typeof(id)=="undefined" || !id) id = 0;
		if(id>0){
			myVideo.getVideoInfo(id);
		}
	},
	getVideoFormHtml : function(id){
		var html = '<div class="job_add">' +
						'<form class="setting-form" id="form" name="form" action="">' +
						'<div class="input">' +
					        '<label for="name">视频名称：</label>' +
					        '<input type="hidden" id="id" name="id" value=""/>' +
					        '<input type="hidden" id="groupID" name="groupID" value=""/>' +
					        '<input type="text" id="name" name="name" class="form-control validate[maxSize[255],required]" value=""/>' +
					    '</div>' +
					    '<div class="input">' +
							'<table style="width:100%;">' +
								'<tr>' +
									'<td valign="top" style="width:90px;">' +
										'<label>视频图片：</label>' +
									'</td>' +
									'<td>' +
										'<input type="file" name="uploadify" id="uploadify" />' +
										'<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />' +
										'<input type="hidden" id="photo" name="photo" value="" />' +
										'<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />' +
									'</td>' +
								'</tr>' +
							'</table>' +
					   '</div>' +
					   '<div id="coverIMG_div" style="display:none;position: absolute; z-index: 122; width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">' +
						'</div>' +
						'<div style="margin-top:100px;">' +
						    '<div class="input">' +
						        '<label for="video">视频地址：</label>' +
						        '<input type="text" id="video" name="video" class="form-control validate[maxSize[255],required]" value=""/>' +
						    '</div>' +
						    '<div class="input">' +
								'<label for="intro">视频描述：</label>' +
								'<textarea  id="intro" name="intro" class="form-control validate[maxSize[4000]]" style="width:400px;height:100px;" placeholder=""></textarea>' +
							'</div>' +
						    '<div class="btn">' +
						    	'<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button" onclick="myVideo.btnOK();"/>' +
						    	'<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button" onclick="myVideo.btnCancel();"/>' +
						    '</div>' +
					    '</div>' +
					'</form>' +
				'</div>';
		return html;
	},
	getVideoInfo : function(id){
		var dataUrl = "../user/video.do?a=getVideoInfo&id="+id;
		$.ajax({url: dataUrl, async:false, dataType:"json",
			success: function(data) {
				if(data != null){
					$("#id").val(data.id);
					$("#groupID").val(data.groupId);
	    			$("#name").val(data.name);
	    			$("#photo").val(data.photo);
	    			$("#video").val(data.video);
	    			$("#intro").val(data.intro);
	    			myVideo.imgUploaded();
	    		}
			}
		});
	},
	openVideoFormDlg : function(title,html){
		pagei = $.layer({
			type: 1,   //0-4的选择,
			title: title,
			maxmin: false,
			border: [10, 0.2, '#000'],
			closeBtn: [1, true],
			shadeClose: false,
			fix: true,
			zIndex : 1000,
			area: ['800px', '500px'],
			page: {
				html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
			}
		});
		myVideo.initUploadify("uploadify","queueItemCount","photo","uploadErrorMsg",true,myVideo.imgUploaded);
		$("#form").validationEngine("attach",{
			autoPositionUpdate:false,//是否自动调整提示层的位置
			scroll:false,//屏幕自动滚动到第一个验证不通过的位置
			focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
			promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
		});
	},
	btnOK : function(){
		if(!myVideo._validForm()) return;
		myVideo.saveVideo();
		if(pagei != null)
			layer.close(pagei);
	},
	btnCancel: function(){
		if(pagei != null)
			layer.close(pagei);
	},
	_validForm : function() {
		if (!$("#form").validationEngine("validate")){
			//验证提示
			$("#form").validationEngine({scroll:false});
			return false;
		}
		return true;
	},
	imgUploaded : function(){
		var c_img_t = 50;
		var c_img_l = 300;
		$("#coverIMG_div").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		$("#coverIMG_div").empty();
		if($("#photo").val()!=""){
			$("#coverIMG_div").html("<img src=\""+rootPath+$("#photo").val()+"\" border=\"0\" style=\"width:150px;height:100px;\" />");
			$("#coverIMG_div").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"myVideo.delCoverImg();\">删除</a></div>");
		}
	},
	delCoverImg : function(){
		$("#coverIMG_div").css("display","none");
		$("#coverIMG_div").empty();
		$("#queueItemCount").val("0");
		$("#photo").val("");
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
			'fileTypeExts': '*.jpg;*.png;*.bmp' ,
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
					"folder":"upload/user/photo"
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
			onSelectError : myVideo.uploadify_onSelectError,
			onUploadError : myVideo.uploadify_onUploadError
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