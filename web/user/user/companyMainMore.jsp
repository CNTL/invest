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
		#rows dl{
			margin-bottom:5px;
		}
		#rows dt{
			text-align:left;
			width:90px;
			font-size:14px;
		}
		#rows dd{
			margin-left:0;
		}
	 
		 #rows dd a {
		 	font-size:14px;
            color: #555;
            margin-right: 5px;
            cursor: pointer;
            padding: 5px 5px;
            text-decoration:none;
            
        }

        #rows dd a:hover {
            color: white;
            background: #38C6E4;
            text-decoration:none;
        }
        .aselect{
        	color: white;
            background: #38C6E4;
            text-decoration:none;
        }

	</style>
   
    <script type="text/javascript" src="../static/js/idangerous.swiper.min.js"></script>
    <script type="text/javascript" src="../static/js/common.js"></script>
    <script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
       
</head>

<body>
<div id="body-container" style="min-width:980px;">
	<%@include file="../../inc/header.inc"%>
	<div class="banner">
        <img src="../static/image/banner3.png" />
    </div>
	
    <div class="project_list">
  
        <div class="block1">
        	<div class="top">
                <h2><c:out value="${perName}"/></h2>
            </div> 
              <div class="container-fluid">
				<div class="row text-left" id="rows">
					
				</div>
			</div>
            <c:forEach var="person" varStatus="status" items="${msg.messages}">
				<c:choose>
					<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
					<c:otherwise> <div class="box"></c:otherwise>
				</c:choose>
					<div class="box_top"></div>
	                <div class="box_main project">
	                    <div class="pic">
	                    	<a href="CompanyDetail.do?a=queryDetail&mainType=4&id=<c:out value="${person.id}"/>">
								<c:choose>
									<c:when test="${person.head=='' || person.head == null}"><img src="../static/image/temp/pic2.png" /></c:when>
									<c:otherwise><img src="../<c:out value="${person.head}"/>" /></c:otherwise>
								</c:choose>
	                      	</a>
	                      	<span>机构</span>
	                    </div>
	                    <div class="title">
                            <a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>"><c:out value="${person.orgShortname}"/></a>
                        </div>
	                    <div class="desc">
	                        <c:out value="${person.intro}"/>
	                    </div>
	                    <div class="tool">
	                        <a data-url="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>" class="share">分享</a>
	                        <a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>" class="view"></a>
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
				<c:otherwise><a href="CompanyMainMore.do?a=queryOrg&mainType=5&perjob=<c:out value="${perjob}"/>&curPage=<c:out value="${msg.curPage-1}"/>" class="prev">上一页</a></c:otherwise>
			</c:choose>
			<c:forEach var="x" begin="${msg.pageBegin}" end="${msg.pageEnd}">
				<c:choose>
					<c:when test="${msg.curPage==x}"><a href="javascript:void();" class="current"><c:out value="${x}"/></a></c:when>
					<c:otherwise><a href="CompanyMainMore.do?a=queryOrg&mainType=5&perjob=<c:out value="${perjob}"/>&curPage=<c:out value="${x}"/>"><c:out value="${x}"/></a></c:otherwise>
				</c:choose>
			</c:forEach>
			<c:choose>
				<c:when test="${msg.curPage==msg.pageCount}"><a href="javascript:void();" class="prev">下一页</a></c:when>
				<c:otherwise><a href="CompanyMainMore.do?a=queryOrg&mainType=5&perjob=<c:out value="${perjob}"/>&curPage=<c:out value="${msg.curPage+1}"/>" class="prev">下一页</a></c:otherwise>
			</c:choose>
		</div>
	</div>
	<%@include file="../../inc/footer.inc"%>
	</div>
	
	 
</body>
</html>
