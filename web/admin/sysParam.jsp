<%@page pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" type="text/css" media="screen" href="../js/plugin/artdialog/css/ui-dialog.css">
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
	                    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
                            
                            <header>
                                <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                                <h2>系统参数</h2>

                            </header>

                            <!-- widget div-->
                            <div>
                                <!-- widget content -->
                                <div class="widget-body no-padding">
                                  <div class="widget-body-toolbar">
                                  	<div id="toolDiv">
											<a id="add" class="btn btn-default btn-sm DTTT_button_copy" onclick="openwindow('sysParamNew.jsp','新增参数','800px', '300px')">
												<span>新增</span>
											</a>
											<a id="update" class="btn btn-default btn-sm DTTT_button_copy" onclick="update();">
												<span>修改</span>
											</a>
											<a id="delete" class="btn btn-default btn-sm DTTT_button_copy" onclick="deleteConfirm();">
												<span>删除</span>
											</a>
										</div>
                                  </div>
									 <table id="dt_sysparam" class="table table-striped table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>参数编码</th>
                                                    <th>参数值</th>
                                                    <th>描述</th>
                                                </tr>
                                            </thead>
                                           
                                        </table>
                                     
                                </div>
                                <!-- end widget content -->

                            </div>
                            <!-- end widget div -->

                        </div>
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
    <!-- footer -->

	<script src="../js/plugin/datatables/media/js/jquery.dataTables.min.js"></script>
	<script src="../js/plugin/artdialog/jquery.artDialog.js?skin=black"></script>
	<script src="../js/plugin/artdialog/plugins/iframeTools.js"></script>
	<script type="text/javascript">
 
		$(document).ready(function() {
			
			pageSetUp();
			query();
		})
		
		function query(){
			var $dt_sysparam = $("#dt_sysparam"); 
			$dt_sysparam.dataTable().fnDestroy();  
			var querys = new Array();
			
			var url = "sysParam.do?action=page";
			var aoColumns = [
	            { "data": "id","sWidth":"40px" },
				{ "data": "keyname" ,"sWidth":"100px" },
				{ "data": "keyvalue" ,"sWidth":"200px" },
				{ "data": "detail" ,"sWidth":"*" },
	        ];
			var options = null;
			search("dt_sysparam", url, aoColumns, querys,options);
		}
		 

		</script>
</body>

</html>