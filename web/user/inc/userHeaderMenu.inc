<%@page pageEncoding="UTF-8"%>
 <div class="col-md-3 container-left">
	<ul class="menu-list">
		<c:forEach var="gm" items="${iMenus}">
			<li class="<c:out value="${gm.className}"/>"><a href="<c:out value="${gm.url}"/>" ><c:out value="${gm.name}"/></a></li>
		</c:forEach>
	</ul>
</div>
