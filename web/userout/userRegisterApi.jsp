<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>设置关联账号-众筹网-中国最具影响力的众筹平台</title>
<meta name="keywords" content="众筹 创业 项目 投资 支持"/>
<meta name="description" content="在众筹网发布项目，获得投资支持，实现你的创业梦想"/>

<link rel="stylesheet" type="text/css" href="./css/userRelate.css" />
<link rel="stylesheet" type="text/css" href="./css/userRelate1.css" />
<script type="text/javascript" src="./css/userRelate.js"></script>
</head>
<body>
<div class="header clearfix" pbid="header">
    <div class="wrap">
        <div class="img-logo">
            <h1>
                <a alt="影投网" class="ie6fixpic" title="影投网" href="../index">影投网</a>
            </h1>
        </div>
        <div class="login-wrap">
		    <a href="userLogin.jsp">登录</a>
		    <em>|</em>
		    <a href="userRegister.jsp">注册</a>
	    </div>
                
    </div>
</div>
<script>
function user_do_api_register(data){
	if(data.status==1){
        _gaTrack('register','registerSuccess');
		wx.alert('注册成功,请登录吧',function(){
            location.href ='/userLogin';
        });
	}else if(data.status==0){
		wx.alert(data.info);
	}else{
		wx.alert(data.info);
	}
}
</script>
<div class="main">
    <div class="wrap">
    <!--signin static-->
    <div class="block-list">
        <div class="radius-hd">
            <div class="radius-hl common-sprite"></div>
            <div class="radius-hc w950"></div>
            <div class="radius-hr common-sprite"></div>
        </div>
        <div class="radius-bd w958">
            <div class="tit-wrap">
                <h3>设置关联账号</h3>
            </div>
            <div class="account-set clearfix">
                <div class="act-user">
                   <a href="http://t.qq.com/ljj328365450">
                   		<!-- 微博头像 -->
                   		<img src="" width="100" height="100">
                   </a>
                    <p><span class="red"></span>，欢迎您加入影投网</p>
                </div>
                <div class="act-info">
                    <div class="act-func">
                        <ul class="clearfix">
                            <a href="userRegisterWeibo.jsp"><li class="select"><span>创建新账号</span></li></a>
                            <a href="userLoginWeibo.jsp"><li class=""><span>关联已有账号</span></li></a>
                        </ul>
                    </div>
                    <form wx-validator wx-validator-ajax id="user_do_api_register" name="user_do_api_register" action="/user-ajax_register">
	                    <div class="act-bd">
	                        <div class="act-tit">完善以下资料，即可创建影投网账户</div>
	                        <div style="display:none;">
				        		<input type="text" id="id" name="id" value="0"/>
				        	</div>
				            <div class="form-group">
				                <label for="code"><i style="color:red;">*</i>登录账号</label>
				                <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入用户名" id="code" name="code" />
				            </div>
				            <div class="form-item clearfix">
	                            <label>昵称：</label>
	                            <input type="text" name="user_name" wx-validator-rule="required|byteRangeLength" wx-validator-param="|4-24|" value="${nickName == null ? '' : nickName}" wx-validator-user_name-byteRangeLength="2到12个汉字或4到24个字符"/>
	                        </div>
				            <div class="form-group">
				                <label for="name"><i style="color:red;">*</i>邮箱</label>
				                <input class="form-control validate[required]" type="text" autocomplete="off" placeholder="请输入邮箱" id="email" name="email" />
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
	                        <!-- <div class="form-reg">
	                            <input name="verifys" type="checkbox" checked>阅读并同意影投网的<a href="/help-term" target="_blank" class="red">《服务协议》</a>
	                            <span id="wx-validator-verifys-nocheck" class="error-text hidden">请阅读条款</span>
	                        </div> -->
	                        <input type="hidden" name="api" value="1">
	                        <div class="form-submit">
	                            <a type="submit" class="btn-base btn-red-h30 common-sprite" href="javascript:void(0);"><span class="common-sprite">完成</span></a>
	                        </div>
	                    </div>
	                    <div>
							<input type="text" id="openID" name="openID" value="${openID == null ? '' : openID}"/>
							<input type="text" id="weiboName" name="weiboName" value="${weiboName == null ? '' : weiboName}"/>
						</div>
                    </form>
                    <!--act-func static-->
                </div>
            </div>
        </div>
        <div class="radius-ft">
            <div class="radius-fl common-sprite"></div>
            <div class="radius-fc w950"></div>
            <div class="radius-fr common-sprite"></div>
        </div>
    </div>
    <!--signin end-->
    </div>
</div>
<script>
(function(){
    var vcode_leftTime = 60;
    var vcode_timer = null;
    function vcode_startRycleVbtn() {
        if (vcode_leftTime > 1) {
            vcode_leftTime--;
            $("#JS-vcodelabel").html('<em class="red">' + vcode_leftTime + '</em>秒后<br>重新发送');
            $("#JS-vcodelabel").show();
            $("#JS-vcodebtn").hide();
            vcode_timer = setTimeout(vcode_startRycleVbtn, 1000);
        } else {
            if (vcode_timer) clearTimeout(vcode_timer);
            vcode_leftTime = 60;
            $("#JS-vcodelabel").hide();
            $("#JS-vcodebtn").show();
        }
    }

    $("#JS-vcodebtn").bind("click", function(){
        var mobile = $.trim($("#user_do_api_register").find("input[name='mobile']").val());
        if(mobile == '' || !wx.validator.rule.mobile(mobile))
        {
            wx.alert("手机号格式不正确");
            return false;
        }
        var ajaxurl = '/user-ajax_registercode';
        var query = "mobile=" + mobile;
        vcode_startRycleVbtn();
        $.ajax({ 
            url: ajaxurl,
            dataType: "json",
            data:query,
            type: "POST",
            success: function(ajaxobj){
                if(ajaxobj.status==1)
                {
                    wx.alert("密码已经发出到手机，有效期5分钟，请迅速登录")
                }
                else
                {
                    vcode_leftTime = 0;
                    vcode_startRycleVbtn();
                    wx.alert(ajaxobj.info);
                }
            },
            error:function(ajaxobj)
            {
                vcode_leftTime = 0;
                vcode_startRycleVbtn();
                wx.alert("验证码发送失败，请重试");
            }
        });
    });
})();
function user_do_api_register_before(){
	if($("input[name='verifys']").is(":checked"))
		   return true;
	else
		wx.alert("请同意众筹网《服务协议》");
}
 $(function(){
    _gaTrack('register','preRegister');
 })
</script>
<div class="footer-login">
<p>&copy;2014   北京网信众筹网络科技有限公司   zhongchou.cn   版权所有&nbsp&nbsp&nbsp&nbsp&nbsp京公网安备11010502026064 | 京ICP备14016844号 </p>
</div>
<!--footer end-->
<div style="display:none;">
    <script type="text/javascript">
      var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
      document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fe89e365e0d0438aa7f6d6eab7960962c' type='text/javascript'%3E%3C/script%3E"));
    </script>
</div>
   </body> 
  <script>
    // (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    // (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    // m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    // })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
    // ga('create', 'UA-43592515-1', 'zhongchou.com');
    // ga('send', 'pageview');
    
    var _ncf={"prd":"zhongchou","pstr":"","pfunc":null,"pcon":"&nm=user&na=api_register","pck":{"beid":"beid","zcid":"zcid"}};
    (function(p,h,s){var o=document.createElement(h);o.src=s;p.appendChild(o)})(document.getElementsByTagName("HEAD")[0],"script","http://zcstatic.wangxingroup.com/js/ncfpb.1.1.min.js");

    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-43592515-1']);
    _gaq.push(['_trackPageview']);
    (function() {
      var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
      ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
      var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      $(ga).on('load',function(){
        if(_gaList && _gaList.length){
          var g = _gaq._getAsyncTracker();
          for(var i=0;i<_gaList.length;i++){
            g._trackEvent.apply(g,_gaList[i]);
            _gaList.remove(i);
          }
        }
      });
    })();
  </script>
<!--[if IE 6]>
<script src="static/js/DD_belatedPNG_0.0.8a.js"></script>
<![endif]-->
</html> 