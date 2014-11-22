<%@page pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme() +  "://" + request.getServerName() + 
	":" + request.getServerPort() +  request.getContextPath() + "/";
%>
<!doctype html>
<html><head>
<meta charset="utf-8">
<title>影聘</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../user/css/userCommon.css">
<script type="text/javascript">
	var rootURL = "<%=basePath %>";
</script>
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="script/userVideoEdit.js"></script>
</head>
<body>
<div class="content">
	<h1>新增视频</h1>
	<form class="setting-form" id="form" name="form" action="">
		<div class="form-group form-item">
	        <label >视频名称：</label>
	        <input type="text" id="name" name="name" value="" class="validate[maxSize[255],required] ac_input"/>
	    </div>
	    <div class="form-group form-item">
	        <label >上传图片：</label>
	        <input type="file" id="photo" name="photo" value="" class="validate[maxSize[255],required] ac_input"/>
	    </div>
	    <div class="form-group form-item">
	        <label >视频地址：</label>
	        <input type="text" id="video" name="video" value="" class="validate[maxSize[255],required] ac_input"/>
	    </div>
	    <div class="form-group form-item">
	    	 <label >视频说明：</label>
			<textarea  id="intro" name="intro" class="form-control validate[required]" style="width:400px;height:100px;" placeholder="视频说明"></textarea>
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