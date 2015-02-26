$(document).ready(function () {
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#submitLogin").attr("disabled", true);
				findPwd.submit();
			}
		}
	});
});

var findPwd = {
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../user/user.do?a=findPwd", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','修改资料成功！');
	    		} else {
	    			$.messager.alert('修改失败',data);
	    		}
	    		$("#btnSave").attr("disabled", false);
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   //alert(errorThrown);
			}
	    });
	}
}