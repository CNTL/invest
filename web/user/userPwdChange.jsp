<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>影投网-中国最具影响力的影投平台</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/userCommon.css">
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="script/userPwdChange.js"></script>
</head>
<body>
<div class="content">
	<form id="form" name="form" method="post" action="">
		<br><br>
		<div class="form-group form-item">
			<label for="code"><i style="color:red;">*</i>原始密码：</label>
			<input type="password" class="form-control validate[maxSize[255],required]" onchange="pwdChange.checkOldpassword()" value="" id="old_pwd" name="old_pwd"/>
		</div>
		<div class="form-group form-item">
			<label for="userpassword"><i style="color:red;">*</i>新密码：</label>
			<input type="password" class="form-control validate[maxSize[255],required]" onchange="pwdChange.checkpassword()" value="" id="password" name="password">
		</div>
		<div class="form-group form-item">
			<label  for="passwordagain"><i style="color:red;">*</i>确认密码：</label>
			<input type="password" class="form-control validate[maxSize[255],required]" onchange="pwdChange.checkpassword_again()" value="" id="pwdagain" name="pwdagain">
		</div>
		<div style="width:200px" align="left" class="form-actions">
	        <button id="btnSave" type="submit" class="btn blue pull-right">
	           	<i onclick="">保存</i>
	        </button>
	    </div>
	</form>
</div>
</body>
</html>