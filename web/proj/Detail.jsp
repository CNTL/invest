<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript" src="../js/layer/extend/layer.ext.js"></script>
	<script type="text/javascript" src="../proj/script/detail.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
	</script>
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
                        <li><a href="javascript:void();" class="current">项目主页</a></li>
                        <li><a href="Support.do?id=<c:out value="${proj.id}"/>">支持者(<c:out value="${proj.countSupport}"/>)</a></li>
                    </ul>
                </div>
                <div class="desc">
					<c:out escapeXml="false" value="${proj.content}"/>
                </div>
            </div>
            <div class="comm">
                <h2>项目进度：</h2>
                <div class="item">
                    <div class="username">
                        <img src="../static/image/temp/avatar1.png" />
                        <span>发起人</span> 我是独家小蜜蜂
                    </div>
                    <div class="time">
                        12小时前
                    </div>
                    <div class="desc">
                        欢迎大家转发我的项目分享到微博以及微信朋友圈或者直接支持我的专辑哦，希望我的这个从小的心愿可以实现。
                    </div>
                    <div class="clear"></div>
                    <span class="btn">评论(0)</span>
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
                        <!--<li class="last"><span>211</span><br />喜欢</li>-->
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
					<div id="returncontent_<c:out value="${mode.id}"/>" class="returncontent" modeid="<c:out value="${mode.id}"/>" imgs="<c:out value="${mode.imgURL}"/>"><c:out value="${mode.returnContent}"/></div>
					<span class="supportBtn" modeid="<c:out value="${mode.id}"/>" userid="<c:out value="${loginUser.id}"/>">
						<c:choose>
							<c:when test="${mode.price<=0}">无私支持</c:when>
							<c:otherwise>支持￥<label class="moneyFormat"><c:out value="${mode.price}"/></label></c:otherwise>
						</c:choose>
				  </span></div>
            </div>
			</c:forEach>
        </div>
        <div class="clear"></div>
    </div>
	<%@include file="../inc/footer.inc"%>
</body>
</html>