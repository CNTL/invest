<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../inc/meta.inc"%>
    <link rel="stylesheet" type="text/css" media="screen" href="../js/plugin/jquery-autocomplete/jquery.autocomplete.css">
	<script type="text/javascript" src="../js/plugin/jquery-autocomplete/jquery.autocomplete.js"></script>
	
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var menu = "<c:out value="${menu}"/>";
		var pageIndex = 1;
		$(function(){
			$.get("../user/msg.do?a=getMyMsgsCount", function(data){
				   $("#pageAll").text(data);
				});
			$("#pageCurrent").text(pageIndex);
			loadMsg();
			autouser();
			
			$("#pageNext").click(function(){
				var pageall = parseInt($("#pageAll").text(),10);
				var maxPage = 10;//Math.ceil(pageall/10);
				if(pageIndex<maxPage){
					pageIndex = pageIndex+1;
					$("#pageCurrent").trigger("click");
					loadMsg();
				}
			});
			
			$("#pagePre").click(function(){
				if(pageIndex>1){
					pageIndex = pageIndex-1;
					$("#pageCurrent").trigger("click");
					loadMsg();
				}
			});
			
			$("#pageCurrent").click(function(){
				$(this).text(pageIndex);
			});
		 
			$("#btnsendmsg").click(function(){
				if($("#btnsendmsg").attr("url")&&$("#username").val()!=""){
					window.location.href = $("#btnsendmsg").attr("url");
				}
				else{
					$.messager.alert("请选择用户。");
				}
			});
		});
		function autouser(){
			$("#username").autocomplete("../user/userFetch.do?a=find", {
			    minChars: 1,
			    width: 310,
			    dataType:"json",
			    mustMatch: true,
			    matchContains: true,
			    autoFill: false,
			    parse:function(data){
			    	return $.map(data, function(row) {
		                return {
		                    data: row,
		                    value: row.name,
		                    result: row.name
		                }
		            });

			    },
			    formatItem: function(row, i, max) {
			        
			        var sb = [];
			        sb.push("<table>");
			        sb.push("<tr>");
			        sb.push("<td style=\"width:60px;height:60px;padding:0;margin:0;\">");
			        sb.push("<img class=\"img-circle\" style=\"width:60px;height:60px;\" src=\""+webroot+row.head+"\" alt=\"\" />");
			        sb.push("</td>");
			        sb.push("<td style=\"padding:5px;\">");
			        sb.push(row.name);
			        sb.push("</td>");
			        sb.push("</tr>");
			        sb.push("</table>");
			        return sb.join("");
			    },
			    formatMatch: function(row, i, max) {
			        return row.name + " " + row.code;
			    },
			    formatResult: function(row) {
			        return row.name;

			    }
			}).result(function(event, row, formatted) {
                
                $("#btnsendmsg").attr("url","../user/MsgDetailMa.do?msguserid="+row.id);
            });
		}
		function loadMsg(){
			
			$.getJSON("../user/msg.do?a=getMyMsgs&pageIndex="+pageIndex, function(json){
				  var tb = $("#msgtable");
				  var sb = [];
				  tb.find("tbody").empty();
				  $.each(json,function(i,n){
					  
					  var msguserid ;
					  var isread = "对方未读";
					  if(n.msg_isread=="1"){
						  isread = "对方已读";
						 
					  }
					  var isme = false;
  					  if($("#userID").val().toString() == n.msg_fromID ){
  						  isme = true;
  						 msguserid = n.msg_toID;
  					  }
  					  else{
  						msguserid = n.msg_fromID;
  					  }
  					  alert($("#"+msguserid).length);
  					  if($("#"+msguserid).length==0){
  						  //不能重复出现同一个人的会话
  						sb.push("<tr>");
  	  					 
    					  if(isme){
    						 
    						  
    						  sb.push("<td id=\""+n.msg_toID+"\"> <a href=\"<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&mainType=4&id="+n.msg_toID+"\" target=\"_blank\">");
    						  sb.push("<img class=\"img-circle\" style=\"width: 60px;height:60px;\" src=\"../"+n.msg_toHead+"\" > </br>");
    						  sb.push("<span style=\"margin-left:15px;\">"+n.msg_to+"</span>");
    						  sb.push("</a></td>");
    					  }
    					  else{
    						
	   						sb.push("<td id=\""+n.msg_fromID+"\"> <a href=\"<c:out value="${rootPath}"/>user/PeopleDetailMain.do?a=detail&mainType=4&id="+n.msg_fromID+"\" target=\"_blank\">");
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
	  					 tb.find("tbody").append(sb.join(""));
  					  }
  					  
				  });
				  
				  
				 
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
	<form class="form-horizontal" style="padding:0;margin:0;" role="form">
		<div class="form-group">
		    <label for="inputEmail3" class="col-sm-1 control-label" style="width:130px;font-size:16px;">搜索收件人：</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control" id="username" placeholder="请输入用户名" />
		    </div>
		    <div class="col-sm-2">
		    	<a  id="btnsendmsg" class="btn btn-success">发送消息</a>
		    </div>
		  </div>
		
	</form>
	 
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
			<span class="count">总记录数：</span><span id="pageAll" title="总记录数" class="count">1 </span>
			<a id="pagePre" class="prev" href="javascript:void();">上一页</a>
			<a id="pageCurrent" class="current" href="javascript:void();">1</a>
			<a id="pageNext" class="prev" href="javascript:void();">下一页</a>
		</div>
	</div>
	
	<%@include file="../../inc/footer.inc"%>
</body>
</html>