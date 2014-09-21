<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html><head>
<meta charset="utf-8">
<title>影投网-中国最具影响力的影投平台</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link type="text/css" rel="stylesheet" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/icon.css">
<style>
.form-item label { width:150px; float:left; height:28px; font-size:14px; line-height:28px; text-align:right; }
.form-item input { width:200px;}
.form-item .sex-box { width:180px; }
.form-item .sex-box input { width:auto; height:auto; padding:0; border:none; display:inline; margin:0 2px 0px 0px; float:none; }
.form-item .option-box select { width:100px; height:26px; line-height:26px; border:1px solid #9c9c9c; border-radius:3px; color:#555; margin-right:5px; }
.form-item input[type="text"]:focus, .form-item input[type="password"]:focus { border-color: #f5a9a6; box-shadow: 0 1px 4px rgba(255, 50, 50, 0.25) inset, 0 0 3px rgba(255, 50, 50, 0.25); }

</style>
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="script/userSetting.js"></script>
</head>
<body>
<div class="content">
	<form id="form" name="form" method="post" action="">
		<br><br>
		<div class="form-group form-item">
			<label for="code"><i style="color:red;">*</i>原始密码：</label>
			<input class="form-control validate[required]" type="password" value="" id="old_pwd" name="old_pwd"/>
		</div>
		<div class="form-group form-item">
			<label for="userpassword"><i style="color:red;">*</i>新密码：</label>
			<input type="password" class="form-control validate[required]" onchange="checkpassword()" value="" id="password" name="password">
		</div>
		<div class="form-group form-item">
			<label  for="passwordagain"><i style="color:red;">*</i>确认密码：</label>
			<input type="password" class="form-control validate[required]" onchange="checkpassword_again()" value="" id="pwdagain" name="pwdagain">
		</div>
		<div style="width:200px" align="left" class="form-actions">
	        <button id="btnSave" type="submit" class="btn blue pull-right">
	           	<i onclick="">保存</i>
	        </button>
	    </div>
		<!-- <div class="setting-submit">
			<a type="submit" class="btn-base btn-red-h30 common-sprite" href="javascript:;"><span class="common-sprite">保存</span></a>
		</div> -->
	</form>
</div>
<script type="text/javascript">
	jQuery(document).ready(function () {
		$("#form").validationEngine({
			autoPositionUpdate:true,
			onValidationComplete:function(from,r){
				if (r){
					window.onbeforeunload = null;
					$("#btnSave").attr("disabled", true);
					var password = pwdChange.checkpassword();
					var pwdAgain = pwdChange.checkpassword_again();
					if(password && pwdAgain){
						pwdChange.pwdSubmit();
					}
				}
			}
		});
	});
	var pwdChange = {
		pwdSubmit:function(){
			//1.旧密码是否正确，须校验 2.新密码校验 3.密码加密 4.写入数据库
			/*
			var oldpassword = $("#old_pwd").val();
			var loginPassWord = e5.utils.getCookie("loginPassWord");//取密码
			//alert("取到cookie密码"+wechatPassWord+"-取到cookie手机号"+wechatUserName);
			if(oldpassword!=loginPassWord){
				//帐户或密码错误
				//alert("您输入的旧密码错误！")
				var d = dialog({
					title: '消息',
					content: "您输入的旧密码错误！",
					okValue: '确 定',
					ok: function () {},
				});
				d.showModal();
			}
			*/
			//2.新密码是否一致
			var param = "&password=" + $("#password").val();
			var theURL = "user.do?a=pwdEdit" + param;
			//3.对密码进行加密,写入数据库
			$.ajax({
		        type:"POST", //请求方式  
		        url:theURL, //请求路径  
		        cache: false,     
		        dataType: 'text',   //返回值类型  
		        success:function(data){
		    		if(data != null && data == 'ok'){
		    			$.messager.confirm('消息', '修改密码成功，请重新登录?', function(r){
		    				if (r){
		    					parent.location.href = 'userLogin.jsp';
		    				}
		    			});
		    		}else {
		    			alert("修改密码失败:" + data);
		    		}
		        } ,
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					   alert(errorThrown);
				}
		    });
		},
		checkpassword : function(){
			var password_temp = $("#password").val();
			 //密码限制6位以上
		    if(password_temp.length<6){
		    	alert("密码长度不能少于6位");
		    	return false;
		    } else if(password_temp.indexOf(" ")!=-1){
		    	alert("密码不能包含空格");
				return false;
			} else{
				return true;
			}
		},
		checkpassword_again : function(){
			var password = $("#password").val();
			var passwordagain_temp = $("#pwdagain").val();
			//密码限制6位以上
			if(passwordagain_temp.length<6){
				alert("密码长度不能少于6位");
				return false;
		    } else if(passwordagain_temp.indexOf(" ")!=-1){
		    	alert("密码不能包含空格");
				return false;
			} else if (password != passwordagain_temp) {//加密验证fjc 1.首先判断两个密码是否一致 2.对密码进行加密
				alert("两次输入的密码不一致");
		    	document.getElementById('pwdagain').value="";
				document.getElementById('pwdagain').placeholder='密码不一致';
				return false;
			} else {
				$("#pwdagain").text("");
				return true;
			}
		}
	}
</script>
</body></html>