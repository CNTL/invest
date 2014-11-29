var proj_form = {
	eventList : [
		{id:"proj_province",evt:"change", fn:"changeProvince"}, 
		{id:"proj_city",evt:"change", fn:"changeCity"}, 
		{id:"proj_countDay_sel",evt:"change", fn:"changeCountDaySel"}
		],
	DEFAULT_PAIR : {key:"id",value:"name"},
	init : function(){
		//按事件表依次绑定
		for (var i = 0; i < proj_form.eventList.length; i++) {
			var h = proj_form.eventList[i];
			$("#" + h.id).bind(h.evt, proj_form[h.fn]);
		}
		
		proj_form.changeCountDaySel();
		proj_form.initProjTypes();
		//proj_form._setOptions("proj_type",proj_datas.getProjTypes(),proj_form.DEFAULT_PAIR);
		proj_form._setOptions("proj_province",proj_datas.getProvinces(),proj_form.DEFAULT_PAIR);
		
		proj_form.initUploadify();
		$("#btnSave").bind("click",proj_form["save"]);
	},
	_save : function(){
		alert("调用了_save()");
	},
	save : function(){
		$("#proj_imgURL").val("");
		$("#uploadErrorMsg").val("");
		var queueItemCount = parseInt($("#queueItemCount").val());
		if(queueItemCount>1){
			alert("您只能选择一个要上传的附件!");
			return false;
		}
		if(queueItemCount==1){
			$('#uploadify').uploadify('upload');
		}else{
			alert("没有要上传的文件，直接保存！");
			proj_form._save();
		}
	},
	initProjTypes : function(){
		$("#proj_type_select li").remove();
		var types = proj_datas.getProjTypes();
		for(var i=0;i<types.length;i++){
			var className = "";
			if(i == 0) className = " class=\"current\"";
			var li = "<li data=\""+types[i].id+"\""+className+">"+types[i].name+"</li>";
			$("#proj_type_select").append(li);
		}
		
		$('#proj_type_select>li').click(function () {
			$('#proj_type_select>li').removeClass('current');
			$(this).addClass('current');
			$('#proj_type').val($(this).attr('data'));
		});
		
		$('#proj_type_select li.current').trigger("click");
	},
	changeProvince : function(){
		var cities = [];
		var pid = $("#proj_province").val();
		cities = proj_datas.getCities(pid);
		proj_form._setOptions("proj_city",cities,proj_form.DEFAULT_PAIR);
	},
	changeCity : function(){
		var counties = [];
		var cid = $("#proj_city").val();
		counties = proj_datas.getCounties(cid);
		proj_form._setOptions("proj_county",counties,proj_form.DEFAULT_PAIR);
	},
	changeCountDaySel : function(){
		var selValue = $("#proj_countDay_sel").val();
		proj_form._setReadonly("proj_countDay");
		if(!selValue || selValue<=0){
			proj_form._removeReadonly("proj_countDay");
		}else{
			$("#proj_countDay").val(selValue*30).css("font-size","18px").css("text-align","center");
		}
	},
	initUploadify : function(){
		var sessionid= getCookie("JSESSIONID");
		$("#uploadify").uploadify({
			scriptAccess:'always',
			auto:false,
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
			onUploadStart: function(file) {//上传开始时触发（每个文件触发一次）
				//动态更新设备额值 
				//deviceName  = device.val();
				//向后台传值 
				var formdata = {
				//	"deviceName":device.val(),
				//	"DocID":currentDocIDs,
				//	"DocLibID":currentDocLibIDs,
				//	"ATT_TOPIC":$("#topic").val(),
					"folder":"upload/project"
				};
				$("#uploadify").uploadify("settings", "formData", formdata);  
			} ,
			onUploadSuccess : function(file,data,response) {//上传完成时触发（每个文件触发一次）
				if(data==""){
				}else{
					var d=data.replace("\n", ' ');
					var result = eval('('+data+')');
					if(result.success){
						$("#proj_imgURL").val(result.path);
					}else{
						$("#uploadErrorMsg").val(result.msg);
					}
				}
				$("#queueItemCount").val("0");
				alert($("#proj_imgURL").val()+"\n"+$("#uploadErrorMsg").val());
				proj_form._save();
			}, 
			onSelect : function(file) {//当每个文件添加至队列后触发 
			}, 
			onDialogClose : function(queueData) {
				var queueItemCount = queueData.queueLength;
				if(queueItemCount >1){
				   $('#uploadify').uploadify('cancel');//删除第一个
				   queueItemCount = queueItemCount -1;
				}
				$("#queueItemCount").val(queueItemCount);
			},
			onDialogOpen : function() {
			},
			onClearQueue : function(queueItemCount) {
			} ,
			onSelectError : proj_form.uploadify_onSelectError,
			onUploadError : proj_form.uploadify_onUploadError
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
	_setReadonly : function(id) {
		var src = $("#" + id);
		if (src.length > 0) {
			src.prop("readonly", true);
			src[0].style.backgroundColor = "#cccccc";
		}
		
		return src;
	},
	_removeReadonly : function(id) {
		var src = $("#" + id);
		if (src.length > 0) {
			src.prop("readonly", false).removeClass("disabled");
			src.prop("disabled", false);
			src[0].style.backgroundColor = "white";
		}
		
		return src;
	}
}

//--------------初始化---------------
$(function(){
	var init = function() {
		if (!proj_datas || !proj_datas.ready) {
			setTimeout(init, 100);
			return;
		}
		proj_form.init();
	}
	init();
});
