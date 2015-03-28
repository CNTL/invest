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
 
<style>
 .thumbnail{
    width:288px;
    height:310px;
    float:left;
    border:1px solid #e3e3e3;
    margin:5px 0 5px 13px;
}
.thumbnail iframe{
    width:275px;
    height:268px;
}
 .thumbnail embed {
    width:275px;
    height:268px;
}
 .thumbnail p{
   text-align:center;
}
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
 
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
 
<%@include file="../inc/script.inc"%>

<script type="text/javascript" src="../user/video/script/videoViewGroupList.js"></script>
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
 
<%@include file="../../inc/footer.inc"%>
</body>
</html>