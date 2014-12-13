$(document).ready(function () {
	//autoCompleteInit();
	msg.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				msg.sendMsg();
			}
		}
	});
});
var msg = {
	init : function(){
		msg.closeMsg();
		$("#btnSend").click(msg.openMsg);
		$("#msgTo").blur(msg.toBlur);//会员，带输入提示
		msg.getMyMsgs();
	},
	openMsg : function(){
		$('#w').window('open');
	},
	closeMsg : function(){
		$("#msgTo").val("");
		$("#msgTo_ID").val("");
		$('#w').window('close');
	},
	sendMsg : function(){
		var msgTo_ID = $('#msgTo_ID').val();
		var msgTo = $('#msgTo').val();
		var msgContent = $('#msgContent').val();
		var params = "&msgTo_ID=" + msgTo_ID + "&msgTo=" + encodeURIComponent(msgTo) + "&msgContent=" + encodeURIComponent(msgContent); 
		//alert(params)
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/msg.do?a=sendMsg", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '发送成功！', function(r){
	    				if (r){
	    					window.location.href=window.location.href; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('发送失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('发送失败！',errorThrown);
			}
	    });
	},
	//带输入提示。若不是系统已有名，则清空
	toBlur : function() {
		var id = $("#msgTo_ID").val();
		var name = $("#msgTo").val();
		if (!id) {
			$("#msgTo").val("");
		}
	},
	getMyMsgs : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/msg.do?a=getMyMsgs", //请求路径  
	        cache: false,
	        dataType: 'json',   //返回值类型  
	        success:function(result){
	        	if(!result||typeof Object.prototype.toString.call(result) == "[object Array]"||!result.length)return;
				var l = result.length;
				for (var j = 0; j < l; j++) {
					var userMsg = result[j];
					msg.setMsgs(userMsg);
				}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('初始化失败！',errorThrown);
			}
	    });
	},
	setMsgs : function(userMsg){
		/**
		 * 根据接收用户组装消息列表 
		 **/
		$("#msgDiv .userHead").attr("href", "/home/id-" + userMsg.msg_toID);
		$("#msgDiv .userHead img").attr("src", rootPath + userMsg.userHead);
		
		$("#msgDiv .msgTo").attr("href", "/home/id-" + userMsg.msg_toID);
		$("#msgDiv .msgTo").html(userMsg.msg_to);
		$("#msgDiv .gray").html(userMsg.createTime);
		$("#msgDiv .msg-cnt").html(userMsg.msg_content);
		$("#msgDiv .msgNum").attr("href", "../user/MsgDetailMa.do?infoType=1&msg_toID=" + userMsg.msg_toID);
		$("#msgDiv .msgNum").html("共" + userMsg.msgNum + "条对话");
		
		$("#msgDiv .Js-reply").attr("onclick", "msg.replyMsg(" + userMsg.msg_toID + ",'" + userMsg.msg_to + "')");
		$("#msgDiv .delMsg").attr("onclick", "msg.delMsg(" + userMsg.msg_toID + ")");
		$("#msgList").append($("#msgDiv ul").html());
	},
	delMsg : function(msg_toID){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/msg.do?a=delMsgs&msg_toID=" + msg_toID, //请求路径  
	        cache: false,
	        dataType: 'TEXT',   //返回值类型  
	        success:function(data){
	        	if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '删除成功！', function(r){
	    				if (r){
	    					window.location.href=window.location.href; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('删除失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('删除失败！',errorThrown);
			}
	    });
	},
	replyMsg : function(msg_toID,msg_to){
		$("#msgTo").val(msg_to);
		$("#msgTo_ID").val(msg_toID);
		msg.openMsg();
	}
}