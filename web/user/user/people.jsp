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
   <%@include file="../inc/script.inc"%>
    <script>
        $(function () {
            var mySwiper = new Swiper('.people_scroll', {
                loop: true,
                wrapperClass: 'wrapper',
                slideClass: 'slide'
            });
            $('.left').on('click', function (e) {
                e.preventDefault();
                mySwiper.swipePrev();
            });
            $('.right').on('click', function (e) {
                e.preventDefault();
                mySwiper.swipeNext();
            });
        });
    </script>
</head>

<body>
	<%@include file="../../inc/header.inc"%>
	<div class="banner">
	    <img src="../static/image/banner2.png" />
	</div>

    <div class="project_list">
        <div class="block1">
            <div class="top">
                <h2>导演</h2>
                <!-- <div class="cate">
                    <a href="#">制片人</a><span>|</span>
                    <a href="#">纸片主任</a><span>|</span>
                    <a href="#">统筹</a><span>|</span>
                    <a href="#">监制</a><span>|</span>
                    <a href="#">策划</a><span>|</span>
                    <a href="#">编剧</a><span></span>
                    <a href="#">更多 &gt;&gt;</a>
                </div> -->
            </div>
            <c:forEach var="person" varStatus="status" items="${persons1}">
				<c:choose>
					<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
					<c:otherwise> <div class="box"></c:otherwise>
				</c:choose>
					<div class="box_top"></div>
	                <div class="box_main people">
	                    <div class="pic">
	                    	<a href="Project.do?id=<c:out value="${person.id}"/>">
								<c:choose>
									<c:when test="${person.head==''}"><img src="../static/image/temp/pic2.png" /></c:when>
									<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
								</c:choose>
	                      	</a>
	                    </div>
	                    <div class="title">
	                        <a href="#"><c:out value="${person.name}"/></a>
	                        <span>导演</span>
	                    </div>
	                    <div class="desc">
	                        <c:out value="${person.intro}"/>
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
        
        
        <div class="block2">
            <div class="wrap">
	            <div class="top">
	                <h2>演员</h2>
	                <!-- <div class="cate">
	                    <a href="#">制片人</a><span>|</span>
	                    <a href="#">纸片主任</a><span>|</span>
	                    <a href="#">统筹</a><span>|</span>
	                    <a href="#">监制</a><span>|</span>
	                    <a href="#">策划</a><span>|</span>
	                    <a href="#">编剧</a><span></span>
	                    <a href="#">更多 &gt;&gt;</a>
	                </div> -->
	            </div>
	            <c:forEach var="person" varStatus="status" items="${persons2}">
					<c:choose>
						<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
						<c:otherwise> <div class="box"></c:otherwise>
					</c:choose>
						<div class="box_top"></div>
		                <div class="box_main people">
		                    <div class="pic">
		                    	<a href="Project.do?id=<c:out value="${person.id}"/>">
									<c:choose>
										<c:when test="${person.head==''}"><img src="../static/image/temp/pic2.png" /></c:when>
										<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
									</c:choose>
		                      	</a>
		                    </div>
		                    <div class="title">
		                        <a href="#"><c:out value="${person.name}"/></a>
		                        <span>演员</span>
		                    </div>
		                    <div class="desc">
		                        <c:out value="${person.intro}"/>
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
        </div>
        
        
        <div class="block3">
            <div class="top">
                <h2>摄影</h2>
                <!-- <div class="cate">
                    <a href="#">制片人</a><span>|</span>
                    <a href="#">纸片主任</a><span>|</span>
                    <a href="#">统筹</a><span>|</span>
                    <a href="#">监制</a><span>|</span>
                    <a href="#">策划</a><span>|</span>
                    <a href="#">编剧</a><span></span>
                    <a href="#">更多 &gt;&gt;</a>
                </div> -->
            </div>
            <c:forEach var="person" varStatus="status" items="${persons3}">
				<c:choose>
					<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
					<c:otherwise> <div class="box"></c:otherwise>
				</c:choose>
					<div class="box_top"></div>
	                <div class="box_main people">
	                    <div class="pic">
	                    	<a href="../user/user.do?a=detail&id=<c:out value="${person.id}"/>">
								<c:choose>
									<c:when test="${person.head==''}"><img src="../static/image/temp/pic2.png" /></c:when>
									<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
								</c:choose>
	                      	</a>
	                    </div>
	                    <div class="title">
	                        <a href="#"><c:out value="${person.name}"/></a>
	                        <span>摄影</span>
	                    </div>
	                    <div class="desc">
	                        <c:out value="${person.intro}"/>
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
        
        
        <div class="block4">
        	<div class="wrap">
	            <div class="top">
	                <h2>后期</h2>
	                <!-- <div class="cate">
	                    <a href="#">制片人</a><span>|</span>
	                    <a href="#">纸片主任</a><span>|</span>
	                    <a href="#">统筹</a><span>|</span>
	                    <a href="#">监制</a><span>|</span>
	                    <a href="#">策划</a><span>|</span>
	                    <a href="#">编剧</a><span></span>
	                    <a href="#">更多 &gt;&gt;</a>
	                </div> -->
	            </div>
	            <c:forEach var="person" varStatus="status" items="${persons4}">
					<c:choose>
						<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
						<c:otherwise> <div class="box"></c:otherwise>
					</c:choose>
						<div class="box_top"></div>
		                <div class="box_main people">
		                    <div class="pic">
		                    	<a href="Project.do?id=<c:out value="${person.id}"/>">
									<c:choose>
										<c:when test="${person.head==''}"><img src="../static/image/temp/pic2.png" /></c:when>
										<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
									</c:choose>
		                      	</a>
		                    </div>
		                    <div class="title">
		                        <a href="#"><c:out value="${person.name}"/></a>
		                        <span>后期</span>
		                    </div>
		                    <div class="desc">
		                        <c:out value="${person.intro}"/>
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
        </div>
	</div>
	<%@include file="../../inc/footer.inc"%>
</body>
</html>
