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
<link href="../user/photo/css/photo.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			<div class="setting-detail">
				<input type="button" id="createGroup" name="createGroup" class="add" value="创建图册"/>
				<div id="photos">
				</div>
			</div>
		</div>
	</div>
</div>
<div id="w" class="easyui-window" data-options="title:'新增图册',iconCls:'icon-save'" style="width:700px;height:500px;">
	<div class="easyui-layout" data-options="fit:true">
		<div id="editDiv" class="job_add">
			<form class="setting-form" id="form" name="form" action="">
				<div class="input">
			        <label for="name">相册名称：</label>
			        <input type="hidden" id="id" name="id" value="<c:out value="${id}"/>"/>
			        <input type="text" id="groupName" name="groupName" class="form-control validate[maxSize[255],required]" value=""/>
			        <br>
			    </div>
			    <div class="input">
					<label for="content">相册描述：</label>
					<textarea  id="groupIntro" name="groupIntro" class="form-control validate[maxSize[4000]]" style="width:400px;height:100px;" placeholder=""></textarea>
				</div>
			    <div class="input">
		            <label for="groupPhoto">相册头图：</label>
		            <input type="file" id="groupPhotoFile" name="groupPhotoFile" class="form-control" />
		            <input type="hidden" id="groupPhoto" name="groupPhoto" class="form-control" />
		            <input type="button" style="width:50px" id="groupPhotoBtn" value="上传">
		            <div id="groupPhotoBak" style="height:100px;width:100px;margin-left:200px;">
		            	<img id="groupPhotoImg" style="height:100px;width:100px;"/>
		            </div>
			    </div>
			    <div class="btn">
			    	<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button" onclick="photoGroup.savePhotoGroup();"/>
			    	<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button" onclick="photoGroup.closeMsg();"/>
			    </div>
			</form>
		</div>
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
<script type="text/javascript" src="../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.js" ></script>
<script type="text/javascript" src="../user/photo/script/fileUpload.js"></script>
<script type="text/javascript" src="../user/photo/script/photoGroupList.js"></script>
</body>
</html>