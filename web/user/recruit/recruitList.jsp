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
<div class="banner">
    <img src="../static/image/banner1.png" />
</div>
<div class="job_list">
    <div class="sider">
    <c:forEach var="type" varStatus="status" items="${types}">
    	<div class="cate">
	    	<h2><a href="#"><c:out value="${type.name}"/></a></h2>
		    <ul>
		    <c:forEach var="subType" varStatus="status" items="${type.subDics}">
		    	<li><a href="#"><c:out value="${subType.name}"/></a></li>
		    </c:forEach>
		    </ul>
		    <div class="clear"></div>
		    <div class="expand">
                <div class="blank"></div>
                <div class="item">
                	<h3><a href="#"><c:out value="${type.name}"/></a></h3>
                	<ul>
                	<c:forEach var="subType" varStatus="status" items="${type.subDics}">
				    	<li><a href="#"><c:out value="${subType.name}"/></a></li>
				    </c:forEach>
				    </ul>
                	<div class="clear"></div>
                </div>
        	</div>
	    </div>
    </c:forEach>
    </div>
    <div class="main">
         <a class="add" style="cursor:pointer" onclick="jobList.addRecruit();">+ 发布职位</a>
		 <div class="top">
             <a href="#">公司</a>
             <a href="#">简历管理</a>
         </div>
         <div class="search">
             <form action="" method="POST">
                 <div class="type">
                     <select name="type" id="type">
                         <option value="0">职位</option>
                         <option value="1">公司</option>
                     </select>
                 </div>
                 <div class="key">
                     <input type="text" name="key" value="请输入你要搜索的职位，如“制片人”" />
                 </div>
                 <div class="submit">
                     <input type="submit" name="" value="搜索" />
                 </div>
             </form>
             <div class="clear"></div>
         </div>
         <div class="sort">
         	<c:choose>
				<c:when test="${queryType eq 'queryHot'}">
					<a href="#" id="queryNew" onclick="jobList.change(this);">最新职位</a>
	             	<a class="current" href="#" id="queryHot" onclick="jobList.change(this);">热门职位</a>
				</c:when>
				<c:otherwise>
					<a class="current" href="#" id="queryNew" onclick="jobList.change(this);">最新职位</a>
	             	<a href="#" id="queryHot" onclick="jobList.change(this);">热门职位</a>
				</c:otherwise>
			</c:choose>
             
         </div>
         <!-- <div id="main">
         </div> -->
         <c:forEach var="msg" varStatus="status" items="${msg.messages}">
				<c:choose>
					<c:when test="${status.index%3==0}"><div class="box box_last"></c:when>
					<c:otherwise><div class="box"></c:otherwise>
				</c:choose>
                <div class="box_top"></div>
                <div class="box_main job">
                    <div class="pic">
                    	<c:choose>
							<c:when test="${recruitType=='edit'}">
								<a href="../user/recruit.do?a=edit&mainType=3&id=${msg.id}">${msg.jobName}
							</c:when>
							<c:otherwise>
								<a href="../user/recruit.do?a=detail&mainType=3&id=${msg.id}">${msg.jobName}
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
    <div class="clear"></div>
    <c:choose>
		<c:when test="${more eq '1'}">
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
		</c:when>
		<c:otherwise>
			<div class="more">
	         	<a href="../recruit/ListMain.do?a=queryNew&more=1&recruitType=view&mainType=3">查看更多</a>
	         </div>
		</c:otherwise>
	</c:choose>
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
<script src = "../user/recruit/script/recruitList.js"></script>
</body>
</html>