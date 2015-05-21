<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title><c:out value="${title}"/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="<c:out value="${keywords}"/>" />
	<meta name="description" content="<c:out value="${description}"/>" />
    <%@include file="../inc/csslink.inc"%>
    <style>
.reclist{
	margin:0;
	padding:5px;
}
.reclist li{
	line-height:60px;
	border-left:3px solid #FFB124;
	margin:10px 0;
	padding:5px;
}
.reclist li h3{
	font-size:16px;
}
.reclist li span{
	font-size:14px;
	margin-left:10px;
	
}
.reclist li p{
	color:#999999;
	
}
.reclist li a {text-decoration:none;color:#666666;} 
.reclist li a:hover{
	cursor:pointer;
}

.listover{
	background:#FFB124;
	color:#fff !important;
}

</style>
</head>

<body>
    <%@include file="../../inc/header.inc"%>
	<%@include file="../inc/peopleHeader.inc"%>
	
    <div class="body-container" style="margin-bottom:100px;">
   <div role="tabpanel">

			  <!-- Nav tabs -->
			  <ul class="nav nav-tabs" role="tablist">
			    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">发起的项目</a></li>
			    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">支持的项目</a></li>
			   
			  </ul>
			
			  <!-- Tab panes -->
			  <div class="tab-content">
			    <div role="tabpanel" class="tab-pane active" id="home">
			    <ul class="reclist">
			     <c:forEach var="pub" varStatus="status" items="${proPubList}" >
			     	<li>
			     	<a href="../project/Project.do?id=${pub.id}">
			     	<h3>${pub.name}<span>[${pub.provinceName}/${pub.cityName}]</span> 
			     	<span class="pull-right">
			     	<c:if test="${pub.payType==1}"><span class="label label-danger">竞拍</span></c:if>
			     	
			     	
			     	${pub.typeName}
			     	/目标：${pub.amountGoal}
			     	</span></h3>
			     	 
			     	
			     	</a>
			     	
			     	</li>
			     </c:forEach>
			     </ul>
			    </div>
			    <div role="tabpanel" class="tab-pane" id="profile">
				<ul class="reclist">
			     <c:forEach var="surp" varStatus="status" items="${proSurpList}" >
			     	<li>
			     	<a href="../project/Project.do?id=${pub.id}">
			     	<h3>${surp.name}<span>[${surp.provinceName}/${surp.cityName}]</span> 
			     	<span class="pull-right">
			     	<c:if test="${surp.payType==1}"><span class="label label-danger">竞拍</span></c:if>
			     	
			     	
			     	${surp.typeName}
			     	/目标：${surp.amountGoal}
			     	</span></h3>

			     	</a>
			     	
			     	</li>
			     </c:forEach>
			     </ul>
				</div>
			  </div>
			
			</div>
    </div>
	<%@include file="../../inc/footer.inc"%>
	
	<%@include file="../inc/script.inc"%>
    <script type="text/javascript">
    $(function(){
    	 
    	
    	
    });
    </script>
 
</body>
</html>