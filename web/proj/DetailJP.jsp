<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript" src="../js/layer/extend/layer.ext.js"></script>
	<script type="text/javascript" src="../proj/script/detail.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
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
		.project_view .sider .content .item{
			height:68px;
			width: 100%;
			padding: 10px 0;
			background-color: #F3F3F3;
			margin-bottom: 10px;
		}
		.project_view .sider .content .item .avatar {
			float: left;
			width: 50px;
			height: 50px;
			margin-left: 10px;
		}
		.project_view .sider .content .item .avatar img {
			width: 100%;
			height: 100%;
		}
		.project_view .sider .content .item .username {
			float: left;
			margin-left: 10px;
			height: 50px;
			line-height: 25px;
		}
		.project_view .sider .content .item .msg {
			float: right;
			display: inline-block;
			width: 50px;
			height: 20px;
			text-align: center;
			line-height: 20px;
			font-size: 12px;
			color: #fff;
			margin-right: 10px;
			margin-top: 15px;
			background: url(../image/s31.png) no-repeat;
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
                        <li><a href="javascript:void();" class="current">项目主页</a></li>
                        <li><a href="Support.do?id=<c:out value="${proj.id}"/>">竞拍者(<c:out value="${proj.countSupport}"/>)</a></li>
                    </ul>
                </div>
                <div class="desc">
					<p style="text-align:center;"><c:out escapeXml="false" value="${proj.videoUrl}"/></p>
					<c:out escapeXml="false" value="${proj.content}"/>
                </div>
            </div>
            <div class="comm" style="display:none;">
                <h2>项目进度：</h2>
				  <c:forEach var="stage" items="${stages}">
                <div class="item">
                    <div class="username" style="padding-left:53px;">
                        <img src="../static/image/temp/avatar1.png" />
                        <span><c:out value="${stage.stage.name}"/></span><c:out value="${stage.schedule.userName}"/>
                    </div>
                    <div class="time" style="width:80px;line-height:25px;">
                        <c:out value="${stage.schedule.created}"/>
                    </div>
                    <div class="desc">
                        <c:out escapeXml="false" value="${stage.schedule.content}"/>
                    </div>
                    <div class="clear"></div>
                    <!--<span class="btn">评论(0)</span>-->
                </div>
				  </c:forEach>
            </div>
        </div>
        <div class="sider">
			<div class="desc" style="padding: 5px 5px;height:48px;">
				<div class="bdsharebuttonbox" style="width: 200px;float: left;"><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_douban" data-cmd="douban" title="分享到豆瓣网"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a></div>
				<div class="bdshare-button-style0-24" style="float:right;">
					<c:if test="${favorited==0}">
					<a href="javascript:void(0);" onclick="addFavorite(<c:out value="${proj.id}"/>);" style="background-image: url(../static/image/sc.png);">收藏</a>
					</c:if>
					<c:if test="${favorited==1}">
					<a href="javascript:void(0);" >已收藏</a>
					</c:if>
				</div>

			</div>
            <div class="desc">
				<span class="status">
					<c:choose>
						<c:when test="${proj.status==0}">未开始</c:when>
						<c:when test="${proj.status==1}">竞拍中</c:when>
						<c:when test="${proj.status==2}">竞拍结束</c:when>
						<c:when test="${proj.status==3}">锁定</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</span>
                <h2>目前金额：</h2>
                <div class="money">￥<span class="moneyFormat"><c:out value="${proj.amountRaised}"/></span></div>
                <div class="tip">
                    此项目在 <span><c:out value="${proj.endDate}"/></span> 结束<br />
                    起拍金额为 ¥<span class="moneyFormat"><c:out value="${proj.amountGoal}"/></span>
                </div>
                <div class="progress">
                    <div class="now" style="width: <c:out value="${finishPer}"/>%;"></div>
                </div>
                <div class="perc"><c:out value="${finishPer}"/>%</div>
                <div class="clear"></div>
                <div class="info">
                    <ul>
                        <li><span><c:out value="${surplus}"/></span><br />剩余时间</li>
                        <li><span><c:out value="${proj.countSupport}"/></span><br />竞拍者</li>
                        <li class="last"><span><c:out value="${proj.countLove}"/></span><br />收藏</li>
                    </ul>
                </div>
            </div>
			<div class="support">
				<div class="top">
					<c:choose>
					<c:when test="${proj.status==1}">我要竞拍</c:when>
					<c:otherwise></c:otherwise>
					</c:choose>
					<div class="count">已有 <span class="red"><c:out value="${proj.countSupport}"/></span> 位竞拍者</div>
				</div>
				<c:if test="${proj.status==1}">
				<div class="content">
					<form id="jpForm" name="jpForm" action="" onSubmit="return false">
						<i class="s-money" style="background: url(../static/image/juanz.png) no-repeat;width: 15px;height: 24px;position: absolute;left: 15px;top: 85px;"></i>
						<input id="amountJP" name="amountJP" type="text" class="validate[required,custom[number]]" oldvalue="<c:out value="${proj.amountRaised}"/>" goal="<c:out value="${proj.amountGoal}"/>" value="" 
						style="margin-left:18px;width: 238px;height:50px;line-height:30px;border: 1px #000 solid;border-radius: 5px;outline: none;padding: 0 28px;color: #55acef;font-size: 24px;font-weight: bold;"/>
						<img src="../static/image/jp.png" border="0" style="cursor: pointer;margin-top:10px;" onclick="jingpai(<c:out value="${proj.id}"/>);"/>
					</form>
				</div>
				</c:if>
           </div>
			<div class="support" id="price-list" style="margin-top:15px;margin-bottom:15px;">
				<div class="top">出价记录</div>
				<div class="content">
					<c:forEach var="support" items="${supports}">
					<div class="item">
						<div class="avatar">
							<c:choose>
								<c:when test="${support.userHead==''}"><img src="../static/image/temp/avatar1.png" /></c:when>
								<c:otherwise><img style="border-radius: 50%;" src="../<c:out value="${support.userHead}"/>" /></c:otherwise>
							</c:choose>
						</div>
						<div class="username" style="width:190px;">
							<a href="javascript:void(0);"><c:out value="${support.userName}"/></a><br/>
							出价 <font color="#ff8290">￥<c:out value="${support.amount}"/></font> 元<br/>
							<div class="pgs" data-price="<c:out value="${support.amount}"/>" style="width:1%;height:5px;background:#FFA1AC;"></div>
							
						</div>
						<span class="msg" style="display:none;">发私信</span>
						
						<div class="clear"></div>
					</div>
					</c:forEach>
				</div>
				</div>
			</div>
        </div>
        <div class="clear"></div>
    
	<%@include file="../inc/footer.inc"%>
</body>
</html>