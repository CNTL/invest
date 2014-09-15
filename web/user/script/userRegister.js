jQuery(document).ready(function () {
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
					from[0].submit();
				}
			}
		}
	});
	
	//$("#code").removeAttr("readonly");
	var value = "<%=valueArray %>";
	if(value != null && value.length > 0){
		var valueArray = value.split(",");
		$("#id").val(valueArray[0]);
		$("#name").val(valueArray[1]);
		$("#code").val(valueArray[2]);
		//$("#code").attr("readonly", 'true');
		$("#password").val(valueArray[3]);
    	}
  // init background slide images
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