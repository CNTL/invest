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
    <div class="shadow"></div>
    <div class="job_view">
        <div class="main">
            <div class="title">
            	<input type="hidden" id="recruitID" name="recruitID" value="${recruit.id}"/>
                <span>${recruit.jobName}</span>
                <a style="cursor:pointer" class="favorite" onclick="resume.collect();"></a>
            </div>
            <div class="desc">
                <ul>
                    <li>${recruit.salary}</li>
                    <li>${user.city}</li>
                    <li>${recruit.days}</li>
                </ul>
            </div>
            <div class="date">
                发布时间：${recruit.time}
                <a href="#" class="report">举报该职位</a>
            </div>
            <div class="block">
                <h2>职位描述</h2>
                <div class="content">
                    ${recruit.content}
                </div>
            </div>
            <div class="tip">
                你还没有简历呢，你可以完善在线简历，也可上传附件简历直接投递
            </div>
            <a style="cursor:pointer" class="submit" id="btnSend">投递简历</a>
        </div>
        <div class="sider">
            <div class="desc">
                <div class="pic">
                <!-- ../../static/image/temp/pic4.png -->
                    <img src="${user.head}" />
                </div>
                <div class="title">
			    	${user.orgFullname}
                </div>
                <div class="clear"></div>
                <div class="intro">
                    <span>规模 ：  ${user.orgScale}</span><br/>
                    <span>主页 ： ${user.orgHomePage}</span><br /><br />
                    <span>工作地点 ：</span><br />
                    	${user.location}
                </div>
                <div class="map" style="width:250px;height:200px;" id="allmap">
                </div>
            </div>
            <div class="relate">
                <h2>相似职位</h2>
                <c:forEach var="msg" varStatus="status" items="${simiRecruits}">
                <div class="item">
                    <div class="pic">
                        <a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">
	                        <c:choose>
								<c:when test="${msg.jobPictrue==''}"><img src="../static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="../<c:out value="${msg.jobPictrue}"/>" /></c:otherwise>
							</c:choose>
						</a>
                    </div>
                    <div class="title">
                        <a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">${msg.jobName}</a>
                        <span><c:out value="${msg.company}"/></span>
                    </div>
                    <div class="company">
                        <c:out value="${msg.company}"/>
                    </div>
                    <div class="info">
                        <ul>
                            <li><c:out value="${msg.salary}"/></li>
                            <li><c:out value="${msg.address}"/></li>
                            <li><c:out value="${msg.days}"/></li>
                        </ul>
                    </div>
                    <div class="clear"></div>
                </div>
                </c:forEach>
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