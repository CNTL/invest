<%@ include file="./include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="./inc/meta.inc"%>
    <link rel="stylesheet" type="text/css" href="css/animate.min.css" />
	<script type="text/javascript" src="static/js/idangerous.swiper.min.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/plugin/masonry/masonry.pkgd.min.js"></script>
 
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		$(function () {
			setCookie("loginCurrentUrl", window.location.href);
			setCookie("loginCurrentMenu", "1");
            var mySwiper = new Swiper('.swiper', {
                pagination: '.pagination',
                loop: true,
                wrapperClass: 'wrapper',
                slideClass: 'slide',
                grabCursor: true,
                paginationClickable: true
            });
            $('.left').on('click', function (e) {
                e.preventDefault();
                mySwiper.swipePrev();
            });
            $('.right').on('click', function (e) {
                e.preventDefault();
                mySwiper.swipeNext();
            });
            
            waterpull();
        });
		function waterpull(){
			
			 $('#container').masonry({
	                // options 设置选项
	                singleMode: true,
	                itemSelector: '.box',//class 选择器
	                columnWidth: 120,//一列的宽度 Integer
	                isAnimated: true,//使用jquery的布局变化  Boolean
	                animationOptions: {
	                    //jquery animate属性 渐变效果  Object { queue: false, duration: 500 }
	                },
	                gutterWidth: 0,//列的间隙 Integer
	                isFitWidth: false,// 适应宽度   Boolean
	                isResizableL: false,// 是否可调整大小 Boolean
	                isRTL: false,//使用从右到左的布局 Boolean
	            });
			 
	         $(".box").addClass('animated zoomIn');
		}
	</script>
	<style type="text/css">
		.container .cell{margin-right:0px;}
	</style>
</head>
<body>
	<%@include file="./inc/header.inc"%>
	
	<div class="scroll">
        <a class="left" href="#"></a>
        <a class="right" href="#"></a>
        <div class="swiper">
            <div class="wrapper">
                <div class="slide"> <img src="static/image/temp/pic1.png" /> </div>
                <div class="slide"> <img src="static/image/temp/pic1.png" /> </div>
                <div class="slide"> <img src="static/image/temp/pic1.png" /> </div>
            </div>
        </div>
        <div class="pagination"></div>
    </div>
	
	<div class="indextip">
        <div class="wrap">
            <h2>何为合众映画？</h2>
            映画，亦称电影；合众，即指合众人之力。我们是中国第一个全方位专业的电影服务型网站，年轻的电影人可以在这里发起项目，众筹融资。寻找机遇，展示自我。我们是一个为电影而生的机会平台。
        </div>
    </div>
	<div class="container" id="container">
	<!-- 項目3 -->
	<c:forEach var="proj" varStatus="status" items="${projects}" begin="0" end="2" step="1">
	 <div class="box">
         <div class="box_top"></div>
         <div class="box_main project">
             <div class="pic">
                 <a href="Project.do?id=<c:out value="${proj.id}"/>"><img src="<c:out value="${proj.imgUrl}"/>" /></a>
                 <span>项目</span>
             </div>
             <div>
                 <div class="title">
                     <a href="Project.do?id=<c:out value="${proj.id}"/>"><c:out value="${proj.name}"/></a>
                 </div>
                 <div class="desc">
                     <c:out escapeXml="false" value="${proj.summary}"/>
                 </div>
                 <div class="info">
                     <c:out value="${proj.countDay}"/>天 ￥<c:out value="${proj.amountGoal}"/>
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
                         <li><span>￥<c:out value="${proj.amountRaised}"/></span><br />已筹资</li>
                         <li class="last"><span><c:out value="${proj.surplus}"/></span><br />剩余时间</li>
                     </ul>
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
	<c:forEach var="notice" varStatus="status" items="${notices}">
	<div class="box">
			<div class="box_top"></div>
			<div class="box_main">
				<div class="notice">
					<h2>最新通知</h2>
					<div class="content">
						<span class="i"></span><c:out value="${notice.title}"/>
					</div>
					<div class="more">
						<a href="#">查看更多</a>
					</div>
				</div>
			</div>
			<div class="box_bottom"></div>
		</div>
	</c:forEach>
	
	<c:forEach var="proj" varStatus="status" items="${projects}" begin="3" end="3" step="1">
	 <div class="box">
         <div class="box_top"></div>
         <div class="box_main project">
             <div class="pic">
                 <a href="Project.do?id=<c:out value="${proj.id}"/>"><img src="<c:out value="${proj.imgUrl}"/>" /></a>
                 <span>项目</span>
             </div>
             <div>
                 <div class="title">
                     <a href="Project.do?id=<c:out value="${proj.id}"/>"><c:out value="${proj.name}"/></a>
                 </div>
                 <div class="desc">
                     <c:out escapeXml="false" value="${proj.summary}"/>
                 </div>
                 <div class="info">
                     <c:out value="${proj.countDay}"/>天 ￥<c:out value="${proj.amountGoal}"/>
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
                         <li><span>￥<c:out value="${proj.amountRaised}"/></span><br />已筹资</li>
                         <li class="last"><span><c:out value="${proj.surplus}"/></span><br />剩余时间</li>
                     </ul>
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
	
	<c:forEach var="recuit" varStatus="status" items="${userRecruit}">
	<div class="box">
			<div class="box_top"></div>
			<div class="box_main job">
				<div class="pic">
					<a href="#"><img src="static/image/temp/pic2.png" /></a> <span>影聘</span>
				</div>
				<div>
					<div class="title">
						<a href="#"><c:out value="${recuit.jobName}"/></a> <span><c:out value="${recuit.company}"/></span>
					</div>
					<div class="info">
						<ul>
							<li><c:out value="${recuit.salary}"/></li>
							<li><c:out value="${recuit.city}"/></li>
							<li><c:out value="${recuit.resumeNum}"/>个月</li>
						</ul>
					</div>
					<div class="desc">
						<span><c:out value="${recuit.jobIntro}"/></span><br />
						发布时间：<c:out value="${recuit.time}"/><br /> 已投递简历人数：2人
					</div>
				</div>
				<div class="tool">
					<a href="#" class="share">分享</a> <a href="#" class="view"></a>
				</div>
			</div>
			<div class="box_bottom"></div>
		</div>
	</c:forEach>
	
	<c:forEach var="user" varStatus="status" items="${users}">
	<div class="box">
		<div class="box_top"></div>
		<div class="box_main people">
			<div class="pic">
				<img src="static/image/temp/pic2.png" /> <span>影人</span>
			</div>
			<div class="title">
				<a href="#"><c:out value="${user.name}"/></a> <span><c:out value="${user.perJob}"/></span>
			</div>
			<div class="desc"><c:out value="${user.intro}"/></div>
			<div class="tool">
				<a href="#" class="share">分享</a> <a href="#" class="view"></a>
			</div>
		</div>
		<div class="box_bottom"></div>
	</div>
	</c:forEach>
	
	<!-- container end -->
	</div>
	
	<%@include file="../inc/footer.inc"%>
</body>
</html>