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
<script type="text/javascript" src="script/findPwd.js"></script>
</head>

<body id="login_bg">
	<div class="login_wrapper">
    	<input type="hidden" id="resubmitToken" value="" />
        <div class="find_psw">
        	<img src="../img/psw_step2.png" width="369" height="56" alt="找回密码第二步" />
           	<div class="c9 sendemail">
            	<span>密码重置邮件已发送至你的邮箱：<a>${param.email == null ? "" : param.email }</a></span>
                请在24小时内登录你的邮箱接收邮件，链接激活后可重置密码。
            </div>
                <a href='${param.emailType == null ? "" : param.emailType }' id="submitLogin" target="_blank">登录邮箱查看</a>
            </div>
    </div>
</body>
</html>