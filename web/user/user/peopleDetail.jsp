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
    	label{
    		margin:0;
    		padding-left:0;
    	}
    	#intro p{
    		font-size:16px;
    		font-weight:normal;
    	}
    </style>
</head>

<body>
    <%@include file="../../inc/header.inc"%>
	<%@include file="../inc/peopleHeader.inc"%>
	
    <div class="body-container" style="margin-bottom:100px;">
    <br />
      	<form class="form-horizontal" role="form">

      	<c:if test="${user.name_show==1}">
		  <div class="form-group">
		    <label class="col-sm-2 control-label">姓名：</label>
		    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.name}"/></label>
		  </div>
		</c:if>
	  	  <div class="form-group">
		    <label class="col-sm-2 control-label">性别：</label>
		    <label class="col-sm-2 control-label" style="text-align:left;"><c:if test="${user.gender==1}">女</c:if> <c:if test="${user.gender==0}">男</c:if> </label>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">年龄：</label>
		    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.ageTypeName}"/></label>
		  </div>
		  	<c:if test="${user.height_show==1}">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">身高：</label>
			    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.height}"/>&nbsp;厘米</label>
			  </div>
			</c:if>
			<c:if test="${user.weight_show==1}">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">身高：</label>
			    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.weight}"/>&nbsp;斤</label>
			  </div>
			</c:if>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">工作城市：</label>
		    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.provinceName}"/>-<c:out value="${user.cityName}"/></label>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">职业：</label>
		    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.perJobName}"/></label>
		  </div>
		  <c:if test="${user.school_show==1}">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">毕业学校：</label>
			    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.school}"/></label>
			  </div>
			</c:if>
			 <c:if test="${user.professional_show==1}">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">学习专业：</label>
			    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.professional}"/></label>
			  </div>
			</c:if>
			<c:if test="${user.degree_show==1}">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">学历：</label>
			    <label class="col-sm-2 control-label" style="text-align:left;"><c:out value="${user.degree}"/></label>
			  </div>
			</c:if>
		    <c:if test="${user.intro_show==1}">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">工作经历：</label>
			    
			  </div>
			</c:if>
			  <c:if test="${user.intro_show==1}">
			  <div class="form-group">
			    <input type="hidden" id="txtintro" value="<c:out value="${user.intro}"/>" />
			   <div class="col-sm-offset-2 col-sm-10" id="intro" style="text-align:left;font-size:16px;font-weight:normal;"></div>
			  </div>
			</c:if>
		    
		
     	</form>
    </div>
	<%@include file="../../inc/footer.inc"%>
	
	<%@include file="../inc/script.inc"%>
    <script type="text/javascript" src="../static/js/common.js"></script>
    <script type="text/javascript" src="../js/utils.js"></script>
    <script type="text/javascript">
    $(function(){
    	$(".people_globaltop").css({
    		"height":"320px",
    		"background":"#F8F8F8 url(../static/image/head_bg_001.jpg)",
    		"background-size":"cover"
    		});
     
    	$(".wrap").css({
    		"height":"320px" 
    		});
    	
    	$("#intro").html($("#txtintro").val());
    	
    	
    });
    </script>
 
</body>
</html>