<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.kernel.web.WebUtil"%>
<html>
<head>
	<title>分类类型</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	
	<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/easyloader.js"></script>
	<script type="text/javascript">var apphost = "<%=WebUtil.getRoot(request)%>";</script>
	<script type="text/javascript" src="../js/tl/tl.form.js"></script>
</head>
<body>
	<form id="tlform" class="easyui-form" method="post">
		<input type="hidden" id="ItemID" name="ItemID" value="<c:out value="${id}"/>" />
		<div>   
			<label for="dt_name">名称:</label>   
			<input class="easyui-textbox easyui-validatebox" type="text" id="dt_name" name="dt_name" data-options="required:true" />   
		</div>
		<div>   
			<label for="dt_code">简码:</label>   
			<input class="easyui-textbox" type="text" id="dt_code" name="dt_code"/>   
		</div>
		<div>   
			<label for="dt_memo">简码:</label>   
			<input class="easyui-textbox" type="text" id="dt_memo" name="dt_memo" data-options="multiline:true" style="height:120px;" />
		</div>
	</form>
</body>
</html>