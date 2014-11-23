<%@ page pageEncoding = "UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="ft"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,  user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>我的视频</title>
<meta name="keywords" content="">
<link rel="stylesheet" type="text/css" href="../css/userCenter.css">
<link rel="stylesheet" type="text/css" href="../../css/list.css">
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script src="../../js/pageTurning.js"></script>
<script src = "./script/userVideoList.js"></script>
</head>
<body>
<div id="todayList" class=" setting-title clearfix">
	<h3>我的视频</h3>
	<input type="button" id="newVideo" name="newVideo" value="添加视频" onclick='dourl("userVideoEdit.jsp");'/>
</div>
<hr/>
<div id="listhome1" class="Listpage">
  <div id="todayList">
	   <ul id="todayListul" class="todayList">
		  	<c:forEach items="${msg.messages }" var="message">
			  	<li onClick='dourl("recruit.do?a=detail&id=${message.id}");'>
		    		<div class="img"><img src="${message.photo}"/></div>
		    		<p id="title" style="color: rgb(0, 0, 0); font-size: 16px; word-wrap: break-word;">
		    		${message.intro}
		    		</p>
		    		<p style='font-size:12px;color:#8A8A8A;'>${message.createTime}</p>
		     	</li>
	        </c:forEach>
		</ul>
	</div>
    <div id="main_doclist" class="turnPage">
		<ul id="status" class="status">
			<li>总数[<span id="PageCount">${msg.total }</span>]</li>
			<li>
				[<span id="CurrentPage">${msg.curPage==null?0:msg.curPage }</span>/<span id="Pages">${msg.pageCount==null?0:msg.pageCount }</span>]
			</li>
		<!--<li class="separator-status">|</li>-->
			<li id="btnFirstpage" class="page-unselected" title="第一页"><<</li>
			<li id="btnLastpage" class="page-unselected" title="上一页"><</li>
			<li id="pagesArea"></li>
			<li id="btnNextpage" class="page-unselected" title="下一页">></li>
			<li id="btnFinalpage" class="page-unselected" title="最后一页">>></li>
		</ul>
	</div>
</div>
<input type="hidden" id="length" name="length" value='${param.length == null ? "0" : param.length }'/>
</body>
</html>