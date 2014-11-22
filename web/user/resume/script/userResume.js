$(document).ready(function () {
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#submit").attr("disabled", true);
				resume.submit();
			}
		}
	});
});
var resume = {
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../../user/resume.do?a=saveResume", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '上传附件成功！', function(r){
	    				if (r){
	    					window.location.href=window.location.href; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('上传附件失败！',data);
	    		}
	    		$("#submit").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('消息', errorThrown);
			}
	    });
	},
	ajaxFileUpload : function() {
		// 开始上传文件时显示一个图片
        $("#" + id + "Wait").ajaxStart(function() {
            $(this).show();
        // 文件上传完成将图片隐藏起来
        }).ajaxComplete(function() {
            $(this).hide();
        });
        var elementIds=[id]; //flag为id、name属性名
        $.ajaxFileUpload({
            url: '../../user/resume.do?a=uploadAtt&field=' + id, 
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: id + "File", // 上传文件的id、name属性名
            dataType: 'text', //返回值类型，一般设置为json、application/json
            elementIds: elementIds, //传递参数到服务器
            success: function(data, status){
            	if(data != null && data.length > 0){
            		var isStart = (data.indexOf('<pre>') == 0);
                	var isEnd = (data.indexOf('</pre>') == data.length - 6);
                	var srcUrl = "";
                	
                	if(isStart && isEnd){
                		data = data.substr(5,data.length-11);
                	}
                	if(data.length > 0){
                		$("#affix").val(data);
                	} else{
                		$.messager.alert('消息！',"上传失败，请重新上传！");
                	}
            	} else {
            		$.messager.alert('消息！',"上传失败，请重新上传！");
            	}
            	
            },
            error: function(data, status, e){ 
            	$.messager.alert('消息！', e);
            }
        });
    },
	getMyMsgs : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../../user/resume.do?a=getMyMsgs", //请求路径  
	        cache: false,
	        dataType: 'json',   //返回值类型  
	        success:function(result){
	        	if(!result||typeof Object.prototype.toString.call(result) == "[object Array]"||!result.length)return;
				var l = result.length;
				for (var j = 0; j < l; j++) {
					var userResume = result[j];
					msg.setMsgs(userResume);
				}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('初始化失败！',errorThrown);
			}
	    });
	},
	setMsgs : function(userResume){
		/**
		 * 根据接收用户组装消息列表 
		 **/
		$("#msgDiv .userHead").attr("href", "/home/id-" + userResume.msg_toID);
		$("#msgDiv .userHead img").attr("src", rootPath + userResume.userHead);
		
		$("#msgDiv .msgTo").attr("href", "/home/id-" + userResume.msg_toID);
		$("#msgDiv .msgTo").html(userResume.msg_to);
		$("#msgDiv .gray").html(userResume.createTime);
		$("#msgDiv .msg-cnt").html(userResume.msg_content);
		$("#msgDiv .msgNum").attr("href", "msgDetail.jsp?msg_toID=" + userResume.msg_toID +
				"&msg_to=" + userResume.msg_to);
		$("#msgDiv .msgNum").html("共" + userResume.msgNum + "条对话");
		
		$("#msgDiv .Js-reply").attr("onclick", "msg.replyMsg(" + userResume.msg_toID + ",'" + userResume.msg_to + "')");
		$("#msgDiv .delMsg").attr("onclick", "msg.delMsg(" + userResume.msg_toID + ")");
		$(".clearfix").append($("#msgDiv ul").html());
	},
	delMsg : function(id){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../../user/resume.do?a=delResume&id=" + id, //请求路径  
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
	}
}

//校验上传文件
function checkPic() {
	var location = document.getElementById('affixF').value;
	if (location == "") {
		$.messager.alert('消息', "请先选简历附件");
		return false;
	}
	var point = location.lastIndexOf(".");
	var type = location.substr(point).toLowerCase() ;
	if (type == ".doc" || type == ".docx" || type == ".pdf" || type == ".ppt") {
	//if (type == ".jpg") {
		img = document.createElement("img");
		img.src = location;
		if (img.fileSize > 10*1024*1024) {
			$.messager.alert('消息', "附件大小请不要大于10M");
			return false;
		} else {
			document.getElementById("nowDiv").style.display="none";
			return true;
		}
	} else {
		$.messager.alert('消息', "只能上传doc、docx、pdf、ppt格式的附件");
		//alert("只能上传jpg格式的图片");
		return false;
	}
	return true;
}