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
<link rel="stylesheet" type="text/css" href="../user/resume/css/resume.css">
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="people_globaltop">
    <div class="wrap">
        <ul class="nav">
            <c:forEach var="gm" items="${iMenus}">
        		<li><a href="<c:out value="${gm.url}"/>" class="<c:out value="${gm.className}"/>"><c:out value="${gm.name}"/></a></li>
			</c:forEach>
        </ul>
    </div>
</div>
<div class="job_add">
	<form class="setting-form" id="form" name="form" action="">
	    <div class="input">
	        <label for="name">简历名称：</label>
	        <input type="hidden" id="id" name="id" value="<c:out value="${id}"/>"/>
	        <input type="text" id="name" name="name" class="form-control validate[maxSize[255]]" value=""/>
	        <br>
	    </div>
	    <div class="input">
			<label for="content">简历内容：</label>
			<textarea  id="content" name="content" class="form-control validate[maxSize[4000]]" style="width:400px;height:100px;" placeholder=""></textarea>
		</div>
	    <div class="input">
            <label for="affix">简历附件：</label>
            <input type="file" id="affixF" name="affixF" class="form-control" />
            <input type="hidden" id="affix" name="affix" class="form-control" />
            <input type="button" style="width:50px" id="affixBtn" value="上传">
            <!-- <iframe name='hidden_frame' id="hidden_frame" style='display: none'></iframe> -->
	    </div>
	    <div class="btn">
        	<input type="submit" id="btnSave" value="提交信息">
        </div>
	</form>
</div>
			
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript" src="../js/plugin/AjaxFileUploaderV2.1/ajaxfileupload.js" ></script>
<script type="text/javascript" src="../user/resume/script/userResume.js"></script>
</body>
</html>