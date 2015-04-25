<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
    <script type="text/javascript" src="<c:out value="${rootPath}"/>js/utils.js"></script>
	<script type="text/javascript" src="<c:out value="${rootPath}"/>js/share.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
	</script>
</head>
<body>
<div id="body-container" style="min-width:980px;">
	<%@include file="../inc/header.inc"%>
	<div class="shadow">
    </div>
	<div class="project_list">
		<c:if test="${searchType==1 || searchType==0}">
        <div class="block1 search_type_<c:out value="${searchType}"/>">
            <div class="top">
                <h2>项目</h2>
                <div class="cate">
					当前检索关键字：<span style="font-size:16px;color:red;"><c:out value="${searchKeyWord}"/></span>
                </div>
            </div>
			<c:forEach var="proj" varStatus="status" items="${projs}">
			<c:choose>
				<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
				<c:otherwise> <div class="box"></c:otherwise>
			</c:choose>
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
						<a href="<c:out value="${rootPath}"/>project/Project.do?id=<c:out value="${proj.id}"/>">
							<c:choose>
								<c:when test="${proj.imgUrl==''}"><img src="<c:out value="${rootPath}"/>static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="<c:out value="${rootPath}"/><c:out value="${proj.imgUrl}"/>" /></c:otherwise>
							</c:choose>
                      </a>
                        <span class="two"><c:out value="${proj.typeNickName}"/></span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="<c:out value="${rootPath}"/>project/Project.do?id=<c:out value="${proj.id}"/>"><c:out value="${proj.name}"/></a>
                        </div>
                        <div class="desc">
                            <c:out escapeXml="false" value="${proj.summary}"/>
                        </div>
                        <div class="info">
                            目标：<c:out value="${proj.countDay}"/>天 ￥<c:out value="${proj.amountGoal}"/>
							<span>
								<c:choose>
									<c:when test="${proj.status==0}">未开始</c:when>
									<c:when test="${proj.status==1}">众筹中</c:when>
									<c:when test="${proj.status==2}">众筹结束</c:when>
									<c:when test="${proj.status==3}">锁定</c:when>
									<c:otherwise>未知</c:otherwise>
								</c:choose>
							</span>
                        </div>
                        <div class="progress">
                            <div class="now" style="width: <c:out value="${proj.finishPer}"/>%;"></div>
                        </div>
                        <div class="status">
                            <ul>
                                <li><span><c:out value="${proj.finishPer}"/>%</span><br />已达</li>
                                <li><span><c:out value="${proj.amountRaised}"/></span><br />已筹资</li>
                                <li class="last"><span><c:out value="${proj.surplus}"/></span><br />剩余时间</li>
                            </ul>
                        </div>
                    </div>
                    <div class="tool">
							<a data-url="<c:out value="${rootPath}"/>project/Project.do?id=<c:out value="${proj.id}"/>" class="share">分享</a>
							<a href="<c:out value="${rootPath}"/>project/Project.do?id=<c:out value="${proj.id}"/>" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			</c:forEach>
            <div class="clear"></div>
        </div>
		</c:if>
		<c:if test="${searchType==2 || searchType==0}">
        <div class="block1 search_type_<c:out value="${searchType}"/>">
            <div class="top">
                <h2>影聘</h2>
                <div class="cate">
					当前检索关键字：<span style="font-size:16px;color:red;"><c:out value="${searchKeyWord}"/></span>
                </div>
            </div>
			<c:forEach var="recruit" varStatus="status" items="${recruits}">
			<c:choose>
				<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
				<c:otherwise> <div class="box"></c:otherwise>
			</c:choose>
				<div class="box_top"></div>
				<div class="box_main job">
					<div class="pic">
						<a href="<c:out value="${rootPath}"/>user/recruit.do?a=detail&amp;mainType=3&amp;id=<c:out value="${recruit.id}"/>">
							<img src="<c:out value="${rootPath}"/><c:out value="${recruit.jobPictrue}"/>">
						</a>
						<span>影聘</span>
					</div>
					<div>
						<div class="title">
							<a href="<c:out value="${rootPath}"/>user/recruit.do?a=detail&amp;mainType=3&amp;id=<c:out value="${recruit.id}"/>"><c:out value="${recruit.jobName}"/></a>
							<span><c:out value="${recruit.company}"/></span>
						</div>
						<div class="info">
							<ul>
								 
								 <c:if test="${recruit.jobType==0}">
				                 	<li>总价${recruit.salary}元</li>
				                    <li>${recruit.provinceName}</li>
				                    <li>${recruit.days}天</li>
				                </c:if>
				                
				                 <c:if test="${recruit.jobType==1}">
				                 	<li>月薪${recruit.salary}元</li>
				                    <li>${recruit.provinceName}</li>
				                    <li>${recruit.days}个月</li>
				                </c:if>
							</ul>
						</div>
						<div class="desc">
							<span>职位诱惑：<c:out value="${recruit.jobAttract}"/></span><br>
							发布时间：<c:out value="${recruit.createtime}"/><br>
							已投递简历人数：<c:out value="${recruit.resumeNum}"/>人
						</div>
					</div>
					<div class="tool">
						<a data-url="<c:out value="${rootPath}"/>user/recruit.do?a=detail&amp;mainType=3&amp;id=<c:out value="${recruit.id}"/>" class="share">分享</a>
						<a href="<c:out value="${rootPath}"/>user/recruit.do?a=detail&amp;mainType=3&amp;id=<c:out value="${recruit.id}"/>" class="view"></a>
					</div>
				</div>
				<div class="box_bottom"></div>
			</div>
			</c:forEach>
			<div class="clear"></div>
        </div>
		</c:if>
		<c:if test="${searchType==3 || searchType==0}">
        <div class="block1 search_type_<c:out value="${searchType}"/>">
            <div class="top">
                <h2>影人</h2>
                <div class="cate">
					当前检索关键字：<span style="font-size:16px;color:red;"><c:out value="${searchKeyWord}"/></span>
                </div>
            </div>
			<c:forEach var="user" varStatus="status" items="${users}">
			<c:choose>
				<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
				<c:otherwise> <div class="box"></c:otherwise>
			</c:choose>
				<div class="box_top"></div>
				<div class="box_main project">
					<div class="pic">
						<a href="<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&amp;mainType=4&amp;id=<c:out value="${user.id}"/>">
							<img src="<c:out value="${rootPath}"/><c:out value="${user.head}"/>">
						</a>
						<span>影人</span>
					</div>
					<div>
						<div class="title">
							<a href="<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&amp;mainType=4&amp;id=<c:out value="${user.id}"/>">
							<c:out value="${user.name}"/>
							</a>
							<span><c:out value="${user.typeName}"/></span>
						</div>
						<div class="desc">
							<c:out value="${user.intro}"/>
						</div>
					</div>
					<div class="tool">
						<a data-url="<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&amp;mainType=4&amp;id=<c:out value="${user.id}"/>" class="share">分享</a>
						<a href="<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&amp;mainType=4&amp;id=<c:out value="${user.id}"/>" class="view"></a>
					</div>
				</div>
				<div class="box_bottom"></div>
			</div>
			</c:forEach>
			<div class="clear"></div>
        </div>
		</c:if>
		<div class="clear"></div>
		
		<div class="pager">
			<span class="count" title="总记录数"><c:out value="${projCount}"/> 条</span>
			<c:choose>
				<c:when test="${page==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
				<c:otherwise><a href="Search.do?t=<c:out value="${searchType}"/>&k=<c:out value="${searchKeyWord}"/>&page=<c:out value="${page-1}"/>" class="prev">上一页</a></c:otherwise>
			</c:choose>
			<c:forEach var="x" begin="${pageBegin}" end="${pageEnd}">
				<c:choose>
					<c:when test="${page==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
					<c:otherwise><a href="Search.do?t=<c:out value="${searchType}"/>&k=<c:out value="${searchKeyWord}"/>&page=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${page==pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
				<c:otherwise><a href="Search.do?t=<c:out value="${searchType}"/>&k=<c:out value="${searchKeyWord}"/>&page=<c:out value="${page+1}"/>" class="prev">下一页</a></c:otherwise>
			</c:choose>
		</div>
    </div>
	<%@include file="../inc/footer.inc"%>	
	</div>
</body>
</html>