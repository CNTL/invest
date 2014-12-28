var pagei = null;
var msg = {
	addMsg : function(){
		var userID = $("#userID").val();
		if(userID == null || userID.length == 0 || userID == 0){
			setCookie("loginCurrentUrl", window.location.href);
			window.location.href = "../user/loginMain.do"
			return;
		}
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
		$("#msgToDiv").html($("#userName").val());
		$("#msgTo").val($("#userName").val());
		$("#msgTo_ID").val($("#curUserID").val());
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
	}
}