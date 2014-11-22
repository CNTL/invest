<%@page pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html><head>
<meta charset="utf-8">
<title>影投网-中国最具影响力的影投平台</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="../../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.css" />
<link rel="stylesheet" type="text/css" href="../css/userCommon.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="../script/userCommon.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>
<script type="text/javascript" src="../../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.js" ></script>
<script type="text/javascript" src="script/userRelAuth.js"></script>
</head>
<body>
<div class="content">
<form class="setting-form" id="form" name="form" action="">
	<div class="form-group form-item">
        <label for="name"><i style="color:red;">*</i>姓名：</label>
        <input class="form-control validate[required]" type="text" autocomplete="off" id="name" name="name" />
    </div>
    <div class="form-group form-item">
        <label for="type"><i style="color:red;">*</i>注册类型：</label>
        <div class="option-box">
	        <select id="type" name="type" class="custform-select validate[maxSize[255],required]" wx-validator-error-value="选择注册类型">
	        <option value="1">个人</option><option value="3">机构</option>  
	        </select>
        </div>
    </div>
	<div class="form-group form-item">
		<label for="province"><i style="color:red;">*</i>所在地：</label>
		<div class="option-box">
			<select id="province" name="province" wx-validator-error-value="选择省份">
				<option value="安徽" rel="3">安徽</option>
				<option value="澳门" rel="396">澳门</option>
				<option value="北京" rel="52">北京</option>
				<option value="福建" rel="4">福建</option>
				<option value="甘肃" rel="5">甘肃</option>
				<option value="广东" rel="6">广东</option>
				<option value="广西" rel="7">广西</option>
				<option value="贵州" rel="8">贵州</option>
				<option value="海南" rel="9">海南</option>
				<option value="河北" rel="10">河北</option>
				<option value="黑龙江" rel="12">黑龙江</option>
				<option value="河南" rel="11">河南</option>
				<option value="湖北" rel="13">湖北</option>
				<option value="湖南" rel="14">湖南</option>
				<option value="江苏" rel="16">江苏</option>
				<option value="江西" rel="17">江西</option>
				<option value="吉林" rel="15">吉林</option>
				<option value="辽宁" rel="18">辽宁</option>
				<option value="内蒙古" rel="19">内蒙古</option>
				<option value="宁夏" rel="20">宁夏</option>
				<option value="青海" rel="21">青海</option>
				<option value="山东" rel="22">山东</option>
				<option value="上海" rel="321">上海</option>
				<option value="山西" rel="23">山西</option>
				<option value="陕西" rel="24">陕西</option>
				<option value="四川" rel="26">四川</option>
				<option value="台湾" rel="397">台湾</option>
				<option value="天津" rel="343">天津</option>
				<option value="香港" rel="395">香港</option>
				<option value="西藏" rel="28">西藏</option>
				<option value="新疆" rel="29">新疆</option>
				<option value="云南" rel="30">云南</option>
				<option value="浙江" rel="31">浙江</option>
				<option value="重庆" rel="394">重庆</option>
			</select>
			<select id="city" name="city" wx-validator-error-value="请选择城市">
				<option>请选择城市</option>
			</select>
	        <span id="wx-validator-province-error" class="error-text hidden">请选择省份</span>
	        <span id="wx-validator-city-error" class="error-text hidden">请选择城市</span>
		</div>
	</div>
	<div class="form-group form-item">
        <label for="job"><i style="color:red;">*</i>职业：</label>
        <div class="option-box">
	        <select id="job" name="job" class="custform-select validate[maxSize[255],required]" wx-validator-error-value="选择职业">
		        <option value="1">导演</option>
		        <option value="2">演员</option>
		        <option value="3">摄影</option>
		        <option value="4">后期</option>
	        </select>
        </div>
    </div>
	<div class="form-group form-item">
        <label for="phone">手机：</label>
        <input class="form-control validate[required,custom[mobilephone],required]" type="text" autocomplete="off" id="phone" name="phone" />
    </div>
    <div class="form-group form-item">
        <label for="identityCard">身份证：</label>
        <input class="form-control validate[custom[shenfenzheng],required]" type="text" autocomplete="off" id="identityCard" name="identityCard" />
    </div>
    <div class="form-group form-item">
        <label for="openingBanks"><i style="color:red;">*&nbsp;&nbsp;</i>银行卡开户行：</label>
        <input class="form-control validate[required]" type="text" id="openingBanks" name="openingBanks" />
    </div>
    <div class="form-group form-item">
        <label for="bankNums"><i style="color:red;">*&nbsp;&nbsp;</i>银行卡号：</label>
        <input class="form-control validate[required]" type="text" id="bankNums" name="bankNums" />
    </div>
    <div class="form-group form-item">
    	<label for="organization"><i style="color:red;">*&nbsp;&nbsp;</i>企业组织机构证件照：</label>
        <input name="organizationFile" id="organizationFile" width="300px" class="form-control" type="file" />
        <input name="organization" id="organization" type="hidden" value="" />
        <button id="organizationBtn" type="button" class="btn blue uploadbtn">
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
    <div class="form-group form-item">
    	<label for="businessLicense"><i style="color:red;">*&nbsp;&nbsp;</i>企业营业执照扫描件：</label>
        <input name="businessLicenseFile"  id="businessLicenseFile" width="100px" class="form-control" type="file" />
        <input name="businessLicense" id="businessLicense" type="hidden" value="" />
        <button id="businessLicenseBtn" type="button" class="btn blue uploadbtn">
           	<i onclick="">上传</i>
        </button>
        <div id="businessLicenseWait" style="padding: 50px 0 0 0;display:none;">
	        <div style="width: 103px;margin: 0 auto;"><img src="../../js/plugin/AjaxFileUploaderV2.1/loading.gif"/></div>
	        <br></br>
	        <div style="width: 103px;margin: 0 auto;"><span>请稍等...</span></div>
	        <br></br>
	    </div>
	    <div style="display:none;">
	    	<img id="businessLicenseImg" name="businessLicenseImg" style="width:200px;height:200px;" alt="" src="">
	    </div>
    </div>
	<div style="width:200px" align="left" class="form-actions">
        <button id="btnSave" type="submit" class="mybtn blue pull-right">
           	<i onclick="">保存</i>
        </button>
    </div>
</form>
</div>
</body>
</html>