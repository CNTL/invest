<%@page pageEncoding="UTF-8"%>
<div class="setting-title clearfix">

	<c:if test="${userType==0}">
		<h3>个人设置</h3>
		<a href="../user/BasicInfo.do">返回个人中心</a>
	</c:if>
	<c:if test="${userType==1}">
		<h3>机构设置</h3>
		<a href="../org/BasicInfo.do">返回机构中心</a>
	</c:if>
</div>
<div class="setting-content clearfix">
	<div class="setting-menu">
		<ul class="clearfix">
			<c:forEach var="gm" items="${iMenus}">
				<li class="<c:out value="${gm.className}"/>"><a href="<c:out value="${gm.url}"/>" class="icons info ie6fixpic"><c:out value="${gm.name}"/></a></li>
			</c:forEach>
		</ul>
	</div>