$(document).ready(function () {
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				var password = pwdChange.checkpassword();
				var pwdAgain = pwdChange.checkpassword_again();
				if(password && pwdAgain){
					$("#btnSave").attr("disabled", true);
					pwdChange.pwdSubmit();
				}
			}
		}
	});
});
var pwdChange = {
	pwdSubmit:function(){
		//1.旧密码是否正确，须校验 2.新密码校验 3.密码加密 4.写入数据库
		//2.新密码是否一致
		var param = "&password=" + $("#password").val();
		var theURL = "user.do?a=pwdEdit" + param;
		//3.对密码进行加密,写入数据库
		$.ajax({
	        type:"POST", //请求方式  
	        url:theURL, //请求路径  
	        cache: false,     
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '修改密码成功，请重新登录?', function(r){
	    				if (r){
	    					parent.location.href = 'userLogin.jsp';
	    				}
	    			});
	    		} else {
	    			$.messager.alert('修改密码失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	checkOldpassword : function(){
		return pwdChange.checkpasswordPub("old_pwd");
	},
	checkpassword : function(){
		return pwdChange.checkpasswordPub("password");
	},
	checkpassword_again : function(){
		$("#pwdagain").removeClass();
		var password = $("#password").val();
		var passwordagain_temp = $("#pwdagain").val();
		var flag = pwdChange.checkpasswordPub("old_pwd");
		if (password != passwordagain_temp) {//1.首先判断两个密码是否一致 2.对密码进行加密
			$("#pwdagain").addClass("form-control validate[maxSize[255],required,funcCall[textNoSame]]");
	    	document.getElementById('pwdagain').value="";
	    	flag = false;
		} else {
			$("#pwdagain").addClass("form-control validate[maxSize[255],required]");
			return true;
		}
		return flag;
	},
	checkpasswordPub : function(id){
		var obj = $("#" + id + "");
		if(obj.length == 0) return false;
		var password_temp = obj.val();
		obj.removeClass();
		obj.focus();
		 //密码限制6位以上
	    if(password_temp.length<6){
	    	obj.addClass("form-control validate[maxSize[255],required,funcCall[textLength]]");
	    	return false;
	    } else if (password_temp.indexOf(" ")!=-1){
	    	obj.addClass("form-control validate[maxSize[255],required,funcCall[textBlank]]");
			return false;
		} else {
			obj.addClass("form-control validate[maxSize[255],required]");
			return true;
		}
	}
}
function textLength(){
	return "密码长度不能少于6位!";
}
function textBlank(){
	return "密码不能包含空格!";
}
function textNoSame(){
	return "两次输入的密码不一致!";
}