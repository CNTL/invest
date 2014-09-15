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
	<link rel="stylesheet" type="text/css" media="screen" href="../js/plugin/datatables/media/css/jquery.dataTables.css">
	<style>
	table.dataTable tbody tr.mySelected {
	  background-color: #b0bed9;
	}
	</style>
	<%@include file="inc/csslink.inc"%>
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
	                    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="true" data-widget-fullscreenbutton="true">
	                        <header>
	                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
	                            <h2>用户列表</h2>
	                        </header>
	                        <!-- widget div-->
	                        <div>
	                            <!-- widget edit box -->
	                            <div class="jarviswidget-editbox">
	                                <!-- This area used as dropdown edit box -->
	                            </div>
	                            <!-- end widget edit box -->
	                            <!-- widget content -->
	                            <div class="widget-body no-padding">
	                                <div class="widget-body-toolbar">
	                                	<div id="queryDiv">
		                                	用户名：<input type="text" id="name" name="name" value=""/>
											账号：<input type="text" id="code" name="code" value=""/>
											<span>
												<i class="fa fa-search" onclick="query()">查询</i>
											</span>
										</div>
										<div id="toolDiv">
											<a id="add" class="btn btn-default btn-sm DTTT_button_copy" onclick="openwindow('sysuserEdit.jsp','sysuserAdd',500, 600)">
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
	                                <table id="userlist" class="table table-striped table-hover">
	                                    <thead>
	                                        <tr>
	                                            <th>ID</th>
	                                            <th>头像</th>
	                                            <th>姓名</th>
	                                            <th>昵称</th>
	                                            <th>用户名</th>
	                                            <th>手机号</th>
	                                            <th>注册类型</th>
	                                            <th>地域</th>
	                                            <th>微信</th>
	                                            <th>微博</th>
	                                            <th>职业</th>
	                                            <th>email</th>
	                                            <th>身份证号</th>
	                                            <th>是否实名认证</th>
	                                            <th>&nbsp;&nbsp;&nbsp;&nbsp;创建时间&nbsp;&nbsp;&nbsp;&nbsp;</th>
	                                        </tr>
	                                    </thead>
	                                    
	                                    <tfoot>
											<tr>
												<th>ID</th>
	                                            <th>头像</th>
	                                            <th>姓名</th>
	                                            <th>昵称</th>
	                                            <th>用户名</th>
	                                            <th>手机号</th>
	                                            <th>注册类型</th>
	                                            <th>地域</th>
	                                            <th>微信</th>
	                                            <th>微博</th>
	                                            <th>职业</th>
	                                            <th>email</th>
	                                            <th>身份证号</th>
	                                            <th>是否实名认证</th>
	                                            <th>&nbsp;&nbsp;&nbsp;&nbsp;创建时间&nbsp;&nbsp;&nbsp;&nbsp;</th>
											</tr>
										</tfoot>
	                                </table>
	
	                            </div>
	                            <!-- end widget content -->
	                        </div>
	                        <!-- end widget div -->
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
    <!-- footer -->

        <!-- PAGE RELATED PLUGIN(S) -->
        <script src="../js/plugin/datatables/media/js/jquery.dataTables.js"></script>
		<script>
		
		$(document).ready(function() {
			query();
		});
		function update(){
			if(valueArray == null || valueArray.length == 0) {
				alert("请选择一条数据！");
			} else {
				openwindow('userEdit.jsp?valueArray=' + encodeURI(valueArray),'userEdit',500, 600);
			}
		}
		function query(){
			var $searchResult = $("#userlist");  
			$searchResult.dataTable().fnDestroy();  
		    var querys = new Array();
			var name = $("#name").val();
			if(name != null && name.length > 0){
				querys.push("name=" + name);
			}
			var code = $("#code").val();
			if(code != null && code.length > 0){
				querys.push("code=" + code);
			}
			//alert("querys=" + querys);
			var url = "../user/user.do?a=list";
			var aoColumns = [
	            { "data": "id" },
				{ "data": "head" },
				{ "data": "name" },
				{ "data": "nickName" },
				{ "data": "code" },
				{ "data": "phone" },
				{ "data": "type" },
				{ "data": "city" },
				{ "data": "wechat" },
				{ "data": "microblog" },
				{ "data": "job" },
				{ "data": "email" },
				{ "data": "identityCard" },
				{ "data": "isRealNameIdent" },
				{ "data": "createTimeStr" }
	        ];
			search("userlist", url, aoColumns, querys);
		}
		function deleteConfirm()  
		{  
			if(valueArray == null || valueArray.length == 0) {
				alert("请选择一条数据！");
			} else {
				if(confirm('确实要删除此用户吗?')){
					$.ajax('user.do?a=del', {  
				        dataType : 'json',  
				        data: {  
				        	id: valueArray[0]  
				        },  
				        success: function(data)  
				        {
			                if (data==true)  
			                {  
			                	query();
			                } else {  
			                    alert('删除发生错误，请联系管理员!');  
			                }  
			            },  
				        error: function(){  
		                   	alert('服务器无响应，请联系管理员!');  
		                }  
				    }); 
				}
			}
		}
		</script>

	
	</body>

</html>