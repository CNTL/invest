<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
	<title>分类管理</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<%@include file="../admin/inc/csslink.inc"%>
	<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/easyloader.js"></script>
	<%=request.getAttribute("JsTags")%>
	<script type="text/javascript">
		var apphost = "<c:out value="${apphost}"/>";
	</script>
</head>
<body>
	<%@include file="../admin/inc/header.inc"%>
	<%@include file="../admin/inc/sysnav.inc"%>
	<div id="main" role="main">
		<div id="content" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height:auto;padding:0px;">
				<%@include file="../admin/inc/ribbon.inc"%>
			</div>
			<div data-options="region:'west',split:true,title:'分类管理'" style="width:248px;padding:10px;">
				<ul id="dic-tree">
				</ul>
				<div id="tree-menu" class="easyui-menu" style="width:120px;">
					<div class="tree-menu-type" data-options="iconCls:'icon-add'">新增分类类型</div>
					<div class="tree-menu-type" class="menu-sep"></div>
					<div class="tree-menu-cat" data-options="iconCls:'icon-add'">新增分类</div>
					<div data-options="iconCls:'icon-edit'">修改</div>
					<div data-options="iconCls:'icon-remove'">删除</div>
				</div>
			</div>
			<!--<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>-->
			<!--<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>-->
			<div data-options="region:'center'">
				<div id="content-layout" data-options="fit:true">
					<div data-options="region:'center',title:'子分类'">
						<table id="datagrid"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../admin/inc/script.inc"%>
	<%@include file="../admin/inc/footer.inc"%>
</body>
</html>