<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<script type="text/javascript" src="../proj/script/detail.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var curstage = "<c:out value="${curstage}"/>";
	</script>
	<style type="text/css">
		.project_view .main .content .progress li.s1 {
			background: url(../static/image/p1.png) no-repeat;
		}

		.project_view .main .content .progress li.s2 {
			margin-left: 70px;
			background: url(../static/image/p2.png) no-repeat;
		}

		.project_view .main .content .progress li.s3 {
			margin-left: 70px;
			background: url(../static/image/p3.png) no-repeat;
		}
		
		.project_view .main .content .progress li.s1_1 {
			background: url(../static/image/p1_1.png) no-repeat;
		}

		.project_view .main .content .progress li.s2_1 {
			margin-left: 70px;
			background: url(../static/image/p2_1.png) no-repeat;
		}

		.project_view .main .content .progress li.s3_1 {
			margin-left: 70px;
			background: url(../static/image/p3_1.png) no-repeat;
		}
	</style>
</head>
<body>
	<%@include file="../inc/header.inc"%>
	<div class="shadow">
    </div>
	<div class="project_view">
        <div class="main">
            <div class="content">
                <h2><c:out value="${proj.name}"/></h2>
                <div class="info">
                    <span class="f">发起人</span> <c:out value="${user.name}"/> <span><c:out value="${province.name}"/><c:out value="${city.name}"/><c:out value="${county.name}"/></span>
                </div>
                <div class="progress">
                    <ul>
                        <li class="s1"></li>
                        <li class="s2"></li>
                        <li class="s3"></li>
                    </ul>
                </div>
				<div class="nav">
                    <ul>
                        <li><a href="Project.do?id=<c:out value="${proj.id}"/>">项目主页</a></li>
                        <li><a href="javascript:void();" class="current">支持者(<c:out value="${proj.countSupport}"/>)</a></li>
                    </ul>
               </div>
			   <c:forEach var="support" items="${supports}">
			   <div class="item" style="height:90px;">
                    <div class="avatar">
						<c:choose>
							<c:when test="${support.userHead==''}"><img src="../static/image/temp/avatar1.png" /></c:when>
							<c:otherwise><img style="width:60px;height:60px;" class="img-circle" src="../<c:out value="${support.userHead}"/>" /></c:otherwise>
						</c:choose>
                    </div>
                    <div class="username">
                        <a href="javascript:void(0);"><c:out value="${support.userName}"/></a><br/>
                        支持此项目 <span>￥<c:out value="${support.amount}"/></span> 元
                    </div>
                    <span class="msg" style="display:none;">发私信</span>
                    <div class="clear"></div>
                </div>
				</c:forEach>
			   <div class="clear"></div>
                <div class="pager">
						<span class="count" title="总记录数"><c:out value="${supportCount}"/> 条</span>
						<c:choose>
							<c:when test="${page==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
							<c:otherwise><a href="Support.do?id=<c:out value="${proj.id}"/>&page=<c:out value="${page-1}"/>" class="prev">上一页</a></c:otherwise>
						</c:choose>
						<c:forEach var="x" begin="1" end="${pageCount}">
							<c:choose>
								<c:when test="${page==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
								<c:otherwise><a href="Support.do?id=<c:out value="${proj.id}"/>&page=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${page==pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
							<c:otherwise><a href="Support.do?id=<c:out value="${proj.id}"/>&page=<c:out value="${page+1}"/>" class="prev">下一页</a></c:otherwise>
						</c:choose>
                </div>
            </div>
        </div>
		 <div class="sider">
            <div class="desc">
				<span class="status">
					<c:choose>
						<c:when test="${proj.status==0}">未开始</c:when>
						<c:when test="${proj.status==1}">众筹中</c:when>
						<c:when test="${proj.status==2}">众筹结束</c:when>
						<c:when test="${proj.status==3}">锁定</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</span>
                <h2>目前累计金额：</h2>
                <div class="money">￥<span class="moneyFormat"><c:out value="${proj.amountRaised}"/></span></div>
                <div class="tip">
                    此项目必须在 <span><c:out value="${proj.endDate}"/></span> 前得到
                    ¥<span class="moneyFormat"><c:out value="${proj.amountGoal}"/></span> 的支持才可成功！
                </div>
                <div class="progress">
                    <div class="now" style="width: <c:out value="${finishPer}"/>%;"></div>
                </div>
                <div class="perc"><c:out value="${finishPer}"/>%</div>
                <div class="clear"></div>
                <div class="info">
                    <ul>
                        <li><span><c:out value="${surplus}"/></span><br />剩余时间</li>
                        <li><span><c:out value="${proj.countSupport}"/></span><br />支持者</li>
                        <li class="last"><span><c:out value="${proj.countLove}"/></span><br />收藏</li>
                    </ul>
                </div>
            </div>
			<c:forEach var="mode" items="${modes}">
            <div class="support">
                <div class="top">
					<c:choose>
						<c:when test="${mode.price<=0}">无私支持</c:when>
						<c:otherwise>支持￥<label class="moneyFormat"><c:out value="${mode.price}"/></label></c:otherwise>
					</c:choose>
                    <div class="count">已有 <span class="red"><c:out value="${mode.countSupport}"/></span> 位支持者/限额 <span class="red"><c:out value="${mode.countGoal}"/></span> 位</div>
                </div>
                <div class="content">
                    <c:out value="${mode.returnContent}"/>
                    <span>
						<c:choose>
							<c:when test="${mode.price<=0}">无私支持</c:when>
							<c:otherwise>支持￥<label class="moneyFormat"><c:out value="${mode.price}"/></label></c:otherwise>
						</c:choose>
					  </span>
                </div>
            </div>
			</c:forEach>
        </div>
        <div class="clear"></div>
	</div>	   
	<%@include file="../inc/footer.inc"%>
</body>
</html>