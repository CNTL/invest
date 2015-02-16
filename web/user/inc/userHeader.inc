<%@page pageEncoding="UTF-8"%>

   <c:if test="${loginUser.type==0}">
		<h3>个人设置</h3>
		 
   </c:if>
	<c:if test="${loginUser.type==1}">
		<h3>机构设置</h3>
	</c:if>
 