<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.kernel.web.WebUtil"%>
<html>
<head>
	<title>删除</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	
	<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/easyloader.js"></script>
	<script type="text/javascript">var apphost = "<%=WebUtil.getRoot(request)%>";</script>
	<script type="text/javascript" src="../js/tl/tl.form.js"></script>
</head>
<body>
	<div id="main" style="width:100%;padding-top:20px;padding-left:20px;">
		<form id="tlform" class="easyui-form" method="post">
			<input type="hidden" id="ItemID" name="ItemID" value="<c:out value="${id}"/>" />
			<input type="hidden" id="IsType" name="IsType" value="<c:out value="${istype}"/>" />
			<input type="hidden" id="TypeID" name="TypeID" value="<c:out value="${typeid}"/>" />
			<div id="msg" style="margin-top:20px;width:100%;text-align:center;">您确定要执行 删除 操作?</div>
		</form>
	</div>
	<script>
		$(function () {
			var id = $("#ItemID").val();
			if(id == "" || id<=0){
				window.parent.tldialog.close();
			}
		});
	</script>
</body>
</html>