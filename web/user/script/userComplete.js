$(document).ready(function () {
	//初始化
	complete.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				complete.submit();
			}
		}
	});
});
var complete = {
	init : function(){
		$.ajax({
	        type:"GET", //请求方式  
	        url:"user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#code").append(data.code);
	    			$("#email").val(data.email);
	    			$("#nickName").val(data.nickName);
	    			$("#postAddr").val(data.postAddr);
	    			$("#intro").val(data.intro);
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
	        url:"user.do?a=complete", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','修改资料成功！');
	    			//parent.location.href = 'userLogin.jsp';
	    		} else {
	    			$.messager.alert('修改失败',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}