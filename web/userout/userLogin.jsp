<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <title>登录</title>
  	<link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
  	<link rel="stylesheet" type="text/css" href="./css/style.css">
  	<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
    <script type="text/javascript" src="../js/plugin/backstretch/jquery.backstretch.min.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script type="text/javascript" src="script/validate-login.js"></script>
	<!-- <script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101154766" data-redirecturi="http://leijuan1014.oicp.net/qc_callback.html" charset="utf-8"></script> -->
	<!-- <script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=3062596557&debug=true" type="text/javascript" charset="utf-8"></script> -->
	<style type="text/css">
  	.registor_now{color:#019875;background:url(./img/registor_now.png) right center no-repeat; padding-right:24px; float:left; height:30px;line-height:30px;}
	.registor_now:hover{color:#019875; text-decoration:underline;}
  	.icon_wb{width:54px; height:54px; float:left; background:url(./img/wbqq.png) left top no-repeat; margin:0 30px 0 10px;}
	.icon_qq{width:54px; height:54px; float:left; background:url(./img/wbqq.png) right top no-repeat;}
	.qqsina{height: 85px;background: #f2fffc; border-bottom-left-radius: 3px;border-bottom-right-radius: 3px;border-top: 1px dotted #e2ebe9;padding: 0 30px;padding-top: 25px;position: relative;}
	.qqsina div{font-size: 14px;display: inline-block;*zoom:1;*display:inline;position: absolute;left: 30px;top: 38px;}
	.qqsina a{position:absolute;background: url(./img/qqsina.png)  no-repeat;display: inline-block;width: 46px; height: 46px;*zoom:1;*display:inline;}
	.qqsina a.icon_wb{left: 225px; top:23px;}
  	</style>
 </head>
 <body >
 	<div class="login_wrapper">
    	<input type="hidden" id="resubmitToken" value="">		
		 <div class="login_box">
        	<form id="form" action="" method="post">
				<input class="inputStyle form-control validate[maxSize[255],required]" type="text" id="usercode" name="usercode" value="" tabindex="1" placeholder="请输入登录名">
			  	<input class="form-control validate[maxSize[255],required]" type="password" id="password" name="password" tabindex="2" placeholder="请输入密码">
			    <span class="error" style="display:none;" id="beError"></span>
			    <label class="fl" for="remember"><input type="checkbox" id="remember" value="" checked="checked" name="autoLogin"> 记住我</label>
			    <a href="./findPwdStep1.jsp" class="fr" target="_blank">忘记密码？</a>
			    <input type="submit" id="login" value="登 &nbsp; &nbsp; 录">
			    <input type="hidden" id="callback" name="callback" value="">
                <input type="hidden" id="authType" name="authType" value="">
                <input type="hidden" id="signature" name="signature" value="">
                <input type="hidden" id="timestamp" name="timestamp" value="">
			</form>
			<div class="login_right">
				<div>还没有合众映画帐号？</div>
				<a href="userRegister.jsp" class="registor_now">立即注册</a>
			    <div class="login_others">使用以下帐号直接登录:</div>
			    <a id="wb_connect_btn" href="" target="_blank" class="icon_wb" title="使用新浪微博帐号登录"></a>
			    <a href="../user/userlogin.do?a=qqlogin" target="_blank" class="icon_qq" title="使用腾讯QQ帐号登录"></a>
			</div>
        </div>
        <div class="login_box_btm"></div>
    </div>
 </body>
</html>