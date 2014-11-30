<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
String rootPath = request.getScheme() + "://" + request.getServerName() + 
	":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>合众映画</title>
<meta charset="utf-8">
<meta name="keywords" content="合众映画" />
<meta name="description" content="合众映画" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="../../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="../../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link type="text/css" rel="stylesheet" href="../../js/plugin/jquery-autocomplete/jquery.autocomplete.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../user/css/userCommon.css">
<link rel="stylesheet" type="text/css" href="css/msgSend.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-autocomplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
var rootPath = "<%=rootPath %>";
</script>
<script type="text/javascript" src="../../common/autocomplete.js"></script>
<script type="text/javascript" src="script/msgDetail.js"></script>
</head>
<body>
<div id="w" class="easyui-window" data-options="title:'发送私信',iconCls:'icon-save'" style="width:500px;height:300px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
			<form class="setting-form" id="form" name="form" action="">
				<div class="form-group form-item">
			        <label style="width:60px; float:left; margin-top:5px;">收件人：</label>
			        <input type="text" id="msgTo" name="msgTo" value="" class="validate[maxSize[255],required] ac_input" url="../../user/userFetch.do?a=find" pair="true" auto-complete="true" autocomplete="off"/>
			        <input type="hidden" id="msgTo_ID" name="msgTo_ID" value="" />
			    </div>
			    <div class="form-group form-item">
					<textarea  id="msgContent" name="msgContent" class="form-control validate[required]" style="width:400px;height:100px;" placeholder="消息内容"></textarea>
				</div>
			</form>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="msg.sendMsg();">发送</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="msg.closeMsg()">取消</a>
		</div>
	</div>
</div>
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
					<a class="msgFrom" href="/home/id-339712" target="_blank"> </a>
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