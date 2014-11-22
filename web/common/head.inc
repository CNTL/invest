<%@page pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme() + 
	"://" + request.getServerName() + 
	":" + request.getServerPort() + 
	request.getContextPath() + "/";
%>
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
          <li><a href="../user/userSetting.jsp">首页 </a></li>
          <li><a href="/project">项目 </a></li>
          <li><a href="../user/recruit/recruitList.jsp">影聘 </a></li>
          <li><a href="/peoper">影人 </a></li>
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
              <li class="msg"><a class="ie6fixpic" href="<%=basePath %>user/msg/msgSetting.jsp">消息中心<span class="msg-info"></span></a></li>
              <li class="set"><a class="ie6fixpic" href="<%=basePath %>user/userSetting.jsp">个人设置</a></li>
              <li class="exit bn"><a class="ie6fixpic" href="<%=basePath %>user/logout.do">退出</a></li>
          </ul>
        </ul>
      </div>
    </div>
    <!-- user menu end-->

  </div>
</div>