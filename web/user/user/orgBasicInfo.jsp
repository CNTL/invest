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
					        <input type="text" id="code" name="code" value="" disabled/>
					        <br>
					    </div>
					    <div class="input">
					        <label for="nickName">机构简称：</label>
					        <input class="form-control validate[maxSize[255],required]" type="text" id="orgShortname" name="orgShortname" placeholder="机构简称"/>
					    </div>
					    <div class="input">
					        <label for="postAddr">机构全称：</label>
					        <input class="form-control validate[maxSize[255],required]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称"/>
					    </div>
						<div class="input">
							<label>机构简介：</label>
							<textarea  id="intro" name="intro" style="width:400px;height:100px;" placeholder="机构简介"></textarea>
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
<script type="text/javascript" src="../user/user/script/orgBasicInfo.js"></script>
</body>
</html>