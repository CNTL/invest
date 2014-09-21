<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html><head>
<meta charset="utf-8">
<title>影投网-中国最具影响力的影投平台</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="./css/userSetting.css">
<link rel="stylesheet" type="text/css" href="css/userCenter.css">
<script type="text/javascript" src="script/dc.js"></script>
<script type="text/javascript" src="script/ag.js"></script>
<script type="text/javascript" src="script/userSetting.js"></script>
<script src="script/ncfpb.1.1.min.js"></script>
</head>
<body>
<form class="setting-form" name="modify" action="" wx-validator="" autocomplete="off" wx-validator-ajax="" wx-validator-error-class="error-text">
	<input name="user_id" value="328416" type="hidden">					
	<div class="form-item clearfix">
        <label class="red">登录账户：</label>
        <p class="form-msg red"></p>
    </div>
	<div class="form-item clearfix">
        <label for="name"><i style="color:red;">*</i>邮箱</label>
        <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入邮箱" id="email" name="email" />
    </div>
    <div class="form-item clearfix">
        <label for="name">昵称</label>
        <input class="form-control validate[required]" type="text" autocomplete="off" id="nickName" name="nickName" />
    </div>
    <div class="form-item clearfix">
        <label for="name">邮寄地址</label>
        <input class="form-control validate[required]" type="text" autocomplete="off" id="postAddr" name="postAddr" />
    </div>
	<div class="form-item clearfix">
		<label>简介：</label>
		<textarea id="intro" name="intro" placeholder="您的介绍可更有效的帮助支持者了解您和了解项目的背景"></textarea>
	</div>
	
	<div class="setting-submit">
		<a class="btn-base btn-red-h30 common-sprite" href="javascript:;" type="submit"><span class="common-sprite">保存</span></a>
	</div>
</form>
<script type="text/javascript">
	function modify(data){
		if(data.status==1){
			wx.alert("保存成功",function(){
				location.reload();
			});
		}
		if(data.status==0){
			wx.alert(data.info);
			
		}
	}

</script>

 
</body></html>