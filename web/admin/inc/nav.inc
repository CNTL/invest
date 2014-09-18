<!-- Left panel : Navigation area -->
<!-- Note: This width of the aside area can be adjusted through LESS variables -->
<aside id="left-panel">

  <!-- User info -->
  <div class="login-info">
    <span>
      <!-- User image size is adjusted inside CSS, it should stay as it -->

      <a href="javascript:void(0);" id="show-shortcut">
        <img src="../img/avatars/sunny.png" alt="me" class="online" />
        <span>
          系统管理员
        </span>

      </a>

    </span>
  </div>
  <!-- end user info -->
<!-- NAVIGATION : This navigation is also responsive

			To make this navigation dynamic please make sure to link the node
			(the reference to the nav > ul) after page load. Or the navigation
			will not initialize.
			-->
		<nav id="main_nav">
      
      <ul>
        
        <li>
          <a href="#" id="group" title="用户分组">
            <i class="fa fa-lg fa-fw fa-key "></i>
            <span class="menu-item-parent">用户分组</span>
          </a>
          <ul>
            <li>
              <a id="group-name" href="sysParam.jsp?nav=user-name">分组名称</a>
            </li>
            <li>
              <a id="group-perm" href="sysRun.jsp?nav=group-perm">用户权限</a>
            </li>
          </ul>
        </li>
        
        <li>
          <a href="#" id="user" title="系统用户">
            <i class="fa fa-lg fa-fw fa-key "></i>
            <span class="menu-item-parent">系统用户</span>
          </a>
          <ul>
            <li>
              <a id="user-manager" href="sysParam.jsp?nav=user-manager">用户管理</a>
            </li>
          </ul>
        </li>
        
         <li>
          <a href="#" id="module" title="功能管理">
            <i class="fa fa-lg fa-fw fa-key "></i>
            <span class="menu-item-parent">功能管理</span>
          </a>
          <ul>
            <li>
              <a id="module-manager" href="sysParam.jsp?nav=module-manager">用户管理</a>
            </li>
          </ul>
        </li>
        
        <li>
          <a href="#" id="param" title="系统参数">
            <i class="fa fa-lg fa-fw fa-key "></i>
            <span class="menu-item-parent">系统参数</span>
          </a>
          <ul>
            <li>
              <a id="param-sys" href="sysParam.jsp?nav=param-sys">系统参数设置</a>
            </li>
          </ul>
        </li>
        
        <li>
          <a href="#" id="cat" title="分类管理">
            <i class="fa fa-lg fa-fw fa-key "></i>
            <span class="menu-item-parent">分类管理</span>
          </a>
          <ul>
            <li>
              <a id="cat-manager" href="sysParam.jsp?nav=cat-manager">分类管理</a>
            </li>
          </ul>
        </li>
        
      </ul>
		</nav>
			<span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>
		</aside>
		<!-- END NAVIGATION -->