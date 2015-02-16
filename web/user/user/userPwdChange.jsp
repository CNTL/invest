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
<div class="container container-ex">
	<%@include file="../inc/userHeader.inc"%>
	<div class="row container-wapper">
	<%@include file="../inc/userHeaderMenu.inc"%>
	<div class="col-md-8">
        <div class="container-right">
        	<form class="form-horizontal" role="form"  id="form" name="form" method="post" action="">
        	
        		  <div class="form-group">
                      <label for="code" class="col-sm-3 control-label">原始密码：</label>
                      <div class="col-sm-9">
                          <input type="password" class="form-control validate[maxSize[255],required" onchange="pwdChange.checkOldpassword()" value="" id="old_pwd" name="old_pwd" placeholder="原始密码">
                      </div>
                  </div>
                  
                   <div class="form-group">
                      <label for="code" class="col-sm-3 control-label">新密码：</label>
                      <div class="col-sm-9">
                          <input type="password" class="form-control validate[maxSize[255],required" onchange="pwdChange.checkpassword()" value="" id="password" name="password" placeholder="新密码">
                      </div>
                  </div>
                  
                   <div class="form-group">
                      <label for="code" class="col-sm-3 control-label">确认密码：</label>
                      <div class="col-sm-9">
                          <input type="password" class="form-control validate[maxSize[255],required" onchange="pwdChange.checkpassword_again()" value="" id="pwdagain" name="pwdagain" placeholder="确认密码">
                      </div>
                  </div>
                  
                 
                  
                  <div class="form-group">
                     <div class="col-sm-12 text-center">
                         <button type="submit" class="btn btn-primary" id="btnSave">保存信息</button>
                     </div>
                 </div>
                 
        	</form>
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