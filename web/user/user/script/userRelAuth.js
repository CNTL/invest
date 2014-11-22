$(document).ready(function () {
	//初始化
	complete.init();
	$("#organizationBtn").click(function() {
		complete.ajaxFileUpload("organization");
    });
	$("#businessLicenseBtn").click(function() {
		complete.ajaxFileUpload("businessLicense");
    });
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				complete.submit();
			}
		}
	});
});
var complete = {
	init : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			//alert(data.province);
	    			//alert(data.city);
	    			$("#name").val(data.name);
	    			$("#type").val(data.type);//下拉框
	    			$("#province").val(data.province);//下拉框
	    			load_city(data.city);
	    			$("#job").val(data.job);//下拉框
	    			$("#phone").val(data.phone);
	    			$("#identityCard").val(data.identityCard);
	    			if(data.organization != null && data.organization.length > 0){
	    				$("#organization").val(data.organization);
	    				$("#organization" + "Img").attr("src", basePath + data.organization);
	    				$("#organization" + "Img").parent().show();
	    			}
	    			
	    			if(data.businessLicense != null && data.businessLicense.length > 0) {
	    				$("#businessLicense").val(data.businessLicense);
	    				$("#businessLicense" + "Img").attr("src", basePath + data.businessLicense);
	    				$("#businessLicense" + "Img").parent().show();
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
		var businessLicense = $("#businessLicense").val();
		if(businessLicense == null || businessLicense.length == 0){
			$.messager.alert('消息','请上传企业营业执照扫描件！');
			flag = false;
			$("#btnSave").attr("disabled", false);
			return;
		}
		if(flag == true){
			$.ajax({
		        type:"POST", //请求方式  
		        url:"../../user/user.do?a=relAuth", //请求路径  
		        cache: false,
		        data:$('#form').serialize(),  //传参 
		        dataType: 'text',   //返回值类型  
		        success:function(data){
		    		if(data != null && data == 'ok'){
		    			$.messager.alert('消息','认证资料提交成功！');
		    			//parent.location.href = 'userLogin.jsp';
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
		var flag = complete.checkPic(id);
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
	            url: '../../user/user.do?a=uploadAtt&field=' + id, 
	            type: 'post',
	            secureuri: false, //一般设置为false
	            fileElementId: id + "File", // 上传文件的id、name属性名
	            dataType: 'text', //返回值类型，一般设置为json、application/json
	            elementIds: elementIds, //传递参数到服务器
	            success: function(data, status){
	            	if(data != null && data.length > 0){
	            		var isStart = (data.indexOf('<pre>') == 0);
	                	var isEnd = (data.indexOf('</pre>') == data.length - 6);
	                	var srcUrl = "";
	                	
	                	if(isStart && isEnd){
	                		data = data.substr(5,data.length-11);
	                	}
	                	if(data.length > 0){
	                		$("#" + id).val(data);
	                		$("#" + id + "Img").attr("src", basePath + data);
	                    	$("#" + id + "Img").parent().show();
	                	} else{
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