<%@page pageEncoding="UTF-8"%>
<div class="header">
	<div class="logo">
		<a href="javascript:void(0);" title="合众映画"></a>
	</div>
	<div class="nav">
		<ul>
			<c:forEach var="gm" items="${menus}">
				<li><a href="<c:out value="${gm.url}"/>" class="<c:out value="${gm.className}"/>"><c:out value="${gm.name}"/></a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="mini_search hidden-xs">
		<input type="text" id="k" name="k" class="text" value="搜索影片名称" />
		<input type="button" class="btn" value="" />
		
	</div>
	 
	<c:if test="${loginUser.id==0}" >
	<div class="mini_login">
		<a href="<c:out value="${rootPath}"/>login.jsp">登录</a>
		<span>|</span>
		<a href="<c:out value="${rootPath}"/>register.jsp">注册</a>
	</div>
	</c:if>
	<c:if test="${loginUser.id>0}">
	<div class="mini_member">
        <div class="avatar">
            <a href="#"><img style="width:60px;height:60px;" src="<c:out value="${loginUser.head}"/>" /></a>
        </div>
        <div class="username">
            <a href="#"><c:out value="${loginUser.name}"/></a>
        </div>
        <div class="menu">
            <ul>
				<c:forEach var="gm" items="${pMenus}">
				<li class="<c:out value="${gm.className}"/>"><a href="<c:out value="${gm.url}"/>" class="ie6fixpic"><c:out value="${gm.name}" escapeXml="false"/></a></li>
				</c:forEach>
            </ul>
        </div>
    </div>
	</c:if>
	<div style="display:none;">
		<input type="hidden" id="userID" name="userID" value="<c:out value="${loginUser.id}"/>"/>
	</div>
</div>