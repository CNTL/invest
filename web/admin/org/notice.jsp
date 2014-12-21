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
		<form id="form" name="form" method="post" target="iframe" action="Notice.do?action=update">
			<input type="hidden" id="notice_id" name="notice_id" value="<c:out value="${notice.id}"/>" />
			<div class="form-group">
				<label for="notice_title"><i style="color:red;">*</i>标题</label>
				<div class="input-icon">
					<input class="form-control placeholder-no-fix validate[required]" type="text" autocomplete="off" placeholder="请输入公告标题" id="notice_title" name="notice_title" value="<c:out value="${notice.title}"/>" />
				</div>
			</div>
			<div class="form-group">
				<label for="notice_content"><i style="color:red;">*</i>内容</label>
				<div class="input-icon">
					<textarea id="notice_content" name="notice_content" rows="6" cols="20" class="form-control placeholder-no-fix validate[required]" ><c:out value="${notice.content}"/></textarea>
				</div>
			</div>
			<div class="form-group">
				<label>显示在首页</label>
				<div class="input-icon">
					<input type="checkbox" id="notice_order_chk" name="notice_order_chk">
					<input type="hidden" id="notice_order" name="notice_order" value="<c:out value="${notice.order}"/>" />
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
			$("#notice_order_chk").on("click",function(){
				$("#notice_order").val("0");
				if($(this).is(":checked")){
					$("#notice_order").val("1");
				}
			});
			
			if($("#notice_order").val() == "1"){
				$("#notice_order_chk").attr("checked","checked");
			}else{
				$("#notice_order_chk").removeAttr("checked");
			}
		});
	</script>
</body>
</html>