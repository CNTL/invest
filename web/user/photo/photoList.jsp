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
<link href="../js/plugin/boxer/jquery.fs.boxer.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="container container-ex">
	<%@include file="../inc/userHeader.inc"%>
	<div class="row text-right">
		<div class="col-md-9" >
		
		
		</div>
		<div class="col-md-1">
			<input id="photogroups" name="photogroups" type="button" class="btn btn-info" style="" value="返回图册"/>
	     	<input type="hidden" id="opType" name="opType" value="edit"/>
			<input id="groupID" name="groupID" type="hidden" value="<c:out value="${groupID}"/>"/>
			<input id="groupName" name="groupName" type="hidden" value="<c:out value="${groupName}"/>"/>
		</div>
		<div class="col-md-2 ">
		 	<input type="file" name="uploadify" id="uploadify" />
			<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
			<input type="hidden" id="headImg" name="headImg" value="" />
			<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
		</div>
     	
     </div>
	<div class="row container-wapper">
	<div class="col-md-12">
        <div class="container-right" id="photo-list">
        	<c:forEach var="photo" varStatus="status" items="${photos}">
        	<div style="height:240px;float:left;margin:5px;padding:3px;border:1px solid #E8E8E8;">
        		<a href="<%=com.tl.common.WebUtil.getRoot(request) %><c:out value="${photo.photo}"/>" class="boxer" title="<c:out value="${photo.intro}"/>" data-gallery="gallery">
    				<img style="height:200px;margin-bottom:5px;" data-id="<c:out value="${photo.id}"/>" src="<%=com.tl.common.WebUtil.getRoot(request) %><c:out value="${photo.photo}"/>" alt="<c:out value="${photo.intro}"/>" />
				</a>
				<p class="text-center"><c:out value="${photo.intro}"/></p>
			</div>
        	
        	</c:forEach>
        </div>
     </div>
	</div>
</div>
 
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->


<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";

</script>
<script type="text/javascript" src="../js/plugin/boxer/jquery.fs.boxer.min.js"></script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../user/photo/script/photoList.js"></script>
 
<!-- footer -->

</body>
</html>