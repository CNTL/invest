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
    <div class="register">
        <div class="form">
            <form id="form" action="" method="post">
                <input type="hidden" name="type" id="type" value="0" />
                <h2>用户注册</h2>
                <div class="select">
                    <ul id="roleSelect">
                    	 <li data="0" class="current">个人</li>
                        <li data="1">机构</li>
                    </ul>
                </div>
                <div class="input">
                	<input type="text" class="form-control validate[maxSize[255],required]" id="code" name="code" placeholder="请输入用户名"/>
                </div>
                <div class="input">
                	<input type="text" class="form-control validate[maxSize[255],required]" id="email" name="email" placeholder="请输入邮箱"/>
				</div>
                <div class="input">
					<input type="password" class="form-control validate[maxSize[255],required]" onchange="checkpassword();" value="" id="password" name="password" placeholder="请输入密码"/>
				</div>
                <div class="input">
					<input type="password" class="form-control validate[maxSize[255],required]" onchange="checkpassword_again();" value="" id="pwdagain" name="pwdagain" placeholder="请再次输入密码"/>
	            </div>
                <div class="input" style="margin-bottom: 25px;">
                	<input type="text" style=" width: 100px;margin-right: 15px;" class="form-control validate[maxSize[255],required]" onchange="checkMyVal();" id="myVal" name="myVal"  placeholder="请输入校验码"/>
		            <img id="validateCode" src="" style="vertical-align: middle; margin-right: 20px;"></img><a id="changeCode" style="cursor:pointer;" onclick="changeValPic();">换一张</a>
		            <input type="hidden" id="curVal" name="curVal" style="color: #fe6249;text-decoration: underline;cursor: pointer;" value=""/>
	            </div>
                <div class="btn">
                    <input type="submit" id="login" name="login" value="提交申请" />
                </div>
            </form>
        </div>

    </div>
	<!-- footer -->
	<%@include file="../inc/footer.inc"%>
	<!-- footer -->
	<!-- script -->
	<%@include file="./inc/script.inc"%>
	<script type="text/javascript">
	var rootURL = "<%=com.tl.common.WebUtil.getRoot(request) %>";
	</script>
	<!-- script -->  
    <script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../userout/script/userRegister.js"></script>
</body>
</html>