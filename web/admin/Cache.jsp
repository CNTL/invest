<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
	<title>管理系统--电影众筹网</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<%@include file="inc/csslink.inc"%>
	<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript" src="../js/layer/extend/layer.ext.js"></script>
	<%=request.getAttribute("JsTags")%>
	<script type="text/javascript">
		var apphost = "<c:out value="${apphost}"/>";
	</script>
</head>
<body class="">
	<%@include file="inc/header.inc"%>
    <%@include file="inc/sysnav.inc"%>
	<div id="main" role="main">
		<%@include file="../admin/inc/ribbon.inc"%>
		<div id="content">
			<div id="easyui-layout" data-options="fit:true">
				<div region="center" border="false">	
					<div id="caches" style="width:600px;margin:20px auto;line-height:30px;">
						
					</div>
					<div style="width:600px;margin:20px auto;line-height:30px;">
						<input type="button" id="btnRefresh" class="btn btn-success" value="刷新" />
						<input type="button" id="btnRefreshALL" class="btn btn-success" value="刷新全部" />
					</div>
				</div>
			</div>
        </div>
	</div>
    <%@include file="inc/script.inc"%>
    <%@include file="inc/footer.inc"%>
 	<script src="script/main.js"></script>
</body>

</html>