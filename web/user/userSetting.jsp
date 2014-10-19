<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html><head>
<meta charset="utf-8">
<title>影投网-中国最具影响力的影投平台</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="./css/userSetting.css">
<link rel="stylesheet" type="text/css" href="css/userCenter.css">
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/plugin/backstretch/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="../common/head.js"></script>
<script type="text/javascript" src="script/userSetting.js"></script>
</head>
<body>
<!--header static-->
<%@ include file="../common/head.inc"%>
<div class="main clearfix">
	<div class="setting wrap">
		<div class="setting-title clearfix">
		<h3>个人设置</h3>
		<a href="">返回个人中心</a>
		</div>
		<div class="setting-content clearfix">
			<div class="setting-menu">
				<ul class="clearfix">
					<li class="select"><a id="infoEdit" class="icons info ie6fixpic" onclick="userset.change(this);">资料修改</a></li>
					<li><a id="pwsEdit" class="icons password ie6fixpic" onclick="userset.change(this);">密码修改</a></li>
					<li><a id="headEdit" class="icons portrait ie6fixpic" onclick="userset.change(this);">头像修改</a></li>
					<li><a id="relAuth" class="icons portrait ie6fixpic" onclick="userset.change(this);">实名认证</a></li>
					<li><a id="photo" class="icons portrait ie6fixpic" onclick="userset.change(this);">个人图册</a></li>
					<li><a id="video" class="icons portrait ie6fixpic" onclick="userset.change(this);">个人视频</a></li>
				</ul>
			</div>
			<div class="setting-detail">
				<iframe id="main" style="width:100%;height:600px;border:0px;" src="userComplete.jsp"></iframe>  
			</div>
		</div>
	</div>
</div>
</body>
</html>