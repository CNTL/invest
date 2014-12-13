<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<link rel="stylesheet" type="text/css" href="../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.css" />
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
					<form class="setting-form" id="form" name="form" action="">
						<div class="input">
					        <label for="orgFullname">公司全称：</label>
					        <input class="form-control validate[maxSize[255]]" type="text" id="orgFullname" name="orgFullname" />
					    </div>
						<div class="input">
					        <label for="name">法人代表：</label>
					        <input class="form-control validate[required]" type="text" id="name" name="name" />
					    </div>
					    <div class="input">
					        <label for="identityCard">法人代表身份证：</label>
					        <input class="form-control validate[custom[shenfenzheng],required]" type="text" id="identityCard" name="identityCard" />
					    </div>
					    <div class="input">
					        <label for="openingBanks">银行卡开户行：</label>
					        <input class="form-control validate[required]" type="text" id="openingBanks" name="openingBanks" />
					    </div>
					    <div class="input">
					        <label for="bankNums">银行卡号：</label>
					        <input class="form-control validate[required]" type="text" id="bankNums" name="bankNums" />
					    </div>
					    <div class="input">
					    	<label for="organization">企业组织机构证件照：</label>
					        <input name="organizationFile" id="organizationFile" type="file" />
					        <input name="organization" id="organization" type="hidden" value="" />
					        <input type="button" style="width:50px" id="organizationBtn" value="上传">
					        <div id="organizationWait" style="padding: 50px 0 0 0;display:none;">
						        <div style="width: 103px;margin: 0 auto;"><img src="../js/plugin/AjaxFileUploaderV2.1/loading.gif"/></div>
						        <br></br>
						        <div style="width: 103px;margin: 0 auto;"><span>请稍等...</span></div>
						        <br></br>
						    </div>
						    <div style="display:none;">
						    	<img id="organizationImg" name="organizationImg" style="width:200px;height:200px;" alt="" src="">
						    </div>
					    </div>
					    <div class="input">
					    	<label for="orgBusinessLicense">企业营业执照扫描件：</label>
					        <input name="orgBusinessLicenseFile"  id="orgBusinessLicenseFile" width="100px" class="form-control" type="file" />
					        <input type="button" style="width:50px" id="orgBusinessLicenseBtn" value="上传">
					        <input name="orgBusinessLicense" id="orgBusinessLicense" type="hidden" value="" />
					        <div id="orgBusinessLicenseWait" style="padding: 50px 0 0 0;display:none;">
						        <div style="width: 103px;margin: 0 auto;"><img src="../js/plugin/AjaxFileUploaderV2.1/loading.gif"/></div>
						        <br></br>
						        <div style="width: 103px;margin: 0 auto;"><span>请稍等...</span></div>
						        <br></br>
						    </div>
						    <div style="display:none;">
						    	<img id="orgBusinessLicenseImg" name="orgBusinessLicenseImg" style="width:200px;height:200px;" alt="" src="">
						    </div>
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
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript">
var basePath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.js" ></script>
<script type="text/javascript" src="../user/user/script/orgRelAuth.js"></script>
</body>
</html>