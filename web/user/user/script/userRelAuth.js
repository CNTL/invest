
$(document).ready(function () {
	var init = function() {
		if (!type_datas || !type_datas.ready) {
			setTimeout(init, 100);
			return;
		}
		//初始化
		relAuth.init();
		relAuth.initUploadify("uploadify","queueItemCount","organization","uploadErrorMsg",true,relAuth.imgUploaded);
		relAuth.initUploadify("uploadify1","queueItemCount1","orgBusinessLicense","uploadErrorMsg1",true,relAuth.imgUploaded1);
		$("#form").validationEngine({
			autoPositionUpdate:true,
			onValidationComplete:function(from,r){
				if (r){
					window.onbeforeunload = null;
					$("#btnSave").attr("disabled", true);
					relAuth.submit();
				}
			}
		});
	}
	init();
});
var relAuth = {
	eventList : [
  		{id:"firstType",evt:"change", fn:"changeFirstType"}
  	],
	DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		//按事件表依次绑定
		for (var i = 0; i < relAuth.eventList.length; i++) {
			var h = relAuth.eventList[i];
			$("#" + h.id).bind(h.evt, relAuth[h.fn]);
		}
		//relAuth.initJob();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#name").val(data.name);
	    			$("#firstType_h").val(data.firstType);
	    			$("#secondType_h").val(data.secondType);
	    			//$("#firstType").val(data.firstType);
	    			//$("#secondType").val(data.secondType);
	    			$("#province").val(data.province);//下拉框
	    			$("#perPhone").val(data.perPhone);
	    			$("#identityCard").val(data.identityCard);
	    			if(data.isRealNameIdent == 1) {
	    				$("#isIdent").addClass("label label-success");
	    				isIdent = "已认证";
	    			} else {
	    				$("#isIdent").addClass("label label-danger");
	    				isIdent = "未认证";
	    			}
	    			$("#isIdent").html(isIdent);
	    			if(data.organization != null && data.organization.length > 0){
	    				var srcUrl = data.organization;
	    				$("#organization").val(srcUrl);
	    				relAuth.imgUploaded();
	    			}
	    			if(data.orgBusinessLicense != null && data.orgBusinessLicense.length > 0) {
	    				var srcUrl = data.orgBusinessLicense;
	    				$("#orgBusinessLicense").val(srcUrl);
	    				relAuth.imgUploaded1();
	    			}
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
		
		relAuth._setOptions("firstType", type_datas.getFirstTypes(), relAuth.DEFAULT_PAIR);
		
		var firstType_h = $("#firstType_h").val();
		if(firstType_h != null && firstType_h != ''){
			$("#firstType").val(firstType_h).trigger("change");
		}
		var secondType_h = $("#secondType_h").val();
		if(secondType_h != null && secondType_h != ''){
			$("#secondType").val(secondType_h).trigger("change");
		}
	},
	initJob : function(){
		var dataUrl = "../user/userFetch.do?a=jobTypes";
		var loading = -1;
		$.ajax({url: dataUrl, async:true, dataType:"json",
			beforeSend:function(XMLHttpRequest){				
				loading = layer.msg("正在初始化数据...", 0, 16);
			},
			success: function(datas) {
				var curJobTypes = [];
				var jobTypes = datas.jobTypes;
				for(var i=0;i<jobTypes.length;i++){
					var jobType = jobTypes[i]; 
					if(jobType.pid==0){
						curJobTypes.push(jobType);
					}
				}
				relAuth._setOptions("perJob",curJobTypes,relAuth.DEFAULT_PAIR);
			},
			complete: function(XMLHttpRequest, textStatus){
				layer.close(loading);
			},
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				layer.close(loading);
				layer.alert('加载数据失败！', 3);
			}
		});
	},
	changeFirstType : function(){
		var secondTypes = [];
		var pid = $("#firstType").val();
		secondTypes = type_datas.getSecondTypes(pid);
		relAuth._setOptions("secondType",secondTypes,relAuth.DEFAULT_PAIR);
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
	submit : function(){
		var flag = true;
		var organization = $("#organization").val();
		if(organization == null || organization.length == 0){
			 
			AlertInfo(300,30,"请上传身份证正面证件照！")
			flag = false;
			$("#btnSave").attr("disabled", false);
			return;
		}
		var orgBusinessLicense = $("#orgBusinessLicense").val();
		if(orgBusinessLicense == null || orgBusinessLicense.length == 0){
			 
			AlertInfo(300,30,"请上传身份证反面证件照！")
			flag = false;
			$("#btnSave").attr("disabled", false);
			return;
		}
		if(flag == true){
			$.ajax({
		        type:"POST", //请求方式  
		        url:"../user/user.do?a=userRelAuth", //请求路径  
		        cache: false,
		        data:$('#form').serialize(),  //传参 
		        dataType: 'text',   //返回值类型  
		        success:function(data){
		    		if(data != null && data == 'ok'){
		    			AlertInfo(200,30,"认证资料提交成功！")
		    			 
		    		} else {
		    			AlertInfo(200,30,"认证资料提交失败！")
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert(errorThrown);
				}
		    });
		}
	},
	imgUploaded : function(){
		var c_img_t = 330;
		var c_img_l = 300;
		$("#coverIMG_div").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		$("#coverIMG_div").empty();
		if($("#organization").val()!=""){
			$("#coverIMG_div").html("<img src=\""+rootPath+$("#organization").val()+"\" border=\"0\" style=\"width:150px;height:100px;\" />");
			$("#coverIMG_div").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"relAuth.delCoverImg();\">删除</a></div>");
		}
	},
	delCoverImg : function(){
		$("#coverIMG_div").css("display","none");
		$("#coverIMG_div").empty();
		$("#queueItemCount").val("0");
		$("#organization").val("");
		$("#uploadErrorMsg").val("");
	},
	imgUploaded1 : function(){
		var c_img_t = 500;
		var c_img_l = 300;
		$("#coverIMG_div1").css("top",c_img_t+"px").css("left",c_img_l+"px").css("display","");
		$("#coverIMG_div1").empty();
		if($("#orgBusinessLicense").val()!=""){
			$("#coverIMG_div1").html("<img src=\""+rootPath+$("#orgBusinessLicense").val()+"\" border=\"0\" style=\"width:150px;height:100px;\" />");
			$("#coverIMG_div1").append("<div style=\"width:100%;margin-top:10px;text-align:center;\"><a href=\"javascript:void();\" style=\"background: url(../img/delete.png) no-repeat left;padding-left: 20px;\" onclick=\"relAuth.delCoverImg1();\">删除</a></div>");
		}
	},
	delCoverImg1 : function(){
		$("#coverIMG_div1").css("display","none");
		$("#coverIMG_div1").empty();
		$("#queueItemCount1").val("0");
		$("#orgBusinessLicense").val("");
		$("#uploadErrorMsg1").val("");
	},
	initUploadify : function(el,countCtrl,imgCtrl,errorCtrl,auto,successInvok){
		setTimeout(function(){
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
			onSelectError : relAuth.uploadify_onSelectError,
			onUploadError : relAuth.uploadify_onUploadError
		});
		},10);
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
		return false;;
    }
}