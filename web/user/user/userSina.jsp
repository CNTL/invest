<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>Document</title>
 </head>
 <body>
 	<script>
 	var weiboInit = function(){//微博登录初始化
		WB2.anyWhere(function(W){
            W.widget.connectButton({
                id: "wb_connect_btn",	
                type:"3,2",
                callback : {
                    login:function(o){	//登录后的回调函数
                        // alert("login: " + o.screen_name)
                        weiboAfterLogin(o.screen_name);
                    },	
                    logout:function(){	//退出后的回调函数
        				//alert('logout');
                    }
                }
            });
        });
	},
	weiboAfterLogin = function(weibo){
		var keyUrl = "/user/afterlogin.do?a=weiboAfterLogin&weibo=" + weibo;
		$.get(keyUrl, function(data) {
		});
	};
 	</script>
 </body>
</html>