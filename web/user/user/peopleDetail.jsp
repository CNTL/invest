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
</head>

<body>
    <%@include file="../../inc/header.inc"%>
	<%@include file="../inc/peopleHeader.inc"%>
	
    <div class="body-container" style="height:300px;">
    
        <h2>
        <c:out value="${(user.intro == null || user.intro == '') ? '他很忙，什么资料也没有留下来。': user.intro}"/>
        </h2>

    </div>
	<%@include file="../../inc/footer.inc"%>
	
	<%@include file="../inc/script.inc"%>
    <script type="text/javascript" src="../static/js/common.js"></script>
    <script type="text/javascript" src="../js/layer/layer.min.js"></script>
    <script type="text/javascript" src="../js/utils.js"></script>
    <script type="text/javascript" src="../user/user/script/peopleDetail.js"></script>
</body>
</html>