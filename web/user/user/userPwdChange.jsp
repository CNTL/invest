<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<%@include file="../inc/csslink.inc"%>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			<div class="setting-detail">
				<div class="job_add">
					<form id="form" name="form" method="post" action="">
						<div class="input">
							<label for="code">原始密码：</label>
							<input type="password" class="form-control validate[maxSize[255],required]" onchange="pwdChange.checkOldpassword()" value="" id="old_pwd" name="old_pwd" placeholder="原始密码"/>
						</div>
						<div class="input">
							<label for="userpassword">新密码：</label>
							<input type="password" class="form-control validate[maxSize[255],required]" onchange="pwdChange.checkpassword()" value="" id="password" name="password" placeholder="新密码"/>
						</div>
						<div class="input">
							<label  for="passwordagain">确认密码：</label>
							<input type="password" class="form-control validate[maxSize[255],required]" onchange="pwdChange.checkpassword_again()" value="" id="pwdagain" name="pwdagain" placeholder="确认密码"/>
						</div>
						<div class="btn">
				        	<input type="submit" id="btnSave" value="提交信息">
				        </div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript" src="../user/user/script/userPwdChange.js"></script>
</body>
</html>