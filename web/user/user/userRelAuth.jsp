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
<link rel="stylesheet" type="text/css" href="../css/userCommon.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="../../userout/script/userCommon.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath %>";
</script>
<script type="text/javascript" src="../../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.js" ></script>
<script type="text/javascript" src="script/userRelAuth.js"></script>
</head>
<body>
<div class="job_add">
	<form class="setting-form" id="form" name="form" action="">
		<div class="input">
	        <label for="name">姓名：</label>
	        <input class="form-control validate[required]" type="text" id="name" name="name" />
	    </div>
		<div class="input">
			<label for="perProvince">工作所在地：</label>
			<select id="perProvince" name="perProvince">
				<option>请选择省份</option>
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
			<select id="perCity" name="perCity">
				<option>请选择城市</option>
			</select>
		</div>
		<div class="input">
	        <label for="perJob">职业：</label>
	        <select id="perJob" name="perJob" class="custform-select validate[maxSize[255],required]" wx-validator-error-value="选择职业">
		        <option value="1">导演</option>
		        <option value="2">演员</option>
		        <option value="3">摄影</option>
		        <option value="4">后期</option>
	        </select>
	    </div>
		<div class="input">
	        <label for="perPhone">手机：</label>
	        <input class="form-control validate[required,custom[mobilephone],required]" type="text" id="perPhone" name="perPhone" />
	    </div>
	    <div class="input">
	        <label for="identityCard">身份证：</label>
	        <input class="form-control validate[custom[shenfenzheng],required]" type="text" id="identityCard" name="identityCard" />
	    </div>
	    <div class="input">
	        <label for="bankNums">银行卡号：</label>
	        <input class="form-control validate[required]" type="text" id="bankNums" name="bankNums" />
	    </div>
	    <div class="input">
	        <label for="openingBanks">开户行：</label>
	        <input class="form-control validate[required]" type="text" id="openingBanks" name="openingBanks" />
	    </div>
	    <div class="btn">
	    	<input type="submit" id="btnSave" value="提交信息">
	    </div>
	</form>
</div>
</body>
</html>