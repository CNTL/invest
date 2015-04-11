<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<%@include file="../user/inc/csslink.inc"%>
</head>

<body>
<%@include file="../inc/header.inc"%>
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
                 	<input type="submit" id="submitLogin" class="btn btn-success" value="找回密码" />
                </div>
            </form>
        </div>
    </div>
    
   
	<!-- footer -->
	<!-- script -->
	<%@include file="../user/inc/script.inc"%>
	<!-- script -->
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
	    				 
	    				$.messager.alert('消息',data.error);
	    			} else {
	    				var email = data.email;
	    				var emailType = data.emailType;
	    				window.location.replace("sendpwd.do?email=" + encodeSpecialCode(email) + "&emailType=" + emailType);
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
 <!-- footer -->
	<%@include file="../inc/footer.inc"%>
</body>
</html>