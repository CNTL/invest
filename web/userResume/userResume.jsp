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
<script type="text/javascript" src="script/userResume.js"></script>
</head>
<body>
<div class="page tr"></div>
<div class="message-box">
	<div id="message_all" class="message-item">
		<div>
			<ul class="clearfix">
        	</ul>
	        <div class="page tr">
	        </div>
     	 </div>
    </div>
</div>
<div id="msgDiv" style="display:none">
	<ul>
		<li class="talk-list">
			<div class="talk-face">
				<a class="userHead" href="/home/id-356405" target="_blank">
					<img src="http://zcstatic.wangxingroup.com/img/defaultavatar/noavatar_middle.gif">
				</a>
            </div>
            <div class="msg-box">
               	<div class="user-name">
               		<a class="msgTo" href="/home/id-356405" target="_blank"></a>
               		<span class="gray"></span>
               	</div>
               	<div class="msg-cnt"></div>
               	<div class="talk-handle tr">
	                <a class="msgNum" href="/msgDetail.jsp"></a>
	                <a class="Js-reply">回复</a>
	                <a class="delMsg">删除</a>
              	</div>
           	</div>
        </li>
	</ul>
</div>
<div class="content">
	<form class="setting-form" id="form" name="form" action="" onsubmit="return checkPic();" >
	    <div class="form-group form-item">
			<label>简历内容：</label>
			<textarea  id="content" name="content" style="width:400px;height:100px;" placeholder=""></textarea>
		</div>
	    <div class="form-group form-item">
            <label for="affix">简历附件：</label>
            <input type="file" id="affixF" name="affixF" class="form-control" />
            <input type="hidden" id="affix" name="affix" class="form-control" />
            <button id="btnSave" type="submit" class="btn blue uploadbtn">
	           	<i onclick="">上传</i>
	        </button>
               	<iframe name='hidden_frame' id="hidden_frame" style='display: none'></iframe>
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