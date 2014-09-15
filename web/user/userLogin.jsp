<%@page import="com.tl.kernel.context.Context"%>
<%@page import="com.tl.sys.sysuser.SysuserManager"%>
<%@page import="com.tl.common.StringUtils"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
 
    
    <link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
    <link href="../css/adminlogin.css" rel="stylesheet" type="text/css" />
    <link href="../css/components.css" rel="stylesheet" type="text/css" />
    <link href="../css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    

    <!-- END THEME STYLES -->
    <link rel="shortcut icon" href="../img/favicon/favicon.ico" type="image/x-icon">
    <link rel="icon" href="../img/favicon/favicon.ico" type="image/x-icon">
</head>
<!-- END HEAD -->

<body class="login">
    <!-- BEGIN LOGO -->
    <div class="logo">
        <a href="#">
            <img src="../img/logo.png" alt="" />
        </a>
    </div>
    <!-- END LOGO -->
 
    <!-- BEGIN LOGIN -->
    <div class="content">
        <!-- BEGIN LOGIN FORM -->
        <form class="login-form" action="" method="post">
            <h3 class="form-title">登录</h3>
            <div class="alert alert-danger alert-dismissible fade in  display-hide" role="alert">
				<button class="close"  data-dismiss="alert"></button>
				<span>请录入您的用户名和密码。 </span>
			</div>
            <div class="form-group">
                <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                <label class="control-label visible-ie8 visible-ie9">用户名</label>
                <div class="input-icon">
                    <i class="fa fa-user"></i>
                    <input class="form-control placeholder-no-fix" type="text" id="usercode" autocomplete="off" placeholder="请输入用户名" name="username" />
                </div>
            </div>
            <div class="form-group">
                <label class="control-label visible-ie8 visible-ie9">密码</label>
                <div class="input-icon">
                    <i class="fa fa-lock"></i>
                    <input class="form-control placeholder-no-fix" type="password" id="password" autocomplete="off" placeholder="请输入密码" name="password" />
                </div>
            </div>
            <div class="form-actions">
                <button id="register" type="button" class="btn blue pull-right">
                   	注册 <i class="m-icon-swapright m-icon-white"></i>
                </button>
                <button id="login" type="submit" class="btn blue pull-right">
                   	 登录 <i class="m-icon-swapright m-icon-white"></i>
                </button>
            </div>

        </form>
        <!-- END LOGIN FORM -->


    </div>
    <!-- END LOGIN -->
    <!-- BEGIN COPYRIGHT -->
    <div class="copyright">
        2014 &copy; 版权归影投网所有
    </div>
    <!-- END COPYRIGHT -->
    
    <script src="../js/jquery/jquery.min.js"></script>
	<script src="../js/bootstrap/js/bootstrap.min.js"></script>
    <script src="../js/plugin/jquery-validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="../js/plugin/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<script src="script/validate-login.js" type="text/javascript"></script>
	 
    <script>
        jQuery(document).ready(function () {
        	
        	Login.init();
        	
     		// init background slide images
            $.backstretch([
             "../img/loginbg/1.jpg",
             "../img/loginbg/2.jpg",
             "../img/loginbg/3.jpg",
             "../img/loginbg/4.jpg"
            ], {
                fade: 1000,
                duration: 8000
            }
         );
        });
    </script>
    <!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>