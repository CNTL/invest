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
     
     function loadUser(){
     	var id = $.query.get("id");
     	if(id>0){
     		var url = "../user/user.do?a=getUser&id="+id;
     		
     		$.getJSON(url, function(json){
     			
     			var sb = [];
     			var type = json.type;
     			var gender = "-1";
 				if(json.gender!=null&&json.gender!=""){
 					gender = json.gender;
 				}
     			var userType = "个人";
     			if(json.type==1){
     				userType = "机构";
     			}
     			sb.push("<tbody>");
     			if(json.isRealNameIdent==0){
     				sb.push("<tr>");
         			sb.push("<td colSpan=\"2\" class=\"text-center\"><button  id=\"btnOK\" class=\"btn btn-primary\">认证通过</button></td>");
         			
         			sb.push("</tr>");
     			}
     			else{
     				sb.push("<tr>");
         			sb.push("<td colSpan=\"2\" class=\"text-center\"><button  id=\"btnCancle\" class=\"btn btn-primary\">取消认证</button></td>");
         			
         			sb.push("</tr>");
     			}
     			
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
         			sb.push("<td>出生日期：</td>");
         			sb.push("<td><input id=\"u-birthdate\" type=\"text\" value=\""+json.birthdate+"\" />示例：1980-01-01</td>");
         			sb.push("</tr>");
         			
         			sb.push("<tr>");
         			sb.push("<td>性别：</td>");
         			sb.push("<td><select id=\"u-gender\"><option value =\"-1\">请选择性别</option><option value =\"1\">男</option><option value =\"0\">女</option></select></td>");
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
         			sb.push("<tr>");
         			sb.push("<td colSpan=\"2\">手持身份证照片</td>");
         			
         			sb.push("</tr>");
         			sb.push("<tr>");
         			sb.push("<td colSpan=\"2\"><img src=\"../"+json.organization+"\" alt=\"手持身份证照片\" style=\"width:100%;\" /></td>");
         			sb.push("</tr>");
         			
         			sb.push("<tr>");
         			sb.push("<td colSpan=\"2\">身份证正反面照片</td>");
         			
         			sb.push("</tr>");
         			sb.push("<tr>");
         			sb.push("<td colSpan=\"2\"><img src=\"../"+json.orgBusinessLicense+"\" alt=\"身份证正反面照片\" style=\"width:100%;\" /></td>");
         			
         			
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
         			sb.push("<td colSpan=\"2\"><img src=\"../"+json.organization+"\" alt=\"组织机构证件照\" style=\"width:100%;\" /></td>");
         			sb.push("</tr>");
         			
         			sb.push("<tr>");
         			sb.push("<td colSpan=\"2\">组织机构证件照</td>");
         			
         			sb.push("</tr>");
         			sb.push("<tr>");
         			sb.push("<td colSpan=\"2\"><img src=\"../"+json.orgBusinessLicense+"\" alt=\"组织机构证件照\" style=\"width:100%;\" /></td>");
         			
         			sb.push("</tr>");

         			sb.push("</tbody>");
     			}

     			$("#userInfo").append(sb.join(''));
     			
     			if($("#u-gender").length>0){
     				$("#u-gender option[value='"+gender+"']").attr("selected","selected");
     			}
     			
     			$("#btnOK").click(function(){
     				if(type == 0){
     					if($("#u-gender").length>0&&$("#u-gender").val()=="-1"){
         					alert("请设置性别。")
         					return;
         				}
         				if($("#u-birthdate").length>0){
         					if(checkDate($("#u-birthdate").val())==false){
         						alert("请设置出生日期。")
             					return;
         					}
         					
         				}
     				}
     				
     				
     				var url = "../user/user.do?a=checkUser&id="+id+"&birthdate="+$("#u-birthdate").val()+"&gender="+$("#u-gender").val()+"&check=1&type="+type;
     	    		$.get(url, function(data){
     	    			  window.parent.tldialog.closeRefresh();
     	    		});
     			});
     			
     			$("#btnCancle").click(function(){
     				if(type == 0){
     					if($("#u-gender").length>0&&$("#u-gender").val()=="-1"){
         					alert("请设置性别。")
         					return;
         				}
         				if($("#u-birthdate").length>0){
         					if(checkDate($("#u-birthdate").val())==false){
         						alert("请设置出生日期。")
             					return;
         					}
         					
         				}
     				}
     				
     				var url = "../user/user.do?a=checkUser&id="+id+"&birthdate="+$("#u-birthdate").val()+"&gender="+$("#u-gender").val()+"&check=0&type="+type;;
     	    		$.get(url, function(data){
     	    			  window.parent.tldialog.closeRefresh();
     	    		});
     			});
     			
     			
     			 
     		});
     	}
     }
     function checkDate(str){
    	 var dateRegEx = new RegExp(/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:0?[1-9]|1[0-2])(\/|-)(?:0?[1-9]|1\d|2[0-8]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(0?2(\/|-)29)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$/);
         if (dateRegEx.test(str)) {
             return true;
         }
         return false;
    	 
    } 
   
    </script>
   

</body>
<!-- END BODY -->
</html>