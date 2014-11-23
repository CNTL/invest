<%@page pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<title>合众映画</title>
<meta charset="utf-8">
<meta name="keywords" content="合众映画" />
<meta name="description" content="合众映画" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.css" />
<link rel="stylesheet" type="text/css" href="../../static/css/reset.css" />
<link rel="stylesheet" type="text/css" href="../../static/css/index.css" />
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="../../userout/script/userCommon.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>
<script type="text/javascript" src="../../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.js" ></script>
<script type="text/javascript" src="script/orgRelAuth.js"></script>
<style type="text/css">
	.uploadbtn{
		margin-top : 40px;
		width: 50px;
        height: 40px;
        line-height: 15px;
        border: 0;
        background-color: #4AC4EF;
        color: #fff;
        font-size: 14px;
        cursor: pointer;
	}
</style>
</head>
<body>
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
	        <button id="organizationBtn" type="button" type="submit" class="uploadbtn">
	           	<i onclick="">上传</i>
	        </button>
	        <div id="organizationWait" style="padding: 50px 0 0 0;display:none;">
		        <div style="width: 103px;margin: 0 auto;"><img src="../../js/plugin/AjaxFileUploaderV2.1/loading.gif"/></div>
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
	        <button id="orgBusinessLicenseBtn" type="button" type="submit" class="uploadbtn">
	           	<i onclick="">上传</i>
	        </button>
	        <input name="orgBusinessLicense" id="orgBusinessLicense" type="hidden" value="" />
	        <div id="orgBusinessLicenseWait" style="padding: 50px 0 0 0;display:none;">
		        <div style="width: 103px;margin: 0 auto;"><img src="../../js/plugin/AjaxFileUploaderV2.1/loading.gif"/></div>
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
</body>
</html>