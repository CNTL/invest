<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html><head>
<meta charset="utf-8">
<title>影投网-中国最具影响力的影投平台</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="css/userCommon.css">
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="script/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="script/userJobEdit.js"></script>
</head>
<body>
<div class="content">
	<form class="setting-form" id="form" name="form" action="">
		<div class="form-group form-item">
	        <label for="jobName"><i style="color:red;">*</i>招聘主题：</label>
	        <input class="form-control validate[maxSize[255],required]" type="text" autocomplete="off" id="jobName" name="jobName" />
	    </div>
	    <div class="form-group form-item">
			<label for="province"><i style="color:red;">*</i>公司地点：</label>
			<div class="option-box">
				<select id="province" name="province" wx-validator-error-value="选择省份">
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
				<select id="city" name="city" wx-validator-error-value="请选择城市">
					<option>请选择城市</option>
				</select>
				<select id="area" name="area" wx-validator-error-value="请选择地区">
					<option>请选择地区</option>
				</select>
		        <span id="wx-validator-province-error" class="error-text hidden">请选择省份</span>
		        <span id="wx-validator-city-error" class="error-text hidden">请选择城市</span>
		        <span id="wx-validator-area-error" class="error-text hidden">请选择地区</span>
			</div>
		</div>
		<div class="form-group form-item">
	        <label for="jobNature"><i style="color:red;">*</i>公司性质：</label>
	        <input class="form-control validate[maxSize[255],required]" type="text" autocomplete="off" id="jobNature" name="jobNature" />
	    </div>
	    <div class="form-group form-item">
	        <label for="jobTrade">所属行业：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" autocomplete="off" id="jobTrade" name="jobTrade" />
	    </div>
	    <div class="form-group form-item">
	        <label for="jobScale"><i style="color:red;">*</i>公司规模：</label>
	        <div class="option-box">
		        <select id="jobScale" name="jobScale" class="custform-select validate[maxSize[255],required]" wx-validator-error-value="公司规模">
			        <option value="1">100人以下</option>
			        <option value="2">100-500人</option>
			        <option value="3">500-1000人</option>
			        <option value="4">1000-5000人</option>
			        <option value="4">5000人以上</option>
		        </select>
	        </div>
	    </div>
	    <div>
		    <h1>招聘内容</h1>
		    <script type="text/plain" id="jobContent" name="jobContent"></script>
		</div>
		<div class="form-group form-item">
	        <label for="jobCompany">公司全称：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" autocomplete="off" id="jobCompany" name="jobCompany" />
	    </div>
	    <div class="form-group form-item">
	        <label for="jobLinkman">联系人：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" autocomplete="off" id="jobLinkman" name="jobLinkman" />
	    </div>
	    <div class="form-group form-item">
	        <label for="jobLinkPhone">联系人电话：</label>
	        <input class="form-control validate[maxSize[255]]" type="text" autocomplete="off" id="jobLinkPhone" name="jobLinkPhone" />
	    </div>
	    <div class="form-group form-item">
	        <label for="jobLinkEmail">联系邮箱：</label>
	        <input class="form-control validate[maxSize[255],custom[email]]" type="text" autocomplete="off" id="jobLinkEmail" name="jobLinkEmail" />
	    </div>
		
		<div style="width:200px" align="left" class="form-actions">
	        <button id="btnSave" type="submit" class="btn blue pull-right">
	           	<i onclick="">保存</i>
	        </button>
	    </div>
	</form>
</div>
</body>
</html>