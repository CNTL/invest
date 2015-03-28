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
<style>
dt{
	font-size:18px;
	margin:10px;
	font-weight:normal;
}
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<%@include file="../inc/peopleHeader.inc"%>
<div class="body-container">
	
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->


<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../js/plugin/boxer/jquery.fs.boxer.min.js"></script>
<script type="text/javascript" src="../user/photo/script/photoViewGroupList.js"></script>
<script type="text/javascript">
    $(function(){
    	$(".people_globaltop").css({
    		"height":"320px",
    		"background":"#F8F8F8 url(../static/image/head_bg_001.jpg)",
    		"background-size":"cover"
    		});
     
    	$(".wrap").css({
    		"height":"320px" 
    		});
    });
    </script>
<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
</body>
</html>