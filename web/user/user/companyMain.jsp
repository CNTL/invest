<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../inc/meta.inc"%>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		$(function(){
			setCookie("loginCurrentUrl", window.location.href);
			setCookie("loginCurrentMenu", "2");
			
		});
	</script>
</head>
<body>
<div id="body-container" style="min-width:980px;">
	<%@include file="../../inc/header.inc"%>
	<div class="banner hidden-xs">
        <img src="../static/image/banner2.png" />
    </div>
	<div class="project_list">
        <div class="block1 proj_type_<c:out value="${orgType1.id}"/>">
            <div class="top">
                <h2><c:out value="${orgType1.name}"/></h2>
                <div class="cate">
                   
                    <a href="CompanyMainMore.do?a=queryOrg&mainType=5&perjob=<c:out value="${orgType1.id}"/>">更多 &gt;&gt;</a>
                </div>
            </div>
			<c:forEach var="person" varStatus="status" items="${orgUser1.messages}">
			<c:choose>
				<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
				<c:otherwise> <div class="box"></c:otherwise>
			</c:choose>
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
						<a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>">
							<c:choose>
								<c:when test="${person.head==''|| person.head == null}"><img src="<c:out value="${rootPath}"/>static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="<c:out value="${rootPath}"/><c:out value="${person.head}"/>" /></c:otherwise>
							</c:choose>
                      </a>
                      <span>机构</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>"><c:out value="${person.orgShortname}"/></a>
                        </div>
                        <div class="desc">
                            <c:out escapeXml="false" value="${person.intro}"/>
                        </div>
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

		 <div class="block2 proj_type_<c:out value="${orgType2.id}"/>">
		  <div class="wrap">
            <div class="top">
                <h2><c:out value="${orgType2.name}"/></h2>
                <div class="cate">
                   
                    <a href="CompanyMainMore.do?a=queryOrg&mainType=5&perjob=<c:out value="${orgType2.id}"/>">更多 &gt;&gt;</a>
                </div>
            </div>
			<c:forEach var="person" varStatus="status" items="${orgUser2.messages}">
			<c:choose>
				<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
				<c:otherwise> <div class="box"></c:otherwise>
			</c:choose>
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
						<a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>">
							<c:choose>
								<c:when test="${person.head==''|| person.head == null}"><img src="<c:out value="${rootPath}"/>static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="<c:out value="${rootPath}"/><c:out value="${person.head}"/>" /></c:otherwise>
							</c:choose>
                      </a>
 						<span>机构</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>"><c:out value="${person.orgShortname}"/></a>
                        </div>
                        <div class="desc">
                            <c:out escapeXml="false" value="${person.intro}"/>
                        </div>
                       
                    </div>
                    <div class="tool">
							<a data-url="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>" class="share">分享</a>
							<a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			</c:forEach>
			</div>
            <div class="clear"></div>
        </div>
        
         <div class="block1 proj_type_<c:out value="${orgType3.id}"/>">
            <div class="top">
                <h2><c:out value="${orgType3.name}"/></h2>
                <div class="cate">
                   
                    <a href="CompanyMainMore.do?a=queryOrg&mainType=5&perjob=<c:out value="${orgType3.id}"/>">更多 &gt;&gt;</a>
                </div>
            </div>
			<c:forEach var="person" varStatus="status" items="${orgUser3.messages}">
			<c:choose>
				<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
				<c:otherwise> <div class="box"></c:otherwise>
			</c:choose>
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
						<a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>">
							<c:choose>
								<c:when test="${person.head==''|| person.head == null}"><img src="<c:out value="${rootPath}"/>static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="<c:out value="${rootPath}"/><c:out value="${person.head}"/>" /></c:otherwise>
							</c:choose>
                      </a>
                      <span>机构</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>"><c:out value="${person.orgShortname}"/></a>
                        </div>
                        <div class="desc">
                            <c:out escapeXml="false" value="${person.intro}"/>
                        </div>
                       
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
		
		 <div class="block2 proj_type_<c:out value="${orgType4.id}"/>">
		  <div class="wrap">
            <div class="top">
                <h2><c:out value="${orgType4.name}"/></h2>
                <div class="cate">
                   
                    <a href="CompanyMainMore.do?a=queryOrg&mainType=5&perjob=<c:out value="${orgType4.id}"/>">更多 &gt;&gt;</a>
                </div>
            </div>
			<c:forEach var="person" varStatus="status" items="${orgUser4.messages}">
			<c:choose>
				<c:when test="${status.index%4==0}"><div class="box box_last"></c:when>
				<c:otherwise> <div class="box"></c:otherwise>
			</c:choose>
                <div class="box_top"></div>
                <div class="box_main project">
                    <div class="pic">
						<a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>">
							<c:choose>
								<c:when test="${person.head==''|| person.head == null}"><img src="<c:out value="${rootPath}"/>static/image/temp/pic2.png" /></c:when>
								<c:otherwise><img src="<c:out value="${rootPath}"/><c:out value="${person.head}"/>" /></c:otherwise>
							</c:choose>
                      </a>
                      <span>机构</span>
                    </div>
                    <div>
                        <div class="title">
                            <a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>"><c:out value="${person.orgShortname}"/></a>
                        </div>
                        <div class="desc">
                            <c:out escapeXml="false" value="${person.intro}"/>
                        </div>
                       
                    </div>
                    <div class="tool">
							<a data-url="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>" class="share">分享</a>
							<a href="CompanyDetail.do?a=queryDetail&mainType=5&id=<c:out value="${person.id}"/>" class="view"></a>
                    </div>
                </div>
                <div class="box_bottom"></div>
            </div>
			</c:forEach>
			</div>
            <div class="clear"></div>
        </div>
    </div>
	<%@include file="../../inc/footer.inc"%>	
	</div>
</body>
</html>