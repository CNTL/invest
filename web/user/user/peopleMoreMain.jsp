<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
 	<%@include file="../../inc/meta.inc"%>
	<%@include file="../inc/csslink.inc"%>
	<style>
	  	.nav li{width:80px;
			floag:left;
			list-style:none;
			display:inline;
			font:16px "微软雅黑","宋体",Arial;
			color:#333;
		}
		.nav a {text-transform:none;text-decoration:none;} 
		.nav a:hover{
			background:#019875;
			color:white;
			cursor:pointer;
		}
		.current{
			background:#019875;
			color:white;
			cursor:pointer;
		} 
		 #taglist{
		 	padding:50px 200px;
		 	height:50px;
		 }
		 #taglist dd a {
		 	font-size:14px;
            color: #555;
            margin-right: 5px;
            cursor: pointer;
            padding: 5px 5px;
        }

        #taglist dd a:hover {
            color: white;
            background: #38C6E4;
        }
        
        .ddselect{
        	color: white;
            background: #38C6E4;
        }

	</style>
   
    <script type="text/javascript" src="../static/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="../static/js/common.js"></script>
    <script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
    <script type="text/javascript" src="../user/user/script/peopleMore.js"></script>
       
</head>

<body>
<div id="body-container" style="min-width:980px;">
	<%@include file="../../inc/header.inc"%>
	<div class="banner">
        <img src="../static/image/banner3.png" />
    </div>
	
    <div class="project_list">
    <div class="container-fluid">
		<div class="row" id="rows">
			<dl class="dl-horizontal" id="taglist" data-type="41">
                <dt>搜索条件：</dt>
                <dd><a data-group="all" href="#">全部</a>|<a data-group="sex" href="#">男</a>|<a data-group="sex" href="#">女</a>|<a data-group="age" href="#">20岁以下</a>|<a data-group="age" href="#">20岁-30岁</a>|<a data-group="age" href="#">30岁以上</a>|</dd>
            </dl>
            <dl class="dl-horizontal" id="taglist2" data-type="42">
                <dt>搜索条件：</dt>
                <dd><a data-group="all" href="#">全部</a>|</dd>
            </dl>
             <dl class="dl-horizontal" id="taglist2" data-type="43">
                <dt>搜索条件：</dt>
                <dd><a data-group="all" href="#">全部</a>|</dd>
            </dl>
            <dl class="dl-horizontal" id="taglist2" data-type="44">
                <dt>搜索条件：</dt>
                <dd><a data-group="all" href="#">全部</a>|</dd>
            </dl>
		</div>
	</div>
        <div class="block1">
        	<%-- <div class="top">
                <h2><c:out value="${perName}"/></h2>
            </div> --%>
        	<div>
				<dt>
					<ul class="nav">
						<c:forEach var="name" varStatus="status" items="${typeNames}">
							<li>
							<c:choose>
								<c:when test="${name==typeName}">
								<a class="current" href="../user/PeopleMoreMain.do?a=queryMorePersons&mainType=4&type=<c:out value="${perType}"/>&typeName=<c:out value="${name}"/>"><c:out value="${name}"/></a>
								</c:when>
								<c:otherwise>
								<a href="../user/PeopleMoreMain.do?a=queryMorePersons&mainType=4&type=<c:out value="${perType}"/>&typeName=<c:out value="${name}"/>"><c:out value="${name}"/></a>
								</c:otherwise>
							</c:choose>
							</li>
							|
						</c:forEach>
					</ul>
				</dt>
			</div>
			<br>
            <c:forEach var="person" varStatus="status" items="${msg.messages}">
				<c:choose>
					<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
					<c:otherwise> <div class="box"></c:otherwise>
				</c:choose>
					<div class="box_top"></div>
	                <div class="box_main people">
	                    <div class="pic">
	                    	<a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${person.id}"/>">
								<c:choose>
									<c:when test="${person.head=='' || person.head == null}"><img src="../static/image/temp/pic2.png" /></c:when>
									<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
								</c:choose>
	                      	</a>
	                      	<span>影人</span>
	                    </div>
	                    <div class="title">
	                        <a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${person.id}"/>">
	                        <c:out value="${person.name}"/>
	                        </a>
	                        <span><c:out value="${perName}"/></span>
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
        <div class="pager">
			<span class="count" title="总记录数"><c:out value="${msg.total }"/> 条</span>
			<c:choose>
				<c:when test="${msg.curPage==1}"><a href="javascript:void();" class="prev">上一页</a></c:when>
				<c:otherwise><a href="../user/PeopleMoreMain.do?a=queryPersons&mainType=4&type=<c:out value="${type}"/>&curPage=<c:out value="${msg.curPage-1}"/>" class="prev">上一页</a></c:otherwise>
			</c:choose>
			<c:forEach var="x" begin="${msg.pageBegin}" end="${msg.pageEnd}">
				<c:choose>
					<c:when test="${msg.curPage==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
					<c:otherwise><a href="../user/PeopleMoreMain.do?a=queryPersons&mainType=4&type=<c:out value="${type}"/>&curPage=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${msg.curPage==msg.pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
				<c:otherwise><a href="../user/PeopleMoreMain.do?a=queryPersons&mainType=4&type=<c:out value="${type}"/>&curPage=<c:out value="${msg.curPage+1}"/>" class="prev">下一页</a></c:otherwise>
			</c:choose>
		</div>
	</div>
	<%@include file="../../inc/footer.inc"%>
	</div>
</body>
</html>
