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
							<label for="orgFullname">
							<span id="isIdent">
						 	</span>
						 	</label>
						</div>
						<div class="input">
					        <label for="orgFullname">公司全称：</label>
					        <input class="form-control validate[maxSize[255]]" type="text" id="orgFullname" name="orgFullname" />
					    </div>
						<div class="input">
					        <label for="name">法人代表：</label>
					        <input class="form-control validate[required]" type="text" id="name" name="name" />
					    </div>
					    <div class="input">
					        <label for="identityCard">法人代表身份证：</label>
					        <input class="form-control validate[custom[shenfenzheng],required]" type="text" id="identityCard" name="identityCard" />
					    </div>
					    <div class="input">
					        <label for="perPhone">法人代表手机：</label>
					        <input class="form-control validate[custom[mobilephone],required]" type="text" id="perPhone" name="perPhone" style="width:200px" />
					        <span class="btn" id="btnGetCode">获取验证码</span>
					    </div>
					    <div class="input">
					        <label for="identityCard">短信验证码：</label>
					        <input class="form-control" type="text" id="perPhoneCode" name="perPhoneCode" style="width:200px" />
					        <input type="hidden" id="perPhoneCodeCur" name="perPhoneCodeCur"/>
					    </div>
					    <div class="input" style="display:none;">
					        <label for="openingBanks">银行卡开户行：</label>
					        <input class="form-control validate[required]" type="text" id="openingBanks" name="openingBanks" />
					    </div>
					    <div class="input" style="display:none;">
					        <label for="bankNums">银行卡号：</label>
					        <input class="form-control" type="hidden" id="bankNums" name="bankNums" />
					    </div>
					    <div class="input" style="height:150px;">
							<table style="width:100%;">
								<tr>
									<td valign="top" style="width:90px;">
										<label>企业组织机构证件照：</label>
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
										<label>企业营业执照扫描件：</label>
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
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript">
var rootPath = "<%=com.tl.common.WebUtil.getRoot(request) %>";
</script>
<script type="text/javascript" src="../js/plugin/uploadify-3.2.1/jquery.uploadify.js"></script>
<script type="text/javascript" src="../js/utils.js"></script>
<script type="text/javascript" src="../user/user/script/orgRelAuth.js"></script>
</body>
</html>