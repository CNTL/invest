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
<style>
 iframe{
            border:1px solid red;
            float:left;
            margin:5px;
            width:255px;
            height:249px;
        }
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<%@include file="../inc/peopleHeader.inc"%>
<div class="body-container">
    
       	
		 
	 
</div>
<input type="hidden" id="userID" name="userID" value="<c:out value="${user.id}"/>"/>
<input type="hidden" id="userName" name="userName" value="<c:out value="${user.perNickName}"/>"/>
<input id="groupID" name="groupID" type="hidden" value="<c:out value="${groupID}"/>"/>
<input type="hidden" id="opType" name="opType" value="view"/>
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
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../user/video/script/videoViewList.js"></script>
<script type="text/javascript" src="../user/user/script/peopleDetail.js"></script>
</body>
</html>