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
	<div class="mini_search">
		<input type="text" id="k" name="k" class="text" value="搜索影片名称" />
		<input type="button" class="btn" value="" />
	</div>
	<c:if test="${userID==0}" >
	<div class="mini_login">
		<a href="login.html">登录</a>
		<span>|</span>
		<a href="register.html">注册</a>
	</div>
	</c:if>
	<c:if test="${userID>0}">
	<div class="mini_member">
        <div class="avatar">
            <a href="#"><img src="<c:out value="${userAvatar}"/>" /></a>
        </div>
        <div class="username">
            <a href="#"><c:out value="${userName}"/></a>
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
</div>