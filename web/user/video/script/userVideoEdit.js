$(document).ready(function () {
	//userVideo.init();
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				userVideo.submit();
			}
		}
	});
});
var userVideo = {
	init : function(){
		
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"userVideo.do?a=save", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','保存影聘内容成功！');
	    		} else {
	    			$.messager.alert('保存影聘内容失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}