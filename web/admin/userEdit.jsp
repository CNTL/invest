<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.common.ParamInitUtils"%>
<!DOCTYPE html>
<html>
<head>
    <title>新增用户</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
 	<link type="text/css" rel="stylesheet" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
</head>
<!-- END HEAD -->

<body class="login">
    <!-- BEGIN LOGIN -->
    <div class="content" style="padding:10px 50px 0 10px;">
        <!-- BEGIN LOGIN FORM -->
        <form id="form" name="form" method="post" action="sysuser.do?action=updateuser">
        	<div style="display:none;">
        		<input type="text" id="id" name="id" value="0"/>
        	</div>
        	<div class="form-group">
                <label><i style="color:red;">*</i>姓名</label>
                <div class="input-icon">
                    <input class="form-control validate[required]  placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入姓名" id="username" name="username" />
                </div>
            </div>
            <div class="form-group">
                <label for="userpassword"><i style="color:red;">*</i>用户名</label>
                <div class="input-icon">
                    <input class="form-control validate[required] placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入用户名" id="code" name="code" />
                </div>
            </div>
            <div class="form-group">
				<label  for="userpassword"><i style="color:red;">*</i>请输入密码</label>
				<input type="password" class="form-control validate[required]" onchange="checkpassword()" value="" id="pwd" name="pwd" placeholder="请输入密码">
				<label  id="tip" class="tip" style="color:red;" value=""></label>
			</div>
			<div class="form-group">
				<label  for="passwordagain"><i style="color:red;">*</i>请再次输入密码</label>
				<input type="password" class="form-control validate[required]" onchange="checkpassword_again()" value="" id="pwdagain" name="pwdagain" placeholder="请再次输入密码">
			    <label  id="tip_again" class="tip_again" style="color:red;" value=""></label>
			</div>
			<div class="form-group">
                <label>邮件</label>
                <div class="input-icon">
                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入邮件地址" id="email" name="email" />
                </div>
            </div>
            <div class="form-group">
                <label>手机</label>
                <div class="input-icon">
                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入手机" id="mobile" name="mobile" />
                </div>
            </div>
            <div class="form-actions text-center">
<!-- 				<button id="btnCloseRefresh" type="button" onclick="window.parent.tldialog.closeRefresh();" class="btn btn-primary"> -->
<!--                    	<i>关闭并刷新</i> -->
<!--                 </button> -->
           		<button id="btnCancel" type="button" onclick="window.parent.tldialog.close();" class="btn btn-primary">
                   	<i>关闭</i>
                </button>
                <button id="btnSave" type="submit" class="btn btn-primary" onclick="submitCheck();">
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
    <script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
	
 
    <script>
        jQuery(document).ready(function () {
        	loadUser();
        	$("#form").validationEngine({
    			autoPositionUpdate:true,
    			onValidationComplete:function(from,r){
    				if (r){
    					window.onbeforeunload = null;
    					$("#btnSave").attr("disabled", true);
    					$("#btnCancel").attr("btnCancel", true);
    					var pwd = checkpassword();
    					var pwdAgain = checkpassword_again();
    					var isEqual = submitCheck();
    					if(pwd && pwdAgain && isEqual){
    						postUser();
    					}
    				}
    			}
    		});
        	
        });
        
        function postUser(){
        	var url = "sysuser.do?action=updateuser&id="+$("#id").val()+"&username="+$("#username").val()+"&code="+$("#code").val()+"&pwd="+$("#pwd").val()+"&email="+$("#email").val()+"&mobile="+$("#mobile").val();
        	$.get(url, function(data){
        		  if(data=="ok"){
        			  window.parent.tldialog.closeRefresh();
        		  }
        		});
        }
        function loadUser(){
        	var id = $.query.get("id");
        	if(id>0){
        		var url = "sysuser.do?action=getuser&id="+id;
        		$.getJSON(url, function(json){
        			 $("#id").val(json.id);
        			 $("#username").val(json.username);
        			 $("#code").val(json.code);
        			 $("#pwd").val(json.pwd);
        			 $("#pwdagain").val(json.pwd);
        			 $("#email").val(json.email);
        			 $("#mobile").val(json.mobile);
        		});
        	}
        }
        
        function checkpassword(){
			var password_temp = $("#pwd").val();
			 //密码限制6位以上
		    if(password_temp.length<6){
		    	$("#tip").text("密码长度不能少于6位");
		    	 
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
			var passwordagain_temp = $("#pwdagain").val();
			//密码限制6位以上
			if(passwordagain_temp.length<6){
				$("#tip_again").text("密码长度不能少于6位");
				return false;
		    }else if(passwordagain_temp.indexOf(" ")!=-1){
				$("#tip_again").text("密码不能包含空格");
				return false;
			}
			else{
				$("#tip_again").text("");
				return true;
			}
		}
		
		function submitCheck(){
			var pwd = $("#pwd").val();
			var pwdagain = $("#pwdagain").val();
            if (pwd != pwdagain) {//加密验证fjc 1.首先判断两个密码是否一致 2.对密码进行加密
				$("#pwdagain").val("");
				$("#tip_again").html("密码不一致");
				return false;
			}else{
				return true;
			}
		}
    </script>
   

</body>
<!-- END BODY -->
</html>