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
        		<input type="hidden" id="firstType_h" name="firstType_h" value=""/>
				<input type="hidden" id="secondType_h" name="secondType_h" value=""/>
				
				<div class="form-group">
                      <label for="code" class="col-sm-4 control-label">认证状态：</label>
                      <div class="col-sm-6">
                          <label for=""><span id="isIdent"></span></label>
                      </div>
                  </div>
                  
                   <div class="form-group">
                      <label for="name" class="col-sm-4 control-label">姓名：</label>
                      <div class="col-sm-6">
                          <input type="text" class="form-control validate[required]" type="text" id="name" name="name" >
                      </div>
                  </div>
                  
                  <div class="form-group" style="display:none;">
                      <label for="firstType" class="col-sm-4 control-label">职业：</label>
                      <div class="col-sm-3">
                          <select id="firstType" name="firstType" class="form-control "></select>
							  
                      </div>
                      <div class="col-sm-3">
							<select id="secondType" name="secondType" class="form-control "></select>
                      </div>
                  </div>
                  
                   <div class="form-group">
                      <label for="perPhone" class="col-sm-4 control-label">手机：</label>
                      <div class="col-sm-6">
                          <input type="text" class="form-control validate[required,custom[mobilephone],required]" type="text" id="perPhone" name="perPhone" >
                      </div>
                  </div>
                  
                   <div class="form-group">
                      <label for="identityCard" class="col-sm-4 control-label">身份证号码：</label>
                      <div class="col-sm-6">
                          <input type="text" class="form-control validate[custom[shenfenzheng],required]" type="text" id="identityCard" name="identityCard">
                      </div>
                  </div>
                  
                   <div class="form-group">
                    <label for="identityCard" class="col-sm-4 control-label">手持身份证正面照：</label>
                    
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
                    <label for="identityCard" class="col-sm-4 control-label">身份证背面照：</label>
                    
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
 
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../user/user/script/datas.js"></script>
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript" src="../user/user/script/userRelAuth.js"></script>
</body>
</html>