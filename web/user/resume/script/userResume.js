$(document).ready(function () {
	/*$("#affixBtn").click(function() {
		resume.ajaxFileUpload("affix");
    });*/
	resume.init();
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				resume.submit();
			}
		}
	});
});
var resume = {
	init : function(){
		var id = $("#id").val();
		if(id == null && id.length == 0) return;
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/resume.do?a=curResume&id=" + id, //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null && data.length == 1){
	    			$("#name").val(data[0].name);
	    			$("#content").val(data[0].content);
	    			$("#affix").val(data[0].affix);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/resume.do?a=saveResume", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.confirm('消息', '保存简历成功！', function(r){
	    				if (r){
	    					window.location.href= "../resume/myresume.do?infoType=1"; 
	    				}
	    			});
	    		} else {
	    			$.messager.alert('保存简历失败！',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert('消息', errorThrown);
			}
	    });
	},
	ajaxFileUpload : function(id) {
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