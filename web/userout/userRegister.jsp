<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />

<link rel="stylesheet" type="text/css" href="../js/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="../js/plugin/bs-validation/bs-validation-ie78.css" />
<link rel="stylesheet" type="text/css" href="../static/css/reset.css" />
<link rel="stylesheet" type="text/css" href="../static/css/index.css" />
<style>
body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, input, button, select, option, textarea, p, blockquote, th, td {
    font-size: 14px;
}
#roleSelect li{
	border-radius:4px;
}
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
	<%@include file="../inc/header.inc"%>
    <div class="shadow"></div>
    <div class="register">
        <div class="form">
           <form class="form-horizontal" style="width:600px;"  id="form" action="#" role="form">
                <input type="hidden" name="type" id="type" value="0" />
                <h2>用户注册</h2>
                <div class="select" style="padding-left:130px;">
                    <ul id="roleSelect">
                    	 <li data="0" class="current">个人</li>
                        <li data="1">机构</li>
                    </ul>
                </div>
				<div class="form-group">
					<label for="code" class="col-sm-2 control-label">用户名:</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" maxlength="20" id="code" placeholder="请输入用户名" check-type="required">
					</div>
					
				</div>
				<div class="form-group" id="userRec">
					<label for="perjob" class="col-sm-2 control-label">职业:</label>
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
					<label for="password1" class="col-sm-2 control-label">密码:</label>
					<div class="col-sm-6">
						<input type="password" type="text" class="form-control"  minlength="6" maxlength="200" id="password1" placeholder="请输入密码" check-type="required">
					</div>
				</div>
				<div class="form-group">
					<label for="password2" class="col-sm-2 control-label">确认密码:</label>
					<div class="col-sm-6">
						<input type="password" type="text" class="form-control"  minlength="6" maxlength="200" id="password2" placeholder="请再次输入密码" check-type="required">
					</div>
				</div>
				
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">邮箱:</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" maxlength="200" id="email" placeholder="请输入邮箱" check-type="mail required">
					</div>
					<div class="col-sm-offset-2 col-sm-10">
						<span class="help-block" style="padding-top: 10px;">请填写真实的邮箱，邮箱将用于找回密码使用。</span>
					</div>
				</div>
				
				<div class="form-group">
		        <label for="myVal" class="col-sm-2 control-label">验证码:</label>
		        <div class="col-sm-4">
		          <input type="text" class="form-control" maxlength="4" id="myVal" name="myVal" check-type="required" >
		          
		        </div>
		        
		      </div>  
		      <div class="form-group">
		      
		        <div class="col-sm-offset-2 col-sm-6">
		        	<img id="validateCode" src="" style="vertical-align: middle; margin-right: 20px;"></img><a id="changeCode" style="cursor:pointer;" onclick="changeValPic();">换一张</a>
		           <input type="hidden" id="curVal" name="curVal" style="color: #fe6249;text-decoration: underline;cursor: pointer;" value=""/>
		          
		        </div>
		      </div>  
		      <div class="form-group">
		        <label for="myVal" class="col-sm-7 control-label text-center">
		       		<input type="checkbox" id="accept" checked="checked" /> 阅读并同意<a target="_blank" href="../help/RegDoc.do" style="color:#55acef;">《用户注册服务协议》</a>
		        </label>
		         
		      </div>  
   
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" id="login" name="login"
							class="btn btn-info col-sm-4">提交申请</button>
					</div>
				</div>
			</form>
        </div>

    </div>
	<!-- footer -->
	<%@include file="../inc/footer.inc"%>
	<!-- footer -->
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
	<script src="../js/jquery/jquery.min.js"></script>
	<script src="../js/jquery/jquery-migrate.min.js"></script> 
	<script src="../js/bootstrap/js/bootstrap.min.js"></script>
    <script src="../js/plugin/bs-validation/bs-validation.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery.bootstrap.dialog-plus/jquery.dialog-plus.js"></script>
   
	<script type="text/javascript">
	var rootURL = "<c:out value="${rootPath}"/>";
	</script>
	<!-- script -->  
	 <script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="../userout/script/userRegister.js"></script>
 
</body>
</html>