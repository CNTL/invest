<%@page pageEncoding="UTF-8"%>
<div class="people_globaltop">
     <div class="wrap">
           <!-- <a href="../org/BasicInfo.do?infoType=1&mainType=1" class="profile">个人设置</a> -->
           <div class="avavtar">
               <c:choose>
				<c:when test="${user.head=='' || user.head == null}"><img src="../static/image/temp/pic2.png" /></c:when>
				<c:otherwise><img src="../<c:out value="${user.head}"/>" /></c:otherwise>
			</c:choose>
           </div>
           <div class="info">
               <h2>我是一个<c:out value="${user.perJobName}"/>
				<span>
					<c:choose>
						<c:when test="${user.type==0}">个人</c:when>
						<c:when test="${user.type==1}">机构</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</span>
			 </h2>
               <div class="desc">
                  	 昵称：<c:out value="${user.perNickName}"/><br />
                   <a onclick="msg.addMsg();"><span>短信息</span></a>
               </div>
           </div>
           <div class="clear"></div>
           <ul class="nav">
               <li><a href="../user/PeopleDetailMain.do?a=detail&id=${user.id}" <c:if test="${viewType eq 'detail'}">class="current"</c:if>>个人简介</a></li>
               <li><a href="../user/PhotoGroupMain.do?a=photo&id=${user.id}" <c:if test="${viewType eq 'photo'}">class="current"</c:if>>相册</a></li>
               <li><a href="../user/VideoGroupMain.do?a=video&id=${user.id}" <c:if test="${viewType eq 'video'}">class="current"</c:if>>视频</a></li>
           </ul>
           <div style="display:none;">
           		<input type="hidden" id="userID" name="userID" value="<c:out value="${user.id}"/>"/>
    			<input type="hidden" id="userName" name="userName" value="<c:out value="${user.perNickName}"/>"/>
           </div>
	</div>
</div>