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
.reclist{
	margin:0;
	padding:5px;
}
.reclist li{
	line-height:60px;
	border-left:3px solid #FFB124;
	margin:10px;
	padding:10px;
}
.reclist li h3{
	font-size:20px;
}
.reclist li span{
	font-size:14px;
	margin-left:10px;
	
}

.reclist li a {text-transform:none;text-decoration:none;color:#999999;} 
.reclist li a:hover{
	background-color:#FFB124 !important;
	color:#fff !important;
	cursor:pointer;
}
</style>
</head>
<body>
	<%@include file="../../inc/header.inc"%>
    <div class="shadow"></div>
    <div class="job_view">
        <div class="main">
        	<div>
        	<div class="avatar" style="float:left;">
                 	 <img src="../${user.head}" class="img-circle" style="width:120px;height:120px;" />
                 </div>
            <div style="float:left;margin-top:50px;margin-left:10px;">
            	<input type="hidden" id="userID" name="userID" value="${user.id}"/>
                <h1 style="font-size:24px;font-weight:bold;">${user.orgShortname}</h1>
                
            </div>
            </div>
             <div class="clear"></div>
             <br/>
            <div class="desc">
                <ul>
                    
                    <li>${user.provinceName}-${user.cityName}</li>

                </ul>
            </div>
            <div class="date">
                	 
            </div>
             <div class="title">
            		<span>公司介绍</span> 
            		
             </div>
              <div class="content" style="font-size:20px;line-height:30px;padding:10px;">
                  ${user.orgFullname},简称${user.orgShortname} , ${user.intro}
                </div>
           <div class="clear"></div>
             <div class="title">
            		<span>招聘职位</span> 
             </div>
            <div class="tip">
               该公司近两月共有 24 个职位正在招聘
            </div>
            <div style="border:1px solid #e8e8e8;">
            <ul class="reclist">
            <c:forEach var="rec" varStatus="status" items="${recList}" >
            	<li> 
            	<a href="../recruit/DetailMain.do?a=detail&id=${rec.id}">
            	 	<h3>${rec.jobName}<span>[${rec.cityName}]</span> <span class="pull-right">${rec.createtimeStr}</span></h3>
            	 	<p>${rec.salary}元/${rec.days}天/${rec.working}经验/
            	 	<c:if test="${rec.isFulltime==0}" >
            	 	兼职
            	 	</c:if>
            	 	<c:if test="${rec.isFulltime==1}" >
            	 	全职
            	 	</c:if>
            	 	/
            	 	${rec.jobAttract}</p>
            	 </a>
            	 </li>
            </c:forEach>
            </ul>
            </div>
            
        </div>
        <div class="sider">
            <div class="desc">

                <div class="intro">
                    <span>规模 ：  ${user.orgScaleName}</span><br/>
                    <span>主页 ： ${user.orgHomePage}</span><br /><br />
                    <span>工作地点 </span><br />
                    	${user.location}
                </div>
                <div class="map" style="width:250px;height:200px;" id="allmap">
                </div>
            </div>
             
        </div>
        <div class="clear"></div>
    </div>
    <div style="display:none">
    	<input type="text" id="location" name="location" value="${user.location}"/>
    </div>
	<!-- script -->
	<%@include file="../inc/script.inc"%>
	<!-- script -->
	
	<!-- footer -->
	<%@include file="../../inc/footer.inc"%>
	<!-- footer -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6eea93095ae93db2c77be9ac910ff311"></script>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../user/recruit/script/recruitDetail.js"></script>
</body>
</html>