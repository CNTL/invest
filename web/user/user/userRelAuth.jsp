<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title><c:out value="${title}"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<c:out value="${keywords}"/>" />
<meta name="description" content="<c:out value="${description}"/>" />
<link rel="stylesheet" type="text/css" href="../js/plugin/uploadify-3.2.1/uploadify.css"/>
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
							<label for="">
							<span id="isIdent">
						 	</span>
						 	</label>
						</div>
						<div class="input">
					        <label for="name">姓名：</label>
					        <input class="form-control validate[required]" type="text" id="name" name="name" />
					    </div>
					     <div class="input">
							<label>影人分类：</label>
							<select id="firstType" name="firstType" class="validate[required]">
							</select>
							<select id="secondType" name="secondType" class="validate[required]">
							</select>
						</div>
						<div class="input">
					        <label for="perPhone">手机：</label>
					        <input class="form-control validate[required,custom[mobilephone],required]" type="text" id="perPhone" name="perPhone" />
					    </div>
					    <div class="input">
					        <label for="identityCard">身份证：</label>
					        <input class="form-control validate[custom[shenfenzheng],required]" type="text" id="identityCard" name="identityCard" />
					    </div>
					    <div class="input" style="height:150px;">
							<table style="width:100%;">
								<tr>
									<td valign="top" style="width:90px;">
										<label>身份证正面证件照：</label>
									</td>
									<td>
										<input type="file" name="uploadify" id="uploadify" />
										<input type="hidden" id="queueItemCount" name="queueItemCount" value="0" />
										<input type="hidden" id="organization" name="organization" value="" />
										<input type="hidden" id="uploadErrorMsg" name="uploadErrorMsg" value="" />
									</td>
								</tr>
							</table>
					    </div>
					    <div id="coverIMG_div" style="display:none;position: absolute; z-index: 122; width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">
						</div>
						<div class="input" style="height:150px;">
							<table style="width:100%;">
								<tr>
									<td valign="top" style="width:90px;">
										<label>身份证反面证件照：</label>
									</td>
									<td>
										<input type="file" name="uploadify1" id="uploadify1" />
										<input type="hidden" id="queueItemCount1" name="queueItemCount1" value="0" />
										<input type="hidden" id="orgBusinessLicense" name="orgBusinessLicense" value="" />
										<input type="hidden" id="uploadErrorMsg1" name="uploadErrorMsg1" value="" />
									</td>
								</tr>
							</table>
					    </div>
					    <div id="coverIMG_div1" style="display:none;position: absolute; z-index: 122; width:150px;height:150px;overflow:hidden;background:#fff;border:1px solid #C7C7C7;">
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
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../user/user/script/datas.js"></script>
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript" src="../user/user/script/userRelAuth.js"></script>
</body>
</html>