<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.common.ParamInitUtils"%>
<!DOCTYPE html>
<html>
<head>
    <title>设置项目排序</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
 	<link type="text/css" rel="stylesheet" href="../js/plugin/jquery-validate/css/validationEngine.jquery.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="../js/bootstrap/css/bootstrap.min.css">
</head>
<!-- END HEAD -->

<body class="login">
    <!-- BEGIN LOGIN -->
    <div class="content" style="padding:10px;">
  		<table id="userInfo" class="table table-bordered">
 			 
		</table>
  		
    </div>
    <!-- END LOGIN -->
    <!-- BEGIN COPYRIGHT -->
    <!-- END COPYRIGHT -->
    <script type="text/javascript" src="../js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="../js/plugin/query/jquery.query.js"></script>
    <script type="text/javascript" src="../js/plugin/jquery-validate/js/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="../js/plugin/jquery-validate/js/languages/jquery.validationEngine-zh_CN.js"></script>
    <script>
      jQuery(document).ready(function () {
      	loadUser();
      });
     
     function loadUser(){
     	var id = $.query.get("id");
     	if(id>0){
     		var url = "../admin/ProjectFetcher.do?action=getProject&id="+id;
     		
     		$.getJSON(url, function(json){
				var sb = [];
     			var order = json.order;
     			sb.push("<tbody>");
     			sb.push("<tr>");
     			sb.push("<td colSpan=\"2\" class=\"text-center\"><button  id=\"btnOK\" class=\"btn btn-primary\">设置排序</button> &nbsp;</td>");
     			
     			sb.push("</tr>");
     			
     			sb.push("<tr>");
     			sb.push("<td>首页排序：</td>");
     			sb.push("<td><select id=\"u-order\"><option value =\"0\">0</option><option value =\"1\">1</option><option value =\"2\">2</option><option value =\"3\">3</option><option value =\"4\">4</option><option value =\"5\">5</option><option value =\"6\">6</option><option value =\"7\">7</option><option value =\"8\">8</option></td>");
     			sb.push("</tr>");
     			
   				sb.push("<tr>");
       			sb.push("<td>项目名称：</td>");
       			sb.push("<td>"+json.name+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("<tr>");
       			sb.push("<td>创建时间：</td>");
       			sb.push("<td>"+json.created+"</td>");
       			sb.push("</tr>");
       			
       		 
       			
       			sb.push("<tr>");
       			sb.push("<td colSpan=\"2\">项目简介</td>");
       			
       			sb.push("</tr>");
       			sb.push("<tr>");
       			sb.push("<td colSpan=\"2\">"+json.summary+"</td>");
       			sb.push("</tr>");
       	 
       		
       			sb.push("<tr>");
       			sb.push("<td colSpan=\"2\">项目图片</td>");
       			
       			sb.push("</tr>");
       			sb.push("<tr>");
       			sb.push("<td colSpan=\"2\"><img src=\""+json.imgUrl+"\" alt=\"项目图片\" style=\"width:100%;\" /></td>");
       			sb.push("</tr>");
       			
       			sb.push("</tbody>");
       			
     			$("#userInfo").append(sb.join(''));
     			
     			if($("#u-order").length>0){
     				$("#u-order option[value='"+order+"']").attr("selected","selected");
     			}
     			
     			$("#btnOK").click(function(){
     				
     				var url = "../admin/ProjectFetcher.do?action=sortProject&id="+id+"&order="+$("#u-order").val();
     				 
     	    		$.get(url, function(data){
     	    			  window.parent.tldialog.closeRefresh();
     	    		});
     			});
     		});
     	}
     }

    </script>
   

</body>
<!-- END BODY -->
</html>