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
	<div class="mini_search hidden-xs" style="width:300px;">
		<form id="mianSearchForm" name="mianSearchForm" action="<c:out value="${rootPath}"/>Search.do">
			<select id="seachType" name="t" style="height:34px;line-height:34px;font-size:14px;border:0px;border-right:1px #E0E0E0;position:absolute;top:1px;left:16px;">
				<option value="1">项目</option>
				<option value="2">影聘</option>
				<option value="3">影人</option>
			</seachType>
			<input type="text" id="seachKeyWord" name="k" class="text" value="" placeholder="请输入搜索关键字" style="left:76px;width:180px;color: #666;"/>
			<input type="submit" id="btnSeach" class="btn" value="" />
		</form>
		<script type="text/script">
			$("#mianSearchForm").validationEngine("attach",{
				autoPositionUpdate:false,//是否自动调整提示层的位置
				scroll:false,//屏幕自动滚动到第一个验证不通过的位置
				focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
				promptPosition:"bottomRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
			});
		</script>
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