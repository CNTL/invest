<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html><head>
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
<script type="text/javascript" src="script/userComplete.js"></script>
</head>
<body>
<div class="content">
	<form class="setting-form" id="form" name="form" action="">
		<div class="form-group form-item">
	        <label class="red">登录账户：</label>
	        <p class="form-msg red" id="code"></p>
	        <br>
	    </div>
		<div class="form-group form-item">
	        <label for="email"><i style="color:red;">*</i>邮箱：</label>
	        <input class="form-control validate[maxSize[255],custom[email],required]" type="text" autocomplete="off" id="email" name="email" />
	    </div>
	    <div class="form-group form-item">
	        <label for="nickName">昵称：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" autocomplete="off" id="nickName" name="nickName" />
	    </div>
	    <div class="form-group form-item">
	        <label for="postAddr">邮寄地址：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" autocomplete="off" id="postAddr" name="postAddr" />
	    </div>
		<div class="form-group form-item">
			<label>简介：</label>
			<textarea  id="intro" name="intro" style="width:400px;height:100px;" placeholder="您的介绍可更有效的帮助支持者了解您和了解项目的背景"></textarea>
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