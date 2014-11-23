<%@page pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="../../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link type="text/css" rel="stylesheet" href="../../js/plugin/jquery-autocomplete/jquery.autocomplete.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/userCommon.css">
<link rel="stylesheet" type="text/css" href="css/msgSend.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-autocomplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
var rootPath = "<%=rootPath %>";
</script>
<script type="text/javascript" src="../../common/autocomplete.js"></script>
<script type="text/javascript" src="script/msgSend.js"></script>
</head>
<body>
<div style="width:150px" align="left" class="form-actions">
    <button id="btnSend" type="button" class="btn pull-right">
       	<i onclick="">发送私信</i>
    </button>
</div>
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
<div class="page tr"></div>
<div class="message-box">
	<div id="message_all" class="message-item">
		<div>
			<ul class="clearfix">
        	</ul>
	        <div class="page tr">
	        </div>
     	 </div>
    </div>
</div>
<div id="msgDiv" style="display:none">
	<ul>
		<li class="talk-list">
			<div class="talk-face">
				<a class="userHead" href="/home/id-356405" target="_blank">
					<img src="">
				</a>
            </div>
            <div class="msg-box">
               	<div class="user-name">
               		<a class="msgTo" href="/home/id-356405" target="_blank"></a>
               		<span class="gray"></span>
               	</div>
               	<div class="msg-cnt"></div>
               	<div class="talk-handle tr">
	                <a class="msgNum" href="/msgDetail.jsp"></a>
	                <a class="Js-reply">回复</a>
	                <a class="delMsg">删除</a>
              	</div>
           	</div>
        </li>
	</ul>
</div>
</body>
</html>