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
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="script/userPwdChange.js"></script>
</head>
<body>
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
</body>
</html>