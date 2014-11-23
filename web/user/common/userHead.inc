<%@page pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + 
	":" + request.getServerPort() + request.getContextPath() + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>合众映画</title>
<meta name="keywords" content="合众映画" />
<meta name="description" content="合众映画" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/index.css" />
<script type="text/javascript" src="<%=basePath %>static/js/common.js"></script>
<script type="text/javascript" src="<%=basePath %>user/common/userHead.js"></script>
<script type="text/javascript">
var rootPath = "<%=basePath %>";
</script>
<div class="header">
    <div class="logo">
        <a href="index.html" title="合众映画"></a>
    </div>
    <div class="nav">
        <ul>
        	<li><a href="<%=basePath %>index.html" class="current">首页</a></li>
            <li><a href="<%=basePath %>project.html">项目</a></li>
            <li><a href="<%=basePath %>user/recruit/recruitList.jsp">影聘</a></li>
            <li><a href="<%=basePath %>people.html">影人</a></li>
        </ul>
    </div>
    <div class="mini_search">
        <input type="text" id="k" name="k" class="text" value="搜索影片名称" />
        <input type="button" class="btn" value="" />
    </div>
    <!-- 
    <div class="mini_login">
        <a href="login.html">登录</a>
        <span>|</span>
        <a href="register.html">注册</a>
    </div>
     -->
    <div class="mini_member">
        <div class="avatar">
            <a href="#"><img src="<%=basePath %>static/image/temp/avatar1.png" /></a>
        </div>
        <div class="username">
            <a href="#">我是一个演员</a>
        </div>
        <div class="menu">
            <ul>
              	<li class="set"><a class="ie6fixpic" href="<%=basePath %>user/userSetting.jsp">个人设置</a></li>
              	<li class="msg"><a class="ie6fixpic" href="<%=basePath %>user/msg/msgSetting.jsp">消息中心<span class="msg-info"></span></a></li>
              	<li class="spo"><a class="ie6fixpic" href="">简历管理</a></li>
              	<li class="spo"><a class="ie6fixpic" href="">项目管理</a></li>
              	<li class="exit bn"><a class="ie6fixpic" href="<%=basePath %>user/logout.do">退出</a></li>
            </ul>
        </div>
    </div>
</div>