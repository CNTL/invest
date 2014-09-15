<%@page pageEncoding="UTF-8"%>
<%@ include file="../include/Include.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>SmartAdmin v1.0 </title>
	<meta name="description" content="">
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<%@include file="inc/csslink.inc"%>
	
	<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/color.css">
</head>
<body class="">
	<!-- 头部 -->
	<%@include file="inc/header.inc"%>
	<!-- 头部 -->
		 
	<!-- 导航 -->
    <%@include file="inc/nav.inc"%>
    <!-- 导航 -->
         
    <!-- MAIN PANEL -->
	<div id="main" role="main">
		<%@include file="inc/ribbon.inc"%>
		<!-- MAIN CONTENT -->
		<div id="content">
	        <!-- widget grid -->
	        <section id="widget-grid" class="">
	            <!-- row -->
	            <div class="row">
	                <!-- NEW WIDGET START -->
	                <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	                    <!-- Widget ID (each widget will need unique ID)-->
	                   	<div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
	                        <header>
	                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
	                            <h2>用户列表</h2>
	                        </header>
	                     <table id="tb_sysuser" class="easyui-datagrid">
					        <thead>
					            <tr>
					                <th data-options="field:'ck',checkbox:true"></th>
					                <th data-options="field:'id',width:80,hidden:true">ID</th>
					                <th data-options="field:'username',width:100,align:'center'">用户名</th>
					                <th data-options="field:'code',width:80,align:'center'">账号</th>
					                <th data-options="field:'pwd',width:200,align:'center'">密码</th>
					                <th data-options="field:'createTimeStr',width:200,align:'center'">创建时间</th>
					            </tr>
					        </thead>
					    </table>
	                     
	                    </div>
	                    <!-- end widget -->
	                </article>
	                <!-- WIDGET END -->
	            </div>
	            <!-- end row -->
	        </section>
	        <!-- end widget grid -->
		</div>
		<!-- END MAIN CONTENT -->
	</div>
	<!-- END MAIN PANEL -->
	 <!-- script -->
    <%@include file="inc/script.inc"%>
    <!-- script -->
	<!-- footer -->
    <%@include file="inc/footer.inc"%>
    <script src="../js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="../js/jquery-easyui/jquery.easyui.utils.js" type="text/javascript"></script>
    
    <!-- footer -->
	<script type="text/javascript">
	$(document).ready(function() {
		pageSetUp();
		var tbuser =$('#tb_sysuser');
		var options = {
				width: '100%', 
				height:($(window).height()-146),
				url:'sysuser.do?a=loadData',
				 toolbar: [
				    
				    {
				    	id:'btnedit',
				 		text:'修改',
				 		iconCls:'icon-edit',
				 		handler:function(){
				 			 
				 			//var row = tbuser.datagrid('getChecked');
				 			//alert(row[0].id);
				 			
				 			var options = {
				 					 title: "修改系统用户",
				 					 url:"sysuserEdit.jsp",
				 		             width: 600 ,
				 		             height: 400,
				 		           	 closed: true,
				 		             loadingMessage: '正在加载数据，请稍等片刻......'
				 				}
				 			dialogUtils.init("nsysuser",options);
				 		}
	                    
	                }, {
	                	id:'btndel',
				 		text:'删除',
				 		iconCls:'icon-remove',
				 		handler:function(){
				 			alert("delete");
				 		}
	                }]
		};
		
		GridUtils.init(tbuser,options);
		//tbuser.datagrid('hideColumn', 'id');
	});
	
	/**
	* 同步监听窗口变化
	*/
	 function gridResize(width,height){
		 var tbuser =$('#tb_sysuser');
		
		 tbuser.datagrid('resize', {
		        width:'100%',
		        height:($(window).height()-50)
		    });

	 }
         
	</script>
	</body>
</html>