$(document).ready(function () {
	jobEdit.init();
	//初始化
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				jobEdit.submit();
			}
		}
	});
});
var jobEdit = {
	init : function(){
		//实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    UE.getEditor('content', { initialFrameHeight: 300 });
	},
	submit : function(){
		$.ajax({
	        type:"POST", //请求方式  
	        url:"../../user/recruit.do?a=save", //请求路径  
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