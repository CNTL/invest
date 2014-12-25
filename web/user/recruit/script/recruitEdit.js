$(document).ready(function () {
	var init = function() {
		if (!type_datas || !type_datas.ready) {
			setTimeout(init, 100);
			return;
		}
		jobEdit.init();
		//初始化
		$("#form").validationEngine({
			autoPositionUpdate:true,
			onValidationComplete:function(from,r){
				if (r){
					window.onbeforeunload = null;
					$("#btnSave").attr("disabled", true);
					jobEdit.submit();
				}
			}
		});
	}
	init();
});
var jobEdit = {
	eventList : [
 		{id:"firstType",evt:"change", fn:"changeFirstType"}
 		],
 	DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		//按事件表依次绑定
		for (var i = 0; i < jobEdit.eventList.length; i++) {
			var h = jobEdit.eventList[i];
			$("#" + h.id).bind(h.evt, jobEdit[h.fn]);
		}
		jobEdit.editorInit();
	    $("#mapSearch").attr("onclick","getMap();");
	    jobEdit.initUploadify("uploadify","queueItemCount","jobPictrue","uploadErrorMsg",true,jobEdit.imgUploaded);
	    jobEdit._setOptions("firstType",type_datas.getFirstTypes(),jobEdit.DEFAULT_PAIR);
	},
	changeFirstType : function(){
		var secondTypes = [];
		var pid = $("#firstType").val();
		secondTypes = type_datas.getSecondTypes(pid);
		jobEdit._setOptions("secondType",secondTypes,jobEdit.DEFAULT_PAIR);
	},
	_setOptions : function(id, datas, pair) {
		var sel = document.getElementById(id);
		if (!sel) return;
		
		while (sel.options.length > 0)
			sel.options.remove(0);

		for (var i = 0; i < datas.length; i++) {
			var op = document.createElement("OPTION");
			op.value = datas[i][pair.key];
			op.text = datas[i][pair.value];
			sel.options.add(op);
		}
		$(sel).trigger("change");
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
	        url:"../user/recruit.do?a=save", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '保存影聘内容成功！', function(r){
	    				if (r){
	    					history.go(-1);
	    				}
	    			});
	    		} else {
	    			$.messager.alert('保存影聘内容失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	imgUploaded : function(){
		var c_img_t = 20;
		var c_img_l = 650;
		$("#coverIMG_div").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		$("#coverIMG_div").empty();
		if($("#jobPictrue").val()!=""){
			$("#coverIMG_div").html("<img src=\""+rootPath+$("#jobPictrue").val()+"\" border=\"0\" style=\"width:150px;height:100px;\" />");
			$("#coverIMG_div").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"jobEdit.delCoverImg();\">删除</a></div>");
		}
		/*if($("#jobPictrue").val()!=""){
			$("#coverIMG_div").html("<img src=\""+webroot+$("#jobPictrue").val()+"\" border=\"0\" style=\"width:400px;height:276px;\" />");
			$("#coverIMG_div").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"jobEdit.delCoverImg();\">删除</a></div>");
		}*/
	},
	delCoverImg : function(){
		$("#coverIMG_div").css("display","none");
		$("#coverIMG_div").empty();
		$("#queueItemCount").val("0");
		$("#jobPictrue").val("");
		$("#uploadErrorMsg").val("");
	},
	initUploadify : function(el,countCtrl,imgCtrl,errorCtrl,auto,successInvok){
		var sessionid= getCookie("JSESSIONID");
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
					"folder":"upload/user/recruit"
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
			onSelectError : jobEdit.uploadify_onSelectError,
			onUploadError : jobEdit.uploadify_onUploadError
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
function getMap(){
	window.open("../common/MsgMap.jsp");
}