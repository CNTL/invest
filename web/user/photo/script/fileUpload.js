var fileUpload = {
	ajaxFileUpload : function(id, uploadUrl, uploadPath) {
		var flag = fileUpload.checkPic(id);
		if(flag) {
			/*// 开始上传文件时显示一个图片
	        $("#" + id + "Wait").ajaxStart(function() {
	            $(this).show();
	        // 文件上传完成将图片隐藏起来
	        }).ajaxComplete(function() {
	            $(this).hide();
	        });*/
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
	            		var srcUrl = fileUpload.imgPath(data, uploadPath);
	                	if(srcUrl.length > 0){
	                		$("#" + id).val(srcUrl);
	                		$("#" + id + "Img").attr("src", rootPath + srcUrl);
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
    	var start = path.indexOf(uploadPath);
		var srcUrl = "";
		srcUrl = path.substr(start, path.length - start - 6);
		srcUrl.replace(srcUrl, '</pre>');
		return srcUrl;
    },
    //校验上传文件
    checkPic : function(id) {
    	var location = document.getElementById(id + "File").value;
    	if (location == "") {
    		$.messager.alert('消息', "请先选择图片文件");
    		return false;
    	}
    	var point = location.lastIndexOf(".");
    	var type = location.substr(point).toLowerCase();
    	if (type == ".jpg" || type == ".gif" || type == ".png" || type == ".jpeg" || type == ".bmp") {
    	//if (type == ".jpg") {
    		var img = document.createElement("img");
    		img.src = location;
    		if (img.fileSize > 1024000) {
    			$.messager.alert('消息', "图片尺寸请不要大于100KB");
    			return false;
    		} else {
    			return true;
    		}
    	} else {
    		$.messager.alert('消息', "只能上传jpg、jpeg、gif、png、bmp格式的图片");
    		//alert("只能上传jpg格式的图片");
    		return false;
    	}
    	document.getElementById("nowDiv").style.display="none";
    	return true;
    }
}