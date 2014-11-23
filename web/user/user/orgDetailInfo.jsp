<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>合众映画</title>
<meta charset="utf-8">
<meta name="keywords" content="合众映画" />
<meta name="description" content="合众映画" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" type="text/css" href="../../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../../js/jquery-easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="../../static/css/reset.css" />
<link rel="stylesheet" type="text/css" href="../../static/css/index.css" />
<link rel="stylesheet" type="text/css" href="../css/userCommon.css"/>
<script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=6eea93095ae93db2c77be9ac910ff311"></script>
<script type="text/javascript" src="script/orgDetailInfo.js"></script>
</head>
<body>
<div class="job_add">
	<form class="setting-form" id="form" name="form" action="">
	    <div class="input">
	        <label for="orgFullname">机构全称：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="orgFullname" name="orgFullname" placeholder="机构全称"/>
	    </div>
	    <div class="input">
	        <label for="orgAddress">公司地点：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="orgAddress" name="orgAddress" onclick="getMap();" placeholder="公司地点"/>
	    </div>
	    <div class="input">
	        <label for="orgNature">公司性质：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="orgNature" name="orgNature" placeholder="公司性质"/>
	    </div>
	    <div class="input">
	        <label for="orgTrade">所属行业：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="orgTrade" name="orgTrade" placeholder="所属行业"/>
	    </div>
	    <div class="input">
	        <label for="orgScale">公司规模：</label>
	        <select id="orgScale" name="orgScale" class="custform-select validate[maxSize[255],required]" placeholder="公司规模">
		        <option value="1">导演</option>
		        <option value="2">演员</option>
		        <option value="3">摄影</option>
		        <option value="4">后期</option>
	        </select>
	    </div>
		<div class="input">
	        <label for="orgHomePage">公司主页：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" id="orgHomePage" name="orgHomePage" placeholder="公司主页"/>
	    </div>
	    <div class="btn">
        	<input type="submit" id="btnSave" value="提交信息">
        </div>
	</form>
	<div style="display:none" style="width:680;height:490">
		<iframe id="msgMap" src="../common/MsgMap.jsp" width="670" align="center" height="480"></iframe>
	</div>
</div>
</body>
</html>