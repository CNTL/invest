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
	<br/>
	<div class="project_list">
        <div class="block1">
            <c:forEach var="msg" varStatus="status" items="${msg.messages}">
				<c:choose>
					<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
					<c:otherwise><div class="box"></c:otherwise>
				</c:choose>
                <div class="box_top"></div>
                <div class="box_main job">
                    <div class="pic">
                    	<c:choose>
							<c:when test="${recruitType=='edit'}">
								<a href="../user/recruit.do?a=edit&mainType=3&id=${msg.id}">
							</c:when>
							<c:otherwise>
								<a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">
							</c:otherwise>
						</c:choose>
	                        <c:choose>
								<c:when test="${msg.jobPictrue==''}"><img src="../static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="../<c:out value="${msg.jobPictrue}"/>" /></c:otherwise>
							</c:choose>
						</a>
                        <span>影聘</span>
                    </div>
                    <div>
                        <div class="title">
                            <c:choose>
								<c:when test="${recruitType=='edit'}">
									<a href="../user/recruit.do?a=edit&mainType=3&id=${msg.id}">${msg.jobName}</a>
								</c:when>
								<c:otherwise>
									<a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">${msg.jobName}</a>
								</c:otherwise>
							</c:choose>
                            <span><c:out value="${msg.company}"/></span>
                        </div>
                        <div class="info">
                            <ul>
                                <li><c:out value="${msg.salary}"/></li>
                                <li><c:out value="${msg.address}"/></li>
                                <li><c:out value="${msg.days}"/></li>
                            </ul>
                        </div>
                        <div class="desc">
                            <span><c:out value="${msg.jobAttract}"/></span><br />
                           	 发布时间：<c:out value="${msg.createtime}"/><br />
                            	已投递简历人数：<c:out value="${msg.resumeNum}"/>人
                        </div>
                    </div>
                    <div class="tool">
                        <a href="#" class="share">分享</a>
                        <a href="#" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			</c:forEach>
            <div class="clear"></div>
        </div>
        <div class="pager">
			<span class="count" title="总记录数"><c:out value="${msg.total }"/> 条</span>
			<c:choose>
				<c:when test="${msg.curPage==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
				<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&curPage=<c:out value="${msg.curPage-1}"/>" class="prev">上一页</a></c:otherwise>
			</c:choose>
			<c:forEach var="x" begin="${msg.pageBegin}" end="${msg.pageEnd}">
				<c:choose>
					<c:when test="${msg.curPage==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
					<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&curPage=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${msg.curPage==msg.pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
				<c:otherwise><a href="../recruit/ListMain.do?a=<c:out value="${queryType}"/>&more=1&recruitType=view&mainType=3&curPage=<c:out value="${msg.curPage+1}"/>" class="prev">下一页</a></c:otherwise>
			</c:choose>
		</div>
	</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->    
<script>
	var typeFlag = "<%=request.getParameter("recruitType") %>";
	var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
 </script>
<script type="text/javascript" src="../static/js/jquery-migrate-1.1.1.js"></script>
<script type="text/javascript" src="../static/js/jQselect.js"></script>
</body>
</html>