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
        	<form class="form-horizontal" role="form"  id="form" name="form" action="">
        	
        		  <div class="form-group">
                      <label for="code" class="col-sm-3 control-label">登录账户：</label>
                      <div class="col-sm-9">
                          <input type="text" class="form-control" id="code" name="code" value="" disabled="disabled">
                      </div>
                  </div>
                  
                  <div class="form-group">
                      <label for="orgShortname" class="col-sm-3 control-label">机构简称：</label>
                      <div class="col-sm-9">
                          <input type="text" class="form-control validate[maxSize[255]]" type="text" id="orgShortname" name="orgShortname" placeholder="机构简称">
                      </div>
                  </div>
                  
                  <div class="form-group">
                      <label for="orgFullname" class="col-sm-3 control-label">机构全称：</label>
                      <div class="col-sm-9">
                          <input type="text" class="form-control validate[maxSize[255]]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称">
                      </div>
                  </div>
                  
                 
                  
                  <div class="form-group">
                      <label for="intro" class="col-sm-3 control-label">机构简介：</label>
                      <div class="col-sm-9">
                          
                          <textarea class="form-control" rows="5" id="intro" name="intro" placeholder="intro"></textarea>
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
<script type="text/javascript" src="../user/user/script/orgBasicInfo.js"></script>
</body>
</html>