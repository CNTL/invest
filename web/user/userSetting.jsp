<%@page pageEncoding="UTF-8"%>
<!doctype html>
<html><head>
<meta charset="utf-8">
<title>影投网-中国最具影响力的影投平台</title>
<meta name="keywords" content="影投 创业 项目 投资 支持">
<meta name="description" content="在影投网发布项目，获得投资支持，实现你的创业梦想">
<link rel="stylesheet" type="text/css" href="./css/userSetting.css">
<link rel="stylesheet" type="text/css" href="css/userCenter.css">
<script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../js/plugin/backstretch/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="script/dc.js"></script>
<script type="text/javascript" src="script/ag.js"></script>
<script type="text/javascript" src="script/userSetting.js"></script>
<script src="script/ncfpb.1.1.min.js"></script>
</head>
<body>
<!--header static-->
<div class="header">
  <div class="wrap clearfix" pbid="header">
    <div class="img-logo">
          <h1>
        <a class="ie6fixpic" title="影投网" href="/index" alt="影投网">影投网</a>
      </h1>
        </div>
    <!--menu start-->
    <div class="menu">
      <ul class="clearfix">
          <li><a href="/index">首页 </a></li>
          <li><a href="/browse">浏览项目 </a></li>
          <li><a href="/open">开放平台 </a></li>
          <li><a href="/partake">新手帮助 </a></li>
          <li><a href="/project">发起项目 </a></li>
      </ul>
    </div>
    <!--menu end-->
    <!--search start-->
    <div class="search common-sprite ie6fixpic sw">
      <form id="header_new_search_form" method="post" action="/deals" wx-validator="" autocomplete="off">
      <input style="color: gray;" class="search-key gray" name="k" type="text" wx-validator-notip="" wx-validator-rule="required" wx-validator-placeholder="搜索">
      <input id="Js-search-submit" class="btn-search ie6fixpic" value="提交查询内容" type="submit">
      </form>
    </div>
    <!--search end-->

    <!-- user menu start-->
    <div id="jsddm" class="user-menu">
      <div class="menu-hd tx">
        <a class="tit" href="/home/id-328416">
          <span class="avatar-box">
            <img id="headeravatar" src="http://zcstatic.wangxingroup.com/img/defaultavatar/noavatar_small.gif">
                        <sup>有新消息</sup>
                      </span>
          <span class="user-name word-break ie6fixpic">雷娟</span>
        </a>
      </div>
      <div class="menu-bd JS-myinfo">
        <ul class="clearfix">
          <ul>
              <li class="sup"><a class="ie6fixpic" href="">支持的项目</a></li>
              <li class="spo"><a class="ie6fixpic" href="">发起的项目</a></li>
			  <li class="spo"><a class="ie6fixpic" href="">发起者后台</a></li>
              <li class="msg"><a class="ie6fixpic" href="">消息中心<span class="msg-info">1</span></a></li>
              <li class="set"><a class="ie6fixpic" href="">个人设置</a></li>
              <li class="exit bn"><a class="ie6fixpic" href="">退出</a></li>
          </ul>
        </ul>
      </div>
    </div>
    <!-- user menu end-->

  </div>
</div>
<div class="main clearfix">
	<div class="setting wrap">
		<div class="setting-title clearfix">
		<h3>个人设置</h3>
		<a href="">返回个人中心</a>
		</div>
		<div class="setting-content clearfix">
			<div class="setting-menu">
				<ul class="clearfix">
					<li class="select"><a id="infoEdit" class="icons info ie6fixpic" onclick="userset.change(this);">资料修改</a></li>
					<li><a id="pwsEdit" class="icons password ie6fixpic" onclick="userset.change(this);">密码修改</a></li>
					<li><a id="headEdit" class="icons portrait ie6fixpic" onclick="userset.change(this);">头像修改</a></li>
					<li><a id="relAuth" class="icons portrait ie6fixpic" onclick="userset.change(this);">实名认证</a></li>
				</ul>
			</div>
			<div class="setting-detail">
				<iframe id="main" style="width:100%;height:600px;border:0px;" src="userComplete.jsp"></iframe>  
			</div>
		</div>
	</div>
</div>
</body>
</html>