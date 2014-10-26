<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.kernel.web.WebUtil"%>
<html>
<head>
	<title>分类</title>
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
			<input type="hidden" id="ItemPID" name="ItemPID" value="<c:out value="${pid}"/>" />
			<input type="hidden" id="TypeID" name="TypeID" value="<c:out value="${typeid}"/>" />
			<div>   
				<label for="dic_name">名称:</label>   
				<input class="easyui-textbox easyui-validatebox" type="text" id="dic_name" name="dic_name" value="<c:out value="${dic_name}"/>" data-options="required:true" />   
			</div>
			<div>   
				<label for="dic_code">简码:</label>   
				<input class="easyui-textbox" type="text" id="dic_code" name="dic_code" value="<c:out value="${dic_code}"/>"/>   
			</div>
			<div>   
				<label for="dic_code">VALUE:</label>   
				<input class="easyui-textbox" type="text" id="dic_value" name="dic_value" value="<c:out value="${dic_value}"/>"/>   
			</div>
			<div>   
				<label for="dic_code">TEXT:</label>   
				<input class="easyui-textbox" type="text" id="dic_text" name="dic_text" value="<c:out value="${dic_text}"/>"/>   
			</div>
			<div>   
				<label for="dic_memo">备注:</label>   
				<input class="easyui-textbox" type="text" id="dic_memo" name="dic_memo" value="<c:out value="${dic_memo}"/>" data-options="multiline:true" style="height:120px;" />
			</div>
		</form>
	</div>
</body>
</html>