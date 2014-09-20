<!-- Left panel : Navigation area -->
<!-- Note: This width of the aside area can be adjusted through LESS variables -->
<aside id="left-panel">
	<!-- User info -->
	<div class="login-info">
		<span>
			<!-- User image size is adjusted inside CSS, it should stay as it -->
			<a href="javascript:void(0);" id="show-shortcut" uid="<c:out value="${userID}"/>">
				<img src="../img/avatars/sunny.png" alt="me" class="online" />
				<span><c:out value="${userName}"/></span>
			</a>
		</span>
	</div>
	<!-- end user info -->
	<!-- NAVIGATION : This navigation is also responsive
		To make this navigation dynamic please make sure to link the node
		(the reference to the nav > ul) after page load. Or the navigation
		will not initialize.
	-->
	<nav id="main_nav">      
		<ul>
			<c:forEach var="gm" items="${menus}">
				<c:choose>
					<c:when test="${gm.pid==0}">
						<li>
							<a href="#" id="group_<c:out value="${gm.id}"/>" title="<c:out value="${gm.name}"/>">
								<i class="fa fa-lg fa-fw fa-key "></i>
								<span class="menu-item-parent"><c:out value="${gm.name}"/></span>
							</a>
							<ul>
							<c:forEach var="subm" items="${menus}">
								<c:choose>
									<c:when test="${subm.pid==gm.id}">
										<li>
											<a id="menu-<c:out value="${gm.id}"/>-<c:out value="${subm.id}"/>" href="<c:out value="${subm.url}"/>"><c:out value="${subm.name}"/></a>
										</li>
									</c:when>
								</c:choose>
							</c:forEach>
							</ul>
						</li>
					</c:when>
				</c:choose>
			</c:forEach>
		</ul>
	</nav>
	<span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>
</aside>
<!-- END NAVIGATION -->