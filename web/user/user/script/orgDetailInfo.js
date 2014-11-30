$(document).ready(function () {
	//初始化
	detail.init();
	$("#form").validationEngine({
		autoPositionUpdate:true,
		onValidationComplete:function(from,r){
			if (r){
				window.onbeforeunload = null;
				$("#btnSave").attr("disabled", true);
				detail.submit();
			}
		}
	});
});
var detail = {
	init : function(){
		$("#mapSearch").attr("onclick","getMap();");
		$.ajax({
	        type:"GET", //请求方式  
	        url:"../user/user.do?a=findLogin", //请求路径  
	        cache: false,
	        dataType: 'JSON',   //返回值类型  
	        success:function(data){
	    		if(data != null){
	    			$("#orgFullname").val(data.orgFullname);
	    			$("#location").val(data.location);
	    			$("#coordinate").val(data.coordinate);
	    			$("#orgNature").val(data.orgNature);
	    			$("#orgTrade").val(data.orgTrade);
	    			$("#orgScale").val(data.orgScale);
	    			$("#orgHomePage").val(data.orgHomePage);
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
	        url:"../user/user.do?a=orgDetailInfo", //请求路径  
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
				   alert(errorThrown);
			}
	    });
	}
}
function getMap(){
	window.open("../user/common/MsgMap.jsp");
}