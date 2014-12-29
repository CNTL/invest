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
<div class="people_profile">
	<input type="hidden" id="opType" name="opType" value="view"/>
	<input id="groupID" name="groupID" type="hidden" value="<c:out value="${groupID}"/>"/>
		<div class="project_list">
     		<div id="imgs" class="block1">
     			<c:forEach var="photo" varStatus="status" items="${photos}">
     			<c:choose>
					<c:when test="${status.index%4==0}"><div class="box box_last" style="width:220px;"></c:when>
					<c:otherwise><div class="box" style="width:220px;"></c:otherwise>
				</c:choose>
   					<div class="people" style="border: 1px #858585 solid;">
   						<div class="pic" style="width:100%;">
   							<a><img style="cursor:pointer;" src="<%=com.tl.common.WebUtil.getRoot(request) %><c:out value="${photo.photo}"/>"></a>
   						</div>
   						<div class="title">
   							<a style="text-decoration:none;"><c:out value="${photo.photoName}"/></a>
   						</div>
   					</div>
   					<div class="clear"></div>
   				</div>
     			</c:forEach>
		</div>
     </div>
     <div class="clear"></div>
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script src="../layer/jquery.js?v=1.83.min"></script>
<script src="../layer/layer.min.js"></script>
<script type="text/javascript">
;!function(){
	layer.use('extend/layer.ext.js', function(){
	    //初始加载即调用，所以需放在ext回调里
	    layer.ext = function(){
	        layer.photosPage({
	            html:'',
	            title: '图册浏览',
	            id: 100, //相册id，可选
	            parent:'#imgs'
	        });
	    };
	});
}();
</script>
</body>
</html>