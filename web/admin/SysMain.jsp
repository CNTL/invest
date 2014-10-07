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
	<script type="text/javascript" src="../js/jquery-easyui/easyloader.js"></script>
	<script type="text/javascript" src="../admin/script/listpage/tl.listpage.js"></script>
	<%=request.getAttribute("JsTags")%>
	<script type="text/javascript">
		var apphost = "<c:out value="${apphost}"/>";
	</script>
</head>
<body class="">
	<%@include file="inc/header.inc"%>
    <%@include file="inc/sysnav.inc"%>
	<div id="main" role="main">
		<div id="content" data-options="fit:true">
			<div data-options="region:'north',border:false" style="height:auto;padding:0px;">
				<%@include file="../admin/inc/ribbon.inc"%>
			</div>
			<div region="center" border="false">	
				<table id="datagrid"></table>		
				<div id="toolbar">
					<div id="main_search" style="padding:5px;">
						<div id="searchBtnArea">
							<div class="btn-group searchListGroup">
								<div class="btn" id="searchList">
									<i class="icon-search"></i>
									<span class="more" id="toggleSearchAdvList"><i class="caret"></i></span>
								</div>
							</div>
						</div>
						<div id="divQueryCust" style="margin-right:40px;">
						</div>
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