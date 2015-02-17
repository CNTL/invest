<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
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
        		 
				<input class="form-control" type="hidden" id="openingBanks" name="openingBanks" />
                <input class="form-control" type="hidden" id="bankNums" name="bankNums" />
				
				<div class="form-group">
                      <label for="code" class="col-sm-4 control-label">认证状态：</label>
                      <div class="col-sm-6">
                          <label for=""><span id="isIdent"></span></label>
                      </div>
                  </div>
                  
                   <div class="form-group">
                      <label for="orgFullname" class="col-sm-4 control-label">公司全称：</label>
                      <div class="col-sm-6">
                          <input type="text" class="form-control validate[required]" id="orgFullname" name="orgFullname" >
                      </div>
                  </div>
                  
                  <div class="form-group">
                      <label for="name" class="col-sm-4 control-label">法人代表：</label>
                      <div class="col-sm-6">
                          <input type="text" class="form-control validate[required]" id="name" name="name" >
                      </div>
                  </div>
                  
                   <div class="form-group">
                      <label for="identityCard" class="col-sm-4 control-label">法人代表身份证：</label>
                      <div class="col-sm-6">
                          <input type="text" class="form-control validate[custom[shenfenzheng],required]" id="identityCard" name="identityCard" >
                      </div>
                  </div>
                  
                  <div class="form-group">
                      <label for="perPhone" class="col-sm-4 control-label">法人代表手机：</label>
                      <div class="col-sm-4">
                          <input type="text" class="form-control validate[custom[mobilephone],required]" type="text" id="perPhone" name="perPhone" style="width:200px">
                          
                      </div>
                      <div class="col-sm-2">
                      	<span class="btn btn-info" id="btnGetCode">获取验证码</span>
                      </div>
                  </div>
                  
                  <div class="form-group">
                      <label for="perPhoneCode" class="col-sm-4 control-label">短信验证码：</label>
                      <div class="col-sm-2">
                           <input class="form-control" type="text" id="perPhoneCode" name="perPhoneCode"  />
					        <input type="hidden" id="perPhoneCodeCur" name="perPhoneCodeCur"/>
                      </div>
                  </div>
                  
                  
                  
                   <div class="form-group">
                    <label for="uploadify" class="col-sm-4 control-label">准拍证扫描件：</label>
                    
				 
					<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
					<input type="hidden" id="organization" name="organization" value="" />
					<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
					<div class="col-sm-2">
						<input type="file" name="uploadify" id="uploadify" />
					</div>
                      <div class="col-sm-6">
                          <div id="coverIMG_div" style="display:none;width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;"></div>
                      </div>
                  </div>
                  <div class="form-group">
                      <label  class="col-sm-4 control-label">或者</label>
                       
                  </div>
                  <div class="form-group">
                    <label for="identityCard" class="col-sm-4 control-label">企业营业执照扫描件：</label>
					<input type="hidden" id="queueItemCount1" name="queueItemCount1" value="0" />
					<input type="hidden" id="orgBusinessLicense" name="orgBusinessLicense" value="" />
					<input type="hidden" id="uploadErrorMsg1" name="uploadErrorMsg1" value="" />
					<div class="col-sm-2">
						<input type="file" name="uploadify1" id="uploadify1" />
					</div>
                      <div class="col-sm-6">
                          <div id="coverIMG_div1" style="display:none; width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;"></div>
                      </div>
                  </div>
                  <div class="form-group">
                     <div class="col-sm-12 text-center">
                         <button type="submit" class="btn btn-primary" id="btnSave">保存</button>
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
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../user/user/script/orgRelAuth.js"></script>
</body>
</html>