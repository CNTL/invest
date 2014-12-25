$(document).ready(function () {
	msg.getMyMsgs();
});
var pagei = null;
var msg = {
	addMsg : function(){
		msg.openFormDlg("发消息", msg.getFormHtml(""));
	},
	openFormDlg : function(title,html){
		pagei = $.layer({
			type: 1,   //0-4的选择,
			title: title,
			maxmin: false,
			border: [10, 0.2, '#000'],
			closeBtn: [1, true],
			shadeClose: false,
			fix: true,
			zIndex : 1000,
			area: ['800px', '450px'],
			page: {
				html: html //此处放了防止html被解析，用了\转义，实际使用时可去掉
			}
		});
		$("#form").validationEngine("attach",{
			autoPositionUpdate:false,//是否自动调整提示层的位置
			scroll:false,//屏幕自动滚动到第一个验证不通过的位置
			focusFirstField:false,//验证未通过时，是否给第一个不通过的控件获取焦点
			promptPosition:"topRight" //验证提示信息的位置，可设置为："topRight", "bottomLeft", "centerRight", "bottomRight" 
		});
	},
	getFormHtml : function(id){
		var html = '<div class="job_add">' +
						'<form class="setting-form" id="form" name="form" action="">' +
							'<div class="input">' +
						        '<label for="msgTo">收件人：</label>' + '<span id="msgToDiv"></span>' + 
						        '<input type="hidden" id="msgTo" name="msgTo" value=""/>' +
						        '<input type="hidden" id="msgTo_ID" name="msgTo_ID" value="" />' +
						    '</div>' +
						    '<br>' +
						    '<div class="input">' +
						    	'<label for="msgContent">内容：</label>' +
								'<textarea  id="msgContent" name="msgContent" class="validate[required]" style="width:500px;height:200px;" placeholder="消息内容"></textarea>' +
							'</div>' +
						    '<div class="btn">' +
						    	'<input style="width:100px; margin-left: 100px;" id="btnOK" name="btnOK" value="提交" type="button" onclick="msg.btnOK();"/>' +
						    	'<input style="width:100px; margin-left: 150px;" id="btnCancel" name="btnCancel" value="取消" type="button" onclick="msg.btnCancel();"/>' +
						    '</div>' +
						'</form>' +
					'</div>';
		return html;
	},
	btnOK : function(){
		if(!msg._validForm()) return;
		msg.sendMsg();
		if(pagei != null)
			layer.close(pagei);
	},
	btnCancel: function(){
		if(pagei != null)
			layer.close(pagei);
	},
	_validForm : function() {
		if (!$("#form").validationEngine("validate")){
			//验证提示
			$("#form").validationEngine({scroll:false});
			return false;
		}
		return true;
	},
	sendMsg : function(){
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
		$("#msgDiv .userHead").attr("href", "../user/PeopleDetailMain.do?a=detail&id=" + userMsg.msg_toID);
		var userHead = userMsg.userHead;
		if(userHead == null || userHead.length == 0)
			userHead = "static/image/temp/avatar1.png";
		$("#msgDiv .userHead img").attr("src", rootPath + userHead);
		$("#msgDiv .msgTo").attr("href", "../user/PeopleDetailMain.do?a=detail&id=" + userMsg.msg_toID);
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
		msg.addMsg();
		$("#msgToDiv").html(msg_to);
		$("#msgTo").val(msg_to);
		$("#msgTo_ID").val(msg_toID);
	}
}