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
<div id="w" class="easyui-window" data-options="title:'新增图册',iconCls:'icon-save'" style="width:500px;height:300px;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:10px;background:#fff;border:1px solid #ccc;">
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
		            <label for="groupHead">相册头图：</label>
		            <input type="file" id="groupHeadF" name="groupHeadF" class="form-control" />
		            <input type="hidden" id="groupHead" name="groupHead" class="form-control" />
		            <input type="button" style="width:50px" id="groupHeadBtn" value="上传">
		            <div id="groupHeadBak" style="height:100px;width:100px;"></div>
			    </div>
			    <!-- 
			    <div class="btn">
		        	<input type="submit" id="btnSave" value="提交信息">
		        </div>
		         -->
			</form>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="photoGroup.savePhotoGroup();">保存</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="photoGroup.closeMsg()">取消</a>
		</div>
	</div>
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript" src="../user/photo/script/photoGroupList.js"></script>
</body>
</html>