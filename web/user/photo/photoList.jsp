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
<link href="../js/plugin/boxer/jquery.fs.boxer.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="container container-ex">
	<%@include file="../inc/userHeader.inc"%>
	<div class="row text-right">
		<div class="col-md-12" >
		<input id="photogroups" name="photogroups" type="button" class="btn btn-info" style="margin:10px;" value="个人相册"/>
		<input id="uploadPhoto" name="uploadPhoto" type="button" class="btn btn-info" style="margin:10px;" value="上传照片"/>
		 
     	<input type="hidden" id="opType" name="opType" value="edit"/>
		<input id="groupID" name="groupID" type="hidden" value="<c:out value="${groupID}"/>"/>
		<input id="groupName" name="groupName" type="hidden" value="<c:out value="${groupName}"/>"/>
		
		</div>
     	
     </div>
	<div class="row container-wapper">
	<div class="col-md-12">
        <div class="container-right" id="photo-list">
        	<c:forEach var="photo" varStatus="status" items="${photos}">
        		<a href="<%=com.tl.common.WebUtil.getRoot(request) %><c:out value="${photo.photo}"/>" class="boxer" title="<c:out value="${photo.intro}"/>" data-gallery="gallery">
    				<img style="height:200px;margin-bottom:5px;" data-id="<c:out value="${photo.id}"/>" src="<%=com.tl.common.WebUtil.getRoot(request) %><c:out value="${photo.photo}"/>" alt="<c:out value="${photo.intro}"/>" />
				</a>
        	
        	</c:forEach>
        </div>
     </div>
	</div>
</div>
 
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->


<!-- footer -->
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";

</script>
<script type="text/javascript" src="../js/plugin/boxer/jquery.fs.boxer.min.js"></script>
<script type="text/javascript" src="../user/photo/script/photoList.js"></script>
 
<!-- footer -->
<%@include file="../../inc/footer.inc"%>
</body>
</html>