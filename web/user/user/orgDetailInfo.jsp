<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@include file="../inc/script.inc"%>
<%@include file="../inc/csslink.inc"%>
<script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../userout/script/userCommon.js"></script>
<script type="text/javascript" src="../proj/script/datas.js"></script>
<script type="text/javascript" src="../user/user/script/orgDetailInfo.js"></script>
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
					        <label id="lbcompany" for="orgFullname">公司全称：</label>
					        <input class="form-control validate[maxSize[255],required]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称"/>
					    </div>
					    <div class="input">
							<label id="area">招聘地点：</label>
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
							<label for="province">招聘地址：</label>
				            <input type="text" class="form-control input-lg" id="location" name="location" value="" placeholder="公司地址"/>
					        <input type="button" style="width:100px" id="mapSearch" value="从地图搜索">
				            <input type="hidden" class="form-control input-lg" id="coordinate" name="coordinate" value="" placeholder="公司地址坐标"/>
						</div>
					    <div class="input" id="propty">
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
					        <label for="orgScale" id="lborgScale">公司规模：</label>
					        <select id="orgScale" name="orgScale" class="custform-select validate[maxSize[255],required]" style="width:400px">
						        <option value="1">100人以下</option>
						        <option value="2">100-500人</option>
						        <option value="3">500-1000人</option>
						        <option value="4">1000-5000人</option>
						        <option value="5">5000人以上</option>
					        </select>
					    </div>
						<div class="input">
					        <label for="orgHomePage" id="lborgHomePage">公司主页：</label>
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

<!-- script -->

<!-- footer -->
<%@include file="../../inc/footer.inc"%>
<!-- footer -->

</body>
</html>