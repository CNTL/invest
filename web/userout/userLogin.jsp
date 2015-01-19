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
        <div class="form">
            <form id="form" action="" method="post">
                <h2>用户登录</h2>
                <div class="input">
                    <input type="text" class="form-control validate[maxSize[255],required]" id="usercode" name="usercode" value="" placeholder="登录账号"/>
                </div>
                <div class="input">
                    <input type="password" class="form-control validate[maxSize[255],required]" id="password" name="password" value="" placeholder="登录密码"/>
                </div>
                <div class="info">
                    <input type="checkbox" id="auto" name="auto" checked="checked" /> 记住我
                    <a href="../userout/findPwdStep1.jsp" class="forget">忘记密码？</a>
                </div>
                <div class="btn">
                    <input type="submit" id="login" name="login" value="登录" />
                </div>
            </form>
        </div>
        <div class="other">
            <div class="info">还没有账号？<a href="../user/registerMain.do">立即注册</a><br />
            也可以使用以下账号直接登录：</div>
            <div class="third">
			    <a href="../user/userlogin.do?a=qqlogin" target="_blank" class="icon_qq" title="使用腾讯QQ帐号登录"></a>
                <a id="wb_connect_btn" href="#"><img src="../static/image/sina2.png" /></a> 
                <a href="../user/userlogin.do?a=qqlogin"><img src="../static/image/qq.png" /></a>
            </div>
        </div>
    </div>
	<!-- footer -->
	<%@include file="../inc/footer.inc"%>
	<!-- footer -->
	<!-- script -->
	<%@include file="./inc/script.inc"%>
	<!-- script -->
	<script type="text/javascript">
	var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
	</script>    
    <script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../userout/script/validate-login.js"></script>
</body>
</html>