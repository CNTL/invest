
<!DOCTYPE HTML>
<html>
<head>
<!-- <script id="allmobilize" charset="utf-8" src="http://www.lagou.com/js/third/allmobilize.min.js"></script> -->
<script id="allmobilize" charset="utf-8" src="http://a.yunshipei.com/ef48a3377914af6ef846830fcae2a8e6/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld" href="#" />
<!-- end 云适配 -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>找回密码-拉勾网-最专业的互联网招聘平台</title>
<meta property="qc:admins" content="23635710066417756375" />
<meta content="拉勾网是最权威的互联网行业招聘网站,提供全国真实的互联网招聘信息,工资不面议当面谈,找工作,招聘网,寻人才就来拉勾网,互联网行业找工作首先拉勾网" name="description">
<meta content="互联网招聘,找工作,招聘网,人才网" name="keywords">

<meta name="baidu-site-verification" content="QIQ6KC1oZ6" />

<!-- <div class="web_root"  style="display:none">http://www.lagou.com</div> -->
<script type="text/javascript">
var ctx = "http://www.lagou.com";
var rctx = "http://hr.lagou.com";
</script><link rel="Shortcut Icon" href="http://www.lagou.com/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="http://www.lagou.com/css/style.css?v=1.5.5.6_1022"/>

<script src="http://www.lagou.com/js/libs/jquery.1.10.1.min.js?v=1.5.5.6_1022" type="text/javascript"></script>

<script type="text/javascript" src="http://www.lagou.com/js/libs/jquery.lib.min.js?v=1.5.5.6_1022"></script>
<script type="text/javascript" src="http://www.lagou.com/js/core.min.js?v=1.5.5.6_1022"></script>
<script type="text/javascript" src="http://www.lagou.com/js/libs/analytics.js?v=1.5.5.6_1022"></script>
<script type="text/javascript" src="http://www.lagou.com/js/libs/tongji.js?v=1.5.5.6_1022"></script>
<script type="text/javascript" src="http://www.lagou.com/js/additional-methods.js?v=1.5.5.6_1022"></script>
<script type="text/javascript">
var youdao_conv_id = 271546; 
</script> 
<script type="text/javascript" src="http://conv.youdao.com/pub/conv.js"></script>
</head>

<body id="login_bg">
	<div class="login_wrapper">
    	<input type="hidden" id="resubmitToken" value="" />
        <div class="find_psw">
        	<img src="../img/psw_step3.png" width="369" height="56" alt="找回密码第三步" />
            <div class="c9 email_address">
            	<span>邮件地址：<a>${user.email == null ? "" : user.email }</a></span>
            </div>
            <form id="pswForm">
            	<input type="password" name="new_password" id="new_password" tabindex="1" placeholder="请输入新密码" />
              	<input type="password" name="confirm_password" id="confirm_password" tabindex="2" placeholder="请再次输入新密码" />
                <span id="beError" class="error" style="display:none;"></span>
                <input type="hidden" name="userId" value='${user.id == null ? "0" : user.id }' />
                <input type="hidden" name="oldpwd" value='${user.password == null ? "0" : user.password }' />
                <input type="submit" id="submitLogin" value="确 &nbsp; &nbsp; 定" />
                <span id="tip" class="dn">密码重置成功！</span>
            </form>
        </div>
    </div>
    
<script type="text/javascript">
    $(document).ready(function() {
    	//验证表单
	   
	    	 $("#pswForm").validate({
	    	        rules: {
	    	        	new_password: {
			    	    	required: true,
			    	    	rangelength: [6,16]
			    	   	},
			    	   	confirm_password: {
			    	    	required: true,
			    	   	    equalTo: "#new_password"
			    	   	}
			    	},
			    	messages: {
			    		new_password: {
			    	    	required: "请输入新密码",
			    	    	/* rangelength: "请输入6-16位密码，字母区分大小写" */
			    	    	rangelength: "请输入6-16位新密码，字母区分大小写"
			    	   	},
			    	   	confirm_password: {
			    	    	required: "请再次输入新密码",
			    	    	equalTo: "两次输入的密码不一致，请重新输入"
			    	   	}
			    	},
			    	submitHandler:function(form){ 
			    		var new_password =$('#new_password').val();
			    		var confirm_password =$('#confirm_password').val();
			    		var userId = '036c216dd0afe62afcf0c61d38830ac96e13b56987f3e6c60f459282c3a815a2a2b6c1ac43dd60f16630caa0f21b6b9ffa941fd596c8d7bebadc5a24e2778eebbe8126ecf95c5aba0092b8e9c25a0e89';
			    		var oldpwd = '164eedac3d5ed47571b709431141aeddb3ed52a07b9717a7e24a470e08a137b44187f70faa0658d3616736d87df0962287b96163f0790b477f13299ba89dbeeed64fa90bf57d558663e6f41887fde659';
			    		$(form).find(":submit").attr("disabled", true);
			            $.ajax({
			            	type:'POST',
			            	data: {
		            			newpwd:new_password,
		            			newrepwd:confirm_password,
		            			userId:userId,
		            			oldpwd:oldpwd
			            	},
			            	url:ctx+'/user/rp.json'
			            }).done(function(result) {
			            	if(result.success){
			            		$('#tip').show();
			            		setTimeout("skip()",3000);
			            	}else{
								$('#beError').text(result.msg).show();
			            	}
			            	$(form).find(":submit").attr("disabled", false);
			            });
			        }  
	    	    });
	    	});
    
    function skip(){
    	window.location.href=ctx+'/';
    } 
</script>
</body>
</html>
    
</body>
</html>