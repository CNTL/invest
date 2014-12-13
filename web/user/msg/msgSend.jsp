<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<%@include file="../inc/csslink.inc"%>
<link type="text/css" rel="stylesheet" href="../js/plugin/jquery-autocomplete/jquery.autocomplete.css"/>
<link rel="stylesheet" type="text/css" href="../user/css/userCommon.css">
<link rel="stylesheet" type="text/css" href="../user/msg/css/msgSend.css">
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			<div class="setting-detail">
				<div id="msgList">
				</div>
			</div>
		</div>
	</div>
</div>
<div>
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
				    <br>
				    <div class="form-group form-item">
				    	<label style="width:60px; float:left; margin-top:5px;">内容：</label>
						<textarea  id="msgContent" name="msgContent" class="validate[required] ac_input" style="width:300px;height:100px;" placeholder="消息内容"></textarea>
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
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript" src="../js/plugin/jquery-autocomplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../common/autocomplete.js"></script>
<script type="text/javascript" src="../user/msg/script/msgSend.js"></script>
</body>
</html>