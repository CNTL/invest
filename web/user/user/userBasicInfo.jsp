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
.rectitle{
	font-size:18px;
	font-weight:bold;
	margin:10px 0;
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
 background:#4AC4EF;
 color:#fff;
 text-decoration: none;
}
.selected{
 background:#4AC4EF;
 color:#fff;
 text-decoration: none;
}
</style>
</head>
<body>
<%@include file="../../inc/header.inc"%>
<div class="shadow"></div>
<div class="container container-ex">
	<%@include file="../inc/userHeader.inc"%>
	<div class="row container-wapper">
	<%@include file="../inc/userHeaderMenu.inc"%>
	<div class="col-md-8">
        <div class="container-right">
        	<form class="form-horizontal" role="form"  id="form" name="form" action="">
        	
        		  <div class="form-group">
                      <label for="code" class="col-sm-3 control-label">登录账户：</label>
                      <div class="col-sm-9">
                          <input type="text" class="form-control" id="code" name="code" value="" disabled="disabled">
                      </div>
                  </div>
                  
                  <div class="form-group">
                      <label for="perNickName" class="col-sm-3 control-label">昵称：</label>
                      <div class="col-sm-9">
                          <input type="text" class="form-control validate[maxSize[255]]" id="perNickName" name="perNickName" placeholder="昵称">
                      </div>
                  </div>
                  
                  <div class="form-group">
                       <label for="gender" class="col-sm-3 control-label">性别：</label>
                       <div class="col-sm-9">
                            <select name="gender" id="gender" class="form-control">
                                       <option value="0">男</option>
                                       <option value="1">女</option>
                                   </select>
                       </div>
                   </div>
                  <div class="form-group">
                      <label id="area" for="province" class="col-sm-3 control-label">工作城市：</label>
                      <div class="col-sm-3">
                           <select id="province" name="province" class="form-control validate[required]" onchange="complete.changeProvince();">
								<option value="">省份</option>
								<option value="1">选项一</option>
							</select>
							 
                      </div>
                      <div class="col-sm-3">
                            
							<select id="city" name="city" class="form-control validate[required]">
								<option value="">城市</option>
								<option value="1">选项一</option>
							</select>
                      </div>
                  </div>
                  <div class="form-group">
					<label for="perjob" class="col-sm-3 control-label">职业:</label>
					<div class="col-sm-2">
						<span class="btn btn-primary" data-toggle="modal" data-target="#myModal">选择职业</span>
						<input type="hidden" id="recIDs" name="recIDs" />
						<input type="hidden" id="recNames" name="recNames" />
					</div>
					<div class="col-sm-4">
						<input type="text" class="form-control" readonly="readonly" maxlength="20" id="perjob"  check-type="required">
					</div>
				</div>
                  <div class="form-group">
                      <label for="intro" class="col-sm-3 control-label">个人简介：</label>
                      <div class="col-sm-9">
                          
                          <textarea class="form-control" rows="5" id="intro" name="intro" placeholder="个人简介"></textarea>
                      </div>
                  </div>
                  
                  <div class="form-group">
                     <div class="col-sm-12 text-center">
                         <button type="submit" class="btn btn-primary" id="btnSave">保存信息</button>
                     </div>
                 </div>
                 
        	</form>
        </div>
     </div>
	</div>
</div>

<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title rectitle" id="myModalLabel">职业选择</h4>
      </div>
      <div class="modal-body" id="modal-body">
      <c:forEach var="type" varStatus="status" items="${types}" >
      <div class="rectitle"><c:out value="${type.name}"/></div>
      <div class="recitem">
       	<c:forEach var="subType" varStatus="status" items="${type.subDics}" >
	  		<a onclick="selectrec()" data-id="${subType.id}" data-name="${subType.name}">${subType.name}</a>
	 	</c:forEach>
	  </div>
      </c:forEach>

      </div>
    </div>
  </div>
</div>

<!-- footer -->
<script type="text/javascript" src="../proj/script/datas.js"></script>
<script type="text/javascript" src="../user/user/script/userBasicInfo.js"></script>
<!-- footer -->

<%@include file="../../inc/footer.inc"%>
</body>
</html>