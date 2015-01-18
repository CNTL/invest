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
        	<img src="../userout/img/psw_step3.png" width="369" height="56" alt="找回密码第三步" />
            <div class="input">
            	<span>邮件地址：<a>${user.email == null ? "" : user.email }</a></span>
            	<br>
            	<span>登陆用户名：<a>${user.code == null ? "" : user.code }</a></span>
            </div>
            <form id="pswForm">
            	<div class="input" style="margin-left:40px;">
            		<input type="password" name="new_password" id="new_password" tabindex="1" placeholder="请输入新密码" />
            	</div>
             	<div class="input" style="margin-left:40px;">
              		<input type="password" name="confirm_password" id="confirm_password" tabindex="2" placeholder="请再次输入新密码" />
              	</div>
                <span id="beError" class="error" style="display:none;"></span>
                <input type="hidden" id="userId" name="userId" value='${user.id == null ? "0" : user.id }' />
                <input type="hidden" id="oldpwd" name="oldpwd" value='${user.password == null ? "0" : user.password }' />
                <div class="btn" style="margin-left:90px;">
                 	<input type="submit" id="submitLogin" value="确 &nbsp; &nbsp; 定" />
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
<script type="text/javascript">
    $(document).ready(function() {
    	//验证表单
   		$("#pswForm").validate({
   	        rules: {
   	        	new_password: {
	    	    	required: true,
	    	    	rangelength: [6,16]
	    	   	},
	    	   	confirm_password: {
	    	    	required: true,
	    	   	    equalTo: "#new_password"
	    	   	}
	    	},
	    	messages: {
	    		new_password: {
	    	    	required: "请输入新密码",
	    	    	/* rangelength: "请输入6-16位密码，字母区分大小写" */
	    	    	rangelength: "请输入6-16位新密码，字母区分大小写"
	    	   	},
	    	   	confirm_password: {
	    	    	required: "请再次输入新密码",
	    	    	equalTo: "两次输入的密码不一致，请重新输入"
	    	   	}
	    	},
	    	submitHandler:function(form){ 
	    		var new_password =$('#new_password').val();
	    		var confirm_password =$('#confirm_password').val();
	    		var userId = $("#userId").val();
	    		var oldpwd = $("#oldpwd").val();
	    		var params = {
   			       "id": userId,
   			       "newpwd": new_password
    			};
	    		$.ajax({
	    			type: "POST",
	    	        url:"../user/userlogin.do?a=updatePwd", //请求路径  
	    	        data: $.param(params),
	    	        dataType: "TEXT",
	    	        success:function(data){
	    	        	window.location.href='../user/loginMain.do';
	    	        } ,
	    			error:function (XMLHttpRequest, textStatus, errorThrown) {
	    				 alert(errorThrown);
	    			}
	    	    });
	        }  
   	    });
   	});
    
</script>
</body>
</html>
    
</body>
</html>