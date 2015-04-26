<%@ include file="../../include/Include.jsp"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../inc/meta.inc"%>
	<script type="text/javascript">
		var webroot = "<c:out value="${rootPath}"/>";
		var menu = "<c:out value="${menu}"/>";
		$(function(){
			loadMsg();
			$("#btn-reply").click(replay);
			$('body').keypress(function(e){
				if(e.ctrlKey && e.which == 13 || e.which == 10) {
					replay();
				}
			});
			readMsgAll();

		});
		function loadMsg(){
			var msguserid = $.query.get("msguserid");
			$("#msg_toID").val(msguserid);
			 $.getJSON("../user/user.do?a=getUser&id="+msguserid,function(json){
				if(json){
					 var tbmsg = $("#tb-msguser");
					 var msg = [];
					   msg.push("<tr>");
					   msg.push("<td class=\"span2\" style=\"width:100px;\">");
					   msg.push("<a href=\"#\" target=\"_blank\">");
					   msg.push("<img class=\"img-circle\" style=\"width:60px;height: 60px;\" src=\"../"+json.head+"\" />	</a>");
					   msg.push("<p></p></td>");
					   msg.push("<td  class=\"span12\">");
					   msg.push("<a href=\"#\" target=\"_blank\" style=\"font-size:14px;\">"+json.perNickName+"</a>");
					   //msg.push("<small><p>"+json.intro+"</p></small>");
					   msg.push("</td>");
					   msg.push("</tr>");
					   $("#msg_to").val(json.perNickName);
					   tbmsg.append(msg.join(""));
				}
				
			 });
			$.getJSON("../user/msg.do?a=getTalkList&msguserid="+msguserid, function(json){
				  var list = $("#chat-list");
				  var sb = [];
				  $.each(json,function(i,n){
					  var floatcss = "chat_left";
					  
					  if($("#userID").val()==n.msg_fromID){
						  floatcss= "chat_right";
					  }
					  
					  
			
	    			   sb.push("<li>");
	    			   sb.push("<div class=\" old_list "+floatcss+"\">");
	    			   sb.push("<p class=\"chat_time\">"+n.msg_createTime+"</p>");
	    			   sb.push("<p class=\"chat_content line_break\">");
	    			   sb.push(n.msg_content+"</p>");
	    			   sb.push("</li>");

				  });
				  
				  list.empty();
				  list.append(sb.join(""));
				});
		}
		function readMsgAll(){
			var msguserid = $.query.get("msguserid");
			$.get("../user/msg.do?a=readMsgToMe&userID="+msguserid, function(data){
				  
				});
		}
	function replay(){
		
		if($("#txt-content").val()==""){
			$.messager.alert("请输入消息内容。")
			return;
		}
		var data = {
				msgTo_ID:$("#msg_toID").val(),
				msgTo:$("#msg_to").val(),
				msgContent:$("#txt-content").val()
		};
		$.get("../user/msg.do?a=sendMsg",data, function(data){
			  if(data.toString()=="ok"){
				  window.location.reload();
			  }
			});
	}
		
	</script>
	<style>
	  
	small {
	font-size: 85%;
	}
	.muted {
		color: #999;
	}
	.muted {
		color: #777777;
	}
	.table{
		background:#F5F5F5;
		border:1px solid #F5F5F5;
	}
	.chat_ul {
	list-style: none outside none;
	margin: 10px;
	}
	.chat_ul li {
		margin: 10px 5px;
		min-height:60px;
	}
	.chat_left {
		float: left;
		padding-right: 10%;
	}
	.chat_ul .chat_time {
		font-size: 8px;
		color: gray;
		margin-bottom: 0px;
	}
	.line_break {
		white-space: pre-wrap;
		word-wrap: break-word;
		line-height: 24px;
	}
	.chat_content {
		font-size: 14px;
	}
	.chat_left .chat_content {
		background-color: #DAEAF9;
		border-radius: 4px;
		padding: 5px;
	}
	.chat_right {
		float: right;
		padding-left: 10%;
	}
	.chat_right .chat_content {
		background-color: #E7E7E9;
		border-radius: 4px;
		padding: 5px;
	}
	</style>
	 
</head>
<body>
	<%@include file="../../inc/header.inc"%>
	<input type="hidden" id="msg_to" value="" />
	<input type="hidden" id="msg_toID" value="" />
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
				<li style="font-size:28px;line-height:36px;">消息记录</li>
            </ul>
		</div>
    </div>
			
	<div class="people_profile">
		 <div class="text-right"> <a href="../user/MsgMa.do?infoType=1&mainType=4" class="btn btn-info"> 消息中心</a></div>
		<div class="content" style="float:left;width:100%;border:1px solid #F5F5F5;">
		
			<table class="table" id="tb-msguser">
				<tbody>
<!-- 					<tr> -->
<!-- 						<td class="span2"> -->
<!-- 							<a href="#" target="_blank">  -->
<!-- 							<img class="img-circle" style="width:60px;height: 60px;" src="" />	</a>	 -->
<!-- 							<p></p> -->
<!-- 						</td> -->
<!-- 						<td class="span12"> -->
<!-- 							<a href="#" target="_blank">windson7(张国栋)</a> -->
<!-- 							 <small><p>介绍</p></small> -->
<!-- 						 </td> -->
<!-- 					</tr> -->
				</tbody>
			</table>
			<ul class="chat_ul" id="chat-list" style="margin: 0 0 10px 25px;padding:0;">
<!-- 				    <li> -->
<!-- 				    <div class=" old_list chat_right"> -->
<!-- 					    <p class="chat_time">02月09日 22:12</p> -->
<!-- 					    <p class="chat_content line_break"> -->
<!-- 					    <small><font color="green">[送达]</font> </small> - 你好，很想认识你！</p> -->
<!--     				</div> -->
<!--     				<div style="clear: both;"></div> -->
<!--     			   </li> -->
    			   
		     </ul>
			 <textarea class="form-control" id="txt-content" rows="3"></textarea>
			 <div style="text-align:right;"><kbd><kbd>Ctrl</kbd> + <kbd>Enter</kbd></kbd>
			 快捷回复<button id="btn-reply" class="btn btn-success" style="padding:6px 12px;margin:5px;" type="button">发送</button></div>
		</div>
		<div class="clear"></div>
	</div>
	
	<%@include file="../../inc/footer.inc"%>
</body>
</html>