$(document).ready(function () {
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				$("#btnCancel").attr("btnCancel", true);
				var password = checkpassword();
				var pwdAgain = checkpassword_again();
				if(password && pwdAgain){
					//from[0].submit();
					//userlogin.do?a=create
					registerSubmit();
				}
			}
		}
	});
	
    $.backstretch([
     "../img/loginbg/1.jpg",
     "../img/loginbg/2.jpg",
     "../img/loginbg/3.jpg",
     "../img/loginbg/4.jpg"
    ], {
        fade: 1000,
        duration: 8000
    }
 );
});
function registerSubmit(){
	//1.旧密码是否正确，须校验 2.新密码校验 3.密码加密 4.写入数据库
	//2.新密码是否一致
	//3.对密码进行加密,写入数据库
	$.ajax({
        type:"POST", //请求方式  
        url:"userlogin.do?a=create", //请求路径  
        cache: false,     
        data:$('#form').serialize(),  //传参       
        dataType: 'text',   //返回值类型  
        success:function(data){
    		if(data != null && data == 'ok'){
    			$.messager.confirm('消息', '注册成功，请登录?', function(r){
    				if (r){
    					parent.location.href = 'userLogin.jsp';
    				}
    			});
    		}else {
    			$.messager.alert('消息', "注册失败:" + data);
    		}
        }  
    });
}
function checkpassword(){
	var password_temp = $("#password").val();
	 //密码限制6位以上
    if(password_temp.length<6){
    	$("#tip").text("密码长度不能少于6位");
    	//document.getElementById('CUST_PASSWORD').placeholder='密码长度不能小于6位';
    	return false;
    } else if(password_temp.indexOf(" ")!=-1){
		$("#tip").text("密码不能包含空格");
		return false;
	} else{
		$("#tip").text("");
		return true;
	}
}
function checkpassword_again(){
	var password = $("#password").val();
	var passwordagain_temp = $("#pwdagain").val();
	//密码限制6位以上
	if(passwordagain_temp.length<6){
		$("#tip_again").text("密码长度不能少于6位");
		return false;
    } else if(passwordagain_temp.indexOf(" ")!=-1){
		$("#tip_again").text("密码不能包含空格");
		return false;
	} else if (password != passwordagain_temp) {//加密验证fjc 1.首先判断两个密码是否一致 2.对密码进行加密
    	$("#tip_again").text("两次输入的密码不一致");
    	document.getElementById('pwdagain').value="";
		document.getElementById('pwdagain').placeholder='密码不一致';
		return false;
	} else {
		$("#tip_again").text("");
		return true;
	}
}