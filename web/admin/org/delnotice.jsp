<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
	<title>删除公告</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />    
 	<link type="text/css" rel="stylesheet" href="../../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
	<link type="text/css" rel="stylesheet" href="../../js/bootstrap/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="../../css/components.css" />
	<link type="text/css" rel="stylesheet" href="../css/form.css" />
	
	<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../../js/plugin/backstretch/jquery.backstretch.min.js"></script>
	<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="../js/form.js"></script>
</head>
<body>
	<div class="wrap">
		<iframe id="iframe" name="iframe" style="display:none;"></iframe>
		<form id="form" name="form" method="post" target="iframe" action="Notice.do?action=delete">
			<input type="hidden" id="ItemID" name="ItemID" value="<c:out value="${ItemID}"/>" />
			<input type="hidden" id="Item" name="Item" value="<c:out value="${Item}"/>" />
			<div style="height:120px;text-align:center;">
				确定要删除所选中的记录么?
			</div>
			<div class="form-actions">
				<button id="btnCancel" type="button" onclick="window.parent.tldialog.close();" class="btn blue pull-right">
					<i>关闭</i>
				</button>
				<button id="btnSave" type="submit" class="btn blue pull-right">
					<i>保存</i>
				</button>
			</div>
		</form>
	</div>
</body>
</html>