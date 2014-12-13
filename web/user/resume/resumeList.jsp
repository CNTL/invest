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
<div class="people_globaltop">
    <div class="wrap">
        <ul class="nav">
        	<c:forEach var="gm" items="${iMenus}">
        		<li><a href="<c:out value="${gm.url}"/>" class="<c:out value="${gm.className}"/>"><c:out value="${gm.name}"/></a></li>
			</c:forEach>
        </ul>
    </div>
</div>
<br/>
<div class="btn">
	<input type="button" id="addResume" value="添加简历">
</div>
<div class="people_view">
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
    <div class="pager">
        <span class="count" title="总记录数">6 条</span>
        <span class="prev">上一页</span>
        <span class="current">1</span>
        <a href="#" class="next">下一页</a>
    </div>
</div>
<div>
<input type="hidden" id="total" name="total" value="" />
<input type="hidden" id="curPage" name="curPage" value="" />
<input type="hidden" id="pageCount" name="pageCount" value="" />
<input type="hidden" id="length" name="length" value="" />
<input type="hidden" id="userName" name="userName" value="" />
</div>
<%-- 
<div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			<div class="setting-detail">
				<div class="job_add">
				    <div class="input">
				    	<input type="button" style="width:100px" id="addResume" value="添加简历"/>
				        <div id="main">
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
	</div>
 --%>

<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->    
<script type="text/javascript" src = "../user/resume/script/resumeList.js"></script>
</body>
</html>