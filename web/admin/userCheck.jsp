<%@page pageEncoding="UTF-8"%>
<%@page import="com.tl.common.ParamInitUtils"%>
<!DOCTYPE html>
<html>
<head>
    <title>认证用户</title>
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
        
        function postUser(){
        	var url = "sysuser.do?action=updateuser&id="+$("#id").val()+"&username="+$("#username").val()+"&code="+$("#code").val()+"&pwd="+$("#pwd").val()+"&email="+$("#email").val()+"&mobile="+$("#mobile").val();
        	$.get(url, function(data){
        		  if(data=="ok"){
        			  window.parent.tldialog.closeRefresh();
        		  }
        		});
        }
        function loadUser(){
        	var id = $.query.get("id");
        	if(id>0){
        		var url = "../user/user.do?a=getUser&id="+id;
        		
        		$.getJSON(url, function(json){
        			var sb = [];
        			var userType = "个人";
        			if(json.type==1){
        				userType = "机构";
        			}
        			sb.push("<tbody>");
        			
        			if(json.type==0){
        				sb.push("<tr>");
            			sb.push("<td>类型：</td>");
            			sb.push("<td>"+userType+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>编码：</td>");
            			sb.push("<td>"+json.code+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>姓名：</td>");
            			sb.push("<td>"+json.name+"</td>");
            			sb.push("</tr>");
            			
            			
            			
            			sb.push("<tr>");
            			sb.push("<td>昵称：</td>");
            			sb.push("<td>"+json.perNickName+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>身份证：</td>");
            			sb.push("<td>"+json.identityCard+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>电话：</td>");
            			sb.push("<td>"+json.perPhone+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>邮件：</td>");
            			sb.push("<td>"+json.email+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>地址：</td>");
            			sb.push("<td>"+json.perPostAddr+"</td>");
            			sb.push("</tr>");
            			
            			
            			sb.push("<tr>");
            			sb.push("<td>邮编：</td>");
            			sb.push("<td>"+json.perPostCode+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("</tbody>");
        			}else{
        				sb.push("<tr>");
            			sb.push("<td>类型：</td>");
            			sb.push("<td>"+userType+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>编码：</td>");
            			sb.push("<td>"+json.code+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>机构简称：</td>");
            			sb.push("<td>"+json.orgShortname+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>机构全称：</td>");
            			sb.push("<td>"+json.orgFullname+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td>法人代表：</td>");
            			sb.push("<td>"+json.name+"</td>");
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td colSpan=\"2\">组织机构证件照</td>");
            			
            			sb.push("</tr>");
            			sb.push("<tr>");
            			sb.push("<td colSpan=\"2\"><img src=\""+json.organization+"\" alt=\"组织机构证件照\" /></td>");
            			
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td colSpan=\"2\">组织机构证件照</td>");
            			
            			sb.push("</tr>");
            			sb.push("<tr>");
            			sb.push("<td colSpan=\"2\"><img src=\""+json.orgBusinessLicense+"\" alt=\"组织机构证件照\" /></td>");
            			
            			sb.push("</tr>");
            			
            			sb.push("<tr>");
            			sb.push("<td colSpan=\"2\"><button id=\"btnOK\" class=\"btn btn-primary\">认证通过</button></td>");
            			
            			sb.push("</tr>");
            			sb.push("</tbody>");
        			}

        			$("#userInfo").append(sb.join(''));
        			
        			 
        		});
        	}
        }
        
         
    </script>
   

</body>
<!-- END BODY -->
</html>