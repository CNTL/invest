<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>找回密码-合众映画</title>

<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script src="../js/utils.js" type="text/javascript"></script>
<script type="text/javascript" src="script/findPwd.js"></script>
</head>

<body id="login_bg">
	<div class="login_wrapper">
    	<input type="hidden" id="resubmitToken" value="" />
     	<div class="find_psw">
        	<img src="../img/psw_step1.png" width="369" height="56" alt="找回密码第一步" />
            <form id="form" name="form" action="../user/user.do?a=findPwd" method="post">
           		<input type="text" name="email" id="email" class="form-control validate[maxSize[255],custom[email],required]" tabindex="1" value="" placeholder="请输入注册时使用的邮箱地址" />
                <input type="submit" id="submitLogin" value="找回密码" />
            </form>
        </div>
    </div>
    
<script type="text/javascript">
$(document).ready(function () {
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#submitLogin").attr("disabled", true);
				findPwd.submit();
			}
		}
	});
});

var findPwd = {
	submit : function(){
		//window.location.replace("findPwdStep2.jsp?email=" + encodeURI("leijuan1014@163.com") + "&emailType=" + encodeURI("http://mail.163.com"));
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/userlogin.do?a=findPwd", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'json',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			if(data.error != null && data.error.length > 0){
	    				alert(result.error)
	    				$.messager.alert('消息',result.error);
	    			} else {
	    				var email = data.email;
	    				var emailType = data.emailType;
	    				alert(emailType);
	    				window.location.replace("findPwdStep2.jsp?email=" + encodeSpecialCode(email) + "&emailType=" + emailType);
	    			}
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}
</script>
</body>
</html>