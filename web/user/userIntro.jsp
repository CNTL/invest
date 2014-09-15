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
    <title>完善信息</title>
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
        	完善信息
    </div>
    <!-- END LOGO -->
 
    <!-- BEGIN LOGIN -->
    <div class="content">
        <!-- BEGIN LOGIN FORM -->
        <form id="form" name="form" method="post" action="user.do?a=complete">
            <div class="form-group">
                <label for="intro">简介</label>
                <textarea style="width: 200px; height: 50px;" id="intro" name="intro" class="custform-textarea validate[required,maxSize[4000]]">
                </textarea>
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
 
    <script>
        jQuery(document).ready(function () {
        	$("#form").validationEngine({
    			autoPositionUpdate:true,
    			onValidationComplete:function(from,r){
    				if (r){
    					window.onbeforeunload = null;
    					$("#btnSave").attr("disabled", true);
    					$("#btnCancel").attr("btnCancel", true);
    					var pwd = checkpassword();
    					var pwdAgain = checkpassword_again();
    					if(pwd && pwdAgain){
    						from[0].submit();
    					}
    				}
    			}
    		});
        	
        	$("#code").removeAttr("readonly");
        	var value = "<%=valueArray %>";
        	if(value != null && value.length > 0){
        		var valueArray = value.split(",");
        		$("#id").val(valueArray[0]);
        		$("#name").val(valueArray[1]);
        		$("#code").val(valueArray[2]);
        		$("#code").attr("readonly", 'true');
        		$("#pwd").val(valueArray[3]);
        	}
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
        
        function checkpassword(){
			var password_temp = $("#pwd").val();
			 //密码限制6位以上
		    if(password_temp.length<6){
		    	$("#tip").text("密码长度不能少于6位");
		    	//document.getElementById('CUST_PASSWORD').placeholder='密码长度不能小于6位';
		    	return false;
		    } else if(password_temp.indexOf(" ")!=-1){
				$("#tip").text("密码不能包含空格");
				return false;
			} else{
				$("#tip").text("");
				return true;
			}
		}
		function checkpassword_again(){
			var pwd = $("#pwd").val();
			var passwordagain_temp = $("#pwdagain").val();
			//密码限制6位以上
			if(passwordagain_temp.length<6){
				$("#tip_again").text("密码长度不能少于6位");
				return false;
		    } else if(passwordagain_temp.indexOf(" ")!=-1){
				$("#tip_again").text("密码不能包含空格");
				return false;
			} else if (pwd != passwordagain_temp) {//加密验证fjc 1.首先判断两个密码是否一致 2.对密码进行加密
            	$("#tip_again").text("两次输入的密码不一致");
            	document.getElementById('pwdagain').value="";
				document.getElementById('pwdagain').placeholder='密码不一致';
				return false;
			} else {
				$("#tip_again").text("");
				return true;
			}
		}
    </script>
    <!-- END JAVASCRIPTS -->

</body>
<!-- END BODY -->
</html>