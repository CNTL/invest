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
<div class="job_list">
    <div class="main">
         <div id="main">
         </div>
         <div class="clear"></div>
         <div class="more">
             <a href="#">查看更多</a>
         </div>
     </div>
    <div class="clear"></div>
</div>
<div>
<input type="hidden" id="queryType" name="queryType" value="myRecruit" />
</div>
<%-- <div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			<div class="setting-detail">
				<div class="job_list">
				    <div class="main">
				         <div id="main">
				         </div>
				         <div class="clear"></div>
				         <div class="more">
				             <a href="#">查看更多</a>
				         </div>
				     </div>
				    <div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
</div> --%>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->  
<script>
	var queryType = "myRecruit";
 </script>  
<script type="text/javascript" src="../static/js/jquery-migrate-1.1.1.js"></script>
<script type="text/javascript" src="../static/js/jQselect.js"></script>
<script src = "../user/resume/script/recruitresume.js"></script>
</body>
</html>