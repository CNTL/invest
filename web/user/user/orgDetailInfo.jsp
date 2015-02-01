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
					        <input class="form-control validate[maxSize[255],required]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称"/>
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
					    <div class="input">
							<label for="province">公司地点：</label>
				            <input type="text" class="form-control input-lg" id="location" name="location" value="" placeholder="公司地址"/>
					        <input type="button" style="width:100px" id="mapSearch" value="从地图搜索">
				            <input type="hidden" class="form-control input-lg" id="coordinate" name="coordinate" value="" placeholder="公司地址坐标"/>
						</div>
					    <div class="input">
					        <label for="orgNature">公司性质：</label>
					        <input class="form-control validate[maxSize[255]]" type="text" id="orgNature" name="orgNature" style="width:200px" placeholder="公司性质"/>
					         <select id="orgNatureSel" name="orgTradeSel" class="custform-select validate[maxSize[255],required]" style="width:170px">
						        <option value="国营">国营</option>
						        <option value="外商独资">外商独资</option>
						        <option value="中外合资">中外合资</option>
						        <option value="私营企业">私营企业</option>
						        <option value="其他">其他</option>
					        </select>
					    </div>
					    <div class="input" style="display:none">
					        <label for="orgTrade">所属行业：</label>
					        <input class="form-control" type="text" id="orgTrade" name="orgTrade" style="width:400px"/>
					        
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