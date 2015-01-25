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
<div class="main clearfix">
	<div class="setting wrap">
			<%@include file="../inc/userHeader.inc"%>
			<div class="setting-detail">
				<div class="job_add">
				    <form class="setting-form" id="form" name="form" action="">
				        <div class="input">
					        <label for="nickName">登录账户：</label>
					        <input type="text" id="code" name="code" value="" disabled="true"/>
					        <br>
					    </div>
					    <div class="input">
					        <label for="perNickName">昵称：</label>
					        <input class="form-control validate[maxSize[255]]" type="text" id="perNickName" name="perNickName" placeholder="昵称"/>
					    </div>
					    <div class="input">
					    	<label for="gender">性别：</label>
					    	<select id="gender" name="gender">
		                    	<option value="0">男</option>
		                        <option value="1" selected>女</option> 
		                    </select>
		                </div>
						<div class="input">
							<label>个人简介：</label>
							<textarea  id="intro" name="intro" style="width:400px;height:100px;" placeholder="个人简介"></textarea>
						</div>
				        <div class="btn">
				        	<input type="submit" id="btnSave" value="提交信息">
				        </div>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- script -->
<%@include file="../inc/script.inc"%>
<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->
<script type="text/javascript" src="../user/user/script/userBasicInfo.js"></script>
</body>
</html>