<%@page pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + 
	":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
    <link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="./css/style.css">
  	 <script type="text/javascript">
		var rootURL = "<%=basePath %>";
	</script>
  	<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
    <script type="text/javascript" src="../js/plugin/backstretch/jquery.backstretch.min.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
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
	.register_radio{list-style:none; margin:0 0 20px 0; padding:0;}
	.register_radio li{width:148px; height:42px; font-size:18px; color:#555; text-align:center; line-height:42px;float:left; background:#fff; margin:15px 11px 0 0; border:2px solid #fafafa; cursor:pointer; position:relative;}
	.register_radio li:hover{border:2px solid #93b7bb;}
	.register_radio li.current{border:2px solid #93b7bb;}
	.register_radio li em{ background:url(./img/s_choose.png) no-repeat; width:31px; height:32px; position:absolute; right:-1px; top:-1px;}
	.register_radio li input[type="radio"]{cursor:pointer;position:absolute;left:-2px;top:-2px;margin:0;width:152px;height:46px;filter:alpha(Opacity=0);-moz-opacity:0;opacity: 0; }
	.register_radio input[type="radio"]{display:block;}
  	</style>
</head>
<!-- END HEAD -->

<body class="login">
	<div class="login_wrapper">
		<div class="login_box">
			<form id="form" action="" method="post">
	      		<ul class="register_radio clearfix">
		            <li class="current">
		            	个人<input type="radio" value="0" name="type" checked> 
		            </li>
		            <li class="">
		           	           机构<input type="radio" value="1" name="type"> 
		            </li>
		        </ul>
	            <input type="text" class="form-control validate[maxSize[255],required]" id="code" name="code" placeholder="请输入用户名"/>
                <input type="text" class="form-control validate[maxSize[255],required]" id="email" name="email" placeholder="请输入邮箱"/>
				<input type="password" class="form-control validate[maxSize[255],required]" onchange="checkpassword();" value="" id="password" name="password" placeholder="请输入密码">
				<input type="password" class="form-control validate[maxSize[255],required]" onchange="checkpassword_again();" value="" id="pwdagain" name="pwdagain" placeholder="请再次输入密码">
	            <input type="text" class="form-control validate[maxSize[255],required]" onchange="checkMyVal();" id="myVal" name="myVal"  placeholder="请输入校验码"/>
	            <img id="validateCode" src=""></img><a onclick="changeValPic();">换一张</a>
	            <input type="hidden" id="curVal" name="curVal" value=""/>
	            <br><br>
	            <input type="submit" id="login" value="注 &nbsp; &nbsp; 册">
	         </form>
	         <div class="login_right">
	         	<div>已有合众映画帐号</div>
	         	<a href="./userLogin.jsp" class="registor_now">直接登录</a>
	            <div class="login_others">使用以下帐号直接登录:</div>
			    <a id="wb_connect_btn" href="" target="_blank" class="icon_wb" title="使用新浪微博帐号登录"></a>
			    <a href="../user/userlogin.do?a=qqlogin" target="_blank" class="icon_qq" title="使用腾讯QQ帐号登录"></a>
	         </div>
	    </div>
    </div>
    <div class="login_box_btm"></div>
    <script type="text/javascript" src="script/userRegister.js"></script>
</body>
</html>