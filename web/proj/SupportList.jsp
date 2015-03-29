<%@ include file="../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../inc/meta.inc"%>
	<script type="text/javascript" src="../proj/script/detail.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
	</script>
	
</head>
<body>
 
	<div class="project_view" style="width:100%;">
        <div class="main" style="width:100%;">
         
            <div class="content" style="padding-left:100px;">
               <h2><c:out value="${proj.name}"/></h2>
       
			   <c:forEach var="support" items="${supports}">
			   <div class="item" style="height:90px;">
                    <div class="avatar">
						<c:choose>
							<c:when test="${support.userHead==''}"><img src="../static/image/temp/avatar1.png" /></c:when>
							<c:otherwise><img style="width:60px;height:60px;" class="img-circle" src="../<c:out value="${support.userHead}"/>" /></c:otherwise>
						</c:choose>
                    </div>
                    <div class="username">
                        <a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${support.userId}"/>"><c:out value="${support.userName}"/></a><br/>
                        支持此项目 <span>￥<c:out value="${support.amount}"/></span> 元
                    </div>
                    <span class="msg" style="display:inline;"><a style="color:#fff;" target="_blank" href="../user/MsgDetailMa.do?msguserid=<c:out value="${support.userId}"/>">发私信</a></span>
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

        <div class="clear"></div>
	</div>	   
</body>
</html>