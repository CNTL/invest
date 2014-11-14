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
                <a alt="众筹网" class="ie6fixpic" title="众筹网" href="/index">众筹网</a>
            </h1>
        </div>
                <div class="login-wrap">
	      <a href="/user-login">登录</a>
	      <em>|</em>
	      <a href="/user-register">注册</a>
	    </div>
                
    </div>
</div>
<script>
function user_do_api_login(data){
    if(data.status==1){
        wx.alert('登录成功',function(){
            location.href ='/';
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
                   <a href="http://t.qq.com/ljj328365450"><img src="http://app.qlogo.cn/mbloghead/f1b166f95b1f51214e1e/100" width="100" height="100"></a>
                   <p><span class="red">ljj328365450</span>，欢迎您加入众筹网</p>
                </div>
                <div class="act-info">
                    <!--act-func static-->
                    <div class="act-func">
                        <ul class="clearfix">
                            <a href="userRegisterWeibo.jsp"><li><span>创建新账号</span></li></a>
                            <a href="userLoginWeibo.jsp"><li class="select"><span>关联已有账号</span></li></a>
                        </ul>
                    </div>
                    <form wx-validator wx-validator-ajax name="user_do_api_login" action="">
	                    <div class="act-bd">
	                        <div class="form-item clearfix">
	                            <label>用户名：</label>
	                            <input type="text" name="username" wx-validator-rule="required" type="text" wx-validator-placeholder="用户名/邮箱/手机号" value="" />
	                        </div>
	                        <div class="form-item clearfix">
	                            <label>密码：</label>
	                            <input type="password" name="user_pwd"  wx-validator-rule="required"  type="password" wx-validator-placeholder="请输入密码" />
	                        </div>
	                        <div class="form-reg">
	                            <input name="verify" type="checkbox" checked="checked">阅读并同意众筹网的<a href="/help-term" target="_blank" class="red">《服务协议》</a>
	                            <span id="wx-validator-verify-nocheck" class="red hidden">请阅读条款</span>
	                        </div>
	                        <input type="hidden" name="api" value="1">
	                        <input type="hidden" name="ajax" value="1">
	                        <div class="form-submit">
	                            <a type="submit" class="btn-base btn-red-h30 common-sprite" href="javascript:void(0);"><span class="common-sprite">完成</span></a>
	                        </div>
	                    </div>
	                    <div>
							<input type="text" id="openID" name="openID" value="${openID == null ? '' : openID}"/>
							<input type="text" id="weiboName" name="weiboName" value="${weiboName == null ? '' : weiboName}"/>
						</div>
                    </form>
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
function user_do_api_login_before(){
    if($("input[name='verify']").is(":checked"))
           return true;
    else
        wx.alert("请同意众筹网《服务协议》");
}
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
     <script type='text/javascript'>
    var _agt=_agt||[];
    _agt.push(['_atscu','AG_524833_SVCU']);
    _agt.push(['_atsdomain','zhongchou.cn']);
    (function(){
    var ag=document.createElement('script'); 
     ag.type='text/javascript'; 
     ag.async = true;
    ag.src=(document.location.protocol=='https:'?'https':'http')+'://'+'t.agrantsem.com/js/ag.js';
    var s=document.getElementsByTagName('script')[0]; 
     s.parentNode.insertBefore(ag,s);})();
  </script>
  </body> 
  <script>
    // (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    // (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    // m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    // })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
    // ga('create', 'UA-43592515-1', 'zhongchou.com');
    // ga('send', 'pageview');
    
    var _ncf={"prd":"zhongchou","pstr":"","pfunc":null,"pcon":"&nm=user&na=api_login","pck":{"beid":"beid","zcid":"zcid"}};
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