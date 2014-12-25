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
					<a class="userHead">
						<img src="">
					</a>
	            </div>
	            <div class="msg-box">
	               	<div class="user-name">
	               		<a class="msgTo"></a>
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
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../user/msg/script/msgSend.js"></script>
</body>
</html>