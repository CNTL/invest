<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.common.ParamInitUtils"%>
<%
	String valueArray = ParamInitUtils.getString(request.getParameter("valueArray"));
	//valueArray = java.net.URLDecoder.decode(valueArray,"UTF-8"); 
	if(valueArray != null && valueArray.length() > 0)
		valueArray = new String(request.getParameter("valueArray").getBytes("ISO-8859-1"),"UTF-8"); 
%>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    
 	<link type="text/css" rel="stylesheet" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
    <link href="../css/adminlogin.css" rel="stylesheet" type="text/css" />
    <link href="../css/components.css" rel="stylesheet" type="text/css" />
    
    <!-- END THEME STYLES -->
    <link rel="shortcut icon" href="../img/favicon/favicon.ico" type="image/x-icon">
    <link rel="icon" href="../img/favicon/favicon.ico" type="image/x-icon">
</head>
<!-- END HEAD -->

<body class="login">
    <!-- BEGIN LOGO -->
    <div class="logo">
        	用户注册
    </div>
    <!-- END LOGO -->
 
    <!-- BEGIN LOGIN -->
    <div class="content">
        <!-- BEGIN LOGIN FORM -->
        <form id="form" name="form" method="post" action="">
        	<div style="display:none;">
        		<input type="text" id="id" name="id" value="0"/>
        	</div>
            <div class="form-group">
                <label for="code"><i style="color:red;">*</i>登录账号</label>
                <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入用户名" id="code" name="code" />
            </div>
            <div class="form-group">
                <label for="name"><i style="color:red;">*</i>邮箱</label>
                <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入邮箱" id="email" name="email" />
            </div>
            <div class="form-group">
                <label for="type"><i style="color:red;">*</i>注册类型</label>
                <br>
                <select style="width: 300px;" id="type" name="type" class="custform-select validate[maxSize[255],required]" url="app/Data.do?type=USERTYPE" oldValue="-" pair="true">
                <option value="1">个人</option><option value="2">团体</option><option value="3">机构</option>  
                </select>
            </div>
            <div class="form-group">
				<label for="userpassword"><i style="color:red;">*</i>请输入密码</label>
				<input type="password" class="form-control validate[required]" onchange="checkpassword()" value="" id="password" name="password" placeholder="请输入密码">
				<label id="tip" class="tip" style="color:red;" value=""></label>
			</div>
			<div class="form-group">
				<label  for="passwordagain"><i style="color:red;">*</i>请再次输入密码</label>
				<input type="password" class="form-control validate[required]" onchange="checkpassword_again()" value="" id="pwdagain" name="pwdagain" placeholder="请再次输入密码">
			    <label  id="tip_again" class="tip_again" style="color:red;" value=""></label>
			</div>
			
            <div class="form-actions">
           		<button id="btnCancel" type="button" onclick="window.close();" class="btn blue pull-right">
                   	<i>关闭</i>
                </button>
                <button id="btnSave" type="submit" class="btn blue pull-right">
                   	<i onclick="">保存</i>
                </button>
            </div>

        </form>
        <!-- END LOGIN FORM -->


    </div>
    <!-- END LOGIN -->
    <!-- BEGIN COPYRIGHT -->
    <!-- END COPYRIGHT -->
    <script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="../js/plugin/backstretch/jquery.backstretch.min.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
 	<script type="text/javascript" src="script/userRegister.js"></script>
 
    <script>
        
    </script>
    <!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>