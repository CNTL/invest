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
    <script type="text/javascript" src="../static/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="../static/js/common.js"></script>
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
        <img src="../static/image/banner3.png" />
    </div>
    <div class="project_list">
	   	<c:forEach var="data" varStatus="status1" items="${datas}">
	   	<div class="block1">
            <a class="left" href="#"></a>
            <a class="right" href="#"></a>
            <div class="top">
                <h2><c:out value="${data.perName}"/></h2>
                <div class="cate">
                    <a href="../user/PeopleMoreMain.do?type=<c:out value="${data.perType}"/>">更多 &gt;&gt;</a>
                </div>
            </div>
            <div class="people_scroll">
                <div class="wrapper">
                    <div class="slide">
                    	<c:forEach var="person" varStatus="status" items="${data.persons}">
                    	<c:choose>
							<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
							<c:otherwise> <div class="box"></c:otherwise>
						</c:choose>
                            <div class="box_top"></div>
                            <div class="box_main project">
                                <div class="pic">
			                    	<a href="../user/PeopleDetailMain.do?a=detail&id=<c:out value="${person.id}"/>">
										<c:choose>
											<c:when test="${person.head=='' || person.head == null}"><img src="../static/image/temp/pic2.png" /></c:when>
											<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
										</c:choose>
			                      	</a>
			                      	<span>影人</span>
			                    </div>
                                <div>
                                	<div class="title">
				                        <a href="#"><c:out value="${person.name}"/></a>
				                        <%-- <span><c:out value="${data.perName}"/></span> --%>
				                    </div>
				                    <div class="desc">
				                        <c:out value="${person.intro}"/>
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
                    </div>
                </div>
            </div>
        </div>
        <div class="shadow"></div>
        </c:forEach>
       </div>

	<%@include file="../../inc/footer.inc"%>
</body>
</html>
