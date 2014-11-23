<%@page pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="../../static/css/reset.css" />
<link rel="stylesheet" type="text/css" href="../../static/css/index.css" />
<link rel="stylesheet" type="text/css" href="../css/userCommon.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="script/orgBasicInfo.js"></script>
</head>
<body>
<div class="job_add">
	<form class="setting-form" id="form" name="form" action="">
		<div class="input">
	        <label for="nickName">登录账户：</label>
	        <input type="text" id="code" name="code" value="" disabled="true"/>
	        <br>
	    </div>
	    <div class="input">
	        <label for="nickName">机构简称：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="orgShortname" name="orgShortname" placeholder="机构简称"/>
	    </div>
	    <div class="input">
	        <label for="postAddr">机构全称：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称"/>
	    </div>
		<div class="input">
			<label>机构简介：</label>
			<textarea  id="intro" name="intro" style="width:400px;height:100px;" placeholder="机构简介"></textarea>
		</div>
		<div class="btn">
        	<input type="submit" id="btnSave" value="提交信息">
        </div>
	</form>
</div>
</body>
</html>