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
               <li><a href="../user/PeopleDetailMain.do?a=detail&id=${user.id}">个人简介</a></li>
               <li><a href="../user/PhotoGroupMain.do?a=photo&id=${user.id}" class="current">相册</a></li>
               <li><a href="../user/VideoGroupMain.do?a=video&id=${user.id}">视频</a></li>
           </ul>
	</div>
</div>

<div class="project_list">
	<input type="hidden" id="userID" name="userID" value="<c:out value="${user.id}"/>"/>
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
<script type="text/javascript" src="../user/photo/script/photoViewList.js"></script>
</body>
</html>