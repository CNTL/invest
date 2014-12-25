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
<%@include file="../inc/peopleHeader.inc"%>
<div class="project_list">
	<input type="hidden" id="opType" name="opType" value="view"/>
	<input id="groupID" name="groupID" type="hidden" value="<c:out value="${groupID}"/>"/>
		<div class="project_list">
     		<div class="block1">
      			<div id="showPhoto" style="display:none;">
				<img src="../user/photo/img/close.jpg" id="close">
				<div id="showPic"><img></div>
				<div id="bgblack"></div>
				<div id="navigator">
					<span id="prev"><< 上一幅</span><span id="next">下一幅 >></span>
				</div>
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
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../user/user/script/peopleDetail.js"></script>
<script type="text/javascript" src="../user/photo/script/photoViewList.js"></script>
</body>
</html>