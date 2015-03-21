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
<%@include file="../inc/script.inc"%>
<style>
#contentTxt {
font-size:16px;
font-family:"Microsoft YaHei", helvetica, arial, verdana, tahoma, sans-serif;
color:#666;
line-height:30px;
}
#contentTxt span{
font-size:16px;
font-family:"Microsoft YaHei", helvetica, arial, verdana, tahoma, sans-serif;
color:#666;
line-height:30px;
}

legend{
border-bottom:3px #FFA1AC solid;
color:#666;
font-size:20px;
margin:10px;
padding:5px;
font-weight:bold;
}
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>

<div class="body-container">
 
	 <form class="form-horizontal" role="form">
	  <fieldset>
      <div id="legend">
        <legend id="name"></legend>
      </div>
    </fieldset>
    <div class="form-group">
	    <label  class="col-sm-2 control-label" id="headuser"></label>
	    <div class="col-sm-10">
	      <label class="control-label" id="username" style="margin-top:20px;"></label>
	    </div>
	  </div>
	  
	  
	   <div class="form-group">
	    <label  class="col-sm-2 control-label">性别：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="gender"></label>&nbsp;
	    </div>
	  </div>
	     <div class="form-group">
	    <label  class="col-sm-2 control-label">出生年月：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="birthdate"></label>&nbsp;
	    </div>
	  </div>
     <div class="form-group">
	    <label  class="col-sm-2 control-label">身高：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="height"></label>&nbsp;厘米
	    </div>
	  </div>
	  <div class="form-group">
	    <label  class="col-sm-2 control-label">体重：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="weight"></label>&nbsp;公斤
	    </div>
	  </div>
	  <div class="form-group">
	    <label  class="col-sm-2 control-label">毕业学校：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="school"></label>
	    </div>
	  </div>
	   <div class="form-group">
	    <label  class="col-sm-2 control-label">专业：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="professional"></label>
	    </div>
	  </div>
	  	   <div class="form-group">
	    <label  class="col-sm-2 control-label">学历：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="degree"></label>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="perPhone"  class="col-sm-2 control-label">电话：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="perPhone"></label>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="perJob"  class="col-sm-2 control-label">职业：</label>
	    <div class="col-sm-10">
	      <label class="control-label" id="perJob"></label>
	    </div>
	  </div>
     
     
	  <div class="form-group">
	    <label class="col-sm-2 control-label">工作经历:</label>
	    
	  </div>
	  
	   <div class="form-group">
	    <label  class="col-sm-2 control-label"></label>
	    <div class="col-sm-10">
	     <div id="contentTxt" class="text-left" style="font-size:14px;"></div>
	    </div>
	  </div>
	 
	</form>
</div>
		<input type="hidden" id="id" name="id" value="<c:out value="${id}"/>"/>
		<input type="hidden" id="affix" name="affix" value="" />

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