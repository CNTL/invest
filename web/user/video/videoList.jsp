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
				<div class="btn">
					<input type="button" id="createVideo" name="createVideo" class="add" value="添加视频"/>
				</div>
				<div id="photos">
				</div>
			</div>
		</div>
	</div>
</div>
<div id="editDiv" style="display:none;">
	<div class="job_add">
		<form class="setting-form" id="form" name="form" action="">
			<div class="input">
		        <label for="name">视频名称：</label>
		        <input type="hidden" id="id" name="id" value="<c:out value="${id}"/>"/>
		        <input type="hidden" id="groupID" name="groupID" value="<c:out value="${groupID}"/>"/>
		        <input type="text" id="videoName" name="videoName" class="form-control validate[maxSize[255],required]" value=""/>
		    </div>
		    <div class="input">
	            <label for="photo">视频图片：</label>
	            <input type="file" id="photoF" name="photoF" class="form-control" />
	            <input type="hidden" id="photo" name="photo" class="form-control" />
	            <input type="button" style="width:50px" id="photoBtn" value="上传">
	            <div id="photoBak" style="height:20px;width:20px;"></div>
		    </div>
		    <div class="input">
		        <label for="video">视频地址：</label>
		        <input type="text" id="videoUrl" name="videoUrl" class="form-control validate[maxSize[255],required]" value=""/>
		    </div>
		    <div class="input">
				<label for="intro">视频描述：</label>
				<textarea  id="intro" name="intro" class="form-control validate[maxSize[4000]]" style="width:400px;height:100px;" placeholder=""></textarea>
			</div>
		    
		    <div class="btn">
		    	<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button" onclick="myVideo.btnOK();"/>
		    	<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button" onclick="myVideo.btnCancel();"/>
		    </div>
		</form>
	</div>
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../user/video/script/videoList.js"></script>
</body>
</html>