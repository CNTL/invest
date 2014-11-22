function init(){
	changeValPic();
	$("input[name='type']").change(function() {     //id 为season行内radio值变化函数  
		$(".register_radio li").attr("class", "");
	    if(this.checked){
	    	$(this).parent().attr("class", "current");
	    }
	});
}
function registerSubmit(){
	//1.旧密码是否正确，须校验 2.新密码校验 3.密码加密 4.写入数据库
	//2.新密码是否一致
	//3.对密码进行加密,写入数据库
	$.ajax({
        type:"POST", //请求方式  
        url:"../user/userlogin.do?a=create", //请求路径  
        cache: false,     
        data:$('#form').serialize(),  //传参       
        dataType: 'text',   //返回值类型  
        success:function(data){
    		if(data != null && data == 'ok'){
    			$.messager.confirm('消息', '注册成功，请登录!', function(r){
    				if (r){
    					parent.location.href = 'userLogin.jsp';
    				}
    			});
    		} else {
    			$.messager.alert('消息', "注册失败:" + data);
    		}
        }  
    });
}

function checkpassword(){
	var password_temp = $("#password").val();
	//密码限制6位以上
	$("#password").removeClass();
	if(password_temp.length<6){
		$("#password").addClass("form-control validate[maxSize[255],funcCall[pwdLen]]");
		return false;
    } else if(password_temp.indexOf(" ")!=-1){
    	$("#password").addClass("form-control validate[maxSize[255],funcCall[pwdKG]]");
		return false;
	} else{
		$("#password").addClass("form-control validate[maxSize[255],required]");
		return true;
	}
}
function checkpassword_again(){
	var password = $("#password").val();
	var passwordagain_temp = $("#pwdagain").val();
	$("#pwdagain").removeClass();
	if(passwordagain_temp.length<6){
		$("#pwdagain").addClass("form-control validate[maxSize[255],funcCall[pwdLen]]");
		return false;
    } else if(passwordagain_temp.indexOf(" ")!=-1){
    	$("#pwdagain").addClass("form-control validate[maxSize[255],funcCall[pwdKG]]");
		return false;
	} else if (password != passwordagain_temp) {//加密验证fjc 1.首先判断两个密码是否一致 2.对密码进行加密
		$("#pwdagain").addClass("form-control validate[maxSize[255],funcCall[pwdNoSame]]");
		return false;
	} else{
		$("#pwdagain").addClass("form-control validate[maxSize[255],required]");
		return true;
	}
}

function pwdLen(){
	return '密码长度不能少于6位';
}
function pwdKG(){
	return '密码不能包含空格';
}
function pwdNoSame(){
	return '两次输入的密码不一致';
}
function valError(){
	return '校验码不正确';
}

function changeValPic(){
	$("#validateCode").removeAttr("src");
	$.ajax({
        type:"GET", //请求方式  
        url:"../user/userlogin.do?a=authCode", //请求路径  
        cache: false,
        dataType: 'text',   //返回值类型  
        success:function(data){
        	$("#curVal").val(data);
        	$("#validateCode").attr("src", rootURL + "userout/img/validate.png");
        } ,
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
		}
    });
}
function checkMyVal(){
	var curVal = $("#curVal").val();
	var myVal = $("#myVal").val();
	$("#myVal").removeClass();
	if(myVal != null && myVal !="" && myVal != curVal){
		$("#myVal").addClass("form-control validate[maxSize[255],funcCall[valError]]");
		return false;
    } else{
		$("#myVal").addClass("form-control validate[maxSize[255],required]");
		return true;
	}
}
$(document).ready(function () {
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#login").attr("disabled", true);
				var password = checkpassword();
				var pwdAgain = checkpassword_again();
				var checkVal = checkMyVal();
				if(password && pwdAgain && checkVal){
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
	});
    init();
});