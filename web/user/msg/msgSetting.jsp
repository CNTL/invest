<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>合众映画</title>
<meta charset="utf-8">
<meta name="keywords" content="合众映画" />
<meta name="description" content="合众映画" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../css/userCenter.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/plugin/backstretch/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
</head>
<body>
<%@ include file="../common/userHead.inc"%>
<div class="main clearfix">
	<div class="setting wrap">
		<div class="setting-title clearfix">
		<h3>消息中心</h3>
		<a href="">返回个人中心</a>
		</div>
		<div class="setting-content clearfix">
			<div class="setting-menu">
				<ul class="clearfix">
					<li class="select"><a id="msgSend" class="icons info ie6fixpic" onclick="msg.change(this);">私信</a></li>
				</ul>
			</div>
			<div class="setting-detail">
				<iframe id="main" style="width:100%;height:600px;border:0px;" src="msgSend.jsp"></iframe>  
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="../script/userSetting.js"></script>
<script type="text/javascript" src="script/msgSend.js"></script>
</body>
</html>