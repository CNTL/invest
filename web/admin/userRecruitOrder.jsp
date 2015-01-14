<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.common.ParamInitUtils"%>
<!DOCTYPE html>
<html>
<head>
    <title>设置职位排序</title>
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
     		var url = "../user/recruit.do?a=getRecruit&id="+id;
     		
     		$.getJSON(url, function(json){
				var sb = [];
     			var order = json.jobOrder;
     			sb.push("<tbody>");
     			sb.push("<tr>");
     			sb.push("<td colSpan=\"2\" class=\"text-center\"><button  id=\"btnOK\" class=\"btn btn-primary\">设置排序</button> &nbsp;</td>");
     			
     			sb.push("</tr>");
     			
     			sb.push("<tr>");
     			sb.push("<td>首页排序：</td>");
     			sb.push("<td><select id=\"u-order\"><option value =\"0\">0</option><option value =\"1\">1</option><option value =\"2\">2</option><option value =\"3\">3</option><option value =\"4\">4</option><option value =\"5\">5</option><option value =\"6\">6</option><option value =\"7\">7</option><option value =\"8\">8</option></td>");
     			sb.push("</tr>");
     			
   				sb.push("<tr>");
       			sb.push("<td>职位：</td>");
       			sb.push("<td>"+json.jobName+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("<tr>");
       			sb.push("<td>薪水：</td>");
       			sb.push("<td>"+json.salary+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("<tr>");
       			sb.push("<td>工作要求：</td>");
       			sb.push("<td>"+json.working+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("<tr>");
       			sb.push("<td>职位诱惑：</td>");
       			sb.push("<td>"+json.jobAttract+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("<tr>");
       			sb.push("<td>联系人：</td>");
       			sb.push("<td>"+json.linkman+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("<tr>");
       			sb.push("<td>联系人手机：</td>");
       			sb.push("<td>"+json.linkPhone+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("<tr>");
       			sb.push("<td>联系人邮件：</td>");
       			sb.push("<td>"+json.linkEmail+"</td>");
       			sb.push("</tr>");
       		
       			sb.push("<tr>");
       			sb.push("<td colSpan=\"2\">职位描述</td>");
       			
       			sb.push("</tr>");
       			sb.push("<tr>");
       			sb.push("<td colSpan=\"2\">"+json.content+"</td>");
       			sb.push("</tr>");
       			
       			sb.push("</tbody>");
       			
     			$("#userInfo").append(sb.join(''));
     			
     			if($("#u-order").length>0){
     				$("#u-order option[value='"+order+"']").attr("selected","selected");
     			}
     			
     			$("#btnOK").click(function(){
     				
     				var url = "../user/recruit.do?a=orderRecruit&id="+id+"&order="+$("#u-order").val();
     				alert(url);
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