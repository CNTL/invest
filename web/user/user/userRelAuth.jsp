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
					    <!-- 
						<div class="input">
							<label for="province">工作所在地：</label>
							<select id="province" name="province">
								<option>请选择省份</option>
								<option value="安徽" rel="3">安徽</option>
								<option value="澳门" rel="396">澳门</option>
								<option value="北京" rel="52">北京</option>
								<option value="福建" rel="4">福建</option>
								<option value="甘肃" rel="5">甘肃</option>
								<option value="广东" rel="6">广东</option>
								<option value="广西" rel="7">广西</option>
								<option value="贵州" rel="8">贵州</option>
								<option value="海南" rel="9">海南</option>
								<option value="河北" rel="10">河北</option>
								<option value="黑龙江" rel="12">黑龙江</option>
								<option value="河南" rel="11">河南</option>
								<option value="湖北" rel="13">湖北</option>
								<option value="湖南" rel="14">湖南</option>
								<option value="江苏" rel="16">江苏</option>
								<option value="江西" rel="17">江西</option>
								<option value="吉林" rel="15">吉林</option>
								<option value="辽宁" rel="18">辽宁</option>
								<option value="内蒙古" rel="19">内蒙古</option>
								<option value="宁夏" rel="20">宁夏</option>
								<option value="青海" rel="21">青海</option>
								<option value="山东" rel="22">山东</option>
								<option value="上海" rel="321">上海</option>
								<option value="山西" rel="23">山西</option>
								<option value="陕西" rel="24">陕西</option>
								<option value="四川" rel="26">四川</option>
								<option value="台湾" rel="397">台湾</option>
								<option value="天津" rel="343">天津</option>
								<option value="香港" rel="395">香港</option>
								<option value="西藏" rel="28">西藏</option>
								<option value="新疆" rel="29">新疆</option>
								<option value="云南" rel="30">云南</option>
								<option value="浙江" rel="31">浙江</option>
								<option value="重庆" rel="394">重庆</option>
							</select>
							<select id="city" name="city">
								<option>请选择城市</option>
							</select>
						</div>
						<div class="input">
					        <label for="perJob">职业：</label>
					        <select id="perJob" name="perJob" class="custform-select validate[maxSize[255],required]" wx-validator-error-value="选择职业">
					        </select>
					    </div>
					     -->
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
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript" src="../user/user/script/userRelAuth.js"></script>
</body>
</html>