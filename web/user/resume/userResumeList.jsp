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
<link rel="stylesheet" type="text/css" href="../user/resume/css/resume.css">
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="main clearfix">
	<div class="setting wrap">
	<%@include file="../inc/userHeader.inc"%>
	<div class="setting-detail">
		<div class="btn">
			<input type="button" id="addResume" value="添加简历">
		</div>
		<div class="people_view" style="width:700px;">
		    <table>
		        <thead>
		            <tr>
		                <th>简历名称</th>
		                <th style="width: 80px;">操作</th>
		            </tr>
		        </thead>
		        <tbody id='mytbody'>
		        </tbody>
		    </table>
		</div>
	</div>
</div>
<div>
<input type="hidden" id="total" name="total" value="" />
<input type="hidden" id="curPage" name="curPage" value="" />
<input type="hidden" id="pageCount" name="pageCount" value="" />
<input type="hidden" id="length" name="length" value="" />
<input type="hidden" id="userName" name="userName" value="" />
<input type="hidden" id="type" name="type" value="userResumeList" />
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->    
<script type="text/javascript" src = "../user/resume/script/resumeList.js"></script>
</body>
</html>