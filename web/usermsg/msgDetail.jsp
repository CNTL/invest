<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
String rootPath = request.getScheme() + "://" + request.getServerName() + 
	":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>众筹网-中国最具影响力的众筹平台</title>
<meta name="keywords" content="众筹 创业 项目 投资 支持">
<meta name="description" content="在众筹网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link type="text/css" rel="stylesheet" href="../js/plugin/jquery-autocomplete/jquery.autocomplete.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../user/css/userCommon.css">
<link rel="stylesheet" type="text/css" href="css/msgSend.css">
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-autocomplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
var rootPath = "<%=rootPath %>";
</script>
<script type="text/javascript" src="../common/autocomplete.js"></script>
<script type="text/javascript" src="script/msgDetail.js"></script>
</head>
<body>
<div style="display:none">
	<input type="text" id="msg_toID" name="msg_toID" value="${param.msg_toID}"/>
	<input type="text" id="msg_to" name="msg_to" value="${param.msg_to}"/>
</div>
<div class="message-box">
	<!--message static-->
	<div class="message-item">
		<div class="hd">
			<h3 class="fl">与 <span class="red">${param.msg_to}</span>的对话</h3>
			<a class="btn-base btn-gray-h20 common-sprite fr" href="msgSend.jsp">
				<span class="common-sprite">返回</span>
			</a>
		</div>
		<div class="bd">
			<ul class="clearfix">
			</ul>
			<div class="page tr"></div>
		</div>
	</div>				
	<!--message end-->
</div>
<div id="msgDiv" style="display:none">
	<ul>
		<li class="talk-list">
			<div class="talk-face">
				<a class="userHead" href="/home/id-339712" target="_blank">
					<img src="http://zcstatic.wangxingroup.com/img/defaultavatar/noavatar_middle.gif">
				</a>
			</div>
			<div class="msg-box">
				<div class="user-name">
					<a class="msgTo" href="/home/id-339712" target="_blank"> </a>
					<span class="gray"></span>
				</div>
				<div class="msg-cnt"></div>
				<div class="talk-handle tr">
					<a class="Js-reply">回复</a>
					<a class="delMsg">删除</a>
				</div>
			</div>
		</li>
	</ul>
	<div class="page tr"></div>
</div>
</body>
</html>