<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@include file="../../inc/meta.inc"%>
<%@include file="../inc/csslink.inc"%>
<style>
.rectitle{
	font-size:18px;
	font-weight:bold;
	margin:10px 0;
}
.lb{
font-size: 14px;
}
.recitem a{
display: inline-block;
width: 100px;
height: 28px;
line-height: 28px;
font-size: 16px;
cursor:pointer;
text-decoration: none;
}
.recitem a:hover{
 background:green;
 color:#fff;
 text-decoration: none;
}
</style>
</head>
<body>
<div id="body-container" style="min-width:980px;">
<%@include file="../../inc/header.inc"%>
 <div class="banner hidden-xs">
    <img src="../static/image/banner1.png" />
</div>
    <div class="project_list">
      	<div class="block1">
      	<h1 style="font-size:24px;lien-height:24px;height:25px;margin:10px;">我的职位订阅</h1>
<form class="form-horizontal" >
<div class="form-group">
    <label for="name" class="col-sm-2 control-label lb">订阅名称：</label>
    <div class="col-sm-6">
      <input type="text" name="name" class="form-control input-sm" id="name" placeholder="请输入订阅名称">
    </div>
  </div>
  <div class="form-group">
    <label for="email" class="col-sm-2 control-label lb">订阅邮件：</label>
    <div class="col-sm-6">
      <input type="email" class="form-control" name="email" id="email" placeholder="请输入订阅邮件">
    </div>
  </div>
   <div class="form-group">
    <label for="sl-rate" class="col-sm-2 control-label lb">订阅频率：</label>
    <div class="col-sm-4">
     <select class="form-control" name="sl-rate" id="sl-rate"><option value="3">3天</option><option value="7">7天</option></select>
    </div>
  </div>
  <div class="form-group">
    <label for="sl-city" class="col-sm-2 control-label lb">订阅城市：</label>
    <div class="col-sm-10">
     <button class="btn btn-default" data-toggle="popover"  type="button" id="sl-city"    >
    	选择城市
    	<span class="caret"></span>
  	</button>
  	<span id="city" style="font-size:16px;margin-left:10px;"></span>
  	<input type="hidden" name="hcityid" id="hcityid" />
  	<input type="hidden" name="hcityname" id="hcityname" />
    </div>
  </div>
 <div class="form-group">
    <label for="rec" class="col-sm-2 control-label lb">订阅职位：</label>
    <div class="col-sm-10">
 
	<button class="btn btn-default" data-toggle="modal" data-target="#myModal" type="button" id="rec"    >
    	选择职业
    	<span class="caret"></span>
  	</button>
  	<span id="sl-rec" style="font-size:16px;margin-left:10px;"></span>
  	<input type="hidden" name="hrecid" id="hrecid" />
  	<input type="hidden" name="hrecname" id="hrecname" />
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">保存</button>
    </div>
  </div>
</form>
</div>
      	</div>
    </div>

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->    
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title rectitle" id="myModalLabel">订阅职位选择</h4>
      </div>
      <div class="modal-body" id="modal-body">
      <c:forEach var="type" varStatus="status" items="${types}" >
      <div class="rectitle"><c:out value="${type.name}"/></div>
      <div class="recitem">
       	<c:forEach var="subType" varStatus="status" items="${type.subDics}" >
	  		<a data-dismiss="modal" onclick="selectrec()" data-id="${subType.id}" data-name="${subType.name}">${subType.name}</a>
	 	</c:forEach>
	  </div>
      </c:forEach>
      </div>
    </div>
  </div>
</div>

<div id="morecity" class="item" style="display:none;">
	<c:forEach var="city" varStatus="status" items="${cities}" begin="0"  step="1" >
		<a onclick="selectcity()" style="margin:5px;font-size:16px;cursor:pointer;" data-id="${city.id}" data-name="${city.name}">${city.name}</a>
	</c:forEach>
</div>
<script>
	var typeFlag = "<%=request.getParameter("recruitType") %>";
	var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
 <script type="text/javascript" src="../js/bootstrap/js/bootstrap.min.js"></script>
<script src = "../user/recruit/script/recruitSubscibe.js"></script>
</body>
</html>