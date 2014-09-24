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
	    			$("#name").append(data.name);
	    			$("#type").val(data.type);//下拉框
	    			$("#province").val(data.province);//下拉框
	    			$("#city").val(data.city);//下拉框
	    			$("#job").val(data.job);//下拉框
	    			$("#phone").val(data.phone);
	    			$("#identityCard").val(data.identityCard);
	    			$("#organization").val(data.organization);
	    			$("#businessLicense").val(data.businessLicense);
	    			var bankcards = eval(data.bankcards);
	    			if(bankcards != null && bankcards.length > 0){
	    				$("#openingBanks").val(bankcards[0].openingBank);//银行卡开户行
		    			$("#bankNums").val(bankcards[0].bankNum);//银行卡号
	    			}
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
	        url:"user.do?a=relAuth", //请求路径  
	        cache: false,
	        data:$('#form').serialize(),  //传参 
	        dataType: 'text',   //返回值类型  
	        success:function(data){
	    		if(data != null && data == 'ok'){
	    			$.messager.alert('消息','认证资料提交成功！');
	    			//parent.location.href = 'userLogin.jsp';
	    		} else {
	    			$.messager.alert('认证资料提交失败！',data);
	    		}
	        } ,
			error:function (XMLHttpRequest, textStatus, errorThrown) {
				   alert(errorThrown);
			}
	    });
	}
}