<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>合众映画</title>
<meta charset="utf-8">
<meta name="keywords" content="合众映画" />
<meta name="description" content="合众映画" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/userCenter.css">
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/plugin/backstretch/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
</head>
<body>
<!--header static-->
<%@ include file="./common/orgHead.inc"%>
<div class="main clearfix">
	<div class="setting wrap">
		<div class="setting-title clearfix">
		<h3>机构设置</h3>
		<a href="orgSetting.jsp">返回机构中心</a>
		</div>
		<div class="setting-content clearfix">
			<div class="setting-menu">
				<ul class="clearfix">
					<li class="select"><a id="infoEdit" class="icons info ie6fixpic" onclick="userset.change(this);">基本资料</a></li>
					<li><a id="pwsEdit" class="icons password ie6fixpic" onclick="userset.change(this);">修改密码</a></li>
					<li><a id="headEdit" class="icons portrait ie6fixpic" onclick="userset.change(this);">头像管理</a></li>
					<li><a id="relAuth" class="icons portrait ie6fixpic" onclick="userset.change(this);">机构认证</a></li>
					<li><a id="detail" class="icons info ie6fixpic" onclick="userset.change(this);">详细资料</a></li>
					<li><a id="photo" class="icons portrait ie6fixpic" onclick="userset.change(this);">个人图册</a></li>
					<li><a id="video" class="icons portrait ie6fixpic" onclick="userset.change(this);">个人视频</a></li>
				</ul>
			</div>
			<div class="setting-detail">
				<iframe id="main" style="width:100%;height:600px;border:0px;" src="user/orgBasicInfo.jsp"></iframe>  
			</div>
		</div>
	</div>
</div>
<%@ include file="./common/footing.inc"%>
<script type="text/javascript" src="script/orgSetting.js"></script>
</body>
</html>