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
<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
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
<%-- 
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
					<table style="width:100%;">
						<tr>
							<td valign="top" style="width:90px;">
								<label>相册头图：</label>
							</td>
							<td>
								<input type="file" name="uploadify" id="uploadify" />
								<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
								<input type="hidden" id="groupPhoto" name="groupPhoto" value="" />
								<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
							</td>
						</tr>
					</table>
			   </div>
			   <div id="coverIMG_div" style="display:none;position: absolute; z-index: 122; width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">
				</div>
			    <div class="btn" style="margin-top:120px;">
			    	<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button" onclick="photoGroup.savePhotoGroup();"/>
			    	<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button" onclick="photoGroup.closeMsg();"/>
			    </div>
			</form>
		</div>
	</div>
</div>
 --%>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../user/photo/script/photoGroupList.js"></script>
</body>
</html>