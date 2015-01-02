<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<!-- <link rel="stylesheet" type="text/css" href="../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.css" /> -->
<%@include file="../inc/csslink.inc"%>
<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
<link rel="stylesheet" type="text/css" href="../user/resume/css/resume.css">
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="main clearfix">
	<div class="setting wrap">
	<%@include file="../inc/userHeader.inc"%>
	<div class="setting-detail">
		<div class="job_add">
			<form class="setting-form" id="form" name="form" action="">
			    <div class="input">
			        <label for="name">简历名称：</label>
			        <input type="hidden" id="id" name="id" value="<c:out value="${id}"/>"/>
			        <input type="text" id="name" name="name" class="form-control validate[maxSize[255]]" value=""/>
			        <br>
			    </div>
				<div class="input">
		             <label>简历内容：</label>
		             <div class="text">
		                 <textarea id="contentTxt" name="contentTxt" class="form-control validate[maxSize[4000]]"></textarea>
		                 <input type="hidden" id="content" name="content" value=""/>
		             </div>
		             <div class="clear"></div>
		        </div>
			    <div class="input">
					<table style="width:100%;">
						<tr>
							<td valign="top" style="width:90px;">
								<label>简历附件：</label>
							</td>
							<td>
								<input type="file" name="uploadify" id="uploadify" />
								<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
								<input type="hidden" id="affix" name="affix" value="" />
								<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
							</td>
						</tr>
					</table>
			    </div>
			    <div id="coverIMG_div" style="display:none;position: absolute; z-index: 122; width:300px;height:50px;overflow:hidden;background:#fff;border:0px solid #C7C7C7;">
				</div>
			    <div class="btn">
		        	<input type="submit" id="btnSave" value="提交信息">
		        </div>
			</form>
		</div>
		<div>
		<input type="hidden" id="type" name="type" value="<c:out value="${hidden}"/>" />
		</div>
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
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../user/resume/script/userResume.js"></script>
</body>
</html>