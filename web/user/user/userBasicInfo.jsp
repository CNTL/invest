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
<link rel="stylesheet" type="text/css" href="../js/plugin/bootstrap-switch/css/bootstrap-switch.min.css">
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
	<div class="col-md-9">
        <div class="container-right">
        	<form class="form-horizontal" role="form"  id="form" name="form" action="">
        	
        		  <div class="form-group">
                      <label for="code" class="col-sm-3 control-label">登录账户：</label>
                      <div class="col-sm-5">
                          <input type="text" class="form-control" id="code" name="code" value="" disabled="disabled">
                      </div>
                  </div>
                   <div class="form-group">
                      <label for="name" class="col-sm-3 control-label">真实姓名：</label>
                      <div class="col-sm-5">
                          <input type="text" class="form-control" id="name" name="name" value="" disabled="disabled">
                      </div>
                       <div class="col-sm-2">
                      	 <label  class="control-label"><input type="checkbox"  id="name_showcb" name="name_showcb"></label>
                      	 <input type="hidden" id="name_show" name="name_show" value="" />
                      </div>
                  </div>
                  <div class="form-group">
                  	<div class="col-sm-3">
                  	</div>
                  	<div class="col-sm-5">
                  	<span id="helpBlock" class="help-block "> 需移步到实名认证才可以完成。</span>
                  	</div>
                 		
                  </div>
                  <div class="form-group">
                      <label for="perNickName" class="col-sm-3 control-label">昵称：</label>
                      <div class="col-sm-5">
                          <input type="text" class="form-control validate[maxSize[255]]" id="perNickName" name="perNickName" placeholder="昵称">
                      </div>
                  </div>
                    <div class="form-group">
                      <label for="point" class="col-sm-3 control-label">信用积分：</label>
                      <div class="col-sm-2">
                           <label id="point" class="col-sm-3 control-label"></label>
                      </div>
                  </div>
                  
                  <div class="form-group">
                       <label for="gender" class="col-sm-3 control-label">性别：</label>
                       <div class="col-sm-3">
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
                      <label id="area" for="province" class="col-sm-3 control-label">年龄：</label>
                      <div class="col-sm-3">
                           <select id="age" name="age" class="form-control validate[required]" >
								 
							</select>
							 
                      </div>
                       
                  </div>
                   <div class="form-group">
                      <label for="height" class="col-sm-3 control-label">身高：</label>
                      <div class="col-sm-3">
                          <input type="text" class="form-control validate[minSize[2],custom[integer]]" id="height" name="height" placeholder="">
                      </div>
                      <div class="col-sm-2">
                      	<label  class="control-label">厘米</label>
                      </div>
                       <div class="col-sm-2">
                      	 <label  class="control-label"><input type="checkbox" id="height_showcb"  name="height_showcb"></label>
                      	 <input type="hidden" id="height_show" name="height_show" value="" />
                      </div>
                  </div>
                   <div class="form-group">
                      <label for="weight" class="col-sm-3 control-label">体重：</label>
                      <div class="col-sm-3">
                          <input type="text" class="form-control validate[maxSize[3],custom[integer]]" id="weight" name="weight" placeholder="">
                      </div>
                      <div class="col-sm-2">
                      	<label  class="control-label">公斤</label>
                      </div>
                       <div class="col-sm-2">
                      	 <label  class="control-label"><input type="checkbox" id="weight_showcb"  name="weight_showcb"></label>
                      	  <input type="hidden" id="weight_show" name="weight_show" value="" />
                      </div>
                  </div>
                   <div class="form-group">
                      <label for="school" class="col-sm-3 control-label">毕业学校：</label>
                      <div class="col-sm-5">
                          <input type="text" class="form-control validate[maxSize[255]]" id="school"  name="school" placeholder="">
                      </div>
                     
                       <div class="col-sm-2">
                      	 <label  class="control-label"><input type="checkbox" id="school_showcb"  name="school_showcb"></label>
                      	 <input type="hidden" id="school_show" name="school_show" value="" />
                      </div>
                  </div>
                    <div class="form-group">
                      <label for="professional" class="col-sm-3 control-label">学习专业：</label>
                      <div class="col-sm-5">
                          <input type="text" class="form-control validate[maxSize[255]]" id="professional" name="professional" placeholder="">
                      </div>
                     
                       <div class="col-sm-2">
                      	 <label  class="control-label"><input type="checkbox" id="professional_showcb"  name="professional_showcb"></label>
                      	  <input type="hidden" id="professional_show" name="professional_show" value="" />
                      </div>
                  </div>
                   <div class="form-group">
                      <label id="area" for="province" class="col-sm-3 control-label">学历：</label>
                      <div class="col-sm-3">
                           <select id="degreeid" name="degreeid" class="form-control validate[maxSize[255],required]" >
					        <option value="2">高中</option>
					        <option value="3">大专</option>
					        <option value="4">本科</option>
					        <option value="5">硕士</option>
					        <option value="6">博士</option>
				        </select>
				        <input type="hidden" id="degree" name="degree" value="" />
                      </div>
                      <div class="col-sm-offset-2 col-sm-2 switch" >
                     		<input type="checkbox" checked id="degree_showcb" name="degree_showcb">
					  </div> 
                  </div>
                  
                  <div class="form-group">
                      <label for="intro" class="col-sm-3 control-label">工作经历：</label>
                       <div class="col-sm-offset-5 col-sm-2">
                      	 <label  class="control-label"><input type="checkbox" id="intro_showcb"  name="intro_showcb"></label>
                      	  <input type="hidden" id="intro_show" name="intro_show" value="" />
                      </div>
                  </div>
                   <div class="form-group">
                      
                      <div class="col-sm-12">
                         <textarea  id="intro_editer" name="intro_editer" class="form-control validate[maxSize[4000]]"  placeholder="个人简介"></textarea>
                 		<input type="hidden" id="intro" name="intro" value=""/>
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
        <h4 class="modal-title rectitle" id="myModalLabel">职业选择(可多选)</h4>
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
<script type="text/javascript" src="../static/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../js/plugin/bootstrap-switch/js\bootstrap-switch.min.js"></script>
<!-- footer -->

<%@include file="../../inc/footer.inc"%>
</body>
</html>