<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		$(function(){
			setCookie("loginCurrentUrl", window.location.href);
			setCookie("loginCurrentMenu", "2");
			var type = <c:out value="${type}"/>;
			if(type > 0){
				$(".proj_type_").hide();
				$(".pager").show();
			}else{
				$(".pager").hide();
			}
		});
	</script>
</head>
<body>
<div id="body-container" style="min-width:980px;">
	<%@include file="../inc/header.inc"%>
	<div class="banner hidden-xs">
        <img src="../static/image/banner2.png" />
    </div>
	<div class="project_list">
        <div class="block1 proj_type_<c:out value="${projType1}"/>">
            <div class="top">
                <h2>微电影项目</h2>
                <div class="cate">
                    <!--<a href="#">剧情片</a><span>|</span>
                    <a href="#">记录片</a><span>|</span>
                    <a href="#">动画片</a><span>|</span>
                    <a href="#">实验片</a><span></span>-->
                    <a href="List.do?type=<c:out value="${projType1}"/>">更多 &gt;&gt;</a>
                </div>
            </div>
			<c:forEach var="proj" varStatus="status" items="${projs1}">
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

        <div class="block2 proj_type_<c:out value="${projType2}"/>">
            <div class="wrap">
                <div class="top">
                    <h2>电影长片项目</h2>
                    <div class="cate">
                        <!--<a href="#">剧情片</a><span>|</span>
                        <a href="#">记录片</a><span>|</span>
                        <a href="#">动画片</a><span>|</span>
                        <a href="#">老电影修复</a><span></span>-->
                        <a href="List.do?type=<c:out value="${projType2}"/>">更多 &gt;&gt;</a>
                    </div>
                </div>
                <c:forEach var="proj" varStatus="status" items="${projs2}">
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
							<span class="three"><c:out value="${proj.typeNickName}"/></span>
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
        </div>

        <div class="block3 proj_type_<c:out value="${projType3}"/>">
            <div class="top">
                <h2>电影相关项目</h2>
                <div class="cate">
                    <!--<a href="#">电影书籍</a><span>|</span>
                    <a href="#">影人对话</a><span>|</span>
                    <a href="#">试片室</a><span>|</span>
                    <a href="#">电影科技</a><span></span>-->
                    <a href="List.do?type=<c:out value="${projType3}"/>">更多 &gt;&gt;</a>
                </div>
            </div>
            <c:forEach var="proj" varStatus="status" items="${projs3}">
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
                        <span class="four"><c:out value="${proj.typeNickName}"/></span>
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
		
		<div class="clear"></div>
		
		<div class="pager" style="display: none;">
			<span class="count" title="总记录数"><c:out value="${projCount}"/> 条</span>
			<c:choose>
				<c:when test="${page==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
				<c:otherwise><a href="List.do?type=<c:out value="${type}"/>&page=<c:out value="${page-1}"/>" class="prev">上一页</a></c:otherwise>
			</c:choose>
			<c:forEach var="x" begin="${pageBegin}" end="${pageEnd}">
				<c:choose>
					<c:when test="${page==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
					<c:otherwise><a href="List.do?type=<c:out value="${type}"/>&page=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${page==pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
				<c:otherwise><a href="List.do?type=<c:out value="${type}"/>&page=<c:out value="${page+1}"/>" class="prev">下一页</a></c:otherwise>
			</c:choose>
		</div>
		
    </div>
	<%@include file="../inc/footer.inc"%>	
	</div>
</body>
</html>