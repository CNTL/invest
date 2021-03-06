<%@page pageEncoding="UTF-8"%>
<div class="people_globaltop">
     <div class="wrap">
           <!-- <a href="../org/BasicInfo.do?infoType=1&mainType=1" class="profile">个人设置</a> -->
           <div class="avavtar">
               <c:choose>
					<c:when test="${user.head=='' || user.head == null}"><img style="border-radius: 50%;" src="../static/image/temp/pic2.png" /></c:when>
					<c:otherwise><img style="border-radius: 50%;" src="../<c:out value="${user.head}"/>" /></c:otherwise>
			 	</c:choose>
           </div>
           <div class="info">
           		<c:choose>
					<c:when test="${user.type==0}">
						<h2><c:out value="${user.perNickName}"/>
						<span>个人</span>
						<span>
	                  	<c:choose>
							<c:when test="${user.isRealNameIdent==1}">已认证</c:when>
							<c:otherwise>未认证</c:otherwise>
					 	</c:choose>
					 	</span>
						</h2>
						<div class="desc">
		                  	<c:out value="${user.provinceName}"/>/<c:out value="${user.cityName}"/>&nbsp;<c:out value="${user.perJobName}"/>、
		                  	
		                  	 <br />
		                    <a href="../user/MsgDetailMa.do?msguserid=<c:out value="${user.id}"/>" style="cursor:pointer;" ><span>短信息</span></a>
		                </div>
					</c:when>
					<c:when test="${user.type==1}">
						<h2><c:out value="${user.orgFullname}"/>
						<span>机构</span>
						<span>
	                  	 <c:choose>
							<c:when test="${user.isRealNameIdent==1}">已认证</c:when>
							<c:otherwise>未认证</c:otherwise>
					 	 </c:choose>
					 	 </span>
						</h2>
						<div class="desc">
		                  	 简称：<c:out value="${user.orgShortname}"/>
		                  	 <br />
		                    <a href="../user/MsgDetailMa.do?msguserid=<c:out value="${user.id}"/>" style="cursor:pointer;" ><span>短信息</span></a>
		                </div>
					</c:when>
					<c:otherwise>未知</c:otherwise>
				</c:choose>
               
           </div>
           <div class="clear"></div>
           <ul class="nav">
           		<c:choose>
					<c:when test="${user.type==0}">
						<li><a href="../user/PeopleDetailMain.do?a=detail&mainType=4&id=${user.id}" <c:if test="${viewType eq 'detail'}">class="current"</c:if>>简介</a></li>
		                <li><a href="../user/PhotoGroupMain.do?a=photo&mainType=4&id=${user.id}" <c:if test="${viewType eq 'photo'}">class="current"</c:if>>相册</a></li>
		                <li><a href="../user/VideoGroupMain.do?a=video&mainType=4&id=${user.id}" <c:if test="${viewType eq 'video'}">class="current"</c:if>>视频</a></li>
						<li><a href="../user/peopleProject.do?a=proj&mainType=2&id=${user.id}" <c:if test="${viewType eq 'proj'}">class="current"</c:if>>项目</a></li>
					</c:when>
					<c:when test="${user.type==1}">
						<li><a href="../user/PeopleDetailMain.do?a=detail&mainType=3&id=${user.id}" <c:if test="${viewType eq 'detail'}">class="current"</c:if>>简介</a></li>
		                <li><a href="../user/PhotoGroupMain.do?a=photo&mainType=3&id=${user.id}" <c:if test="${viewType eq 'photo'}">class="current"</c:if>>相册</a></li>
		                <li><a href="../user/VideoGroupMain.do?a=video&mainType=3&id=${user.id}" <c:if test="${viewType eq 'video'}">class="current"</c:if>>视频</a></li>
						<li><a href="../user/peopleProject.do?a=proj&mainType=2&id=${user.id}" <c:if test="${viewType eq 'proj'}">class="current"</c:if>>项目</a></li>
					</c:when>
				</c:choose>
           </ul>
           <div>
           		<input type="hidden" id="curUserID" name="curUserID" value="<c:out value="${user.id}"/>"/>
    			<input type="hidden" id="userName" name="userName" value="<c:out value="${user.perNickName}"/>"/>
           </div>
	</div>
</div>