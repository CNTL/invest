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
        	<img src="../userout/img/psw_step2.png" width="369" height="56" alt="找回密码第二步" />
        	<br><br>
           	<div class="input" style="margin-left:-100px;">
            	<span>密码重置邮件已发送至你的邮箱：<a>${param.email == null ? "" : param.email }</a></span>
                请在24小时内登录你的邮箱接收邮件，链接激活后可重置密码。
            </div>
            <div class="input" style="margin-left:100px;">
                <a href='${param.emailType == null ? "" : param.emailType }' id="submitLogin">
                	<span style="color:green;">登录邮箱查看</span>
                </a>
            </div>
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
</body>
</html>