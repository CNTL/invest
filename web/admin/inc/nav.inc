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
          <a href="#" id="param" title="系统参数">
            <i class="fa fa-lg fa-fw fa-key "></i>
            <span class="menu-item-parent">系统参数</span>
          </a>
          <ul>
            <li>
              <a id="param-sys" href="sysParam.jsp?nav=param-sys">系统参数设置</a>
            </li>
            <li>
              <a id="param-run" href="sysRun.jsp?nav=param-run">系统运维</a>
            </li>
          </ul>
        </li>
        
        <li>
          <a href="#" id="user" title="用户管理">
            <i class="fa fa-lg fa-fw fa-user"></i>
            <span class="menu-item-parent">用户管理</span>
          </a>
          <ul>
            <li>
              <a id="user-sysuserlist" href="../admin/sysuserlist.jsp?nav=user-sysuserlist">系统用户管理</a>
            </li>
            <li>
              <a id="user-userlist" href="../admin/userlist.jsp?nav=user-userlist">用户管理</a>
            </li>
          </ul>
        </li>
        <li>
          <a href="#">
            <i class="fa fa-lg fa-fw fa-group"></i>
            <span class="menu-item-parent">组织机构</span>
          </a>
          <ul>
            <li>
              <a href="form-elements.html">组织机构管理</a>
            </li>
            <li>
              <a href="form-templates.html">角色管理</a>
            </li>
            <li>
              <a href="validation.html">权限管理</a>
            </li>

          </ul>
        </li>

        <li>
          <a href="#">
            <i class="fa fa-lg fa-fw fa-thumbs-up "></i>
            <span class="menu-item-parent">众筹管理</span>
          </a>
          <ul>
            <li>
              <a href="table.html">众筹类型</a>
            </li>
            <li>
              <a href="datatables.html">Data Tables</a>
            </li>
          </ul>
        </li>

        <li>
          <a href="#">
            <i class="fa fa-lg fa-fw fa-video-camera "></i>
            <span class="menu-item-parent">项目管理</span>
          </a>
          <ul>
            <li>
              <a href="table.html">项目类型</a>
            </li>
            <li>
              <a href="datatables.html">Data Tables</a>
            </li>
          </ul>
        </li>
      </ul>
		</nav>
			<span class="minifyme"> <i class="fa fa-arrow-circle-left hit"></i> </span>
		</aside>
		<!-- END NAVIGATION -->