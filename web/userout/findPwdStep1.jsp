<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>合众映画</title>
    <meta name="keywords" content="合众映画" />
    <meta name="description" content="合众映画" />
    <%@include file="../user/inc/csslink.inc"%>
</head>
<body>
	<div class="shadow"></div>
	<div class="login">
    	<input type="hidden" id="resubmitToken" value="" />
     	<div class="form" style="margin-left:200px;">
        	<img src="../userout/img/psw_step1.png" width="369" height="56" alt="找回密码第一步" />
        	<br><br>
            <form id="form" name="form" action="../user/user.do?a=findPwd" method="post">
           		<div class="input" style="margin-left:40px;">
	           		<input type="text" name="email" id="email" class="form-control validate[maxSize[255],custom[email],required]" tabindex="1" value="" placeholder="请输入注册时使用的邮箱地址" />
                </div>
                <br>
                <div class="btn" style="margin-left:90px;">
                 	<input type="submit" id="submitLogin" value="找回密码" />
                </div>
            </form>
        </div>
    </div>
    
    <!-- footer -->
	<%@include file="../inc/footer.inc"%>
	<!-- footer -->
	<!-- script -->
	<%@include file="./inc/script.inc"%>
	<!-- script -->
    <script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="script/findPwd.js"></script>
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