<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
	<title>角色</title>
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
		<form id="form" name="form" method="post" target="iframe" action="User.do?action=role">
			<input type="hidden" id="ItemID" name="ItemID" value="<c:out value="${RoleID}"/>" />
			<div class="form-group">
				<label for="name"><i style="color:red;">*</i>角色名称</label>
				<div class="input-icon">
					<input class="form-control placeholder-no-fix validate[required]" type="text" autocomplete="off" placeholder="请输入角色名称" id="name" name="name" value="<c:out value="${RoleName}"/>" />
				</div>
			</div>
			<div class="form-group">
				<label>权限</label>
				<div class="input-icon">
					<table>
					<c:forEach var="gm" items="${menus}">
						<c:choose>
							<c:when test="${gm.pid==0}">
								<tr>
									<td><span style="font-size:14px;font-weight:800;"><c:out value="${gm.name}"/></span></td>
									<td>
										<c:forEach var="subm" items="${menus}">
											<c:choose>
												<c:when test="${subm.pid==gm.id}">
													<label style="margin-left:20px;"><input type="checkbox" id='menu-<c:out value="${subm.id}"/>' name='menu' value="<c:out value="${subm.id}"/>" /><c:out value="${subm.name}"/></label>
												</c:when>
											</c:choose>
										</c:forEach>
									</td>
								</tr>
							</c:when>
						</c:choose>
					</c:forEach>
					</table>
				</div>
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
	<script>
		$(function(){
			var ps = "<c:out value="${permissions}"/>";
			var psArray = new Array();
			psArray = ps.split(",");
			for(var i=0;i<psArray.length;i++){
				$("#menu-"+psArray[i]).attr("checked","true");
			}
		});
	</script>
</body>
</html>