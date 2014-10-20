$(document).ready(function () {
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
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/userMsg.do?a=sendMsg", //请求路径  
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
		var msg_toID = $("#msg_toID").val();
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/userMsg.do?a=getTalkList&msg_toID=" + msg_toID, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(result){
	        	if(!result||typeof Object.prototype.toString.call(result) == "[object Array]"||!result.length)return;
				var l = result.length;
				for (var j = 0; j < l; j++) {
					var userMsg = result[j];
					msg.setMsgs(userMsg);
				}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('删除失败！',errorThrown);
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
		$("#msgDiv .gray").html(userMsg.createTime);
		$("#msgDiv .msg-cnt").html(userMsg.msg_content);
		$("#msgDiv .Js-reply").attr("onclick", "msg.replyMsg(" + userMsg.msg_toID + ",'" + userMsg.msg_to + "')");
		$("#msgDiv .delMsg").attr("onclick", "msg.delMsg(" + userMsg.id + ")");
		$(".clearfix").append($("#msgDiv ul").html());
	},
	delMsg : function(id){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/userMsg.do?a=delMsg&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
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
	replyMsg : function(msg_toID, msg_to){
		$("#msgTo_ID").val(msg_toID);
		$("#msgTo").val(msg_to);
		msg.openMsg();
	}
}
