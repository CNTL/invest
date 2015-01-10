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
					        <label for="orgFullname">公司全称：</label>
					        <input class="form-control validate[maxSize[255]]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称"/>
					    </div>
					    <div class="input">
							<label>地域：</label>
							<select id="province" name="province" class="validate[required]" onchange="detail.changeProvince();">
								<option value="">省份</option>
								<option value="1">选项一</option>
							</select>
							<select id="city" name="city" class="validate[required]">
								<option value="">城市</option>
								<option value="1">选项一</option>
							</select>
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
						 -->
					    <div class="input">
							<label for="province">公司地点：</label>
				            <input type="text" class="form-control input-lg" id="location" name="location" value="" placeholder="公司地址"/>
					        <input type="button" style="width:100px" id="mapSearch" value="从地图搜索">
				            <input type="hidden" class="form-control input-lg" id="coordinate" name="coordinate" value="" placeholder="公司地址坐标"/>
						</div>
					    <div class="input">
					        <label for="orgNature">公司性质：</label>
					        <input class="form-control validate[maxSize[255]]" type="text" id="orgNature" name="orgNature" placeholder="公司性质"/>
					    </div>
					    <div class="input">
					        <label for="orgTrade">所属行业：</label>
					        <input class="form-control validate[maxSize[255]]" type="text" id="orgTrade" name="orgTrade" style="width:200px"/>
					         <select id="orgTradeSel" name="orgTradeSel" class="custform-select validate[maxSize[255],required]" style="width:170px">
						        <option value="国营">国营</option>
						        <option value="外商独资">外商独资</option>
						        <option value="中外合资">中外合资</option>
						        <option value="私营企业">私营企业</option>
						        <option value="其他">其他</option>
					        </select>
					    </div>
					    <div class="input">
					        <label for="orgScale">公司规模：</label>
					        <select id="orgScale" name="orgScale" class="custform-select validate[maxSize[255],required]" style="width:400px">
						        <option value="1">100人以下</option>
						        <option value="2">100-500人</option>
						        <option value="3">500-1000人</option>
						        <option value="4">1000-5000人</option>
						        <option value="5">5000人以上</option>
					        </select>
					    </div>
						<div class="input">
					        <label for="orgHomePage">公司主页：</label>
					        <input class="form-control" type="text" id="orgHomePage" name="orgHomePage" placeholder="公司主页"/>
					    </div>
					    <div class="btn">
				        	<input type="submit" id="btnSave" value="提交信息">
				        </div>
					</form>
					<div style="display:none" style="width:680;height:490">
						<iframe id="msgMap" src="../common/MsgMap.jsp" width="670" align="center" height="480"></iframe>
					</div>
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
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript" src="../proj/script/datas.js"></script>
<script type="text/javascript" src="../user/user/script/orgDetailInfo.js"></script>
</body>
</html>