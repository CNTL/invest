<%@page pageEncoding="UTF-8"%>
<div class="container" style="height:90px;width:100%;min-width:960px;padding:0;margin:0;border:0px solid red;">
  <div class="row" style="border:0px solid red;">
    <div class="col-md-2" style="border:0px solid blue;min-width:250px;">
		<!---->
		<div class="hz-logo">
			<a href="javascript:void(0);" title="合众映画"></a>
		</div>
		
	</div>
  
    <div class="col-md-4" style="border:0px solid blue;margin:0;padding:0;width:504px;">
	 
		 <div class="hz-nav">
			<ul>
				<c:forEach var="gm" items="${menus}">
					<li><a style="border:0px solid red;" href="<c:out value="${gm.url}"/>" class="<c:out value="${gm.className}"/>"><c:out value="${gm.name}"/></a></li>
				</c:forEach>
			</ul>
		</div>
		 <div class="clearfix"></div>
	</div>
	
	<div class="col-md-6 search-container">
		 <form id="mianSearchForm" name="mianSearchForm" action="<c:out value="${rootPath}"/>Search.do">
		<div class="row" style="margin: 25px 0 0 0;border:0px solid blue;">
			<div class="col-md-3 head-searchcat">
				<select id="seachType" name="t" class="form-control">
				    <option value="0">全部</option>
					<option value="1">项目</option>
					<option value="2">影聘</option>
					<option value="3">影人</option>
				</select>
			</div>
			<div class="col-md-3  head-searchctxt">
				<input type="text" id="seachKeyWord" name="k" class="form-control" value="" placeholder="搜索关键字" />
			</div>
			<div class="col-md-6 head-searchbtn">
				<input type="submit" id="btnSeach" class="hzsearchbtn" value="" />
			</div>
		</div>
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
	 
	<div class="" style="border:0px solid green;width:150px;height:30px;float:right;">
	
		<div class="row" >
			 
			<div class="col-md-8" style="width:60px;padding:0;">
			
				<c:if test="${loginUser.id>0}">
				<div class="hzmini_member">
					<div class="avatar">
						<a href="<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&mainType=4&id=<c:out value="${loginUser.id}"/>"><img class="img-circle" style="width:60px;height:60px;" src="<c:out value="${loginUser.head}"/>" /></a>
					</div>
					<div class="menu">
						<ul id="menulist">
							<c:forEach var="gm" items="${pMenus}">
							<li style="padding-left:20px;" ><a href="<c:out value="${gm.url}"/>" class="ie6fixpic"><c:out value="${gm.name}" escapeXml="false"/></a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				</c:if>
				<div style="display:none;">
					<input type="hidden" id="userID" name="userID" value="<c:out value="${loginUser.id}"/>"/>
				</div>
			</div>
			
			<div class="col-md-4 head-username" >
			<c:if test="${loginUser.id>0}">
				<div class="username" >
						<a id="hzusername"><c:out value="${loginUser.name}"/></a>  
			     </div>
			</c:if>
			<c:if test="${loginUser.id==0}" >
				<div class="hzmini_login">
					<a href="<c:out value="${rootPath}"/>login.jsp">登录</a>
					
					<span>|</span>
					<a href="<c:out value="${rootPath}"/>register.jsp">注册</a>
				<!--	-->
				</div>
			</c:if>
			</div>
			
		</div>
		</div>
	</div>
</div>