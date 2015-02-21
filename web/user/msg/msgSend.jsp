<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../inc/meta.inc"%>
    <link rel="stylesheet" type="text/css" media="screen" href="<c:out value="${rootPath}"/>js/bootstrap/css/bootstrap.min.css">
	<script type="text/javascript" src="../js/layer/layer.min.js"></script>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var menu = "<c:out value="${menu}"/>";
		$(function(){
			
			loadMsg();
			 
		});
		function loadMsg(){
			$.getJSON("../user/msg.do?a=getMyMsgs", function(json){
				  var tb = $("#msgtable");
				  var sb = [];
				  $.each(json,function(i,n){
					  
					  var msguserid ;
					  var isread = "对方未读";
					  if(n.msg_isread=="1"){
						  isread = "对方已读";
						 
					  }
  					  sb.push("<tr>");
  					  var isme = false;
  					  if($("#userID").val().toString() == n.msg_fromID ){
  						  isme = true;
  					  }
  					  if(isme){
  						  msguserid = n.msg_toID;
  						  sb.push("<td> <a href=\"<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&mainType=4&id="+n.msg_toID+"\" target=\"_blank\">");
  						  sb.push("<img class=\"img-circle\" style=\"width: 60px;height:60px;\" src=\"../"+n.msg_toHead+"\" > </br>");
  						  sb.push("<span style=\"margin-left:15px;\">"+n.msg_to+"</span>");
  						  sb.push("</a></td>");
  					  }
  					  else{
  						msguserid = n.msg_fromID;
 						sb.push("<td> <a href=\"<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&mainType=4&id="+n.msg_fromID+"\" target=\"_blank\">");
  					    sb.push("<img class=\"img-circle\" style=\"width: 60px;height:60px;\" src=\"../"+n.msg_fromHead+"\" > </br>");
  					    sb.push("<span style=\"margin-left:15px;\">"+n.msg_from+"</span>");
  					    sb.push("</a></td>");
  					  }
					  
					  sb.push("<td>");
					  sb.push("<a href=\"MsgDetailMa.do?msguserid="+msguserid+"\">"+n.msg_content+"</a>");
					  if(isme){
						  sb.push("<span style=\"color: rgb(153, 153, 153); font-size: 12px; margin-left: 5px;\">["+isread+"]</span>");
					  }
					  else{
						  if(n.msg_isread=="0"){
							  sb.push("<span style=\"color: red; font-size: 12px; margin-left: 5px;\">[未读]</span>");
						  }
					  }
					 
					  sb.push(" </br>");
					  sb.push(" <small>"+n.msg_createTime+"</small>");
					  sb.push("</td>");
					 
					  sb.push("<td>");
					  if(!isme&&n.msg_isread=="0"){
						  sb.push(" <a class=\"muted\" href=\"javascript:readmsg("+msguserid+")\">标记已读</a>|");
					  }
					  sb.push(" <a class=\"muted\" href=\"MsgDetailMa.do?msguserid="+msguserid+"\">回复</a>|");
					  sb.push(" <a class=\"muted\" href=\"javascript:delmsg("+n.id+")\">删除</a>	");
					 
					 
					  sb.push("</td>");
					  
					  sb.push("</tr>");
				  });
				  
				  tb.find("tbody").empty();
				  tb.find("tbody").append(sb.join(""));
				});
		}
		 
		function delmsg(id){
			$.get("../user/msg.do?a=delMsg&id="+id, function(data){
				   if(data=="ok"){
					   
					   window.location.reload();
				   }
				});
		}
		function readmsg(id){
			 
			$.get("../user/msg.do?a=readMsg&userID="+id, function(data){
				   if(data=="ok"){
					  
					   window.location.reload();
					  
				   }
				});
		}
		function readMsgAll(){
			$.get("../user/msg.do?a=readMsgAll", function(data){
				   if(data=="ok"){
					   window.location.reload();
					    
				   }
				});
		}
	</script>
	<style>
	th{
		font-size:14px;
		line-height:20px;
	}
	td{
		font-size:14px;
		line-height:20px;
		text-align:left;
	}
	.table{
		width:100%;
		border-spacing: 2px;
		border-color: gray
	}
	.table td.span2, .table th.span2 {
	float: none;
	width: 124px;
	margin-left: 0px;
	}
	.table th {
		font-weight: bold;
	}
	.table thead th {
		vertical-align: bottom;
	}
	.table td.span6, .table th.span6 {
		float: none;
		width: 444px;
		margin-left: 0px;
	}
	table td[class*='span'], table th[class*='span'], .row-fluid table td[class*='span'], .row-fluid table th[class*='span'] {
		display: table-cell;
		float: none;
		margin-left: 0px;
	}
	</style>
	 
</head>
<body>
	<%@include file="../../inc/header.inc"%>
	
	<div class="people_globaltop">
           <div class="wrap">
<!--             <a href="../org/BasicInfo.do?infoType=1&mainType=1" class="profile">个人设置</a> -->
            <div class="avavtar">
                <img style="border-radius: 50%;" src="<c:out value="${loginUser.head}"/>" />
            </div>
            <div class="info">
                <h2><c:out value="${loginUser.perNickName}"/> 
                
					<span>
						<c:choose>
							<c:when test="${loginUser.type==0}">个人</c:when>
							<c:when test="${loginUser.type==2}">机构</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose>
					</span>
					<span>
	                  	<c:choose>
							<c:when test="${loginUser.isRealNameIdent==1}">已认证</c:when>
							<c:otherwise>未认证</c:otherwise>
					 	</c:choose>
					 	</span>
				 </h2>
                <div class="desc">
                   <div> <c:out value="${loginUser.perJobName}"/>、<c:out value="${cityname}"/></div>
                   
                  <br />
                    
                </div>
            </div>
            <div class="clear"></div>
            <ul class="nav">
				<li style="font-size:28px;line-height:36px;">消息中心</li>
            </ul>
		</div>
    </div>
			
	<div class="people_profile">
		 
		<div class="content" style="float:left;width:100%;">
		
		 
		<table class="table" id="msgtable">
			<thead>
				<th style="width:200px;">联系人</th>
				<th style="width:800px;">内容&nbsp;&nbsp;&nbsp;&nbsp;<a class="muted" href="javascript:readMsgAll()">全部标记为已读</a></th>
				<th style="width:250px;">状态</th>
				 
			</thead>
			<tbody>
			</tbody>
		</table>
			 
		</div>
		<div class="clear"></div>
		<div class="pager">
			分页
		</div>
	</div>
	
	<%@include file="../../inc/footer.inc"%>
</body>
</html>