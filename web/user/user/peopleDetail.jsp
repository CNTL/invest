<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title><c:out value="${title}"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="<c:out value="${keywords}"/>" />
	<meta name="description" content="<c:out value="${description}"/>" />
    <link rel="stylesheet" type="text/css" href="../static/css/reset.css" />
    <link rel="stylesheet" type="text/css" href="../static/css/index.css" />
    <script type="text/javascript" src="../static/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/js/common.js"></script>
</head>

<body>
    <%@include file="../../inc/header.inc"%>
	
	<div class="people_globaltop">
        <div class="wrap">
            <a href="../org/BasicInfo.do?infoType=1&mainType=1" class="profile">个人设置</a>
            <div class="avavtar">
                <c:choose>
					<c:when test="${user.head=='' || user.head == null}"><img src="../static/image/temp/pic2.png" /></c:when>
					<c:otherwise><img src="../<c:out value="${user.head}"/>" /></c:otherwise>
				</c:choose>
            </div>
            <div class="info">
                <h2>我是一个<c:out value="${user.perJobName}"/>
					<span>
						<c:choose>
							<c:when test="${user.type==0}">个人</c:when>
							<c:when test="${user.type==1}">机构</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose>
					</span>
				 </h2>
                <div class="desc">
                   	 昵称：<c:out value="${user.perNickName}"/><br />
                    <span>短信息</span>
                </div>
            </div>
            <div class="clear"></div>
            <ul class="nav">
                <li><a href="#" class="current">个人简介</a></li>
                <li><a href="../user/PhotoGroupMain.do?a=photo&id=${user.id}">相册</a></li>
                <li><a href="../user/VideoGroupMain.do?a=video&id=${user.id}">视频</a></li>
            </ul>
		</div>
    </div>

    <div class="people_profile">
        <div class="content">
            <div class="tip">
            	<c:out value="${(user.intro == null || user.intro == '') ? '他很忙，什么资料也没有留下来。': user.intro}"/>
            </div>
        </div>
        <div class="clear"></div>
    </div>
	<%@include file="../../inc/footer.inc"%>
</body>
</html>