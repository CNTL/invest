$(document).ready(function () {
	//初始化
	relAuth.init();
	$("#organizationBtn").click(function() {
		relAuth.ajaxFileUpload("organization");
    });
	$("#orgBusinessLicenseBtn").click(function() {
		relAuth.ajaxFileUpload("orgBusinessLicense");
    });
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
});
var relAuth = {
	init : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#orgFullname").val(data.orgFullname);
	    			$("#name").val(data.name);
	    			$("#identityCard").val(data.identityCard);
	    			if(data.organization != null && data.organization.length > 0){
	    				var srcUrl = data.organization;
	    				$("#organization").val(srcUrl);
	    				$("#organizationImg").attr("src", basePath + srcUrl);
	    				$("#organizationImg").parent().show();
	    			}
	    			if(data.orgBusinessLicense != null && data.orgBusinessLicense.length > 0) {
	    				var srcUrl = data.orgBusinessLicense;
	    				$("#orgBusinessLicense").val(srcUrl);
	    				$("#orgBusinessLicenseImg").attr("src", basePath + srcUrl);
	    				$("#orgBusinessLicenseImg").parent().show();
	    			}
	    			var bankcards = eval(data.bankcards);
	    			if(bankcards != null && bankcards.length > 0){
	    				$("#openingBanks").val(bankcards[0].openingBank);//银行卡开户行
		    			$("#bankNums").val(bankcards[0].bankNum);//银行卡号
	    			}
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	submit : function(){
		var flag = true;
		var organization = $("#organization").val();
		if(organization == null || organization.length == 0){
			$.messager.alert('消息','请上传企业组织机构证件照！');
			flag = false;
			$("#btnSave").attr("disabled", false);
			return;
		}
		var orgBusinessLicense = $("#orgBusinessLicense").val();
		if(orgBusinessLicense == null || orgBusinessLicense.length == 0){
			$.messager.alert('消息','请上传企业营业执照扫描件！');
			flag = false;
			$("#btnSave").attr("disabled", false);
			return;
		}
		if(flag == true){
			$.ajax({
		        type:"POST", //请求方式  
		        url:"../user/user.do?a=orgRelAuth", //请求路径  
		        cache: false,
		        data:$('#form').serialize(),  //传参 
		        dataType: 'text',   //返回值类型  
		        success:function(data){
		    		if(data != null && data == 'ok'){
		    			$.messager.alert('消息','认证资料提交成功！');
		    		} else {
		    			$.messager.alert('认证资料提交失败！',data);
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert(errorThrown);
				}
		    });
		}
	},
	ajaxFileUpload : function(id) {
		var flag = relAuth.checkPic(id);
		if(flag) {
			// 开始上传文件时显示一个图片
	        $("#" + id + "Wait").ajaxStart(function() {
	            $(this).show();
	        // 文件上传完成将图片隐藏起来
	        }).ajaxComplete(function() {
	            $(this).hide();
	        });
	        var elementIds=[id]; //flag为id、name属性名
	        $.ajaxFileUpload({
	            url: '../user/user.do?a=uploadAtt&field=' + id, 
	            type: 'post',
	            secureuri: false, //一般设置为false
	            fileElementId: id + "File", // 上传文件的id、name属性名
	            dataType: 'text', //返回值类型，一般设置为json、application/json
	            elementIds: elementIds, //传递参数到服务器
	            success: function(data, status){
	            	if(data != null && data.length > 0){
	            		var srcUrl = relAuth.imgPath(data);
	                	if(srcUrl.length > 0){
	                		$("#" + id).val(srcUrl);
	                		$("#" + id + "Img").attr("src", basePath + srcUrl);
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
    imgPath : function(path){
    	var start = path.indexOf('upload/user/relAuth');
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