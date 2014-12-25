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
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="job_view">
	<div class="main">
		<div class="block">
            <h2>简历名称</h2>
            <div id="name" class="content">
                 
            </div>
        </div>
        <div class="block">
            <h2>简历附件：</h2>
            <div id="coverIMG_div" style="display:none;position: absolute; z-index: 122; width:300px;height:50px;overflow:hidden;background:#fff;border:0px solid #C7C7C7;">
			</div>
        </div>
        <div class="block">
            <h2>简历内容：</h2>
            <div id="contentTxt" class="content">
                 
            </div>
        </div>
	</div>
	<div class="clear"></div>
</div>
<div style="display:none">
		<input type="hidden" id="id" name="id" value="<c:out value="${id}"/>"/>
		<input type="hidden" id="affix" name="affix" value="" />
	</div>
<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
			
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../user/resume/script/resumeDetail.js"></script>
</body>
</html>