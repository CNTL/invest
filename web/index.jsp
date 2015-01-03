<%@ include file="./include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="./inc/meta.inc"%>
    <link rel="stylesheet" type="text/css" href="css/animate.min.css" />
	<script type="text/javascript" src="static/js/idangerous.swiper.min.js"></script>
	<script type="text/javascript" src="js/utils.js"></script>
	<script type="text/javascript" src="js/share.js"></script>
	<script type="text/javascript" src="js/plugin/masonry/masonry.pkgd.min.js"></script>
 
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var pageIndex = 1;
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
            
            $("#btn-more").click(function(){
            	getItem();
            	
            });
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
		
		function getItem(){
			pageIndex = pageIndex+1;
			$.getJSON("MainList.do?action=getIndexItems&pageIndex="+pageIndex, function(json){
				  var sb = [];
				  if(json==null){ return ;}
				  
				  if(json.projectItems!=null && json.projectItems.length>0){
					  sb.push(formatProject(json.projectItems));
				  }
				  if(json.userRecruitItems!=null&&json.userRecruitItems.length>0){
					  sb.push(formatUserRecruit(json.userRecruitItems));
				  }
				  if(json.userItems!=null&&json.userItems.length>0){
					 sb.push(formatUser(json.userItems));
				  }
				  var items = $(sb.join(""));
				  $('#container').append(items).masonry('appended',items);
				  shareInfo();
				 
			});

			
			
		}
		function formatProject(items){
			var sb =[];
			$.each(items,function(i,n){
				var status = "未知";
				if(n.status==0){
					status = "未开始";
				}
				if(n.status==1){
					status = "众筹中";	
				}
				if(n.status==2){
					status = "众筹结束";
				}
				if(n.status==3){
					status = "锁定";
				}
				
				sb.push("<div class=\"box\">");
				sb.push("    <div class=\"box_top\"></div>");
				sb.push("    <div class=\"box_main project\">");
				sb.push("        <div class=\"pic\">");
				sb.push("            <a href=\"project/Project.do?id="+n.id+"\"><img src=\""+n.imgUrl+"\" /></a>");
				sb.push("            <span>项目</span>");
				sb.push("        </div>");
				sb.push("        <div>");
				sb.push("            <div class=\"title\">");
				sb.push("                <a href=\"project/Project.do?id="+n.id+"\">"+n.name+"</a>");
				sb.push("            </div>");
				sb.push("            <div class=\"desc\">");
				sb.push(n.summary);
				sb.push("            </div>");
				sb.push("            <div class=\"info\">"+n.countDay+"天 ￥"+n.amountGoal);
				sb.push("                <span>"+status+"</span>");
				sb.push("            </div>");
				sb.push("            <div class=\"progress\">");
				sb.push("                <div class=\"now\" style=\"width:"+n.finishPer+"%;\"></div>");
				sb.push("            </div>");
				sb.push("            <div class=\"status\">");
				sb.push("                <ul>");
				sb.push("                    <li><span>"+n.finishPer+"%</span><br />已达</li>");
				sb.push("                    <li><span>￥"+n.amountRaised+"</span><br />已筹资</li>");
				sb.push("                    <li class=\"last\"><span>"+n.surplus+"</span><br />剩余时间</li>");
				sb.push("                </ul>");
				sb.push("            </div>");
				sb.push("        </div>");
				sb.push("        <div class=\"tool\">");
				sb.push("            <a href=\"#\" class=\"share\">分享</a>");
				sb.push("            <a href=\"#\" class=\"view\"></a>");
				sb.push("        </div>");
				sb.push("    </div>");
				sb.push("    <div class=\"box_bottom\"></div>");
				sb.push("</div>");
			});
			
			return sb.join('');
		}
		
		function formatUserRecruit(items){
			var sb =[];
			$.each(items,function(i,n){
				sb.push("<div class=\"box\">");
				sb.push("    <div class=\"box_top\"></div>");
				sb.push("    <div class=\"box_main job\">");
				sb.push("        <div class=\"pic\">");
				sb.push("            <a href=\"recruit/DetailMain.do?a=detail&id="+n.id+"\"><img src=\""+n.jobPictrue+"\" /></a> <span>影聘</span>");
				sb.push("        </div>");
				sb.push("        <div>");
				sb.push("            <div class=\"title\">");
				sb.push("                <a href=\"recruit/DetailMain.do?a=detail&id="+n.id+"\">"+n.jobName+"</a> <span>"+n.company+"</span>");
				sb.push("            </div>");
				sb.push("            <div class=\"info\">");
				sb.push("                <ul>");
				sb.push("                    <li>"+n.salary+"</li>");
				sb.push("                    <li>"+n.city+"</li>");
				sb.push("                    <li>"+n.days+"天</li>");
				sb.push("                </ul>");
				sb.push("            </div>");
				sb.push("            <div class=\"desc\">");
				sb.push("                <span>"+n.jobIntro+"</span><br />");
				sb.push("                发布时间："+n.createtime+"<br /> 已投递简历人数："+n.resumeNum+"人");
				sb.push("            </div>");
				sb.push("        </div>");
				sb.push("        <div class=\"tool\">");
				sb.push("            <a href=\"#\" class=\"share\">分享</a> <a href=\"#\" class=\"view\"></a>");
				sb.push("        </div>");
				sb.push("    </div>");
				sb.push("    <div class=\"box_bottom\"></div>");
				sb.push("</div>");
			});
			
			return sb.join('');
		}
		
		function formatUser(items){
			var sb =[];
			$.each(items,function(i,n){
				sb.push("<div class=\"box\">");
				sb.push("    <div class=\"box_top\"></div>");
				sb.push("    <div class=\"box_main people\">");
				sb.push("        <div class=\"pic\">");
				sb.push("            <a href=\"user/PeopleDetailMain.do?a=detail&id="+n.id+"\"><img src=\""+n.head+"\" /> </a><span>影人</span>");
				sb.push("        </div>");
				sb.push("        <div class=\"title\">");
				sb.push("            <a href=\"user/PeopleDetailMain.do?a=detail&id="+n.id+"\">"+n.name+"</a> <span>"+n.perJobName+"</span>");
				sb.push("        </div>");
				sb.push("        <div class=\"desc\">"+n.intro+"</div>");
				sb.push("        <div class=\"tool\">");
				sb.push("            <a href=\"#\" class=\"share\">分享</a> <a href=\"#\" class=\"view\"></a>");
				sb.push("        </div>");
				sb.push("    </div>");
				sb.push("    <div class=\"box_bottom\"></div>");
				sb.push("</div>");
			});
			
			return sb.join('');
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
                <div class="slide"> <img src="static/image/temp/0.jpg" /> </div>
                <div class="slide"> <img src="static/image/temp/1.jpg" /> </div>
                <div class="slide"> <img src="static/image/temp/2.jpg" /> </div>
                <div class="slide"> <img src="static/image/temp/3.jpg" /> </div>
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
                 <a href="project/Project.do?id=<c:out value="${proj.id}"/>"><img src="<c:out value="${proj.imgUrl}"/>" /></a>
                 <span>项目</span>
             </div>
             <div>
                 <div class="title">
                     <a href="project/Project.do?id=<c:out value="${proj.id}"/>"><c:out value="${proj.name}"/></a>
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
						
						<h3><span class="i"></span><c:out value="${notice.title}"/></h3>
						<p style="border-bottom:1px dashed #FCB988;height:4px;width:98%;">&nbsp;</p>
						<p><c:out value="${notice.content}"/></p>
					</div>
					<div class="more">
						<a href="notice/List.do">查看更多</a>
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
                 <a href="project/Project.do?id=<c:out value="${proj.id}"/>"><img src="<c:out value="${proj.imgUrl}"/>" /></a>
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
					<a href="recruit/DetailMain.do?a=detail&id=<c:out value="${recuit.id}"/>"><img src="<c:out value="${recuit.jobPictrue}"/>" /></a> <span>影聘</span>
				</div>
				<div>
					<div class="title">
						<a href="recruit/DetailMain.do?a=detail&id=<c:out value="${recuit.id}"/>"><c:out value="${recuit.jobName}"/></a> <span><c:out value="${recuit.company}"/></span>
					</div>
					<div class="info">
						<ul>
							<li><c:out value="${recuit.salary}"/></li>
							<li><c:out value="${recuit.city}"/></li>
							<li><c:out value="${recuit.days}"/>天</li>
						</ul>
					</div>
					<div class="desc">
						<span><c:out value="${recuit.jobIntro}"/></span><br />
						发布时间：<c:out value="${recuit.createtime}"/><br /> 已投递简历人数：<c:out value="${recuit.resumeNum}"/>人
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
				<a href="user/PeopleDetailMain.do?a=detail&id=<c:out value="${user.id}"/>"><img src="<c:out value="${user.head}"/>" /> </a><span>影人</span>
			</div>
			<div class="title">
				<a href="user/PeopleDetailMain.do?a=detail&id=<c:out value="${user.id}"/>"><c:out value="${user.name}"/></a> <span><c:out value="${user.perJobName}"/></span>
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
	<div class="more text-center"><button id="btn-more" class="btn btn-info btn-sm">点击更多</button></div>
	<p>&nbsp;</p>
	
	<%@include file="../inc/footer.inc"%>
</body>
</html>